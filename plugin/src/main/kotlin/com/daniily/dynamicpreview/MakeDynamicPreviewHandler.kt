package com.daniily.dynamicpreview

//import com.android.tools.idea.gradle.util.GradleProjectPath
import com.android.tools.idea.gradle.model.IdeModuleSourceSet
import com.android.tools.idea.gradle.util.getGradleProjectPath
import com.android.tools.idea.projectsystem.gradle.GradleProjectPath
//import com.android.tools.idea.projectsystem.gradle.getGradleProjectPath
import com.intellij.codeInsight.daemon.GutterIconNavigationHandler
import com.intellij.execution.RunManagerEx
import com.intellij.execution.executors.DefaultRunExecutor
import com.intellij.openapi.externalSystem.model.execution.ExternalSystemTaskExecutionSettings
import com.intellij.openapi.externalSystem.model.execution.ExternalTaskExecutionInfo
import com.intellij.openapi.externalSystem.service.notification.ExternalSystemNotificationManager
import com.intellij.openapi.externalSystem.service.notification.NotificationCategory
import com.intellij.openapi.externalSystem.service.notification.NotificationData
import com.intellij.openapi.externalSystem.service.notification.NotificationSource
import com.intellij.openapi.externalSystem.util.ExternalSystemApiUtil
import com.intellij.openapi.externalSystem.util.ExternalSystemUtil
import com.intellij.openapi.module.Module
import com.intellij.openapi.project.Project
import com.intellij.openapi.util.text.StringUtil
import com.intellij.util.execution.ParametersListUtil
import org.gradle.cli.CommandLineArgumentException
import org.gradle.cli.CommandLineParser
import org.jetbrains.kotlin.idea.util.module
import org.jetbrains.kotlin.psi.KtNamedFunction
import org.jetbrains.plugins.gradle.execution.GradleRunnerUtil
import org.jetbrains.plugins.gradle.service.execution.cmd.GradleCommandLineOptionsConverter
import org.jetbrains.plugins.gradle.util.GradleConstants
import java.awt.event.MouseEvent
import java.io.File
import java.io.IOException
import kotlin.jvm.internal.Intrinsics
import org.jetbrains.rpc.LOG

internal class MakeDynamicPreviewHandler : GutterIconNavigationHandler<KtNamedFunction> {

    private fun runGradle(
        project: Project,
        fullCommandLine: String,
        workDirectory: String,
    ) {
        val taskExecutionInfo: ExternalTaskExecutionInfo
        try {
            taskExecutionInfo = buildTaskInfo(
                workDirectory,
                fullCommandLine,
            )
        } catch (ex: CommandLineArgumentException) {
            val notificationData = NotificationData(
                "<b>Command-line arguments cannot be parsed</b>",
                """
                  <i>$fullCommandLine</i> 
                  ${ex.message}
                  """.trimIndent(),
                NotificationCategory.WARNING, NotificationSource.TASK_EXECUTION
            )
            notificationData.isBalloonNotification = true
            ExternalSystemNotificationManager.getInstance(project)
                .showNotification(GradleConstants.SYSTEM_ID, notificationData)
            return
        }
        ExternalSystemUtil.runTask(
            taskExecutionInfo.settings,
            taskExecutionInfo.executorId,
            project,
            GradleConstants.SYSTEM_ID
        )
        val configuration = ExternalSystemUtil.createExternalSystemRunnerAndConfigurationSettings(
            taskExecutionInfo.settings,
            project, GradleConstants.SYSTEM_ID
        ) ?: return
        val runManager = RunManagerEx.getInstanceEx(project)
        val existingConfiguration = runManager.findConfigurationByName(configuration.name)
        if (existingConfiguration == null) {
            runManager.setTemporaryConfiguration(configuration)
        } else {
            runManager.selectedConfiguration = existingConfiguration
        }
    }


    @Throws(CommandLineArgumentException::class)
    private fun buildTaskInfo(
        projectPath: String,
        fullCommandLine: String,
    ): ExternalTaskExecutionInfo {
        val gradleCmdParser = CommandLineParser()
        val commandLineConverter = GradleCommandLineOptionsConverter()
        commandLineConverter.configure(gradleCmdParser)
        val parsedCommandLine =
            gradleCmdParser.parse(ParametersListUtil.parse(fullCommandLine, true, true))
        val optionsMap = commandLineConverter.convert(parsedCommandLine, HashMap())
        val systemProperties = optionsMap.remove("system-prop")
        val vmOptions = if (systemProperties == null) "" else StringUtil.join(
            systemProperties,
            { entry: String -> "-D$entry" }, " "
        )
        val scriptParameters = StringUtil.join<Map.Entry<String, List<String>>>(
            optionsMap.entries,
            { (longOptionName, values): Map.Entry<String, List<String>> ->
                if (values.isNotEmpty()) {
                    return@join StringUtil.join(
                        values,
                        { entry1: String -> "--$longOptionName $entry1" },
                        " "
                    )
                } else {
                    return@join "--$longOptionName"
                }
            }, " "
        )
        val tasks = parsedCommandLine.extraArguments
        val settings = ExternalSystemTaskExecutionSettings()
        settings.externalProjectPath = projectPath
        settings.taskNames = tasks
        settings.scriptParameters = scriptParameters
        settings.vmOptions = vmOptions
        settings.externalSystemIdString = GradleConstants.SYSTEM_ID.toString()
        return ExternalTaskExecutionInfo(settings, DefaultRunExecutor.EXECUTOR_ID)
    }

    override fun navigate(event: MouseEvent, element: KtNamedFunction) {
        val project = element.project
//        val module = element.module?.getGradleProjectPath()?.path?.let { "$it:" } ?: ""
        val module = element.module?.getModuleGradleProjectPath()?.path?.let { "$it:" } ?: ""
        val task = "kspDebugKotlin"
        val command = "$module$task"
        runGradle(project, command, project.basePath ?: return)
    }

}

private fun Module.getModuleGradleProjectPath(
    useCanonicalPath: Boolean = false
): GradleProjectPath? {
    val projectId = ExternalSystemApiUtil.getExternalProjectId(this) ?: return null
    val gradleProjectPath = ":" + projectId.substringAfter(':', "")
    val projectPath = GradleRunnerUtil.resolveProjectPath(this) ?: return null
    val projectFile = File(projectPath)
    val rootFolder: File = if (useCanonicalPath) {
        try {
            projectFile.canonicalFile
        } catch (e: IOException) {
            projectFile
        }
    } else {
        projectFile.absoluteFile
    }
    return try {
        GradleProjectPath(rootFolder, gradleProjectPath, IdeModuleSourceSet.MAIN)
    } catch (e: InstantiationError) {
        LOG.error("InstantiationError")
        LOG.error("message = ${e.message}")
        LOG.error("localizedMessage = ${e.localizedMessage}")
        LOG.error("cause = ${e.cause}")
        LOG.error(e)
        null
    }
}

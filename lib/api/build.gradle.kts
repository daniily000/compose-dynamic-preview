plugins {
    id("kotlin")
    `java-library`
    `maven-publish`
    id("project-build")
}

applyConfig(Configs.Api)

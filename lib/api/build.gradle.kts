import configs.applyConfig

plugins {
    id("project-build")
    id("kotlin")
    `java-library`
    `maven-publish`
    signing
}

applyConfig(Configs.Api)

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }

    versionCatalogs {
        create("cryptographyLibs") {
            from("dev.whyoleg.cryptography:cryptography-version-catalog:0.3.0")
        }
    }
}

rootProject.name = "kn-openssl-linkage-issue"

import org.jetbrains.kotlin.gradle.tasks.*

plugins {
    kotlin("multiplatform") version "1.9.10"
}

kotlin {
//    @OptIn(ExperimentalKotlinGradlePluginApi::class)
//    compilerOptions {
//        this.freeCompilerArgs.add("-Xverbose-phases=Linker")
//    }

    val nativeTargets = listOf(
        mingwX64(),
        linuxX64(),
        macosX64(),
        macosArm64(),
    ).onEach {
        it.binaries.executable {
            entryPoint = "main"
        }
        it.compilations.configureEach {
            compilerOptions.configure {
                freeCompilerArgs.add("-Xverbose-phases=Linker")
            }
        }
    }

    sourceSets {
        val nativeMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(cryptographyLibs.provider.openssl3.prebuilt)
            }
        }
        nativeTargets.forEach {
            getByName("${it.name}Main").dependsOn(nativeMain)
        }
    }
}

tasks.build {
    dependsOn(tasks.withType<KotlinNativeLink>())
}

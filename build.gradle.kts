import org.jetbrains.kotlin.gradle.*
import org.jetbrains.kotlin.gradle.plugin.mpp.*

plugins {
    kotlin("multiplatform") version "1.9.10"
}

kotlin {
    @OptIn(ExperimentalKotlinGradlePluginApi::class)
    compilerOptions {
        this.freeCompilerArgs.add("-Xverbose-phases=Linker")
    }

    val javaOsName = System.getProperty("os.name")
    val javaOsArch = System.getProperty("os.arch")
    when {
        javaOsName.contains("mac", ignoreCase = true)     -> when (javaOsArch) {
            "x86_64", "amd64"  -> macosX64("native")
            "arm64", "aarch64" -> macosArm64("native")
            else               -> error("Unknown os.arch: $javaOsArch")
        }

        javaOsName.contains("linux", ignoreCase = true)   -> linuxX64("native")
        javaOsName.contains("windows", ignoreCase = true) -> mingwX64("native")
        else                                              -> error("Unknown os.name: $javaOsName")
    }

    sourceSets {
        nativeMain.dependencies {
            implementation(cryptographyLibs.provider.openssl3.prebuilt)
        }

        nativeTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

// additional configuration for testing in release mode
kotlin {
    targets.withType<KotlinNativeTarget>().configureEach {
        binaries.test(listOf(NativeBuildType.RELEASE))
    }
    targets.withType<KotlinNativeTargetWithTests<*>>().configureEach {
        testRuns.create("releaseTest") {
            setExecutionSourceFrom(binaries.getTest(NativeBuildType.RELEASE))
        }
    }
}

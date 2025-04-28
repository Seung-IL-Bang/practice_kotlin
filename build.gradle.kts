plugins {
    kotlin("jvm") version "1.9.25" // the plugin defines the version of Kotlin to be used in the project
    kotlin("plugin.spring") version "1.9.25" apply false// Kotlin Spring compiler plugin for adding the open modifier to Kotlin classes in order to make them compatible with Spring Framework features
    id("org.springframework.boot") version "3.4.5" apply false
    id("io.spring.dependency-management") version "1.1.7"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

allprojects {
    group = "io.demo"
    version = "0.0.1-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.kotlin.plugin.spring")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    tasks.named("bootJar") {
        enabled = false
    }

    tasks.named("jar") {
        enabled = true
    }

    dependencies {
        /**
         *  In the dependencies block, a few Kotlin-related modules listed:
         *  the module adds support for serialization and deserialization of Kotlin classes and data classes
         *  Jackson extensions for Kotlin for working with JSON
         */
        implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
        /**
         * Kotlin reflection library
         * Kotlin reflection library, required for working with Spring
         */
        implementation("org.jetbrains.kotlin:kotlin-reflect")

//        runtimeOnly("com.h2database:h2")
        testImplementation("org.springframework.boot:spring-boot-starter-test")
        testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    kotlin { // the kotlin plugin configuration block. This is where you can add extra arguments to the compiler to enable or disable various language features.
        compilerOptions {
            freeCompilerArgs.addAll("-Xjsr305=strict") // `-Xjsr305=strict` enables the strict mode for JSR-305 annotations
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}


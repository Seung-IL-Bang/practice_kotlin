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

// build.gradle.kts에 추가할 스크립트

// 모듈 이름을 카멜 케이스로 변환하는 함수
fun toCamelCase(text: String): String {
    if (text.isEmpty()) {
        return ""
    }

    val result = StringBuilder()
    val words = text.split("-", "_") // 하이픈 또는 언더스코어로 분리

    // 첫 번째 단어는 소문자로 시작
    result.append(words[0].lowercase())

    // 나머지 단어들은 대문자로 시작
    for (i in 1 until words.size) {
        if (words[i].isNotEmpty()) {
            result.append(words[i].substring(0, 1).uppercase())
            if (words[i].length > 1) {
                result.append(words[i].substring(1).lowercase())
            }
        }
    }

    return result.toString()
}

// Spring Boot Kotlin 모듈 생성 함수
fun createSpringBootKotlinModule(moduleName: String, packageName: String) {
    val base = file(moduleName)
    val packagePath = packageName.replace('.', '/')

    val moduleCamelCase = toCamelCase(moduleName)
    val applicationClassName = "${moduleCamelCase.substring(0, 1).uppercase()}${moduleCamelCase.substring(1)}Application"

    // 디렉토리 경로 생성
    listOf(
        "${base}/src/main/kotlin/${packagePath}",
        "${base}/src/test/kotlin/${packagePath}",
        "${base}/src/main/resources/static",
        "${base}/src/main/resources/templates"
    ).forEach { path ->
        val f = file(path)
        if (!f.exists()) {
            f.mkdirs()
        }
    }

    // 모듈의 루트 디렉토리 생성
    if (!base.exists()) {
        base.mkdirs()
    }

    // build.gradle.kts 파일 생성
    val buildFile = file("${base}/build.gradle.kts")
    if (!buildFile.exists()) {
        buildFile.createNewFile()  // 먼저 빈 파일 생성
        buildFile.writeText(
"""tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = true
}

tasks.getByName<Jar>("jar") {
    enabled = false
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
}
"""
        )
    }

    // Application.kt 생성
    val appClassFile = file("${base}/src/main/kotlin/${packagePath}/${applicationClassName}.kt")
    if (!appClassFile.exists()) {
        appClassFile.parentFile.mkdirs() // 부모 디렉토리 확인
        appClassFile.createNewFile() // 파일 생성
        appClassFile.writeText(
            """package ${packageName}

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ${applicationClassName}

fun main(args: Array<String>) {
    runApplication<${applicationClassName}>(*args)
}
"""
        )
    }

    // application.properties 생성
    val propertiesFile = file("${base}/src/main/resources/application.properties")
    if (!propertiesFile.exists()) {
        propertiesFile.parentFile.mkdirs() // 부모 디렉토리 확인
        propertiesFile.createNewFile() // 파일 생성
        propertiesFile.writeText(
            """spring.application.name=${moduleName}
"""
        )
    }

    // 테스트 클래스 생성
    val testClassFile = file("${base}/src/test/kotlin/${packagePath}/${applicationClassName}Tests.kt")
    if (!testClassFile.exists()) {
        testClassFile.parentFile.mkdirs() // 부모 디렉토리 확인
        testClassFile.createNewFile() // 파일 생성
        testClassFile.writeText(
            """package ${packageName}

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ${applicationClassName}Tests {

    @Test
    fun contextLoads() {
    }
}
"""
        )
    }

    // settings.gradle.kts 파일 업데이트
    val settingsKtsFile = file("${rootProject.projectDir}/settings.gradle.kts")
    if (settingsKtsFile.exists()) {
        val settingsContent = settingsKtsFile.readText()

        // 이미 포함되어 있는지 확인
        if (!settingsContent.contains("include(\"${moduleName}\")")) {
            // 새로운 include 명령어 추가
            if (settingsContent.endsWith("\n")) {
                settingsKtsFile.appendText("include(\"${moduleName}\")")
            } else {
                settingsKtsFile.appendText("\ninclude(\"${moduleName}\")")
            }
            println("Added '${moduleName}' to settings.gradle.kts")
        } else {
            println("Module '${moduleName}' was already included in settings.gradle.kts")
        }
    } else {
        println("settings.gradle.kts file not found.")
    }

    println("Kotlin Module '${moduleName}' with Spring Boot Application created successfully.")
    println("Package: ${packageName}")
    println("Application class: ${applicationClassName}.kt")
    println("Test class: ${applicationClassName}Tests.kt")
}

// 모듈 생성 태스크 등록
tasks.register("createModule") {
    doLast {
        val moduleName = project.findProperty("moduleName") as String?
        val packageName = project.findProperty("packageName") as String? ?: project.group.toString()

        if (moduleName.isNullOrEmpty()) {
            throw GradleException("Usage: ./gradlew createKotlinModule -PmoduleName=my-module [-PpackageName=com.example.module]")
        }

        createSpringBootKotlinModule(moduleName, packageName)
    }
}










val exposedVersion: String by project
dependencies {
    api("org.jetbrains.exposed:exposed-core:$exposedVersion")
    api("org.jetbrains.exposed:exposed-crypt:$exposedVersion")
    api("org.jetbrains.exposed:exposed-dao:$exposedVersion")
    api("org.jetbrains.exposed:exposed-jdbc:$exposedVersion")

//    api("org.jetbrains.exposed:exposed-jodatime:$exposedVersion")
//    // or
//    api("org.jetbrains.exposed:exposed-java-time:$exposedVersion")
//    // or
    api("org.jetbrains.exposed:exposed-kotlin-datetime:$exposedVersion")

    api("org.jetbrains.exposed:exposed-json:$exposedVersion")
    api("org.jetbrains.exposed:exposed-money:$exposedVersion")
    api("org.jetbrains.exposed:exposed-spring-boot-starter:$exposedVersion")
}

// https://github.com/JetBrains/Exposed?tab=readme-ov-file

/**
 * implementation: 의존성이 구현에만 사용되고, 이 모듈을 사용하는 다른 모듈로 의존성이 전파되지 않습니다.
 * api: 의존성이 모듈의 공개 API의 일부로 간주되어, 이 모듈을 사용하는 다른 모듈로 의존성이 전파됩니다.
 */
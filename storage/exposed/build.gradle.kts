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
plugins {
    kotlin("jvm") version "2.1.20"
    kotlin("plugin.spring") version "2.1.20"
    id("org.springframework.boot") version "3.4.4"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("plugin.jpa") version "2.1.20"
    id("com.github.node-gradle.node") version "7.0.2" // ✅ Node.js + npm
}

group = "com.example"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("nz.net.ultraq.thymeleaf:thymeleaf-layout-dialect:3.4.0")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// --- ✨ Node.js, npm 설정 ---
node {
    version.set("20.11.0")
    npmVersion.set("10.2.4")
    download.set(true)                   // ✅ 로컬에 없으면 자동 설치
    nodeProjectDir.set(file("${projectDir}"))
}

// --- ✨ NPM 라이브러리 복사 Task ---
val npmCopyLibs by tasks.registering(com.github.gradle.node.npm.task.NpmTask::class) {
    dependsOn("npmInstall")               // ✅ npm install 먼저
    args.set(listOf("run", "copy-libs"))   // ✅ npm run copy-libs 실행
    inputs.file("package.json")            // ✅ package.json 변경 감지
    outputs.dir("src/main/resources/static/libs") // ✅ 결과물 output 명시
}

// --- ✨ Spring Boot 빌드 전에 npmCopyLibs 실행 연결 ---
tasks.named<ProcessResources>("processResources") {
    dependsOn(npmCopyLibs) // ✅ 리소스 복사 전에 npmCopyLibs 무조건 실행
}
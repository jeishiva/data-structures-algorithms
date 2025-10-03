plugins {
    kotlin("jvm") version "1.9.23"
}

group = "com.jeishiva"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlin test (common assertions)
    testImplementation(kotlin("test"))

    // JUnit 5 (Jupiter) for @Test, @BeforeEach, etc.
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.11.0")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.11.0")
}

tasks.test {
    useJUnitPlatform()
}

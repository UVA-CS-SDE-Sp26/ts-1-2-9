plugins {
    id("java")
    id("application")
}


application {
    mainClass.set("TopSecret")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:5.+")
    implementation("org.jspecify:jspecify:1.0.0")
}

tasks.test {
    useJUnitPlatform()
}


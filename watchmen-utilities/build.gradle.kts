plugins {
    id("java")
}

group = "com.matrdata.watchmen"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

//dependencies {
//    implementation(project(":watchmen-model"))
//}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
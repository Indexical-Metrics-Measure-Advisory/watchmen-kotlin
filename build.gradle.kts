import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.6.20"
}

allprojects {
    group = "com.matrdata"
    version = "1.0.0-SNAPSHOT"

    apply {
        plugin("java")
    }

    repositories {
        mavenCentral()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

subprojects {
    group = "com.matrdata"
    version = "1.0.0-SNAPSHOT"

    configure<SourceSetContainer> {
        named("main") {
            java.srcDir("src/main/kotlin")
        }
    }

    repositories {
        mavenCentral()
    }

    apply {
        plugin("kotlin")
        plugin("java")
    }

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
        testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
        testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    }

    tasks.getByName<Test>("test") {
        useJUnitPlatform()
    }

    val compileKotlin: KotlinCompile by tasks
    compileKotlin.kotlinOptions {
        jvmTarget = "1.8"
    }
    val compileTestKotlin: KotlinCompile by tasks
    compileTestKotlin.kotlinOptions {
        jvmTarget = "1.8"
    }
}


//tasks.withType<KotlinCompile> {
//    kotlinOptions {
//        jvmTarget = JavaVersion.VERSION_1_8.toString()
//        freeCompilerArgs = listOfNotNull(
//            "-Xopt-in=kotlin.RequiresOptIn",
//            "-Xinline-classes",
//            "-Xallow-result-return-type"
//        )
//    }
//}
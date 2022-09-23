import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	kotlin("jvm") version "1.7.10"
}

allprojects {
	group = "com.matrdata.watchmen"
	version = "1.0.0-SNAPSHOT"

	repositories {
		mavenCentral()
	}
}

subprojects {
	group = "com.matrdata.watchmen"
	version = "1.0.0-SNAPSHOT"

	apply {
		plugin("kotlin")
		plugin("java")
	}

	configure<SourceSetContainer> {
		named("main") {
			java.srcDir("src/main/kotlin")
		}
	}

	repositories {
		mavenCentral()
	}

	dependencies {
		implementation(kotlin("stdlib-jdk8"))
		testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
		testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
	}

	tasks.getByName<Test>("test") {
		useJUnitPlatform()
	}

	java {
		sourceCompatibility = JavaVersion.VERSION_17
		targetCompatibility = JavaVersion.VERSION_17
	}

	val compileKotlin: KotlinCompile by tasks
	compileKotlin.kotlinOptions {
		jvmTarget = "17"
	}
	val compileTestKotlin: KotlinCompile by tasks
	compileTestKotlin.kotlinOptions {
		jvmTarget = "17"
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
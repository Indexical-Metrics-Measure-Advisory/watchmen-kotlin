plugins {
	id("java")
}

dependencies {
	api(project(":watchmen-auth"))
	api(project(":watchmen-storage"))
	api(project(":watchmen-meta"))
}

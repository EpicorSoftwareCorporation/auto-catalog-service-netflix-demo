import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.4.3"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	kotlin("jvm") version "1.4.30"
	kotlin("plugin.spring") version "1.4.30"
	id("com.netflix.dgs.codegen") version "4.0.12"
}

group = "com.epicor.auto.catalog"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
}

dependencies {
	api("com.netflix.graphql.dgs:graphql-dgs-spring-boot-starter:3.1.1")

	implementation("com.graphql-java:graphql-java-extended-scalars:1.0")
	implementation("com.github.javafaker:javafaker:1.+")

	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

	implementation ("com.datastax.oss","native-protocol","1.4.11")
	implementation ("com.datastax.oss","java-driver-core-shaded","4.9.0")
	implementation ("com.datastax.oss","java-driver-mapper-runtime","4.9.0")
	implementation ("com.datastax.oss","java-driver-core","4.9.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

@OptIn(kotlin.ExperimentalStdlibApi::class)
tasks.withType<com.netflix.graphql.dgs.codegen.gradle.GenerateJavaTask> {
	generateClient = true
	packageName = "com.epicor.auto.catalog.labor.generated"
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "11"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

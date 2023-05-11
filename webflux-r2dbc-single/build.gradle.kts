plugins {
	java
	id("org.springframework.boot") version "2.7.7"
	id("io.spring.dependency-management") version "1.0.15.RELEASE"
	id("maven-publish")
}

group = "com"
version = "0.0.1"

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	maven { url = uri("https://repo.spring.io/release") }
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation ("org.springframework.boot:spring-boot-starter-data-r2dbc:2.7.7")
	implementation("org.mariadb:r2dbc-mariadb:1.1.2")
//	implementation("io.asyncer:r2dbc-mysql:1.0.0")
//	implementation("io.r2dbc:r2dbc-mssql:1.0.0.RELEASE")
//	implementation("jakarta.persistence:jakarta.persistence-api:3.1.0")

	implementation("org.apache.commons:commons-lang3:3.12.0")
	implementation("com.google.code.gson:gson:2.10.1")

	compileOnly("org.projectlombok:lombok")
	developmentOnly("org.springframework.boot:spring-boot-devtools")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-logging:3.0.1")
	implementation("org.springframework.boot:spring-boot-starter-log4j2:3.0.1")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

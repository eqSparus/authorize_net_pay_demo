plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "com.bytepace.antiland"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// Дополнительно
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Платежи
	implementation("net.authorize:anet-java-sdk:3.0.0")

	// Web
	implementation("org.springframework.boot:spring-boot-starter-web")

	// Безопасность
//	implementation("org.springframework.boot:spring-boot-starter-security")

	// БД
//	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//	runtimeOnly("org.postgresql:postgresql")

	// Тесты
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("org.springframework.security:spring-security-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

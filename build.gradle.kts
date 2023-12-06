plugins {
	id("java")
	id("org.springframework.boot") version "3.2.0"
	id("io.spring.dependency-management") version "1.1.4"
	id("io.freefair.lombok") version "8.4"
	id("org.sonarqube") version "4.4.1.3373"
}

group = "eu.nerdfactor"
version = "1.0.0"

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")

	// database
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	runtimeOnly("com.h2database:h2")

	testImplementation(platform("org.junit:junit-bom:5.9.1"))
	testImplementation("org.junit.jupiter:junit-jupiter")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation(kotlin("stdlib-jdk8"))
}

tasks.test {
	useJUnitPlatform()
}

tasks{
	test{
		useJUnitPlatform()
	}
	bootBuildImage{
		imageName = "nerdfactor/bowling-service"
	}
}
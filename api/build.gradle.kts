plugins {
  java
  id("org.springframework.boot") version "2.2.4.RELEASE"
  id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("io.freefair.lombok") version "5.0.0-rc2"
	
  jacoco	
}       

repositories {
	mavenCentral()
}

dependencies {
	implementation("com.fasterxml.jackson.core:jackson-databind")
	implementation("org.springframework.boot:spring-boot-starter-web")
  implementation("io.projectreactor:reactor-core")
	testImplementation("org.apache.httpcomponents:httpclient")
	testImplementation("org.mockito:mockito-core:2.+")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	testImplementation("de.flapdoodle.embed:de.flapdoodle.embed.mongo")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
  
  implementation("org.springframework.boot:spring-boot-starter-security")
  implementation("org.springframework.security:spring-security-test")
  testImplementation("org.springframework.security:spring-security-test")
  implementation("io.jsonwebtoken:jjwt:0.9.1")
}

tasks {
    val treatWarningsAsError =
            listOf("-Xlint:unchecked", "-Xlint:deprecation", "-Werror")

    getByName<JavaCompile>("compileJava") {
        options.compilerArgs = treatWarningsAsError
    }

    getByName<JavaCompile>("compileTestJava") {
        options.compilerArgs = treatWarningsAsError
    }
}
 
sourceSets {
  main {
    java.srcDirs("src/main/java")
  }
  test {
    java.srcDirs("src/test/java")
  }
}

val test by tasks.getting(Test::class) {
	useJUnitPlatform {}
}

defaultTasks("clean", "test", "jacocoTestReport")
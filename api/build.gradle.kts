plugins {
  java
  id("org.springframework.boot") version "2.2.4.RELEASE"
  id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("io.freefair.lombok") version "5.0.0-rc2"
	
  jacoco
  pmd
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
  testImplementation("org.springframework.security:spring-security-test")
  
  implementation("io.jsonwebtoken:jjwt-api:0.11.1")
  implementation("io.jsonwebtoken:jjwt-impl:0.11.1")
            // Uncomment the next line if you want to use RSASSA-PSS (PS256, PS384, PS512) algorithms:
            //'org.bouncycastle:bcprov-jdk15on:1.60',
  implementation("io.jsonwebtoken:jjwt-jackson:0.11.1") // or 'io.jsonwebtoken:jjwt-gson:0.11.1' for gson
  
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

pmd {
  isIgnoreFailures = false
}

defaultTasks("clean", "test", "jacocoTestReport", "pmdMain")
import org.apache.tools.ant.filters.ReplaceTokens

plugins {
    id("java")
    id("com.gradleup.shadow") version "8.3.5"
}

group = "dev.jsinco.topvoter"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://oss.sonatype.org/content/groups/public/")
    maven("https://repo.extendedclip.com/content/repositories/placeholderapi/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.20-R0.1-SNAPSHOT")
    compileOnly("com.github.NuVotifier:NuVotifier:2.7.2")
    compileOnly("me.clip:placeholderapi:2.11.6")
    implementation("io.javalin:javalin:6.5.0")
    compileOnly("org.apache.logging.log4j:log4j-api:2.23.1")
    compileOnly("org.apache.logging.log4j:log4j-core:2.23.1")
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(17)
}

tasks {
    processResources {
        outputs.upToDateWhen { false }
        filter<ReplaceTokens>(mapOf(
            "tokens" to mapOf("version" to project.version.toString()),
            "beginToken" to "\${",
            "endToken" to "}"
        )).filteringCharset = "UTF-8"
    }

    shadowJar {
        relocate("io.javalin", "dev.jsinco.topvoter.javalin")
        relocate("org.eclipse.jetty", "dev.jsinco.topvoter.javalin.jetty")
    }

    build {
        dependsOn(shadowJar)
    }
}

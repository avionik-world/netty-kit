import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    kotlin("plugin.serialization") version "1.9.0"
    kotlin("jvm") version "1.9.23"
    alias(libs.plugins.sonatypeCentralPortalPublisher)
}

group = "world.avionik"
version = "1.0.1"

repositories {
    mavenCentral()

    // simplecloud dependencies
    maven("https://repo.thesimplecloud.eu/artifactory/list/gradle-release-local/")
}

dependencies {
    compileOnly(kotlin("stdlib"))

    // avionik-world dependencies
    compileOnly("world.avionik:config-kit:1.0.2")
    runtimeOnly("world.avionik:config-kit:1.0.2")
    api("world.avionik:clientserverapi:4.2.1")

    // netty dependencies
    api("io.netty:netty-all:4.1.100.Final")

    // simplecloud dependencies
    compileOnly("eu.thesimplecloud.jsonlib:json-lib:1.0.10")

    // log4j dependencies
    compileOnly("org.apache.logging.log4j:log4j-core:2.23.1")
    runtimeOnly("org.apache.logging.log4j:log4j-core:2.23.1")
    compileOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.23.1")
    runtimeOnly("org.apache.logging.log4j:log4j-slf4j-impl:2.23.1")
}

tasks.named("shadowJar", ShadowJar::class) {
    mergeServiceFiles()
}

signing {
    useGpgCmd()
    sign(configurations.archives.get())
}

centralPortal {
    username = project.findProperty("sonatypeUsername") as String
    password = project.findProperty("sonatypePassword") as String

    pom {
        name.set("Netty Kit")
        description.set("Easy to create a NettyServer and a NettyClient")
        url.set("https://github.com/avionik-world/netty-kit")

        developers {
            developer {
                id.set("niklasnieberler")
                email.set("admin@avionik.world")
            }
        }
        licenses {
            license {
                name.set("Apache-2.0")
                url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
            }
        }
        scm {
            url.set("https://github.com/avionik-world/netty-kit.git")
            connection.set("git:git@github.com:avionik-world/netty-kit.git")
        }
    }
}
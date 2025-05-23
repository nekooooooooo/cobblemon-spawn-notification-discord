import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "2.1.20"
    id("fabric-loom") version "1.9.1"
    id("maven-publish")
    kotlin("plugin.serialization") version "2.1.10"
}

version = project.property("mod_version") as String
group = project.property("maven_group") as String

base {
    archivesName.set(project.property("archives_base_name") as String)
}

val targetJavaVersion = 21
java {
    toolchain.languageVersion = JavaLanguageVersion.of(targetJavaVersion)
    // Loom will automatically attach sourcesJar to a RemapSourcesJar task and to the "build" task
    // if it is present.
    // If you remove this line, sources will not be generated.
    withSourcesJar()
}

loom {
    splitEnvironmentSourceSets()

    mods {
        register("cobblemon_spawn_notification_discord") {
            sourceSet("main")
            sourceSet("client")
        }
    }
}

repositories {
    // Add repositories to retrieve artifacts from in here.
    // You should only use this when depending on other mods because
    // Loom adds the essential maven repositories to download Minecraft and libraries from automatically.
    // See https://docs.gradle.org/current/userguide/declaring_repositories.html
    // for more information about repositories.
    mavenLocal()
    mavenCentral()
    maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
    maven("https://maven.impactdev.net/repository/development/")
    maven("https://api.modrinth.com/maven")
    maven("libs")
    maven("https://repo.erdbeerbaerlp.de/repository/maven-public/")
    maven("https://cursemaven.com")
    maven("https://maven.wispforest.io/releases/")
}

dependencies {

    // To change the versions see the gradle.properties file
    minecraft("com.mojang:minecraft:${project.property("minecraft_version")}")
    mappings("net.fabricmc:yarn:${project.property("yarn_mappings")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${project.property("loader_version")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${project.property("kotlin_loader_version")}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${project.property("fabric_version")}")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0")

    // Cobblemon
    modImplementation("com.cobblemon:fabric:${property("cobblemon_version")}")

    // Cobblemon Spawn Notifier
    // modImplementation(files("libs/cobblemon-spawn-notification-1.5-fabric-1.2.1.jar"))

    // modImplementation(files("libs/cobblemon-spawn-notification-1.5-fabric-1.2.1.jar"))
    modImplementation("maven.modrinth:cobblemon-spawn-notification:${property("cobblemon_spawn_notification_version")}")
    // modImplementation("maven.modrinth:dcintegration:${property("dcintegration_version")}")

    // MC2Discord
    compileOnly("maven.modrinth:mc2discord:Gl8sM8TE")

    // Discord-MC-Chat
    // modImplementation("maven.modrinth:discord-mc-chat:7ptDmFQV")
    compileOnly(files("libs/Discord-MC-Chat-1.20.1-2.4.1.jar"))

    compileOnly("com.squareup.okhttp3:okhttp:4.12.0")

    // DiscordIntegration
    compileOnly("de.erdbeerbaerlp:dcintegration.common:3.0.7")
    compileOnly("org.apache.logging.log4j:log4j-api:2.17.1")
}

tasks.processResources {
    inputs.property("version", project.version)
    inputs.property("minecraft_version", project.property("minecraft_version"))
    inputs.property("loader_version", project.property("loader_version"))
    filteringCharset = "UTF-8"

    filesMatching("fabric.mod.json") {
        expand(
            "version" to project.version,
            "minecraft_version" to project.property("minecraft_version"),
            "loader_version" to project.property("loader_version"),
            "kotlin_loader_version" to project.property("kotlin_loader_version")
        )
    }
}

tasks.withType<JavaCompile>().configureEach {
    // ensure that the encoding is set to UTF-8, no matter what the system default is
    // this fixes some edge cases with special characters not displaying correctly
    // see http://yodaconditions.net/blog/fix-for-java-file-encoding-problems-with-gradle.html
    // If Javadoc is generated, this must be specified in that task too.
    options.encoding = "UTF-8"
    options.release.set(targetJavaVersion)
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions.jvmTarget.set(JvmTarget.fromTarget(targetJavaVersion.toString()))
}

tasks.jar {
    from("LICENSE") {
        rename { "${it}_${project.base.archivesName}" }
    }
}

// configure the maven publication
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifactId = project.property("archives_base_name") as String
            from(components["java"])
        }
    }

    // See https://docs.gradle.org/current/userguide/publishing_maven.html for information on how to set up publishing.
    repositories {
        // Add repositories to publish to here.
        // Notice: This block does NOT have the same function as the block in the top level.
        // The repositories here will be used for publishing your artifact, not for
        // retrieving dependencies.
    }
}

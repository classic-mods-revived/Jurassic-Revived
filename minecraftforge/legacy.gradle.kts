import org.slf4j.event.Level

import org.gradle.jvm.toolchain.JavaLanguageVersion

val isForge1201 = stonecutter.current.version == "1.20.1"
val targetJava = if (isForge1201) 17 else 21

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(targetJava))
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.release.set(targetJava)
}



plugins {
	`multiloader-loader`
	id("net.neoforged.moddev.legacyforge")
}

legacyForge {
	version = "${commonMod.minecraft_version}-${commonMod.prop("minecraftforge_version")}"
}

repositories {
	mavenCentral()
	maven("https://maven.architectury.dev/")
	maven("https://maven.terraformersmc.com/releases/")
	maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
	maven("https://maven.blamejared.com/")
	maven("https://maven.shedaniel.me/")
}

dependencies {
    // Architectury transformer runtime (declare it directly)
    modRuntimeOnly("dev.architectury:architectury-transformer:5.2.88:runtime")

    // Architectury (Forge)
    modImplementation("dev.architectury:architectury-forge:${commonMod.prop("architectury_version")}")
	modImplementation("me.shedaniel.cloth:cloth-config-forge:${commonMod.prop("cloth_config_version_1_20_1")}")

	// GeckoLib: if you get SRG/mapping issues at runtime, this should also be a mod dependency
    modImplementation("software.bernie.geckolib:geckolib-forge-${commonMod.minecraft_version}:${commonMod.prop("geckolib_version")}")

    modCompileOnly("mezz.jei:jei-${commonMod.minecraft_version}-common-api:${commonMod.prop("jei_version")}")
    modCompileOnly("mezz.jei:jei-${commonMod.minecraft_version}-forge-api:${commonMod.prop("jei_version")}")
    modRuntimeOnly("mezz.jei:jei-${commonMod.minecraft_version}-forge:${commonMod.prop("jei_version")}")
}


legacyForge {
	val at = project.file("build/resources/main/META-INF/accesstransformer.cfg");

	accessTransformers.from(at.absolutePath)
	validateAccessTransformers = true

	runs {
		register("client") {
			client()
			ideName = "MinecraftForge Client (${project.path})"
			logLevel = Level.TRACE
		}
		register("gameTestServer") {
			type = "gameTestServer"
			ideName = "MinecraftForge GameTestServer (${project.path})"
			logLevel = Level.TRACE
		}
		register("data") {
			data()
			ideName = "MinecraftForge Data (${project.path})"
			logLevel = Level.TRACE
		}
		register("server") {
			server()
			ideName = "MinecraftForge Server (${project.path})"
			logLevel = Level.TRACE
		}
	}

	parchment {
		commonMod.propOrNull("parchment_mappings")?.let {
			mappingsVersion = it
			minecraftVersion = if (it != "") commonMod.minecraft_version else ""
		}
	}

	mods {
		register(commonMod.id) {
			sourceSet(sourceSets.main.get())
		}
	}
}

sourceSets.main {
	resources.srcDir("src/generated/resources")
}

tasks {
	processResources {
		exclude("${mod.id}.accesswidener")

		val atFile =
			project(":common").file("src/main/resources/accesstransformers/accesstransformer-${commonMod.minecraft_version}.cfg")

		from(atFile.parentFile) {
			include(atFile.name)
			rename(atFile.name, "META-INF/accesstransformer.cfg")
			into("")
		}
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(":minecraftforge:${commonMod.propOrNull("minecraft_version")}:processResources")
}
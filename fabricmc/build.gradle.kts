plugins {
	`multiloader-loader`
	id("dev.architectury.loom")
}

val is120 = commonMod.minecraft_version.startsWith("1.20")
val targetBytecode = if (is120) 17 else 21

val clothConfigVersion = if (is120) {
	commonMod.prop("cloth_config_version_1_20_1")
} else {
	commonMod.prop("cloth_config_version_1_21_1")
}

println("DEBUG: Fabric ${project.path} -> Minecraft Version: ${commonMod.minecraft_version}")
println("DEBUG: Fabric ${project.path} -> Overwriting java_version to: $targetBytecode")

// Force overwrite properties for resource expansion
project.extensions.extraProperties["java_version"] = targetBytecode
project.extensions.extraProperties["cloth_config_version"] = clothConfigVersion

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(21)) // run/tooling on 21
	withSourcesJar()
}

java.sourceCompatibility = JavaVersion.toVersion(targetBytecode)
java.targetCompatibility = JavaVersion.toVersion(targetBytecode)

afterEvaluate {
	tasks.withType<JavaExec>().configureEach {
		val launcher = javaToolchains.launcherFor {
			languageVersion.set(JavaLanguageVersion.of(targetBytecode))
		}
		javaLauncher.set(null as org.gradle.jvm.toolchain.JavaLauncher?)
		setExecutable(launcher.get().executablePath.asFile.absolutePath)
	}
}

tasks.withType<JavaCompile>().configureEach {
	options.release.set(targetBytecode)
	options.encoding = "UTF-8"
}

repositories {
	mavenCentral()
	maven("https://maven.architectury.dev/")
	maven("https://maven.terraformersmc.com/releases/")
	maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
	maven("https://maven.blamejared.com/")
	maven("https://maven.shedaniel.me/")
	maven("https://api.modrinth.com/maven") { name = "Modrinth" }
}

dependencies {
	minecraft("com.mojang:minecraft:${commonMod.minecraft_version}")
	mappings(loom.layered {
		officialMojangMappings()
		commonMod.propOrNull("parchment")?.let { parchmentVersion ->
			if (parchmentVersion != "") parchment("org.parchmentmc.data:parchment-${commonMod.minecraft_version}:$parchmentVersion@zip")
		}
	})

	val energyVersion = if (is120) {
		commonMod.prop("teamreborn_energy_1_20_1")
	} else {
		commonMod.prop("teamreborn_energy_1_21_1")
	}

	modImplementation("teamreborn:energy:$energyVersion")

	modImplementation("net.fabricmc:fabric-loader:${commonMod.prop("fabric_loader_version")}")
	modApi("net.fabricmc.fabric-api:fabric-api:${commonMod.prop("fabric_api_version")}")

	// Required deps
	modImplementation("dev.architectury:architectury-fabric:${commonMod.prop("architectury_version")}")
	modImplementation("software.bernie.geckolib:geckolib-fabric-${commonMod.minecraft_version}:${commonMod.prop("geckolib_version")}")
	modImplementation("me.shedaniel.cloth:cloth-config-fabric:$clothConfigVersion")

	// Mod Menu (REQUIRED if you want the config button always present)
	modImplementation("com.terraformersmc:modmenu:${commonMod.prop("modmenu_version")}")

	// JEI (optional)
	modCompileOnly("mezz.jei:jei-${commonMod.minecraft_version}-common-api:${commonMod.prop("jei_version")}")
	modCompileOnly("mezz.jei:jei-${commonMod.minecraft_version}-fabric-api:${commonMod.prop("jei_version")}")
	modLocalRuntime("mezz.jei:jei-${commonMod.minecraft_version}-fabric:${commonMod.prop("jei_version")}")

	// Jade
	val jadeVersion = if (is120) commonMod.prop("jade_version_1_20_1") else commonMod.prop("jade_version_1_21_1")
	modImplementation("maven.modrinth:jade:$jadeVersion+fabric")
}

loom {
	accessWidenerPath =
		common.project.file("../../src/main/resources/accesswideners/${commonMod.minecraft_version}-${mod.id}.accesswidener")

	runs {
		named("client") {
			client()
			configName = "Fabric Client"
			ideConfigGenerated(true)
		}
		named("server") {
			server()
			configName = "Fabric Server"
			ideConfigGenerated(true)
		}
		create("datagen") {
			server()
			configName = "Fabric Data Generation"
			ideConfigGenerated(true)
			vmArg("-Dfabric-api.datagen")
			vmArg("-Dfabric-api.datagen.output-dir=${file("src/main/generated")}")
			vmArg("-Dfabric-api.datagen.modid=${commonMod.id}")
			runDir("build/datagen")
			// Ensure directory exists
			file("build/datagen").mkdirs()
		}
	}
}

sourceSets {
	main {
		resources {
			srcDir("src/main/generated")
		}
	}
}

tasks.named<ProcessResources>("processResources") {
	inputs.property("targetBytecode", targetBytecode)
	duplicatesStrategy = DuplicatesStrategy.EXCLUDE

	filesMatching("fabric.mod.json") {
		expand(mapOf(
			"java_version" to targetBytecode,
			"cloth_config_version" to clothConfigVersion,
			"architectury_version" to commonMod.prop("architectury_version"),
			"geckolib_version" to commonMod.prop("geckolib_version"),
			"modmenu_version" to commonMod.prop("modmenu_version"),
			"fabric_loader_version" to commonMod.prop("fabric_loader_version"),
			"fabric_api_version" to commonMod.prop("fabric_api_version"),
			"minecraft_version" to commonMod.minecraft_version,
			"mod_id" to commonMod.id,
			"mod_name" to commonMod.name,
			"mod_license" to commonMod.license,
			"mod_version" to commonMod.version,
			"mod_authors" to commonMod.author,
			"mod_description" to commonMod.description
		))

		filter { line ->
			if (targetBytecode == 21) {
				line.replace(">=17", ">=21")
			} else {
				line.replace(">=21", ">=17")
			}
		}
	}
}
plugins {
	`multiloader-loader`
	id("net.neoforged.moddev")
}

val is120 = commonMod.minecraft_version.startsWith("1.20")
val targetBytecode = if (is120) 17 else 21

repositories {
	mavenCentral()
	maven("https://maven.architectury.dev/")
	maven("https://maven.terraformersmc.com/releases/")
	maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
	maven("https://maven.blamejared.com/")
	maven("https://maven.shedaniel.me/")
}

java {
	toolchain.languageVersion.set(JavaLanguageVersion.of(21))
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

neoForge {
	enable {
		version = commonMod.prop("neoforge_version")
	}
}

dependencies {
	// Architectury & GeckoLib
	implementation("dev.architectury:architectury-neoforge:${commonMod.prop("architectury_version")}")
	implementation("me.shedaniel.cloth:cloth-config-neoforge:${commonMod.prop("cloth_config_version_1_21_1")}")
	implementation("software.bernie.geckolib:geckolib-neoforge-${commonMod.minecraft_version}:${commonMod.prop("geckolib_version")}")

	// JEI (NeoForge)
	compileOnly("mezz.jei:jei-${commonMod.minecraft_version}-common-api:${commonMod.prop("jei_version")}")
	compileOnly("mezz.jei:jei-${commonMod.minecraft_version}-neoforge-api:${commonMod.prop("jei_version")}")
	runtimeOnly("mezz.jei:jei-${commonMod.minecraft_version}-neoforge:${commonMod.prop("jei_version")}")
}

neoForge {
	val at = project.file("build/resources/main/META-INF/accesstransformer.cfg");

	accessTransformers.from(at.absolutePath)
	validateAccessTransformers = true

	runs {
		register("client") {
			client()
			ideName = "NeoForge Client (${project.path})"
		}
		if (stonecutter.eval(stonecutter.current.version, ">=1.21.4")) {
			register("clientData") {
				clientData()
				ideName = "NeoForge Client Data (${project.path})"
			}
			register("serverData") {
				serverData()
				ideName = "NeoForge Server Data (${project.path})"
			}
		} else {
			register("data") {
				data()
				ideName = "NeoForge Data (${project.path})"
			}
		}
		register("server") {
			server()
			ideName = "NeoForge Server (${project.path})"
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

		val atFile = project(":common").file("src/main/resources/accesstransformers/accesstransformer-${commonMod.minecraft_version}.cfg")

		from(atFile.parentFile) {
			include(atFile.name)
			rename(atFile.name, "META-INF/accesstransformer.cfg")
			into("")
		}

		inputs.property("targetBytecode", targetBytecode)

		filesMatching(listOf("META-INF/mods.toml", "META-INF/neoforge.mods.toml")) {
			expand(mapOf(
				"architectury_version" to commonMod.prop("architectury_version"),
				"geckolib_version" to commonMod.prop("geckolib_version"),
				"cloth_config_version_1_21_1" to commonMod.prop("cloth_config_version_1_21_1"),
				"minecraft_version" to commonMod.minecraft_version,
				"neoforge_version" to commonMod.prop("neoforge_version"),
				"mod_id" to commonMod.id,
				"mod_name" to commonMod.name,
				"mod_license" to commonMod.license,
				"mod_version" to commonMod.version,
				"mod_authors" to commonMod.author,
				"mod_description" to commonMod.description
			))

			filter { line ->
				if (targetBytecode == 21) {
					line.replace("[17,)", "[21,)")
				} else {
					line
				}
			}
		}
	}
}

tasks.named("createMinecraftArtifacts") {
	dependsOn(":neoforge:${commonMod.propOrNull("minecraft_version")}:processResources")
}
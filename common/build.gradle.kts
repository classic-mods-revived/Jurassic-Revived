plugins {
	id("multiloader-common")
	id("dev.architectury.loom")
	id("architectury-plugin") version "3.5.166"
}

val is120 = project.path.contains("1.20")
val targetBytecode = if (is120) 17 else 21

println("DEBUG: Project ${project.path} -> Compiler: JDK 21 (Fixed), Bytecode Target: $targetBytecode")

java {
	// TRICK: Always use the Java 21 Toolchain to run the build.
	// This stops Gradle from complaining about Architectury 13 requiring Java 21,
	// even when we are currently focused on 1.20.1.
	toolchain.languageVersion.set(JavaLanguageVersion.of(21))
	withSourcesJar()
}

tasks.withType<JavaCompile>().configureEach {
	// TRICK: Even though we are using JDK 21 to compile, we force the output
	// to be compatible with Java 17 when building for 1.20.1.
	options.release.set(targetBytecode)
	options.encoding = "UTF-8"
}

loom {
	accessWidenerPath =
		common.project.file("../../src/main/resources/accesswideners/${commonMod.minecraft_version}-${mod.id}.accesswidener")
}

repositories {
	mavenCentral()
	maven("https://api.modrinth.com/maven") { name = "Modrinth" }
	maven("https://maven.architectury.dev/")
	maven("https://maven.terraformersmc.com/releases/")
	maven("https://dl.cloudsmith.io/public/geckolib3/geckolib/maven/")
	maven("https://maven.blamejared.com/")
}

dependencies {
	minecraft("com.mojang:minecraft:${commonMod.minecraft_version}")
	mappings(loom.layered {
		officialMojangMappings()
		commonMod.propOrNull("parchment_mappings")?.let { parchmentVersion ->
			if (parchmentVersion != "") parchment("org.parchmentmc.data:parchment-${commonMod.minecraft_version}:$parchmentVersion@zip")
		}
	})

	compileOnly("org.spongepowered:mixin:0.8.5")
	"io.github.llamalad7:mixinextras-common:0.5.0".let {
		compileOnly(it)
		annotationProcessor(it)
	}

	// Architectury & GeckoLib
	modImplementation("dev.architectury:architectury:${commonMod.prop("architectury_version")}")
	modImplementation("software.bernie.geckolib:geckolib-fabric-${commonMod.minecraft_version}:${commonMod.prop("geckolib_version")}")

	// JEI (Common API)
	// We use "modCompileOnly" because we only need the API to write code, not the full mod
	modCompileOnly("mezz.jei:jei-${commonMod.minecraft_version}-common-api:${commonMod.prop("jei_version")}")

    // Jade (Common API)
    val jadeVersion = if (is120) commonMod.prop("jade_version_1_20_1") else commonMod.prop("jade_version_1_21_1")
    val jadeClassifier = if (is120) "forge" else "fabric"
	
    // Use modCompileOnly to provide the API for compilation
    modCompileOnly("maven.modrinth:jade:$jadeVersion+$jadeClassifier")
}

val commonJava: Configuration by configurations.creating {
	isCanBeResolved = false
	isCanBeConsumed = true
}

val commonResources: Configuration by configurations.creating {
	isCanBeResolved = false
	isCanBeConsumed = true
}

artifacts {
	afterEvaluate {
		val mainSourceSet = sourceSets.main.get()
		mainSourceSet.java.sourceDirectories.files.forEach {
			add(commonJava.name, it)
		}
		mainSourceSet.resources.sourceDirectories.files.forEach {
			add(commonResources.name, it)
		}
	}
}
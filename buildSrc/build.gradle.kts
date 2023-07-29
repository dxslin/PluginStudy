import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
    // 安卓项目结构的API，与根目录build.gradle文件中dependencies引入的安卓build插件一致。
    implementation("com.android.tools.build:gradle:8.0.2")
}

// 仓库设置，如果想导入第三方的library需要配置
repositories {
    maven("https://maven.aliyun.com/repository/central")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.aliyun.com/repository/gradle-plugin")
    mavenLocal()
    google()
    mavenCentral()
}

kotlin {
    // Add Deps to compilation, so it will become available in main project
    sourceSets.getByName("main").kotlin.srcDir("buildSrc/src/main/kotlin")
}

tasks.withType(KotlinCompile::class.java) {
    kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
}

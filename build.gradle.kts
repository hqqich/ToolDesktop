import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

group = "com.tsinglink.ToolDesktop"
version = "1.0-SNAPSHOT"

repositories {
    google()
    mavenCentral()
    maven("https://jitpack.io")
    maven("https://maven.aliyun.com/repository/public")
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "17"
        }
        withJava()
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                // 参考文档：https://stackoverflow.com/questions/70150052/org-jetbrains-skiko-libraryloadexception-cannot-find-libskiko-macos-arm64-dylib/70150484
                // windows x64
                implementation(compose.desktop.currentOs)
                // macOS arm
//                implementation(compose.desktop.macos_arm64)
                // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
                implementation("com.squareup.okhttp3:okhttp:4.9.3")
                implementation("com.squareup.okhttp3:okhttp-urlconnection:4.9.3")

                // https://mvnrepository.com/artifact/org.xerial/sqlite-jdbc
                implementation("org.xerial:sqlite-jdbc:3.41.2.1")
                // https://mvnrepository.com/artifact/org.apache.commons/commons-dbcp2
                implementation("org.apache.commons:commons-dbcp2:2.9.0")


                // https://mvnrepository.com/artifact/org.springframework/spring-jdbc
                implementation("org.springframework:spring-jdbc:5.3.26")
                // https://mvnrepository.com/artifact/org.springframework/spring-beans
                implementation("org.springframework:spring-beans:5.3.26")
                // https://mvnrepository.com/artifact/org.springframework/spring-core
                implementation("org.springframework:spring-core:5.3.26")
                // https://mvnrepository.com/artifact/org.springframework/spring-jcl
                implementation("org.springframework:spring-jcl:5.3.26")
                // https://mvnrepository.com/artifact/org.springframework/spring-tx
                implementation("org.springframework:spring-tx:5.3.26")

//
//
                implementation("com.github.ltttttttttttt:load-the-image:1.0.5")//this
//
//
//                implementation("uk.co.caprica:vlcj:4.7.1")



                // https://mvnrepository.com/artifact/com.google.code.gson/gson
                implementation("com.google.code.gson:gson:2.10.1")
            }
        }
        val jvmTest by getting
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "ToolDesktop"
            packageVersion = "1.0.0"
        }
    }
}

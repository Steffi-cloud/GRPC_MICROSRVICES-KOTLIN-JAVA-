plugins {
    kotlin("jvm") version "1.7.22"
    application
    id("com.google.protobuf") version "0.9.4"
}

// ---- imports MUST come right after plugins ----
import com.google.protobuf.gradle.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.gradle.api.file.DuplicatesStrategy
// ------------------------------------------------

group = "com.example"
version = "0.0.1"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")

    implementation("io.grpc:grpc-kotlin-stub:1.3.0")
    implementation("io.grpc:grpc-netty-shaded:1.58.0")
    implementation("io.grpc:grpc-protobuf:1.58.0")
    implementation("com.google.protobuf:protobuf-java:3.24.3")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.22.3"
    }
    plugins {
        // define the plugins ONCE here
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.58.0"
        }
        id("grpckt") {
            artifact = "io.grpc:protoc-gen-grpc-kotlin:1.3.0:jdk8@jar"
        }
    }
    generateProtoTasks {
        // apply them to all proto generation tasks
        all().configureEach {
            plugins {
                id("grpc")
                id("grpckt")
            }
        }
    }
}

// default src/main/proto is already used by the plugin, so no need to configure sourceSets

application {
    mainClass.set("com.example.grpcclient.HelloClientKt")
}

tasks.processResources {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}

// Kotlin 1.7.22 supports jvmTarget up to 18; we use 17, run on JDK 21
tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

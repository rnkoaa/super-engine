plugins {
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.kapt")
    id("org.jetbrains.kotlin.plugin.allopen") 
    id("com.github.johnrengelman.shadow") 
    id("io.micronaut.application") 
    idea
}

val kotlinVersion = project.properties["kotlinVersion"]
val micronautVersion = project.properties["micronautVersion"]

version = "0.0.1"
group = "com.olive.books"

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-jackson-databind")
    implementation("io.micronaut:micronaut-runtime")
//    implementation("io.micronaut:management")
    implementation("io.micronaut.kafka:micronaut-kafka")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("ch.qos.logback:logback-classic")
    testImplementation("org.assertj:assertj-core")
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:kafka:1.16.2")
    testImplementation("org.testcontainers:testcontainers")
    implementation("io.micronaut:micronaut-validation")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
//    implementation("io.micronaut:micronaut-jackson-databind")
//    implementation("io.micronaut:micronaut-runtime")
//    implementation("io.micronaut.kafka:micronaut-kafka")
//    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
//    implementation("jakarta.annotation:jakarta.annotation-api")
//    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
//    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
//    runtimeOnly("ch.qos.logback:logback-classic")
//    implementation("io.micronaut:micronaut-validation")
//
//    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
//
//    testImplementation("io.micronaut:micronaut-http-client")
//    testImplementation("org.assertj:assertj-core")
//    testImplementation("org.testcontainers:kafka")
//    testImplementation("org.awaitility:awaitility:4.1.1")
//    testImplementation("org.testcontainers:junit-jupiter")
//    testImplementation("org.testcontainers:testcontainers")
}

application {
    mainClass.set("com.olive.books.BookApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "17"
        }
    }
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.olive.books.*")
    }
}

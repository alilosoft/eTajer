plugins {
    kotlin("jvm") version "1.3.72"
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom")) // Align versions of all Kotlin components
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    // data/jdbc
    implementation("com.vladsch.kotlin-jdbc:kotlin-jdbc:0.5.0-beta-7")
    // logging
    implementation("ch.qos.logback:logback-classic:1.2.3")
    // test
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.6.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.6.2")
    //testImplementation("org.jetbrains.kotlin:kotlin-test")
    //testImplementation("org.jetbrains.kotlin:kotlin-test-junit")
}

application {
    mainClassName = "etajer.cashier.AppKt"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
        //kotlinOptions.freeCompilerArgs = listOf("-Xallow-result-return-type")
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    test {
        useJUnitPlatform()
    }
    jar {
        manifest {
            attributes("Main-Class" to application.mainClassName)
        }
        from(configurations.runtimeClasspath
                .get()
                .map { file -> if (file.isDirectory) file else zipTree(file) }
        )
    }
}

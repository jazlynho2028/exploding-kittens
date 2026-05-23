import com.github.spotbugs.snom.Confidence
import com.github.spotbugs.snom.Effort

plugins {
    java
    application
    id("org.openjfx.javafxplugin") version "0.0.14"
    checkstyle
    id("com.github.spotbugs") version "6.0.25"
    jacoco
    id("info.solidsoft.pitest") version "1.15.0"
}

group = "nu.csse.sqe"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // checkstyle
    implementation("com.puppycrawl.tools:checkstyle:10.18.2")

    // test
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.junit.platform:junit-platform-suite")
    testImplementation("org.pitest:pitest-junit5-plugin:1.2.2")

    // https://mvnrepository.com/artifact/org.easymock/easymock
    testImplementation("org.easymock:easymock:5.4.0")

    // cucumber
    testImplementation(platform("io.cucumber:cucumber-bom:7.20.1"))
    testImplementation("io.cucumber:cucumber-java")
    testImplementation("io.cucumber:cucumber-junit-platform-engine")

    // fasterxml
    implementation("com.fasterxml.jackson.core:jackson-databind:2.17.0")
}

javafx {
    version = "17.0.10"
    modules("javafx.controls", "javafx.fxml")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(11)
    }
}

application {
    mainClass.set("ui.ExplodingKittensApp")
}

tasks.compileJava {
    options.release = 11
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required = false
        html.required = true
        html.stylesheet = resources.text.fromFile("config/xsl/checkstyle-noframes-severity-sorted.xsl")
    }
}

checkstyle{
    toolVersion = "10.18.2"
    isIgnoreFailures = false
}

spotbugs {
    ignoreFailures = false
    showStackTraces = true
    showProgress = true
    effort = Effort.DEFAULT
    reportLevel = Confidence.DEFAULT
    //omitVisitors = listOf("FindNonShortCircuit")
    reportsDir = file("spotbugs")
    //onlyAnalyze = listOf("com.foobar.MyClass", "com.foobar.mypkg.*")
    maxHeapSize = "1g"
    extraArgs = listOf("-nested:false")
    //jvmArgs = listOf("-Duser.language=ja") // set user language to japanese
}

tasks.spotbugsMain {
    reports.create("html") {
        required = true
        outputLocation = layout.buildDirectory.file("reports/spotbugs/spotbugsMain.html")
        setStylesheet("fancy-hist.xsl")
    }
}

tasks.spotbugsTest {
    reports.create("html") {
        required = true
        outputLocation = layout.buildDirectory.file("reports/spotbugs/spotbugsTest.html")
        setStylesheet("fancy-hist.xsl")
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required = false
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("reports/jacoco")
    }
}

tasks.build {
    dependsOn("pitest")
}

tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    finalizedBy(tasks.pitest)
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}

pitest {
    targetClasses.set(listOf("ui.StartController"))
    targetTests.set(listOf("ui.StartControllerTests"))
    junit5PluginVersion = "1.2.2"
    pitestVersion = "1.15.0" //not needed when a default PIT version should be used

    threads = 4
    outputFormats = setOf("HTML")
    timestampedReports = false
    testSourceSets.set(listOf(sourceSets.test.get()))
    mainSourceSets.set(listOf(sourceSets.main.get()))
    jvmArgs.set(listOf("-Xmx1024m"))
    useClasspathFile.set(true) //useful with bigger projects on Windows
    fileExtensionsToFilter.addAll("xml")
    exportLineCoverage = true
}

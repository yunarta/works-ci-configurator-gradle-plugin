package com.mobilesolutionworks.android.gradle.configurator.reporter

import com.mobilesolutionworks.android.gradle.configurator.base.PluginTestSpecification
import org.gradle.internal.impldep.org.apache.commons.io.FileUtils
import org.junit.Before

class PluginPmdTests extends PluginTestSpecification {

    @Before
    static def configure() {
        def loader = PluginTestSpecification.class.classLoader

        def resource = loader.getResource("project")
        FileUtils.copyDirectory(new File(resource.file), testDir.root)
    }

    def "test substitute by spec project"() {
        when:
        buildGradle.append("""
        apply plugin: "works-ci-reporter"
        apply plugin: "jacoco"
        
        subprojects {
            apply plugin: "works-ci-reporter"
            apply plugin: "works-dependency-substitute"
            
            worksSubstitution {
                substitute spec("junit:junit") with version("4.12")
            }
            
            worksReporter  {
                pmdTasks = ["pmdMain"]
            }            
        }
        """)

        execute("pmdMain", "test", "jacocoTestReport", "developerJacocoTestReport", "worksGatherReport")

        then:
        true
    }

    def "test substitute by spec project2"() {
        when:
        buildGradle.append("""
        apply plugin: "works-ci-reporter"
        apply plugin: "jacoco"
        
        subprojects {
            apply plugin: "works-ci-reporter"
            apply plugin: "works-dependency-substitute"
            
            worksSubstitution {
                substitute spec("junit:junit") with version("4.12")
            }
            
            worksReporter  {
                pmdFiles = files("build/reports/pmd")
            }            
        }
        """)

        execute("pmdMain", "test", "jacocoTestReport", "developerJacocoTestReport", "worksGatherReport")

        then:
        true
    }
}

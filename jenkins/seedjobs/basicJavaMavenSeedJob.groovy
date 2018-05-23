def gitUrl = 'https://github.com/toamitkumar/basic-java-maven'

createCiJob("basic-java-maven", gitUrl, "pom.xml")

def createCiJob(def jobName, def gitUrl, def pomFile) {
  job("${jobName}-1-ci") {
    parameters {
      stringParam("BRANCH", "master", "Define TAG or BRANCH to build from")
      // stringParam("REPOSITORY_URL", "http://nexus:8081/repository/maven-releases/", "Nexus Release Repository URL")
    }
    scm {
      git {
        remote {
          url(gitUrl)
        }
        extensions {
          cleanAfterCheckout()
        }
      }
    }
    wrappers {
      colorizeOutput()
      preBuildCleanup()
    }
    triggers {
      scm('30/H * * * *')
      githubPush()
    }
    steps {
      maven {
          goals('clean versions:set -DnewVersion=DEV-\${BUILD_NUMBER}')
          mavenInstallation('Maven 3.3.3')
          rootPOM( pomFile )
          mavenOpts('-Xms512m -Xmx1024m')
          providedGlobalSettings('bc30ebe0-68e1-4fa7-ab30-38092113a63c')
      }
      maven {
        goals('clean install')
        mavenInstallation('Maven 3.3.3')
        rootPOM(pomFile)
        mavenOpts('-Xms512m -Xmx1024m')
        providedGlobalSettings('bc30ebe0-68e1-4fa7-ab30-38092113a63c')
      }
    }
  }
}

buildPipelineView('Pipeline') {
  filterBuildQueue()
  filterExecutors()
  title('Basic Java Maven CI')
  displayedBuilds(5)
  selectedJob("basic-java-maven-ci")
  alwaysAllowManualTrigger()
  refreshFrequency(60)
}

listView('Basic Java Maven') {
    description('')
    filterBuildQueue()
    filterExecutors()
    jobs {
      regex(/basic-java-maven-.*/)
    }
    columns {
        status()
        buildButton()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
    }
}

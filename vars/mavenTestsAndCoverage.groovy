//Run unit tests and coverage via maven
def call(Map config){
    stage("Run JUnit Tests & JaCoCo Coverage"){
        docker.image("maven:${env.MVN_TAG}").inside(){
            // Build and execute unit tests with verify. Use the JaCoCo plugin for generating the code coverage report. For this the jacoco plugin shall be configured on the project.
            echo 'Executing unit tests with coverage report'
                sh "${MVN_CMD} verify"
        }
    }
}

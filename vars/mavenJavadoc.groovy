//Generates a javadoc for the project and handle the error ir failed so the pipeline is not break for this error.
def call(Map config){
    stage("Test Javadoc"){
        docker.image("maven:${env.MVN_TAG}"){
            catchError(buildResult: 'SUCCESS', stageResult: 'UNSTABLE') {
                // Generates a javadoc from the project, shall fail if an error on the javadoc generation is found
                sh "${MVN_CMD} -Dmaven.javadoc.failOnError=true javadoc:javadoc"
            }
        }
    }
}
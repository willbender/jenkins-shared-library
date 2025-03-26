//Create a changelog file with the changes made in the project.
def call(Map config){
    stage("Create Changelog"){
        docker.image("maven:${env.MVN_TAG}")){
            sh "${MVN_CMD} changelog:changelog"
            
        }
    }
}
//Build/Compile an artifact with maven
def call(Map config){
    stage("Build Artefict"){
        docker.image("maven:${env.MVN_TAG}"){
            // See if the project can be compiled.
            echo "Compiling ${env.BRANCH_NAME} Branch"
            sh "${MVN_CMD} -U clean compile"
        }
    }
}
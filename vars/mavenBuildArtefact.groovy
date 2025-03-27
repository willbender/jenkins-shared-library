//Build/Compile an artifact with maven
def call(Map config){
    stage("Build Artifact"){
        docker.image("maven:${env.MVN_TAG}").inside(){
            // See if the project can be compiled.
            echo "Compiling ${env.BRANCH_NAME} Branch"
            sh "${MVN_CMD} -U clean compile"
        }
    }
}
//Generates a Docker image from the artifact and upload it to the configured docker registry.
def call(Map config){
    stage("Create Docker Image"){
        docker.image("maven:${env.MVN_TAG}").inside(){
            DOCKER_IMAGE_NAME = env.IMAGE_NAME
            DOCKER_IMAGE_REGISTRY = env.DOCKER_REGISTRY

            // Generating and pushing the docker image via JIB. For this is important to have the configuration in pom.xml
            // For main branch, the version inside the pom.xml shall be changed to show the just released version, in other case the version is going to be the one prepared for next iteration.
            if (BRANCH_NAME == 'main') {
                sh "${MVN_CMD} versions:set -DnewVersion=${env.IMAGE_VERSION}"
            }
            withCredentials(
                [usernamePassword(credentialsId: 'docker-registry', passwordVariable: 'DOCKER_PASSWORD', usernameVariable: 'DOCKER_USERNAME')]
                ) {
                env.DOCKER_IMAGE = "${DOCKER_IMAGE_REGISTRY}/${DOCKER_IMAGE_NAME}:${env.IMAGE_VERSION}"
                sh "${MVN_CMD} clean package jib:build -Dmaven.test.skip=true -Dimage=${env.DOCKER_IMAGE} -Djib.to.tags=${BRANCH_NAME} -Djib.to.auth.username=\"\$DOCKER_USERNAME\" -Djib.to.auth.password=\"\$DOCKER_PASSWORD\""
            }
            // The changes on pom.xml are undo as it is just needed to generate the docker image
            sh 'git restore pom.xml'
        }
    }
}
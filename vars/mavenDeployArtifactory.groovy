//Deploy the project artefact to the artifactory server.
//The artifactory configuration is inside the project pom.
def call(Map config){
    stage("Deploy Artifact"){
        docker.image("maven:${env.MVN_TAG}").inside(){
            GITHUB_TOKEN = credentials('github-token')
             sh '''
                git config --global credential.helper '!f() { echo "username=willbender"; echo "password=$GITHUB_TOKEN"; }; f'
                '''
            if (BRANCH_NAME == 'main') {
                // If the branch is main, then the artifact is deployed as released
                echo 'Generating a tag'
                echo 'Deploy to Artifactory'
                sh "${MVN_CMD} -DpreparationGoals=clean release:prepare"
                sh "${MVN_CMD} -Darguments=-Dmaven.test.skip=true -Dmaven.javadoc.skip=true release:perform"
            } else {
                // In other case, a snapshot is deployed.
                echo 'SNAPSHOT & deploy to Artifactory'
                sh "${MVN_CMD} versions:set -DnewVersion=${env.POM_VERSION.substring(0, env.POM_VERSION.indexOf('-'))}-${BRANCH_NAME}-${env.BUILD_NUMBER}-SNAPSHOT"
                sh "${MVN_CMD} -B -DskipTests deploy"
            }
        }
    }
}
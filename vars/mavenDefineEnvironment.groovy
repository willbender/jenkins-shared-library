def call(Map config){
    //Environment variables definition
    //Name of the branch where the pipeline is running
    env.BRANCH_NAME = getGitBranchName()
    //Maven command to use
    env.MVN_CMD = 'mvn -ntp '
    //Docker image for maven to use for maven steps.
	env.MVN_TAG = "${config.mvnVersion}"
    
    //Docker registry for images.
    env.DOCKER_REGISTRY = "${config.dockerRegistry}"
    //Some information is read from pom file.
    pom = readMavenPom file: 'pom.xml'
    env.GROUP_ID = pom.groupId
    env.ARTIFACT_ID = pom.artifactId
    env.POM_VERSION = pom.version
    env.IMAGE_NAME = getPomImageName()
    env.IMAGE_VERSION = getPomImageVersion(env.BRANCH_NAME)
    //Changes the display of the build in Jenkins
    currentBuild.displayName = env.IMAGE_VERSION
    currentBuild.description = env.IMAGE_NAME + ' - ' + env.IMAGE_VERSION
    env.GIT_EMAIL = 'william.bernal@gmail.com'
	env.GIT_USERNAME = 'willbender'
    echo 'Configuring git variables'
	sh "git config user.email \"${env.GIT_EMAIL}\""
	sh "git config user.name \"${env.GIT_USERNAME}\""
}

def call(Map config){
    //Environment variables definition
    //Name of the branch where the pipeline is running
    def env.BRANCH_NAME = getGitBranchName()
    //Maven command to use
    def env.MVN_CMD = 'mvn -ntp '
    //Docker image for maven to use for maven steps.
	def env.MVN_TAG = "${config.mvnVersion}"
    
    //Docker registry for images.
    def env.DOCKER_REGISTRY = "${config.dockerRegistry}"
    //Some information is read from pom file.
    def pom = readMavenPom file: 'pom.xml'
    def env.GROUP_ID = pom.groupId
    def env.ARTIFACT_ID = pom.artifactId
    def env.POM_VERSION = pom.version
    def env.IMAGE_NAME = getPomImageName()
    def env.IMAGE_VERSION = getPomImageVersion(env.BRANCH_NAME)
    //Changes the display of the build in Jenkins
    currentBuild.displayName = env.IMAGE_VERSION
    currentBuild.description = env.IMAGE_NAME + ' - ' + env.IMAGE_VERSION
}

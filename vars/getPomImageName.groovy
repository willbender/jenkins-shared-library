// This is needed in order to obtain the image name from the pom file in maven projects.
def call() {
	return env.GROUP_ID.replace('.', '/') + '/' + env.ARTIFACT_ID.toLowerCase()
}
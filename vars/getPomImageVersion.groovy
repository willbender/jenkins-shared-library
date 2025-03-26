// Return the version of the artifact that is going to build.
def call(branchName) {
	BUILD_VERSION = env.POM_VERSION
	if (BUILD_VERSION.indexOf('-') >= 0) {
		BUILD_VERSION = env.POM_VERSION.substring(0, env.POM_VERSION.indexOf('-'))
	}
	if (branchName != 'main') {
		BUILD_VERSION = BUILD_VERSION + '-' + branchName + '-' + BUILD_NUMBER + '-SNAPSHOT'
	}
	return BUILD_VERSION
}
// This is needed in order to obtain the branch on which the pipeline is running.
def call() {
	return scm.branches[0].name
}
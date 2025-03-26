//Define the parameters for the pipeline
def call(Map config){
    //Name of the branch where the pipeline is running
    env.BRANCH_NAME = getGitBranchName()
    def list = []
    echo "Defining parameters for the pipeline."
    if (config.dockerImage) {
        //Parameter for the generation of docker image
        list.add(booleanParam(defaultValue: true, name: 'dockerImage', description: 'Generate Docker Image?'))
    }
    //Parameters needed just for main branch.
    if (env.BRANCH_NAME == 'main') {
        list.add(booleanParam(description: 'Generate the changelog.', name: 'generateChangelog', defaultValue: false))
    }
    //If there are parameters to define.
    if(list){
        properties([parameters(list)])
    }
}
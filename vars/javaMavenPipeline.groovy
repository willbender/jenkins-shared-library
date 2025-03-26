def call(Map config){
    //Define parameters for the pipeline.
    globalParameters(config)
    node {
        //Checkout shall be defined or some errors can be triggered in the pipeline.
        checkout scm
        //Define environment variables
        mavenDefineEnvironment(config)
        //Build artefact via maven
        mavenBuildArtefact(config)
        //Execute unit tests and coverage
        mavenTestsAndCoverage(config)
        //Generate javadoc
        mavenJavadoc(config)
        //Deploy artefact to artifactory
        mavenDeployArtifactory(config)
        //Generate docker image
        if(params.dockerImage){
            mavenDockerImage(config)
        }
        // Generate changelog
        if(params.generateChangelog){
            mavenChangelog(config)
        }
    }
}
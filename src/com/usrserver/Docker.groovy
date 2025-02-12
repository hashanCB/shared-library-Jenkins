package com.usrserver

class Docker {
    def script

    def Docker (script){
        this.script=script
    }

    def DockerBuildimage (String Buildname) {
        script.echo "build image"
        script.sh "docker build -t '${Buildname}'  ."
        script.sh 'docker images'
    }

    def DockerLogin(){
        script.withCredentials([script.usernamePassword(credentialsId:'docker-login-credintials',
                                  passwordVariable: 'PASS',
                                   usernameVariable:'USER')]) {
                    script.sh "echo '${script.PASS}' | docker login -u '${script.USER}' --password-stdin"}


    }

    def DockerPush (String imageName){
        script.sh "docker push '${imageName}'"
        script.echo "pushed images to docker"
    }

    def IncrementVersion () {
        script.echo "increment Version....."
        script.sh'npm run patch --no-git-tag-version'

        def packageJson = readJSON file: 'package.json'
        def version = packageJson.version

        env.IMAGE_NAME = "${version}-${env.BUILD_NUMBER}"
    }
}

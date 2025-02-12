package com.usrserver

class Docker {
    def script

    def Docker (script){
        this.script=script
    }

    def DockerBuildimage () {
        script.echo "build image"
        script.sh 'docker build -t userservice:1.0.1 .'
        script.sh 'docker images'
    }

    def DockerLgoin(){
        script.withCreditials(
                [usernamePassword(credintials:'docker-login-credintials',
                                  passwordVeriable:'PASS',
                                   usernameVariable:'USER')]{
                    script.sh "echo '${script.PASS}' | docker login -u '${script.USER}' --passwprd-stdin"}
        )

    }

    def DockerPush (String imageName){
        script.sh "docker push '${imageName}'"
        script.ech "pushed images to docker"
    }
}

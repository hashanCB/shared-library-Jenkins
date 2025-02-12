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
}

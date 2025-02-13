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
        script.sh'npm version patch --no-git-tag-version'

        def packageJson = script.readJSON file: 'package.json'
        def version = packageJson.version

        script.env.IMAGE_NAME = "${version}-${script.env.BUILD_NUMBER}"
    }

    def GitversionCommi(){
        script.withCredentials([script.usernamePassword(credentialsId:'github-credentials',
                passwordVariable: 'PASS',
                usernameVariable:'USER')]) {
            script.sh 'git config --global user.email "jenkins@gmail.com"'
            script.sh 'git config --global user.name "jenkins"'
            script.sh 'git remote set-url origin https://${USER}:${PASS}@github.com:hashanCB/userService.git'
            script.sh 'git checkout main'
            script.sh 'git add .'
            script.sh 'git commit -m " from  jenkins : update version"'
            script.sh 'git push origin HEAD:main'

        }

    }
}

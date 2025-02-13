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

        def packageJson = script.readJSON file: 'package.json' //Pipeline Utility Steps pluging
        def version = packageJson.version

        script.env.IMAGE_NAME = "${version}-${script.env.BUILD_NUMBER}"
    }

    def GitversionCommi(){
        script.withCredentials([script.string(credentialsId:'github-key',
                variable: 'GITHUB_TOKEN') ]) {
            script.sh 'git config --global user.email "jenkins@gmail.com"'
            script.sh 'git config --global user.name "jenkins"'
            script.sh 'git remote set-url origin https://${GITHUB_TOKEN}@github.com/hashanCB/userService.git'
            script.sh 'git add .'
            script.sh 'git commit -m " from  jenkins : update version"'
            script.sh 'git push origin HEAD:main'

        }

    }


    def Awsdeplay(String ImageName){
        script.sshagent(['aws-ec2-userser']) {
            script.sh 'scp docker-composer.yml ec2-user@13.60.196.175:/home/ec2-user '

        }
    }
}

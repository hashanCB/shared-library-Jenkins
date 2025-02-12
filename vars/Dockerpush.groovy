import com.usrserver.Docker

def call(String imageName){
    return  new Docker(this).DockerPush(imageName)
}

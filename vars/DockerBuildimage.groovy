import com.usrserver.Docker

def call(String imagrname){
    return  new Docker(this).DockerBuildimage(imagrname)
}

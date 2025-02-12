import com.usrserver.Docker

def call(){
    return new Docker(this).IncrementVersion()
}

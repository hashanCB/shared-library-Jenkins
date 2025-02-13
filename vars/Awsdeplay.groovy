import com.usrserver.Docker

def call(String ImageName) {
    return new Docker(this).Awsdeplay(ImageName)
}

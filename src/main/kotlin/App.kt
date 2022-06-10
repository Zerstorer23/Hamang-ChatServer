
import Logins.CredentialIO.readCredential
import Logins.LoginInfo
import kotlin.system.exitProcess

class App {
    fun start() {
       val loginInfo = readCredential("setting.txt")
        startServer(loginInfo)
    }

    private fun startServer(loginInfo: LoginInfo) {
        try {
            val chatDriver = ChatDriver(loginInfo)
            val tcpServer = TCPServer(chatDriver)
            tcpServer.open()
            chatDriver.start()
            tcpServer.thread.join()
        } catch (e: Exception) {
            e.printStackTrace();
            exitProcess(1)
        }
    }
}

fun main() {
    App().start()
}

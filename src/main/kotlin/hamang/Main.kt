package hamang
import hamang.Logins.CredentialIO.readCredential
import hamang.Logins.LoginInfo
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    start()
}
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

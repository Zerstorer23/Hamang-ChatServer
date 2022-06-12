package hamang

import WebSocket.MyServer
import hamang.Logins.CredentialIO.readCredential
import hamang.Logins.LoginInfo
import kotlin.system.exitProcess

fun main(args: Array<String>) {
    start()
}

fun start() {
    val loginInfo = readCredential("setting.txt")
    ChatDriver.initialise(loginInfo)
    if (loginInfo.isWeb) {
        startWebServer(loginInfo)
    } else {
        startServer()
    }
}

private fun startServer() {
    try {
        val tcpServer = TCPServer();
        tcpServer.open()
        ChatDriver.start()
        tcpServer.thread.join()
    } catch (e: Exception) {
        e.printStackTrace();
        exitProcess(1)
    }
}

private fun startWebServer(loginInfo: LoginInfo) {
    try {
        ChatDriver.initialise(loginInfo)
        MyServer.startWebServer();
        ChatDriver.start()
    } catch (e: Exception) {
        e.printStackTrace();
        exitProcess(1)
    }
}
/*
private fun tcpServer(){
    try {
        val tcpServer = TCPServer(chatDriver)
        tcpServer.open()
        tcpServer.thread.join()
    } catch (e: Exception) {
        e.printStackTrace();
        exitProcess(1)
    }
}*/

package hamang
import java.io.BufferedInputStream
import java.io.DataInputStream
import java.net.ServerSocket
import java.net.Socket
import kotlin.concurrent.thread

class TCPServer() {
    lateinit var thread: Thread
    private val port = 9917
    fun open() {
        ServerSocket()
        val server = ServerSocket(port)
        println("Server is running on port ${server.localPort}")
        thread = thread {
            while (true) {
                val client = server.accept()
                println("Client connected: ${client.inetAddress.hostAddress}")
                // Run client in it's own thread.
                thread { ClientHandler(client).run() }
            }
        }
    }
}

class ClientHandler(private val client: Socket) {
    private var inStream = DataInputStream(BufferedInputStream(client.getInputStream()))
    private var running: Boolean = false
    fun run() {
        running = true
        while (running) {
            try {
                val line = getLine()
                val tokens = line.split("#")
                if (tokens.size != 2) continue
                println("Found " + tokens[1])
                ChatDriver.enqueueMessage(tokens[1])
            } catch (ex: Exception) {
                ex.printStackTrace()
                shutdown()
            } finally {
            }
        }
    }

    private fun getLine(): String {
        var line = byteArrayOf()
        while (inStream.available() != 0) {
            val text = inStream.readByte()
            line += text
            if (inStream.available() == 0) {
                return String(line, Charsets.UTF_8)
            }
        }
        return ""
    }

    private fun shutdown() {
        running = false
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection")
    }
}

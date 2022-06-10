package hamang.Logins

import java.io.File
import java.io.FileNotFoundException

object CredentialIO {
    public fun readCredential(filename: String) : LoginInfo {
        val info = LoginInfo(false, "", "",15)
        try {
            File(filename).bufferedReader().use { br ->
                br.forEachLine {
                    val split = it.split('=')
                    if (split.size > 1) {
                        when (split[0]) {
                            "유동" -> info.isAnonymous = split[1].toIntOrNull() != 0
                            "id" -> info.id = split[1]
                            "pw" -> info.password = split[1]
                            "delay" -> info.delay = split[1].toInt()
                        }
                    }
                }
            }
        } catch (e: FileNotFoundException) {
            println("{$filename} 파일이 없습니다. 직접 정보를 입력하세요.")
            print("유동 닉네임 로그인 = 1, 반/고정 닉네임 로그인 = 0 입력 > ")
            info.isAnonymous = readln().toIntOrNull() != 0
            print("아이디 입력 > ")
            info.id = readln()
            print("비밀번호 입력 > ")
            info.password = readln()
            val text = if (info.isAnonymous) "유동" else "반/고정"
            println("$text: ${info.id}(으)로 접속")
        }
        return info;
    }
}
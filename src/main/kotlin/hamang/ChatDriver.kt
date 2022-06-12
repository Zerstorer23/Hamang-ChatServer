package hamang

import hamang.Logins.LoginInfo
import be.zvz.kotlininside.KotlinInside
import be.zvz.kotlininside.api.article.ArticleList
import be.zvz.kotlininside.api.article.ArticleWrite
import be.zvz.kotlininside.api.async.article.AsyncArticleWrite
import be.zvz.kotlininside.api.comment.CommentWrite
import be.zvz.kotlininside.api.type.Article
import be.zvz.kotlininside.api.type.comment.StringComment
import be.zvz.kotlininside.api.type.content.MarkdownContent
import be.zvz.kotlininside.http.DefaultHttpClient
import be.zvz.kotlininside.session.user.Anonymous
import be.zvz.kotlininside.session.user.LoginUser
import kotlinx.coroutines.*
import java.util.*

public object ChatDriver {
    private lateinit var info: LoginInfo

    const val GALLERY_NAME = "haruhiism"
    private val chatQueue: Queue<String> = LinkedList()

    @JvmStatic
    fun initialise(login: LoginInfo) {
        info = login;
        KotlinInside.createInstance(
            if (info.isAnonymous) {
                Anonymous(info.id, info.password)
            } else {
                LoginUser(info.id, info.password)
            },
            DefaultHttpClient(),
            true
        )
        println("hamang.ChatDriver initialized: 1초 대기")
        Thread.sleep(1000)
    }

    @JvmStatic
    fun enqueueMessage(msg: String) {
        chatQueue.add(msg)
    }

    private suspend fun writePost(title: String, content: String): Boolean {
        println("글 작성: $title")
        val writeResult = AsyncArticleWrite(
            GALLERY_NAME,
            Article(
                title,
                listOf(
                    MarkdownContent(content)
                )
            ),
            KotlinInside.getInstance().session
        ).writeAsync().await()
        if (writeResult.result) {
            println("글 작성 성공: $title")
        } else {
            println("글 작성 실패: ${writeResult.cause}")
        }
        return writeResult.result;
    }

    private fun getFirstArticle(): ArticleList.GallList? {
        val articleList = ArticleList(GALLERY_NAME, 0)
        articleList.request()
        val gallList = articleList.getGallList() // 글 목록
        var targetArticle: ArticleList.GallList? = null;
        for (article in gallList) {
            if (article.subject[0] == '!') {
                targetArticle = article;
                break;
            }
        }
        return targetArticle;
    }

    private fun writeReply(content: String): Boolean {
        val target = getFirstArticle() ?: return false
        val commend = StringComment(content)
        println("글 찾음: ${target.identifier}")
        val writer = CommentWrite(
            GALLERY_NAME,
            target.identifier,
            commend,
            KotlinInside.getInstance().session,
            KotlinInside.getInstance().auth.fcmToken
        )
        val result = writer.write().result
        if (result) println("댓글로 대신작성")
        return result
    }

    private suspend fun doSleep() {
        val postDelay = (info.delay + (5 * Math.random())) * 1000L
        println("도배 방지를 위해 ${postDelay / 1000}초 대기...")
        delay(postDelay.toLong())
    }

    @JvmStatic
    fun start() {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                delay(2500)
                if (chatQueue.size > 0) {
                    val (title, content) = getTitleAndContent()
                    async(Dispatchers.IO) {
                        val res = writePost(title, content)
                        if (!res) writeReply(title)
                    }.start()
                    doSleep()
                }
            }
        }
    }



    private fun getTitleAndContent(): Pair<String, String> {
        val firstMessage = "!${chatQueue.poll()}"
        val content = StringBuilder(" - $firstMessage")
        val title = StringBuilder(firstMessage).apply {
            while (chatQueue.size > 0) {
                if (length < 10) {
                    val nextMessage = chatQueue.poll()
                    if (length < 30) {
                        append(' ').append(nextMessage)
                    }
                    content.append("\n - ").append(nextMessage)
                }
            }
        }.toString()
        return Pair(title, content.toString())
    }

}

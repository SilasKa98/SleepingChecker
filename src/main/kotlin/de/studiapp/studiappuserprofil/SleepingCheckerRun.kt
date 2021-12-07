package de.studiapp.studiappuserprofil


import org.springframework.stereotype.Component
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*
import javax.annotation.PostConstruct

@Component
class SleepingCheckerRun(val database: UrlRepository ) {

    @PostConstruct
    fun init() {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
                val urls = database.findAll()
                val urlList = arrayListOf<String>()
                for(i in urls){
                    urlList.add(i.url)
                }
                for(i in urlList) {
                    val client = HttpClient.newBuilder().build()
                    val request = HttpRequest.newBuilder()
                            .uri(URI.create(i))
                            .build()
                    val response = client.send(request, HttpResponse.BodyHandlers.ofString())
                    if (response.statusCode() == 200) {
                        println("Service " + response.uri() + " pinged successfully - " + response.statusCode())
                    } else {
                        println("Service " + response.uri() + " ping failed with error status - " + response.statusCode())
                    }
                }
            }
        }, 0, 900000) //call the server every
    }

}
package de.studiapp.studiappuserprofil

import com.google.gson.Gson
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import java.net.URL
import com.google.gson.JsonObject
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.*


//Rest Endpoint

@RestController
@RequestMapping("/sleepingChecker")
class SleepingCheckerResource() {

    var firebaseDbURL = "https://sleepingchecker-default-rtdb.firebaseio.com/"

    @GetMapping
    fun getAllUrls(): ArrayList<SleepingCheckerModel> {
        val url = URL("$firebaseDbURL.json")
        val json = jSONObject(url.readText())
        val data = ArrayList<SleepingCheckerModel>()
        for (u in json.keySet()) {
            val urls = Gson().fromJson<SleepingCheckerModel>(json.get(u).toString(), SleepingCheckerModel::class.java)
            data.add(urls)
        }
        if (data.size == 0) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND)
        } else {
            return data
        }
    }

    @PostMapping
    fun postUrl(@RequestBody sleepingCheckerData: SleepingCheckerModel): ResponseEntity<SleepingCheckerModel> {
        val client = HttpClient.newBuilder().build()
        val id = UUID.randomUUID()
        val jsonString = Gson().toJson(SleepingCheckerModel("$id","${sleepingCheckerData.url}"))

        val request = HttpRequest.newBuilder().uri(URI.create("$firebaseDbURL$id.json")).PUT(HttpRequest.BodyPublishers.ofString(jsonString)).build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() == 200){
            return ResponseEntity.ok(sleepingCheckerData)
        }else{
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,"Profile was not created"
            )
        }
    }

    @DeleteMapping("/{id}")
    fun delUserProfileData(@PathVariable id: String): ResponseEntity<String> {
        val client = HttpClient.newBuilder().build()
        val request = HttpRequest.newBuilder().uri(URI.create("$firebaseDbURL$id.json")).DELETE().build()
        val response = client.send(request, HttpResponse.BodyHandlers.ofString())

        if (response.statusCode() == 200){
            return ResponseEntity.ok(id)
        }else{
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST,"Profile corresponding to id " +id + "was not deleted!"
            )
        }
    }


    fun jSONObject(data: String) : JsonObject {
        return Gson().fromJson(data, JsonObject::class.java)
    }
}



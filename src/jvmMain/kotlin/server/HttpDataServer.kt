package server

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.Request
import util.Api
import util.HttpClient
import java.io.IOException

class HttpDataServer {

    fun getHttpData(): String {
        return getText("http://www.baidu.com")
    }

    fun getWAZDayData(): JsonArray {

        val request = Request.Builder()
            .url(Api.dataApi)
            .get()
            .build()

        HttpClient.client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            for ((name, value) in response.headers) {
                println("$name: $value")
            }

            val json = response.body!!.string()

            val asJsonObject: JsonObject = JsonParser.parseString(json).asJsonObject


            var data: JsonObject = asJsonObject.getAsJsonObject("data")

            val datas = data.getAsJsonArray("datas")

            return datas
        }


    }


    fun getVideoData(): JsonArray {

        val request = Request.Builder()
            .url(Api.videoApi)
            .get()
            .build()

        HttpClient.client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            for ((name, value) in response.headers) {
                println("$name: $value")
            }

            val json = response.body!!.string()

            val asJsonObject: JsonObject = JsonParser.parseString(json).asJsonObject


            var data: JsonObject = asJsonObject.getAsJsonObject("result")

            val datas = data.getAsJsonArray("list")

            return datas
        }


    }




    /**
     * 请求数据，返回数据
     */
    fun getText(url: String): String {

        val request = Request.Builder()
            .url(url)
            .get()
            .build()

        HttpClient.client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            for ((name, value) in response.headers) {
                println("$name: $value")
            }

            val string = response.body!!.string()
            println(string)

            return string
        }
    }


}
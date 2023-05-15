import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.OkHttpClient
import okhttp3.Request
import util.Api
import util.HttpClient
import java.io.IOException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*


fun main() {
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
//
//        return datas
    }



}



package util

import okhttp3.OkHttpClient
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

// 对象类，就像java里的单例
object HttpClient {

    //Okhttp请求客户端
    val client: OkHttpClient

    init {

        client = getTrustAllClient()!!

    }

    private var mMyTrustManager: MyTrustManager? = null

    private fun createSSLSocketFactory(): SSLSocketFactory? {
        var ssfFactory: SSLSocketFactory? = null
        try {
            mMyTrustManager = MyTrustManager()
            val sc = SSLContext.getInstance("TLS")
            sc.init(null, arrayOf<TrustManager>(mMyTrustManager!!), SecureRandom())
            ssfFactory = sc.socketFactory
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
        return ssfFactory
    }

    //实现X509TrustManager接口
    class MyTrustManager : X509TrustManager {
        @Throws(CertificateException::class)
        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        @Throws(CertificateException::class)
        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate?> {
            return arrayOfNulls(0)
        }
    }

    //实现HostnameVerifier接口
    private class TrustAllHostnameVerifier : HostnameVerifier {
        override fun verify(hostname: String, session: SSLSession): Boolean {
            return true
        }
    }

    // Okhttp 访问自签名证书 HTTPS 地址解决方案 (https://www.jianshu.com/p/cc7ae2f96b64?utm_campaign=maleskine&utm_content=note&utm_medium=seo_notes&utm_source=recommendation)
    fun getTrustAllClient(): OkHttpClient? {
        val mBuilder = OkHttpClient.Builder()
        mBuilder.sslSocketFactory(createSSLSocketFactory()!!, mMyTrustManager!!)
            .hostnameVerifier(TrustAllHostnameVerifier())
        return mBuilder.build()
    }

}
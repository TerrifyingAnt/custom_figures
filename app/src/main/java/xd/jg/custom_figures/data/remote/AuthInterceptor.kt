package xd.jg.custom_figures.data.remote

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response
import xd.jg.custom_figures.data.local.TokenManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val tokenManager: TokenManager
) : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.getAccessToken().first()
        }

        val originalRequest = chain.request()
        val isExcludedUrl = isUrlExcluded(originalRequest.url)

        if (!isExcludedUrl) {
            val authorizedRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()

            return chain.proceed(authorizedRequest)
        }

        return chain.proceed(originalRequest)
    }

    private fun isUrlExcluded(url: HttpUrl): Boolean {
        // Add your logic to check if the URL should be excluded
        // For example, you can check if the URL starts with a specific path
        val excludedPaths = listOf("/ch-user-data-storage", "/ch-main-storage", "/login", "/register")
        return excludedPaths.any { url.encodedPath.startsWith(it) }
    }
}
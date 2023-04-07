package dev.muyiwa.common.data.di

import com.squareup.moshi.*
import dagger.*
import dagger.hilt.*
import dagger.hilt.components.*
import dev.muyiwa.common.*
import dev.muyiwa.common.data.api.*
import dev.muyiwa.common.data.api.interceptors.*
import dev.muyiwa.common.data.api.utils.*
import okhttp3.*
import okhttp3.logging.*
import retrofit2.*
import retrofit2.converter.moshi.*
import javax.inject.*

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
	@Provides
	@Singleton
	fun provideNoxxyApi(builder: Retrofit.Builder): NoxxyApi {
		return builder
			.build()
			.create(NoxxyApi::class.java)
	}

	@Provides
	fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
		return Retrofit.Builder()
			.baseUrl(BASE_ENDPOINT)
			.client(okHttpClient)
			.addConverterFactory(MoshiConverterFactory.create())
	}

	@Provides
	fun provideOkHttpClient(
		requestInterceptor: Interceptor,
		httpLoggingInterceptor: HttpLoggingInterceptor,
		networkStatusInterceptor: NetworkStatusInterceptor,
	): OkHttpClient {
		return OkHttpClient.Builder()
			.addInterceptor(requestInterceptor)
			.addInterceptor(networkStatusInterceptor)
			.addInterceptor(httpLoggingInterceptor)
			.build()
	}

	@Provides
	fun provideHttpLoggingInterceptor(loggingInterceptor: LoggingInterceptor): HttpLoggingInterceptor {
		val interceptor = HttpLoggingInterceptor(loggingInterceptor)
		interceptor.level = HttpLoggingInterceptor.Level.BODY
		return interceptor
	}

	@Provides
	fun provideRequestInterceptor(): Interceptor {
		return Interceptor { chain ->
			val url = chain.request().url.newBuilder()
				.addQueryParameter(KEY_NAME, BuildConfig.TMDB_API_KEY)
				.build()
			val request = chain.request().newBuilder().url(url).build()
			return@Interceptor chain.proceed(request)
		}
	}

}
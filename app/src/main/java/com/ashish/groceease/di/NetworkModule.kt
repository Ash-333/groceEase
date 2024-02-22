package com.ashish.groceease.di

import com.ashish.groceease.api.AddressApi
import com.ashish.groceease.api.AuthApi
import com.ashish.groceease.api.AuthInterceptor
import com.ashish.groceease.api.CategoryApi
import com.ashish.groceease.api.OrderApi
import com.ashish.groceease.api.ProductApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Builder{
        return Builder()
            .baseUrl("https://groceriesapi.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authInterceptor: AuthInterceptor):OkHttpClient{
        return OkHttpClient.Builder().addInterceptor(authInterceptor).build()
    }

    @Singleton
    @Provides
    fun provideAuthApi(retrofitBuilder: Builder):AuthApi{
        return retrofitBuilder.build().create(AuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideProductApi(retrofitBuilder: Builder,okHttpClient: OkHttpClient):ProductApi{
        return retrofitBuilder
            .client(okHttpClient)
            .build().create(ProductApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoryApi(retrofitBuilder: Builder,okHttpClient: OkHttpClient):CategoryApi{
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(CategoryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideOrderApi(retrofitBuilder: Builder,okHttpClient: OkHttpClient):OrderApi{
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(OrderApi::class.java)
    }

    @Singleton
    @Provides
    fun provideAddressApi(retrofitBuilder: Builder,okHttpClient: OkHttpClient):AddressApi{
        return retrofitBuilder
            .client(okHttpClient)
            .build()
            .create(AddressApi::class.java)
    }
}
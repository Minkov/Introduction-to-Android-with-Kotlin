package com.minkov.app

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import okhttp3.OkHttpClient
import okhttp3.Request

/**
 * Created by doncho on 11/25/17.
 */

class BooksRepository {
    private fun makeRequest(url: String): String? {
        val client = OkHttpClient()
        val request = Request.Builder()
                .get()
                .url(url)
                .build()

        val response = client.newCall(request)
                .execute()

        return response.body()?.string()
    }

    private fun parseBooks(body: String): List<Lang> {
        val gson = Gson()
        return gson.fromJson(body, object : TypeToken<List<Lang>>() {}.type)
    }

    fun listAll(): Flowable<List<Lang>> {
        return Flowable.create<List<Lang>>({ e ->
            val booksJson = makeRequest(Constants.LANGS_URL) ?: ""

            val books = parseBooks(booksJson);
            if (books != null) {
                e.onNext(books)
                e.onComplete()
            } else {
                e.onError(Throwable("Invalid book"))
            }
        }, BackpressureStrategy.BUFFER);
    }
}
package com.minkov.app

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ListActivity : AppCompatActivity() {
    private lateinit var listView: ListView

    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)


        showLoading(R.id.loader, R.id.content)
        adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)

        listView = findViewById<ListView>(R.id.lv_names);
        listView.adapter = adapter

        val btn = findViewById<Button>(R.id.btn_show)

        btn.setOnClickListener({
            when (listView.visibility) {
                View.INVISIBLE -> {
                    listView.visibility = View.VISIBLE
                    btn.text = "Hide"
                }
                View.VISIBLE -> {
                    listView.visibility = View.INVISIBLE
                    btn.text = "Show"
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        load()
    }

    fun load() {
        val repo = BooksRepository();
        repo.listAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { books ->
                    // adapter.clear()
                    // adapter.addAll(books.map { it.name })
                    books.filter { it.platform == "Android" }
                            .map { it.name }
                            .forEach(adapter::add)
                    
                    hideLoading(R.id.loader, R.id.content)
                }
    }
}

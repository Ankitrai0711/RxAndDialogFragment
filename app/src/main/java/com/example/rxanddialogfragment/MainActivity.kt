package com.example.rxanddialogfragment

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        rx()
        rxClick()
    }

    private fun rxClick() {
        val button = findViewById<Button>(R.id.button)
        disposable.add(RxView.clicks(button)
            .throttleFirst(1500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val dialogFragment = MyDialogFragment()
                dialogFragment.show(supportFragmentManager, "MY_DIALOG_TAG")
                Log.d("Tag", "Clicked in rxClick")
            })
    }

    private fun rx() {
        val list = listOf("A", "B", "C")
        val observable = Observable.fromIterable(list)
        observable.subscribe(object : Observer<String> {
            override fun onSubscribe(d: Disposable) {
                Log.d("Tag", "onSubscribe is called")
            }

            override fun onError(e: Throwable) {
                Log.d("Tag", "onError is called $e")
            }

            override fun onComplete() {
                Log.d("Tag", "onComplete is called")
            }

            override fun onNext(t: String) {
                Log.d("Tag", "onNext is called $t")
            }
        })
    }

    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}

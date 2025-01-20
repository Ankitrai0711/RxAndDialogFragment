package com.example.rxanddialogfragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity(),CallBack {
    private val disposable = CompositeDisposable()
    private lateinit var myViewModel : MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
       myViewModel = ViewModelProvider(this,ViewModelFactory(110)).get(MyViewModel::class.java)
        findViewById<TextView>(R.id.tvCount).text =  myViewModel.getData().toString()
        textChange()
        rx()
        rxClick()
    }
    fun textChange(){
        var et = findViewById<EditText>(R.id.etData)
        et.addTextChangedListener(object :TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
               findViewById<TextView>(R.id.tvData).text = et.text

            }

        })
    }

    private fun rxClick() {
        val button = findViewById<Button>(R.id.button)
        disposable.add(RxView.clicks(button)
            .throttleFirst(1500, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val dialogFragment = MyDialogFragment(this@MainActivity)
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


    override fun click(data: String) {
        findViewById<TextView>(R.id.tvCount).text =  myViewModel.updateData().toString()
        findViewById<TextView>(R.id.tv).text = data
        val dialogFragment = supportFragmentManager.findFragmentByTag("MY_DIALOG_TAG") as? DialogFragment
        dialogFragment?.dismiss()
    }


    override fun onDestroy() {
        disposable.dispose()
        super.onDestroy()
    }
}

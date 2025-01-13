package com.example.rxanddialogfragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MyDialogFragment : DialogFragment() {
 private lateinit var mcontext : Context
    override fun onAttach(context: Context) {
        mcontext = context
        super.onAttach(context)
    }


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

        val list = listOf("A","B","C","A","B","C","A","B","C","A","B","C","A","B","C","A","B","C")

        val view = inflater.inflate(R.layout.fragment_dialog, container, false)

        val recycler = view.findViewById<RecyclerView>(R.id.recyclerView)
            recycler.layoutManager = LinearLayoutManager(context)


        recycler.adapter = context?.let { MyAdapter(it,list) }




            return view
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            val weidth = resources.getDimension(R.dimen.dialog_width)
            val  height= resources.getDimension(R.dimen.dialog_height)
            dialog?.window?.apply {
                setLayout(weidth.toInt(),height.toInt()) // Adjust size
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) // Optional transparent background
            }
            super.onViewCreated(view, savedInstanceState)
        }

    }
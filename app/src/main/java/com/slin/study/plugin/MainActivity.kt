package com.slin.study.plugin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnLongClickListener
import androidx.appcompat.app.AppCompatActivity
import com.slin.apt.generated.Initiator
import com.slin.study.plugin.activity.AnotherActivity
import com.slin.study.plugin.apt.ModuleAInitial
import com.slin.study.plugin.databinding.ActivityMainBinding

/**
 * author: slin
 * date: 2021/12/14
 * description:
 *
 */

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvHello.setOnClickListener {
            Log.d(TAG, "OnClick: ${binding.tvHello}")
            startActivity(Intent(this@MainActivity, AnotherActivity::class.java))
        }
        binding.tvHello.text = "你好"

        Initiator.initial(this)

        binding.tvLambda.visibility = View.VISIBLE

        binding.tvLambda.setOnLongClickListener(object :View.OnLongClickListener{
            override fun onLongClick(view: View): Boolean {
                Log.d(TAG, "onLongClick: $view")

                onLongClickListener?.onLongClick(view)

                return false
            }

        })

        setOnLongClickListener { p0 ->
            Log.d(TAG, "setOnLongClickListener onLongClick: $p0")
            false
        }

    }

    private var onLongClickListener:OnLongClickListener? = null

    fun setOnLongClickListener(l: OnLongClickListener) {
        this.onLongClickListener = l
    }

}
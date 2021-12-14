package com.slin.study.plugin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.slin.study.plugin.activity.AnotherActivity
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
        binding.tvLambda.visibility = View.GONE
    }
}
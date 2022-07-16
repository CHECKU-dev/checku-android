package com.example.nodeproject2.ui

import android.os.Bundle
import com.example.nodeproject2.R
import com.example.nodeproject2.base.BaseActivity
import com.example.nodeproject2.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun init() {

    }
}
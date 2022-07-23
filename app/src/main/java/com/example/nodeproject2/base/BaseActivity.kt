package com.example.nodeproject2.base

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.nodeproject2.di.CheckuApplication

abstract class BaseActivity<T : ViewDataBinding>(@LayoutRes private val layoutResId: Int) :
    AppCompatActivity() {
    protected lateinit var binding: T
    private var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutResId)
//        super.onCreate(savedInstanceState)
//        binding = inflate(layoutInflater)
//        setContentView(binding.root)
        init()
    }

    abstract fun init()

    fun showLoadingDialog() {
        CheckuApplication.instance.showLoadingDialog(this)
    }

    fun hideLoadingDialog() {
        CheckuApplication.instance.hideLoadingDialog()
    }


    override fun onBackPressed() {
        if (System.currentTimeMillis() - waitTime >= 1500) {
            waitTime = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else finish()
    }

    protected fun shortShowToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()

    protected fun longShowToast(msg: String) =
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()


}
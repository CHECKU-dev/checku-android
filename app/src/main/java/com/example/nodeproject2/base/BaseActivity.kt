package com.example.nodeproject2.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<T : ViewBinding>(private val inflate: (LayoutInflater) -> T) :
    AppCompatActivity() {
    protected lateinit var binding: T
    private var waitTime = 0L

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
//        binding = DataBindingUtil.setContentView(this, layoutResId)
        init()
    }

    abstract fun init()

    // 로딩 다이얼로그, 즉 로딩창을 띄워줌.
    // 네트워크가 시작될 때 사용자가 무작정 기다리게 하지 않기 위해 작성.
//    fun showLoadingDialog(context: Context) {
//        mLoadingDialog = KudongsanLoadingDialog(context)
//        mLoadingDialog.show()
//    }
    // 띄워 놓은 로딩 다이얼로그를 없앰.
//    fun dismissLoadingDialog() {
//        if (mLoadingDialog.isShowing) {
//            mLoadingDialog.dismiss()
//        }
//    }


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
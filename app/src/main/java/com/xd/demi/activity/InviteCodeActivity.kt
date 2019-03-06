package com.xd.demi.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.xd.demi.R
import com.xd.demi.view.InviteCodeView
import kotlinx.android.synthetic.main.activity_invite.*

/**
 * Created by demi on 2019/3/1 下午4:07.
 */
class InviteCodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_invite)
        btn_control.setOnClickListener {
            if (et_in.isDrawNumber) {
                et_in.hideNumberText()
                btn_control.text = "显示邀请码"
            } else {
                et_in.showNumberText()
                btn_control.text = "隐藏邀请码"
            }
        }
        et_in.setOnFinishInputCodeListener {
            Toast.makeText(this, "输入完毕：$it", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}
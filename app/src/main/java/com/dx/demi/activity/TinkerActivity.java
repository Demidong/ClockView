package com.dx.demi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.dx.demi.R;
import com.tencent.tinker.lib.tinker.TinkerInstaller;

/**
 * Created by demi on 2017/5/12.
 */

public class TinkerActivity extends Activity {
    private TextView mTextView;
    private Button mButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinker);
        mTextView = (TextView) findViewById(R.id.tv);
        mButton = (Button) findViewById(R.id.btn);
        //mTextView.setText("这不是bug");
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TinkerInstaller.onReceiveUpgradePatch(getApplicationContext(),
                        Environment.getExternalStorageDirectory().getAbsolutePath()
                                + "/patch_signed_7zip.apk");//等下要push到SD卡里面去apk，以达到更新的目的
            }
        });
    }
}

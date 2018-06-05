package com.xd.demi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.xd.demi.R;
import com.xd.demi.view.CameraSurfaceView;

public class CameraActivity extends Activity {
    private CameraSurfaceView cameraview;
    private ImageView transform;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 全屏显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_camera);
        cameraview = (CameraSurfaceView) findViewById(R.id.cameraview);
        transform = (ImageView) findViewById(R.id.transform);
        Toast.makeText(CameraActivity.this,"请按音量下键拍照",Toast.LENGTH_LONG).show();
        transform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cameraview.switchCameraWay();
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                cameraview.takePhoto();
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}

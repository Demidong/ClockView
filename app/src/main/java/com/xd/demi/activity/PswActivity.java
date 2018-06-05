package com.xd.demi.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.xd.demi.R;
import com.xd.demi.bean.Point;
import com.xd.demi.view.NinePointView;
import com.xd.demi.view.NineSquareView;
import com.mic.etoast2.EToast2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by demi on 2017/4/13.
 */

public class PswActivity extends Activity implements EasyPermissions.PermissionCallbacks {
    private NineSquareView nineSquareView;
    private NinePointView ninePointView;
    private TextView title;
    private TextView notice;
    private TextView reset;
    private ImageView head;
    private View view_line;
    private SharedPreferences preferences;
    private static String PREFERENCENAME = "pointString";
    private boolean isSetPsw = false;
    private LinkedHashMap<String, Point> pswpoints = new LinkedHashMap<>();
    private int totalTime = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activitiy_psw);
        nineSquareView = (NineSquareView) findViewById(R.id.nsv);
        ninePointView = (NinePointView) findViewById(R.id.npv);
        title = (TextView) findViewById(R.id.title);
        notice = (TextView) findViewById(R.id.notice);
        reset = (TextView) findViewById(R.id.reset);
        head = (ImageView) findViewById(R.id.head);
        view_line = findViewById(R.id.view_line);
        preferences = getSharedPreferences("points", Context.MODE_PRIVATE);
        nineSquareView.setOnFinishGestureListener(new NineSquareView.OnFinishGestureListener() {
            @Override
            public void onfinish(LinkedHashMap<String, Point> points) {
                if (isSetPsw) {//已设置好密码了。
                    if (isEquals(getHashMap(), points)) {
                        startActivity(new Intent(PswActivity.this, MainActivity.class));
                        finish();
                    } else {
                        totalTime--;
                        if (totalTime == 0) {
                            startActivity(new Intent(PswActivity.this, MainActivity.class));
                            finish();
                            return;
                        }
                        notice.setTextColor(Color.RED);
                        notice.setText("密码错误,你还有" + totalTime + "次机会!");
                    }
                } else if (!isSetPsw && pswpoints.size() == 0) {//第一次设置
                    if (points.size() < 4) {
                        notice.setTextColor(Color.RED);
                        notice.setText("请至少连接4个点");
                        return;
                    }
                    pswpoints.putAll(points);
                    ninePointView.setHightLightPoints(points);
                    notice.setTextColor(Color.BLUE);
                    notice.setText("请再次绘制手势密码");
                } else {//第二次设置
                    if (isEquals(pswpoints, points)) {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString(PREFERENCENAME, map2String(points)).commit();
                        notice.setTextColor(Color.BLUE);
                        notice.setText("设置成功");
                        startActivity(new Intent(PswActivity.this, MainActivity.class));
                        finish();
                        isSetPsw = true;
                    } else {
                        notice.setTextColor(Color.RED);
                        notice.setText("与首次绘制不一致,请再次绘制");
                        EToast2.makeText(PswActivity.this, "与首次绘制不一致,请再次绘制", EToast2.LENGTH_LONG).show();
                        pswpoints.clear();
                    }
                }

            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        init();
        checkPermissions();
    }

    private void reset() {
        isSetPsw = false;
        pswpoints.clear();
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(PREFERENCENAME);
        editor.commit();
        ninePointView.clearPoints();
        notice.setTextColor(Color.BLACK);
        notice.setText("为了您的账户安全,请设置手势密码");
    }

    private void init() {
        isSetPsw = preferences.getString(PREFERENCENAME, null) != null;
        title.setVisibility(isSetPsw ? View.INVISIBLE : View.VISIBLE);
        view_line.setVisibility(isSetPsw ? View.INVISIBLE : View.VISIBLE);
        head.setVisibility(isSetPsw ? View.VISIBLE : View.GONE);
        ninePointView.setVisibility(isSetPsw ? View.GONE : View.VISIBLE);
        notice.setText(isSetPsw ? "" : "为了您的账户安全,请设置手势密码");
        reset.setVisibility(isSetPsw ? View.GONE : View.VISIBLE);
    }

    public boolean isEquals(LinkedHashMap<String, Point> pointsOne, LinkedHashMap<String, Point> pointsTwo) {
        Iterator<String> iterator = pointsOne.keySet().iterator();
        Iterator<String> iterator2 = pointsTwo.keySet().iterator();
        if (pointsOne.size() != pointsTwo.size()) {
            return false;
        }
        while (iterator.hasNext()) {
            String s = iterator.next();
            String s2 = iterator2.next();
            if (!s.equals(s2)) {
                return false;
            }
        }
        return true;
    }

    public String map2String(LinkedHashMap<String, Point> hashmap) {
        // 实例化一个ByteArrayOutputStream对象，用来装载压缩后的字节文件。
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        String sceneListString = null;
        // 然后将得到的字符数据装载到ObjectOutputStream
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(
                    byteArrayOutputStream);
            // writeObject 方法负责写入特定类的对象的状态，以便相应的 readObject 方法可以还原它
            objectOutputStream.writeObject(hashmap);
            // 最后，用Base64.encode将字节文件转换成Base64编码保存在String中
            sceneListString = new String(Base64.encode(
                    byteArrayOutputStream.toByteArray(), Base64.DEFAULT), "utf8");
            // 关闭objectOutputStream
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sceneListString;
    }

    public LinkedHashMap<String, Point> getHashMap() {
        String liststr = preferences.getString(PREFERENCENAME, null);
        try {
            return string2Map(liststr);
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //@SuppressWarnings("unchecked")
    public LinkedHashMap<String, Point> string2Map(
            String SceneListString) throws
            IOException, ClassNotFoundException {
        byte[] mobileBytes = Base64.decode(SceneListString.getBytes(),
                Base64.DEFAULT);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                mobileBytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(
                byteArrayInputStream);
        LinkedHashMap<String, Point> SceneList = (LinkedHashMap<String, Point>) objectInputStream
                .readObject();
        objectInputStream.close();
        return SceneList;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        LogUtils.d("onPermissionsGranted:" + requestCode + ":" + perms.size());
        if (hasAllNeededPermissions()) {
            launch();
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        LogUtils.d("onPermissionsDenied:" + requestCode + ":" + perms.size());
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("您没有全部授权，可能无法使用部分功能")
                .setPositiveButton("去授权", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent settingIntent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        settingIntent.setData(Uri.fromParts("package", getPackageName(), null));
                        startActivityForResult(settingIntent, AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE);
                    }
                })
                .setCancelable(true)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        launch();
                    }
                })
                .show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void checkPermissions() {
        if (!hasAllNeededPermissions()) {
            requestPermission();
        }
    }

    private boolean hasAllNeededPermissions() {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
    }

    private void launch() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA},
                0);
    }

}

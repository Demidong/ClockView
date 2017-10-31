package com.dx.demi.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.dx.demi.R;

import java.util.zip.Inflater;

/**
 * Created by demi on 16/12/13.
 */

public class NotifyCationActivity extends Activity {
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuild;
    private TextView btn_cancel, btn_retry;//下载失败时,通知栏按钮
    private TextView send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_notify);
        send= (TextView) findViewById(R.id.send);
        View notify_view = getLayoutInflater().inflate(R.layout.notify_down_fail, null);
        btn_cancel = (TextView) notify_view.findViewById(R.id.btn_cancel);
        btn_retry = (TextView) notify_view.findViewById(R.id.btn_retry);
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNotificationManager != null) {
                    mNotificationManager.cancel(1);
                }
            }
        });
        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("ddddddddd");
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.notify_down_fail);
                mBuild = new NotificationCompat.Builder(NotifyCationActivity.this)

                        .setSmallIcon(R.mipmap.ic_launcher)   //若没有设置largeicon，此为左边的大icon，设置了largeicon，则为右下角的小icon，无论怎样，都影响Notifications area显示的图标

                        .setContentTitle("微盘宝更新下载") //标题

                        .setOngoing(true)      //true使notification变为ongoing，用户不能手动清除，类似QQ,false或者不设置则为普通的通知

                        .setAutoCancel(true)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setContentInfo("0%")
                        .setTicker("微盘宝更新下载")
                        .setContent(contentView);
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                mNotificationManager.notify(1, mBuild.build());
            }
        });


    }

}

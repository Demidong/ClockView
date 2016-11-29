package com.dx.demi.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.dx.demi.View.PieChatView;
import com.dx.demi.R;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Random;

/**
 * Created by demi on 16/11/28.
 */

public class PieChatActivity extends Activity {
    private LinkedHashMap kindsMap = new LinkedHashMap<String, Integer>();
    private ArrayList<Integer> colors = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piechat);
        PieChatView pieChatView = (PieChatView) findViewById(R.id.pie);
        kindsMap.put("苹果", 10);
        kindsMap.put("梨子", 30);
        kindsMap.put("香蕉", 10);
        kindsMap.put("葡萄", 30);
        kindsMap.put("哈密瓜", 10);
        kindsMap.put("猕猴桃",30);
        kindsMap.put("草莓", 10);
        kindsMap.put("橙子", 30);
        kindsMap.put("火龙果", 10);
        kindsMap.put("椰子", 20);
        for (int i = 1; i <= 40; i++){
            int r= (new Random().nextInt(100)+10)*i;
            int g= (new Random().nextInt(100)+10)*3*i;
            int b= (new Random().nextInt(100)+10)*2*i;
            int color = Color.rgb(r,g,b);
            if(Math.abs(r-g)>10&&Math.abs(r-b)>10&&Math.abs(b-g)>10){
                colors.add(color);
            }
        }
        pieChatView.setCenterTitle("水果大拼盘");
        pieChatView.setDataMap(kindsMap);
        pieChatView.setColors(colors);
        pieChatView.setMinAngle(50);
        pieChatView.startDraw();
    }
}

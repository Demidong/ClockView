package com.dx.demi.activity;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.dx.demi.R;

/**
 * Created by demi on 2017/2/8.
 */

public class AnimationActivity extends Activity {
    Spinner animkinds;
    Spinner anims;
    ImageView image;
    AnimationSet animationSet;
    AlphaAnimation alphaAnimation;
    ScaleAnimation scaleAnimation;
    RotateAnimation rotateAnimation;
    TranslateAnimation translateAnimation;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        anims = (Spinner) findViewById(R.id.anims);
        animkinds = (Spinner) findViewById(R.id.animkinds);
        image = (ImageView) findViewById(R.id.image);
        animationSet = new AnimationSet(true);
        alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        scaleAnimation = new ScaleAnimation(0, 3f, 0, 3f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        translateAnimation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.5f, Animation.RELATIVE_TO_SELF, 0);
        anims.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (pos == 0) {
                    animationSet.setDuration(2000);
                    animationSet.getAnimations().clear();
                    animationSet.setFillAfter(true);
                    switch (position) {
                        case 0:
                            animationSet.addAnimation(alphaAnimation);
                            break;
                        case 1:
                            animationSet.addAnimation(scaleAnimation);
                            break;
                        case 2:
                            animationSet.addAnimation(rotateAnimation);
                            break;
                        case 3:
                            animationSet.addAnimation(translateAnimation);
                            break;
                        case 4:
                            animationSet.addAnimation(rotateAnimation);
                            animationSet.addAnimation(scaleAnimation);
                            animationSet.addAnimation(alphaAnimation);
                            break;
                    }
                    image.startAnimation(animationSet);
                } else {

                    switch (position) {
                        case 0:
                            ObjectAnimator//
                                    .ofFloat(image, "alpha", 0f, 1f)//
                                    .setDuration(2000)//
                                    .start();
                            break;
                        case 1:
                            ObjectAnimator//
                                    .ofFloat(image, "scaleX", 0f, 2f)//
                                    .setDuration(2000)//
                                    .start();
                            break;
                        case 2:
                            ObjectAnimator//
                                    .ofFloat(image, "rotationY", 0.0F, 180.0F)//
                                    .setDuration(2000)//
                                    .start();
                            break;
                        case 3:
                            ObjectAnimator//
                                    .ofFloat(image, "translationY", 0f, 80f)//
                                    .setDuration(2000)//
                                    .start();
                            break;
                        case 4:
                            PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f,
                                    0f, 1f);
                            PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f,
                                    0, 1f);
                            PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f,
                                    0, 1f);
                            ObjectAnimator.ofPropertyValuesHolder(image, pvhX, pvhY, pvhZ).setDuration(2000).start();
                            break;
                    }
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        animkinds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}

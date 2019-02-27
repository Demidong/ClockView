//
// Created by qiuping.dong on 2019/2/27.
//

#include <jni.h>
#include <stdio.h>

extern "C"
{
    JNIEXPORT jstring JNICALL Java_com_xd_demi_activity_HelloJNIActivity_getFromJNI(JNIEnv *env,jobject obj){
            // 参数说明
                  // 1. JNIEnv：代表了VM里面的环境，本地的代码可以通过该参数与Java代码进行操作
                  // 2. obj：定义JNI方法的类的一个本地引用（this）
            return env -> NewStringUTF("Hello I am From JNI");
    }
}
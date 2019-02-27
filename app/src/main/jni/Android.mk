LOCAL_PATH       :=  $(call my-dir)
# 设置工作目录，而my-dir则会返回Android.mk文件所在的目录

include              $(CLEAR_VARS)
# 清除几乎所有以LOCAL——PATH开头的变量（不包括LOCAL_PATH）

LOCAL_MODULE     :=  hello_jni
# 设置模块的名称，即编译出来.so文件名
# 注，要和上述步骤中build.gradle中NDK节点设置的名字相同
LOCAL_SRC_FILES  :=  HelloJNI.cpp
# 指定参与模块编译的C/C++源文件名

include              $(BUILD_SHARED_LIBRARY)
# 指定生成的静态库或者共享库在运行时依赖的共享库模块列表。

# 示例说明
# 示例1：c++文件参与编译
    # 单个c++文件参与编译
    #include $(CLEAR_VARS)
    #LOCAL_MODULE := HelloJNI # 编译出来的文件名
    #LOCAL_SRC_FILES := HelloJNI.cpp

    # 多个c++文件参与编译
    #include $(CLEAR_VARS)
    #LOCAL_MODULE := Carsontest # 编译出来的文件名
    #LOCAL_SRC_FILES := \
    #    test1.cpp \
    #    test/ftest2.cpp \
     #   test3.c \

# 示例2：.so文件参与模块编译
    #include $(CLEAR_VARS)
   # LOCAL_MODULE := Carsontest # 编译出来的文件名
   # LOCAL_SRC_FILES := test/Carsontest.so
   # include $(PREBUILT_SHARED_LIBRARY) # 设置可被依赖

# 示例3：设置需依赖的.so文件
    #LOCAL_SHARED_LIBRARIES := \
    #    test1 \
    #    Carsontest
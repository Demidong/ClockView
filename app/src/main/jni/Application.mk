APP_ABI := armeabi-v7a
# 最常用的APP_ABI字段：指定需要基于哪些CPU平台的.so文件
# 常见的平台有armeabi x86 mips，其中移动设备主要是armeabi平台
# 默认情况下，Android平台会生成所有平台的.so文件，即同APP_ABI := armeabi x86 mips
# 指定CPU平台类型后，就只会生成该平台的.so文件，即上述语句只会生成armeabi平台的.so文件
APP_PLATFORM := android-28
#ARMv5——armeabi
#ARMv7 ——armeabi-v7a
#ARMv8——arm64- v8a
#x86——x86
#MIPS ——mips
#MIPS64——mips64
#x86_64——x86_64
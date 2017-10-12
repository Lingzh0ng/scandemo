#include <jni.h>
#include <string>

extern "C"
JNIEXPORT jstring JNICALL
Java_com_wearapay_ndkdemo_MainActivity_stringFromJN(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

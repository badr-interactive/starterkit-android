#include <jni.h>
#include <string>

using namespace std;

const string ANDROID_GOOGLE_CLIENT = "392223742967-d1ha3fpu60289nlinkvntcp93aubke8a.apps.googleusercontent.com";
const string BASE_URL = "https://private-064ae-starterkit.apiary-proxy.com";

extern "C" jstring JNICALL
Java_com_bi_starterkit_data_Constants_getAndroidGoogleClient(
        JNIEnv *env, jobject instance) {
    string s;

    s = ANDROID_GOOGLE_CLIENT;

    return env->NewStringUTF(s.c_str());
}

extern "C" jstring JNICALL
Java_com_bi_starterkit_data_Constants_getBaseUrl(
        JNIEnv *env, jobject instance) {
    string s;

    s = BASE_URL;

    return env->NewStringUTF(s.c_str());
}
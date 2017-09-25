#include <jni.h>
#include <string>

using namespace std;

const string ANDROID_GOOGLE_CLIENT = "392223742967-d1ha3fpu60289nlinkvntcp93aubke8a.apps.googleusercontent.com";
const string BASE_URL = "https://dev.badr.co.id/freedom";
const string COMODO_KEY = "";
const string CERTIFICATE_KEY = "-----BEGIN CERTIFICATE-----\n"
        "MIIDwjCCAqoCCQD4mAktA7OcdzANBgkqhkiG9w0BAQUFADCBojELMAkGA1UEBhMC\n"
        "SUQxEzARBgNVBAgMCkpBV0EgQkFSQVQxDjAMBgNVBAcMBURFUE9LMR0wGwYDVQQK\n"
        "DBRQVC4gQkFEUiBJTlRFUkFDVElWRTELMAkGA1UECwwCSVQxFzAVBgNVBAMMDmRl\n"
        "di5iYWRyLmNvLmlkMSkwJwYJKoZIhvcNAQkBFhphZG1pbkBiYWRyLWludGVyYWN0\n"
        "aXZlLmNvbTAeFw0xNzA5MDUwMjU4NDdaFw0xODA5MDUwMjU4NDdaMIGiMQswCQYD\n"
        "VQQGEwJJRDETMBEGA1UECAwKSkFXQSBCQVJBVDEOMAwGA1UEBwwFREVQT0sxHTAb\n"
        "BgNVBAoMFFBULiBCQURSIElOVEVSQUNUSVZFMQswCQYDVQQLDAJJVDEXMBUGA1UE\n"
        "AwwOZGV2LmJhZHIuY28uaWQxKTAnBgkqhkiG9w0BCQEWGmFkbWluQGJhZHItaW50\n"
        "ZXJhY3RpdmUuY29tMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1S9A\n"
        "igBeZjcHALfmiocUXu8P1M75aYDYYt+hhX2aO1B8RMZKfatcjv1c7KoL0niCb4ZY\n"
        "VJrB/xL3dtCqa7pNdwOFkn/xSilh8nocEux6VVEyL+pX6xxFghg89IIDe77cPV2v\n"
        "4zhfZRiH6dhp1HTGCEmvdjfS8V4YcCyG2rqAmnRcBGNySkl5epkXDg5WJi7ZqUwc\n"
        "8TSbMSAKWV8AFh5fAsHhG6WbzU1MyMC7XuU0CBIiQBXyqlqUUmTKYgKICm1nruph\n"
        "trMgmxJZ/bLs/F0mjLB7ke2JnM4VPEDHKOqkccIptlnEfPTs/h6Kz15KjFoXtrKQ\n"
        "RwAP2uS5tXDqbuEWHQIDAQABMA0GCSqGSIb3DQEBBQUAA4IBAQBnC2hIVCqK/Ijk\n"
        "O7BYrRXgNR07DuSU5wtrcjimhcL0JVRafOAUQ2VL6EqzNq1udxSBizCyWPOov1GN\n"
        "esVQVW/0oKZVI0lU4tyzbBJNdlznI4YHrt4mmMmI+KZTd8M/P9j7akLc3mlUdA6t\n"
        "hNdSAYsPAUcVH6OiL777OnTGXAhAlrYoH+YSbZbBzsvC5hauqhvcC6KbOoeBLMgI\n"
        "iEOmu7C3PAlSLiUKf8JC/gfbNbrIIpfBX40PI3eChBaYqj83U4hrHAQahPs8iXN6\n"
        "xtKLD/m/i1cKu/e850Z+Ib9HkiUcUH7lH3HdXdWElVyLyJ+vKQjSpqrdYfJ3PCpx\n"
        "V6PB3Pqt\n"
        "-----END CERTIFICATE-----";
const string SALT_KEY = "5729D65CB687DFACE17FA8876223A";

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

extern "C" jstring JNICALL
Java_com_bi_starterkit_data_Constants_getCertificateKey(
        JNIEnv *env, jobject instance) {
    string s;

    s = CERTIFICATE_KEY;

    return env->NewStringUTF(s.c_str());
}

extern "C" jstring JNICALL
Java_com_bi_starterkit_data_Constants_getComodoKey(
        JNIEnv *env, jobject instance) {
    string s;

    s = COMODO_KEY;

    return env->NewStringUTF(s.c_str());
}

extern "C" jstring JNICALL
Java_com_bi_starterkit_data_Constants_getSaltKey(
        JNIEnv *env, jobject instance) {
    string s;

    s = SALT_KEY;

    return env->NewStringUTF(s.c_str());
}
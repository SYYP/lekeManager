package www.manager.leke.com.lekemanager.http;

import java.io.IOException;


import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.SpUtils;

import static www.manager.leke.com.lekemanager.configuration.HtppConfiguration.DEVICE_MODEL;

/***
 *  网络出现异常 ，设置尝试 拦截器
 */
public class RetryIntercepter implements Interceptor {


    public int maxRetry;//最大重试次数
    private int retryNum = 0;//假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    public RetryIntercepter(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request  request = addHeader(chain) ;
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && retryNum < maxRetry) {
            retryNum++;
            LogUtils.e("网络请求出错 ，正在尝试请求 第" + retryNum + "次");

            BaseFragmentActivity.showToast("网络错误,正在进行尝试 第" + retryNum + "次尝试，请耐心等待");
            response = chain.proceed(request);
        }
        return response;
    }


    private Request addHeader(Chain chain) {
        Request orignaRequest = chain.request();
        Request.Builder newBuilder = orignaRequest.newBuilder();
        newBuilder.header("Content-Type", "application/json");
        newBuilder.header("Accept", "application/json");
        newBuilder.method(orignaRequest.method(), orignaRequest.body());
        newBuilder.addHeader("X-Device-Model", DEVICE_MODEL);
        newBuilder.addHeader("X-Auth-Options", "1e7904f32c4fcfd59b8a524d1bad1d8a.qg0J9zG*FIkBk^vo");
        newBuilder.addHeader("X-User-Token", SpUtils.getString(Contacts.TOKEN));
        return newBuilder.build();
    }
}

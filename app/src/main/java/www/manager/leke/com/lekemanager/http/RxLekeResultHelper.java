package www.manager.leke.com.lekemanager.http;


import android.content.Intent;
import android.util.Pair;
import android.widget.Toast;
import java.net.UnknownHostException;
import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;
import www.manager.leke.com.lekemanager.activity.LoginActivity;
import www.manager.leke.com.lekemanager.base.BaseResult;
import www.manager.leke.com.lekemanager.base.ServerCode;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

public class RxLekeResultHelper {
    public static <T> Observable.Transformer<BaseResult<T>, T> handleResult() {
        return tObservable -> tObservable.flatMap(
                entity -> {
                    UIUtils.runInMainThread(() -> {
                        if (HttpManager.loadingProgressDialog != null && HttpManager.loadingProgressDialog.isShowing()) {
                            LogUtils.e("关闭网络对话框");
                            HttpManager.loadingProgressDialog.dismiss();
                        }
                    });
                    if (entity.getCode() == ServerCode.success) {
                        return createData(entity.getData());
                    } else if (entity.getCode() == ServerCode.token_invalid) {
                        //token失效，重新获取token中
//                        YxClient.getInstance().logout();
                        Intent intent = new Intent(UIUtils.getContext(), LoginActivity.class);
                        UIUtils.getContext().startActivity(intent);
                        return null;
////                                    return Observable.error(new ApiException(entity.getCode() + "", entity.getMsg(), entity.getData()));
                    }
                       else {
                        if (entity.getCode() == ServerCode.cosplay_post_error){
                            UIUtils.showToastSafe("当前为模拟学生登录状态,禁止操作", Toast.LENGTH_LONG);
                        }
                        return Observable.error(new ApiException(entity.getCode(), entity.getMsg(), entity.getData()));
                    }
                }, throwable -> {
                    UIUtils.runInMainThread(() -> {
                        if (HttpManager.loadingProgressDialog != null && HttpManager.loadingProgressDialog.isShowing()) {
                            LogUtils.e("关闭网络对话框");
                            HttpManager.loadingProgressDialog.dismiss();
                        }
                    });
                    LogUtils.e("HTTP:"+throwable.toString());

                    if (throwable instanceof UnknownHostException) {
                        UIUtils.showToastSafe("当前网络可能无外网，换个网络试试看哦！！",0);
//                        return null;
                    }

                    return Observable.error(throwable);
                }, () -> {
                    UIUtils.runInMainThread(() -> {
                        if (HttpManager.loadingProgressDialog != null && HttpManager.loadingProgressDialog.isShowing()) {
                            LogUtils.e("关闭网络对话框");
                            HttpManager.loadingProgressDialog.dismiss();
                        }
                    });
                    return null;
                }
        );
    }


    public static <T> Observable.Transformer<BaseResult<T>, Pair<Integer, T>> handleResult_new() {

        return tObservable -> tObservable.flatMap(
                (Func1<BaseResult<T>, Observable<Pair<Integer, T>>>) entity -> {
                    UIUtils.runInMainThread(() -> {
                        if (HttpManager.loadingProgressDialog != null && HttpManager.loadingProgressDialog.isShowing()) {
                            LogUtils.e("关闭网络对话框");
                            HttpManager.loadingProgressDialog.dismiss();
                        }
                    });
                    if (entity.getCode() == ServerCode.success) {
                        Pair<Integer, T> pair = new Pair<>(entity.getCount(), entity.getData());
                        return createData(pair);
                    }
//                    else if (entity.getCode() == ServerCode.token_invalid) {
//                        //token失效，重新获取token中
//                        Intent intent = new Intent(UIUtils.getContext(), GetTokenService.class);
//                        UIUtils.getContext().startService(intent);
//                        return Observable.error(new ApiException(entity.getCode(), entity.getMsg(), entity.getData()));
//                    }
                    else {
                        if (entity.getCode() == ServerCode.cosplay_post_error){
                            UIUtils.showToastSafe("当前为模拟学生登录状态,禁止操作", Toast.LENGTH_LONG);
                        }

                        return Observable.error(new ApiException(entity.getCode(), entity.getMsg(), entity.getData()));
                    }
                }, (Func1<Throwable, Observable<Pair<Integer, T>>>) throwable -> {
                    UIUtils.runInMainThread(() -> {
                        if (HttpManager.loadingProgressDialog != null && HttpManager.loadingProgressDialog.isShowing()) {
                            LogUtils.e("关闭网络对话框");
                            HttpManager.loadingProgressDialog.dismiss();
                        }
                    });
                    LogUtils.e("HTTP:"+throwable.toString());
                    if (throwable instanceof UnknownHostException) {
                        UIUtils.showToastSafe("当前网络可能无外网，换个网络试试看哦！！",0);
//                        return null;
                    }
                    return Observable.error(throwable);
                }, (Func0<Observable<Pair<Integer, T>>>) () -> {
                    UIUtils.runInMainThread(() -> {
                        if (HttpManager.loadingProgressDialog != null && HttpManager.loadingProgressDialog.isShowing()) {
                            LogUtils.e("关闭网络对话框");
                            HttpManager.loadingProgressDialog.dismiss();
                        }
                    });
                    return null;
                }
        );
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(t);
                subscriber.onCompleted();
            } catch (Exception e) {
                subscriber.onError(e);
            }
        });
    }


    /*private static <T> Observable<T> createa(T t) {

         return HttpManager.getInstace().getVersion()
//                .compose(this.bindToLifecycle())
                .subscribe(new Action1<VersionInfo>() {
                    @Override
                    public void call(VersionInfo version) {

                        version.get

                        return null;

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        LogUtils.e("FH", "检测版本失败:" + throwable.getMessage());
                        throwable.printStackTrace();
//                        login();
                        return null;
                    }
                });


        *//*return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });*//*
    }*/


}

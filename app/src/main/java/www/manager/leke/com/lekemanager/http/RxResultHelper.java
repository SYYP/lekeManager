package www.manager.leke.com.lekemanager.http;


import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;
import rx.functions.Func1;
import www.manager.leke.com.lekemanager.base.BaseResult;

/***
 *  处理http返回数据的结果
 */
public class RxResultHelper {

    public static <T> Observable.Transformer<BaseResult<T>, T>  handleResult() {
        return new Observable.Transformer<BaseResult<T>, T>() {
            @Override
            public Observable<T> call(Observable<BaseResult<T>> tObservable) {
                return tObservable.flatMap(
                        new Func1<BaseResult<T>, Observable<? extends T>>() {
                            @Override
                            public Observable<? extends T> call(BaseResult<T> entity) {
                                if (entity.getCode() == 200) {
                                    return createData(entity.getData());
                                } else {
                                    return Observable.error(new ApiException(entity.getCode() + "", entity.getMsg()));
                                }
                            }
                        },
                        new Func1<Throwable, Observable<? extends T>>() {
                            @Override
                            public Observable<? extends T> call(Throwable throwable) {
                                return Observable.error(throwable);
                            }
                        }, new Func0<Observable<? extends T>>() {
                            @Override
                            public Observable<? extends T> call() {
                                return null;
                            }
                        }
                );
            }
        };
    }

    private static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }
}
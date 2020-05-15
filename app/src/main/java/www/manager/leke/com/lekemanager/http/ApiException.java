package www.manager.leke.com.lekemanager.http;

/**
 * 功能：自定义网络请求异常
 * 作者: YUAN_YE
 * 日期: 2019/4/23
 * 时间: 10:31
 */
public class ApiException extends Exception {
    String resultMessage;
    int resultCode;
    Object extraData;

    public ApiException(int resultCode, String resultMessage , Object extraData) {
        this.resultMessage = resultMessage;
        this.resultCode = resultCode;
        this.extraData = extraData;
    }

    @Override
    public String getMessage() {
        return resultMessage;
    }

    public int getCode() {
        return resultCode;
    }

    public Object getExtraData(){
        return extraData;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "resultMessage='" + resultMessage + '\'' +
                ", resultCode='" + resultCode + '\'' +
                ", extraData=" + extraData +
                '}';
    }
}

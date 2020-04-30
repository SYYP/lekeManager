package www.manager.leke.com.lekemanager.http;

public class ApiException extends Exception {
    String ResultMessage;
    String ResultCode;

    public ApiException(String resultCode, String resultMessage) {
        ResultMessage = resultMessage;
        ResultCode = resultCode;
    }

    @Override
    public String getMessage() {
        return ResultMessage;
    }

    public String getCode() {
        return ResultCode;
    }
}


package www.manager.leke.com.lekemanager.bean;

/**
 * 功能：EventBus能够简化各组件间的通信，让我们的代码书写变得简单，
 * 能有效的分离事件发送方和接收方(也就是解耦的意思)，
 * 能避免复杂和容易出错的依赖性和生命周期问题。
 * <p>
 * 作者: YUAN_YE
 * 日期: 2019/4/23
 * 时间: 9:37
 */
public class BaseEvent {

    private String type;

    private Object ExtraData;
    public BaseEvent() {
    }

    public BaseEvent(String type) {
        this.type = type;
    }

    public BaseEvent(String type, Object extraData) {
        this.type = type;
        ExtraData = extraData;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setExtraData(Object extraData) {
        ExtraData = extraData;
    }

    public String getType() {
        return type;
    }


    public Object getExtraData() {
        return ExtraData;
    }
}

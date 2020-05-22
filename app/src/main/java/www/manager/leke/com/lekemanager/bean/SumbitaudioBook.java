package www.manager.leke.com.lekemanager.bean;

/**
 * Created by ypu
 * on 2020/5/18 0018
 *  提交点读
 */
public class SumbitaudioBook {
    private int bookId;
    private String audioStatus;//点读状态，必传
    private String audioEntryLogInfo;//编辑提交时的备注

    public SumbitaudioBook(int bookId, String audioStatus, String audioEntryLogInfo) {
        this.bookId = bookId;
        this.audioStatus = audioStatus;
        this.audioEntryLogInfo = audioEntryLogInfo;
    }
    @Override
    public String toString() {
        return "SumbitaudioBook{" +
                "bookId=" + bookId +
                ", audioStatus='" + audioStatus + '\'' +
                ", audioEntryLogInfo='" + audioEntryLogInfo + '\'' +
                '}';
    }
}

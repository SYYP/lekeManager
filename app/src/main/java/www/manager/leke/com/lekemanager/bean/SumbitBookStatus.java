package www.manager.leke.com.lekemanager.bean;

/**
 * Created by ypu
 * on 2020/5/14 0014
 *   图书模块提交时候需上传的信息
 */
public class SumbitBookStatus {

     private  int BookId;
     private String bookStatus;
     private  String bookEntryLogInfo;

    public SumbitBookStatus(int bookId, String bookStatus, String bookEntryLogInfo) {
        BookId = bookId;
        this.bookStatus = bookStatus;
        this.bookEntryLogInfo = bookEntryLogInfo;
    }

    @Override
    public String toString() {
        return "SumbitBookStatus{" +
                "BookId=" + BookId +
                ", bookStatus='" + bookStatus + '\'' +
                ", bookEntryLogInfo='" + bookEntryLogInfo + '\'' +
                '}';
    }
}

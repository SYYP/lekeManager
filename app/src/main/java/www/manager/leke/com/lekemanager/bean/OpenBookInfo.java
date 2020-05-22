package www.manager.leke.com.lekemanager.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 功能：打开笔记 和图书的besn
 * 作者: YUAN_YE
 * 日期: 2019/7/1
 * 时间: 15:41
 */
public class OpenBookInfo implements Parcelable {

    //是否显示下载进度条dialog
    private boolean isProgress;
    private Integer bookId;
    private String type;
    private String imgUrl;
    private String bookTitle;
    private String bookSrcode;
    private boolean autoOpean;  //是否自动跳转

    public boolean isProgress() {
        return isProgress;
    }

    public OpenBookInfo setProgressg(boolean progressg) {
        isProgress = progressg;
        return this;
    }


    public Integer getBookId() {
        return bookId;
    }

    public OpenBookInfo setBookId(Integer bookId) {
        this.bookId = bookId;
        return this;
    }

    public String getType() {
        return type;
    }

    public OpenBookInfo setType(String type) {
        this.type = type;
        return this;
    }

    public String getBookSrcode() {
        return bookSrcode;
    }


    public OpenBookInfo setBookSrcode(String bookSrcode) {
        this.bookSrcode = bookSrcode;
        return this;
    }


    public String getImgUrl() {
        return imgUrl;
    }

    public OpenBookInfo setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
        return this;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public OpenBookInfo setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
        return this;
    }
    public boolean isAutoOpean() {
        return autoOpean;
    }

    public OpenBookInfo setAutoOpean(boolean autoOpean) {
        this.autoOpean = autoOpean;
        return this;
    }



    public OpenBookInfo() {
    }

    @Override
    public String toString() {
        return "OpenBookInfo{" +
                "isProgress=" + isProgress +
                ", bookId='" + bookId + '\'' +
                ", type='" + type + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookSrcode='" + bookSrcode + '\'' +
                ", autoOpean=" + autoOpean +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.isProgress ? (byte) 1 : (byte) 0);
        dest.writeInt(this.bookId);
        dest.writeString(this.type);
        dest.writeString(this.imgUrl);
        dest.writeString(this.bookTitle);
        dest.writeString(this.bookSrcode);
        dest.writeByte(this.autoOpean ? (byte) 1 : (byte) 0);
    }

    protected OpenBookInfo(Parcel in) {
        this.isProgress = in.readByte() != 0;
        this.bookId = in.readInt();
        this.type = in.readString();
        this.imgUrl = in.readString();
        this.bookTitle = in.readString();
        this.bookSrcode = in.readString();
        this.autoOpean = in.readByte() != 0;
    }

    public static final Creator<OpenBookInfo> CREATOR = new Creator<OpenBookInfo>() {
        @Override
        public OpenBookInfo createFromParcel(Parcel source) {
            return new OpenBookInfo(source);
        }

        @Override
        public OpenBookInfo[] newArray(int size) {
            return new OpenBookInfo[size];
        }
    };
}
package www.manager.leke.com.lekemanager.bean;

/**
 * 功能：图书目录
 * 作者: YUAN_YE
 * 日期: 2019/6/26
 * 时间: 21:56
 */
public class ChapterInfo {
    private String title ;
    private String position ;
    private boolean isHead  = false;
    private int level;
    private String fullTitle;
    private boolean select;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isHead() {
        return isHead;
    }

    public void setHead(boolean head) {
        isHead = head;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public void setFullTitle(String fullTitle) {
        this.fullTitle = fullTitle;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    @Override
    public String toString() {
        return "ChapterInfo{" +
                "title='" + title + '\'' +
                ", position='" + position + '\'' +
                ", isHead=" + isHead +
                '}';
    }
}

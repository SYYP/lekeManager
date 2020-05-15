package www.manager.leke.com.lekemanager.bean;

import android.os.Build;
import android.os.Parcel;
import android.support.annotation.RequiresApi;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;
import java.util.Objects;

/**
 * Created by ypu
 * on 2020/5/7 0007
 */
public class xqBean extends  ExpandableGroup<Artist>  {

    private  int iconresId;
    private String pageNumber;

    public int getIconresId() {
        return iconresId;
    }

    public void setIconresId(int iconresId) {
        this.iconresId = iconresId;
    }

    public String getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(String pageNumber) {
        this.pageNumber = pageNumber;
    }

    public xqBean(String title, List<Artist> items, int iconresId, String pageNumber) {
        super(title, items);
        this.iconresId = iconresId;
        this.pageNumber = pageNumber;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        xqBean xqBean = (xqBean) o;
        return iconresId == xqBean.iconresId &&
                Objects.equals(pageNumber, xqBean.pageNumber);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {

        return Objects.hash(iconresId, pageNumber);
    }
}

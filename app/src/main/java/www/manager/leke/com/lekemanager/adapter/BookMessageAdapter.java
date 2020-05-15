package www.manager.leke.com.lekemanager.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.bean.Artist;
import www.manager.leke.com.lekemanager.utils.UIUtils;
import www.manager.leke.com.lekemanager.view.HeaderViewHolder;
import www.manager.leke.com.lekemanager.view.ItemViewHolder;

/**
 * Created by ypu
 * on 2020/5/7 0007
 */
public class BookMessageAdapter extends ExpandableRecyclerViewAdapter<HeaderViewHolder,ItemViewHolder> {
    public BookMessageAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
    }

    @Override
    public HeaderViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {

      View view= UIUtils.inflate(R.layout.item_xqhead);
        return new HeaderViewHolder(view);
    }

    @Override
    public ItemViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
       View view= UIUtils.inflate(R.layout.item_xqchild);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ItemViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Artist artist= (Artist) group.getItems().get(childIndex);
          holder.setData(artist.getName(),artist.getPageSize());
    }

    @Override
    public void onBindGroupViewHolder(HeaderViewHolder holder, int flatPosition, ExpandableGroup group) {

         holder.setXqBeanTitle(group);
    }
}

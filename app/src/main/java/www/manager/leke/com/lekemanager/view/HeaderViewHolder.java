package www.manager.leke.com.lekemanager.view;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.bean.xqBean;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

/**
 * Created by ypu
 * on 2020/5/7 0007
 */
public class HeaderViewHolder extends GroupViewHolder {
    private TextView mTextTitle, mTextNumber;
    private ImageView mIcon;

    public HeaderViewHolder(View itemView) {
        super(itemView);

        mTextTitle = itemView.findViewById(R.id.text_title);
        mTextNumber = itemView.findViewById(R.id.text_number);
        mIcon = itemView.findViewById(R.id.img_icon);
    }

    public void setXqBeanTitle(ExpandableGroup group) {

        if (group instanceof xqBean) {
            mTextTitle.setText(group.getTitle());
            mTextNumber.setText(((xqBean) group).getPageNumber());
            mIcon.setImageResource(((xqBean) group).getIconresId());
        }
    }
    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(0, 90, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(100);
        rotate.setFillAfter(true);
        mIcon.setAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(90, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(100);
        rotate.setFillAfter(true);
        mIcon.setAnimation(rotate);
    }
}

package www.manager.leke.com.lekemanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragment;
import www.manager.leke.com.lekemanager.utils.UIUtils;
import www.manager.leke.com.lekemanager.view.AlignTextView;

/**
 * Created by ypu
 * on 2020/5/7 0007
 */
public class JianjFragment extends BaseFragment {
    @BindView(R.id.text_introduction)
    AlignTextView textIntroduction;


    @Override
    public void setChildTag() {

    }

    @Override
    protected View getXmlView() {
        return UIUtils.inflate(R.layout.fragment_jianj);
    }

    @Override
    protected void initXmlData() {

    }

    @Override
    protected void loadData() {

    }

}

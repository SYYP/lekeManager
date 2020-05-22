package www.manager.leke.com.lekemanager.fragment;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.adapter.BookMessageAdapter;
import www.manager.leke.com.lekemanager.base.BaseFragment;
import www.manager.leke.com.lekemanager.bean.Artist;
import www.manager.leke.com.lekemanager.bean.BookMessageDetail;
import www.manager.leke.com.lekemanager.bean.xqBean;
import www.manager.leke.com.lekemanager.refresh.BaseRefreshLayout;
import www.manager.leke.com.lekemanager.refresh.DaisyRefreshLayout;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/5/7 0007
 */
public class MlFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    List<xqBean> xqBeans = new ArrayList<>();
    private BookMessageAdapter mBookMessageAdapter;
    private BookMessageDetail mMessageDetail;

    @Override
    public void setChildTag() { }
    @Override
    protected View getXmlView() {
        return UIUtils.inflate(R.layout.fragment_ml);
    }

    @Override
    protected void initXmlData() {
        Bundle arguments = getArguments();
        mMessageDetail = (BookMessageDetail) arguments.getSerializable(Contacts.BOOKDETAIL);
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void loadData() {
        BookMessageDetail.BookContentsBean bookContents= mMessageDetail.getBookContents();
        if(bookContents!=null) {
            List<BookMessageDetail.BookContentsBean.NodesBeanX> nodes = bookContents.getNodes();
            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
            if (nodes != null) {
                mBookMessageAdapter = new BookMessageAdapter(nodes, getContext());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(mBookMessageAdapter);
            }
        }

    }


}

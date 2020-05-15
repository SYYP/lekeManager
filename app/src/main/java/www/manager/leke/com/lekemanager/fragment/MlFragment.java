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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.adapter.BookMessageAdapter;
import www.manager.leke.com.lekemanager.base.BaseFragment;
import www.manager.leke.com.lekemanager.bean.Artist;
import www.manager.leke.com.lekemanager.bean.xqBean;
import www.manager.leke.com.lekemanager.refresh.BaseRefreshLayout;
import www.manager.leke.com.lekemanager.refresh.DaisyRefreshLayout;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/5/7 0007
 */
public class MlFragment extends BaseFragment {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout refreshLayout;
    List<xqBean> xqBeans = new ArrayList<>();
    private BookMessageAdapter mBookMessageAdapter;
    @Override
    public void setChildTag() { }
    @Override
    protected View getXmlView() {
        return UIUtils.inflate(R.layout.fragment_ml);
    }

    @Override
    protected void initXmlData() {
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void loadData() {

        //添加数据源
        for (int i = 0; i < 25; i++) {
            List<Artist> list = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                Artist artist = new Artist("小蝌蚪找爸爸" + j, "2" + j + "页");
                list.add(artist);
            }
            xqBean xqBean = new xqBean("第一章小学课本" + i, list, R.drawable.img_icon_left, i + "页");
            xqBeans.add(xqBean);
        }
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }
        mBookMessageAdapter = new BookMessageAdapter(xqBeans);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(mBookMessageAdapter);
        refreshLayout.setEnableRefresh(false);
        refreshLayout.setEnableAutoLoadMore(false);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                    }
//                }, 1000 * 3);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<xqBean> xqBeans1 = new ArrayList<>();
//                        for (int i = 0; i < 10; i++) {
//                            List<Artist> list = new ArrayList<>();
//                            for (int j = 0; j < 5; j++) {
//                                Artist artist = new Artist("小蝌蚪找爸爸" + j, "2" + j + "页");
//                                list.add(artist);
//                            }
//                            xqBean xqBean = new xqBean("第一章小学课本" + i, list, R.drawable.img_icon_left, i + "页");
//                            xqBeans1.add(xqBean);
//                        }
                        xqBean xqBean = new xqBean("第一章小学课本" + 0, new ArrayList<Artist>(), R.drawable.img_icon_left, 0 + "页");
                        xqBeans1.add(xqBean);
                        xqBeans.addAll(xqBeans1);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {
                                mBookMessageAdapter.notifyDataSetChanged();
                            }
                        });
                    }
                }).start();

                refreshLayout.finishLoadMore(3000);
            }
        });

    }


}

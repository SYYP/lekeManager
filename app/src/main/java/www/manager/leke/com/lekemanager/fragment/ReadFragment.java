package www.manager.leke.com.lekemanager.fragment;

import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.adapter.MainRecyclerAdapter;
import www.manager.leke.com.lekemanager.adapter.PointReadRecyclerAdapter;
import www.manager.leke.com.lekemanager.bean.BaseEvent;
import www.manager.leke.com.lekemanager.bean.MainBookMessageBean;
import www.manager.leke.com.lekemanager.http.ApiException;
import www.manager.leke.com.lekemanager.http.HttpManager;
import www.manager.leke.com.lekemanager.manager.AppManager;
import www.manager.leke.com.lekemanager.recycler.MyGridLayoutManager;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.NetUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/4/30 0030
 * 点读模块
 */
public class ReadFragment extends MainFragment {

    private String audioStatus = null;
    List<MainBookMessageBean> mMainBookMessageBeanList = new ArrayList<>();//数据源
    private PointReadRecyclerAdapter mPointReadRecyclerAdapter;

    @Override
    protected void getTextAll() {
         //全部的是默认null
        audioStatus=null;
        netWorkAudioData(audioStatus);
    }

    @Override
    protected void getTextbj() {
        //编辑穿 BT02,BT03
         audioStatus="[BT02,BT03]";
          netWorkAudioData(audioStatus);
    }

    @Override
    protected void getTextxdui() {
         //校对传BT05
        audioStatus=Contacts.BT05;
         netWorkAudioData(audioStatus);
    }

    @Override
    protected void getTextshengh() {
        //审核
        audioStatus = Contacts.BT09;
        netWorkAudioData(audioStatus);
    }

    @Override
    protected void getRefresh() {
          netWorkAudioData(audioStatus);
    }

    @Override
    protected void loadData() {
        MyGridLayoutManager myGridLayoutManager = new MyGridLayoutManager(AppManager.getAppContext(), 2);
        mPointReadRecyclerAdapter = new PointReadRecyclerAdapter(mMainBookMessageBeanList, getActivity());
        recyclerView.setLayoutManager(myGridLayoutManager);
        netWorkAudioData(audioStatus);
    }
    private void netWorkAudioData(String audioStatus) {
        if (NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getBookAudio(audioStatus).subscribe(new Action1<Pair<Integer, List<MainBookMessageBean>>>() {
                @Override
                public void call(Pair<Integer, List<MainBookMessageBean>> integerListPair) {
                    List<MainBookMessageBean> second = integerListPair.second;
                    if (second!=null&&second.isEmpty()) {
                        llFail.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    } else {
                        mMainBookMessageBeanList.clear();
                        mMainBookMessageBeanList.addAll(second);
                        recyclerView.setVisibility(View.VISIBLE);
                        llFail.setVisibility(View.GONE);
                        recyclerView.setAdapter(mPointReadRecyclerAdapter);
                    }

                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    if(throwable instanceof ApiException){
                        UIUtils.showToastSafe(throwable.getMessage());
                    }
                }
            });
        } else {
            UIUtils.showToastSafe(R.string.no_net);
        }
    }

    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        switch (event.getType()){
            case Contacts.BOOKREAD:
                netWorkAudioData(audioStatus);
                break;
        }
    }
}

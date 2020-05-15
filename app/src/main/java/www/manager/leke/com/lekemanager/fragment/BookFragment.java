package www.manager.leke.com.lekemanager.fragment;

import android.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.adapter.MainRecyclerAdapter;
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
 * 图书模块
 */
public class BookFragment extends MainFragment {
    List<MainBookMessageBean> mMainBookMessageBeanList = new ArrayList<>();//数据源
    private MainRecyclerAdapter mMainRecyclerAdapter;
    String bookStatus = null;  //请求时候需要的一个状态码值

    @Override
    protected void getTextAll() {

        bookStatus = null;
        NetworkBookMessage(bookStatus);

    }

    @Override
    protected void getTextbj() {
        //点击编辑时候需要传BA02,BA03
        bookStatus = "[BA02,BA03]";
        NetworkBookMessage(bookStatus);


    }

    @Override
    protected void getTextxdui() {
        //校对
        bookStatus = Contacts.Xiaodui;
        NetworkBookMessage(bookStatus);
    }

    @Override
    protected void getTextshengh() {
        //审核
        bookStatus = Contacts.EXAMINE;
        NetworkBookMessage(bookStatus);

    }

    @Override
    protected void getRefresh() {
        NetworkBookMessage(bookStatus);
    }

    @Override
    protected void loadData() {
        MyGridLayoutManager myGridLayoutManager = new MyGridLayoutManager(AppManager.getAppContext(), 2);
        mMainRecyclerAdapter = new MainRecyclerAdapter(mMainBookMessageBeanList, getActivity());
        recyclerView.setLayoutManager(myGridLayoutManager);

        //加载数据
        NetworkBookMessage(bookStatus);

    }

    private void NetworkBookMessage(String BookStatus) {
        if(NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getMainBookMessage(BookStatus)
                    .subscribe(new Action1<Pair<Integer, List<MainBookMessageBean>>>() {
                        @Override
                        public void call(Pair<Integer, List<MainBookMessageBean>> integerListPair) {
                            //拿到数据源
                            List<MainBookMessageBean> second = integerListPair.second;
                            if (!second.isEmpty()) {
                                includeFail.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                mMainBookMessageBeanList.clear();
                                mMainBookMessageBeanList.addAll(second);
                                recyclerView.setAdapter(mMainRecyclerAdapter);
                            } else {
                                includeFail.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.GONE);
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
        }else {
            UIUtils.showToastSafe(R.string.no_net);
        }
    }
}

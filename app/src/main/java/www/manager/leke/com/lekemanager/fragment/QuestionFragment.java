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
import www.manager.leke.com.lekemanager.adapter.QuestBankRecyclerAdapter;
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
 * 题库模块
 */
public class QuestionFragment extends MainFragment {
    Integer bookOptor =null;
    private List<MainBookMessageBean> mMainBookMessageBeanList=new ArrayList<>();//总数据源
    private QuestBankRecyclerAdapter mQuestBankRecyclerAdapter;

    @Override
    protected void getTextAll() {
        //点击全部 bookOptor=null
         bookOptor=null;
         netWorkQuestionData(bookOptor);
    }

    @Override
    protected void getTextbj() {
        //编辑时候传1
         bookOptor=1;
        netWorkQuestionData(bookOptor);
    }

    @Override
    protected void getTextxdui() {
           //校对时候传2、
            bookOptor=2;

        netWorkQuestionData(bookOptor);
    }

    @Override
    protected void getTextshengh() {
         //审核传 3
        bookOptor=3;
        netWorkQuestionData(bookOptor);
    }

    @Override
    protected void getRefresh() {
        netWorkQuestionData(bookOptor);
    }
    @Override
    protected void loadData() {
        MyGridLayoutManager myGridLayoutManager = new MyGridLayoutManager(AppManager.getAppContext(), 2);
        mQuestBankRecyclerAdapter = new QuestBankRecyclerAdapter(mMainBookMessageBeanList, getActivity());
        recyclerView.setLayoutManager(myGridLayoutManager);
        netWorkQuestionData(bookOptor);
    }

    private void netWorkQuestionData(Integer bookOptor) {
        if(NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getQuestionBank(bookOptor).subscribe(new Action1<Pair<Integer, List<MainBookMessageBean>>>() {
                @Override
                public void call(Pair<Integer, List<MainBookMessageBean>> integerListPair) {
                 List<MainBookMessageBean> mainBookMessageBeans=  integerListPair.second;
                 if(mainBookMessageBeans!=null&&mainBookMessageBeans.isEmpty()){
                      recyclerView.setVisibility(View.GONE);
                     llFail.setVisibility(View.VISIBLE);

                 }else {
                     mMainBookMessageBeanList.clear();
                     mMainBookMessageBeanList.addAll(mainBookMessageBeans);
                     recyclerView.setVisibility(View.VISIBLE);
                     llFail.setVisibility(View.GONE);
                     recyclerView.setAdapter(mQuestBankRecyclerAdapter);
                 }

                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {

                    if (throwable instanceof ApiException) {
                        UIUtils.showToastSafe(throwable.getMessage());
                    }
                }
            });
        }else {
            UIUtils.showToastSafe(R.string.no_net);
        }

    }

    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);
        switch (event.getType()){
            case Contacts.QUESTIONBANKS:
                netWorkQuestionData(bookOptor);
                break;
        }
    }
}

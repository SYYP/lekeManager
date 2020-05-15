package www.manager.leke.com.lekemanager.fragment;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import rx.functions.Action1;

import www.manager.leke.com.lekemanager.adapter.QuestBankRecyclerAdapter;
import www.manager.leke.com.lekemanager.bean.MainBookMessageBean;
import www.manager.leke.com.lekemanager.http.HttpManager;
import www.manager.leke.com.lekemanager.manager.AppManager;
import www.manager.leke.com.lekemanager.recycler.MyGridLayoutManager;

/**
 * Created by ypu
 * on 2020/4/30 0030
 * 题库模块
 */
public class QuestionFragment extends MainFragment {
    Integer bookOptor =null;
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
        netWorkQuestionData(bookOptor);
    }

    @Override
    protected void getRefresh() {
        netWorkQuestionData(bookOptor);
    }

    @Override
    protected void loadData() {
        netWorkQuestionData(bookOptor);
//        MyGridLayoutManager myGridLayoutManager = new MyGridLayoutManager(AppManager.getAppContext(), 2);
//        final QuestBankRecyclerAdapter questBankRecyclerAdapter = new QuestBankRecyclerAdapter(stringList, getActivity());
//        recyclerView.setLayoutManager(myGridLayoutManager);
//        recyclerView.setAdapter(questBankRecyclerAdapter);


    }

    private void netWorkQuestionData(int bookOptor) {
        HttpManager.getInstace().getQuestionBank(bookOptor).subscribe(new Action1<Pair<Integer, List<MainBookMessageBean>>>() {
            @Override
            public void call(Pair<Integer, List<MainBookMessageBean>> integerListPair) {

            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        });

    }
}

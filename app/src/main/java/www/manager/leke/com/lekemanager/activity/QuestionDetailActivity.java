package www.manager.leke.com.lekemanager.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frank.etude.pageable.PageBtnBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.adapter.BookMessageAdapter;
import www.manager.leke.com.lekemanager.adapter.QuestionMladapter;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.base.BaseResult;
import www.manager.leke.com.lekemanager.bean.BookMessageDetail;
import www.manager.leke.com.lekemanager.bean.QuestionListBean;
import www.manager.leke.com.lekemanager.http.ApiException;
import www.manager.leke.com.lekemanager.http.HttpManager;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.NetUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/5/11 0011
 */
public class QuestionDetailActivity extends BaseFragmentActivity {
    @BindView(R.id.img_back)
    TextView imgBack;
    @BindView(R.id.last_btn_text)
    TextView lastBtnText;
    @BindView(R.id.last_btn)
    LinearLayout lastBtn;
    @BindView(R.id.question_choose_btn_text)
    TextView questionChooseBtnText;
    @BindView(R.id.question_choose_btn)
    LinearLayout questionChooseBtn;
    @BindView(R.id.next_btn_text)
    TextView nextBtnText;
    @BindView(R.id.next_btn)
    LinearLayout nextBtn;
    @BindView(R.id.real_jix)
    RelativeLayout realJix;
    @BindView(R.id.real_ml)
    RelativeLayout realMl;
    @BindView(R.id.top_btn_bar1)
    RelativeLayout topBtnBar1;
    @BindView(R.id.color_block)
    View colorBlock;
    @BindView(R.id.start_time_tv)
    TextView startTimeTv;
    @BindView(R.id.spend_time_tv)
    TextView spendTimeTv;
    @BindView(R.id.btn_bar_3)
    LinearLayout btnBar3;
    @BindView(R.id.main_content_display)
    ImageView mainContentDisplay;
    @BindView(R.id.question_choose_rcv)
    RecyclerView questionChooseRcv;
    @BindView(R.id.rcv_chooese_item)
    RecyclerView rcvChooeseItem;
    @BindView(R.id.rb_error)
    RadioButton rbError;
    @BindView(R.id.rb_right)
    RadioButton rbRight;
    @BindView(R.id.ll_chooese_item)
    LinearLayout llChooeseItem;
    @BindView(R.id.answer_btn_bar)
    FrameLayout answerBtnBar;
    @BindView(R.id.page_btn_bar)
    PageBtnBar pageBtnBar;
    @BindView(R.id.img_refush)
    ImageView imgRefush;
    @BindView(R.id.tv_chapter)
    TextView tvChapter;
    @BindView(R.id.driver_chapter)
    View driverChapter;
    @BindView(R.id.fm_chapter)
    FrameLayout fmChapter;
    @BindView(R.id.ll_items)
    LinearLayout llItems;
    @BindView(R.id.item_driver)
    View itemDriver;
    @BindView(R.id.recycler_view_ml)
    RecyclerView recyclerViewMl;
    @BindView(R.id.fm_menu_right)
    FrameLayout fmMenuRight;
    private String mQstatusCode;
    private Integer mQBankBookId;
    HashMap<String, Integer> mHashQuestionNumber = new HashMap<>();//题干数量数据源
    QuestionMladapter mBookMessageAdapter;
    List<BookMessageDetail.BookContentsBean.NodesBeanX> mNodesBeanXList = new ArrayList<>();//目录数据源
    private LinearLayoutManager mLinearLayoutManager;

    @Override
    public void init() {

    }


    @Override
    public void processExtraData() {
        super.processExtraData();
        mQstatusCode = getIntent().getStringExtra(Contacts.STATUSCODE);
        mQBankBookId = getIntent().getIntExtra(Contacts.BOOKID, 0);


    }


    @Override
    public View layout() {
        return inflate(R.layout.activity_answer_detail_list);
    }

    @Override
    public void loadData() {
        networkQustMessage();
        //网络请求
        mLinearLayoutManager = new LinearLayoutManager(QuestionDetailActivity.this);
        if (mBookMessageAdapter == null) {
            mBookMessageAdapter = new QuestionMladapter(mNodesBeanXList, QuestionDetailActivity.this, mHashQuestionNumber);
            recyclerViewMl.setLayoutManager(mLinearLayoutManager);
        }
        recyclerViewMl.setAdapter(mBookMessageAdapter);
    }

    private void getQuestionNumber() {
        if (NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getQuestionAmount(mQBankBookId, mQstatusCode).subscribe(new Action1<HashMap<String, Integer>>() {
                @Override
                public void call(HashMap<String, Integer> map) {
                    if (map != null) {
                        mHashQuestionNumber.clear();
                        mHashQuestionNumber.putAll(map);

                    }
                    mBookMessageAdapter.notifyDataSetChanged();

                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    if (throwable instanceof ApiException)
                        UIUtils.showToastSafe(throwable.getMessage());
                }
            });
        } else {
            UIUtils.showToastSafe(R.string.no_net);
        }
    }

    @Override
    protected void dimissHtpp() {

    }

    @OnClick({R.id.img_back, R.id.img_refush, R.id.real_ml, R.id.fm_menu_right})
    protected void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_refush:
                break;
            case R.id.real_ml:
                fmMenuRight.setVisibility(View.VISIBLE);
                //获取题数量
                getQuestionNumber();
                break;
            case R.id.fm_menu_right:
                fmMenuRight.setVisibility(View.GONE);
                break;
            default:
                break;
        }
    }

    /**
     * 获取目录
     */
    private void networkQustMessage() {
        if (NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getBookmessageDetail(mQBankBookId).subscribe(new Action1<BookMessageDetail>() {
                @Override
                public void call(BookMessageDetail bookMessageDetail) {
                    if (bookMessageDetail != null) {
                        if (bookMessageDetail.getBookContents() != null) {
                            List<BookMessageDetail.BookContentsBean.NodesBeanX> nodes = bookMessageDetail.getBookContents().getNodes();
                            if (nodes != null) {
                                mNodesBeanXList.clear();
                                mNodesBeanXList.addAll(nodes);
                                //拿到数据后开始获取题目目录
                                getNetworkQuestionList(nodes.get(0).getId());

                            }

                        }
                    }
                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {

                    if (throwable instanceof ApiException) {
                        UIUtils.showToastSafe(R.string.no_net);
                    }
                }
            });

        } else {
            UIUtils.showToastSafe(R.string.no_net);
        }
    }

    /**
     * 获取题目列表
     *
     * @param chapterId
     */
    private void getNetworkQuestionList(int chapterId) {
        if (NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getQuestionList(mQBankBookId, chapterId, mQstatusCode)
                    .subscribe(new Action1<Pair<Integer, BaseResult<List<QuestionListBean>>>>() {
                        @Override
                        public void call(Pair<Integer, BaseResult<List<QuestionListBean>>> integerBaseResultPair) {
                            if (integerBaseResultPair != null) {
                                Integer total = integerBaseResultPair.first;
                                List<QuestionListBean> questionListBeanList = (List<QuestionListBean>) integerBaseResultPair.second;
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
        } else {
            UIUtils.showToastSafe(R.string.no_net);
        }
    }


}

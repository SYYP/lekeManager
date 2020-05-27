package www.manager.leke.com.lekemanager.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frank.etude.pageable.PageBtnBar;
import com.frank.etude.pageable.PageBtnBarAdapter;
import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.reader.api.ReaderDocumentTableOfContent;
import com.yanzhenjie.nohttp.Headers;
import com.yanzhenjie.nohttp.download.DownloadListener;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.adapter.QuestionMladapter;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.bean.BookMessageDetail;
import www.manager.leke.com.lekemanager.bean.QuestionListBean;
import www.manager.leke.com.lekemanager.http.ApiException;
import www.manager.leke.com.lekemanager.http.HttpManager;
import www.manager.leke.com.lekemanager.interfaces.OnItemClickListener;
import www.manager.leke.com.lekemanager.manager.AppManager;
import www.manager.leke.com.lekemanager.manager.DownLoadTopicManager;
import www.manager.leke.com.lekemanager.read.ReaderContract;
import www.manager.leke.com.lekemanager.read.ReaderPresenter;
import www.manager.leke.com.lekemanager.reading.ReadingBean;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.MD5Utils;
import www.manager.leke.com.lekemanager.utils.NetUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;
import www.manager.leke.com.lekemanager.view.CustomGridLayoutManager;

/**
 * Created by ypu
 * on 2020/5/11 0011
 */
public class QuestionDetailActivity extends BaseFragmentActivity implements ReaderContract.ReaderView {
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
    @BindView(R.id.real_all)
    RelativeLayout realAll;
    @BindView(R.id.img_close)
    ImageView imgClose;
    @BindView(R.id.fram_jiex)
    FrameLayout framJiex;
    @BindView(R.id.img_jiex)
    ImageView imgJiex;
    @BindView(R.id.img_fail)
    ImageView imgFail;
    @BindView(R.id.tv_failMsg)
    TextView tvFailMsg;
    @BindView(R.id.ll_fail)
    LinearLayout llFail;
    private String mQstatusCode;
    private Integer mQBankBookId;
    HashMap<String, Integer> mHashQuestionNumber = new HashMap<>();//题干数量数据源
    QuestionMladapter mBookMessageAdapter;
    List<BookMessageDetail.BookContentsBean.NodesBeanX> mNodesBeanXList = new ArrayList<>();//目录数据源
    private LinearLayoutManager mLinearLayoutManager;
    private HomeWorkPageNumAdapter homeWorkPageNumAdapter;
    private int showHomeWorkPosition = 0;//记录当前章节数
    private int homeWorkPageSize = 0;//总章节数
    private List<QuestionListBean> mQuestionListBeanList;
    private DownLoadTopicManager mDownLoadTopicManager;
    ReaderPresenter mReaderPresenter;
    private QuestionListBean mQuestionListBean;//当前题干
    boolean isJxi = false;//是否是解析的

    @Override
    public void init() {
        //初始化
        initSubject();
        initListener();
    }


    @Override
    public void processExtraData() {
        super.processExtraData();
        mQstatusCode = getIntent().getStringExtra(Contacts.STATUSCODE);
        mQBankBookId = getIntent().getIntExtra(Contacts.BOOKID, 0);


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //用来在点击其他地方时隐藏[题目选择recyclerview].
        if (ev.getAction() == MotionEvent.ACTION_DOWN
                && questionChooseRcv.getVisibility() == View.VISIBLE) {
            Rect rect = new Rect();
            questionChooseRcv.getGlobalVisibleRect(rect);
            if (ev.getRawX() < rect.left
                    || ev.getRawX() > rect.right
                    || ev.getRawY() < rect.top
                    || ev.getRawY() > rect.bottom
                    ) {
                questionChooseRcv.setVisibility(View.GONE);
                return true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public View layout() {
        return inflate(R.layout.activity_answer_detail_list);
    }

    @Override
    public void loadData() {
        //网络请求
        mLinearLayoutManager = new LinearLayoutManager(QuestionDetailActivity.this);
        if (mBookMessageAdapter == null) {
            mBookMessageAdapter = new QuestionMladapter(mNodesBeanXList, QuestionDetailActivity.this, mHashQuestionNumber);
            recyclerViewMl.setLayoutManager(mLinearLayoutManager);
            recyclerViewMl.setAdapter(mBookMessageAdapter);

        }
        networkQustMessage();
        mBookMessageAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick1(int position) {
                fmMenuRight.setVisibility(View.GONE);
                getNetworkQuestionList(position);
            }
        });
    }


    @Override
    protected void dimissHtpp() {
    }

    @Override
    public void updatePage(int page, Bitmap bitmap, ReadingBean readInfo) {
        System.out.println("当前图书页码：page：" + page);
        mainContentDisplay.setImageBitmap(bitmap);
        pageBtnBar.refreshPageBar();
    }

    @Override
    public View getContentView() {
        if (isJxi) {
            return imgJiex;
        } else {
            return mainContentDisplay;
        }

    }

    @Override
    public void showThrowable(Throwable throwable) {
        throwable.printStackTrace();
    }

    @Override
    public void openDocumentFinsh() {
        getReaderPresenter().gotoPage(0);
    }

    @Override
    public void updateDirectory(ReaderDocumentTableOfContent content) {

    }

    @Override
    public Context getViewContext() {
        return this;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        close();
    }

    @OnClick({R.id.img_back, R.id.img_refush, R.id.real_ml, R.id.fm_menu_right, R.id.last_btn, R.id.question_choose_btn,
            R.id.next_btn, R.id.real_jix, R.id.img_close})
    protected void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.img_refush:
                EpdController.invalidate(realAll, UpdateMode.GC);
                break;
            case R.id.real_ml:
                fmMenuRight.setVisibility(View.VISIBLE);
                //获取题数量

                break;
            case R.id.fm_menu_right:
                fmMenuRight.setVisibility(View.GONE);
                break;
            case R.id.last_btn:
                if (showHomeWorkPosition > 0) {
                    showHomeWorkPosition--;
                    homeWorkPageNumAdapter.onItemClickListener.onItemClick1(showHomeWorkPosition);
                } else {
                    UIUtils.showToastSafe("已经是第一题了");
                }
                break;
            case R.id.question_choose_btn://recycieView 显示与隐藏

                if (questionChooseRcv.getVisibility() == View.VISIBLE) {
                    questionChooseRcv.setVisibility(View.GONE);
                } else {
                    questionChooseRcv.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.next_btn:
                if (showHomeWorkPosition < homeWorkPageSize - 1) {
                    showHomeWorkPosition++;
                    homeWorkPageNumAdapter.onItemClickListener.onItemClick1(showHomeWorkPosition);
                } else {
                    UIUtils.showToastSafe("已经是最后一题了");
                }

                break;
            case R.id.real_jix:
                if(mQuestionListBean!=null) {
                    isJxi = true;
                    framJiex.setVisibility(View.VISIBLE);
                    downAndOpenPdf(mQuestionListBean.getAnalysisLink());
                }
                break;
            case R.id.img_close:
                framJiex.setVisibility(View.GONE);
                downAndOpenPdf(TextUtils.isEmpty(mQuestionListBean.getQStemColorLink()) ? mQuestionListBean.getQStemLink() : mQuestionListBean.getQStemColorLink());
                break;

            default:
                break;
        }
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

    private void initListener() {
        pageBtnBar.setPageBarAdapter(new PageBtnBarAdapter(mContext) {
            @Override
            public int getPageBtnCount() {
                return getPages();
            }

            @Override
            public void onPageBtnClick(View btn, int btnIndex, String textInBtn) {

                gotoPage(btnIndex);
            }
        });

    }

    private void initSubject() {
        //作业题目切换
        homeWorkPageNumAdapter = new HomeWorkPageNumAdapter();
        CustomGridLayoutManager gridLayoutManager = new CustomGridLayoutManager(this, 8);
        gridLayoutManager.setScrollEnabled(false);
        questionChooseRcv.setLayoutManager(gridLayoutManager);
        questionChooseRcv.setAdapter(homeWorkPageNumAdapter);
        homeWorkPageNumAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick1(int position) {
                showHomeWorkPosition = position;
                homeWorkPageNumAdapter.notifyDataSetChanged();
                questionChooseRcv.setVisibility(View.GONE);
                questionChooseBtnText.setText((showHomeWorkPosition + 1) + "/" + homeWorkPageSize);
                //拿到当前题目bean
                mQuestionListBean = mQuestionListBeanList.get(showHomeWorkPosition);
                if (mQuestionListBean != null) {
                    downAndOpenPdf(TextUtils.isEmpty(mQuestionListBean.getQStemColorLink()) ? mQuestionListBean.getQStemLink() : mQuestionListBean.getQStemColorLink());
                } else {
                    UIUtils.showToastSafe("该题不存在");
                }
            }
        });
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
                                getQuestionNumber();
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
                    .subscribe(new Action1<Pair<Integer, List<QuestionListBean>>>() {
                        @Override
                        public void call(Pair<Integer, List<QuestionListBean>> integerBaseResultPair) {
                            if (integerBaseResultPair != null) {
                                Integer total = integerBaseResultPair.first;
                                mQuestionListBeanList = (List<QuestionListBean>) integerBaseResultPair.second;
                                if (mQuestionListBeanList != null && mQuestionListBeanList.size() > 0) {
                                    homeWorkPageSize = mQuestionListBeanList.size();
                                    homeWorkPageNumAdapter.onItemClickListener.onItemClick1(showHomeWorkPosition);
                                    llFail.setVisibility(View.GONE);
                                } else {
                                    llFail.setVisibility(View.VISIBLE);
                                    tvFailMsg.setText("该章节下没有习题!");
                                }
                            } else {
                                UIUtils.showToastSafe("该章节没有题，请选择其他章节");
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


    /***
     * 切换图书页码
     * @param page
     */
    private void gotoPage(int page) {

        if (mReaderPresenter != null) {

            mReaderPresenter.gotoPage(page);
        }


    }

    /**
     * 返回打开PDF的总页码
     *
     * @return
     */
    private int getPages() {

        if (mReaderPresenter != null) {
            return mReaderPresenter.getTotals();
        }
        return 0;
    }


    class HomeWorkPageNumAdapter extends RecyclerView.Adapter<HomeWorkPageNumViewHolder> {

        OnItemClickListener onItemClickListener;

        public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
            this.onItemClickListener = onItemClickListener;
        }

        @Override
        public HomeWorkPageNumViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_page_check_homework, parent, false);
            return new HomeWorkPageNumViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(HomeWorkPageNumViewHolder holder, final int position) {


            holder.mTvPageId.setText((position + 1) + "");


            if (position == showHomeWorkPosition) {
                holder.mTvPageId.setBackgroundResource(R.drawable.img_timu_cuowu);
                holder.mTvPageId.setTextColor(getResources().getColor(android.R.color.white));
            } else {
                holder.mTvPageId.setBackgroundResource(R.drawable.img_timu_chooese);
                holder.mTvPageId.setTextColor(getResources().getColor(android.R.color.black));
            }


            holder.mTvPageId.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick1(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return homeWorkPageSize;
        }
    }

    /*顶部页面的adapter相关*/
    class HomeWorkPageNumViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_page_id)
        TextView mTvPageId;


        public HomeWorkPageNumViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    private void downAndOpenPdf(String originalPdfUrl) {

        String tmpPdfUrl = originalPdfUrl.substring(0, originalPdfUrl.lastIndexOf("/") + 1);
        String tmpPdfUrl2 = originalPdfUrl.substring(originalPdfUrl.lastIndexOf("/") + 1);

        final String pdfUrl = tmpPdfUrl + URLEncoder.encode(tmpPdfUrl2);
        final String mpdfUrl = pdfUrl.startsWith("http") ? pdfUrl : "http:" + pdfUrl;

        System.out.println("url编码过的地址：" + mpdfUrl);
        if (mDownLoadTopicManager == null) {
            mDownLoadTopicManager = new DownLoadTopicManager();
        }
        //TODO下载PDF
        mDownLoadTopicManager.loadExercises(mpdfUrl, new DownloadListener() {
            @Override
            public void onDownloadError(int what, Exception exception) {
                UIUtils.showToastSafe("下载题目失败");
                exception.printStackTrace();
                close(); //先关闭掉之前的PDF
                if (isJxi) {
                    mainContentDisplay.setImageDrawable(createDrawable(960, 1148, (showHomeWorkPosition + 1) + " 下载解析失败"));
                } else {
                    mainContentDisplay.setImageDrawable(createDrawable(960, 1148, (showHomeWorkPosition + 1) + " 下载题目失败"));
                }
                isJxi = false;
            }

            @Override
            public void onStart(int what, boolean isResume, long rangeSize, Headers responseHeaders, long allCount) {
                System.out.println("onStart");
                UIUtils.showToastSafe("下载中...");
            }

            @Override
            public void onProgress(int what, int progress, long fileCount, long speed) {
                System.out.println("progress" + progress);
            }

            @Override
            public void onFinish(int what, final String filePath) {

                if (mForegroundActivity instanceof QuestionDetailActivity) {
                    System.out.println("onFinish" + filePath);
                    UIUtils.showToastSafe("加载中...");
                    close(); //先关闭掉之前的PDF
                    isJxi = false;
                    AppManager.postDelayedHandler(new Runnable() {
                        @Override
                        public void run() {
                            String exercisesId = MD5Utils.MD5Encode(pdfUrl);
                            getReaderPresenter().openDocument(filePath, exercisesId);
                            pageBtnBar.setCurrentSelectPageIndex(0);
                        }
                    }, 300);
                }
            }

            @Override
            public void onCancel(int what) {
                System.out.println("onCancel" + "onCancel");
            }
        });
    }

    /***
     * 切换PDF后需要调用此函数 关闭资源
     */
    private void close() {
        if (mReaderPresenter != null) {
            mReaderPresenter.close();
            mReaderPresenter = null;
        }
    }

    // 穿件带字母的标记图片
    private Drawable createDrawable(int width, int height, String str) {
        Bitmap imgTemp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(imgTemp);
        Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG
                | Paint.DEV_KERN_TEXT_FLAG);
        textPaint.setTextSize(30.0f);
        textPaint.setTypeface(Typeface.DEFAULT_BOLD); // 采用默认的宽度
        textPaint.setColor(Color.BLACK);

        canvas.drawText(str, width / 2 - 65, height / 2,
                textPaint);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return (Drawable) new BitmapDrawable(getResources(), imgTemp);

    }

    /////////////////////////////阅读///////////////////////////////
    private ReaderContract.ReaderPresenter getReaderPresenter() {
        if (mReaderPresenter == null) {
            mReaderPresenter = new ReaderPresenter(QuestionDetailActivity.this);
            mReaderPresenter.setIsGama(false);
        }
        return mReaderPresenter;
    }
}

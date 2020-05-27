package www.manager.leke.com.lekemanager.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;
import com.onyx.android.sdk.reader.api.ReaderDocumentTableOfContent;
import com.onyx.android.sdk.reader.api.ReaderDocumentTableOfContentEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.bean.ChapterInfo;
import www.manager.leke.com.lekemanager.bean.OpenBookInfo;
import www.manager.leke.com.lekemanager.configuration.HtppConfiguration;
import www.manager.leke.com.lekemanager.dialog.DialogUtils;
import www.manager.leke.com.lekemanager.dialog.LoadingProgressDialog;
import www.manager.leke.com.lekemanager.manager.AudioHelper;
import www.manager.leke.com.lekemanager.manager.OpenReaderUtils;
import www.manager.leke.com.lekemanager.read.ReaderContract;
import www.manager.leke.com.lekemanager.read.ReaderPresenter;
import www.manager.leke.com.lekemanager.reading.ReadingBean;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.DeviceTypeGlobal;
import www.manager.leke.com.lekemanager.utils.FileUtil;
import www.manager.leke.com.lekemanager.utils.LogUtils;
import www.manager.leke.com.lekemanager.utils.StringUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;
import www.manager.leke.com.lekemanager.view.AudioControllerView;
import www.manager.leke.com.lekemanager.view.BookMenuView;
import www.manager.leke.com.lekemanager.view.MoveView;
import www.manager.leke.com.lekemanager.view.PdfSurfaceView;
import www.manager.leke.com.lekemanager.view.ReadSeekBar;

/**
 * Created by ypu
 * on 2020/5/11 0011
 */
public class ReadPdfBookActivity extends BaseFragmentActivity implements ReaderContract.ReaderView {
    @BindView(R.id.img_back)
    TextView imgBack;
    @BindView(R.id.real_more)
    RelativeLayout realMore;
    @BindView(R.id.real_ml)
    RelativeLayout realMl;
    @BindView(R.id.top_btn_bar1)
    RelativeLayout topBtnBar1;
    @BindView(R.id.img_pdf_update)
    TextView imgPdfUpdate;
    @BindView(R.id.img_read_update)
    TextView imgReadUpdate;
    @BindView(R.id.img_read_position)
    TextView imgReadPosition;
    @BindView(R.id.liner_more)
    LinearLayout linerMore;
    boolean isFirst = true;
    @BindView(R.id.read_seekBar)
    ReadSeekBar readSeekBar;
    @BindView(R.id.fm_sliding_up)
    FrameLayout flFragment;
    @BindView(R.id.img_refush)
    ImageView imgRefush;
    @BindView(R.id.bookMenuView)
    BookMenuView bookMenuView;
    @BindView(R.id.audioControllerView)

    AudioControllerView audioControllerView;
    @BindView(R.id.fm_add_child)
    FrameLayout mFmAddChildLayout;
    @BindView(R.id.imgBtn_backPage)
    ImageButton imgBtn_backPage;
    @BindView(R.id.imgBtn_NextPage)
    ImageButton imgBtn_NextPage;
    @BindView(R.id.tv_hide_topAndSeek)
    TextView tv_hide_topAndSeek;
    @BindView(R.id.img_move)
    MoveView imgMove;

    @BindView(R.id.tv_topLocal)
    TextView tv_topLocal;
    @BindView(R.id.tv_local)
    TextView tvLocal;
    @BindView(R.id.img_read_voice)
    TextView imgReadVoice;
    @BindView(R.id.img_btn_reduce)
    ImageButton imgBtnReduce;
    @BindView(R.id.et_voice)
    EditText etVoice;
    @BindView(R.id.img_btn_enlarge)
    ImageButton imgBtnEnlarge;
    @BindView(R.id.ll_voice)
    LinearLayout llVoice;
    private DialogUtils mDialogUtils;
    private View mView;
    private int mBookId;
    private OpenBookInfo mOpenBookInfo;
    ImageView mOnyxImgView;
    PdfSurfaceView mSurfaceViewOnyxImgView;
    private LoadingProgressDialog mLoadingPdf;
    private ReaderPresenter mReaderPresenter;
    private ReadingBean mReadingBean;
    private Bitmap mCurrentPdfBitmap;
    private int mCurrentPage;
    private int mTotalPage;
    private String mLogTag;
    private List<ChapterInfo> mChapterInfos = new ArrayList<>();
    private SparseArray<ImageView> mImgReaders = new SparseArray<>();
    private float mRowX = 0;
    private float mRowY = 0;
    private int mReaderImageW = 0;
    private int mReaderImageH = 0;
    private boolean isFullScreen = false;
    private RelativeLayout.LayoutParams mLayoutParams;
    boolean isRead = false;
    private AudioHelper mAudioHelper;
    @Override
    public void init() {

        mLogTag = ReadPdfBookActivity.class.getName();
        initListener();
        addPdfView();
        initAudioController();
        addReadPosition();
        if(mOpenBookInfo!=null){
            if(Contacts.POSITION.equals(mOpenBookInfo.getBookSrcode())){

                changmenu();
            }
        }
    }

    private void addReadPosition() {
        imgMove.setUpLocalListener(new MoveView.UpdataLocalListener() {
            @Override
            public void onUpLocalListener(int left, int top) {
                tvLocal.setText("viewLeft:" + left + "\r\n" + "viewTop:" + top);
                mLayoutParams = (RelativeLayout.LayoutParams) tvLocal.getLayoutParams();
                tv_topLocal.setText("viewLeft:" + left + "viewTop:" + top);
                mLayoutParams.leftMargin = left + 35;
                mLayoutParams.topMargin = top;
                tvLocal.setLayoutParams(mLayoutParams);


            }
        });
    }

    private void initListener() {
        readSeekBar.setOnPageChangeListener(new ReadSeekBar.OnPageChangeListener() {
            @Override
            public void onPageChange(int page) {
                LogUtils.e("readSeekBar onPageChange page = " + page);
                getReaderPresenter().gotoPage(page);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void leaveWrite() {
            }

            @Override
            public void intoWrite() {

            }
        });

        bookMenuView.setOnMenuChangeListener(new BookMenuView.OnMenuChangeListener() {
            @Override
            public void onPageChange(int page) {

                LogUtils.e("readSeekBar onPageChange page = " + page);
                getReaderPresenter().gotoPage(page);
            }

            @Override
            public void intoWrite() {

            }

            @Override
            public void onItemDeleteClick(int page) {
            }

        });
        etVoice.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {

                    String str = etVoice.getText().toString().trim();
                    if (!StringUtils.isEmpty(str)) {
                        int voice = (Integer.parseInt(str));
                        getAudioHelper().setCutterVoice(voice);
                        setVoiceUiState();
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                    }
                    return true;
                }
                return false;
            }
        });
        setVoiceUiState();
    }

    //加载PDf
    private void addPdfView() {
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        if (HtppConfiguration.DEVICE_MODEL.equalsIgnoreCase(HtppConfiguration.DEVICE_MODEL)) {
            mOnyxImgView = new ImageView(this);
            flFragment.addView(mOnyxImgView, params);
        } else {
            mSurfaceViewOnyxImgView = new PdfSurfaceView(this);
            flFragment.addView(mSurfaceViewOnyxImgView, params);
        }
    }

    @Override
    public View layout() {
        mView = inflate(R.layout.activity_read_pdf);
        return mView;
    }

    @Override
    public void loadData() {
        getLoadingPdf().show();
        getLoadingPdf().setTitle("加载图书中，请稍后..");
        getReaderPresenter().openDocument(FileUtil.getReaderPath() + mOpenBookInfo.getBookId() + ".pdf", String.valueOf(mOpenBookInfo.getBookId()));
        getReaderPresenter().getDirectory();
    }

    private LoadingProgressDialog getLoadingPdf() {
        if (mLoadingPdf == null) {
            mLoadingPdf = new LoadingProgressDialog(this);
        }

        return mLoadingPdf;
    }

    private ReaderContract.ReaderPresenter getReaderPresenter() {
        if (mReaderPresenter == null) {
            mReaderPresenter = new ReaderPresenter(this);
            mReaderPresenter.setIsGama(false);
            mReaderPresenter.setIsEnlargeAndTailoring(false);
        }
        return mReaderPresenter;
    }

    @Override
    protected void dimissHtpp() {
    }

    @Override
    public void processExtraData() {
        super.processExtraData();
        mOpenBookInfo = Objects.requireNonNull(getIntent().getExtras()).getParcelable(ReadPdfBookActivity.class.getName());

    }

    @OnClick({R.id.real_more, R.id.real_ml, R.id.img_read_update, R.id.img_read_position,
            R.id.img_pdf_update, R.id.img_back, R.id.img_refush, R.id.tv_hide_topAndSeek,
            R.id.imgBtn_NextPage, R.id.imgBtn_backPage, R.id.img_read_voice,R.id.img_btn_reduce,R.id.img_btn_enlarge})
    protected void OnClick(View view) {

        switch (view.getId()) {

            case R.id.real_more:  //更多
                if (isFirst) {
                    linerMore.setVisibility(View.VISIBLE);
                    isFirst = false;
                } else {
                    linerMore.setVisibility(View.GONE);
                    isFirst = true;
                }
                break;
            case R.id.real_ml:
                bookMenuView.showChapter(mCurrentPage);
                break;

            case R.id.img_pdf_update://pdf更新
                //先去接口差是否有更新如果有弹窗提示有，没有弹窗提示没有
                if (mOpenBookInfo != null) {
                    new OpenReaderUtils(new OpenBookInfo().setBookId(mOpenBookInfo.getBookId()).setProgressg(true).setAutoOpean(false)).openReader();
                } else {
                    UIUtils.showToastSafe("图书有误!");
                }
                break;
            case R.id.img_read_update://点读更新
                if (mOpenBookInfo != null) {
                    new OpenReaderUtils(new OpenBookInfo().setBookId(mOpenBookInfo.getBookId()).setProgressg(true).setAutoOpean(false).setType(Contacts.READ)).openReader();
                } else {
                    UIUtils.showToastSafe("图书有误!");
                }
                break;
            case R.id.img_read_position://点读位置查阅
                changmenu();
                if (linerMore.getVisibility() == View.VISIBLE) {
                    linerMore.setVisibility(View.GONE);
                } else {
                    linerMore.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.img_back:
                finish();
                break;
            case R.id.img_refush:
                EpdController.invalidate(mView, UpdateMode.GC);
                break;
            case R.id.tv_hide_topAndSeek:
                changeScreenStatus();
                break;
            case R.id.imgBtn_backPage:
                if (mCurrentPage == 0)
                    return;
                mCurrentPage--;
                getReaderPresenter().gotoPage(mCurrentPage);
                break;

            case R.id.imgBtn_NextPage:
                if (mCurrentPage == mTotalPage - 1)
                    return;
                mCurrentPage++;
                getReaderPresenter().gotoPage(mCurrentPage);
                break;
            case R.id.img_read_voice:
                if (llVoice.getVisibility() == View.VISIBLE)
                    llVoice.setVisibility(View.GONE);
                else
                    llVoice.setVisibility(View.VISIBLE);

                break;
            case R.id.img_btn_reduce:
                getAudioHelper().setReduceVoice();
                setVoiceUiState();
                break;
            case R.id.img_btn_enlarge:
                getAudioHelper().setEnlargeVoice();
                setVoiceUiState();
                break;
        }
    }

    private AudioHelper getAudioHelper() {
        if (mAudioHelper == null) {
            mAudioHelper = new AudioHelper(ReadPdfBookActivity.this);
        }
        return mAudioHelper;
    }
    private void changmenu() {
        if (!isRead) {
            isRead = true;
            imgReadPosition.setText("查看预览");
            imgMove.setVisibility(View.VISIBLE);
            audioControllerView.setVisibility(View.GONE);
            mFmAddChildLayout.setVisibility(View.GONE);
            tvLocal.setVisibility(View.VISIBLE);
            tv_topLocal.setVisibility(View.VISIBLE);
        } else {
            tvLocal.setVisibility(View.GONE);
            tv_topLocal.setVisibility(View.GONE);
            imgMove.setVisibility(View.GONE);
            audioControllerView.setVisibility(View.VISIBLE);
            mFmAddChildLayout.setVisibility(View.VISIBLE);
            isRead = false;
            imgReadPosition.setText("点读位置查阅");
        }
    }

    private void changeScreenStatus() {
        if (isFullScreen) {
            tvLocal.setVisibility(View.VISIBLE);
            tv_topLocal.setVisibility(View.VISIBLE);
            intoNormalScreen();
        } else {
            tv_topLocal.setVisibility(View.GONE);
            tvLocal.setVisibility(View.GONE);
            intoFullScreen();
        }
    }

    private void intoNormalScreen() {
        isFullScreen = false;
        readSeekBar.setVisibility(View.VISIBLE);
        imgBtn_backPage.setVisibility(View.GONE);
        imgBtn_NextPage.setVisibility(View.GONE);
        topBtnBar1.setVisibility(View.VISIBLE);
        tv_hide_topAndSeek.setText("隐藏");
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_hide_topAndSeek.getLayoutParams();
        params.bottomMargin = UIUtils.getDimension(R.dimen.y120);
        tv_hide_topAndSeek.setLayoutParams(params);
    }

    private void intoFullScreen() {
        isFullScreen = true;
        topBtnBar1.setVisibility(View.GONE);
        readSeekBar.setVisibility(View.GONE);
        tv_hide_topAndSeek.setVisibility(View.VISIBLE);
        tv_hide_topAndSeek.setText("显示");
        if (DeviceTypeGlobal.DEVICE_TYPE_SYSTEM.equalsIgnoreCase(DeviceTypeGlobal.DEVICE_TYPE_PL107) || DeviceTypeGlobal.DEVICE_TYPE_SYSTEM.equalsIgnoreCase(DeviceTypeGlobal.DEVICE_TYPE_EDU)) {
            imgBtn_backPage.setVisibility(View.VISIBLE);
            imgBtn_NextPage.setVisibility(View.VISIBLE);
        }
        audioControllerView.getLlController().setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) tv_hide_topAndSeek.getLayoutParams();
        params.bottomMargin = UIUtils.getDimension(R.dimen.y15);
        tv_hide_topAndSeek.setLayoutParams(params);
    }

    @Override
    public void updatePage(int page, Bitmap bitmap, ReadingBean readInfo) {
        mReadingBean = readInfo;
        mCurrentPdfBitmap = bitmap;
        setCurrentPdfBitmap();
        mCurrentPage = page;
        readSeekBar.setCurrentPage(page);
        mFmAddChildLayout.removeAllViews();
        if (readInfo != null) {
            addReaderPoint(mReadingBean);
        }
    }

    private void setCurrentPdfBitmap() {
        if (DeviceTypeGlobal.DEVICE_TYPE_SYSTEM.equalsIgnoreCase(DeviceTypeGlobal.DEVICE_TYPE_PL107)) {
            mOnyxImgView.setImageBitmap(mCurrentPdfBitmap);
        } else {
            mSurfaceViewOnyxImgView.setSurfaceViewPdfBitmap(mCurrentPdfBitmap, mView);
        }
    }

    @Override
    public View getContentView() {
        if (DeviceTypeGlobal.DEVICE_TYPE_SYSTEM.equalsIgnoreCase(DeviceTypeGlobal.DEVICE_TYPE_PL107)) {
            return mOnyxImgView;
        } else {
            return mSurfaceViewOnyxImgView;
        }
    }

    @Override
    public void showThrowable(Throwable throwable) {

        UIUtils.showToastSafe("打开图书失败", Toast.LENGTH_LONG);
        finish();
    }

    @Override
    public void openDocumentFinsh() {

        mTotalPage = mReaderPresenter.getTotals();
        getLoadingPdf().dismiss();
        readSeekBar.setTotalPage(mTotalPage, ReadPdfBookActivity.this);
        getReaderPresenter().gotoPage(0);
    }

    @Override
    public void updateDirectory(ReaderDocumentTableOfContent content) {
        if (content != null && !content.isEmpty() && content.getRootEntry() != null) {
            LogUtils.e(mLogTag, "成功获取目录");
            ReaderDocumentTableOfContentEntry entry = content.getRootEntry();
            if (entry.getChildren() != null && entry.getChildren().size() > 0) {
                getChapterInfos(entry, mChapterInfos, 0, "");
            }
            if (mChapterInfos.size() > 0) {
                bookMenuView.setChapterData(mChapterInfos);
            }
        } else {
            UIUtils.showToastSafe("该书还未添加目录", Toast.LENGTH_LONG);
            LogUtils.e(mLogTag, "该书没有目录");
        }
    }

    @Override
    public Context getViewContext() {
        return BaseFragmentActivity.getCurrentActivity();
    }

    /**
     * 转换目录数据
     *
     * @param entry     原始目录结构
     * @param modelList 需要的目录结构
     * @param level     目录等级
     */
    private void getChapterInfos(ReaderDocumentTableOfContentEntry entry, List<ChapterInfo> modelList, int level, String fullTitle) {
        LogUtils.e(mLogTag, "解析onyx目录bean");
        ChapterInfo info = new ChapterInfo();

        if (!TextUtils.isEmpty(entry.getTitle())) {
            fullTitle = (TextUtils.isEmpty(fullTitle) ? "" : fullTitle + "$") + entry.getTitle().trim();
        }
        if (entry.getTitle() != null && entry.getPageName() != null) {
            info.setTitle(entry.getTitle());
            info.setPosition(entry.getPageName());
            info.setHead(level == 1);
            info.setLevel(level);
            info.setFullTitle(fullTitle.trim());
            modelList.add(info);
        }

        List<ReaderDocumentTableOfContentEntry> entries = entry.getChildren();
        if (entries != null && entries.size() > 0) {
            for (ReaderDocumentTableOfContentEntry child : entries) {
                getChapterInfos(child, modelList, level + 1, fullTitle);
            }
        }
    }

    private void initAudioController() {
        audioControllerView.init(this);
        audioControllerView.setControllerListener(new AudioControllerView.AudioControllerListener() {
            @Override
            public void leaveWrite() {

            }

            @Override
            public void intoWrite() {

            }

            @Override
            public void playPositionChange(int position) {
                setPlayPosition(position);
            }
        });
    }

    private void setPlayPosition(int position) {
        if (mImgReaders.size() != 0) {
            // 恢复按钮状态
            for (int i = 0; i < mImgReaders.size(); i++) {
                int key = mImgReaders.keyAt(i);
                ImageView item = mImgReaders.get(key);
                item.setSelected(key == position);
            }
        }
    }

    //添加点读
    private void addReaderPoint(ReadingBean readingBean) {

        mImgReaders.clear();
        List<String> audioPaths = new ArrayList<>();
        if (readingBean != null && readingBean.getVoiceInfos() != null && readingBean.getVoiceInfos().size() > 0) {
            audioControllerView.setVisibility(View.VISIBLE);
            FrameLayout.LayoutParams params;
            for (ReadingBean.VoiceBean bean : readingBean.getVoiceInfos()) {
                params = createReaderLayoutParams(bean);
                final ImageView imageView = new ImageView(this);
                mImgReaders.append(bean.getPostion(), imageView);
                imageView.setImageResource(R.drawable.img_reader_ponit_select_new);
                imageView.setOnClickListener(v -> {
                    //离开手写模式

                    audioControllerView.startPlaySingle(bean.getPostion());


                });

                mFmAddChildLayout.addView(imageView, params);
                audioPaths.add(FileUtil.getAudioPath() + mOpenBookInfo.getBookId() + "/" + bean.getUrl());
            }
        } else {
            audioControllerView.setVisibility(View.GONE);
        }
        audioControllerView.setAudioPaths(audioPaths);
    }

    private void initReaderPointRowXY() {
        if (mRowX == 0 || mRowY == 0) {
            mRowX = (float) UIUtils.getScreenWidth() / (float) 960;
            mRowY = (float) UIUtils.getScreenHeight() / (float) 1280;
        }
        if (mReaderImageW == 0 || mReaderImageH == 0) {
            //原始点读图片的 宽度和高度
            mReaderImageW = (int) (36 * mRowX);
            mReaderImageH = (int) (32 * mRowY);
        }
    }

    private FrameLayout.LayoutParams createReaderLayoutParams(ReadingBean.VoiceBean bean) {
        initReaderPointRowXY();
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(mReaderImageW, mReaderImageH);
        params.gravity = Gravity.START | Gravity.TOP;
        if (DeviceTypeGlobal.DEVICE_TYPE_SYSTEM.equalsIgnoreCase(DeviceTypeGlobal.DEVICE_TYPE_EDU) || DeviceTypeGlobal.DEVICE_TYPE_SYSTEM.equalsIgnoreCase(DeviceTypeGlobal.DEVICE_TYPE_PL107)) {
            params.topMargin = bean.getViewTop();
            params.leftMargin = bean.getViewLeft();
        } else {
            params.topMargin = (int) (bean.getViewTop() * mRowY);
            params.leftMargin = (int) (bean.getViewLeft() * mRowX);
        }
        return params;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (audioControllerView != null)
            audioControllerView.destroy();
    }
    private void setVoiceUiState() {
        etVoice.setText(getAudioHelper().getCurVolume() + "");
        if (getAudioHelper().getCurVolume() == 0) {
            imgBtnReduce.setEnabled(false);
            imgBtnEnlarge.setEnabled(true);
        } else if (getAudioHelper().getCurVolume() >= getAudioHelper().getMaxVolume()) {
            imgBtnEnlarge.setEnabled(false);
            imgBtnReduce.setEnabled(true);
        } else {
            imgBtnReduce.setEnabled(true);
            imgBtnEnlarge.setEnabled(true);
        }
    }
}

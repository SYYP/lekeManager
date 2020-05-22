package www.manager.leke.com.lekemanager.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onyx.android.sdk.api.device.epd.EpdController;
import com.onyx.android.sdk.api.device.epd.UpdateMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.functions.Action1;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.bean.BookMessageDetail;
import www.manager.leke.com.lekemanager.fragment.JianjFragment;
import www.manager.leke.com.lekemanager.fragment.MlFragment;
import www.manager.leke.com.lekemanager.fragment.XqFragment;
import www.manager.leke.com.lekemanager.http.HttpManager;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.ImageLoader;
import www.manager.leke.com.lekemanager.utils.NetUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/5/6 0006
 * 图书或者题库详情页面
 */
public class BookActiivty extends BaseFragmentActivity {
    @BindView(R.id.img_back)
    ImageView imgBack;
    @BindView(R.id.img_books)
    ImageView imgBooks;
    @BindView(R.id.text_title)
    TextView textTitle;
    @BindView(R.id.text_auther)
    TextView textAuther;
    @BindView(R.id.fram_layout)
    FrameLayout framLayout;
    @BindView(R.id.text_xq)
    TextView textXq;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.text_jjie)
    TextView textJjie;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.text_ml)
    TextView textMl;
    @BindView(R.id.view3)
    View view3;

    Fragment mCutterFragment;
    XqFragment mXqFragment;
    JianjFragment mJianjFragment;
    MlFragment mMlFragment;
    boolean isadd;
    @BindView(R.id.img_refush)
    ImageView imgRefush;
    @BindView(R.id.img_books_big)
    ImageView imgBooksBig;
    @BindView(R.id.liner_all)
    LinearLayout linerAll;
    private Integer mBookId;
    BookMessageDetail mBookMessageDetail;

    @Override
    public void init() {

    }


    @Override
    public void processExtraData() {
        super.processExtraData();
        mBookId = getIntent().getIntExtra(Contacts.BOOKID, 0);
    }

    @Override
    public View layout() {
        return inflate(R.layout.activity_book_message);
    }

    @Override
    public void loadData() {

        //加载网络
        NetworkData();

    }

    private void NetworkData() {
        if (NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getBookmessageDetail(mBookId)
                    .subscribe(new Action1<BookMessageDetail>() {
                        @Override
                        public void call(BookMessageDetail bookMessageDetail) {
                            if (bookMessageDetail != null) {
                                mBookMessageDetail = bookMessageDetail;
                            }
                            if (mXqFragment == null) {
                                mXqFragment = new XqFragment();
                                isadd = true;
                            }
                            showFragment(mXqFragment, isadd);
                            //设置数据：
                            textTitle.setText("书名：" + mBookMessageDetail.getBookTitle());
                            textAuther.setText(TextUtils.isEmpty(mBookMessageDetail.getBookExtraInfo().getBookAuthor()) ? "" : "作者:" + mBookMessageDetail.getBookExtraInfo().getBookAuthor());//作者
                            if (Contacts.KEBEN.equals(mBookMessageDetail.getBookTypeCode())) {
                                //课本
                                imgBooksBig.setVisibility(View.VISIBLE);
                                imgBooks.setVisibility(View.GONE);
                                ImageLoader.getInstance().loadBookDetail(BookActiivty.this, imgBooksBig, mBookMessageDetail.getCoverBInfos().get(0).getCoverBAtchRemotePath(), Contacts.KEBEN);
                            } else {
                                //课外书
                                imgBooksBig.setVisibility(View.GONE);
                                imgBooks.setVisibility(View.VISIBLE);
                                ImageLoader.getInstance().loadBookDetail(BookActiivty.this, imgBooks, mBookMessageDetail.getCoverBInfos().get(0).getCoverBAtchRemotePath(), Contacts.KEWAISHU);
                            }


                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {

                        }
                    });
        } else {
            UIUtils.showToastSafe(R.string.no_net);
        }
    }

    @Override
    protected void dimissHtpp() {

    }


    //设置点击事件
    @OnClick({R.id.text_xq, R.id.text_jjie, R.id.text_ml, R.id.img_back, R.id.img_refush})
    public void OnClick(View view) {
        boolean isAdd = false;
        switch (view.getId()) {
            case R.id.text_xq:
                setView(View.VISIBLE, View.INVISIBLE, View.INVISIBLE);
                if (mXqFragment == null) {
                    mXqFragment = new XqFragment();
                    isAdd = true;
                }
                showFragment(mXqFragment, isAdd);
                break;
            case R.id.text_jjie:
                setView(View.INVISIBLE, View.VISIBLE, View.INVISIBLE);
                if (mJianjFragment == null) {
                    mJianjFragment = new JianjFragment();
                    isAdd = true;
                }
                showFragment(mJianjFragment, isAdd);
                break;
            case R.id.text_ml:
                setView(View.INVISIBLE, View.INVISIBLE, View.VISIBLE);
                if (mMlFragment == null) {
                    mMlFragment = new MlFragment();
                    isAdd = true;
                }
                showFragment(mMlFragment, isAdd);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.img_refush:
                EpdController.invalidate(linerAll, UpdateMode.GC);
                break;
        }
    }

    private void setView(int invisible, int invisible2, int visible) {
        view1.setVisibility(invisible);
        view2.setVisibility(invisible2);
        view3.setVisibility(visible);
    }

    private void showFragment(Fragment fragment, boolean isAdd) {
        if (mCutterFragment != null && fragment != null && mCutterFragment == fragment) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(Contacts.BOOKDETAIL, mBookMessageDetail);
        fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (isAdd) {
            ft.add(R.id.fram_layout, fragment);
        } else {
            ft.show(fragment);
        }
        if (mCutterFragment != null) {
            ft.hide(mCutterFragment);
        }
        ft.commitAllowingStateLoss();
        mCutterFragment = fragment;
    }



}

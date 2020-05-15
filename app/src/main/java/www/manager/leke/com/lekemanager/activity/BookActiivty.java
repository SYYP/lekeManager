package www.manager.leke.com.lekemanager.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.fragment.JianjFragment;
import www.manager.leke.com.lekemanager.fragment.MainFragment;
import www.manager.leke.com.lekemanager.fragment.MlFragment;
import www.manager.leke.com.lekemanager.fragment.XqFragment;

/**
 * Created by ypu
 * on 2020/5/6 0006
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
    @Override
    public void init() {

    }

    @Override
    public View layout() {
        return inflate(R.layout.activity_book_message);
    }

    @Override
    public void loadData() {

        if (mXqFragment==null){
            mXqFragment=new XqFragment();
            isadd=true;
        }
        showFragment(mXqFragment,isadd);

    }

    @Override
    protected void dimissHtpp() {

    }


    //设置点击事件
    @OnClick({R.id.text_xq, R.id.text_jjie, R.id.text_ml,R.id.img_back,R.id.img_refush})
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

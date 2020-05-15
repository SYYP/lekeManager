package www.manager.leke.com.lekemanager;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.manager.leke.com.lekemanager.base.BaseFragment;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.fragment.BookFragment;
import www.manager.leke.com.lekemanager.fragment.MainFragment;
import www.manager.leke.com.lekemanager.fragment.Myfragment;
import www.manager.leke.com.lekemanager.fragment.QuestionFragment;
import www.manager.leke.com.lekemanager.fragment.ReadFragment;

public class MainActivity extends BaseFragmentActivity {

    @BindView(R.id.img_book)
    ImageView imgBook;
    @BindView(R.id.img_read)
    ImageView imgRead;
    @BindView(R.id.img_question)
    ImageView imgQuestion;
    @BindView(R.id.img_my)
    ImageView imgMy;
    BaseFragment mCutterFragment;
    BookFragment mBookFragment;
    ReadFragment mReadFragment;
    QuestionFragment mQuestionFragment;
    Myfragment mMyfragment;
    boolean isAdd;

    @Override
    public void init() {
    }

    @Override
    public View layout() {
        return inflate(R.layout.activity_main);
    }

    @Override
    public void loadData() {
        imgBook.setImageDrawable(getResources().getDrawable(R.drawable.img_main_book_press));
        if (mBookFragment == null) {
            mBookFragment = new BookFragment();
            isAdd = true;

        }
        showFragment(mBookFragment, isAdd);
    }

    @Override
    protected void dimissHtpp() {
    }

    private void showFragment(BaseFragment fragment, boolean isAdd) {
        if (mCutterFragment != null && fragment != null && mCutterFragment == fragment) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (isAdd) {
            ft.add(R.id.frag_ment, fragment);
        } else {
            ft.show(fragment);
        }
        if (mCutterFragment != null) {
            ft.hide(mCutterFragment);
        }
        ft.commitAllowingStateLoss();
        mCutterFragment = fragment;
    }

    @OnClick({R.id.img_book, R.id.img_read, R.id.img_question, R.id.img_my})
    public void onClick(View view) {
        boolean isAdd = false;
        switch (view.getId()) {
            case R.id.img_book:
                imgBook.setImageDrawable(getResources().getDrawable(R.drawable.img_main_book_press));
                imgRead.setImageDrawable(getResources().getDrawable(R.drawable.img_main_read_normal));
                imgQuestion.setImageDrawable(getResources().getDrawable(R.drawable.img_main_question_normal));
                imgMy.setImageDrawable(getResources().getDrawable(R.drawable.img_main_my_normal));
                if (mBookFragment == null) {
                    mBookFragment = new BookFragment();
                    isAdd = true;

                }
                showFragment(mBookFragment, isAdd);
                break;
            case R.id.img_read:
                imgBook.setImageDrawable(getResources().getDrawable(R.drawable.img_main_book_normal));
                imgRead.setImageDrawable(getResources().getDrawable(R.drawable.img_main_read_press));
                imgQuestion.setImageDrawable(getResources().getDrawable(R.drawable.img_main_question_normal));
                imgMy.setImageDrawable(getResources().getDrawable(R.drawable.img_main_my_normal));
                if (mReadFragment == null) {
                    mReadFragment = new ReadFragment();
                    isAdd = true;

                }
                showFragment(mReadFragment, isAdd);
                break;
            case R.id.img_question:
                imgBook.setImageDrawable(getResources().getDrawable(R.drawable.img_main_book_normal));
                imgRead.setImageDrawable(getResources().getDrawable(R.drawable.img_main_read_normal));
                imgQuestion.setImageDrawable(getResources().getDrawable(R.drawable.img_main_question_press));
                imgMy.setImageDrawable(getResources().getDrawable(R.drawable.img_main_my_normal));
                if (mQuestionFragment == null) {
                    mQuestionFragment = new QuestionFragment();
                    isAdd = true;

                }
                showFragment(mQuestionFragment, isAdd);
                break;
            case R.id.img_my:
                imgBook.setImageDrawable(getResources().getDrawable(R.drawable.img_main_book_normal));
                imgRead.setImageDrawable(getResources().getDrawable(R.drawable.img_main_read_normal));
                imgQuestion.setImageDrawable(getResources().getDrawable(R.drawable.img_main_question_normal));
                imgMy.setImageDrawable(getResources().getDrawable(R.drawable.img_main_my_press));
                if (mMyfragment == null) {
                    mMyfragment = new Myfragment();
                    isAdd = true;

                }
                showFragment(mMyfragment, isAdd);
                break;
            default:
                break;
        }

    }
}

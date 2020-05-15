package www.manager.leke.com.lekemanager.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.frank.etude.pageable.PageBtnBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;

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

    @Override
    public void init() {

    }

    @Override
    public View layout() {
        return inflate(R.layout.activity_answer_detail_list);
    }

    @Override
    public void loadData() {

    }

    @Override
    protected void dimissHtpp() {

    }
    @OnClick({R.id.img_back, R.id.img_refush})
    protected void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;

            case R.id.img_refush:
                break;
            default:
                break;
        }
    }

}

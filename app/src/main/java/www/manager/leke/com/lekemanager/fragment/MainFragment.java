package www.manager.leke.com.lekemanager.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragment;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/4/30 0030
 */
public abstract class MainFragment extends BaseFragment {
    @BindView(R.id.img_refush)
    ImageView imgRefush;
    @BindView(R.id.text_all)
    TextView textAll;
    @BindView(R.id.view1)
    View view1;
    @BindView(R.id.text_bianj)
    TextView textBianj;
    @BindView(R.id.view2)
    View view2;
    @BindView(R.id.text_xiaodui)
    TextView textXiaodui;
    @BindView(R.id.view3)
    View view3;
    @BindView(R.id.text_shengh)
    TextView textShengh;
    @BindView(R.id.view4)
    View view4;
    @BindView(R.id.liner)
    LinearLayout liner;
    Unbinder unbinder;
    @BindView(R.id.imh_serach)
    ImageView imhSerach;
    @BindView(R.id.real_title)
    RelativeLayout realTitle;
    @BindView(R.id.edit_serach)
    EditText editSerach;
    @BindView(R.id.text_pause)
    TextView textPause;
    @BindView(R.id.real_serach)
    RelativeLayout realSerach;


    @Override
    public void setChildTag() {

    }

    @Override
    protected View getXmlView() {
        return UIUtils.inflate(R.layout.fragment_main);
    }

    @Override
    protected void initXmlData() {
    }

    @Override
    protected void loadData() {
    }
    @OnClick({R.id.imh_serach, R.id.text_pause, R.id.text_all, R.id.text_bianj, R.id.text_xiaodui, R.id.text_shengh,
            R.id.img_refush})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imh_serach://搜索
                realTitle.setVisibility(View.GONE);
                realSerach.setVisibility(View.VISIBLE);
                break;
            case R.id.text_pause://取消
                realTitle.setVisibility(View.VISIBLE);
                realSerach.setVisibility(View.GONE);
                break;

            case R.id.text_all://全部
                setViewViisib(false, view1);
                setViewViisib(true, view2);
                setViewViisib(false, view3);
                setViewViisib(false, view4);
                setTextSize(true, textAll);
                setTextSize(false, textBianj);
                setTextSize(false, textXiaodui);
                setTextSize(false, textShengh);
                getTextAll();
                break;
            case R.id.text_bianj://编辑
                setViewViisib(true, view1);
                setViewViisib(false, view2);
                setViewViisib(false, view3);
                setViewViisib(false, view4);
                setTextSize(false, textAll);
                setTextSize(true, textBianj);
                setTextSize(false, textXiaodui);
                setTextSize(false, textShengh);
                getTextbj();
                break;
            case R.id.text_xiaodui://校对
                setViewViisib(false, view1);
                setViewViisib(false, view2);
                setViewViisib(true, view3);
                setViewViisib(false, view4);
                setTextSize(false, textAll);
                setTextSize(false, textBianj);
                setTextSize(true, textXiaodui);
                setTextSize(false, textShengh);
                getTextxdui();
                break;
            case R.id.text_shengh://审核
                setViewViisib(false, view1);
                setViewViisib(false, view2);
                setViewViisib(false, view3);
                setViewViisib(true, view4);
                setTextSize(false, textAll);
                setTextSize(false, textBianj);
                setTextSize(false, textXiaodui);
                setTextSize(true, textShengh);
                getTextshengh();
                break;
            case R.id.img_refush: //刷新
                getRefresh();
                break;

        }
    }

    protected abstract void getTextAll();//全部、

    protected abstract void getTextbj();//编辑

    protected abstract void getTextxdui();//校对

    protected abstract void getTextshengh();//审核

    protected abstract void getRefresh();//刷新

    public void setViewViisib(boolean viisibb, View view2) {
        if (viisibb) {
            view2.setVisibility(View.VISIBLE);
        } else {
            view2.setVisibility(View.GONE);
        }

    }

    public void setTextSize(boolean textSize, TextView textView) {
        if (textSize) {
            textView.setTextSize(getResources().getDimension(R.dimen.x30));
        } else {
            textView.setTextSize(getResources().getDimension(R.dimen.x28));
        }
    }

}

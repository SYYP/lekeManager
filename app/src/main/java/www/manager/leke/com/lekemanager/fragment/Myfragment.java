package www.manager.leke.com.lekemanager.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.activity.ReviseActivity;
import www.manager.leke.com.lekemanager.base.BaseFragment;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/4/30 0030
 * 我的模块
 */
public class Myfragment extends BaseFragment {
    @BindView(R.id.real)
    RelativeLayout real;
    @BindView(R.id.linger_online)
    LinearLayout lingerOnline;
    @BindView(R.id.liner_test)
    LinearLayout linerTest;
    @BindView(R.id.liner_upgrade)
    LinearLayout linerUpgrade;
    @BindView(R.id.liner_pwd)
    LinearLayout linerPwd;
    @BindView(R.id.liner_end)
    LinearLayout linerEnd;
    @BindView(R.id.img_UI)
    ImageView imgUI;
    @BindView(R.id.img_technology)
    ImageView imgTechnology;
    @BindView(R.id.liner)
    LinearLayout liner;


    @Override
    public void setChildTag() {

    }

    @Override
    protected View getXmlView() {
        return UIUtils.inflate(R.layout.fragment_my);
    }

    @Override
    protected void initXmlData() {

    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.img_UI, R.id.img_technology, R.id.linger_online, R.id.liner_test, R.id.liner_upgrade, R.id.liner_pwd,R.id.liner_end})
    protected void OnClick(View view) {
        switch (view.getId()) {
            case R.id.img_UI:
                break;
            case R.id.img_technology:
                break;
            case R.id.linger_online:
                break;
            case R.id.liner_test:
                break;
            case R.id.liner_upgrade:
                break;
            case R.id.liner_pwd:
                Intent intent=new Intent(getActivity(),ReviseActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.liner_end:
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                break;
            default:
                break;
        }
    }


}

package www.manager.leke.com.lekemanager.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
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
import rx.functions.Action1;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.activity.ReviseActivity;
import www.manager.leke.com.lekemanager.base.BaseFragment;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.NetUtils;
import www.manager.leke.com.lekemanager.utils.SpUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

import static www.manager.leke.com.lekemanager.base.BaseFragmentActivity.showToast;

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
                SpUtils.putInt("url", 1);
                restartApplication(getActivity());
                break;
            case R.id.liner_upgrade:
                SpUtils.putInt("url", 0);
                restartApplication(getActivity());
                break;
            case R.id.liner_pwd:
                Intent intent=new Intent(getActivity(),ReviseActivity.class);
                getActivity().startActivity(intent);
                break;
            case R.id.liner_end:
                //清除Sp
                SpUtils.putString(Contacts.ACCOUNT,"");
                SpUtils.putString(Contacts.PASSWORD,"");
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(0);
                break;
            default:
                break;
        }
    }
    private void getVersion() {
    if(NetUtils.isWifiConnected()){}else {
        UIUtils.showToastSafe(R.string.no_net);
    }
    }

    public void restartApplication(Context context) {
        showToast("切换环境后请“强行停止”或“结束运行”程序");
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", context.getPackageName(), null);
        intent.setData(uri);
        context.startActivity(intent);
    }

}

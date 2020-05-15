package www.manager.leke.com.lekemanager.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import rx.functions.Action1;
import www.manager.leke.com.lekemanager.MainActivity;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.bean.LoginBean;
import www.manager.leke.com.lekemanager.http.HttpManager;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.GsonUitls;
import www.manager.leke.com.lekemanager.utils.NetUtils;
import www.manager.leke.com.lekemanager.utils.SpUtils;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/4/29 0029
 */
public class LoginActivity extends BaseFragmentActivity {
    @BindView(R.id.img_nuber)
    ImageView imgNuber;
    @BindView(R.id.edte_number)
    EditText edteNumber;
    @BindView(R.id.img_number_delete)
    ImageView imgNumberDelete;
    @BindView(R.id.img_pwd)
    ImageView imgPwd;
    @BindView(R.id.edit_pwd)
    EditText editPwd;
    @BindView(R.id.img_delete_pwd)
    ImageView imgDeletePwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.img_tou)
    CircleImageView imgTou;

    @Override
    public void init() {

    }

    @Override
    public View layout() {
        return inflate(R.layout.activity_login);
    }

    @Override
    public void loadData() {
        String account = SpUtils.getString(Contacts.ACCOUNT);
        String password = SpUtils.getString(Contacts.PASSWORD);
        if(!TextUtils.isEmpty(account)&&!TextUtils.isEmpty(password)){
            NetWorkLogin(account, password);
        }
        //加载数据
        edteNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    imgNumberDelete.setImageDrawable(getResources().getDrawable(R.drawable.img_login_normal_delete));
                } else {
                    imgNumberDelete.setImageDrawable(getResources().getDrawable(R.drawable.img_login_delete));
                }
            }
        });
        //加载数据
        editPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString())) {
                    imgNumberDelete.setImageDrawable(getResources().getDrawable(R.drawable.img_login_delete));
                } else {
                    imgNumberDelete.setImageDrawable(getResources().getDrawable(R.drawable.img_login_normal_delete));
                }
            }
        });
    }

    @OnClick({R.id.img_number_delete, R.id.img_delete_pwd, R.id.btn_login})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_number_delete://清楚账号
                edteNumber.setText("");
                break;

            case R.id.img_delete_pwd:
                editPwd.setText("");
                break;

            case R.id.btn_login://登录
                String textZhangh = edteNumber.getText().toString();
                String textPwd = editPwd.getText().toString();
                if (TextUtils.isEmpty(textZhangh)) {
                    showToast("请输入账号");
                    return;

                }
                if (TextUtils.isEmpty(textPwd)) {
                    showToast("请输入密码");
                    return;

                }
                NetWorkLogin(textZhangh, textPwd);

                break;
            default:
                break;
        }
    }

    private void NetWorkLogin(String account, String password) {
        if (NetUtils.isWifiConnected()) {
            HttpManager.getInstace().getLogin(account, password).subscribe(new Action1<LoginBean>() {
                @Override
                public void call(LoginBean loginBean) {
                    SpUtils.putString(Contacts.TOKEN, loginBean.getUserToken());
                    HttpManager.getInstace().getInformation()
                            .subscribe(new Action1<LoginBean>() {
                                @Override
                                public void call(LoginBean infoBean) {
                                    if (infoBean != null) {
                                         //登录成功后我要记住密码下次直接进来
                                        SpUtils.putString(Contacts.ACCOUNT,account);
                                        SpUtils.putString(Contacts.PASSWORD,password);
                                        ArrayList<String> permissionIds = infoBean.getPermissionIds();
                                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                        SpUtils.putString(Contacts.PERMISSIOMIDS, GsonUitls.toJson(permissionIds));
                                        startActivity(intent);
                                    }
                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    Log.d("TAG", "信息为：" + throwable.getMessage());
                                }
                            });

                }
            }, new Action1<Throwable>() {
                @Override
                public void call(Throwable throwable) {
                    Log.d("TAG", "信息为：" + throwable.getMessage());

                }
            });
        } else {
            UIUtils.showToastSafe(getResources().getString(R.string.no_net));
        }
    }

    @Override
    protected void dimissHtpp() {

    }

}

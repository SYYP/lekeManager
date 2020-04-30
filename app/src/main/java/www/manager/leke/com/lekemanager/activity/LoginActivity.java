package www.manager.leke.com.lekemanager.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import www.manager.leke.com.lekemanager.MainActivity;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;

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

    @OnClick({R.id.img_number_delete, R.id.img_delete_pwd,R.id.btn_login})
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
                String textPwd= editPwd.getText().toString();
                if (TextUtils.isEmpty(textZhangh)) {
                    showToast("请输入账号");
                return;

                }
                if (TextUtils.isEmpty(textPwd)) {
                    showToast("请输入密码");
                    return;

                }
                loadIntentFlag(MainActivity.class);

                break;
            default:
                break;
        }
    }

    @Override
    protected void dimissHtpp() {

    }

}

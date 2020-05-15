package www.manager.leke.com.lekemanager.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.utils.UIUtils;

/**
 * Created by ypu
 * on 2020/5/8 0008
 */
public class ReviseActivity extends BaseFragmentActivity {
    @BindView(R.id.edit_old)
    EditText editOld;
    @BindView(R.id.edit_newpwd)
    EditText editNewpwd;
    @BindView(R.id.edit_surepwd)
    EditText editSurepwd;
    @BindView(R.id.btn_sure)
    Button btnSure;

    @Override
    public void init() {

    }

    @Override
    public View layout() {
        return UIUtils.inflate(R.layout.activity_revisepwd);
    }

    @Override
    public void loadData() {



           //添加数据
          btnSure.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  String oldPwd= editOld.getText().toString();
                  String surePwd= editSurepwd.getText().toString();
                  String newPwd= editNewpwd.getText().toString();
                   if(TextUtils.isEmpty(oldPwd)){
                       showToast("请填写原密码");
                       return;
                   }
                  if(TextUtils.isEmpty(newPwd)){
                      showToast("请填写新密码");
                      return;
                  }
                  if(TextUtils.isEmpty(surePwd)){
                      showToast("请确定新密码");
                      return;
                  }
                  if(oldPwd.equals(surePwd)){
                       showToast("新密码不能跟原密码一致");
                       return;
                  }
                  if(!(surePwd.equals(newPwd))){
                      showToast("两次密码不一致");
                  }


              }
          });
    }

    @Override
    protected void dimissHtpp() {

    }



}

package www.manager.leke.com.lekemanager.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;
import www.manager.leke.com.lekemanager.R;
import www.manager.leke.com.lekemanager.base.BaseFragmentActivity;
import www.manager.leke.com.lekemanager.http.ApiException;
import www.manager.leke.com.lekemanager.http.HttpManager;
import www.manager.leke.com.lekemanager.utils.Contacts;
import www.manager.leke.com.lekemanager.utils.NetUtils;
import www.manager.leke.com.lekemanager.utils.SpUtils;
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
                      return;
                  }

                 netWorkData(oldPwd,newPwd);//网络请求
              }
          });
    }

    private void netWorkData( String oldPwd,String newPwd) {
         if(NetUtils.isWifiConnected()) {
             HttpManager.getInstace().getRevisePwd(oldPwd, newPwd)
                     .subscribe(new Action1<Object>() {
                         @Override
                         public void call(Object o) {
                             SpUtils.putString(Contacts.ACCOUNT,"");
                             SpUtils.putString(Contacts.PASSWORD,"");
                             showToast("修改成功");
                             finish();
                         }
                     }, new Action1<Throwable>() {
                         @Override
                         public void call(Throwable throwable) {

                             if (throwable instanceof ApiException) {
                                 UIUtils.showToastSafe(throwable.getMessage());
                             }
                         }
                     });
         }else {
             UIUtils.showToastSafe(R.string.no_net);
         }
    }

    @Override
    protected void dimissHtpp() {

    }



}

package com.example.ojprogramming;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ResetPassword extends Activity {
    private EditText mAccount;                        //�û����༭
    private EditText mPwd_old;                        //����༭
    private EditText mPwd_new;                        //����༭
    private EditText mPwdCheck;                       //����༭
    private Button mSureButton;                       //ȷ����ť
    private Button mCancelButton;                     //ȡ����ť
    private UserDataManager mUserDataManager;         //�û����ݹ�����
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resetpassword);
        mAccount = (EditText) findViewById(R.id.resetpwd_edit_name);
        mPwd_old = (EditText) findViewById(R.id.resetpwd_edit_pwd_old);
        mPwd_new = (EditText) findViewById(R.id.resetpwd_edit_pwd_new);
        mPwdCheck = (EditText) findViewById(R.id.resetpwd_edit_pwd_check);
        mSureButton = (Button) findViewById(R.id.resetpwd_btn_sure);
        mCancelButton = (Button) findViewById(R.id.resetpwd_btn_cancel);
        mSureButton.setOnClickListener(m_resetpwd_Listener);      //ע�����������ť�ļ����¼�
        mCancelButton.setOnClickListener(m_resetpwd_Listener);
        if (mUserDataManager == null) {
            mUserDataManager = new UserDataManager(this);
            mUserDataManager.openDataBase();                              //�����������ݿ�
        }
    }
    View.OnClickListener m_resetpwd_Listener = new View.OnClickListener() {    //��ͬ��ť���µļ����¼�ѡ��
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.resetpwd_btn_sure:                       //ȷ�ϰ�ť�ļ����¼�
                  //  resetpwd_check();
                    break;
                case R.id.resetpwd_btn_cancel:                     //ȡ����ť�ļ����¼�,��ע����淵�ص�¼����
                    Intent intent_Resetpwd_to_Login = new Intent(ResetPassword.this,Login.class) ;    //�л�Resetpwd Activity��Login Activity
                    startActivity(intent_Resetpwd_to_Login);
                    finish();
                    break;
            }
        }
    };
    /*public void resetpwd_check() {                                //ȷ�ϰ�ť�ļ����¼�
        if (isUserNameAndPwdValid()) {
            String userName = mAccount.getText().toString().trim();
            String userPwd_old = mPwd_old.getText().toString().trim();
            String userPwd_new = mPwd_new.getText().toString().trim();
            String userPwdCheck = mPwdCheck.getText().toString().trim();
            int result=mUserDataManager.findUserByNameAndPwd(userName, userPwd_old);
            if(result==1){                                             //����1˵���û������������ȷ,������������
                if(userPwd_new.equals(userPwdCheck)==false){           //�����������벻һ��
                    Toast.makeText(this, getString(R.string.pwd_not_the_same),Toast.LENGTH_SHORT).show();
                    return ;
                } else {
                   // UserData mUser = new UserData(userName, userPwd_new);
                    mUserDataManager.openDataBase();
                    boolean flag = mUserDataManager.updateUserData(mUser);
                    if (flag == false) {
                        Toast.makeText(this, getString(R.string.resetpwd_fail),Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(this, getString(R.string.resetpwd_success),Toast.LENGTH_SHORT).show();
                     //   mUser.pwdresetFlag=1;
                        Intent intent_Register_to_Login = new Intent(ResetPassword.this,Login.class) ;    //�л�User Activity��Login Activity
                        startActivity(intent_Register_to_Login);
                        finish();
                    }
                }
            }else if(result==0){                                       //����0˵���û��������벻ƥ�䣬��������
                Toast.makeText(this, getString(R.string.pwd_not_fit_user),Toast.LENGTH_SHORT).show();
                return;
            }
        }
    }*/
    public boolean isUserNameAndPwdValid() {
        String userName = mAccount.getText().toString().trim();
        //����û��Ƿ����
        int count=mUserDataManager.findUserByName(userName);
        //�û�������ʱ���أ�������ʾ����
        if(count<=0){
            Toast.makeText(this, getString(R.string.name_not_exist, userName),Toast.LENGTH_SHORT).show();
            return false;
        }
        if (mAccount.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.account_empty),Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd_old.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_empty),Toast.LENGTH_SHORT).show();
            return false;
        } else if (mPwd_new.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_new_empty),Toast.LENGTH_SHORT).show();
            return false;
        }else if(mPwdCheck.getText().toString().trim().equals("")) {
            Toast.makeText(this, getString(R.string.pwd_check_empty),Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
        /***
        mSureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(ResetPassword.this)
				.setTitle("��ʾ")
				.setMessage("�޸ĳɹ�")
				.setPositiveButton("ȷ��", null)
				.show();
				
			}
		});
        mCancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(ResetPassword.this)
				.setTitle("��ʾ")
				.setMessage("�޸�ʧ��")
				.setPositiveButton("ȷ��", null)
				.show();
			}
		});***/
 

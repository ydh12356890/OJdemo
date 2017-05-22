package com.example.ojprogramming;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
    private EditText mAccount;                        //�û����༭
    private EditText mPwd;                            //����༭
    private EditText mPwdCheck;                       //����ȷ��
    private Button mSureButton;                       //ȷ����ť
    private Button mCancelButton;                     //ȡ����ť
    private UserDataManager mUserDataManager;         //�û����ݹ�����  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAccount = (EditText)findViewById(R.id.register_name);
        mPwd = (EditText)findViewById(R.id.register_pwd);
        mPwdCheck = (EditText)findViewById(R.id.register_pwd_check);
        mSureButton = (Button)findViewById(R.id.register_btn_sure);
        mCancelButton = (Button)findViewById(R.id.register_btn_cancel);
        
        mSureButton.setOnClickListener(m_register_Listener);      //ע�����������ť�ļ����¼�  
        mCancelButton.setOnClickListener(m_register_Listener);  
  
        if (mUserDataManager == null) {  
            mUserDataManager = new UserDataManager(this);  
            mUserDataManager.openDataBase();                              //�����������ݿ�  
        }  
        
    }
    View.OnClickListener m_register_Listener = new View.OnClickListener() {    //��ͬ��ť���µļ����¼�ѡ��  
        public void onClick(View v) {  
            switch (v.getId()) {  
                case R.id.register_btn_sure:                       //ȷ�ϰ�ť�ļ����¼�  
                   //register_check();  
                    break;  
                case R.id.register_btn_cancel:                     //ȡ����ť�ļ����¼�,��ע����淵�ص�¼����  
                    Intent intent_Register_to_Login = new Intent(Register.this,Login.class) ;    //�л�User Activity��Login Activity  
                    startActivity(intent_Register_to_Login);  
                    finish();  
                    break;  
            }  
        }  
    };  
   /* public void register_check() {                                //ȷ�ϰ�ť�ļ����¼�  
        if (isUserNameAndPwdValid()) {  
            String userName = mAccount.getText().toString().trim();  
            String userPwd = mPwd.getText().toString().trim();  
            String userPwdCheck = mPwdCheck.getText().toString().trim();  
            //����û��Ƿ����  
            int count=mUserDataManager.findUserByName(userName);  
            //�û��Ѿ�����ʱ���أ�������ʾ����  
            if(count>0){  
                Toast.makeText(this, getString(R.string.name_already_exist, userName),Toast.LENGTH_SHORT).show();  
                return ;  
            }  
            if(userPwd.equals(userPwdCheck)==false){     //�����������벻һ��  
                Toast.makeText(this, getString(R.string.pwd_not_the_same),Toast.LENGTH_SHORT).show();  
                return ;  
            } else {  
               // UserData mUser = new UserData(userName, userPwd);  
                mUserDataManager.openDataBase();  
               // long flag = mUserDataManager.insertUserData(mUser); //�½��û���Ϣ  
              //  if (flag == -1) {  
                    Toast.makeText(this, getString(R.string.register_fail),Toast.LENGTH_SHORT).show();  
                }else{  
                    Toast.makeText(this, getString(R.string.register_success),Toast.LENGTH_SHORT).show();  
                    Intent intent_Register_to_Login = new Intent(Register.this,Login.class) ;    //�л�User Activity��Login Activity  
                    startActivity(intent_Register_to_Login);  
                    finish();  
                }  
            }  
        }  
    }  */
    public boolean isUserNameAndPwdValid() {  
        if (mAccount.getText().toString().trim().equals("")) {  
            Toast.makeText(this, getString(R.string.account_empty),  
                    Toast.LENGTH_SHORT).show();  
            return false;  
        } else if (mPwd.getText().toString().trim().equals("")) {  
            Toast.makeText(this, getString(R.string.pwd_empty),  
                    Toast.LENGTH_SHORT).show();  
            return false;  
        }else if(mPwdCheck.getText().toString().trim().equals("")) {  
            Toast.makeText(this, getString(R.string.pwd_check_empty),  
                    Toast.LENGTH_SHORT).show();  
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
				new AlertDialog.Builder(Register.this)
				.setTitle("��ʾ")
				.setMessage("ע��Success!")
				.setPositiveButton("ȷ��", null)
				.show();
				
			}
			
		});
        mCancelButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AlertDialog.Builder(Register.this)
				.setTitle("��ʾ")
				.setMessage("ע��failed!")
				.setPositiveButton("ȷ��", null)
				.show();
				
			}
		});
        
    
    ***/
 


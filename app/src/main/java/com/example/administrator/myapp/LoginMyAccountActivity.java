package com.example.administrator.myapp;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapp.client.SocketApplication;
import com.example.administrator.myapp.client.SocketClient;

public class LoginMyAccountActivity extends AppCompatActivity {
    EditText editAccount;
    EditText editPassword;
    EditText editRegisterName;
    EditText editRegisterPassword;
    TextView textAccount;
    TextView textPassword;
    Button buttonLogin;
    View lineInput;
    ImageView imageLoad;
    TextView textLoad;
    View includeInput;
    TextView textForget;
    TextView textRegister;


    private boolean activityIsLogin=true;
    InfoManager infoManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_login_my_account);

        SocketApplication socketApplication =(SocketApplication) getApplication();
        socketApplication.startClientSocket();
        infoManager=socketApplication.getInfoManager();

        editAccount=findViewById(R.id.edit_account);
        editPassword=findViewById(R.id.edit_password);
        editRegisterName=findViewById(R.id.edit_register_account);
        editRegisterPassword=findViewById(R.id.edit_register_password);
        textAccount=findViewById(R.id.text_account);
        textPassword=findViewById(R.id.text_password);
        buttonLogin=findViewById(R.id.button_login);
        lineInput=findViewById(R.id.line_input);
        imageLoad=findViewById(R.id.include_load);
        textLoad=findViewById(R.id.text_load);
        includeInput=findViewById(R.id.include_input);
        textForget=findViewById(R.id.text_forget);
        textRegister=findViewById(R.id.text_register);

        editAccount.addTextChangedListener(wEditAccount);
        editPassword.addTextChangedListener(wEditPassword);
        buttonLogin.setOnClickListener(cButtonLogin);
        textForget.setOnClickListener(cForgetMyPassword);
        textRegister.setOnClickListener(cRegisterMyAccount);
    }

    public void loginSucceed(){
        Intent succeedIntent=new Intent(LoginMyAccountActivity.this,MainActivity.class);
        succeedIntent.putExtra("myID",infoManager.getMyInfo().getMyID());
        succeedIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(succeedIntent);
        finish();
        Toast.makeText(LoginMyAccountActivity.this,"登录验证成功",Toast.LENGTH_SHORT).show();
    }

    public void loginFail(){
        restoreLine();
        Toast.makeText(LoginMyAccountActivity.this,"登录验证失败",Toast.LENGTH_SHORT).show();
    }

    public void NetworkDisconnection(){
        restoreLine();
        Toast.makeText(LoginMyAccountActivity.this,"网络断开",Toast.LENGTH_SHORT).show();
    }

    public void registerSucceed(){
        restoreLine();
        Toast.makeText(LoginMyAccountActivity.this,"注册用户成功",Toast.LENGTH_SHORT).show();
    }

    public void registerFail(){
        restoreLine();
        Toast.makeText(LoginMyAccountActivity.this,"注册用户失败",Toast.LENGTH_SHORT).show();
    }

    //账号输入监控
    boolean boolAccount=false;
    TextWatcher wEditAccount=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            if(activityIsLogin){
                if(s.length()>4&&s.length()<11){
                    textAccount.setText(R.string.account_true_zh);
                    boolAccount=true;
                    if(boolPassword&&boolAccount){
                        buttonLogin.setTextColor(getResources().getColor(R.color.colorButtonEnabledTrue));
                        buttonLogin.setEnabled(true);
                    }
                }
                else {
                    textAccount.setText(R.string.account_error_zh);
                    if(boolPassword&&boolAccount){
                        buttonLogin.setTextColor(getResources().getColor(R.color.colorButtonEnabledFalse));
                        buttonLogin.setEnabled(false);
                    }
                    boolAccount=false;
                }
            }
            else {
                if(s.length()>1&&s.length()<10){
                    textAccount.setText(R.string.register_account_name_true);
                    boolAccount=true;
                    if(boolPassword&&boolAccount){
                        buttonLogin.setTextColor(getResources().getColor(R.color.colorButtonEnabledTrue));
                        buttonLogin.setEnabled(true);
                    }
                }
                else {
                    textAccount.setText(R.string.register_account_name_false);
                    if(boolPassword&&boolAccount){
                        buttonLogin.setTextColor(getResources().getColor(R.color.colorButtonEnabledFalse));
                        buttonLogin.setEnabled(false);
                    }
                    boolAccount=false;
                }
            }
        }
    };
    //密码输入监控
    boolean boolPassword=false;
    TextWatcher wEditPassword=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }
        @Override
        public void afterTextChanged(Editable s) {
            if(s.length()>=6){
                textPassword.setText(R.string.password_true_zh);
                boolPassword=true;
                if(boolPassword&&boolAccount){
                    buttonLogin.setTextColor(getResources().getColor(R.color.colorButtonEnabledTrue));
                    buttonLogin.setEnabled(true);
                }
            }
            else {
                textPassword.setText(R.string.password_short_zh);
                if(boolPassword&&boolAccount){
                    buttonLogin.setTextColor(getResources().getColor(R.color.colorButtonEnabledFalse));
                    buttonLogin.setEnabled(false);
                }
                boolPassword=false;
            }
        }
    };

    private int lineHeight;
    private int lineWidth;
    //登录按钮点击事件
    View.OnClickListener cButtonLogin=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(activityIsLogin){
                String accountString=editAccount.getText().toString();
                String password=editPassword.getText().toString();
                infoManager.uiLoginMyAccount(Integer.valueOf(accountString),password);
            }
            else {
                String accountString=editRegisterName.getText().toString();
                String password=editRegisterPassword.getText().toString();
                infoManager.uiRegisterMyAccount(accountString,password);
            }

            //动画进行前记录原始高宽度
            lineWidth=lineInput.getWidth();
            lineHeight=lineInput.getHeight();
            setInputAnimator();
        }
    };

    View.OnClickListener cForgetMyPassword=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(LoginMyAccountActivity.this,"Demo暂不支持修改密码,请等待后续更新",Toast.LENGTH_SHORT).show();
        }
    };

    View.OnClickListener cRegisterMyAccount=new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (activityIsLogin){
                activityIsLogin=false;
                textRegister.setText(R.string.login_my_account);
                buttonLogin.setText(R.string.register_zh);
                textAccount.setText(R.string.register_account_name_input_zh);
                editAccount.removeTextChangedListener(wEditAccount);
                editPassword.removeTextChangedListener(wEditPassword);
                editRegisterName.addTextChangedListener(wEditAccount);
                editRegisterPassword.addTextChangedListener(wEditPassword);

                editAccount.setVisibility(View.GONE);
                editPassword.setVisibility(View.GONE);
                editRegisterName.setVisibility(View.VISIBLE);
                editRegisterPassword.setVisibility(View.VISIBLE);
            }
            else {
                activityIsLogin=true;
                textRegister.setText(R.string.register_my_account);
                buttonLogin.setText(R.string.login_zh);
                textAccount.setText(R.string.account_input_zh);
                editAccount.addTextChangedListener(wEditAccount);
                editPassword.addTextChangedListener(wEditPassword);
                editRegisterName.removeTextChangedListener(wEditAccount);
                editRegisterPassword.removeTextChangedListener(wEditPassword);

                editAccount.setVisibility(View.VISIBLE);
                editPassword.setVisibility(View.VISIBLE);
                editRegisterName.setVisibility(View.GONE);
                editRegisterPassword.setVisibility(View.GONE);
            }
        }
    };

    //登录动画-账号密码控件消失，登录按钮消失，缓慢缩小输入框
    private void setInputAnimator(){
        includeInput.setVisibility(View.GONE);
        buttonLogin.setVisibility(View.GONE);
        textForget.setText("");
        textLoad.setVisibility(View.VISIBLE);
        textLoad.setText("正在验证密码.");
        AnimatorSet animatorNarrowWidth = new AnimatorSet();
        //值动画，逐步缩小输入框宽度
        ValueAnimator animator1=ValueAnimator.ofInt(lineWidth,lineHeight);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int v=(int)animation.getAnimatedValue();
                ViewGroup.LayoutParams params=lineInput.getLayoutParams();
                params.width=v;
                //由于line高度设置wrap,高度会主动适应图案高度，需要手动定高
                if(params.height!=lineHeight) {
                    params.height = lineHeight;
                }
                lineInput.setLayoutParams(params);
            }
        });
        //设置动画持续时间/插值器/
        animatorNarrowWidth.setDuration(1000);
        animatorNarrowWidth.setInterpolator(new SpringInterpolator(1));
        animatorNarrowWidth.playSequentially(animator1);
        animatorNarrowWidth.start();
        //为动画创建监听
        animatorNarrowWidth.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }
            @Override
            public void onAnimationEnd(Animator animation) {
                //动画结束显示加载图案
                imageLoad.setVisibility(View.VISIBLE);
                //开始加载动画
                setLoadAnimator();
            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    private void setLoadAnimator() {
        //加载图案颜色逐渐加深
        ObjectAnimator objectAnimator1=ObjectAnimator.ofFloat(imageLoad,"alpha",0.02f,0.6f);
        //加载图案开始旋转
        ObjectAnimator objectAnimator2=ObjectAnimator.ofFloat(imageLoad,"rotation",0,-360);
        final AnimatorSet animatorColorBurn=new AnimatorSet();
        animatorColorBurn.setDuration(2500);
        animatorColorBurn.setInterpolator(new SpringInterpolator(2));
        animatorColorBurn.playSequentially(objectAnimator1);
        final AnimatorSet animatorRotate = new AnimatorSet();
        animatorRotate.playSequentially(objectAnimator2);
        animatorRotate.setDuration(2200);
        animatorRotate.setInterpolator(new SpringInterpolator(3));
        animatorColorBurn.start();
        animatorRotate.start();

        //旋转
        animatorRotate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if(infoManager.getConnStatus()){
                    if(infoManager.getMyLoginStatus()==0||infoManager.getMyLoginStatus()==3) {
                        //密码验证中，重复动画
                        animatorRotate.start();
                    }
                    else if(infoManager.getMyLoginStatus()==1){
                        //验证成功
                        loginSucceed();
                    }
                    else if(infoManager.getMyLoginStatus()==2){
                        //登录失败
                        loginFail();
                    }
                    else if(infoManager.getMyLoginStatus()==4){
                        //注册失败
                        registerSucceed();
                    }
                    else if(infoManager.getMyLoginStatus()==5){
                        //注册成功
                        registerFail();
                    }
                    Log.i("登录页面","用户状态:"+infoManager.getMyStatusString());
                }
                else {
                    NetworkDisconnection();
                }
            }
            @Override
            public void onAnimationCancel(Animator animation) {

            }
            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
    //登录失败，还原输入框
    public void restoreLine(){
        textLoad.setText("登录验证失败");
        imageLoad.setVisibility(View.GONE);

        AnimatorSet animatorRestore = new AnimatorSet();

        ValueAnimator animator1=ValueAnimator.ofInt(lineInput.getWidth(),lineWidth);
        animator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int v=(int)animation.getAnimatedValue();
                ViewGroup.LayoutParams params=lineInput.getLayoutParams();
                params.width=v;
                lineInput.setLayoutParams(params);
            }
        });

        animatorRestore.setDuration(700);
        animatorRestore.setInterpolator(new LinearInterpolator());
        animatorRestore.playSequentially(animator1);
        animatorRestore.start();

        animatorRestore.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                buttonLogin.setVisibility(View.VISIBLE);
                textLoad.setVisibility(View.GONE);
                includeInput.setVisibility(View.VISIBLE);
                textForget.setText(R.string.password_forget_zh);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    //设置动画插值器
    private class SpringInterpolator implements Interpolator {
        int type;
        public SpringInterpolator(int type){
            this.type=type;
        }

        @Override
        public float getInterpolation(float x) {
            switch (type){
                case 1:return getFloat1(x);
                case 2:return getFloat2(x);
                case 3:return getFloat3(x);
                default: return 0;
            }

        }

        private float getFloat1(float x){
            float tension = 1.5f;
            x -= 1.0f;
            return x * x * ((tension + 1) * x + tension) + 1.0f;
        }

        private float getFloat2(float t){
            float p0=0;
            float p1=1;
            float m0=4;
            float m1=4;
            float t2 = t*t;
            float t3 = t2*t;
            return (2*t3 - 3*t2 + 1)*p0 + (t3-2*t2+t)*m0 + (-2*t3+3*t2)*p1 + (t3-t2)*m1;
        }

        private float getFloat3(float x){
            float tension = 2.0f * 1.0f;
            if (x < 0.5) {
                float t=x * 2.0f;
                return 0.5f * (t * t * ((tension + 1) * t - tension));
            }
            else {
                float t=x * 2.0f - 2.0f;
                return 0.5f * ((t * t * ((tension + 1) * t + tension)) + 2.0f);
            }
        }
    }
}
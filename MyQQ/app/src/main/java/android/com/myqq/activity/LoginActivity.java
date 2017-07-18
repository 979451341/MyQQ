package android.com.myqq.activity;

import android.com.myqq.R;
import android.com.myqq.adapter.LoginAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/3.
 */
public class LoginActivity extends BaseActivity {

    private EditText et_name;
    private ImageButton spinner;
    private EditText et_password;
    private ImageButton imgbtn_delete;
    private ArrayList<String> qqName;
    private Button btn_login;

    private PopupWindow pop;
    private LoginAdapter adapter;

    @Override
    public int putView() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {



        et_name = (EditText) findViewById(R.id.et_name);
        spinner = (ImageButton) findViewById(R.id.spinner);
        et_password = (EditText)findViewById(R.id.login_password);
        imgbtn_delete = (ImageButton)findViewById(R.id.login_imgbtn_delete);
        btn_login = (Button)findViewById(R.id.login_btn_login);

        login();
        //当点击右边的倒三角图标时，就在输入框下边显示一个PopupWindow
        spinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                onClick_spinner();
            }
        });

        imgbtn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_password.setText("");
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor=getSharedPreferences("user",
                        MODE_PRIVATE).edit();
                editor.putString("name", et_name.getText() + "");
                editor.putString("password", et_password.getText() + "");
                editor.putBoolean("logined", true);
                editor.commit();

                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("name",et_name.getText()+"");
                intent.putExtra("password", et_password.getText() + "");
                startActivity(intent);
                finish();
            }
        });
    }

    public void login(){
        SharedPreferences pref=getSharedPreferences("user",
                MODE_PRIVATE);
        String name=pref.getString("name", "");
        String password=pref.getString("password", "");
        boolean logined=pref.getBoolean("logined", false);

        boolean intentExtra = getIntent().getBooleanExtra("mainBack",false);
        if(name!=""&&password!=""){
            et_name.setText(name+"");
            et_password.setText(password+"");
        }
        if(name!=""&&password!=""&&logined==true&&intentExtra==false){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            intent.putExtra("name",et_name.getText()+"");
            intent.putExtra("password", et_password.getText() + "");
            startActivity(intent);
            finish();
        }
    }

    public void onClick_spinner(){
        //下拉列表显示

        ListView listView = new ListView(getApplicationContext());
        adapter = new LoginAdapter(getApplicationContext(),qqName);
        listView.setAdapter(adapter);
        listView.setCacheColorHint(0xfafafa);
        //隐藏listView的滚动条
        listView.setVerticalScrollBarEnabled(false);

        pop = new PopupWindow(listView, et_name.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT, true);
        //设置·背景色
        pop.setBackgroundDrawable(new ColorDrawable(0xfafafa));//透明色

        pop.showAsDropDown(et_name, 0, 0);
    }

    @Override
    public void initData() {
        //qq账号
        qqName = new ArrayList<String>();
        for (int i = 10000; i < 10020; i++) {
            qqName.add(i+"");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

       if(keyCode == KeyEvent.KEYCODE_BACK){
           this.finish();

        }
        return true; // 最后，一定要做完以后返回 true，或者在弹出菜单后返回true，其他键返回super，让其他键默认
    }
}

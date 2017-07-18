package android.com.myqq.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/5/3.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(putView());
        initView();
        initData();
    }

    public abstract  int putView();
    public abstract void initView();
    public abstract void initData();
}

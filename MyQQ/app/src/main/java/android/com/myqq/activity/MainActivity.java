package android.com.myqq.activity;

import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.com.myqq.R;
import android.com.myqq.adapter.NavAdapter;
import android.com.myqq.been.ActionItem;
import android.com.myqq.customview.CircleView;
import android.com.myqq.customview.SlidingMenu;
import android.com.myqq.customview.TitlePopup;
import android.com.myqq.fragment.ContactsFragment;
import android.com.myqq.fragment.DynamicFragment;
import android.com.myqq.fragment.MessageFragment;
import android.com.myqq.fragment.PhoneFragment;
import android.com.myqq.service.CountService;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {

    MessageFragment messageFragment;
    PhoneFragment phoneFragment;
    Button btn_more;
    RadioButton rb0,rb1;
    TabLayout main_tabLayout;
    //定义标题栏弹窗按钮
    private TitlePopup titlePopup;
    EditText search_et;
    private static Activity intent;

    private SlidingMenu mMenu;

    private CircleView main_user_log;

    List<String> nav_list_text = new ArrayList<String>();

    ListView nav_list;
    NavAdapter navAdapter;

    LinearLayout nav_changeskin;
    Button nav_changeskin_bt;


    @Override
    public int putView() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {

        initToolbar();
        initSearView();
        initContent();
        initBottom();
        initNav();


    }

    @Override
    public void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            // 在这里做你想做的事情
            backMenu();
            // super.openOptionsMenu();  // 调用这个，就可以弹出菜单
        }else if(keyCode == KeyEvent.KEYCODE_BACK){

                this.finish();
        }
        return true; // 最后，一定要做完以后返回 true，或者在弹出菜单后返回true，其他键返回super，让其他键默认
    }



    public void backMenu(){
        View view = getLayoutInflater().inflate(R.layout.photo_choose_dialog, null);
        Button back=(Button)view.findViewById(R.id.back);
        ((Button)view.findViewById(R.id.main_btn_changeUser)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                intent.putExtra("mainBack",true);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        Dialog dialog = new Dialog(this, R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    //初始化toolbar
    public void initToolbar(){

        mMenu = (SlidingMenu) findViewById(R.id.id_menu);

        main_user_log = (CircleView)findViewById(R.id.main_user_logo);
        main_user_log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenu.toggle();
            }
        });


        rb0 = (RadioButton)findViewById(R.id.rb0);
        rb1 = (RadioButton)findViewById(R.id.rb1);

        rb0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                if (messageFragment == null)
                    messageFragment = new MessageFragment();
                transaction.replace(R.id.main_content, messageFragment);
                transaction.commit();
            }
        });

        rb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                if (phoneFragment == null)
                    phoneFragment = new PhoneFragment();
                transaction.replace(R.id.main_content, phoneFragment);
                transaction.commit();
            }
        });


        btn_more = (Button)findViewById(R.id.main_btn_more);
        btn_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titlePopup.show(v);
            }
        });
        //实例化标题栏弹窗
        titlePopup = new TitlePopup(this, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //给标题栏弹窗添加子类
        titlePopup.addAction(new ActionItem(this, "发起聊天", R.drawable.mm_title_btn_compose_normal));
        titlePopup.addAction(new ActionItem(this, "听筒模式", R.drawable.mm_title_btn_receiver_normal));
        titlePopup.addAction(new ActionItem(this, "登录网页", R.drawable.mm_title_btn_keyboard_normal));
        titlePopup.addAction(new ActionItem(this, "扫一扫", R.drawable.mm_title_btn_qrcode_normal));
        titlePopup.setItemOnClickListener(new TitlePopup.OnItemOnClickListener() {
            @Override
            public void onItemClick(ActionItem item, int position) {

                if(position==3){

                }




            }
        });
    }

    public void initSearView(){

        search_et = (EditText) findViewById(R.id.main_search_et);
        search_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search_et.setCursorVisible(true);
            }
        });
    }
    //初始化content
     public void initContent(){

         //启动服务
         startService(new Intent(MainActivity.this, CountService.class));
         intent = this;

         FragmentManager fm = getFragmentManager();
         FragmentTransaction transaction = fm.beginTransaction();
         messageFragment = new MessageFragment();
         transaction.replace(R.id.main_content, messageFragment);
         transaction.commit();

    }

    public void initBottom(){
        main_tabLayout = (TabLayout) findViewById(R.id.main_tabLayout);
        main_tabLayout.addTab(main_tabLayout.newTab().setText("消息").setIcon(R.mipmap.ic_launcher));
        main_tabLayout.addTab(main_tabLayout.newTab().setText("联系人").setIcon(R.mipmap.ic_launcher));
        main_tabLayout.addTab(main_tabLayout.newTab().setText("动态").setIcon(R.mipmap.ic_launcher));
        main_tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment content;
                String search_hint;
                if (tab.getPosition() == 0) {
                    content = new MessageFragment();
                    search_hint = "搜索";
                } else if (tab.getPosition() == 1) {
                    content = new ContactsFragment();
                    search_hint = "搜索";
                } else {
                    content = new DynamicFragment();
                    search_hint = "搜索电影/音乐/商品...";
                }
                search_et.setHint(search_hint);
                FragmentManager fm = getFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.main_content, content);
                transaction.commit();
//选中了tab的逻辑

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
//未选中tab的逻辑
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
//再次选中tab的逻辑
            }
        });
    }

    public void initNav(){
        nav_list = (ListView)findViewById(R.id.nav_list);
        nav_list_text.add("了解会员特权");
        nav_list_text.add("QQ钱包");
        nav_list_text.add("个性装扮");
        nav_list_text.add("我的收藏");
        nav_list_text.add("我的相册");
        nav_list_text.add("我的文件");
        navAdapter = new NavAdapter(MainActivity.this,nav_list_text);
        nav_list.setAdapter(navAdapter);
        nav_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position ==1){
                    Intent intent = new Intent(MainActivity.this,WalletActivity.class);
                    startActivity(intent);
                }
            }
        });

        nav_changeskin_bt=(Button)findViewById(R.id.nav_changeskin_bt);
        nav_changeskin_bt.setText("夜间");

        nav_changeskin_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // onclick_changeSkin();
            }
        });
    }



    public static Activity getMyIntent(){
        return intent;
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        //结束服务
        stopService(new Intent(MainActivity.this, CountService.class));
    }


}

package android.com.myqq.activity;


import android.com.myqq.R;
import android.com.myqq.adapter.WeiXinListViewAdapter;
import android.com.myqq.been.ChatMsgBean;
import android.com.myqq.db.ChatMsgDB;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChatMsgActivity extends BaseActivity implements OnClickListener{
    /** Called when the activity is first created. */

	private Button mBtnSend;
	private EditText mEditTextContent;
	private ListView mListView;
	private WeiXinListViewAdapter mAdapter;


	String contacts_name;
	TextView name_tv;

	ChatMsgDB db;
	List<ChatMsgBean> mDataArrays = new ArrayList<ChatMsgBean>();
	boolean state =false;


	@Override
	public int putView() {
		return R.layout.activity_contacts;
	}


	public void initView()
    {
		contacts_name = getIntent().getStringExtra("name");
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		mListView = (ListView) findViewById(R.id.contacts_listview);
    	mBtnSend = (Button) findViewById(R.id.contacts_btn_send);
    	mBtnSend.setOnClickListener(this);

		name_tv = (TextView)findViewById(R.id.contacts_name_tv);
    	
    	mEditTextContent = (EditText) findViewById(R.id.contacts_et_sendmessage);
    }

    public void initData()
    {
		name_tv.setText(contacts_name);

		db = ChatMsgDB.getInstance(this);
		mDataArrays = db.load();

    	mAdapter = new WeiXinListViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);
		
    }


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.contacts_btn_send:
			send();
		}
	}
	
	private void send()
	{
		String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0)
		{
			ChatMsgBean entity = new ChatMsgBean();
			entity.setDate(getDate());
			entity.setName("键盘舞者");
			entity.setMsgType(state);
			state=!state;
			entity.setText(contString);
			
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			db.save(entity);


			
			mListView.setSelection(mListView.getCount() - 1);
		}
	}
	
    private String getDate() {
        Calendar c = Calendar.getInstance();

        String year = String.valueOf(c.get(Calendar.YEAR));
        String month = String.valueOf(c.get(Calendar.MONTH));
        String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
        String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
        String mins = String.valueOf(c.get(Calendar.MINUTE));
        
        
        StringBuffer sbBuffer = new StringBuffer();
        sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 
        						
        						
        return sbBuffer.toString();
    }
    

    public void head_xiaohei(View v) {
      }
}
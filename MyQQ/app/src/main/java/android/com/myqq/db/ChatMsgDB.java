package android.com.myqq.db;

import android.com.myqq.been.ChatMsgBean;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatMsgDB {

	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "ChatMsg_db";

	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;

	private static ChatMsgDB DB;

	private SQLiteDatabase db;

	private static String[] msgArray = new String[]{"打撸阿", "好，等我", "好了没", "快了，有点卡",
			"感觉这ad貌似很坑啊", "那没办法",
			"开了", "嗯",};

	private static String[] dataArray = new String[]{"2012-09-01 18:00", "2012-09-01 18:10",
			"2012-09-01 18:11", "2012-09-01 18:20",
			"2012-09-01 18:30", "2012-09-01 18:35",
			"2012-09-01 18:40", "2012-09-01 18:50"};
	private final static int COUNT = 8;
	/**
	 * 将构造方法私有化
	 */
	private ChatMsgDB(Context context) {
		ChatMsgOpenHelper dbHelper = new ChatMsgOpenHelper(context,
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 获取CoolWeatherDB的实例。
	 */
	public static synchronized ChatMsgDB getInstance(Context context) {
		if (DB == null) {
			DB = new ChatMsgDB(context);

			if(DB.load().size()==0)
			for(int i = 0; i < COUNT; i++)
			{
				ChatMsgBean entity = new ChatMsgBean();
				entity.setDate(dataArray[i]);
				if (i % 2 == 0)
				{
					entity.setName("Someone");
					entity.setMsgType(true);
				}else{
					entity.setName("键盘舞者");
					entity.setMsgType(false);
				}

				entity.setText(msgArray[i]);
				DB.save(entity);
			}


		}
		return DB;
	}




	public void save(ChatMsgBean been) {
		if (been != null) {
			ContentValues values = new ContentValues();
			values.put("name", been.getName());
			values.put("date", been.getDate());
			values.put("texts", been.getText());
			db.insert("ChatMsg", null, values);
		}
	}



	public List<ChatMsgBean> load() {
		List<ChatMsgBean> list = new ArrayList<ChatMsgBean>();
		Cursor cursor = db
				.query("ChatMsg", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				ChatMsgBean province = new ChatMsgBean();
				province.setName(cursor.getString(cursor
						.getColumnIndex("name")));
				province.setDate(cursor.getString(cursor
						.getColumnIndex("date")));
				province.setText(cursor.getString(cursor
						.getColumnIndex("texts")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}






}
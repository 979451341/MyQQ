package android.com.myqq.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatMsgOpenHelper extends SQLiteOpenHelper {


	/**
	 *  Province表建表语句
	 */
	public static final String CREATE_PROVINCE = "create table ChatMsg ("
				+ "id integer primary key autoincrement, " 
				+ "name text, "
			    + "date text, "
			    + "texts text) ";

	public ChatMsgOpenHelper(Context context, String name, CursorFactory factory,
							 int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(CREATE_PROVINCE);  // 创建Province表
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}

}
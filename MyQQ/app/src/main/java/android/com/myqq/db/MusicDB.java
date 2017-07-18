package android.com.myqq.db;

import android.com.myqq.been.Music;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class MusicDB {

	/**
	 * 数据库名
	 */
	public static final String DB_NAME = "music_db";

	/**
	 * 数据库版本
	 */
	public static final int VERSION = 1;

	private static MusicDB coolWeatherDB;

	private SQLiteDatabase db;

	/**
	 * 将构造方法私有化
	 */
	private MusicDB(Context context) {
		MusicOpenHelper dbHelper = new MusicOpenHelper(context,
				DB_NAME, null, VERSION);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 获取CoolWeatherDB的实例。
	 */
	public synchronized static MusicDB getInstance(Context context) {
		if (coolWeatherDB == null) {
			coolWeatherDB = new MusicDB(context);
		}
		return coolWeatherDB;
	}

	/**
	 * 将Province实例存储到数据库。
	 */
	public void saveProvince(Music province) {
		if (province != null) {
			ContentValues values = new ContentValues();
			values.put("title", province.getTitle());
			values.put("artist", province.getArtist());
			values.put("duration", province.getDuration());
			values.put("thumb_url", province.getThumb_url());
			db.insert("Music", null, values);
		}
	}

	static final String KEY_TITLE = "title";
	static final String KEY_ARTIST = "artist";
	static final String KEY_DURATION = "duration";
	static final String KEY_THUMB_URL = "thumb_url";
	/**
	 * 从数据库读取全国所有的省份信息。
	 */
	public List<Music> loadProvinces() {
		List<Music> list = new ArrayList<Music>();
		Cursor cursor = db
				.query("Music", null, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do {
				Music province = new Music();
				province.setTitle(cursor.getString(cursor
						.getColumnIndex("title")));
				province.setArtist(cursor.getString(cursor
						.getColumnIndex("artist")));
				province.setDuration(cursor.getString(cursor
						.getColumnIndex("duration")));
				province.setThumb_url(cursor.getString(cursor
						.getColumnIndex("thumb_url")));
				list.add(province);
			} while (cursor.moveToNext());
		}
		return list;
	}






}
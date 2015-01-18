package com.itheima.sqlite.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.itheima.sqlite.dao.MyHelper;

public class ItheimaProvider extends ContentProvider {
	private MyHelper helper;
	private UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);	// 创建匹配器对象, 当Uri无法匹配的时候, 得到-1
	private static final int ACCOUNT_ID = 0;
	private static final int ACCOUNT = 1;
	private static final int ORDER = 2;

	@Override
	public boolean onCreate() {		// 创建之后执行
		System.out.println("onCreate");
		helper = new MyHelper(getContext());
		matcher.addURI("ItheimaProvider", "account/#", ACCOUNT_ID);
		matcher.addURI("ItheimaProvider", "account", ACCOUNT);	// 向匹配器中添加可以识别的Uri, 指定结果码
		matcher.addURI("ItheimaProvider", "order", ORDER);
		return false;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		switch (matcher.match(uri)) {	// 使用匹配器识别Uri, 得到预先指定的结果码
			case ACCOUNT:
				SQLiteDatabase db = helper.getWritableDatabase();
				long id = db.insert("account", "_id", values);		// 通过values拼接SQL语句
				getContext().getContentResolver().notifyChange(uri, null);
				db.close();
				return ContentUris.withAppendedId(uri, id);			// 把新记录的id拼在Uri最后, 返回
			case ORDER:
				System.out.println("暂时没有order表");
				return null;
			default:
				throw new IllegalArgumentException("Uri无法识别: " + uri);
		}
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		switch (matcher.match(uri)) {	
			case ACCOUNT_ID:
				long id = ContentUris.parseId(uri);
				selection = "_id=?";
				selectionArgs = new String[]{ id + "" };
			case ACCOUNT:
				SQLiteDatabase db = helper.getWritableDatabase();
				int count = db.delete("account", selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);
				db.close();
				return count;
			default:
				throw new IllegalArgumentException("Uri无法识别: " + uri);
		}
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		switch (matcher.match(uri)) {	
			case ACCOUNT_ID:
				long id = ContentUris.parseId(uri);
				selection = "_id=?";
				selectionArgs = new String[]{ id + "" };
			case ACCOUNT:
				SQLiteDatabase db = helper.getWritableDatabase();
				int count = db.update("account", values, selection, selectionArgs);
				getContext().getContentResolver().notifyChange(uri, null);	// 当数据改变时, 发送通知, 这时ContentObserver就会接收到
				db.close();
				return count;
			default:
				throw new IllegalArgumentException("Uri无法识别: " + uri);
		}
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		switch (matcher.match(uri)) {	
			case ACCOUNT_ID:
				long id = ContentUris.parseId(uri);		// 截取最后一个"/"后面的数字, 转为long
				selection = "_id=?";
				selectionArgs = new String[]{ id + "" };
			case ACCOUNT:
				SQLiteDatabase db = helper.getReadableDatabase();
				Cursor c = db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
				return c;
			default:
				throw new IllegalArgumentException("Uri无法识别: " + uri);
		}
	}
	
	@Override
	public String getType(Uri uri) {		// 获取Uri的MimeType, text/html  text/css   image/jpg   audio/mp3
		switch (matcher.match(uri)) {
			case ACCOUNT_ID:
				return "vnd.android.cursor.item/account";
			case ACCOUNT:
				return "vnd.android.cursor.dir/account";
			default:
				throw new IllegalArgumentException("Uri无法识别: " + uri);
		}
	}

}

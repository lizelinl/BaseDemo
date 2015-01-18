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
	private UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);	// ����ƥ��������, ��Uri�޷�ƥ���ʱ��, �õ�-1
	private static final int ACCOUNT_ID = 0;
	private static final int ACCOUNT = 1;
	private static final int ORDER = 2;

	@Override
	public boolean onCreate() {		// ����֮��ִ��
		System.out.println("onCreate");
		helper = new MyHelper(getContext());
		matcher.addURI("ItheimaProvider", "account/#", ACCOUNT_ID);
		matcher.addURI("ItheimaProvider", "account", ACCOUNT);	// ��ƥ��������ӿ���ʶ���Uri, ָ�������
		matcher.addURI("ItheimaProvider", "order", ORDER);
		return false;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		switch (matcher.match(uri)) {	// ʹ��ƥ����ʶ��Uri, �õ�Ԥ��ָ���Ľ����
			case ACCOUNT:
				SQLiteDatabase db = helper.getWritableDatabase();
				long id = db.insert("account", "_id", values);		// ͨ��valuesƴ��SQL���
				getContext().getContentResolver().notifyChange(uri, null);
				db.close();
				return ContentUris.withAppendedId(uri, id);			// ���¼�¼��idƴ��Uri���, ����
			case ORDER:
				System.out.println("��ʱû��order��");
				return null;
			default:
				throw new IllegalArgumentException("Uri�޷�ʶ��: " + uri);
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
				throw new IllegalArgumentException("Uri�޷�ʶ��: " + uri);
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
				getContext().getContentResolver().notifyChange(uri, null);	// �����ݸı�ʱ, ����֪ͨ, ��ʱContentObserver�ͻ���յ�
				db.close();
				return count;
			default:
				throw new IllegalArgumentException("Uri�޷�ʶ��: " + uri);
		}
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		switch (matcher.match(uri)) {	
			case ACCOUNT_ID:
				long id = ContentUris.parseId(uri);		// ��ȡ���һ��"/"���������, תΪlong
				selection = "_id=?";
				selectionArgs = new String[]{ id + "" };
			case ACCOUNT:
				SQLiteDatabase db = helper.getReadableDatabase();
				Cursor c = db.query("account", projection, selection, selectionArgs, null, null, sortOrder);
				return c;
			default:
				throw new IllegalArgumentException("Uri�޷�ʶ��: " + uri);
		}
	}
	
	@Override
	public String getType(Uri uri) {		// ��ȡUri��MimeType, text/html  text/css   image/jpg   audio/mp3
		switch (matcher.match(uri)) {
			case ACCOUNT_ID:
				return "vnd.android.cursor.item/account";
			case ACCOUNT:
				return "vnd.android.cursor.dir/account";
			default:
				throw new IllegalArgumentException("Uri�޷�ʶ��: " + uri);
		}
	}

}

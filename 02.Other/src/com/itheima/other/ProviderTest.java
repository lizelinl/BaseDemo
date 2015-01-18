package com.itheima.other;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

public class ProviderTest extends AndroidTestCase {

	public void test1() {
		ContentResolver resolver = getContext().getContentResolver();	// ��ȡContentResolver
		Uri uri = Uri.parse("content://ItheimaProvider");				// ָ��ContentProvider��Uri
		resolver.delete(uri, null, null);								// ��ָ��Uri����ɾ������
		resolver.query(uri, null, null, null, null);
	}
	
	public void testInsert() {
		ContentResolver resolver = getContext().getContentResolver();
		Uri uri = Uri.parse("content://ItheimaProvider/account");
		ContentValues values = new ContentValues();
		values.put("name", "insert");
		values.put("balance", 23456);
		System.out.println(resolver.insert(uri, values));	// �õ��ող����Uri
	}
	
	public void testDelete() {
		ContentResolver resolver = getContext().getContentResolver();
		Uri uri = Uri.parse("content://ItheimaProvider/account/117");
		resolver.delete(uri, null, null);
	}
	
	public void testUpdate() {
		ContentResolver resolver = getContext().getContentResolver();
		Uri uri = Uri.parse("content://ItheimaProvider/account/120");
		ContentValues values = new ContentValues();
		values.put("name", "update");
		values.put("balance", 800 );
		resolver.update(uri, values, null, null);
	}
	
	public void testQuery() {
		ContentResolver resolver = getContext().getContentResolver();
		Uri uri = Uri.parse("content://ItheimaProvider/account/100");
		Cursor c = resolver.query(uri, null, null, null, null);
		while (c.moveToNext()) {
			System.out.println(c.getString(c.getColumnIndex("name")) + ": " + c.getInt(c.getColumnIndex("balance")));
		}
		c.close();
	}
	
	public void testGetType() {
		ContentResolver resolver = getContext().getContentResolver();
		System.out.println(resolver.getType(Uri.parse("content://ItheimaProvider/account/100")));	// ������¼, ����item
		System.out.println(resolver.getType(Uri.parse("content://ItheimaProvider/account")));		// ������¼, ����dir
	}
	
}

package com.itheima.contact;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;

public class ContactTest extends AndroidTestCase {
	private Uri rawContactsUri = Uri.parse("content://com.android.contacts/raw_contacts");
	private Uri dataUri = Uri.parse("content://com.android.contacts/data");

	public void testRead() {
		ContentResolver resolver = getContext().getContentResolver();
		
		// 先查raw_contacts表, 得到所有id(contact_id非null)
		Cursor rawContactsCursor = resolver.query(rawContactsUri, new String[] { "_id" }, null, null, null);
		while (rawContactsCursor.moveToNext()) {
			long id = rawContactsCursor.getLong(0);
			// 用id作为条件, 查询data表中的数据
			
			Cursor dataCursor = resolver.query(dataUri, new String[] { "mimetype", "data1" }, "raw_contact_id=?", new String[] { id + "" }, null);
			while (dataCursor.moveToNext()) {
				String mimetype = dataCursor.getString(0);
				String data = dataCursor.getString(1);
				System.out.println(mimetype + ": " + data);
			}
		}
	}
	
	/*
	for (String name : dataCursor.getColumnNames()) {	// 获取所有列名
		System.out.println(name);
	}
	 */
	
	public void testWrite() {
		ContentResolver resolver = getContext().getContentResolver();
		ContentValues values = new ContentValues();
		
		// 先向raw_contacts表写一个id
		Uri resultUri = resolver.insert(rawContactsUri, values);
		long id = ContentUris.parseId(resultUri);
		
		// 用这个id作为data表的raw_contact_id, 再写3条数据
		values.put("raw_contact_id", id);
		values.put("mimetype", "vnd.android.cursor.item/name");
		values.put("data1", "曹睿");
		resolver.insert(dataUri, values);
		
		values.put("mimetype", "vnd.android.cursor.item/phone_v2");
		values.put("data1", "13012345678");
		resolver.insert(dataUri, values);
		
		values.put("mimetype", "vnd.android.cursor.item/email_v2");
		values.put("data1", "sl@hotmail.com");
		resolver.insert(dataUri, values);
	}
	
	public void testWriteBatch() throws Exception {
		ContentResolver resolver = getContext().getContentResolver();
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();		// 创建集合, 用来装多个操作
		
		ContentProviderOperation operation1 = ContentProviderOperation.newInsert(rawContactsUri)		// 创建一个Insert操作的Builder
				.withValue("_id", null)		// 设置要插入列名和数据(列名为_id, 数据为null时自动生成)
				.build();					// 生成插入操作对象
		ContentProviderOperation operation2 = ContentProviderOperation.newInsert(dataUri)
				.withValueBackReference("raw_contact_id", 0)				// 插入的数据是同组第1个操作的结果
				.withValue("mimetype", "vnd.android.cursor.item/name")
				.withValue("data1", "张泽华")
				.build();
		ContentProviderOperation operation3 = ContentProviderOperation.newInsert(dataUri)
				.withValueBackReference("raw_contact_id", 0)
				.withValue("mimetype", "vnd.android.cursor.item/phone_v2")
				.withValue("data1", "13187654321")
				.build();
		ContentProviderOperation operation4 = ContentProviderOperation.newInsert(dataUri)
				.withValueBackReference("raw_contact_id", 0)
				.withValue("mimetype", "vnd.android.cursor.item/email_v2")
				.withValue("data1", "zzh@163.com")
				.build();
		
		operations.add(operation1);
		operations.add(operation2);
		operations.add(operation3);
		operations.add(operation4);
		
		resolver.applyBatch("com.android.contacts", operations);	// 一次性执行集合中的多个操作
	}
	
	
	
	
	
}

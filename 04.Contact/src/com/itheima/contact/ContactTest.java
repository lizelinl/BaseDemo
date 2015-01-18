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
		
		// �Ȳ�raw_contacts��, �õ�����id(contact_id��null)
		Cursor rawContactsCursor = resolver.query(rawContactsUri, new String[] { "_id" }, null, null, null);
		while (rawContactsCursor.moveToNext()) {
			long id = rawContactsCursor.getLong(0);
			// ��id��Ϊ����, ��ѯdata���е�����
			
			Cursor dataCursor = resolver.query(dataUri, new String[] { "mimetype", "data1" }, "raw_contact_id=?", new String[] { id + "" }, null);
			while (dataCursor.moveToNext()) {
				String mimetype = dataCursor.getString(0);
				String data = dataCursor.getString(1);
				System.out.println(mimetype + ": " + data);
			}
		}
	}
	
	/*
	for (String name : dataCursor.getColumnNames()) {	// ��ȡ��������
		System.out.println(name);
	}
	 */
	
	public void testWrite() {
		ContentResolver resolver = getContext().getContentResolver();
		ContentValues values = new ContentValues();
		
		// ����raw_contacts��дһ��id
		Uri resultUri = resolver.insert(rawContactsUri, values);
		long id = ContentUris.parseId(resultUri);
		
		// �����id��Ϊdata���raw_contact_id, ��д3������
		values.put("raw_contact_id", id);
		values.put("mimetype", "vnd.android.cursor.item/name");
		values.put("data1", "���");
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
		ArrayList<ContentProviderOperation> operations = new ArrayList<ContentProviderOperation>();		// ��������, ����װ�������
		
		ContentProviderOperation operation1 = ContentProviderOperation.newInsert(rawContactsUri)		// ����һ��Insert������Builder
				.withValue("_id", null)		// ����Ҫ��������������(����Ϊ_id, ����Ϊnullʱ�Զ�����)
				.build();					// ���ɲ����������
		ContentProviderOperation operation2 = ContentProviderOperation.newInsert(dataUri)
				.withValueBackReference("raw_contact_id", 0)				// �����������ͬ���1�������Ľ��
				.withValue("mimetype", "vnd.android.cursor.item/name")
				.withValue("data1", "����")
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
		
		resolver.applyBatch("com.android.contacts", operations);	// һ����ִ�м����еĶ������
	}
	
	
	
	
	
}

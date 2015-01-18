package com.itheima.sqlite.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.sqlite.bean.Account;

public class NewAccountDao {
	private MyHelper helper;
	
	public NewAccountDao(Context context) {
		helper = new MyHelper(context);
	}

	public long insert(Account a) {
		SQLiteDatabase db = helper.getWritableDatabase();	
		ContentValues values = new ContentValues();
		values.put("name", a.getName());
		values.put("balance", a.getBalance());
		long id = db.insert("account", "_id", values);		// ����һ������, ����id. 
		db.close();		
		return id;
	}

	public int delete(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();	
		int count = db.delete("account", "_id=?", new String[] { id + "" });	// ɾ������
		db.close();		
		return count;
	}

	public int update(Account a) {
		SQLiteDatabase db = helper.getWritableDatabase();	
		ContentValues values = new ContentValues();
		values.put("name", a.getName());
		values.put("balance", a.getBalance());
		int count = db.update("account", values, "_id=?", new String[] { a.getId() + "" });
		db.close();		
		return count;
	}

	public Account query(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();	// �ȵ���getWriteableDatabse(), ��������쳣, �Ϳ�һ��ֻ����
		Cursor c = db.query("account", new String[] { "name", "balance" }, "_id=?", new String[] { id + "" }, null, null, null);
		
		Account a = null;
		if (c.moveToNext()) {	// �жϽ�����Ƿ������һ������, �������, ָ���Զ�����ƶ�
			String name = c.getString(0);							// �ӽ�����л�ȡ����(�����е�������ȡ)
			int balance = c.getInt(c.getColumnIndex("balance"));	// �ӽ�����л�ȡ����(�ȸ���������ȡ����, �ٸ���������ȡ����)
			a = new Account(id, name, balance);	// ��������, ���������õ�������
		}
		c.close();
		db.close();
		return a;	// ���ض���
	}
	
	public List<Account> queryAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("account", null, null, null, null, null, "balance DESC");
		
		List<Account> list = new ArrayList<Account>();
		while (c.moveToNext()) {
			int id = c.getInt(0);			// ��ȡ���еĵ�һ��(������0��ʼ)
			String name = c.getString(1);	
			int balance = c.getInt(2);		
			list.add(new Account(id, name, balance));	// �ѱ��е����ݷ�װ�ɶ���
		}
		c.close();
		db.close();
		return list;
	}
	
	public Cursor queryCursor() {
		SQLiteDatabase db = helper.getReadableDatabase();
		return db.rawQuery("SELECT * FROM account ORDER BY balance DESC", null);
	}
	
	public List<Account> queryPage(int pageSize, int pageNum) {
		String index = (pageNum - 1) * pageSize + "";		// ��ҳʱ����ʼ����
		String count = pageSize + "";						// ��ѯ����������
		List<Account> list = new ArrayList<Account>();
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("account", null, null, null, null, null, null, index + "," + count);
		while (c.moveToNext()) {
			int id = c.getInt(0);			
			String name = c.getString(1);	
			int balance = c.getInt(2);		
			list.add(new Account(id, name, balance));	
		}
		c.close();
		db.close();
		return list;
	}
	
	public int queryCount() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("account", new String[] { "COUNT(*)" }, null, null, null, null, null);
		c.moveToNext();
		int count = c.getInt(0);
		c.close();
		db.close();
		return count;
	}
	
	public void remit(int fromId, int toId, int amount) {
		SQLiteDatabase db = helper.getWritableDatabase();
		db.beginTransaction();			// ��������
		try {
			db.execSQL("UPDATE account SET balance=balance-? WHERE _id=?", new Object[] { amount, fromId });
			db.execSQL("UPDATE account SET balance=balance+? WHERE _id=?", new Object[] { amount, toId });
			db.setTransactionSuccessful();	// ��������ɹ����
		} finally {
			db.endTransaction();			// ��������
			db.close();
		}
	}

}

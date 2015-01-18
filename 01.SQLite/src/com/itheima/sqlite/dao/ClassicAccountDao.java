package com.itheima.sqlite.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.itheima.sqlite.bean.Account;

public class ClassicAccountDao {
	private MyHelper helper;
	
	public ClassicAccountDao(Context context) {
		helper = new MyHelper(context);
	}

	public void insert(Account a) {
		SQLiteDatabase db = helper.getWritableDatabase();	// ��ȡSQLiteDatabase����
		db.execSQL("INSERT INTO account(name, balance) VALUES(?, ?)", new Object[] { a.getName(), a.getBalance() });	// ִ��һ��SQL���
		db.close();		// �ر�
	}

	public void delete(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();	// ��ȡSQLiteDatabase����
		db.execSQL("DELETE FROM account WHERE _id=?", new Object[] { id }); // ִ��
		db.close();		// �ر�
	}

	public void update(Account a) {
		SQLiteDatabase db = helper.getWritableDatabase();	// ��ȡ
		db.execSQL("UPDATE account SET name=?, balance=? WHERE _id=?", new Object[] { a.getName(), a.getBalance(), a.getId() }); // ִ��
		db.close();		// �ر�
	}

	public Account query(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();	// �ȵ���getWriteableDatabse(), ��������쳣, �Ϳ�һ��ֻ����
		Cursor c = db.rawQuery("SELECT name, balance FROM account WHERE _id=?", new String[] { id + "" });		// ִ�в�ѯ���, �õ������
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
		Cursor c = db.rawQuery("SELECT * FROM account", null);	// ��ѯ������������
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
		Cursor c = db.rawQuery("SELECT * FROM account LIMIT ?,?", new String[] { index, count });	
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
		Cursor c = db.rawQuery("SELECT COUNT(*) FROM account", null);
		c.moveToNext();
		int count = c.getInt(0);
		c.close();
		db.close();
		return count;
	}
	
	public void remit(int fromId, int toId, int amount) {
		SQLiteDatabase db = helper.getWritableDatabase();
		try {
			db.beginTransaction();			// ��������
			db.execSQL("UPDATE account SET balance=balance-? WHERE _id=?", new Object[] { amount, fromId });
			db.execSQL("UPDATE account SET balance=balance+? WHERE _id=?", new Object[] { amount, toId });
			db.setTransactionSuccessful();	// ��������ɹ����
		} finally {
			db.endTransaction();			// ��������
			db.close();
		}
	}

}

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
		long id = db.insert("account", "_id", values);		// 插入一条数据, 返回id. 
		db.close();		
		return id;
	}

	public int delete(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();	
		int count = db.delete("account", "_id=?", new String[] { id + "" });	// 删除数据
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
		SQLiteDatabase db = helper.getReadableDatabase();	// 先调用getWriteableDatabse(), 如果出了异常, 就开一个只读的
		Cursor c = db.query("account", new String[] { "name", "balance" }, "_id=?", new String[] { id + "" }, null, null, null);
		
		Account a = null;
		if (c.moveToNext()) {	// 判断结果集是否包含下一条数据, 如果包含, 指针自动向后移动
			String name = c.getString(0);							// 从结果集中获取数据(根据列的索引获取)
			int balance = c.getInt(c.getColumnIndex("balance"));	// 从结果集中获取数据(先根据列名获取索引, 再根据索引获取数据)
			a = new Account(id, name, balance);	// 创建对象, 把数据设置到对象中
		}
		c.close();
		db.close();
		return a;	// 返回对象
	}
	
	public List<Account> queryAll() {
		SQLiteDatabase db = helper.getReadableDatabase();
		Cursor c = db.query("account", null, null, null, null, null, "balance DESC");
		
		List<Account> list = new ArrayList<Account>();
		while (c.moveToNext()) {
			int id = c.getInt(0);			// 获取表中的第一列(索引从0开始)
			String name = c.getString(1);	
			int balance = c.getInt(2);		
			list.add(new Account(id, name, balance));	// 把表中的数据封装成对象
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
		String index = (pageNum - 1) * pageSize + "";		// 翻页时的起始索引
		String count = pageSize + "";						// 查询多少条数据
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
		db.beginTransaction();			// 开启事务
		try {
			db.execSQL("UPDATE account SET balance=balance-? WHERE _id=?", new Object[] { amount, fromId });
			db.execSQL("UPDATE account SET balance=balance+? WHERE _id=?", new Object[] { amount, toId });
			db.setTransactionSuccessful();	// 设置事务成功标记
		} finally {
			db.endTransaction();			// 结束事务
			db.close();
		}
	}

}

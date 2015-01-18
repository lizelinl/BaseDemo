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
		SQLiteDatabase db = helper.getWritableDatabase();	// 获取SQLiteDatabase对象
		db.execSQL("INSERT INTO account(name, balance) VALUES(?, ?)", new Object[] { a.getName(), a.getBalance() });	// 执行一条SQL语句
		db.close();		// 关闭
	}

	public void delete(int id) {
		SQLiteDatabase db = helper.getWritableDatabase();	// 获取SQLiteDatabase对象
		db.execSQL("DELETE FROM account WHERE _id=?", new Object[] { id }); // 执行
		db.close();		// 关闭
	}

	public void update(Account a) {
		SQLiteDatabase db = helper.getWritableDatabase();	// 获取
		db.execSQL("UPDATE account SET name=?, balance=? WHERE _id=?", new Object[] { a.getName(), a.getBalance(), a.getId() }); // 执行
		db.close();		// 关闭
	}

	public Account query(int id) {
		SQLiteDatabase db = helper.getReadableDatabase();	// 先调用getWriteableDatabse(), 如果出了异常, 就开一个只读的
		Cursor c = db.rawQuery("SELECT name, balance FROM account WHERE _id=?", new String[] { id + "" });		// 执行查询语句, 得到结果集
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
		Cursor c = db.rawQuery("SELECT * FROM account", null);	// 查询表中所有数据
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
			db.beginTransaction();			// 开启事务
			db.execSQL("UPDATE account SET balance=balance-? WHERE _id=?", new Object[] { amount, fromId });
			db.execSQL("UPDATE account SET balance=balance+? WHERE _id=?", new Object[] { amount, toId });
			db.setTransactionSuccessful();	// 设置事务成功标记
		} finally {
			db.endTransaction();			// 结束事务
			db.close();
		}
	}

}

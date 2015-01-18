package com.itheima.sqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {	// 自定义类继承SQLiteOpenHelper

	public MyHelper(Context context) {				// 由于父类没有无参构造函数, 定义一个构造函数调用父类有参的构造函数
		/*
		 * 参数1: Context代表应用程序的环境, 用来确定数据库文件的位置
		 * 参数2: 数据文件的名字
		 * 参数3: 用来创建Cursor(结果集)的工厂, 默认传null就可以
		 * 参数4: 数据库的版本, 后期用来更新数据库, 从1开始
		 */
		super(context, "itheima.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {	// 在数据库文件创建之后执行
		System.out.println("onCreate");
		db.execSQL("CREATE TABLE account(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(20))");	// 执行SQL语句, 创建表
		db.execSQL("ALTER TABLE account ADD balance INTEGER");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	// 在数据库版本提升之后执行
		System.out.println("onUpgrade");
	}
	
}

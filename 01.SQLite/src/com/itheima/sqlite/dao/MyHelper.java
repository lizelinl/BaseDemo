package com.itheima.sqlite.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyHelper extends SQLiteOpenHelper {	// �Զ�����̳�SQLiteOpenHelper

	public MyHelper(Context context) {				// ���ڸ���û���޲ι��캯��, ����һ�����캯�����ø����вεĹ��캯��
		/*
		 * ����1: Context����Ӧ�ó���Ļ���, ����ȷ�����ݿ��ļ���λ��
		 * ����2: �����ļ�������
		 * ����3: ��������Cursor(�����)�Ĺ���, Ĭ�ϴ�null�Ϳ���
		 * ����4: ���ݿ�İ汾, ���������������ݿ�, ��1��ʼ
		 */
		super(context, "itheima.db", null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {	// �����ݿ��ļ�����֮��ִ��
		System.out.println("onCreate");
		db.execSQL("CREATE TABLE account(_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR(20))");	// ִ��SQL���, ������
		db.execSQL("ALTER TABLE account ADD balance INTEGER");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {	// �����ݿ�汾����֮��ִ��
		System.out.println("onUpgrade");
	}
	
}

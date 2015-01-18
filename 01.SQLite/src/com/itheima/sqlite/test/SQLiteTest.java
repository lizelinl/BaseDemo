package com.itheima.sqlite.test;

import java.util.List;

import android.test.AndroidTestCase;

import com.itheima.sqlite.bean.Account;
import com.itheima.sqlite.dao.MyHelper;
import com.itheima.sqlite.dao.NewAccountDao;

public class SQLiteTest extends AndroidTestCase {
	// �������е�Context�������ڲ����ഴ��֮��(���캯��ִ��֮��), ������Զ�����setContext()�����, ����ڳ�Ա����λ�þ�getContext()���ò���
	private NewAccountDao dao;
	
	@Override
	protected void setUp() throws Exception {		// ���Է���ִ��֮ǰִ��
		dao = new NewAccountDao(getContext());
	}
	
	@Override
	protected void tearDown() throws Exception {	// ���Է���ִ��֮��ִ��
	}

	public void testCreateDB() {
		new MyHelper(getContext()).getWritableDatabase(); // ��ȡ���ݿ����
		/*
		 * ���1: ���ݿ��ļ�������, �����ļ�, �����ݿ�����(�õ�SQLiteDatabase����), ִ��onCreate()���� 
		 * ���2: ���ݿ��ļ�����, �汾��û��, �����ݿ����� 
		 * ���3: ���ݿ��ļ�����, �汾������, �������ݿ�, �����ݿ�����,ִ��onUpgrade()���� 
		 * ���4: ���ݿ��ļ�����, �汾�Ž���, ִ��onDowngrade()����, ������Ĭ�ϻ��׳�һ���쳣
		 */
	}

	public void testInsert() {
		for (int i = 0; i < 20; i++) {
			dao.insert(new Account("Test" + i, 500));
		}
	}
	
	public void testDelete() {
		System.out.println(dao.delete(109));
	}
	
	public void testUpdate() {
		dao.update(new Account(108, "��ӡ��", 10000));
		dao.update(new Account(110, "��Ǭ", 10000));
	}
	
	public void testQuery() {
		System.out.println(dao.query(3));
		System.out.println(dao.query(4));
		System.out.println(dao.query(5));
		System.out.println(dao.query(6));
	}
	
	public void testQueryAll() {
		List<Account> list = dao.queryAll();
		for (Account account : list) {
			System.out.println(account);
		}
	}
	
	public void testQueryPage() {
		List<Account> list = dao.queryPage(20, 2);
		for (Account account : list) {
			System.out.println(account);
		}
	}
	
	public void testQueryCount() {
		System.out.println(dao.queryCount());
	}
	
	public void testReimit() {
		dao.remit(1, 2, 100);
	}

}

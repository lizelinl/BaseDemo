package com.itheima.sqlite.test;

import java.util.List;

import android.test.AndroidTestCase;

import com.itheima.sqlite.bean.Account;
import com.itheima.sqlite.dao.MyHelper;
import com.itheima.sqlite.dao.NewAccountDao;

public class SQLiteTest extends AndroidTestCase {
	// 测试类中的Context对象是在测试类创建之后(构造函数执行之后), 虚拟机自动调用setContext()传入的, 如果在成员变量位置就getContext()则拿不到
	private NewAccountDao dao;
	
	@Override
	protected void setUp() throws Exception {		// 测试方法执行之前执行
		dao = new NewAccountDao(getContext());
	}
	
	@Override
	protected void tearDown() throws Exception {	// 测试方法执行之后执行
	}

	public void testCreateDB() {
		new MyHelper(getContext()).getWritableDatabase(); // 获取数据库对象
		/*
		 * 情况1: 数据库文件不存在, 创建文件, 打开数据库连接(得到SQLiteDatabase对象), 执行onCreate()方法 
		 * 情况2: 数据库文件存在, 版本号没变, 打开数据库连接 
		 * 情况3: 数据库文件存在, 版本号提升, 升级数据库, 打开数据库连接,执行onUpgrade()方法 
		 * 情况4: 数据库文件存在, 版本号降低, 执行onDowngrade()方法, 方法中默认会抛出一个异常
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
		dao.update(new Account(108, "李印东", 10000));
		dao.update(new Account(110, "朴乾", 10000));
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

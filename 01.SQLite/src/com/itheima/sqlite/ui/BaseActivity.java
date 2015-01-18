package com.itheima.sqlite.ui;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.sqlite.R;
import com.itheima.sqlite.bean.Account;
import com.itheima.sqlite.dao.ClassicAccountDao;

public class BaseActivity extends Activity {

	private List<Account> list;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        
        System.out.println(Environment.getExternalStorageDirectory());
        System.out.println(getExternalFilesDir(Environment.DIRECTORY_MOVIES));	// 获取当前应用的音乐文件夹
        
        ClassicAccountDao dao = new ClassicAccountDao(this);
		list = dao.queryAll();
		
		ListView lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(new MyAdapter());
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Account a = (Account) parent.getItemAtPosition(position);	// Account
				Toast.makeText(BaseActivity.this, a.toString(), Toast.LENGTH_SHORT).show();
				System.out.println(id);
			}
		});
    }
	
	private class MyAdapter extends BaseAdapter {	// 自定义类继承BaseAdapter, 定义怎么把数据封装成View, 添加到ListView
		public int getCount() {						// 获取条目的数量
			return list.size();
		}
		public Object getItem(int position) {		// 获取指定位置上的数据
			return list.get(position);
		}
		public long getItemId(int position) {		// 获取指定条目的id	
			return list.get(position).getId();
		}
		public View getView(int position, View convertView, ViewGroup parent) {	// 获取指定位置上的条目视图
			System.out.println("getView: " + position);
			
			// 根据定义好的布局文件, 创建一个View(LinearLayout)
			View v = convertView == null ? View.inflate(BaseActivity.this, R.layout.account_item, null) : convertView;
			TextView idTV = (TextView) v.findViewById(R.id.idTV);
			TextView nameTV = (TextView) v.findViewById(R.id.nameTV);
			TextView balanceTV = (TextView) v.findViewById(R.id.balanceTV);
			
			// 获取到集合中指定位置上的对象, 把对象中的数据装入TextView
			final Account a = list.get(position);
			idTV.setText(a.getId() + "");
			nameTV.setText(a.getName());
			balanceTV.setText(a.getBalance() + "");
			
			nameTV.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(BaseActivity.this, a.getName(), Toast.LENGTH_SHORT).show();
				}
			});
			
			balanceTV.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					Toast.makeText(BaseActivity.this, a.getBalance() + "", Toast.LENGTH_SHORT).show();
				}
			});
			
			return v;
		}
	}
    
}

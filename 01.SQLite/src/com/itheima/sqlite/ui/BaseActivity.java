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
        System.out.println(getExternalFilesDir(Environment.DIRECTORY_MOVIES));	// ��ȡ��ǰӦ�õ������ļ���
        
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
	
	private class MyAdapter extends BaseAdapter {	// �Զ�����̳�BaseAdapter, ������ô�����ݷ�װ��View, ��ӵ�ListView
		public int getCount() {						// ��ȡ��Ŀ������
			return list.size();
		}
		public Object getItem(int position) {		// ��ȡָ��λ���ϵ�����
			return list.get(position);
		}
		public long getItemId(int position) {		// ��ȡָ����Ŀ��id	
			return list.get(position).getId();
		}
		public View getView(int position, View convertView, ViewGroup parent) {	// ��ȡָ��λ���ϵ���Ŀ��ͼ
			System.out.println("getView: " + position);
			
			// ���ݶ���õĲ����ļ�, ����һ��View(LinearLayout)
			View v = convertView == null ? View.inflate(BaseActivity.this, R.layout.account_item, null) : convertView;
			TextView idTV = (TextView) v.findViewById(R.id.idTV);
			TextView nameTV = (TextView) v.findViewById(R.id.nameTV);
			TextView balanceTV = (TextView) v.findViewById(R.id.balanceTV);
			
			// ��ȡ��������ָ��λ���ϵĶ���, �Ѷ����е�����װ��TextView
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

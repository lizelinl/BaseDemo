package com.itheima.sqlite.ui;

import java.util.List;

import android.app.Activity;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.itheima.sqlite.R;
import com.itheima.sqlite.bean.Account;
import com.itheima.sqlite.dao.ClassicAccountDao;

public class RefreshActivity extends Activity {

	private List<Account> list;
	private int pageNum = 1;
	private PullToRefreshListView ptrlv;
	private ClassicAccountDao dao;
	private MyAdapter adapter;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        
        dao = new ClassicAccountDao(this);
        list = dao.queryPage(20, pageNum);
        adapter = new MyAdapter();
		
        ptrlv = (PullToRefreshListView) findViewById(R.id.ptrlv);
		ptrlv.setAdapter(adapter);
        ptrlv.setMode(Mode.PULL_FROM_END);
        
        ptrlv.setOnRefreshListener(new OnRefreshListener<ListView>() {			// ���ˢ�¼�����, �����Ҫ����2������, ��OnRefreshListener2
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {	// ��ˢ�µ�ʱ��ִ��
				System.out.println("onRefresh");
				new GetDataTask().execute();
			}
		});
        
        // ע��һ��ContentObserver(����һ��������), ������Ҫʵ��onChange()����, �������޸ĵ�ʱ��, ���Զ�ִ��onChange()����
        Uri uri = Uri.parse("content://ItheimaProvider");
        /**
         * 1 ��Ҫ�۲��Uri(��Ҫ��UriMatcher��ע�ᣬ�����UriҲû��������)
         * 2 notifyForDescendents  Ϊfalse ��ʾ��ȷƥ�䣬��ֻƥ���Uri
                                                                                          Ϊtrue ��ʾ����ͬʱƥ����������Uri
         */
		getContentResolver().registerContentObserver(uri , true, new MyObserver());
    }
	
	private class MyObserver extends ContentObserver {
		public MyObserver() {
			super(new Handler());
		}
		public void onChange(boolean selfChange) {
			list = dao.queryPage(20, pageNum);		// ���²�ѯ����
			adapter.notifyDataSetChanged();			// ˢ��ListView(�����ݺ�ListView��ʾ������ͬ��)
		}
	}
	/** 
	 * 1,��������ִ�е��������������
	 * 2,��̨������ɵĽ���ֵ������
	 * 3,��ִ̨��������ɺ󷵻ؽ��������
	 * @author Administrator 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		protected String[] doInBackground(Void... params) {		// �������߳�, ִ�и÷���
			System.out.println("doInBackground");
			return null;
		}	
	    protected void onPostExecute(String[] result) {			// ִ��doInBackground()֮��, �����߳�ִ�и÷���
	    	System.out.println("onPostExecute");
	    	List<Account> newData = dao.queryPage(20, ++pageNum);	// ��ѯ��һҳ������
	    	Toast.makeText(getApplicationContext(), newData.size() + "", Toast.LENGTH_SHORT).show();
	    	list.addAll(newData);									// ��������ӵ�list��
	    	adapter.notifyDataSetChanged();							// ˢ�½���
	    	ptrlv.onRefreshComplete();
	    }
	}
	
	private class MyAdapter extends BaseAdapter {
		public int getCount() {
			return list.size();
		}
		public Object getItem(int position) {
			return null;
		}
		public long getItemId(int position) {
			return 0;
		}
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView != null ? convertView : View.inflate(RefreshActivity.this, R.layout.account_item, null);
			TextView idTV = (TextView) v.findViewById(R.id.idTV);
			TextView nameTV = (TextView) v.findViewById(R.id.nameTV);
			TextView balanceTV = (TextView) v.findViewById(R.id.balanceTV);
			
			Account a = list.get(position);
			idTV.setText(a.getId() + "");
			nameTV.setText(a.getName());
			balanceTV.setText(a.getBalance() + "");
			return v;
		}
	}
	
    
}

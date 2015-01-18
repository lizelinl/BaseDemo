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
        
        ptrlv.setOnRefreshListener(new OnRefreshListener<ListView>() {			// 添加刷新监听器, 如果需要监听2个方向, 用OnRefreshListener2
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {	// 当刷新的时候执行
				System.out.println("onRefresh");
				new GetDataTask().execute();
			}
		});
        
        // 注册一个ContentObserver(类似一个监听器), 其中需要实现onChange()方法, 在数据修改的时候, 会自动执行onChange()方法
        Uri uri = Uri.parse("content://ItheimaProvider");
        /**
         * 1 需要观察的Uri(需要在UriMatcher里注册，否则该Uri也没有意义了)
         * 2 notifyForDescendents  为false 表示精确匹配，即只匹配该Uri
                                                                                          为true 表示可以同时匹配其派生的Uri
         */
		getContentResolver().registerContentObserver(uri , true, new MyObserver());
    }
	
	private class MyObserver extends ContentObserver {
		public MyObserver() {
			super(new Handler());
		}
		public void onChange(boolean selfChange) {
			list = dao.queryPage(20, pageNum);		// 重新查询数据
			adapter.notifyDataSetChanged();			// 刷新ListView(把数据和ListView显示的内容同步)
		}
	}
	/** 
	 * 1,启动任务执行的输入参数的类型
	 * 2,后台任务完成的进度值的类型
	 * 3,后台执行任务完成后返回结果的类型
	 * @author Administrator 
	 */
	private class GetDataTask extends AsyncTask<Void, Void, String[]> {
		protected String[] doInBackground(Void... params) {		// 开启新线程, 执行该方法
			System.out.println("doInBackground");
			return null;
		}	
	    protected void onPostExecute(String[] result) {			// 执行doInBackground()之后, 在主线程执行该方法
	    	System.out.println("onPostExecute");
	    	List<Account> newData = dao.queryPage(20, ++pageNum);	// 查询下一页的数据
	    	Toast.makeText(getApplicationContext(), newData.size() + "", Toast.LENGTH_SHORT).show();
	    	list.addAll(newData);									// 把数据添加到list中
	    	adapter.notifyDataSetChanged();							// 刷新界面
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

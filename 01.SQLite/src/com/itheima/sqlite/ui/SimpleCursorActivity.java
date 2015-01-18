package com.itheima.sqlite.ui;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.itheima.sqlite.R;
import com.itheima.sqlite.dao.ClassicAccountDao;

@SuppressWarnings("deprecation")
public class SimpleCursorActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        
        ClassicAccountDao dao = new ClassicAccountDao(this);
        Cursor c = dao.queryCursor();
        
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(this, R.layout.account_item, c
				, new String[] { "_id", "name", "balance" }					// 结果集中的列名(必须包含_id)
				, new int[] { R.id.idTV, R.id.nameTV, R.id.balanceTV });	// 指定列上的数据对应哪些TextView
		
		ListView lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Cursor c = (Cursor) parent.getItemAtPosition(position);		// Cursor.moveToPosition(position)
				Toast.makeText(getApplicationContext(), c.getString(1), Toast.LENGTH_SHORT).show();
			}
		});
    }
    
}

package com.itheima.sqlite.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.itheima.sqlite.R;

public class ArrayActivity extends Activity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_standard);
        
        // 根据ID获取ListView
        ListView lv = (ListView) findViewById(R.id.lv);
        
        // 要添加的数据
        String[] arr = { "条目1", "条目2", "条目3", "条目4", "条目5", "条目6", "条目7", "条目8", "条目9", "条目10" };
        
        // 创建适配器(把数据封装成View, 添加到ListView的工具)
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.item, R.id.tv, arr);
        
        // 向ListView中添加条目(设置适配器)
        lv.setAdapter(adapter);
        
        // 处理点击事件
        lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				String s = (String) parent.getItemAtPosition(position);				// 获取指定条目上的数据
				Toast.makeText(ArrayActivity.this, s, Toast.LENGTH_SHORT).show();	// 弹出
			}
		});
    }
    
}

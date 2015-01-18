package com.itheima.notification;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnMultiChoiceClickListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Process;
import android.os.SystemClock;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@SuppressWarnings("deprecation")
	public void sendNotification(View v) {
		// ����֪ͨ
		Notification n = new Notification(R.drawable.ic_launcher, "�������", System.currentTimeMillis());
		
		// ����֪ͨ����
		Intent intent = new Intent();
		intent.setClassName("com.itheima.download", "com.itheima.download.MainActivity");
		PendingIntent pi = PendingIntent.getActivity(this, 100, intent, PendingIntent.FLAG_ONE_SHOT);	// Context, ������, ���ʱ����ͼ, һ��
		n.setLatestEventInfo(this, "FeiQ.exe�������", "FeiQ.exe�������, ��ʱ2��35��.", pi);				// Context, ����, �ı�, ������ͼ
		n.flags = Notification.FLAG_AUTO_CANCEL;	// �ù�֮���Զ�ȡ��
		n.sound = Uri.parse("file:///mnt/sdcard/jiaodizhu.mp3");
		
		// ��ȡ֪ͨ������
		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		
		// �ù���������֪ͨ
		manager.notify(Process.myPid(), n);
	}
	
	public void normal(View v) {
		OnClickListener listener = new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						Toast.makeText(getApplicationContext(), "����", Toast.LENGTH_SHORT).show();
						break;
					case DialogInterface.BUTTON_NEUTRAL:
						Toast.makeText(getApplicationContext(), "�Ժ�", Toast.LENGTH_SHORT).show();
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						Toast.makeText(getApplicationContext(), "�ܾ�", Toast.LENGTH_SHORT).show();
						break;
				}
			}
		};
		
		new Builder(this)
			.setTitle("�Ƿ����ظ���")
			.setMessage("1.�ٶȸ���\r\n2.���๦��\r\n3.�޸�BUG")
			.setPositiveButton("����", listener)
			.setNeutralButton("�Ժ�", listener)
			.setNegativeButton("�ܾ�", listener)
			.setCancelable(false)	// �Ƿ����ȡ��
			.show();
	}
	
	public void single(View v) {
		final String[] items = { "Java", ".Net", "PHP", "��ҳƽ�����", "iOS", "C++" };
		
		OnClickListener listener = new OnClickListener() {
			private int index;
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						Toast.makeText(getApplicationContext(), items[index], Toast.LENGTH_SHORT).show();
						break;
					default:
						index = which;
				}
			}
		};
		
		new Builder(this)
			.setTitle("ѡ��Ҫѧϰ�Ŀγ�")
			.setSingleChoiceItems(items, -1, listener)	// ��������, Ĭ��ѡ��, ������
			.setPositiveButton("ȷ��", listener)
			.show();
	}
	
	
	public void multi(View v) {
		final String[] items = { "����", "�Ⱦ�", "��ͷ" };
		final boolean[] checkedItems = { false, false, false };
		
		OnMultiChoiceClickListener multiListener = new OnMultiChoiceClickListener() {
			public void onClick(DialogInterface dialog, int which, boolean isChecked) {
				System.out.println(items[which] + ": " + isChecked);
			}
		};
		
		OnClickListener positiveListener = new OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < checkedItems.length; i++) {
					sb.append(checkedItems[i] ? items[i] + " " : "");
				}
				Toast.makeText(getApplicationContext(), sb, Toast.LENGTH_SHORT).show();
			}
		};
		
		new Builder(this)
			.setTitle("��ѡ�����İ���")
			.setMultiChoiceItems(items , checkedItems, multiListener)
			.setPositiveButton("ȷ��", positiveListener)
			.show();
	}
	
	public void progress(View v) {
		final ProgressDialog dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialog.setTitle("��������, ���Ժ�...");
		dialog.show();
		dialog.setMax(321);			// ���̶�
		
		new Thread(){
			public void run() {
				for (int i = 1; i <= 321; i++) {
					dialog.setProgress(i);	// ���ý���(���������߳����޸Ľ���)
					SystemClock.sleep(10);
				}
				dialog.dismiss();	// ����
			}
		}.start();
		
		/*
		 * ���������Ľ��涼���������߳��в���. 
		 * 		ֻҪʹ�ý�����, һ���Ǻ�ʱ����. 
		 * 		��ʱ����һ���������߳��е�.
		 * 		������ͨ�����������߳���ʹ��
		 * 		��׿�Ѿ�����������Handler�Ĳ��� 
		 * 
		 * ProgressBar, SeekBar
		 */
		
	}

}

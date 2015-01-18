package com.itheima.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.Header;

import android.content.Context;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class DownloadTask {
	private String targetUrl;
	private File localFile;
	private int threadAmount;
	private Context context;
	private ProgressBar downloadPB;
	private TextView percentTV;
	private TextView progressTV;

	private long contentLength;
	private long threadLength;
	private ArrayList<File> cacheList = new ArrayList<File>();
	private ArrayList<MyFileHandler> handlerList = new ArrayList<MyFileHandler>();
	
	/**
	 * ������������
	 * @param targetUrl		Ŀ���ַ
	 * @param localFile		����·��
	 * @param threadAmount	�߳�����
	 * @param context		Ӧ�û���
	 * @param downloadPB 
	 * @param progressTV 
	 * @param percentTV 
	 */
	public DownloadTask(String targetUrl, File localFile, int threadAmount, Context context, ProgressBar downloadPB, TextView percentTV, TextView progressTV) {
		super();
		this.targetUrl = targetUrl;
		this.localFile = localFile;
		this.threadAmount = threadAmount;
		this.context = context;
		this.downloadPB = downloadPB;
		this.percentTV = percentTV;
		this.progressTV = progressTV;
	}

	public void execute() {
		new AsyncHttpClient().head(targetUrl, new AsyncHttpResponseHandler() {		// ����URL�����������һ������, ��ȡ��Ӧͷ
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				caculateLength(headers);	// ��ȡ�ļ��ܳ���, ����ÿ���̸߳���ĳ���
				beginDownload();			// ��������߳�����
			}
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			}
		});
	}
	
	private void caculateLength(Header[] headers) {		
		for (Header header : headers) 
			if ("Content-Length".equals(header.getName())) 
				contentLength = Long.parseLong(header.getValue());			// ����Ӧͷ�л�ȡ�ļ��ܴ�С
		threadLength = (contentLength + threadAmount - 1) / threadAmount;	// ����ÿ���̸߳������
		downloadPB.setMax((int) contentLength);		// ���ý����������̶�Ϊ�ļ��ܴ�С
	}
	
	private void beginDownload() {
		for (int i = 0; i < threadAmount; i++) {	// ����ѭ��, ÿ��ѭ������1���߳�����
			File cacheFile = new File(context.getCacheDir(), localFile.getName() + ".temp" + i);	// ������ʱ�ļ���·��
			cacheList.add(cacheFile);				// ���ļ�װ�뼯��
			
			long begin = i * threadLength + cacheFile.length();		// ���㿪ʼλ��
			long end = i * threadLength + threadLength - 1;			// �������λ��
			System.out.println(i + ": " + begin + " - " + end);
			if (begin > end)
				continue;
			
			MyFileHandler fileHandler = new MyFileHandler(cacheFile) {			// ��������, ��������, �洢����ʱ�ļ�
				private long ms;
				public void onSuccess(int statusCode, Header[] headers, File file) {
					System.out.println(Thread.currentThread().getName() + ": " + file + " �������");
					checkOtherThread();				// ��������߳��Ƿ��������, ���������, �ϲ��ļ�
				}
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
				}
				public void onProgress(int bytesWritten, int totalSize) {	// ����Ƶ�ʷǳ���
					if (System.currentTimeMillis() - ms > 100) {
						ms = System.currentTimeMillis();
						updateProgress();
					}
				}
			};
			handlerList.add(fileHandler);
			
			AsyncHttpClient client = new AsyncHttpClient();
			client.addHeader("Range", "bytes=" + begin + "-" + end);		// �����������ݵķ�Χ
			client.get(targetUrl, fileHandler);
		}
	}
	
	private void updateProgress() {
		long finishLength = getFinishLength();
		if (finishLength == 0)
			return;
		downloadPB.setProgress((int) finishLength);
		percentTV.setText(finishLength * 1000 / contentLength / 10f + "%"); 
		progressTV.setText(finishLength + "/" + contentLength);
	}
	
	private long getFinishLength() {
		long finishLength = 0;
		for (File cacheFile : cacheList) 			// ����������ʱ�ļ�
			finishLength += cacheFile.length();		// ͳ���ܳ���
		return finishLength;
	}

	private void checkOtherThread() {
		if (getFinishLength() == contentLength) { 	// ����ļ����Ⱥ�ContentLength���, �����������
			updateProgress();
			merge();								// �ϲ����е���ʱ�ļ�
		}
	}

	private void merge() {
		try {
			FileOutputStream out = new FileOutputStream(localFile);		
			for (File cacheFile : cacheList) {								// ����������ʱ�ļ�
				FileInputStream in = new FileInputStream(cacheFile);		
				byte[] buffer = new byte[8192];
				int length;
				while ((length = in.read(buffer)) != -1)	// ��ȡÿ���ļ�
					out.write(buffer, 0, length);			// ��ÿ���ļ������ݶ�д����localFile
				in.close();
				cacheFile.delete();
			}
			out.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void stop() {
		for (MyFileHandler fileHandler : handlerList) {
			fileHandler.cancel();
		}
	}
}

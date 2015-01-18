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
	 * 创建下载任务
	 * @param targetUrl		目标地址
	 * @param localFile		本地路径
	 * @param threadAmount	线程数量
	 * @param context		应用环境
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
		new AsyncHttpClient().head(targetUrl, new AsyncHttpResponseHandler() {		// 根据URL向服务器发起一个请求, 获取响应头
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				caculateLength(headers);	// 获取文件总长度, 计算每个线程负责的长度
				beginDownload();			// 开启多个线程下载
			}
			public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
			}
		});
	}
	
	private void caculateLength(Header[] headers) {		
		for (Header header : headers) 
			if ("Content-Length".equals(header.getName())) 
				contentLength = Long.parseLong(header.getValue());			// 从相应头中获取文件总大小
		threadLength = (contentLength + threadAmount - 1) / threadAmount;	// 计算每个线程负责多少
		downloadPB.setMax((int) contentLength);		// 设置进度条的最大刻度为文件总大小
	}
	
	private void beginDownload() {
		for (int i = 0; i < threadAmount; i++) {	// 定义循环, 每次循环启动1个线程下载
			File cacheFile = new File(context.getCacheDir(), localFile.getName() + ".temp" + i);	// 定义临时文件的路径
			cacheList.add(cacheFile);				// 把文件装入集合
			
			long begin = i * threadLength + cacheFile.length();		// 计算开始位置
			long end = i * threadLength + threadLength - 1;			// 计算结束位置
			System.out.println(i + ": " + begin + " - " + end);
			if (begin > end)
				continue;
			
			MyFileHandler fileHandler = new MyFileHandler(cacheFile) {			// 发送请求, 下载数据, 存储到临时文件
				private long ms;
				public void onSuccess(int statusCode, Header[] headers, File file) {
					System.out.println(Thread.currentThread().getName() + ": " + file + " 下载完成");
					checkOtherThread();				// 检查其他线程是否下载完毕, 如果都完了, 合并文件
				}
				public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
				}
				public void onProgress(int bytesWritten, int totalSize) {	// 运行频率非常高
					if (System.currentTimeMillis() - ms > 100) {
						ms = System.currentTimeMillis();
						updateProgress();
					}
				}
			};
			handlerList.add(fileHandler);
			
			AsyncHttpClient client = new AsyncHttpClient();
			client.addHeader("Range", "bytes=" + begin + "-" + end);		// 设置请求数据的范围
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
		for (File cacheFile : cacheList) 			// 遍历所有临时文件
			finishLength += cacheFile.length();		// 统计总长度
		return finishLength;
	}

	private void checkOtherThread() {
		if (getFinishLength() == contentLength) { 	// 如果文件长度和ContentLength相等, 代表下载完成
			updateProgress();
			merge();								// 合并所有的临时文件
		}
	}

	private void merge() {
		try {
			FileOutputStream out = new FileOutputStream(localFile);		
			for (File cacheFile : cacheList) {								// 遍历所有临时文件
				FileInputStream in = new FileInputStream(cacheFile);		
				byte[] buffer = new byte[8192];
				int length;
				while ((length = in.read(buffer)) != -1)	// 读取每个文件
					out.write(buffer, 0, length);			// 把每个文件的数据都写出到localFile
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

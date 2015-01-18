package com.itheima.nettext;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.accounts.NetworkErrorException;

public class TextService {

	public String getText(String path) throws Exception {
		URL url = new URL(path);												// 把路径封装成URL对象
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();		// 打开连接对象(还未联网)
		conn.setConnectTimeout(5000);					// 设置超时时间, 如果连接超过5000毫秒未响应, 就抛出异常
		
		int code = conn.getResponseCode();				// 获取响应码(真正联网)
		if (code == 200) {								// 如果成功
			InputStream in = conn.getInputStream();		// 获取输入流
			ByteArrayOutputStream out = new ByteArrayOutputStream();	// 可以写出数据到内存的输出流
			byte[] buffer = new byte[8192];
			int length;
			while ((length = in.read(buffer)) != -1)	// 从网络读取数据
				out.write(buffer, 0, length);			// 向内存写出数据
			in.close();
			out.close();
			conn.disconnect();
			
			byte[] data = out.toByteArray();	// 把写到内存的数据读取出来
			String text = new String(data);		// 解码为字符串(默认UTF-8)
			return text;
		}
		
		conn.disconnect();
		throw new NetworkErrorException("网络错误: " + code);
	}

}

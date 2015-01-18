package com.itheima.nettext;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.accounts.NetworkErrorException;

public class TextService {

	public String getText(String path) throws Exception {
		URL url = new URL(path);												// ��·����װ��URL����
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();		// �����Ӷ���(��δ����)
		conn.setConnectTimeout(5000);					// ���ó�ʱʱ��, ������ӳ���5000����δ��Ӧ, ���׳��쳣
		
		int code = conn.getResponseCode();				// ��ȡ��Ӧ��(��������)
		if (code == 200) {								// ����ɹ�
			InputStream in = conn.getInputStream();		// ��ȡ������
			ByteArrayOutputStream out = new ByteArrayOutputStream();	// ����д�����ݵ��ڴ�������
			byte[] buffer = new byte[8192];
			int length;
			while ((length = in.read(buffer)) != -1)	// �������ȡ����
				out.write(buffer, 0, length);			// ���ڴ�д������
			in.close();
			out.close();
			conn.disconnect();
			
			byte[] data = out.toByteArray();	// ��д���ڴ�����ݶ�ȡ����
			String text = new String(data);		// ����Ϊ�ַ���(Ĭ��UTF-8)
			return text;
		}
		
		conn.disconnect();
		throw new NetworkErrorException("�������: " + code);
	}

}

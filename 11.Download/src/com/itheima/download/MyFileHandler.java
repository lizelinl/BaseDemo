package com.itheima.download;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;

import android.content.Context;

import com.loopj.android.http.FileAsyncHttpResponseHandler;

public abstract class MyFileHandler extends FileAsyncHttpResponseHandler {
	private boolean isCancel;

	public MyFileHandler(Context context) {
		super(context);
	}

	public MyFileHandler(File file) {
		super(file);
	}

	@Override
    protected byte[] getResponseData(HttpEntity entity) throws IOException {
        if (entity != null) {
            InputStream instream = entity.getContent();
            long contentLength = entity.getContentLength();
            FileOutputStream buffer = new FileOutputStream(getTargetFile(), true);
            if (instream != null) {
                try {
                    byte[] tmp = new byte[BUFFER_SIZE];
                    int l, count = 0;
                    // do not send messages if request has been cancelled
                    while ((l = instream.read(tmp)) != -1 && !Thread.currentThread().isInterrupted()) {
                    	if (isCancel)
                    		break;
                        count += l;
                        buffer.write(tmp, 0, l);
                        sendProgressMessage(count, (int) contentLength);
                    }
                } finally {
                    instream.close();
                    buffer.flush();
                    buffer.close();
                }
            }
        }
        return null;
    }
	
	public void cancel() {
		isCancel = true;
	}

}

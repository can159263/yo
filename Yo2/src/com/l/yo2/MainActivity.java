package com.l.yo2;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import org.apache.commons.io.IOUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void yoBtnClick(View view) {
		
		final String serverip = this.getString(R.string.server_ip);
		final int serverport = Integer.parseInt(this.getString(R.string.server_port));
		
		
		Thread t = new Thread(){

			@Override
			public void run() {
				
				super.run();
				
				Socket socket = null;
				InputStream is = null;
				OutputStream os = null;
				try {
					socket = new Socket(serverip, serverport);
					os = socket.getOutputStream();
					is = socket.getInputStream();
					IOUtils.write("YO", os);
					socket.shutdownOutput();

					List<String> list = IOUtils.readLines(is);
					if (null != list && !list.isEmpty()) {
						for (String str : list) {
							System.out.println("response content : " + str);
						}
					}

				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(os);
					IOUtils.closeQuietly(socket);
				}
				
			}
			
			
		};
		
		t.start();

		
	}
}

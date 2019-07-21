package dataStructure;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadSpeedTest {

	ExecutorService pool;

	public DownloadSpeedTest() {
		pool = Executors.newFixedThreadPool(10);
	}

	public void startDownload(String url, String dir, String name) {

	}

//	class DownloadRunnable implements Runnable {
//		final static int BUFFER_SIZE = 1024;
//		URL url;
//		RandomAccessFile raf;
//
//		public DownloadRunnable(URL url, RandomAccessFile raf) {
//			this.url = url;
//			this.raf = raf;
//		}
//
//		@Override
//		public void run() {
//			BufferedInputStream in = null;
//
//			try {
//				// open Http connection to URL
//				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//
//				// set the range of byte to download
//				String byteRange = mStartByte + "-" + mEndByte;
//				conn.setRequestProperty("Range", "bytes=" + byteRange);
//				System.out.println("bytes=" + byteRange);
//
//				// connect to server
//				conn.connect();
//
//				// Make sure the response code is in the 200 range.
//				if (conn.getResponseCode() / 100 != 2) {
//					// ERROR!!
//				}
//
//				// get the input stream
//				in = new BufferedInputStream(conn.getInputStream());
//
//				// open the output file and seek to the start location
//				raf.seek(mStartByte);
//
//				byte data[] = new byte[BUFFER_SIZE];
//				int numRead;
//				while((mState == DOWNLOADING) && ((numRead = in.read(data,0,BUFFER_SIZE)) != -1))
//				{
//					// write to buffer
//					raf.write(data,0,numRead);
//					// increase the startByte for resume later
//					mStartByte += numRead;
//					// increase the downloaded size
//					downloaded(numRead);
//				}
//
//				if (mState == DOWNLOADING) {
//					mIsFinished = true;
//				}
//			} catch (IOException e) {
//				// ERROR!!
//			} finally {
//				if (raf != null) {
//					try {
//						raf.close();
//					} catch (IOException e) {}
//				}
//
//				if (in != null) {
//					try {
//						in.close();
//					} catch (IOException e) {}
//				}
//			}
//		}
//	}

	public static void main(String[] args) {

	}
}

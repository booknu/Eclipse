package dataStructure;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

public class TEST {
	static int index = 0;
	static int work = 0;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ThreadFactory factory = new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				System.out.println(index++ + " started...");
				return thread;
			}
		};
		
		// 쓰레드 풀 생성
		ExecutorService pool = Executors.newFixedThreadPool(10, factory);
		for(int i = 0; i < 10000; i++) {
			Runnable r = new TestRunnable(i);
			pool.execute(r);
		}
		Thread t = new Thread(new FinishCheckRunnable(pool));
		t.start();
		pool.shutdown(); // 필수!
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(work);
	}
	static class TestRunnable implements Runnable {
		int index;
		public TestRunnable(int i) {
			index = i;
		}
		
		public void run() {
			System.out.println(index + " ready to sleep...");
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			synchronized(this) {
				workPlus();
				System.out.println(work);
			}
			System.out.println(index + " waked up!!!");
		}
		
		// static인 이유는 객체의 멤버 메소드이면 Thread 객체가 생성될 때마다
		// 멤버 메소드가 생긴다고 볼 수 있기 때문에 sync를 하는 이득이 없다.
		public static synchronized void workPlus() {
			work++;
		}
	}
	
	static class FinishCheckRunnable implements Runnable {
		ExecutorService pool;
		public FinishCheckRunnable(ExecutorService pool) {
			this.pool = pool;
		}
		@Override
		public void run() {
			while(!pool.isTerminated()) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println(work);
		}
	}

	public static String getDirImage(File dir) throws IOException {
		String defaultPath = "icons/noimage.jpg";
		
		File[] files = dir.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.isFile();
			}
		});
		
		for(File file : files) {
			String mime = Files.probeContentType(file.toPath());
			if(mime != null && mime.startsWith("image")) {
				return file.getPath();
			}
		}
		return defaultPath;
	}
}

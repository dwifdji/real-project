package com.mybatisPlus.demo.common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test {
	
	public static void main(String[] args) throws InterruptedException {
		List<String> arrayList = new ArrayList<String>();
		for(int i = 0; i < 30; i++) {
			arrayList.add("好好工作第" + String.valueOf(i));
		}
		
		
		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		long currentTimeMillis = System.currentTimeMillis();
		
		for (int i = 0; i < 100 ; i++) {
			
			String intSt = String.valueOf(i);
			Thread thread1 = new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + "执行了" + intSt);
				}
			});
			threadPool.submit(thread1);
		}
		
		threadPool.shutdown();
		
		System.out.println("使用线程池一共执行："+String.valueOf(System.currentTimeMillis()-currentTimeMillis)+"ms");
	}

}

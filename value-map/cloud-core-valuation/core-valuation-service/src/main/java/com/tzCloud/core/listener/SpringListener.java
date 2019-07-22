package com.tzCloud.core.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class SpringListener implements CommandLineRunner {

	@Override
	public void run(String... args) throws Exception {
		// args 就是 main 函数的 args
		System.err.println("服务启动，args=" + Arrays.toString(args));
	}
}
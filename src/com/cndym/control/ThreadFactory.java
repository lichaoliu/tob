package com.cndym.control;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadFactory {
	public static ExecutorService sendTicketThreadPool;
	public static ExecutorService sendTicketThreadPool1;

	private ThreadFactory() {

	}

	public static void forInstance() {
		sendTicketThreadPool = Executors.newFixedThreadPool(2);
		sendTicketThreadPool1 = Executors.newFixedThreadPool(5);
	}
}

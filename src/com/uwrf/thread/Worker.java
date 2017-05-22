package com.uwrf.thread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.uwrf.util.SHA1;

public class Worker implements Runnable {

	private BlockingQueue<String> passwordQueue;
	private String sha1Hash;
	
	public Worker (BlockingQueue<String> passwordQueue, String sha1Hash) {
		this.passwordQueue = passwordQueue;
		this.sha1Hash = sha1Hash;
	}
	
	@Override
	public void run() {
		while (!Master.matchFound) {
			try {
				String password = passwordQueue.poll(1L, TimeUnit.SECONDS);
				if (sha1Hash.equals(SHA1.getSHA1Hash(password))) {
					System.out.println("The hash code has been cracked!");
					System.out.println("The password is - " + password);
					Master.matchFound = true;
					return;
				}
			} catch (InterruptedException e) {
				System.out.println("Waiting for password");
			}
		}
	}
}

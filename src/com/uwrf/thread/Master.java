package com.uwrf.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

import com.uwrf.util.WordGenerator;

public class Master implements Runnable {

	private int passwordQueueSize = 200;
	private int passwordLength = 10;
	private int numberOfWorkers = 20;
	private volatile boolean wordGenActive = true;
	public static volatile boolean matchFound = false;
	
	private BlockingQueue<String> passwordQueue;
	private Queue<BlockingQueue<String>> workerQueues;
	private int wgFailedAttempt = 0;
	private String sha1Hash;
	
	public Master (String sha1Hash) {
		this.sha1Hash = sha1Hash;
	}
	
	@Override
	public void run() {
		passwordQueue = new ArrayBlockingQueue<String>(passwordQueueSize);
		(new MasterHelper()).start();
		
		workerQueues = new LinkedList<BlockingQueue<String>>();
		
		while (numberOfWorkers-- > 0) {
			BlockingQueue<String> workerQueue = new ArrayBlockingQueue<>(passwordQueueSize);
			Thread worker = new Thread(new Worker(workerQueue, sha1Hash), "Worker_Thread-"+numberOfWorkers);
			worker.start();
			workerQueues.offer(workerQueue);
		}
		
		while (wordGenActive && !matchFound) {
			try {
				String eachPassword = passwordQueue.poll(1L, TimeUnit.SECONDS);
				BlockingQueue<String> workerQueue = workerQueues.poll();
				workerQueue.put(eachPassword);
				workerQueues.offer(workerQueue);
					
			} catch (InterruptedException e) {
				if (++wgFailedAttempt > 10)
				{
					System.out.println("WordGenerator not responsive; Shutting down system");
					System.exit(0);
				}
				System.out.println("Failed to get data from WordGenerator");
			}
			
		}
	}
	
	private class MasterHelper extends Thread {
		@Override
		public void run() {
			try {
				new WordGenerator(passwordQueue, passwordLength);
				wordGenActive = false;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

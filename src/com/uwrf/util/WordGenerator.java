package com.uwrf.util;

import java.util.concurrent.BlockingQueue;

import com.uwrf.thread.Master;

public class WordGenerator {

	private static String characterPool = "1234567890~!@#$%^&*()_+|}{:\"?><,./';\\]['qwertyuioplkjhgfdsazxcvbnmQWERTYUIOPLKJHGFDSAZXCVBNM";
	private BlockingQueue<String> queue;

	private /* static */ void permute(int level, String prefix) throws InterruptedException {

		if (level == 0) {
			this.queue.put(prefix);
			// System.out.println(prefix);
			return;
		} else if (Master.matchFound)
			return;
		for (int i = 0; i < characterPool.length(); i++)
			permute(level - 1, prefix + characterPool.charAt(i));
	}

	public WordGenerator(BlockingQueue<String> queue, int wordLength) throws InterruptedException {
		this.queue = queue;
		for (int currentWordLength = 1; currentWordLength <= wordLength; currentWordLength++)
			permute(currentWordLength, "");
	}

}

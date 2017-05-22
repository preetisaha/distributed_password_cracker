package com.uwrf;

import com.uwrf.thread.Master;
import com.uwrf.util.SHA1;

public class Main {

	public static void main (String args[]) {
		(new Thread(new Master("5baa61e4c9b93f3f0682250b6cf8331b7ee68fd8"), "Master_Thread") ).start();
	}
}

package com.fis.tool.Util;

import org.springframework.stereotype.Service;

@Service
public class SetTimeout {
	public static void setTimeout(Runnable runnable, int delay) {
		new Thread(() -> {
			try {
				Thread.sleep(delay);
				runnable.run();
			} catch (Exception e) {
				System.err.println(e);
			}
		}).start();
	}

}

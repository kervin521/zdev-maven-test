package com.dev.share.serializable;

public abstract class ShutdownHookHandler {
	public static void add(final ShutdownHookHandler handler) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				handler.shutdown();
			}
		});
	}

	public abstract void shutdown();
}

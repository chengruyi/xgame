package com.brothers.game.bootstrap;

import com.brothers.game.config.XGameConfig;
import com.brothers.game.rpc.server.XGameServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.CountDownLatch;


public class XGameBootStrap {

	private static final Logger logger = LoggerFactory.getLogger(XGameBootStrap.class);

	private static final LoadState STATE = new LoadState();

	private static final CountDownLatch latch = new CountDownLatch(1);

	public static void main(String[] args) throws InterruptedException {
		final boolean success = STATE.start();
		if (!success) {
			logger.warn("x-game already started. skipping loading.");
			return;
		}
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(XGameConfig.class);
		context.refresh();

		final XGameServer httpServer = (XGameServer) context.getBean("httpServerAcceptor");
		final XGameServer webSocketServer = (XGameServer) context.getBean("webSocketServerAcceptor");

		httpServer.start();
		webSocketServer.start();

		latch.await();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				httpServer.shutdown();
				webSocketServer.shutdown();
			}
		});
	}
}

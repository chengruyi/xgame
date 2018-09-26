package com.brothers.game.rpc.server;


import com.brothers.game.common.exception.XGameException;
import com.brothers.game.common.utils.XgameThreadFactory;
import com.brothers.game.rpc.server.http.HttpPipelineFactory;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerBossPool;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.channel.socket.nio.NioWorkerPool;
import org.jboss.netty.util.ThreadNameDeterminer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class XGameServer {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ServerBootstrap serverBootstrap;

	private Channel serverChannel;

	private InetSocketAddress bindAddress;

	public XGameServer(int workerCount, int bossCount, PipelineFactory pipelineFactory, SimpleChannelHandler handler) {
		this.serverBootstrap = createBootStrap(workerCount, bossCount);
		setOptions(this.serverBootstrap);
		addPipeline(this.serverBootstrap, pipelineFactory, handler);
	}

	public void start() {
		if (bindAddress == null) {
			throw new XGameException("start x-game before must need init address .");
		}

		logger.info("bind() {}", bindAddress);
		this.serverChannel = this.serverBootstrap.bind(bindAddress);
		//healthCheck
	}

	public void shutdown() {
		this.serverChannel.close();
	}

	private ServerBootstrap createBootStrap(int workerCount, int bossCount) {

		ExecutorService boss = Executors.newCachedThreadPool(new XgameThreadFactory("x-Server-Boss", true));
		NioServerBossPool nioServerBossPool = new NioServerBossPool(boss, bossCount, ThreadNameDeterminer.CURRENT);

		ExecutorService worker = Executors.newCachedThreadPool(new XgameThreadFactory("x-Server-Worker", true));
		NioWorkerPool nioWorkerPool = new NioWorkerPool(worker, workerCount, ThreadNameDeterminer.CURRENT);

		NioServerSocketChannelFactory nioClientSocketChannelFactory = new NioServerSocketChannelFactory(
				nioServerBossPool, nioWorkerPool);
		return new ServerBootstrap(nioClientSocketChannelFactory);
	}


	private void addPipeline(ServerBootstrap bootstrap, final PipelineFactory pipelineFactory,
			final SimpleChannelHandler handler) {
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = pipelineFactory.newPipeline();
				pipeline.addLast("handler", handler);
				return pipeline;
			}
		});
	}

	private void setOptions(ServerBootstrap bootstrap) {

		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		// buffer setting
		bootstrap.setOption("child.sendBufferSize", 1024 * 64);
		bootstrap.setOption("child.receiveBufferSize", 1024 * 64);
	}

	public void bind(String host, Integer port) {
		this.bindAddress = new InetSocketAddress(host, port);
	}
}

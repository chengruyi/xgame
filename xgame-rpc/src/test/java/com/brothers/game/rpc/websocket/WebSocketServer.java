package com.brothers.game.rpc.websocket;


import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandshakeHandler;
import org.junit.Test;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

public class WebSocketServer {

	@Test
	public void test(String[] args) {
		int port = 8080;
		ChannelFactory channelFactory = new NioServerSocketChannelFactory(Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		ServerBootstrap bootstrap = new ServerBootstrap(channelFactory);
		// Set up the event pipeline factory.
		bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
			@Override
			public ChannelPipeline getPipeline() throws Exception {
				ChannelPipeline pipeline = Channels.pipeline();
				pipeline.addLast("1", new HttpRequestDecoder());
				pipeline.addLast("2", new HttpResponseEncoder());
				pipeline.addLast("3", new WebSocketServerProtocolHandshakeHandler("/", null, true));
				pipeline.addLast("4", new WebSocketHandler());
				return pipeline;
			}
		});
		bootstrap.setOption("child.tcpNoDelay", true);
		bootstrap.setOption("child.keepAlive", true);
		bootstrap.bind(new InetSocketAddress(port));
	}

	static class WebSocketHandler extends SimpleChannelHandler {
		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) throws Exception {
			e.getCause().printStackTrace();
			ctx.getChannel().close();
		}

		@Override
		public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
			if (e.getMessage() instanceof TextWebSocketFrame) {
				TextWebSocketFrame frame = (TextWebSocketFrame) e.getMessage();
				System.out.println(frame.getText());
			}
		}
	}

}
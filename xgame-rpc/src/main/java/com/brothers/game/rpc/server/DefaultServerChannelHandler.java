package com.brothers.game.rpc.server;

import org.jboss.netty.channel.*;
import org.jboss.netty.channel.group.ChannelGroup;
import org.jboss.netty.channel.group.DefaultChannelGroup;
import org.jboss.netty.handler.codec.http.HttpRequest;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultServerChannelHandler extends SimpleChannelHandler {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private final ChannelGroup channelGroup = new DefaultChannelGroup("XGameServerFactory");

	@Override
	public void channelConnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		final Channel channel = e.getChannel();
		logger.info("channelConnected started. channel:{}", channel);


		DefaultXGameServer xGameServer = new DefaultXGameServer();

		channel.setAttachment(xGameServer);
		channelGroup.add(channel);


		super.channelConnected(ctx, e);
	}

	@Override
	public void channelDisconnected(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		final Channel channel = e.getChannel();

		DefaultXGameServer xGameServer = (DefaultXGameServer) channel.getAttachment();
		if (xGameServer != null) {
			xGameServer.shutdown();
		}

		super.channelDisconnected(ctx, e);
	}

	@Override
	public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) throws Exception {
		final Channel channel = e.getChannel();
		channelGroup.remove(channel);
		super.channelClosed(ctx, e);
	}

	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) throws Exception {
		final Channel channel = e.getChannel();
		DefaultXGameServer pinpointServer = (DefaultXGameServer) channel.getAttachment();
		if (e.getMessage() instanceof HttpRequest) {
			//handleHttpRequest();

			System.out.println(e.getMessage());
		} else if (e.getMessage() instanceof WebSocketFrame) {
			//handleWebSocketFrame(context, (WebSocketFrame) message);
			System.out.println(e.getMessage());
		}
		super.messageReceived(ctx, e);
	}
}
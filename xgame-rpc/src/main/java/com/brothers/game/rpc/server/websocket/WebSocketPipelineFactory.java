package com.brothers.game.rpc.server.websocket;

import com.brothers.game.rpc.server.PipelineFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpContentCompressor;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;
import org.jboss.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandshakeHandler;


public class WebSocketPipelineFactory implements PipelineFactory {

	public WebSocketPipelineFactory() {

	}

	@Override
	public ChannelPipeline newPipeline() {
		ChannelPipeline pipeline = Channels.pipeline();

		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("encoder", new HttpResponseEncoder());
		pipeline.addLast("websocket", new WebSocketServerProtocolHandshakeHandler("/", null, true));

		return pipeline;
	}

}
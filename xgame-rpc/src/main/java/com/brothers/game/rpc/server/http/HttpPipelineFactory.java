package com.brothers.game.rpc.server.http;

import com.brothers.game.rpc.server.PipelineFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.handler.codec.http.HttpContentCompressor;
import org.jboss.netty.handler.codec.http.HttpRequestDecoder;
import org.jboss.netty.handler.codec.http.HttpResponseEncoder;


public class HttpPipelineFactory implements PipelineFactory {

	public HttpPipelineFactory() {

	}

	@Override
	public ChannelPipeline newPipeline() {
		ChannelPipeline pipeline = Channels.pipeline();

		pipeline.addLast("decoder", new HttpRequestDecoder());
		pipeline.addLast("encoder", new HttpResponseEncoder());
		pipeline.addLast("deflater", new HttpContentCompressor());

		return pipeline;
	}

}
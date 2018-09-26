package com.brothers.game.rpc.server;

import org.jboss.netty.channel.ChannelPipeline;


public interface PipelineFactory {

    ChannelPipeline newPipeline();

}

package com.brothers.game.config;

import com.brothers.game.rpc.server.DefaultServerChannelHandler;
import com.brothers.game.rpc.server.XGameServer;
import com.brothers.game.rpc.server.http.HttpPipelineFactory;
import com.brothers.game.rpc.server.websocket.WebSocketPipelineFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@ComponentScan(basePackages = {"com.brothers.game"})
@PropertySource("classpath:server.properties")
public class XGameConfig {


	@Value("${xgame.http.worker.count}")
	private Integer httpWorkerCount;


	@Value("${xgame.http.bosser.count}")
	private Integer httpBosserCount;

	@Value("${xgame.ws.worker.count}")
	private Integer wsWorkerCount;


	@Value("${xgame.ws.bosser.count}")
	private Integer wshttpBosserCount;


	@Value("${xgame.http.host}")
	private String httpHost;

	@Value("${xgame.ws.host}")
	private String wsHost;


	@Value("${xgame.http.port}")
	private Integer httpPort;

	@Value("${xgame.ws.port}")
	private Integer wsPort;

	@Bean(name = "httpServerAcceptor")
	public XGameServer getHttpServerAcceptor(@Autowired DefaultServerChannelHandler handler) {
		XGameServer server = new XGameServer(httpWorkerCount, httpBosserCount, new HttpPipelineFactory(), handler);
		server.bind(httpHost, httpPort);
		return server;
	}

	@Bean(name = "webSocketServerAcceptor")
	public XGameServer getWebSocketServerAcceptor(@Autowired DefaultServerChannelHandler handler) {
		XGameServer server = new XGameServer(wsWorkerCount, wshttpBosserCount, new WebSocketPipelineFactory(), handler);
		server.bind(wsHost, wsPort);
		return server;
	}

	@Bean
	public DefaultServerChannelHandler getHandler() {
		return new DefaultServerChannelHandler();
	}


}

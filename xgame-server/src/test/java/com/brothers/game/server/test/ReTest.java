package com.brothers.game.server.test;

import com.brothers.game.common.annotion.Command;
import com.brothers.game.common.annotion.RequestModule;
import com.brothers.game.common.utils.UriParser;
import com.brothers.game.controller.LoginController;
import org.junit.Test;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class ReTest {


	@Test
	public void testMethod() {

		Class<? extends Object> clazz = LoginController.class;
		//scan all class interface
		RequestModule socketModule = clazz.getAnnotation(RequestModule.class);

		Method[] methods = clazz.getMethods();
		if (methods != null && methods.length > 0) {
			for (Method method : methods) {
				Command socketCommand = method.getAnnotation(Command.class);
				if (socketCommand == null) {
					continue;
				}
				final String module = socketModule.module();
				final String cmd = socketCommand.cmd();
				String uri = UriParser.parser(module, cmd);


				Parameter[] parameters = method.getParameters();
				LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();
				for (String name : discoverer.getParameterNames(method)) {
					System.out.println(name);
				}


				//logger.error("this module {} and cmd {} is repeat choose first one .", module, cmd);
			}
		}

	}

}

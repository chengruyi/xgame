package com.brothers.game.core.scanner;

import com.brothers.game.common.annotion.Command;
import com.brothers.game.common.annotion.RequestModule;
import com.brothers.game.common.utils.UriParser;
import com.brothers.game.core.annotation.Command;
import com.brothers.game.core.annotation.RequestModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Component
public class HandlerScaner implements BeanPostProcessor {

	private static final Logger logger = LoggerFactory.getLogger(HandlerScaner.class);

	private static final LocalVariableTableParameterNameDiscoverer discoverer = new LocalVariableTableParameterNameDiscoverer();

	@Override
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	@Override
	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

		Class<? extends Object> clazz = bean.getClass();
		Class<?>[] interfaces = clazz.getInterfaces();
		if (interfaces != null && interfaces.length > 0) {
			//scan all class interface
			for (Class<?> interFace : interfaces) {
				RequestModule socketModule = interFace.getAnnotation(RequestModule.class);
				if (socketModule == null) {
					continue;
				}
				Method[] methods = interFace.getMethods();
				if (methods != null && methods.length > 0) {
					for (Method method : methods) {
						Command socketCommand = method.getAnnotation(Command.class);
						if (socketCommand == null) {
							continue;
						}
						final String module = socketModule.module();
						final String cmd = socketCommand.cmd();
						String uri = UriParser.parser(module, cmd);

						discoverer.getParameterNames(method);

						logger.error("this module {} and cmd {} is repeat choose first one .", module, cmd);
					}
				}

			}
		}
		return bean;
	}

}

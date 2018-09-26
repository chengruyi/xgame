package com.brothers.game.controller;

import com.brothers.game.core.annotation.Command;
import com.brothers.game.core.annotation.RequestMethod;
import com.brothers.game.core.annotation.RequestModule;
import com.brothers.game.rpc.code.Result;
import org.springframework.stereotype.Controller;


@RequestModule(module = "/player")
@Controller
public class LoginController {

	@Command(cmd = "/login", method = RequestMethod.POST)
	public Result login(String unionCode) {

		return null;
	}

}

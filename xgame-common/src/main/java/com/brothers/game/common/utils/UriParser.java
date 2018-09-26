package com.brothers.game.common.utils;

import com.google.common.base.Joiner;

import java.util.ArrayList;
import java.util.List;


public class UriParser {

	public static String parser(String... args) {
		List<String> uris = new ArrayList<>(args.length);
		for (String arg : args) {
			uris.add(arg.replace("/", ""));
		}
		return "/"+Joiner.on("/").join(uris);
	}
  }

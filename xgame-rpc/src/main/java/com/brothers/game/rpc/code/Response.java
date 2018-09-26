package com.brothers.game.rpc.code;

import com.brothers.game.common.code.ResultCode;


public class Response {

	private short module;

	private short cmd;

	private int stateCode = ResultCode.SUCCESS;

	private byte[] data;

	public Response() {
	}

	public Response(Request message) {
		this.module = message.getModule();
		this.cmd = message.getCmd();
	}

	public Response(short module, short cmd, byte[] data) {
		this.module = module;
		this.cmd = cmd;
		this.data = data;
	}

	public int getStateCode() {
		return stateCode;
	}

	public void setStateCode(int stateCode) {
		this.stateCode = stateCode;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public short getModule() {
		return module;
	}

	public void setModule(short module) {
		this.module = module;
	}

	public short getCmd() {
		return cmd;
	}

	public void setCmd(short cmd) {
		this.cmd = cmd;
	}
}

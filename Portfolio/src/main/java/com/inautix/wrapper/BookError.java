package com.inautix.wrapper;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
@JsonSerialize(using = ErrorSerializer.class)
public enum BookError {
NOTFOUND(404),
INVALIDDATA(500),
NOTLOGGEDIN(410),
UNDEFINED(900);
	private int code;
	private BookError(int code) {
		this.code = code;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	
}

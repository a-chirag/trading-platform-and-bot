package com.inautix.utils;

public class MetaData {
@Override
	public String toString() {
		return "MetaData [status=" + status + ", message=" + message + "]";
	}
Boolean status;
String message;
public Boolean getStatus() {
	return status;
}
public void setStatus(Boolean status) {
	this.status = status;
}
public String getMessage() {
	return message;
}
public void setMessage(String message) {
	this.message = message;
}

}

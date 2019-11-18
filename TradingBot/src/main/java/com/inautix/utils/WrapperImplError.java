package com.inautix.utils;

public class WrapperImplError implements Wrapper {
MetaData metaData;
BookError error;
public MetaData getMetaData() {
	return metaData;
}
public void setMetaData(MetaData metaData) {
	this.metaData = metaData;
}
public BookError getError() {
	return error;
}
public void setError(BookError error) {
	this.error = error;
}
public void setData(Object data) {
	
}
}

package com.inautix.wrapper;

public class WrapperImplFound implements Wrapper{ 
	MetaData metaData;
	Object data;
	public MetaData getMetaData() {
		return metaData;
	}
	public void setMetaData(MetaData metaData) {
		this.metaData = metaData;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public void setError(BookError error) {
		
	}
	
}

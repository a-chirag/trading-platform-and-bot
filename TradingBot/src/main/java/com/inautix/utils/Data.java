package com.inautix.utils;

import java.util.ArrayList;
import java.util.List;

public class Data {
List data = new ArrayList();

public List getData() {
	return data;
}

public void setData(Object data) {
	this.data.add(data);
}

}

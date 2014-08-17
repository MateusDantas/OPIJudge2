package com.opijudge.server.response;

import java.util.List;

public class ListResponse<T> {

	private List<T> list;
	private int responseStatus;
	
	public ListResponse() {
		
	}
	
	public ListResponse(int responseStatus) {
		
		this.setResponseStatus(responseStatus);
	}
	public ListResponse(List<T> list, int responseStatus) {
		
		this.setList(list);
		this.setResponseStatus(responseStatus);
	}

	public int getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(int responseStatus) {
		this.responseStatus = responseStatus;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list2) {
		this.list = list2;
	}
	
	
}

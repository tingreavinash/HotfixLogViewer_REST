/**
 * Model class for ResponseObject for sending as response to REST call.
 * @author Avinash Tingre
 */
package com.avinash.hotfixviewer.Model;

import java.util.List;

public class ECPLogResponseObject {
	private int count;
	private List<ECPLog> Records;
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public List<ECPLog> getRecords() {
		return Records;
	}
	public void setRecords(List<ECPLog> records) {
		Records = records;
	}
	@Override
	public String toString() {
		return "ResponseObject [count=" + count + ", Records=" + Records + "]";
	}

}

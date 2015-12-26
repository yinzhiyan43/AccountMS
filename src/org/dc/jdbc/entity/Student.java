package org.dc.jdbc.entity;

public class Student {
	private int id;
	private int pid;
	
	private Object blobtest;
	
	
	public Object getBlobtest() {
		return blobtest;
	}
	public void setBlobtest(Object blobtest) {
		this.blobtest = blobtest;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public static void main(String[] args) {
		System.out.println(1<<1);
		System.out.println((int)1.5);
	}
}

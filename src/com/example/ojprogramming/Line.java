package com.example.ojprogramming;

public class Line {
	private String sid;
	private String oj;
	private String pid;
	private String title;
	private String source;
	private int acSubmission;
	private int totalSubmission;

	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public String getOj() {
		return oj;
	}

	public void setOj(String oj) {
		this.oj = oj;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public int getAcSubmission() {
		return acSubmission;
	}

	public void setAcSubmission(int acSubmission) {
		this.acSubmission = acSubmission;
	}

	public int getTotalSubmission() {
		return totalSubmission;
	}

	public void setTotalSubmission(int totalSubmission) {
		this.totalSubmission = totalSubmission;
	}

	@Override
	public String toString() {
		return "Line [sid=" + sid + ", oj=" + oj + ", pid=" + pid + ", title="
				+ title + ", source=" + source + ", acSubmission="
				+ acSubmission + ", totalSubmission=" + totalSubmission + "]";
	}

}

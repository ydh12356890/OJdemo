package com.example.ojprogramming;

public class ContestsData {
	private String contest_id;
	private String title;
	private String start_time;
	private String end_time;
	private String status;
	private String access;
	private String contest_type;
	public String getContest_id() {
		return contest_id;
	}
	public void setContest_id(String contest_id) {
		this.contest_id = contest_id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getStart_time() {
		return start_time;
	}
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	public String getEnd_time() {
		return end_time;
	}
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAccess() {
		return access;
	}
	public void setAccess(String access) {
		this.access = access;
	}
	public String getContest_type() {
		return contest_type;
	}
	public void setContest_type(String contest_type) {
		this.contest_type = contest_type;
	}
	@Override
	public String toString() {
		return "比赛信息如下："+"\n"+ "contest_id：" + contest_id+"\n" + "title：" + title+"\n"
				+ "start_time：" + start_time+"\n" + "end_time：" + end_time+"\n"
				+ "status：" + status+"\n" + "access：" + access+"\n"
				+ "contest_type：" + contest_type;
	}
	
	
	

}

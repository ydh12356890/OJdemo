package com.example.ojprogramming;

public class DetailCommitData {
	 private String sid; 
	  private String run_id; 
	  private String username; 
	  private String status; 
	  private String status_code; 
	  private String ce_info; 
	  //private Language language; 
	  private int time_used; 
	  private int memory_used; 
	  private int testcases; 
	  private int testcases_passed; 
	  private int code_length; 
	  private String submit_time; 
	  private String code; 
	  private boolean is_spj;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getrun_id() {
		return run_id;
	}
	public void setrun_id(String run_id) {
		this.run_id = run_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getstatus_code() {
		return status_code;
	}
	public void setstatus_code(String status_code) {
		this.status_code = status_code;
	}
	public String getce_info() {
		return ce_info;
	}
	public void setce_info(String ce_info) {
		this.ce_info = ce_info;
	}
	public int gettime_used() {
		return time_used;
	}
	public void settime_used(int time_used) {
		this.time_used = time_used;
	}
	public int getmemory_used() {
		return memory_used;
	}
	public void setmemory_used(int memory_used) {
		this.memory_used = memory_used;
	}
	public int getTestcases() {
		return testcases;
	}
	public void setTestcases(int testcases) {
		this.testcases = testcases;
	}
	public int gettestcases_passed() {
		return testcases_passed;
	}
	public void settestcases_passed(int testcases_passed) {
		this.testcases_passed = testcases_passed;
	}
	public int getcode_length() {
		return code_length;
	}
	public void setcode_length(int code_length) {
		this.code_length = code_length;
	}
	public String getsubmit_time() {
		return submit_time;
	}
	public void setsubmit_time(String submit_time) {
		this.submit_time = submit_time;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean is_spj() {
		return is_spj;
	}
	public void setSpj(boolean is_spj) {
		this.is_spj = is_spj;
	}
	@Override
	public String toString() {
		return "提交状况的详细信息：" + "\n" + "SID：" + sid +"\n"+ "run_id：" + run_id+"\n"
				+ "用户名：" + username +"\n"+ "状态：" + status+"\n"
				+ "状态码：" + status_code+"\n" + "ce_info：" + ce_info+"\n"
				+ "time_used：" + time_used+"\n" + "memory_used：" + memory_used+"\n"
				+ "testcases：" + testcases+"\n" + "testcases_passed："
				+ testcases_passed +"\n"+ "code_length：" + code_length+"\n"
				+ "submit_time：" + submit_time+"\n" + "code：" + code+"\n"
				+ "is_spj：" + is_spj;
	} 
	
	  

}

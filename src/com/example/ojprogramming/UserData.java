package com.example.ojprogramming;
public class UserData {
	private int id;
	private String msg;
    private String username;
    private String password;
    private String privilege;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPrivilege() {
		return privilege;
	}
	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}
	@Override
	public String toString() {
		return "�û���Ϣ���£�" + "\n" +  "�û�id��" + id +"\n" + "MSG��" + msg + "\n" + "�û�����" + username + "\n"
				+ "���룺" + password + "\n" + "�û�Ȩ�ޣ�" + privilege;
	}
    

}
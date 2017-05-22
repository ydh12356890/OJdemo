package com.example.ojprogramming;

public class Language {
	private String compiler; 
	  private String language; 
	  private String language_id; 
	  private String oj_name;
	public String getCompiler() {
		return compiler;
	}
	public void setCompiler(String compiler) {
		this.compiler = compiler;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getLanguage_id() {
		return language_id;
	}
	public void setLanguage_id(String language_id) {
		this.language_id = language_id;
	}
	public String getOj_name() {
		return oj_name;
	}
	public void setOj_name(String oj_name) {
		this.oj_name = oj_name;
	}
	@Override
	public String toString() {
		return "Languages的相关信息如下："+"\n" + "compiler：" + compiler +"\n"+ "language：" + language+"\n"
				+ "language_id：" + language_id +"\n"+ "oj_name：" + oj_name;
	} 
	  


}

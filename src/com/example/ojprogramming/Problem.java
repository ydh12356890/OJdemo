package com.example.ojprogramming;

public class Problem {
	 private String title; 
	  //private List<Limits> limits; 
	  private String description; 
	  private String input; 
	  private String output; 
	  private String sample_input; 
	  private String sample_output; 
	  private String source; 
	  private String hint; 
	  private boolean hide;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getOutput() {
		return output;
	}
	public void setOutput(String output) {
		this.output = output;
	}
	public String getSampleInput() {
		return sample_input;
	}
	public void setSampleInput(String sampleInput) {
		this.sample_input = sampleInput;
	}
	public String getSampleOutput() {
		return sample_output;
	}
	public void setSampleOutput(String sampleOutput) {
		this.sample_output = sampleOutput;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getHint() {
		return hint;
	}
	public void setHint(String hint) {
		this.hint = hint;
	}
	public boolean isHide() {
		return hide;
	}
	public void setHide(boolean hide) {
		this.hide = hide;
	}
	@Override
	public String toString() {
		return "������ϸ��Ϣ���£�" + "\n"+"��Ŀ��" + title + "\n"+"��Ŀ������" + description
				+"\n"+ "���룺" + input +"\n"+ "�����" + output +"\n"+ "�������룺"
				+ sample_input+"\n" + "���������" + sample_output+"\n"
				+ "��Դ��" + source +"\n"+ "��ʾ��" + hint +"\n"+ "Hide��" + hide
				;
	} 
	
	  

}


package beanstudycafe_chat;

import java.io.Serializable;

enum Info {
	JOIN ,EXIT,SEND
}

public class ChatDTO implements Serializable {

	private static final long serialVersion = 1L;
	
	private String phone;
	private String message;
	private Info command;
	private String to;
	private String from;
	
//----------------------------------------------getter
	
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getPhone() {
		return phone;
	}
	public String getMessage() {
		return message;
	}
	public Info getCommand() {
		return command;
	}
	
//----------------------------------------------setter

	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setCommand(Info command) {
		this.command = command;
	}
	

}

package com.inscripts.chatapp;

public class Chat {
	private String message;
	private String role;
	private String timeStamp;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	@Override
	public String toString() {
		return "Chat [message=" + message + ", role=" + role + ", timeStamp="
				+ timeStamp + "]";
	}

}

package com.example.model;

/**
 * Created by arindam on 20/9/17.
 */
public class MessageObject {
	Object message;
	long visibility;
	long startTime;

	public MessageObject(Object message, long visibility) {
		this.message = message;
		this.visibility = visibility;
	}

	public MessageObject(Object message, long startTime, long visibility) {
		this.message = message;
		this.visibility = visibility;
		this.startTime = startTime;
	}

	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}

	public long getVisibility() {
		return visibility;
	}

	public void setVisibility(long visibility) {
		this.visibility = visibility;
	}
	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MessageObject)) return false;

		MessageObject object = (MessageObject) o;

		if (getVisibility() != object.getVisibility()) return false;
		return getMessage() != null ? getMessage().equals(object.getMessage()) : object.getMessage() == null;
	}

	@Override
	public int hashCode() {
		int result = getMessage() != null ? getMessage().hashCode() : 0;
		result = 31 * result + (int) (getVisibility() ^ (getVisibility() >>> 32));
		return result;
	}
}

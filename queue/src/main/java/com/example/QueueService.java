package com.example;

public interface QueueService {
	void push(Object message, String queue);
	Object pull(String queue);
	void delete(Object message, String queue);
}

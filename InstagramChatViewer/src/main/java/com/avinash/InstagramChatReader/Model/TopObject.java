package com.avinash.InstagramChatReader.Model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class TopObject {
	
	private List<String> participants;
	private List<ConversationObject> conversation;
	
	@Autowired
	public YAMLConfig customConfig;

	public List<String> getParticipants() {
		return participants;
	}
	public void setParticipants(List<String> participants) {
		this.participants = participants;
	}
	public List<ConversationObject> getConversation() {
		return conversation;
	}
	public void setConversation(List<ConversationObject> conversation) {
		this.conversation = conversation;
	}
	
}

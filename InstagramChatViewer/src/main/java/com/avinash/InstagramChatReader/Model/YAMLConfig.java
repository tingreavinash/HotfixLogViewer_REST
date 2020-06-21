/**
 * Model class for ResponseObject for sending as response to REST call.
 * @author Avinash Tingre
 */
package com.avinash.InstagramChatReader.Model;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class YAMLConfig {
	private String messageFile;
	private String participant;
	
	public String getMessageFile() {
		return messageFile;
	}
	public void setMessageFile(String messageFile) {
		this.messageFile = messageFile;
	}
	public String getParticipant() {
		return participant;
	}
	public void setParticipant(String participant) {
		this.participant = participant;
	}
	
	
}

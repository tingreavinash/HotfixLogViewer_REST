package com.avinash.InstagramChatReader.Model;

import java.util.Date;
import java.util.List;


public class ConversationObject implements Comparable{
	
	private String sender;
	private Date created_at;
	private String media;
	private String media_owner;
	private String media_share_caption;
	private String media_share_url;
	private String heart;
	private String text;
	private String story_share;
	private String story_share_type;
	private List<LikesObject> likes;
	private String video_call_action;
	private String link;
	private String animated_media_images;
	
	
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getVideo_call_action() {
		return video_call_action;
	}
	public void setVideo_call_action(String video_call_action) {
		this.video_call_action = video_call_action;
	}
	public String getStory_share_type() {
		return story_share_type;
	}
	public void setStory_share_type(String story_share_type) {
		this.story_share_type = story_share_type;
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	@Override
	public String toString() {
		return "ConversationObject [sender=" + sender + ", created_at=" + created_at + ", media_owner=" + media_owner
				+ ", media_share_caption=" + media_share_caption + ", media_share_url=" + media_share_url + ", heart="
				+ heart + ", text=" + text + ", story_share=" + story_share + ", likes=" + likes + "]";
	}
	public String getHeart() {
		return heart;
	}
	public void setHeart(String heart) {
		this.heart = heart;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getStory_share() {
		return story_share;
	}
	public void setStory_share(String story_share) {
		this.story_share = story_share;
	}
	public List<LikesObject> getLikes() {
		return likes;
	}
	public void setLikes(List<LikesObject> likes) {
		this.likes = likes;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public Date getCreated_at() {
		return created_at;
	}
	public void setCreated_at(Date created_at) {
		this.created_at = created_at;
	}
	public String getMedia_owner() {
		return media_owner;
	}
	public void setMedia_owner(String media_owner) {
		this.media_owner = media_owner;
	}
	public String getMedia_share_caption() {
		return media_share_caption;
	}
	public void setMedia_share_caption(String media_share_caption) {
		this.media_share_caption = media_share_caption;
	}
	public String getMedia_share_url() {
		return media_share_url;
	}
	public void setMedia_share_url(String media_share_url) {
		this.media_share_url = media_share_url;
	}
	@Override
	public int compareTo(Object o) {
		
		Date compareMsgDate = ((ConversationObject) o).getCreated_at(); 
		Date thisDate = this.getCreated_at();
		
		if(compareMsgDate.compareTo(thisDate) > 0) {
			 return -1;
		 }else if (compareMsgDate.compareTo(thisDate) < 0) {
			 return 1;
		 }else {
			 return 0;
		 }
		
	}
	
	

}

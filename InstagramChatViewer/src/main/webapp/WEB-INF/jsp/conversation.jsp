<%@page import="com.avinash.InstagramChatReader.Model.LikesObject"%>
<%@page import="com.avinash.InstagramChatReader.Controller.UserInterfaceController"%>

<%@page import="com.avinash.InstagramChatReader.InstagramChatApplication"%>
<%@page import="org.springframework.beans.factory.annotation.Autowired"%>
<%@page import="com.avinash.InstagramChatReader.Model.YAMLConfig"%>
<%@page import="java.util.Collections"%>
<%@page import="com.fasterxml.jackson.databind.DeserializationFeature"%>

<%@page import="java.io.FileInputStream"%>
<%@page import="com.avinash.InstagramChatReader.Model.ConversationObject"%>
<%@page import="com.avinash.InstagramChatReader.Model.TopObject"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.File"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@page import="java.net.InetAddress"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.io.Reader"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="com.fasterxml.jackson.databind.ObjectMapper"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF8"%>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

</head>
<body>

	<%
	
	File file = new File(UserInterfaceController.msgFile);
	BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));

	StringBuilder sb = new StringBuilder();
	String line = null;
	while ((line=br.readLine())!= null) {
		
		sb.append(line);
	}
	br.close();
	
	ObjectMapper objectMapper = new ObjectMapper();
	objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	TopObject[] object =objectMapper.readValue(sb.toString(),
			TopObject[].class);
	
	
	System.out.println("File: "+UserInterfaceController.msgFile);	
	System.out.println("Participant: "+UserInterfaceController.participant);	

	System.out.println("Read file success! ");
	%>
	<div class="alert alert-success text-center" role="alert">
		<h6 class="alert-heading">
			Conversation 
			</h6>
	</div>
	
	<div class="container">
	<div class="col">
	<%
	for(TopObject tObj : object){
		
	if(tObj.getParticipants().contains(UserInterfaceController.participant)){
	List<ConversationObject> convObjList = tObj.getConversation();
	Collections.sort(convObjList);
	
	for(ConversationObject convObj : convObjList){
		%>
		<%
		if(UserInterfaceController.participant.equals(convObj.getSender())){
		%>
		<div class="row  justify-content-start">
		<p class="msg-sizing card bg-secondary border-secondary text-white">
		<%if(convObj.getStory_share()!=null) {
		%><%=convObj.getStory_share()%><br>
		<%			
		}
		%>
		
		<%if(convObj.getLink() !=null){
		%>Link: <%= convObj.getLink()%><br>
		<%			
		}
		%>
		
		<%if(convObj.getMedia() !=null) {
		%>		<%=convObj.getMedia()%><br>
		<%			
		}
		%>
		
		<%if(convObj.getMedia_share_caption() !=null) {
		%>Caption: <%=convObj.getMedia_share_caption()%><br><%	
		}
		%>
		
		
		<%if(convObj.getMedia_share_url() !=null) {
		%><img src="<%=convObj.getMedia_share_url()%>" alt="<%=convObj.getMedia_share_url()%>" class="rounded" style="width:200px;height:200px;border:1px solid grey;"><br>
		<%			
		}
		%>
		
		<%if(convObj.getHeart() !=null)  {
		%><%=convObj.getHeart()%><br><%	
		}
		%>
		
		
		<%if(convObj.getText() !=null){
		%><%=convObj.getText()%><br><% 
		}
		%>
		
		<small ><%=convObj.getCreated_at() %></small></p>
		</div>
		
		
		<%			
		}else{
		%>
		<div class="row justify-content-end">
		<p class="msg-sizing card bg-light border-light">
		<%if(convObj.getStory_share()!=null) {
		%><%=convObj.getStory_share()%><br>
		<%			
		}
		%>
		
		<%if(convObj.getLink() !=null){
		%>Link: <%= convObj.getLink()%><br>
		<%			
		}
		%>
		
		<%if(convObj.getMedia() !=null) {
		%>		<%=convObj.getMedia()%><br>
		<%			
		}
		%>
		
		<%if(convObj.getMedia_share_caption() !=null) {
		%>Caption: <%=convObj.getMedia_share_caption()%><br><%	
		}
		%>
		
		
		<%if(convObj.getMedia_share_url() !=null) {
		%><img src="<%=convObj.getMedia_share_url()%>" alt="<%=convObj.getMedia_share_url()%>" class="rounded" style="width:200px;height:200px;border:1px solid grey;"><br>
		<%			
		}
		%>
		
		<%if(convObj.getLikes() !=null) {
			for(LikesObject like: convObj.getLikes()){
			
		%>Liked by: <%=like.getUsername() %>, Time: <%=like.getDate() %> <br><%	
			}}
		%>
		
		<%if(convObj.getHeart() !=null)  {
		%><%=convObj.getHeart()%><br><%	
		}
		%>
		
		
		<%if(convObj.getText() !=null){
		%><%=convObj.getText()%><br><% 
		}
		%>
		
		<small class="msg-right"><%=convObj.getCreated_at() %></small></p>
		</div>
		<%	
		}
		%>
		
		
		<%		
	}
	}
	}
	%>
	</div>
	</div>
</body>
</html>
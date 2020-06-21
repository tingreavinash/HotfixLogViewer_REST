<%@page import="java.util.Date"%>
<%@page import="java.net.InetAddress"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Arrays"%>
<%@page import="java.net.HttpURLConnection"%>
<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="UTF8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">

<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<title>Instagram Chat Reader</title>
<link href="css/bootstrap.min.css" rel="stylesheet" type="text/css" />

<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" href="fontawesome-free-5.13.0-web/css/all.css">

<link href="https://fonts.googleapis.com/css2?family=Noto+Serif:wght@700&display=swap" rel="stylesheet">
  <link rel="icon" href="../image/favicon3.png" type="image/gif" sizes="64x64">

<style>
html, body {
	height: 100%;
}
</style>



</head>
<body class="custom-body" style="margin: 0; ">
<%
InetAddress ip = InetAddress.getByName(request.getRemoteAddr());
String hostname = ip.getHostName();
System.out.println("User:\t"+hostname);
System.out.println("Time:\t"+new Date());
%>

	<div class="container-fluid h-100" id="container-fluid">

		<div class="row h-100">
			<div class="col"
				style="max-height: 100%; padding: 10px; overflow-y: scroll;">	
<!-- 	<img alt="Test image" src="image/search_graphic.png"> -->
		
				<jsp:include page="conversation.jsp"></jsp:include>

			</div>
		</div>


	</div>

	<script type="text/javascript" src="js/jquery-3.5.1.slim.min.js"></script>
	<script type="text/javascript" src="js/popper.min.js"></script>
	<script type="text/javascript" src="js/bootstrap.min.js"></script>
	<script type="text/javascript">
	
	
	$(function() {
		$('[data-toggle="tooltip"]').tooltip()
	})
	</script>
</body>
</html>

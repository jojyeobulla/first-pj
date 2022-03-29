<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %> <!-- 추가 --> 

<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta id="_csrf" name="_csrf" content="${_csrf.token}"/>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">

<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
	integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/"
	crossorigin="anonymous">

<!--
<script src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"></script>
-->
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/js/bootstrap.bundle.min.js"></script>

<style>
	.nav-link{
	font-size: 14px;
	}
	
	.nav-login{
	margin-left: auto;
	}
	
</style>
</head>
<body>

	<title>nav</title>

	<!-- nav -->
	<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		<div class="container">
			<a class="navbar-brand" href="spj_main">SPJ Project</a>
			<button class="navbar-toggler" type="button" data-toggle="collapse"
				data-target="#collapsibleNavbar">
				<span class="navbar-toggler-icon"></span>
			</button>


			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav ms-auto mb-2 mb-lg-0">

					<li class="nav-item"><a class="nav-link active" href="#!">
							<span class="badge rounded-pill bg-primary">Primary</span>
							&nbsp;알바비 명세서
					</a></li> &nbsp;&nbsp;&nbsp;

					<li class="nav-item"><a class="nav-link" href="spj_boardList">
							게시판 </a></li> &nbsp;&nbsp;&nbsp;

					<li class="nav-item"><a class="nav-link" href="spj_about"> About
							SPJ Project </a></li> &nbsp;&nbsp;&nbsp;
				</ul>
			 	<ul class="navbar-nav nav-login ms-auto mb-2 mb-lg-0" >
<!-- 			 		로그인하고나면 로그인 버튼이, 로그아웃으로 바뀌게 구현해야 함.
               		<li class="nav-item"><a class="nav-link" href="spj_login" id="log"> 로그인 </a></li>
               		<li class="nav-item"><a class="nav-link" href="spj_join" id="log2"> 회원가입 </a></li>
             		<li class="nav-item"><a class="nav-link" href="spj_logout" > 로그아웃 </a></li>
                  	<li class="nav-item"><a class="nav-link" href="#">마이페이지</a></li>
					<li class="nav-item"><a class="nav-link" href="#"> 개인정보수정 </a></li> -->
					
					<sec:authorize access="isAnonymous()">
               		<li class="nav-item"><a class="nav-link" href="spj_login"> 로그인 </a></li>
               		<li class="nav-item"><a class="nav-link" href="spj_join"> 회원가입 </a></li>
               		</sec:authorize>
               		<sec:authorize access="isAuthenticated()">
             		<li class="nav-item"><a class="nav-link" href="spj_logout" > 로그아웃 </a></li>
                  	<li class="nav-item"><a class="nav-link" href="#">마이페이지</a></li>
               		</sec:authorize>
				</ul>
				
				
		</div>

	</nav> 
</body>
</html>
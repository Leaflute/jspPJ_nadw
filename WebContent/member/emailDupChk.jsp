<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../asset/setting.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}member.css">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<title>이메일 확인</title>
<script type="text/javascript" src="${jsPath}member.js"></script>
</head>
<body>
<article class="container">
<section class="wrap_inner">
<div class="DupChkPop">
<form action="emailDupChk.co" method="post" name="confirmForm" onsubmit="return confirmEmailChk();">
<c:if test="${selectCnt==1}">
	<table>
		<tr class="cell_green">
			<th colspan="2">
				<span>${email}</span>은 사용할 수 없습니다.
			</th>
		</tr>
		<tr>
			<th class="icon" ><i class="far fa-envelope"></i></th>	
			<td>
				<input class="input" type="text" name="email" maxlength="40"
					style="width:400px" placeholder="다른 이메일 주소를 입력하세요." autofocus required>
			</td>
		</tr>
		
		<tr>
			<th colspan="2" class="cell_white">
				<input class="btn_green" type="submit" value="중복확인">
				<input class="btn_green" type="reset" value="돌아가기" onclick="self.close();">
			</th>
		</tr>
	</table>
</c:if>		
<c:if test="${selectCnt!=1}">
	<table>
		<tr class="cell_green" style="width:400px">
			<th>
				<span>${email}</span>는 사용가능한 이메일입니다.
			</th>
		</tr>
		<tr align="center">
			<th>
				<input class="btn_green" type="button" value="확인" onclick="setEmail('${email}');">
			</th>
		</tr>
	</table>
</c:if>
</form>		
</div>
</section>
</article>
</body>
</html>
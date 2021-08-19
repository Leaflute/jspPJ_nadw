<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../asset/setting.jsp" %>     
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="${cssPath}member.css">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<title>로그인</title>
<script type="text/javascript" src="${jsPath}member.js"></script>
</head>
<body>
<%@ include file="../signHeader.jsp" %>

<!-- article 시작 -->
<article class="container">
	<section class="wrapper">
	<div id="signInBox">
	<a href="index.co"><img src="${imgPath}leafcom-logo.png"></a>
		<form action="logInAction.co" name="myForm" id="join">
			<fieldset>
				<table>
					<tr>
						<td class="icon" width="360" align="center"><i class="far fa-envelope"></i></td>
						<td><input type="text" name="email" size="40" placeholder="이메일 주소"></td>
					</tr>
				
					<tr>
						<td class="icon" width="360" align="center"><i class="fas fa-key"></i></td>
						<td><input type="password" name="pw" size="40" placeholder="비밀번호"></td>
					</tr>
					<tr>
						<td colspan="2" align="right"><a href="#">아이디/비밀번호 찾기　</a></td>
					</tr>					
					<tr>
						<td colspan="2" align="center" class="btn_green">
						<input type="submit" value="로그인" class="btn_green" style="width:100%;height:100%">
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center" class="cell_white">
						<input type="button" value="회원가입" class="btn_white" style="width:100%;height:100%" 
							onclick="window.location='signIn.co'">
						</td>
					</tr>
				</table>
			</fieldset>
		</form>
		</div>	
	</section>	
</article>

<%@ include file="../../common/footer.jsp" %>
</body>
</html>
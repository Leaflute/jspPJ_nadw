<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>비밀번호 인증</title>
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script type="text/javascript" src="${jsPath}member.js"></script>
</head>
<body>
<form action="withrawMemAction.co" method="post" onsubmit="return withrawFinalChk()">
<%@ include file="../../include/header.jsp" %>

<!-- article 시작 -->
<article class="container">
	<!-- 컨테이너 -->
	<div class="wrap_inner">
		<div id="my_page_box">
			<%@ include file="../myPageNav.jsp" %>

			<!-- section -->
			<section class="mem_content">
				<div class="outer_content">
					<div class="inner_content">
						<div class="title_letter">MY정보관리</div>
						<table>
							<tr>
								<th colspan="2">회원탈퇴를 위해 비밀번호를 입력하세요.</th>
							</tr>
							<tr>
								<td align="right"><i class="fas fa-key"></i></td>
								<td>
									<input type="password" name="pw" size="20" placeholder="비밀번호를 입력" autofocus required>
								</td>
							</tr>
							<tr>
								<th colspan="2">
								<input type="submit" class="little_btn" value="제출">
								<input type="button" class="little_btn" value="돌아가기" onclick="window.history.back()">
							</tr>
						</table>
					</div>
				</div>
			</section>
			<!-- section 종료 -->
		</div>
	</div>
</article>
<!-- article 끝 -->

<%@ include file="../../include/footer.jsp" %>
</form>	
</body>
</html>
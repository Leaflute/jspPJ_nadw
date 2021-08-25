<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../include/setting.jsp" %>   
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}dashboard.css">
<script type="text/javascript" src="${jsPath}item.js"></script>
<script src="./asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>
<meta charset="UTF-8">
<title>상품추가</title>
</head>
<body>
<form action="updateItemAction.ad" method="post" enctype="multipart/form-data" name="additemform" onsubmit="return addItemChk()">
<input type="hidden" name="itemId" value="${dto.itemId}">
<input type="hidden" name="originalSmallImg" value="${dto.smallImg}">
<input type="hidden" name="originalLargeImg" value="${dto.largeImg}">
<input type="hidden" name="originalDetailImg" value="${dto.detailImg}">
<input type="hidden" name="regDate" value="${dto.regDate}">
<input type="hidden" name="grade" value="${dto.grade}">
<input type="hidden" name="pageNum" value="${pageNum}">
<%@ include file="../../include/header.jsp" %>
<!-- article 시작 -->
<article class="container">
	<div class="wrapper">
		<div id="my_page_box">
			<!-- 좌측 메뉴바 -->
			<%@ include file="../../include/nav.jsp" %>	
			<!-- section -->
			<section class="mem_content">
				<div class="outer_content">
					<div class="inner_content">
						<div class="title_letter">상품정보수정</div>
						<table class="input_table">
							<tr>
								<th><label for="regdate">상품 번호</label>
								<td>${dto.itemId}	
							</tr>
							<tr>
								<th><label for="regdate">상품 등록일</label>
								<td>${dto.regDate}	
							</tr>
							<tr>
								<th><label for="categoryId">카테고리 *</label></th>
								<td>
									<select name="categoryId">
										<option disabled>카테고리 선택</option>
										<c:forEach var="categoryMap" items="${categoryMap}">
											<option value="${categoryMap.key}">${categoryMap.value}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th><label for="itemName">상품이름 *</label></th>
								<td><input type="text" id="itemName" name="itemName" size="40" 
										value="${dto.itemName}" required></td>	
							</tr>
							<tr>
								<th><label for="company">제조사 *</label></th>
								<td><input type="text" id="company" name="company" size="40" 
										value="${dto.company}" required></td>	
							</tr>							
							<tr>
								<th><label for="smallImg">썸네일 이미지 *</label></th>
								<td><input type="file" id="smallImg" name="smallImg" size="40" 
									accept="image/*" required></td>	
							</tr>
							<tr>
								<th><label for="largeImg">메인 이미지 *</label></th>
								<td><input type="file" id="largeImg" name="largeImg" size="40" 
									accept="image/*" required></td>	
							</tr>
							<tr>
								<th><label for="detailImg">상품 설명 이미지 *</label></th>
								<td><input type="file" id="detailImg" name="detailImg" 
									size="40" accept="image/*" required></td>	
							</tr>
							<tr>
								<th><label for="info">상품 정보 *</label></th>
								<td><textarea id="info" name="info" rows="10" cols="50">${dto.info}</textarea></td>	
							</tr>
							<tr>
								<th><label for="stock">재고 수량 *</label></th>
								<td><input type="number" min=1 max="1000" id="stock" name="stock" 
										value="${dto.stock}"></td>	
							</tr>
							<tr>
								<th><label for="cost">상품 원가 *</label></th>
								<td><input type="number" min=100 max="100000000" id="cost" name="cost" 
										value="${dto.cost}"></td>	
							</tr>
							<tr>
								<th><label for="price">판매 가격 *</label></th>
								<td><input type="number" min=100 max="100000000" id="price" name="price" 
										value="${dto.price}"></td>	
							</tr>
							<tr>
								<th><label for="price">상품 평점</label></th>
								<td>${dto.grade}</td>
							</tr>
						</table>	
						<div class="outer_content"> 
							<input class="little_btn" type="submit" value="수정하기">
							<input class="little_btn" type="reset" value="초기화">
							<input class="little_btn" type="button" value="목록"
								onclick="window.history.back()">
						</div>
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
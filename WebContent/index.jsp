<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="./asset/setting.jsp"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link rel="stylesheet" type="text/css" href="${cssPath}article.css">
<link rel="stylesheet" type="text/css" href="${cssPath}ad_slideshow.css">

<title>Leafcom에 오신것을 환영합니다</title>
<script src="./asset/js/jquery-3.6.0.min.js" type="text/javascript"></script>
<script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>
<script src="./asset/js/main.js" type="text/javascript"></script>
</head>
<body>

<div class="wrapper">
<%@ include file="./common/header.jsp" %>

<!-- article 시작 -->
<article class="container">
	<!-- slider_ad -->
	<section class="full_width" id="main_ad_wrap">
		<div class="wrap_inner" id="ad_slider">
			<div><img src="./asset/img/MainRollingBanner_94657.jpg"></div>
			<div><img src="./asset/img/MainRollingBanner_95713.jpg"></div>
			<div><img src="./asset/img/MainRollingBanner_95795.jpg"></div>
			<div><img src="./asset/img/MainRollingBanner_96041.jpg"></div>
			<div><img src="./asset/img/MainRollingBanner_96043.jpg"></div>
			<div><img src="./asset/img/MainRollingBanner_97347.jpg"></div> 
		</div>
	</section> 
	
	<!-- container -->
	<section class="wrap_inner">
		
		<!-- 배너 -->
		<div class="cnt_banner">
			<img src="./asset/img/31587_banner.jpg">
			<img src="./asset/img/31282_halfR_banner.jpg.gif">
		</div>
		
		<!-- 상품 카테고리 -->
		<div class="cnt_item_category">
			<div class="cnt_item_left">
				<div class="cnt_item_inner">
					<dl>
						<dt style="font-size:xx-large;">CPU</dt>
						<dd><a href="item/item_category.html">전체보기></a></dd>
						<dd><img src="./asset/img/MainCategoryFixedLeftBanner_96943.jpg"></dd>
					</dl>
					<dl>
						<dt>HOT키워드</dd>
						<dd>#대AMD시대</dd>
						<dd>#세잔APU</dd>
						<dd>#인텔의반격</dd>
					</dl>
				</div>	
			</div>
			<div class="cnt_item_middle">
				<img src="./asset/img/MainCategoryRolling_67760.jpg">
			</div>
			<div class="cnt_item_right" style="border:none;">
				<ul>
					<li><img src="./asset/img/348048_150.jpg"><br><b>0원</b><br>도시바 HDD</li>
					<li><img src="./asset/img/442626_150.jpg"><br><b>0원</b><br>MICRON SSD</li>
					<li><img src="./asset/img/450138_150.jpg"><br><b>0원</b><br>삼성RAM 4GB</li>
					<li><img src="./asset/img/673935_150.jpg"><br><b>0원</b><br>삼성RAM 8GB</li>
					<li><img src="./asset/img/681708_150.jpg"><br><b>0원</b><br>삼성RAM 16GB</li>
					<li><img src="./asset/img/730080_150.jpg"><br><b>0원</b><br>Ryzen 5600x</li>
				</ul>				
			</div>
		</div>
	</section>
</article>
<!-- article 끝 -->
<%@ include file="./common/footer.jsp" %>
</div>
<script type="text/javascript">
$("#ad_slider").slick({
	  slidesToShow: 2,
	  slidesToScroll: 1,
	  autoplay: true,
	  autoplaySpeed: 5000
});
</script>	
</body>
</html>
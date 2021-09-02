/**
 * 
 */

// 상품카테고리 변환으로 url리다이렉트
function displayByCondition() {
	var condition = document.getElementById("selectedCondition");
	console.log(condition.value);
	window.location="orderList.ad?condition="+condition.value;
}
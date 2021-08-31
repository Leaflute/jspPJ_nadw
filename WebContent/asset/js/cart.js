/**
 * 장바구니 javaScript
 */

$(function(){
	$("#chkAll").change(function(){
		var isChecked = $("#chkAll").is(":checked");
		$(".cartcol :checkbox").prop("checked", isChecked);
	});
});
	
function changePrice() {
	var chkBox = document.getElementsByName("caIdArray");
	var rowPrice = Number(0);
	for(var i=0; i<chkBox.length; i++) {
		if(chkBox[i].checked) {
			str = document.getElementsByName("rowPrice")[i].value;
			arr = str.substr(0, str.length-1).split(',');
			price = '';
			for (var j=0; j<arr.length; j++) {
				price += arr[j];
			}
			rowPrice += parseInt(price);
		}
	}
	
	document.getElementById("totalPrice").innerHTML = '총 주문금액 ￦'
		+ totalPrice.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ","); 
}


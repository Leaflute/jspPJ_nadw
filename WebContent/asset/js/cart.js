/**
 * 장바구니 javaScript
 */
function selectAll(selectAll) {
	const chkbox = document.getElementsByName("caIdArray");
	
	chkbox.forEach((checkbox) => {
		checkbox.checked = selectAll.checked
	})
	
	totalPrice();
}

function totalPrice() {
	var chkbox = document.getElementsByName("caIdArray");
	var rowPrice = document.getElementsByName("rowPrice");
	var result = document.getElementById("purchasePrice");
	var sum = 0;
	
	for(var i=0;i<chkbox.length-1;i++) {
		if(chkbox[i+1].checked) {
			sum += parseInt(rowPrice[i].value);
		}
	}
	result.value = sum;
}
define(["jquery"], function($) {
	return function(){
		
	
	var selectVal = $('#type').val();
	console.log(selectVal);

    $('#type').on("focus submit", doSetName);

	function doSetName(){
		$('#productName').val("");
	}		
	}
})

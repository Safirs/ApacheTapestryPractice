define(["jquery"], function($) {

    return function(textboxId, hintText, hintColor) {
		console.log("textboxt 1st");
        var $textbox = $("#" + textboxId);

        var normalColor = $textbox.css("color");

        $textbox.on("focus submit", doClearHint);
        $textbox.on("blur change", doCheckHint);

        $textbox.blur();

        function doClearHint() {

			$('#type').val("");
			console.log("textboxt 2st");

            var $field = $(this);
            
            if ($field.val() == hintText) {
                $field.val("");
            }

            $field.css("color", normalColor);
        }

        function doCheckHint() {
		console.log("textboxt 3st");
            var $field = $(this);

            // If $field is empty, put the hintText in it and set its color to
            // hintColor

            if ($field.val() == "") {
                $field.val(hintText);
                $field.css("color", hintColor);
            }

            // Else if $field contains hintText, set its color to hintColor

            else if ($field.val() == hintText) {
                $field.css("color", hintColor);
            }

            // Else, set the $field's color to its normal color

            else {
                $field.css("color", normalColor);
            }
        }

    }

})

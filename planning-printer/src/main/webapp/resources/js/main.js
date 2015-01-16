function display_loader() {
	$('.ajax-loader').show();
	$('.tab-content').addClass('white-overlay');
}

function hide_loader() {
	$('.ajax-loader').hide();
	$('.tab-content').removeClass('white-overlay');
}
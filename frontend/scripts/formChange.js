$(window).load(function() {
    if ($('#sidebar').height() < $('#mainbar').height())
	$('#sidebar').height($('#mainbar').height());
    $('.opts').click(function(){
	var getlocation = location.href.split("#");
	window.location = getlocation[0] + "#" + $(this).attr('name');
	document.cookie = 'anchor=' + $(this).attr('name');
	window.location.reload();
    });
    $("#register").click(function(){
	$("#register-wrap").html('<input type="password" class="form-control" name="password-check" placeholder="Password Confirmation">');
	$('.form-signin-heading').html('Register');
	$("button[name='login']").html('Go!');
	$("button[name='login']").attr("name","register");
    });
});

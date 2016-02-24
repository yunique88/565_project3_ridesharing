var userID = "";

/** ajax request to Login */
$(document).ready(function() {

    //Stops the submit request
    $("#loginForm").submit(function(e){
           e.preventDefault();
    });
   
    //checks for the button click event
    $("#mySubmit").click(function(e){
		userID = $('#email').val();
    	var var1 = $('#email').val();
    	var var2 = $('#pswd').val();
    	
            $.ajax({
                type: "POST",
                url : 'login',
                data: {
                    email : var1, pswd : var2
                },
                
                //if received a response from the server
                success: function(responseText) { 
                	if (responseText[0] == "t") {
                		$('#dropdownMenu1').addClass("btn-primary").text($('#email').val());
                		$('#dropdownOpenClose').removeClass("open");
                		$('#loginForm').removeClass("show").hide();
                		$('#logout').removeClass("hide").show();
                	} else {
                		$('#loginMsg').text("email & pswd doesn't match!").css('color', 'red');
                	}

                },
                
                //If there was no response from the server
                error: function(responseText) {
                    $('#loginMsg').text(data);
                }
            });         
    });
});


/** ajax request to Logout */
$(document).ready(function() {

    //Stops the submit request
    $("#loginForm").submit(function(e){
           e.preventDefault();
    });
   
    //checks for the button click event
    $("#logout").click(function(e){
    	$('#dropdownMenu1').removeClass("btn-primary").text("Sign in");
		$('#dropdownOpenClose').removeClass("open");
		$('#loginForm').removeClass("hide").show();
		$('#logout').removeClass("show").hide();
		$('#loginMsg').text("");
		$('#email').val("");
		$('#pswd').val("");
    });

});


/** ajax request to Register */
$(document).ready(function() {

    //Stops the submit request
    $("#registerForm").submit(function(e){
           e.preventDefault();
    });
   
    //checks for the button click event
    $("#myRegister").click(function(e){
		userID = $('#emailR').val();
    	var var1 = $('#emailR').val();
    	var var2 = $('#pswdR').val();
    	
            $.ajax({
                type: "POST",
                url : 'login',
                data: {
                    email : var1, pswd : var2
                },
                
                //if received a response from the server
                success: function(responseText) { 
                	if (responseText[0] == "t") {
                		$('#dropdownMenu1').addClass("btn-primary").text($('#email').val());
                		$('#dropdownOpenClose').removeClass("open");
                		$('#loginForm').removeClass("show");
                		$('#loginForm').hide();
                		$('#logout').removeClass("hide");
                		$('#logout').show();
                	} else {
                		$('#loginMsg').text("Email and password doesn't match!").css('color', 'red');
                	}

                },
                
                //If there was no response from the server
                error: function(responseText) {
                    $('#loginMsg').text(data);
                }
            });         
    });
    
});

// register button toggles register form
function register() {
	$('#loginForm').removeClass("show").hide();
	$('#registerForm').removeClass("hide").show();
	$('#loginForm').removeClass("show").hide();
	$('#logout').removeClass("show").hide();
	$('#email').val("");
	$('#pswd').val("");
	$('#dropdownMenu1').text("Register");
	return false;
}

function backToSignIn() {
	$('#loginForm').removeClass("hide").show();
	$('#registerForm').removeClass("show").hide();
	$('#loginForm').removeClass("hide").show();
	$('#loginMsg').text("");
	$('#email').val("");
	$('#pswd').val("");
	$('#dropdownMenu1').text("Sign In");
	return false;
}

function percentageSubmit() {
  alert("percent submit clicked, f(x) ran in js");
  return true;
}

function myRouteSubmit() {
  alert("myRoute submit clicked, f(x) ran in js");
  return true;
}

var map;
function initMap() {
  map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: 47.2414, lng: -122.4594},
    zoom: 11
  });
}
var userID = "";
var map;
var origin = null;
var destination = null;
var waypoints = [];
var markers = [];


function Login() {
	alert('change to 100');
    
	$.post("LoginCheckServlet", {
		"email": $("#email").val(), 
		"pswd": $("#pswd").val()
	}).success(function(data) {
		alert("Login success");
		
	}).fail(function(data) {
		console.log(data);
		alert("Login Failed");
//		$("#result").html("Failed");
	});
}


function outputUpdate(vol) {
	document.querySelector('#matchRateValue').value = vol;
}

function initMap() {
	directionsDisplay = new google.maps.DirectionsRenderer();
    map = new google.maps.Map(document.getElementById('map'), {
    center: {lat: 47.2414, lng: -122.4594},
    mapTypeId: google.maps.MapTypeId.ROADMAP,
    draggableCursor: "pointer",
    zoom: 9
  });
  var geocoder = new google.maps.Geocoder();
  directionsDisplay.setMap(map);
  
  google.maps.event.addListener(map, 'click', function(event) {
    if (origin == null) {
      origin = event.latLng;
      addMarker(origin);
    } else if (destination == null) {
      destination = event.latLng;
      addMarker(destination);
    } else {
    	if (waypoints.length < 3) {
            waypoints.push({ location: destination, stopover: true });
            destination = event.latLng;
            addMarker(destination);
          } else {
            alert("Maximum number of waypoints reached");
          }
    }
  });
}

function geocodeAddress(geocoder, resultsMap) {
	  var departure = document.getElementById('departure').value;
	  var destination = document.getElementById('destination').value;
	  geocoder.geocode({'departure': departure}, function(results, status) {
	    if (status === google.maps.GeocoderStatus.OK) {
	      resultsMap.setCenter(results[0].geometry.location);
	      var origin = results[0].geometry.location;
	      var marker1 = new google.maps.Marker({
	        map: resultsMap,
	        position: results[0].geometry.location
	      });
	    } else {
	      alert('Geocode was not successful for the following reason: ' + status);
	    }
	  });
	  
	var destination = document.getElementById('destination').value;
	  geocoder.geocode({'departure': destination}, function(results, status) {
	    if (status === google.maps.GeocoderStatus.OK) {
	      resultsMap.setCenter(results[0].geometry.location);
	      var destination = results[0].geometry.location;
	      var marker = new google.maps.Marker({
	        map: resultsMap,
	        position: results[0].geometry.location
	      });
	    } else {
	      alert('Geocode was not successful for the following reason: ' + status);
	    }
	  });
	  var lat1 = marker1.getposition().lat();
	  var lng1 = marker1.getposition().lng();
	  var origin = new google.maps.LatLng(lat1,lng1);
	  var lat = marker.getposition().lat();
	  var lng = marker.getposition().lng();
	  var destination = new google.maps.LatLng(lat,lng); 
	  
	  var mode = google.maps.DirectionsTravelMode.DRIVING;
	    var request = {
	        origin: departure,
	        destination: destination,
	        travelMode: mode,
	        avoidHighways: false
	    };
	    directionsService.route(request, function(response, status) {
	        if (status == google.maps.DirectionsStatus.OK) {
	  	    directionsDisplay.setDirections(response);
	        }
	      });
	  
}

function addMarker(latlng) {
    markers.push(new google.maps.Marker({
      position: latlng, 
      map: map,
      icon: "http://maps.google.com/mapfiles/marker" + String.fromCharCode(markers.length + 65) + ".png"
    }));    
  }


function calculateRoute() {
	alert("&&&&");
	var from = $("#departure").val();
	var to = $("#destination").val();
    
    var myOptions = {
      zoom: 10,
      center: new google.maps.LatLng(40.84, 14.25),
      mapTypeId: google.maps.MapTypeId.ROADMAP
    };
    
    // Draw the map
    var mapObject = new google.maps.Map(document.getElementById("map"), myOptions);
    var arr=new Array();
    var directionsService = new google.maps.DirectionsService();
    var directionsRequest = {
      origin: from,
      destination: to,
      travelMode: google.maps.DirectionsTravelMode.DRIVING,
      unitSystem: google.maps.UnitSystem.METRIC
    };
    
    directionsService.route(
      directionsRequest, function(response, status) {
        if (status == google.maps.DirectionsStatus.OK) {
			new google.maps.DirectionsRenderer({
				map: mapObject,
				directions: response
				});
			
			var nPoints = response.routes[0].overview_path.length;
  
	  		for (var i = 0; i < 10; i++) { 
	  			var point = response.routes[0].overview_path[i];
	  			arr.push(new coordinate(point.lat(), point.lng()));
	  		}
	  		
	  		document.querySelector('#departureValue').value = response.routes[0].overview_path[0];
	  		document.querySelector('#destinationValue').value = response.routes[0].overview_path[nPoints-1];
	  		document.querySelector('#routePointsValue').value = JSON.stringify(arr);
	  		 
	  		findMatches();
  		}else
          $("#error").append("Unable to retrieve your route<br />");
      }
    );
  }

function findMatches() {
	$.post("FindMatchServlet", {
		"traceID": $("#traceID").val(),
		"userName": $("#userName").val(),
		"matchRate": $("#matchRate").val(),
		"startTime": $("#startTime").val(),
		"startTimeMargin": $("#startTimeMargin").val(),
		"endTime": $("#endTime").val(),
		"endTimeMargin": $("#endTimeMargin").val(),
		"departure": $("#departureValue").val(),
		"departureMargin": $("#departureMargin").val(),
		"destination": $("#destinationValue").val(),
		"destinationMargin": $("#destinationMargin").val(),
		"departureDate": $("#departureDate").val(),
		"routePoints": $("#routePointsValue").val()
	}).success(function(data) {
		var jsonStr = JSON.stringify(data);
		console.log(jsonStr);
	//	$("#result").html(JSON.stringify(data));
		alert("jsonStr:"+jsonStr);
		for (var i = 0; i < data.length; i++) {
			
			var route = data[i];
			
			var myOptions = {
				      zoom: 10,
				      center: new google.maps.LatLng(40.84, 14.25),
				      mapTypeId: google.maps.MapTypeId.ROADMAP
				    };
			
			var mapObject = new google.maps.Map(document.getElementById("map"), myOptions);
		    var arr=new Array();
		    var directionsService = new google.maps.DirectionsService();
		    
		    var coordinates = JSON.parse(route.coords).coordinates;
		    
		    console.log(coordinates);
		    console.log(coordinates.length);
		    
		    var directionsRequest = {
		      origin: new google.maps.LatLng(coordinates[0][1], coordinates[0][0]),
		      destination: new google.maps.LatLng(coordinates[coordinates.length - 1][1], coordinates[coordinates.length - 1][0]),
		      travelMode: google.maps.DirectionsTravelMode.DRIVING,
		      unitSystem: google.maps.UnitSystem.METRIC
		    };
		    
		    directionsService.route(
		      directionsRequest, function(response, status) {
		        if (status == google.maps.DirectionsStatus.OK) {
					new google.maps.DirectionsRenderer({
						map: mapObject,
						directions: response
						});
		        }
		      });
		}
		
	}).fail(function(data) {
		console.log(data);
		alert("Failed");
//		$("#result").html("Failed");
	});
}

function coordinate(x,y)
{
	this.latitude=x;
	this.longitude=y;
	}

function serializeLatLng(ll) {
	arr.push(new coordinate(ll.lat(),ll.lng()));
}  
function createjson()
{
	var myJsonString = JSON.stringify(arr);
	
	alert("myJsonString:"+myJsonString);
}
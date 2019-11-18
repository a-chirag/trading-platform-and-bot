function portfolio() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == 'notLogin') {
				logout();
			} else
				document.getElementById("portfolio").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "/book/v1/portfolio", true);
	xhttp.send();
}
function netWorth(uid) {
	$.ajax({
		url : 'v1/users/' + uid,
		type : 'GET',
		success : function(result) {
			var port = result.data.portfolio;
			var netWorth;
			for (x in port) {
				$.ajax({
					url : 'v1/orderbooks/'+x,
					type : 'GET',
					success : function(result) {
						var price = undefined;
						var prices = undefined;
						prices = result.data;
						for (key in prices) {
							if (prices.hasOwnProperty(key)) {
								price = prices[key];
							}
						}
						netWorth +=price*port[x];
						alert(netWorth);
					}
				});
			}
		}
	});
}
function cancel(uid, oid) {
	$.ajax({
		url : 'v1/users/' + uid + '/orders/' + oid,
		type : 'DELETE',
		success : function(result) {
			console.log(result);
			pending();
		}
	});
}
function overview() {
	var xhttp2 = new XMLHttpRequest();
	xhttp2.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == 'notLogin') {
				logout();
			} else {
				var obj, dbParam, xmlhttp, myObj, x, txt = "";
				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200) {
						if (this.responseText == 'notLogin') {
							logout();
						} else {
							txt = '<div class="card-columns">';
							myObj = JSON.parse(this.responseText);
							myObj = myObj.data;
							txt = '<table class="table table-striped table-hover"><tr><th>Symbol</th><th>Desc</th><th>Price</th><th>Chart</th></tr>';
							for (x in myObj) {
								var price = undefined;
								var prices = undefined;
								prices = myObj[x].prices;
								for (key in prices) {
									if (prices.hasOwnProperty(key)) {
										price = prices[key];
									}
								}
								if (price === undefined) {
									price = "not Priced";
								}

								txt += '<tr onclick=orderview("'
										+ myObj[x].instrument + '")><td >'
										+ myObj[x].instrument + '</td> <td>'
										+ myObj[x].desc
										+ '</td><td  class="success">$ '
										+ price + '</td>'
										+ '<td><div class="col-md-3" id="'
										+ myObj[x].instrument
										+ 'graph"></div></td>' + '</tr>'
							}
							txt += '</table>'
							document.getElementById("overview").innerHTML = txt;
							for (x in myObj) {
								makeGraph3(myObj[x].instrument);
							}
						}
					}
				};
				xhttp.open("GET", "/book/v1/orderbooks", true);
				xhttp.send();
			}

		}
	};
	xhttp2.open("GET", "/book/v1/pending", true);
	xhttp2.send();

}
function buy(form) {

	var price = form.buyprice.value;
	var size = form.size.value;
	var formData = 'price=' + price + '&size=' + size + '&side=' + 'buy'
			+ '&instrument=' + form.instrument.value;
	// process the form
	$.ajax({
		url : "/book/v1/addorder",
		type : "get",
		data : formData,
		processData : false, // tell jQuery not to process the data
		contentType : false
	// tell jQuery not to set contentType
	}).done(function(data) {
		$("#myModal").modal({
			backdrop : false
		});
		setTimeout(function() {
			$("#myModal").modal('hide');
		}, 1000);
		orderviewreload(form.instrument.value, price, 1);
		// Perform ANy action after successfuly post data

	});
	return false;
}
function sell(form) {

	var price = form.sellprice.value;
	var size = form.size.value;
	var formData = 'price=' + price + '&size=' + size + '&side=' + 'Sell'
			+ '&instrument=' + form.instrument.value;
	// process the form
	$.ajax({
		url : "/book/v1/addorder",
		type : "get",
		data : formData,
		processData : false, // tell jQuery not to process the data
		contentType : false
	// tell jQuery not to set contentType
	}).done(function(data) {
		$("#myModal").modal({
			backdrop : false
		});
		setTimeout(function() {
			$("#myModal").modal('hide');
		}, 1000);
		orderviewreload(form.instrument.value, 0, price);
		// Perform ANy action after successfuly post data

	});
	return false;
}
function pending() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == 'notLogin') {
				logout();
			} else
				document.getElementById("pending").innerHTML = this.responseText;
		}
	};
	xhttp.open("GET", "/book/v1/pending", true);
	xhttp.send();
}
function executed() {
	// var xhttp = new XMLHttpRequest();
	// xhttp.onreadystatechange = function() {
	// if (this.readyState == 4 && this.status == 200) {
	// if (this.responseText == 'notLogin') {
	// logout();
	// } else{
	document.getElementById("executed").innerHTML += '<div>';

	// }
	// }
	// };
	// xhttp.open("GET", "/book/v1/executed", true);
	// xhttp.send();
}
function addorder() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == 'notLogin') {
				logout();
			} else
				document.getElementById("addorder").innerHTML = this.responseText;
		}
	};
	xhttp.open("get", "/book/v1/order", true);
	xhttp.send();
}

function submitForm() {
	var side = 'buy';
	var bool = (1 == 1);
	if (document.getElementById("side").checked == bool) {
		var side = 'sell';
	}

	var formData = 'price=' + $('input[name=price]').val() + '&size='
			+ $('input[name=size]').val() + '&side=' + side + '&instrument='
			+ $('input[name=instrument]').val();
	// process the form
	$.ajax({
		url : "/book/v1/addorder",
		type : "get",
		data : formData,
		processData : false, // tell jQuery not to process the data
		contentType : false
	// tell jQuery not to set contentType
	}).done(function(data) {
		$("#myModal").modal({
			backdrop : false
		});
		setTimeout(function() {
			$("#myModal").modal('hide');
		}, 1000);
		// Perform ANy action after successfuly post data

	});
	document.getElementById("addorderform").reset();
	return false;
}
function check() {
	$("#myModal").modal({
		backdrop : false
	});
	setTimeout(function() {
		$("#myModal").modal('hide');
	}, 1000);
}
$('#loginModal').on("shown.bs.modal", function() {
	$('#username').focus();
});
function logout() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			$("#loginModal").modal();
			$('#loginbutton').val("login");
			document.getElementById("invalid").innerHTML = '';
			$('input[name="username"]').focus();
		}
	};
	xhttp.open("get", "/book/v1/logout", true);
	xhttp.send();
}
function login() {
	var formData = 'username=' + $('input[name=username]').val() + '&password='
			+ $('input[name=password]').val();
	// process the form
	$
			.ajax({
				url : "/book/v1/loginuser",
				type : "get",
				data : formData,
				processData : false, // tell jQuery not to process the data
				contentType : false
			// tell jQuery not to set contentType
			})
			.done(
					function(data) {
						if (data == 'invalid') {
							document.getElementById("invalid").innerHTML = 'Invalid Credentials';
							document.getElementById("invalid").style.color = 'red';
							document.getElementById("loginForm").reset();
						} else {
							location.reload();
							$("#loginModal").modal('hide');
							$('#loginbutton').val("logout");
						}
						// Perform ANy action after successfuly post data

					});
	document.getElementById("addorderform").reset();
	return false;
}
function register() {
	$("#loginModal").modal('hide');
	$("#registerModal").modal();
	$('input[name="username"]').focus();
}
function registerUser() {
	var formData = 'username=' + $('input[name=rusername]').val()
			+ '&password=' + $('input[name=rpassword]').val();
	// process the form
	$.ajax({
		url : "/book/v1/registeruser",
		type : "get",
		data : formData,
		processData : false, // tell jQuery not to process the data
		contentType : false
	// tell jQuery not to set contentType
	}).done(function(data) {
		$("#registerModal").modal('hide');
		$('#loginbutton').val("logout");
		$.ajax({
			url : "/book/v1/loginuser",
			type : "get",
			data : formData,
			processData : false, // tell jQuery not to process the data
			contentType : false
		// tell jQuery not to set contentType
		}).done(function(data) {
			location.reload();
			$('#loginbutton').val("logout");
			// Perform ANy action after successfuly post data

		});
		// Perform ANy action after successfuly post data
	});
	document.getElementById("addorderform").reset();
	return false;
}
function orderview(instrument) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == 'notLogin') {
				logout();
			} else {

				document.getElementById("overview").innerHTML = this.responseText;
				// document.getElementById("overview").innerHTML+='<p> <label
				// for="amount">Price range:</label> <input type="text"
				// id="amount" readonly style="border:0; color:#f6931f;
				// font-weight:bold;"> </p> <div id="slider-range"></div>';
				makeGraph2(instrument);
			}
		}
	};
	xhttp.open("get", "/book/v1/orderbook?inst=" + instrument, true);
	xhttp.send();
}
function orderviewreload(instrument, buy, sell) {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == 'notLogin') {
				logout();
			} else {

				document.getElementById("overview").innerHTML = this.responseText;
				// document.getElementById("overview").innerHTML+='<p> <label
				// for="amount">Price range:</label> <input type="text"
				// id="amount" readonly style="border:0; color:#f6931f;
				// font-weight:bold;"> </p> <div id="slider-range"></div>';
				makeGraph2(instrument);
			}
		}
	};
	xhttp.open("get", "/book/v1/orderbook?inst=" + instrument, true);
	xhttp.send();
}
function something(inst, buy, sell) {
	var price = '#' + inst + '-slider-range';
	var size = '#' + inst + '-size-slider';
	var form = document.getElementById(inst);
	$(price)
			.slider(
					{
						range : true,
						min : 0,
						max : 1,
						step : 0.01,
						values : [ buy, sell ],
						slide : function(event, ui) {
							form.buyprice.value = (ui.values[0]);
							form.sellprice.value = (ui.values[1]);
							form.buyProfit.value = ((1 - form.buyprice.value) * form.size.value);
							form.sellProfit.value = ((form.sellprice.value) * form.size.value);
						}
					});
	form.buyprice.value = ($(price).slider("values", 0));
	form.sellprice.value = ($(price).slider("values", 1));
	$(size)
			.slider(
					{
						min : 1,
						max : 10,
						step : 1,
						value : 1,
						slide : function(event, ui) {
							form.size.value = (ui.value);
							form.buyProfit.value = ((1 - form.buyprice.value) * form.size.value);
							form.sellProfit.value = ((form.sellprice.value) * form.size.value)
						}
					});
	form.size.value = ($(size).slider("value"));
}
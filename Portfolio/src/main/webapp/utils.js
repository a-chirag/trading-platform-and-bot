function sortTable(tablen, n) {
	var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
	table = document.getElementById(tablen);
	switching = true;
	// Set the sorting direction to ascending:
	dir = "asc";
	/*
	 * Make a loop that will continue until no switching has been done:
	 */
	while (switching) {
		// Start by saying: no switching is done:
		switching = false;
		rows = table.getElementsByTagName("TR");
		/*
		 * Loop through all table rows (except the first, which contains table
		 * headers):
		 */
		for (i = 1; i < (rows.length - 1); i++) {
			// Start by saying there should be no switching:
			shouldSwitch = false;
			/*
			 * Get the two elements you want to compare, one from current row
			 * and one from the next:
			 */
			x = rows[i].getElementsByTagName("TD")[n];
			y = rows[i + 1].getElementsByTagName("TD")[n];
			/*
			 * Check if the two rows should switch place, based on the
			 * direction, asc or desc:
			 */
			if (dir == "asc") {
				if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
					// If so, mark as a switch and break the loop:
					shouldSwitch = true;
					break;
				}
			} else if (dir == "desc") {
				if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
					// If so, mark as a switch and break the loop:
					shouldSwitch = true;
					break;
				}
			}
		}
		if (shouldSwitch) {
			/*
			 * If a switch has been marked, make the switch and mark that a
			 * switch has been done:
			 */
			rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			switching = true;
			// Each time a switch is done, increase this count by 1:
			switchcount++;
		} else {
			/*
			 * If no switching has been done AND the direction is "asc", set the
			 * direction to "desc" and run the while loop again.
			 */
			if (switchcount == 0 && dir == "asc") {
				dir = "desc";
				switching = true;
			}
		}
	}
}
function makeGraph(inst) {
	var xmlhttp, data;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == 'notLogin') {
				logout();
			} else {
				gdata = JSON.parse(this.responseText);
				gdata = gdata.data;
				var key;
				var data;
				var timeFormat = d3.time.format('%Y-%m-%dT%H:%M:%S');
				var timeFormat2 = d3.time.format("%Y-%m-%dT%H:%M:%S.%L%Z");
				data=[];
				for (key in gdata) {
					if (gdata.hasOwnProperty(key)) {
						var flag;
						flag = {
								"at" : "2014-11-18T07:29:03",
								"value" : 0.553292
							};
						flag.at = String(key);
						flag.value=gdata[key];
					}
					data.push(flag);
				}

	             //initializing dimensions of the visulisation
	             vis = d3.select("#graph").append('svg'),
	                 WIDTH = 400,
	                 HEIGHT = 300,
	                 MARGINS = {
	                     top: 20,
	                     right: 20,
	                     bottom: 20,
	                     left: 50
	             }
	             
	             vis.attr('height', HEIGHT)
	                .attr('width', WIDTH);

	             //Defining time format
	             

	             //Defining range for x. Defining range and domain for y
	             var x = d3.time.scale().range([MARGINS.left, WIDTH - MARGINS.right])
	             var y = d3.scale.linear().range([HEIGHT - MARGINS.top, MARGINS.bottom])//.domain([0, 20])


	             //Defining domain for x
	             x.domain([timeFormat2.parse(data[0].at), timeFormat2.parse(data[data.length-1].at)])
	             //x.domain(d3.extent(data, function 0d) { return d.metricDate; }));
	             y.domain([0, d3.max(data, function (d) { return +d.value; })]);


	             //Define x axis
	             var xAxis = d3.svg.axis()
	                 .scale(x)
	                 .ticks(4)
	                 .orient("bottom")
	                 .tickFormat(d3.time.format("%H:%M:%S")); //<== insert the tickFormat function

	             //Define y axis
	             var yAxis = d3.svg.axis()
	                 .scale(y)
	                 .orient("left");

	             //Appending the axes to the svg
	             vis.append("svg:g")
	                 .attr("class", "axis")
	                 .attr("transform", "translate(0," + (HEIGHT - MARGINS.bottom) + ")")
	                 .call(xAxis);

	             vis.append("svg:g")
	                 .attr("class", "axis")
	                 .attr("transform", "translate(" + (MARGINS.left) + ",0)")
	                 .call(yAxis);

	             //Define line
	             var lineGen = d3.svg.line()
	                 .x(function (d) {
	                     return x(timeFormat2.parse(d.at));
	                 })
	                 .y(function (d) {
	                     return y(d.value);
	                 });
	             var make_x_axis = function () {
	            	    return d3.svg.axis()
		                 .scale(x)
		                 .ticks(4)
		                 .orient("bottom")
		                 .tickFormat(d3.time.format("%H:%M:%S")); 
	            	};

	            	var make_y_axis = function () {
	            	    return d3.svg.axis()
		                 .scale(y)
		                 .orient("left");
	            	};

	             var zoom = d3.behavior.zoom()
	             .x(x)
	             .y(y)
	             .on("zoom", zoomed);
	             //Appending the line to the svg
	             vis.append('svg:path')
	                 .attr('d', lineGen(data))
	                 .attr('stroke', 'green')
	                 .attr('stroke-width', 2)
	                 .attr('fill', 'none');
	             vis.call(zoom);
	             function zoomed() {
	            	    console.log(d3.event.translate);
	            	    console.log(d3.event.scale);
	            	    vis.select(".x.axis").call(xAxis);
	            	    vis.select(".y.axis").call(yAxis);
	            	    vis.select(".x.grid")
	            	        .call(make_x_axis()
	            	        .tickSize(-HEIGHT + MARGINS.top+ MARGINS.bottom, 0, 0)
	            	        .tickFormat(""));
	            	    vis.select(".y.grid")
	            	        .call(make_y_axis()
	            	        .tickSize(+MARGINS.left- WIDTH + MARGINS.right, 0, 0)
	            	        .tickFormat(""));
	            	    vis.select(".line")
	            	        .attr("class", "line")
	            	        .attr("d", lineGen);
	            	}
			}
		}
	};
	xhttp.open("GET", "/book/v1/orderbooks/" + inst + "/prices", true);
	xhttp.send();
}
function makeGraph2(inst) {
	var xmlhttp, data;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == 'notLogin') {
				logout();
			} else {
				gdata = JSON.parse(this.responseText);
				gdata = gdata.data;
				var key;
				var data;
				var timeFormat = d3.time.format('%Y-%m-%dT%H:%M:%S');
				var timeFormat2 = d3.time.format("%Y-%m-%dT%H:%M:%S.%L%Z");
				data=[];
				for (key in gdata) {
					if (gdata.hasOwnProperty(key)) {
						var flag;
						flag = {
								"date" : timeFormat2.parse("2014-11-18T07:29:03"),
								"value" : 0.553292
							};
						flag.date = timeFormat2.parse(String(key));
						console.log(flag.date);
						flag.value=gdata[key];
					}
					data.push(flag);
				}

	             //initializing dimensions of the visulisation
				margin = {
					    top: 20,
					    right: 20,
					    bottom: 20,
					    left: 45
					};

					width = 300 - margin.left - margin.right;
					height = 150 - margin.top - margin.bottom;

					var min = d3.min(d3.extent(data, function (d) {
					    return d.value;
					}));
					var max = d3.min(d3.extent(data, function (d) {
					    return d.value;
					}));
					var x = d3.time.scale()
					    .domain(d3.extent(data, function (d) {
					    return d.date;
					}))
					    .range([0, width]);

					var y = d3.scale.linear()
					    .domain([min/1.5,max+10])
					    .range([height, 0]);

					var line = d3.svg.line()
					    .x(function (d) {
					    return x(d.date);
					})
					    .y(function (d) {
					    return y(d.value);
					});

					  var zoom = d3.behavior.zoom()
					      .x(x)
					      .on("zoom", zoomed);

					svg = d3.select('#'+inst+'graph')
					    .append("svg:svg")
					    .attr('width', width + margin.left + margin.right)
					    .attr('height', height + margin.top + margin.bottom)
					    .call(zoom)
					    .append("svg:g")
					    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

					var make_x_axis = function () {
					    return d3.svg.axis()
					        .scale(x)
					        .orient("bottom")
					        .ticks(5);
					};

					var make_y_axis = function () {
					    return d3.svg.axis()
					        .scale(y)
					        .orient("left")
					        .ticks(5);
					};

					var xAxis = d3.svg.axis()
					    .scale(x)
					    .orient("bottom")
					    .ticks(5);

					svg.append("svg:g")
					    .attr("class", "x axis")
					    .attr("transform", "translate(0, " + height + ")")
					    .call(xAxis);

					var yAxis = d3.svg.axis()
					    .scale(y)
					    .orient("left")
					    .ticks(5);

					svg.append("g")
					    .attr("class", "y axis")
					    .call(yAxis);

					svg.append("g")
					    .attr("class", "x grid")
					    .attr("transform", "translate(0," + height + ")")
					    .call(make_x_axis()
					    .tickSize(-height, 0, 0)
					    .tickFormat(""));

					svg.append("g")
					    .attr("class", "y grid")
					    .call(make_y_axis()
					    .tickSize(-width, 0, 0)
					    .tickFormat(""));

					var clip = svg.append("svg:clipPath")
					    .attr("id", "clip")
					    .append("svg:rect")
					    .attr("x", 0)
					    .attr("y", 0)
					    .attr("width", width)
					    .attr("height", height);

					var chartBody = svg.append("g")
					    .attr("clip-path", "url(#clip)");

					chartBody.append("svg:path")
					    .datum(data)
					    .attr("class", "line")
					    .attr("d", line);

					function zoomed() {
					    console.log(d3.event.translate);
					    console.log(d3.event.scale);
					    svg.select(".x.axis").call(xAxis);
					    svg.select(".y.axis").call(yAxis);
					    svg.select(".x.grid")
					        .call(make_x_axis()
					        .tickSize(-height, 0, 0)
					        .tickFormat(""));
					    svg.select(".y.grid")
					        .call(make_y_axis()
					        .tickSize(-width, 0, 0)
					        .tickFormat(""));
					    svg.select(".line")
					        .attr("class", "line")
					        .attr("d", line);
					} 
			}
		}
	};
	xhttp.open("GET", "/book/v1/orderbooks/" + inst + "/prices", true);
	xhttp.send();
}
function makeGraph3(inst) {
	var xmlhttp, data;
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			if (this.responseText == 'notLogin') {
				logout();
			} else {
				gdata = JSON.parse(this.responseText);
				gdata = gdata.data;
				var key;
				var data;
				var timeFormat = d3.time.format('%Y-%m-%dT%H:%M:%S');
				var timeFormat2 = d3.time.format("%Y-%m-%dT%H:%M:%S.%L%Z");
				data=[];
				for (key in gdata) {
					if (gdata.hasOwnProperty(key)) {
						var flag;
						flag = {
								"date" : timeFormat2.parse("2014-11-18T07:29:03"),
								"value" : 0.553292
							};
						flag.date = timeFormat2.parse(String(key));
						console.log(flag.date);
						flag.value=gdata[key];
					}
					data.push(flag);
				}

	             //initializing dimensions of the visulisation
				margin = {
					    top: 0,
					    right: 0,
					    bottom:0 ,
					    left:0 
					};

					width = 70 - margin.left - margin.right;
					height =35 - margin.top - margin.bottom;
					var min = d3.min(d3.extent(data, function (d) {
					    return d.value;
					}));
					var max = d3.min(d3.extent(data, function (d) {
					    return d.value;
					}));
					var x = d3.time.scale()
					    .domain(d3.extent(data, function (d) {
					    return d.date;
					}))
					    .range([0, width]);

					var y = d3.scale.linear()
					    .domain([min/1.5,max+10])
					    .range([height, 0]);

					var line = d3.svg.line()
					    .x(function (d) {
					    return x(d.date);
					})
					    .y(function (d) {
					    return y(d.value);
					});

					  var zoom = d3.behavior.zoom()
					      .x(x)
					      .on("zoom", zoomed);

					svg = d3.select('#'+inst+'graph')
					    .append("svg:svg")
					    .attr('width', width + margin.left + margin.right)
					    .attr('height', height + margin.top + margin.bottom)
					    .call(zoom)
					    .append("svg:g")
					    .attr("transform", "translate(" + margin.left + "," + margin.top + ")");

					var make_x_axis = function () {
					    return d3.svg.axis()
					        .scale(x)
					        .orient("bottom").tickFormat("");
					};

					var make_y_axis = function () {
					    return d3.svg.axis()
					        .scale(y)
					        .orient("left").tickFormat("");
					};

					var xAxis = d3.svg.axis()
					    .scale(x)
					    .orient("bottom").tickFormat("");
					    
					var yAxis = d3.svg.axis()
					    .scale(y)
					    .orient("left").tickFormat("");

					svg.append("svg:g")
					    .attr("class", "x axis")
					    .attr("transform", "translate(0, " + height + ")")
					    .call(xAxis);


					svg.append("g")
					    .attr("class", "y axis")
					    .call(yAxis);

					svg.append("g")
					    .attr("class", "x grid")
					    .attr("transform", "translate(0," + height + ")")
					    .call(make_x_axis()
					    .tickSize(-height, 0, 0)
					    .tickFormat(""));

					svg.append("g")
					    .attr("class", "y grid")
					    .call(make_y_axis()
					    .tickSize(-width, 0, 0)
					    .tickFormat(""));

					var clip = svg.append("svg:clipPath")
					    .attr("id", "clip")
					    .append("svg:rect")
					    .attr("x", 0)
					    .attr("y", 0)
					    .attr("width", width)
					    .attr("height", height);

					var chartBody = svg.append("g")
					    .attr("clip-path", "url(#clip)");

					chartBody.append("svg:path")
					    .datum(data)
					    .attr("class", "line")
					    .attr("d", line);

					function zoomed() {
					    console.log(d3.event.translate);
					    console.log(d3.event.scale);
					    svg.select(".x.axis").call(xAxis);
					    svg.select(".y.axis").call(yAxis);
					    svg.select(".x.grid")
					        .call(make_x_axis()
					        .tickSize(-height, 0, 0)
					        .tickFormat(""));
					    svg.select(".y.grid")
					        .call(make_y_axis()
					        .tickSize(-width, 0, 0)
					        .tickFormat(""));
					    svg.select(".line")
					        .attr("class", "line")
					        .attr("d", line);
					} 
			}
		}
	};
	xhttp.open("GET", "/book/v1/orderbooks/" + inst + "/prices", true);
	xhttp.send();
}
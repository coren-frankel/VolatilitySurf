function myFunc(coords){
	d3.json(coords, function (err, rows) {
	    function unpack(rows, key) {
	        return rows.map(function (row) {
	            return row[key];
	        });
	    }
	
	    var z_data = []
	    for (i = 0; i < coords.size(); i++) {
	        z_data.push(unpack(rows, i));
	    }
	
	    var data = [{
	        z: z_data,
	        type: 'surface'
	    }];
	
	    var layout = {
	        title: 'Volatility Surface',
	        autosize: true,
	        width: 550,
	        height: 550,
	        margin: {
	            l: 0,
	            r: 50,
	            b: 50,
	            t: 50,
	        },
	        scene: {
	            xaxis: {
	                title: "Moneyness"
	            },
	            yaxis: {
	                title: "Time to Maturity"
	            },
	            zaxis: {
	                title: "Implied Volatility"
	            }
	        }
	    }
	    Plotly.newPlot('myDiv', data, layout);
	})
};
data = [{
    type: 'scatter3d',
    x: xdatacall,
    y: ydatacall,
    z: zdatacall,
    mode: 'markers',
    marker: { 
        color: 'green',
        size: 2
    },
    name: "Calls",
    hovertemplate: "Exp: %{y} days<br>Mnns: %{x:.2f}<br>Vol: %{z:.1f}%<extra>Call</extra>"
},
{
    type: 'scatter3d',
    x: xdataput,
    y: ydataput,
    z: zdataput,
    mode: 'markers',
    marker: { 
        color: 'orange',
        size: 2
    },
    name: "Puts",
    hovertemplate: "Exp: %{y} days<br>Mnns: %{x:.2f}<br>Vol: %{z:.1f}%<extra>Put</extra>"
}];
layout = { 
    title: "Implied Volatility",
    width: 800,
	height: 600,
    margin: {
        l: 0,
        r: 0,
        b: 0,
        t: 30,
    },
    scene:{
        aspectratio: { x: 1, y: 1, z: 0.4 },
        camera: { 
			//projection: {type: "orthographic"},
			eye: { x: -1, y: 1.5, z: 0.5 }
			},
        xaxis: {
            title: "Moneyness",
            backgroundcolor: 'rgb(235, 235, 235)',
            gridcolor: 'rgb(255, 255, 255)',
            zerolinecolor: "rgb(255, 255, 255)",
            showbackground: true
        },
        yaxis: {
            title: "Expiry (calendar days)",
            backgroundcolor: 'rgb(235, 235, 235)',
            gridcolor: 'rgb(255, 255, 255)',
            zerolinecolor: "rgb(255, 255, 255)",
            showbackground: true
        },
        zaxis: {
            title: "Volatility",
            backgroundcolor: 'rgb(235, 235, 235)',
            gridcolor: 'rgb(255, 255, 255)',
            zerolinecolor: "rgb(255, 255, 255)",
            showbackground: true
        }}
};
Plotly.newPlot(document.getElementById('plot'), data, layout);
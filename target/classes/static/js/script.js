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
    name: "Calls"
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
    name: "Puts"
}];
layout = { 
    title: "Implied Volatility Plot",
    width: 600,
	height: 600,
    margin: {
        l: 50,
        r: 50,
        b: 50,
        t: 50,
    },
    scene:{
        aspectratio: { x: 1, y: 1.2, z: 1 },
        // camera: { projection: {type: "orthographic"}},
        xaxis: {
            title: "Moneyness",
            backgroundcolor: 'rgb(235, 235, 235)',
            gridcolor: 'rgb(255, 255, 255)',
            zerolinecolor: "rgb(255, 255, 255)",
            showbackground: true
        },
        yaxis: {
            title: "Expiry",
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
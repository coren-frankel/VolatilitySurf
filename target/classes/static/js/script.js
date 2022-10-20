xdata = [2, 3, 4, 5, 3, 5];
ydata = [2, 4, 8, 16, 7, 1];
zdata = [1, 2, 1, 2, 2.5, 1.5];
data = [{
    type: 'scatter3d',
    x: xdata,
    y: ydata,
    z: zdata,
    mode: 'markers',
    marker: { 
        color: 'green',
        size: 3
    }
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
        xaxis: {
            title: "Moneyness",
            nticks: 7,
            range: [0, 6],
            backgroundcolor: 'rgb(235, 235, 235)',
            gridcolor: 'rgb(255, 255, 255)',
            zerolinecolor: "rgb(255, 255, 255)",
            showbackground: true
        },
        yaxis: {
            title: "Expiry",
            nticks: 5,
            range: [0, 20],
            backgroundcolor: 'rgb(235, 235, 235)',
            gridcolor: 'rgb(255, 255, 255)',
            zerolinecolor: "rgb(255, 255, 255)",
            showbackground: true
        },
        zaxis: {
            title: "Volatility",
            nticks: 7,
            range: [0, 3],
            backgroundcolor: 'rgb(235, 235, 235)',
            gridcolor: 'rgb(255, 255, 255)',
            zerolinecolor: "rgb(255, 255, 255)",
            showbackground: true
        }}
};
Plotly.newPlot(document.getElementById('plot'), data, layout);
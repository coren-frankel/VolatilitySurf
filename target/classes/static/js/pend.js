const loadingBar = document.getElementById('loadingBar');
function search(){
	//DISABLE SEARCH BUTTON AFTER SUBMISSION
	document.getElementById("search").disabled = true
	document.getElementById("error-bar").innerHTML = "";
	//MUCH OF THIS IS AMALGAMATED FROM TUTORIALS
	async function task() {//SIMULATED PROMISES
		return new Promise(res => {
	   		setTimeout(res, Math.random() * 30000);
	  	})//WITH RANDOM TIMEOUTS ON A RESPONSE
	}
	function loadingBarStatus(current) {
		loadingBar.innerHTML = `
	  		<div class="progress m-auto ml9" style="width: 50%;">
				<div class="progress-bar progress-bar-striped 
				progress-bar-animated bg-primary" role="progressbar" 
				style="width: ${current}%">
					${current < 45 ? "Fetching Real-Time Stock Option Data..." : "The Data \"Tide\" Is Coming In..."}
				</div>
			</div>`;
    				
	}//BOOTSTRAP PROGRESS BAR INCLUDING TERNARY TXT MSG
	
	(async() => {//100 "TASKS" PROMISED FOR SIMULATED PROGRESS
		let current = 1;
		const promises = new Array(100)
	    	.fill(0)
	    	.map(() => task().then(() => loadingBarStatus(current++)));
	  	await Promise.all(promises);
	  	loadingBar.innerHTML = `
	  		<div class="progress m-auto" style="width: 50%;">
				<div class="progress-bar progress-bar-striped
				progress-bar-animated bg-primary" role="progressbar" 
				style="width: 99%;">
				Almost Done... This One Should Make A Splash! 🌊
				</div>
			</div>`;
	})();		
}
const form = document.getElementById('form');
form.addEventListener('submit', () => search());
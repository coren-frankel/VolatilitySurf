const loadingBar = document.getElementById('loadingBar');
function search(){
	//DISABLE SEARCH BUTTON AFTER SUBMISSION
	document.getElementById("search").disabled = true
	//MUCH OF THIS IS BORROWED & PATCHED FROM TUTORIALS
	async function task() {//SIMULATED PROMISES
		return new Promise(res => {
	   		setTimeout(res, Math.random() * 30000);
	  	})//WITH RANDOM TIMEOUTS ON A RESPONSE
	}
	function loadingBarStatus(current) {
		loadingBar.innerHTML = `
	  		<div class="progress m-auto ml9" style="width: 50%;">
				<div class="progress-bar progress-bar-striped 
				progress-bar-animated bg-info" role="progressbar" 
				style="width: ${current}%">
					${current < 50 ? "Fetching Real-Time Stock Option Data..." : "<>The <em>Data-Tide</em> Is Coming In! Almost There...</>"}
				</div>
			</div>`;
    				
	}//UPDATES BOOTSTRAP PROGRESS BAR INCLUDING TERNARY TXT MSG
	
	(async() => {
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
				We're almost done pooling the data! This one should make a splash... 🌊
				</div>
			</div>`;
	})();		
}
const form = document.getElementById('form');
form.addEventListener('submit', () => search());
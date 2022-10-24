const loadingBar = document.getElementById('loadingBar');
function search(){
	document.getElementById("search").disabled = 'true'
	async function task() {
	  return new Promise(res => {
	    setTimeout(res, 20000);
	  })
	}
	function loadingBarStatus(current) {
	  loadingBar.innerHTML = `
	  		<div class="progress m-auto" style="width: 50%;">
				<div class="progress-bar progress-bar-striped 
				progress-bar-animated bg-info" role="progressbar" 
				style="width: ${current}%">
				</div>
				Fetching real-time stock option data...
			</div>`;
	}
	(async() => {
	  let current = 1;
	  const promises = new Array(100)
	    .fill(0)
	    .map(() => task().then(() => loadingBarStatus(current++)));
	  await Promise.all(promises);
	  loadingBar.innerHTML = `
	  		<div class="progress m-auto" style="width: 50%;">
				<div class="progress-bar progress-bar-striped
				progress-bar-animated" role="progressbar" 
				style="width: 100%">Surfs Up! 🌊
				</div>
			</div>`;
	})();		
}
const button = document.getElementById("search");
button.addEventListener("click", function() {
	//MORPH THIS SUCKER INTO THE 'RETURN' KEY LISTENER
    search()
}, {once : true});
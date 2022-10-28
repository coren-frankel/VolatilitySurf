$(document)
	.ready(function () {
  	$('#callTable')
  		.DataTable({
  			ordering: true,
	        paging: false,
	        searching: false,
	        info: false,
	        stickyHeader: true,
	        processing: true,
	        scrollY: 300,
	        responsive: true,
	        scrollCollapse: true,
	        order: [2,'asc']
      });
});
$(document)
.ready(function () {
	$('#putTable')
		.DataTable({
			ordering: true,
        paging: false,
        searching: false,
        info: false,
        stickyHeader: true,
        processing: true,
        scrollY: 300,
        responsive: true,
        scrollCollapse: true,
        order: [2,'asc']
  });
});
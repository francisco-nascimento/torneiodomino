
$(document).ready(function() {
	$('select').formSelect();
	setTimeout(function() {
		$('#cardMsg').hide(); // "foo" é o id do elemento que seja manipular.
	}, 3000); // O valor é representado em milisegundos.

	//initialize all modals
	$('.modal').modal({
		dismissible: true
	});

	$('.tabs').tabs();
	$(".dropdown-trigger").dropdown();
	$('.sidenav').sidenav();



});

function exibirModalErro() {
	$(document).ready(function() {

		//initialize all modals
		$('.modal').modal({
			dismissible: true
		});

		//call the specific div (modal)
		$('#modal1').modal('open');

	});
}
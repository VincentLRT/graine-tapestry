/*global jQuery:true, window:true, document:true, isMobile:true, animateScrollTo:true */
/*jslint node: true */

// Décommenter pendant les modifications pour tester le code en mode strict
// "use strict";

jQuery(document).ready(function($){

	/* ================ SET FOCUS for anchor link in url  ==================== */
	/* If there is a '#' in the URL (someone linking directly to a page with an anchor), highlight that section and set focus to it.
	The tabindex attribute is removed AFTER the user navigates away from the element to help address a nasty VoiceOver bug.

	source : http://webaim.org/
	*/

	if (document.location.hash) {
		var myAnchor = document.location.hash;
		var heightHeader = $('header').height();

		if ( $('.sub-nav').length && $('.sub-nav').css('position') == 'fixed' ){
			var heightSub =  $('.sub-nav').height() + 39;
			var heightHeader = heightHeader + heightSub;
		}
		//console.log( ' heightHeader : ' +$('header').height() + ' + heightSub : ' + heightSub);

		if (myAnchor != "#this" && myAnchor != "#more-link" ){ // évite de réaliser les actions si ce hash particulier est présent
			$(myAnchor).attr('tabindex', -1).on('blur focusout', function () {
				$(this).removeAttr('tabindex');
			}).focus();
			animateScrollTo($(myAnchor).offset().top - heightHeader, 700);
		}
	}

	/* ================ SET FOCUS for skip link  ==================== */
	/* This function looks for a change in the hash (activation of an in-page link) and sets focus to and
	highlights the target element. This is necessary because webkit does not set focus as it should. If
	the hash is empty (the user hit the back button after activating an in-page link) focus is set to body.

	source : http://webaim.org/
	*/
	$(window).bind('hashchange', function() {
		var hash = "#"+window.location.hash.replace(/^#/,'');
		if (hash!="#") {
			$(hash).attr('tabindex', -1).on('blur focusout', function () {
				$(this).removeAttr('tabindex');
			}).focus();
		}
	});


	/** ====================== HEADER : POPINS ET DROPDOWN ============================
	  * Gestion de l'ouverture/fermeture des boutons principaux du header :
	  * - Menu burger
 	  * - Menu utilisateur
 	  * - Menu de recherche
 	  * On s'appuie sur les événements Bootstrap déclenchés par les data-toggle à l'ouverture et à la fermeture
 	  *
	  **/

	var $body = $('body'); // On commence par stocker l'objet jQuery pour le body

  // On attache un listener aux événements "show" et "hide" pour modal et "show pour dropdown" et on délégue au parent le plus proche
	// Note: Alors que les modales diffusent l'événement depuis la popin, les dropdown diffusent à partir du premier parent dans le DOM
	$body.on('show.bs.modal hide.bs.modal show.bs.dropdown', '#PopinMenu, #PopinRecherche, .header .dropdown', function(e) {

		 // On récupère l'objet jQuery pour la cible de l'événement déclenché
		var eventType = e.type, // Type d'événement "show" ou "hide" pour future référence
				$target = $(e.target), // La cible de l'événement (modal, dropdown)
				$modalOpened = $('.modal.in'); // Selection des modales actuellement ouvertes

		if (eventType == "show" && $modalOpened) {
				$modalOpened.modal("hide"); // On cache toute les autres
		}

		 // S'il s'agit d'un événement déclenché par l'ouverture / fermeture d'une modale
		if ($target.data('bs.modal')) {
			var	isMenu = $target.attr('id') == "PopinMenu", // On verifie si c'est l'ouverture du menu burger
					toggleTitle = eventType == "show" ? "Fermer le menu" : "Ouvrir le menu",
					modalBodyClass = isMenu ? "modal-menu-open" : "modal-search-open"; // On définie la classe à mettre/enlever sur le body

			$body.toggleClass(modalBodyClass);
			if (isMenu) { $(".btn-menu").attr('title', toggleTitle); }
		}
	});
});

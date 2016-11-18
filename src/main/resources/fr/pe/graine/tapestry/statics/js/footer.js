/*global jQuery:true, window:true, document:true, isMobile:true, animateScrollTo:true */
/*jslint node: true */

// Décommenter pendant les modifications pour tester le code en mode strict
// "use strict";";

jQuery(document).ready(function($){

	/* ================================= FOOTER COLLAPSE ======================================== */

	$('.mulicollapse-link').on('click', function (event) {
		event.preventDefault();
		$('.btn.mulicollapse-link').toggleClass('open');
		//gestion pour le webcallback
		$('.webcallback').addClass('anim').toggleClass('big');
		$("html, body").animate({ scrollTop: ($(document).height()-($('footer').height()+27)) }, 500);
	});

	/* ============================ CONTRAST MODE ================================= */

	var accessBtn = jQuery('.switch-access'),
			$body = $('body'),
			COOKIE_NAME = 'access-mode';

	var _cookie = function(name, value) {
			if (typeof value != 'undefined') {
				// Ajout d'un cookie
				var expires = '',
				 	path = '/';
				var host = location.hostname.replace("www", "");
				document.cookie = name+'='+encodeURIComponent((!value) ? '' : value)+expires+';domain=' + host + ';path='+path;
				return true;
			}
			else {
				// récuperation de la valeur du cookie
				var cookie,val = null;
				if (document.cookie && document.cookie !== '') {
					var cookies = document.cookie.split(';');
					var clen = cookies.length;
					for (var i = 0; i < clen; ++i) {
						cookie = jQuery.trim(cookies[i]);
						if (cookie.substring(0,name.length+1) == (name+'=')) {
							var len = name.length;
							val = decodeURIComponent(cookie.substring(len+1));
							break;
						}
					}
				}
				return val;
			}
		};

	// Gestion au document ready
	// recuperation du mode d'affichage et si cookie "access-mode" est true alors on active le mode accessibilité
	if( _cookie(COOKIE_NAME) == 'true' ){
		$body.addClass('accessibility');
	}

	// Gestion de l'évenement click sur le bouton d'activation
	accessBtn.click(function(){
		if(!$body.hasClass('accessibility')){
			$body.addClass('accessibility');
			_cookie(COOKIE_NAME, 'true');
		}else{
			$body.removeClass('accessibility');
			_cookie(COOKIE_NAME, 'false');
		}

		// La methode animateScrollTo() est chargée par main.js
		animateScrollTo(0, 400);
	});
});

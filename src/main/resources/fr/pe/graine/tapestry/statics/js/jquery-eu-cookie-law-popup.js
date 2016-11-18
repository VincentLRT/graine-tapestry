/**
 * BANNIERE CNIL COOKIES
 * Traduction et modification du code
 * Pierre ALBERT (GFX)
 *
 * //////////////////////////////////////
 *
 * BASÉ SUR "JQUERY EU COOKIE LAW POPUPS"
 * version 1.0.1
 * by Richard Dancsi
 * http://www.wimagguc.com/
 * 
 * Code on Github:
 * https://github.com/wimagguc/jquery-eu-cookie-law-popup
 * 
 * To see a live demo, go to:
 * http://www.wimagguc.com/2015/03/jquery-eu-cookie-law-popup/
 * 
 * 
 */

(function($) {

// Petit hack pour la gestion de la console "bugguer" sous <IE9 >>>
// if (!window.console) window.console = {};
// if (!window.console.log) window.console.log = function () { };

$.fn.euCookieLawPopup = (function() {

	var _self = this;

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Liste des paramètres (de base) /////////////////////////////////////////////////////////////
	// Ne pas modifier ici, mais lors de l'appel JS dans vos maquettes ////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////
	// paramètres des positions possible :
	// bottom, bottomleft, bottomright, top, fixedtop
	_self.params = {
		cookiePolicyUrl : 'http://www.pole-emploi.fr/informations/informations-legales-@/informations_legales/',
		popupPosition : 'bottomleft',
		colorStyle : 'default',
		compactStyle : false,
		popupTitle : '',
		popupText : 'Les cookies assurent le bon fonctionnement de nos services. En utilisant ces derniers, vous acceptez l\'utilisation des cookies.',
		buttonContinueTitle : 'J\'ai compris',
		buttonLearnmoreTitle : 'En savoir plus',
		buttonLearnmoreTitleAccess : 'En savoir plus sur l\'utilisation des cookies',
		buttonLearnmoreOpenInNewWindow : true,
		agreementExpiresInDays : 30,
		autoAcceptCookiePolicy : false,
		htmlMarkup : null
	};

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Variables utilisées dans les fonctions, NE PAS MODIFIER ////////////////////////////////////
	_self.vars = {
		INITIALISED : false,
		HTML_MARKUP : null,
		COOKIE_NAME : 'cnil'
	};

	///////////////////////////////////////////////////////////////////////////////////////////////
	// Méthode privé pour outrepasser les paramètres de base //////////////////////////////////////

	// Écraser les paramètres par défaut si besoin
	var parseParameters = function(markup, settings) {

		if (markup) {
			_self.params.htmlMarkup = markup;
		}

		if (settings) {
			if (typeof settings.cookiePolicyUrl !== 'undefined') {
				_self.params.cookiePolicyUrl = settings.cookiePolicyUrl;
			}
			if (typeof settings.popupPosition !== 'undefined') {
				_self.params.popupPosition = settings.popupPosition;
			}
			if (typeof settings.colorStyle !== 'undefined') {
				_self.params.colorStyle = settings.colorStyle;
			}
			if (typeof settings.popupTitle !== 'undefined') {
				_self.params.popupTitle = settings.popupTitle;
			}
			if (typeof settings.popupText !== 'undefined') {
				_self.params.popupText = settings.popupText;
			}
			if (typeof settings.buttonContinueTitle !== 'undefined') {
				_self.params.buttonContinueTitle = settings.buttonContinueTitle;
			}
			if (typeof settings.buttonLearnmoreTitle !== 'undefined') {
				_self.params.buttonLearnmoreTitle = settings.buttonLearnmoreTitle;
			}
			if (typeof settings.buttonLearnmoreOpenInNewWindow !== 'undefined') {
				_self.params.buttonLearnmoreOpenInNewWindow = settings.buttonLearnmoreOpenInNewWindow;
			}
			if (typeof settings.agreementExpiresInDays !== 'undefined') {
				_self.params.agreementExpiresInDays = settings.agreementExpiresInDays;
			}
			if (typeof settings.autoAcceptCookiePolicy !== 'undefined') {
				_self.params.autoAcceptCookiePolicy = settings.autoAcceptCookiePolicy;
			}
			if (typeof settings.htmlMarkup !== 'undefined') {
				_self.params.htmlMarkup = settings.htmlMarkup;
			}
		}

	};

	var createHtmlMarkup = function() {

		if (_self.params.htmlMarkup) {
			return _self.params.htmlMarkup;
		}

		var html = 
			'<div class="eupopup-container' + 
			    ' eupopup-container-' + _self.params.popupPosition + 
			    (_self.params.compactStyle ? ' eupopup-style-compact' : '') + 
				' eupopup-color-' + _self.params.colorStyle + '">' +
				'<div class="eupopup-head">' + _self.params.popupTitle + '</div>' +
				'<div class="eupopup-body">' + _self.params.popupText + '</div>' +
				'<div class="eupopup-buttons">' +
				  '<button type="button" class="btn-reset eupopup-button eupopup-button_1">' + _self.params.buttonContinueTitle + '</button>' +
				  '<a title="' + _self.params.buttonLearnmoreTitleAccess + '" href="' + _self.params.cookiePolicyUrl + '"' +
				 	(_self.params.buttonLearnmoreOpenInNewWindow ? ' target=_blank ' : '') +
					' class="eupopup-button eupopup-button_2">' + _self.params.buttonLearnmoreTitle + '</a>' +
				  '<div class="clearfix"></div>' +
				'</div>' +
				'<button type="button" class="eupopup-closebutton btn-reset"><span class="icon-close" aria-hidden="true"></span></button>' +
			'</div>';

		return html;
	};
	// Stocker le consentement dans un cookie
	var setUserAcceptsCookies = function(consent) {
		var d = new Date();
		var expiresInDays = _self.params.agreementExpiresInDays * 24 * 60 * 60 * 1000;
		d.setTime( d.getTime() + expiresInDays );
		var expires = "expires=" + d.toGMTString();
		var host = location.hostname.replace("www", "");
		document.cookie = _self.vars.COOKIE_NAME + '=' + consent + "; " + expires + ";domain=" + host + ";path=/";

		$(document).trigger("user_cookie_consent_changed", {'consent' : consent});
	};

	// Voyons voir si nous n'avons pas déjà un cookie de consentement
	var userAlreadyAcceptedCookies = function() {
		var userAcceptedCookies = false;
		var cookies = document.cookie.split(";");
		for (var i = 0; i < cookies.length; i++) {
			// var c = cookies[i].trim();
			var c = $.trim(cookies[i]);
			if (c.indexOf(_self.vars.COOKIE_NAME) == 0) {
				userAcceptedCookies = c.substring(_self.vars.COOKIE_NAME.length + 1, c.length);
			}
		}

		return userAcceptedCookies;
	};
	
	var hideContainer = function() {
		$('.eupopup-container').animate({
			opacity: 0,
			height: 0
		}, 200, function() {
			$('.eupopup-container').hide(0);
		});
	};

	///////////////////////////////////////////////////////////////////////////////////////////////
	// PUBLIC FUNCTIONS  //////////////////////////////////////////////////////////////////////////
	var publicfunc = {

		// INITIALISE EU COOKIE LAW POPUP /////////////////////////////////////////////////////////
		init : function(settings) {

			parseParameters(
				$(".eupopup-markup").html(),
				settings);

			// Pas besoin de'lafficher si l'utilisateur a déjà accepté
			if (userAlreadyAcceptedCookies()) {
				return;
			}

			// Initialiser qu'une seule fois
			if (_self.vars.INITIALISED) {
				return;
			}
			_self.vars.INITIALISED = true;

			// Markup and event listeners >>>
			_self.vars.HTML_MARKUP = createHtmlMarkup();

			if ($('.eupopup-block').length > 0) {
				$('.eupopup-block').append(_self.vars.HTML_MARKUP);
			} else {
				$('BODY').append(_self.vars.HTML_MARKUP);
			}

			$('.eupopup-button_1').click(function() {
				setUserAcceptsCookies(true);
				hideContainer();
				return false;
			});
			$('.eupopup-closebutton').click(function() {
				setUserAcceptsCookies(true);
				hideContainer();
				return false;
			});
			// Markup and event listeners

			// affichage
			$('.eupopup-container').show();

			if (_self.params.autoAcceptCookiePolicy) {
				setUserAcceptsCookies(true);
			}

		}

	};

	return publicfunc;
});

// $(document).bind("user_cookie_consent_changed", function(event, object) {
// 	console.log("User cookie consent changed: " + $(object).attr('consent') );
// });

}(jQuery));

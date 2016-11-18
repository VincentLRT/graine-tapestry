	/*global jQuery:true, window:true, document:true  */
	/*jslint node: true */

	// Décommenter pendant les modifications pour tester le code en mode strict
	// "use strict";

	/***************** Definition d'une serie de méthodes utilitaires réutilisables ********************/


	/* ================ Detection de l'utilisation d'un apareil mobile   ==================== */

	var isMobile = {
		Android: function () {
			return window.navigator.userAgent.match( /Android/i );
		},
		BlackBerry: function () {
			return window.navigator.userAgent.match( /BlackBerry/i );
		},
		iOS: function () {
			return window.navigator.userAgent.match( /iPhone|iPad|iPod/i );
		},
		Opera: function () {
			return window.navigator.userAgent.match( /Opera Mini/i );
		},
		Windows: function () {
			return window.navigator.userAgent.match( /IEMobile/i );
		},
		any: function () {
			return isMobile.Android() || isMobile.BlackBerry() || isMobile.iOS() || isMobile.Opera() || isMobile.Windows();
		}
	};

	/**
	 * A utiliser lorsqu'on veut s'assurer qu'un gestionnaire d'évenements ne se déclenche que lorsque l'évenement est terminé (scroll, resize, keydown)
	 * @method debounce
	 * @param  {Function} fn    Le gestionnaire d'évenement
	 * @param  {integer}   delay Le délai après lequel on considère que l'évenement est terminé
	 * @return {Function}  Le gestionnaire d'évenement temporisé.
	 */
	function debounce( fn, delay ) {
		var timer = null;
		return function () {
			var context = this,
				args = arguments;
			clearTimeout( timer );
			timer = setTimeout( function () {
				fn.apply( context, args );
			}, delay );
		};
	}

	/**
	 * Au utiliser quand on veut qu'un gestionnaire d'évenement soit déclenché tout les X secondes
	 * @method throttle
	 * @param  {Function} fn         Le gestionnaire d'évenement à appliquer
	 * @param  {Integer}  threshhold Le délai à respecter entre 2 déclenchement du gestionnaire d'évenement
	 * @param  {object}   scope      Le contexte du gestionnaire d'évenement ("this" par défaut)
	 * @return {Function}            Le gestionnaire d'évenement avec application du délai entre chaque déclenchement
	 */
	function throttle( fn, threshhold, scope ) {
		threshhold || ( threshhold = 250 );
		var last,
			deferTimer;
		return function () {
			var context = scope || this;

			var now = +new Date,
				args = arguments;
			if ( last && now < last + threshhold ) {
				// hold on to it
				clearTimeout( deferTimer );
				deferTimer = setTimeout( function () {
					last = now;
					fn.apply( context, args );
				}, threshhold );
			}
			else {
				last = now;
				fn.apply( context, args );
			}
		};
	}

	/* LISTENER SUR LES GROUPES RADIO POUR AFFICHER / MASQUER DES BLOCS */
	/* exemple d'utilisation :  $('[name="inscrit"]').changeRadioListener(); */


	jQuery.fn.changeRadioListener = function () {
		var id,
			$el,
			$current,
			$current_input;

		return this.each( function () {
			$el = jQuery( this );

			$el.change( function () {
				// On masque le contenu précédent
				if ( typeof $current !== 'undefined' ) {
					$current.addClass( 'hidden' ).removeClass( 'fadeIn' );
					$current_input.attr( 'aria-expanded', 'false' );
				}

				// On affiche le nouveau contenu
				jQuery( this ).attr( 'aria-expanded', 'true' );
				id = jQuery( this ).attr( 'data-target' );
				$current = jQuery( '#' + id );
				$current_input = jQuery( this );
				$current.removeClass( 'hidden' ).addClass( 'fadeIn' );
			} );
		} );
	};

	/* LISTENER SUR CHECKBOX POUR AFFICHER / MASQUER UN BLOC */
	/* exemple d'utilisation :  $('#nomdelacheckbox').changeCheckboxListener(); */


	jQuery.fn.changeCheckboxListener = function () {
		var id,
			$el;

		return this.each( function () {
			$el = jQuery( this );

			$el.change( function () {
				// On affiche le nouveau contenu
				var expanded = jQuery( this ).attr( 'aria-expanded' );
				jQuery( this ).attr( 'aria-expanded', !expanded );
				id = jQuery( this ).attr( 'data-target' );
				$current = id.split(" ");
				jQuery.each( $current, function( key, value ){
					jQuery( '#' + value ).toggleClass( 'hidden' ).toggleClass( 'fadeIn' );
				});
			} );
		} );
	};

	/* LISTENER SUR SELECT */
	/* exemple d'utilisation :  $('#nomduselect').changeSelectListener(); */

	jQuery.fn.changeSelectListener = function (selected) {
		var id,
			$el,
			$current,
			$current_input;

		return this.each( function () {
			$el = jQuery( this );
			if(selected){
				$current = new Array(selected);
				$current_input = jQuery( this );
			}
			$el.change( function () {
				// On masque le contenu précédent

				if ( typeof $current !== 'undefined' ) {
					$.each( $current, function( key, value ){
						jQuery( '#' + value ).addClass( 'hidden' ).removeClass( 'fadeIn' );
						jQuery( '#' + value ).attr( 'aria-expanded', 'false' );
					});
				}

				// On affiche le nouveau contenu
				var $option = jQuery( this ).find( ":selected" );
				$option.attr( 'aria-expanded', 'true' );
				id = $option.attr( 'data-target' );
				$current = id.split(" ");
				$current_input = jQuery( this );
				$.each( $current, function( key, value ){
					jQuery( '#' + value ).removeClass( 'hidden' ).addClass( 'fadeIn' );
				});
			} );
		} );
	};

	/* ================ Scroll animé  ==================== */

	/**
	 * Fonction pour scroller avec animation vers un élément de la page
	 * @var int scrollValue : la nouvelle position du scroll
	 *					accepte aussi la valeur "firstError" pour positionner le scroll au niveau de la première erreur
	 * @var timing int : la durée de l'animation
	 * @var selector string : le selecteur jQuery pour la racine du scroll. Si null, "html,body"
	 **/
	function animateScrollTo( scrollValue, timing, selector ) {
		
		var defaults = {
				scrollValue: 0,
				timing: 400,
				selector: 'html,body'
			},
			userOptions = {
				scrollValue: scrollValue,
				timing: timing,
				selector: selector
			},
			options = jQuery.extend( defaults, userOptions );

		// Demande Minisite : option pour scroller vers la première erreur
		if ( scrollValue == "firstError") {
			console.log('apply');
			// On récupére le selecteur pour déterminer la racine à utiliser pour le calcul
			var $rootSelector = options.selector == 'html,body' ? jQuery( 'body' ) : jQuery( selector );

			// Si on est dans le contexte d'une modal, la racine doit être l'élément ".modal-dialog", le container étant en overflow scroll
			if ( $rootSelector.hasClass( 'modal' ) ) {
				$rootSelector = $rootSelector.find( '.modal-dialog' );
			}
			// On peut enfin calculer la valeur de scroll en calculant la position de la première erreur.
			options.scrollValue =
				options.selector == 'html,body' ?
				$rootSelector.find( '.has-error' ).first().offset().top - 65 : // Pour un scroll depuis le body, on calcul l'offset normalement en prenant en compte le menu
				Math.abs( $rootSelector.offset().top - $rootSelector.find( '.has-error' ).first().offset().top ) - 5; // Pour une modal, on déduit l'offset à partir de la racine "fixe" et de l'élément en erreur
		}
		


		jQuery( options.selector ).animate( {
			scrollTop: options.scrollValue
		}, options.timing ); // The number here represents the speed of the scroll in milliseconds
	}


	/* ================ ADD LOADER  & LOADER BLOCKER==================== */

	function addLoader( position, withBlocker, sizeClass ) {
		if ( typeof position === "undefined" ) position = "";
		if ( typeof withBlocker === "undefined" ) withBlocker = true;
		if ( typeof sizeClass === "undefined" ) sizeClass = 'loader';

		if ( withBlocker ) jQuery( "body " ).prepend( "<div class='loader-blocker'></div>" );
		jQuery( "body " + position ).prepend( "<div class='" + sizeClass + "'><svg class='circular' viewBox='25 25 50 50'><circle class='path' cx='50' cy='50' r='20' fill='none' stroke-width='2' stroke-miterlimit='10'/></svg></div>" );
	}

	function removeLoader() {
		jQuery( ".loader, .loader-blocker" ).remove();
	}


	/***************** Definition d'une serie de méthodes Initialisable une fois au DOM READY ********************/

	/* ================ TOGGLE TOOLTIP LABEL  ==================== */

	// Methode permettant de permuter le contenu des tooltips associés à des boutons ayant un aspect activé / désactivé
	function toggleTooltipLabel() {
		// La methode surveille le clic sur les boutons ayant un toggle
		jQuery( document ).on( 'click', '[data-toggle="button"]', function () {
			// On initialise les variable nécessaire à la fonction
			var $this = jQuery( this ); // Pour simplifier l'écriture on stocker l'objet jQuery correspondant à this

			// Avant de lancer le tout, on vérifie si le bouton déclenche un tooltip ou non.
			if ( $this.is( '[data-tooltip]' ) ) {
				var $tooltipInner, label;
				$tooltipInner = $this.next().find( '.tooltip-inner' ); // On stocke aussi l'objet jQuery correspondant au contenu du tooltip

				// Le composant "button" de Boostrap change l'attribut aria-pressed selon l'état donc on peut s'appuyer desssus
				if ( $this.attr( 'aria-pressed' ) == "true" ) { // Par contre on récupère un string, pas un booleen, donc on verifie la valeur de la string
					label = $this.data( 'pressedTitle' ); // Si le bouton est pressé, le texte à utilisé est stocké
				}
				else {
					label = $this.data( 'unpressedTitle' ); // Sinon le texte pour l'état non pressé est stocké
				}
				$tooltipInner.text( label ); // Dans tous les cas on change immédiatement le texte du tooltip
				$this.attr( 'data-original-title', label ); // Et on switch le data-original-title sur lequel s'appuie Boostrap pour construire le tooltip
			}
			else {
				return;
			}
		} );
	}

	// Methode qui permet de supprimer les tooltips sur les boutons de toggle quand ils ne sont plus survolés par la souris.
	function clearTooltip() {
		var $buttons = jQuery( '[data-toggle="button"]' );
		if ( $buttons.length > 0 && $buttons.is( '[data-tooltip]' ) ) {
			jQuery( document ).on( "mouseleave", '[data-toggle="button"]', function () {
				jQuery( this ).tooltip( 'hide' );
			} );
		}
	}

	/* ================ INPUT WIDTH ON TAG INPUT GROUP  ==================== */

	/* Methode qui permet d'ajuster la largeur de l'input quand un nouveau tag est ajouté ou retiré */
	function initTagInputWidthAutoAdjust() {
		// On essaie de récuperer les tagInputGroup de la page en cours
		var $tagInputGroups = jQuery( ".tag-input-group" ); // Si y'en a, ils sont stockés pour plus tard.

		// Avant de faire quoique ce soit d'inutile, on teste l'existence des tagInputGroup
		if ( $tagInputGroups.length > 0 ) {
			var selectizeBool =  $tagInputGroups.hasClass('selectize-control');
			var tapestryBool =  $tagInputGroups.hasClass('form-control');
			
			var inputMinWidth = 40, // On détermine la taille minimale des input text.
				onEvents = "tag.init tag.removed tag.added", // On stocke le nom des évenements que l'on veut surveiller
				firstTags = [];

			// On stock sur chaque élément du DOM l'id du groupe pour future référence
			$tagInputGroups.each( function () {
				if(tapestryBool) jQuery( this ).data( 'groupId', jQuery( this ).parent().find( '.t-autocomplete-menu' ).first().attr( 'id' ) );
				if(selectizeBool) jQuery( this ).find( '.selectize-input' ).data( 'groupId', jQuery( this ).parent().find( '.selectize-dropdown' ).first().attr( 'id' ) );
				
				firstTags.push( jQuery( this ).find( '.tag' ).first() );
			} );

			// On délégue au document la charge de surveille les événements déclenchés par des éléments avec la classe tag-input-group.
			jQuery( document ).on( onEvents, ".tag-input-group .tag", function ( e ) {
				
				var $thisTagGroup; 
				var	eventGroupId;
				
				if(tapestryBool){
					$thisTagGroup = jQuery( this ).parents( '.tag-input-group' ); // on stock la référence à l'objet jQuery pour le tagGroup actuel
					eventGroupId = jQuery( e.target ).parents( '.tag-input-wrapper' ).find( '.t-autocomplete-menu' ).first().attr( 'id' );
				}
				if(selectizeBool){
					$thisTagGroup = jQuery( this ).parents( '.selectize-input' ); // on stock la référence à l'objet jQuery pour le tagGroup actuel
					eventGroupId = jQuery( e.target ).parents( '.tag-input-wrapper' ).find( '.selectize-dropdown' ).first().attr( 'id' );
				}
				
				// Maintenant on vérifie si le tag a été ajouté au groupe actuel.
				if ( eventGroupId == $thisTagGroup.data( 'groupId' ) ) {
					var $tagInput = $thisTagGroup.find( 'input[type="text"]' ), // Si oui, on repère l'input a redimensionner
						lineWidth = $thisTagGroup.width(),
						lastLineWidth = 0;

					// Pour ce qui est du calcul, il faut prendre la largeur et retirer le padding-left.

					// On boucle sur les tags présents pour calculer l'espace déjà utilisé.
					var $tags = $thisTagGroup.find( '.tag' ),
						css_max_width;
						
					switch ( $tags.length ) {
					case 1:
						css_max_width = {
							'max-width': '80%'
						};
						break;
					case 2:
						css_max_width = {
							'max-width': '40%'
						};
						break;
					case 3:
						css_max_width = {
							'max-width': '26%'
						};
						break;
					case 4:
						css_max_width = {
							'max-width': '48%'
						};
						break;
					default:
						css_max_width = {
							'max-width': '48%'
						};
						break;
					}

					$thisTagGroup.find( '.tag' ).each( function ( index ) {
						jQuery( this ).css( css_max_width );
						// On ajoute un marge d'erreur de 2px par tag pour compenser les arrondis de jQuery pour leur dimension.
						var thisTagWidth = jQuery( this ).outerWidth( true ) + 2;
						// Si la somme du total des largeurs est supérieur à la largeur max d'une ligne, alors, on a une nouvelle ligne
						// Dans ce cas on retient la largeur de la ligne courante, elle servira de reférence pour savoir la largeur disponible restante.
						lastLineWidth = lastLineWidth + thisTagWidth > lineWidth ? thisTagWidth : lastLineWidth + thisTagWidth;
					} );

					var currentLineAvailableWidth = lineWidth - lastLineWidth;
					if ( currentLineAvailableWidth >= inputMinWidth ) {
						// On applique la nouvelle largeur forcée au parent de l'input en retirant la marge droite.
						$tagInput.parent( 'li' ).css( {
							width: currentLineAvailableWidth,
							marginRight: 0
						} );
					}
					else {
						// Sinon, on retire l'attribut "style" pour repasser la largeur en auto
						$tagInput.parent( 'li' ).css( {
							width: lineWidth,
							marginRight: 0
						} );
					}
				}
			} );

			// On utilise la liste des tags initiée un peu plus tôt pour initialiser chaque champ d'autocompletion
			jQuery( firstTags ).each( function () {
				jQuery( this ).trigger( 'tag.init' );
			} );
		}
	}

	jQuery( document ).ready( function ( $ ) {

		/* ============================ LOADING BTN ================================= */

		$('[data-loading]').on('click', function () {
			// Activation de l'état loading du bouton :
			// Cela ajoute une class disabled, l'attribut disable
			// et remplace le contenu du bouton par ce qui est present dans l'attribut data-loading-text
			var $btn = $(this).button('loading');

			// permet de fermer le tooltip pour eviter divers bugs
			$(this).tooltip('hide');

			// Réinitialiser état du bouton - retour etat original.
			// La méthode "$btn.button('reset')" remet le bouton dans son état d'origine
		});


		/* ================ NAVIGATEUR OBSOLETE ==================== */
		//!TODO: Verifier que jquery.reject.js" est chargé dans tous les pn
		try {
			$.reject( {
				reject: {
					msie: 7,
					firefox: 16
				},
				closeCookie: true
			} );
		}
		catch ( e ) {
			//console.log( 'Attention : Le plugin "jquery.reject.js" n\'est pas chargé, il doit ajouter à la liste des JS de base au même titre que jquery et bootstrap' );
		}

		/* ================ Windows Phone 8 Viewport Fix  ==================== */
		if ( navigator.userAgent.match( /IEMobile\/10\.0/ ) ) {
			var msViewportStyle = document.createElement( "style" );
			msViewportStyle.appendChild(
				document.createTextNode(
					"@-ms-viewport{width:auto!important}"
				)
			);
			document.getElementsByTagName( "head" )[ 0 ].
			appendChild( msViewportStyle );
		}

		/* ================ Gestion Focus personnalisé pour le module d'autocomplete ==================== */
		$( '.search-input-group input' ).on( 'focus', function () {
			$( this ).parents( 'ul' ).addClass( 'focus' );
		} ).on( 'blur', function () {
			$( this ).parents( 'ul' ).removeClass( 'focus' );
		} );

		/* ================ TOOLTIP (si l'appareil n'est pas tactile)  ==================== */
		if ( !isMobile.any() ) {
			$( '[data-tooltip]' ).tooltip().removeAttr( 'title' );
		}

		/* ================ Gestion générique si panel-left ou panel-right dans la page  ==================== */

		$( '.btn-panel-left' ).click( function ( event ) {
			$( 'body' ).toggleClass( 'panel-left-visible' );
			$( this ).attr( 'aria-expanded', !$.parseJSON( $( this ).attr( 'aria-expanded' ) ) );
		} );

		$( '.btn-panel-right' ).click( function ( event ) {
			$( 'body' ).toggleClass( 'panel-right-visible' );
			$( this ).attr( 'aria-expanded', !$.parseJSON( $( this ).attr( 'aria-expanded' ) ) );
		} );

		/* ============================ RETOUR HAUT DE PAGE ================================= */

		// TODO: Rajouter ce bouton en dur dans les layout
		//$('body').append('<button type="button" class="btn btn-default btn-icon-only sr-only link-top"><i class="icon-chevron-up" aria-hidden="true"></i><span class="sr-only">Remonter en haut de page</span></button>');

		var linkTop = $( '.link-top' ),
			lastScrollTop = 0;
		$( window ).scroll( function () {
			if ( linkTop.length > 0 && $( window ).scrollTop() >= 200 ) {
				var st = $( this ).scrollTop();
				if ( st < lastScrollTop ) {
					linkTop.removeClass( 'sr-only' );
				}
				else {
					linkTop.addClass( 'sr-only' );
				}
				lastScrollTop = st;
			}
			else {
				linkTop.addClass( 'sr-only' );
			}
		} );

		$( document ).on( 'click', '.link-top', function ( e ) {
			e.preventDefault();
			animateScrollTo( 0, 700 );
			$( this ).blur();
		} );


		/* ============================ SCROLL FLUIDE VERS ANCRE ================================= */

		//Cible tous les href
		var anchorScrollSelectors = '.heading [href^="#"], .sommaire [href^="#"], .steps [href^="#"], .block-icons [href^="#"], .recherche-cv .block-article .btn-primary, .with-scroll, .sub-nav [href^="#"]';
		$( document ).on( 'click', anchorScrollSelectors, function ( e ) {

			var heightHeader = $('header').height();

			if ( $('.sub-nav').length && $('.sub-nav').css('position') == 'fixed' ){
				var heightSubNav =  $('.sub-nav').height() + 39;
				var heightHeader = heightHeader + heightSubNav;
				//console.log( ' heightHeader : ' +$('header').height() + ' + heightSubNav : ' + heightSubNav);
			}

			if ( window.location.pathname.replace( /^\//, '' ) == this.pathname.replace( /^\//, '' ) && window.location.hostname == this.hostname && this.hash !== "" ) {
				var target = jQuery( this.hash );
				target = target.length ? target : jQuery( '[name=' + this.hash.slice( 1 ) + ']' );

				if ( target.length ) {
					e.preventDefault();
					animateScrollTo( target.offset().top - heightHeader, 700 );
				}
			}
		} );

		/* ============================ AFFICHAGE GESTION BANNIERE CNIL COOKIE ================================= */

		setTimeout( function () {
			try {
				//!TODO: Rajouter jquery-eu-cookie-law-popup.js dans tous les pn
				$( document ).euCookieLawPopup().init( {
					popupPosition: 'bottomleft-mobilebottom',
					agreementExpiresInDays: 365
				} );
			}
			catch ( e ) {
				//console.log( 'Attention : Le plugin "jquery-eu-cookie-law-popup.js" n\'est pas chargé, il doit ajouter à la liste des JS de base au même titre que jquery et bootstrap' );
			}
		}, 400 );

		// On peut maintenant initialiser les comportements
		toggleTooltipLabel();
		clearTooltip();
		initTagInputWidthAutoAdjust();

		// Au resize on recacule la largeur de l'input pour la recherche par tag
		var resizeTimer;
		$( window ).resize( function () {
			clearTimeout( resizeTimer );
			setTimeout( initTagInputWidthAutoAdjust, 1000 );
		} );

		// Faute d'avoir développé une solution côté Tapestry, on retire en JS  les error icons du DOM
		var tErrorIcons = jQuery( '.t-error-icon' );
		if ( tErrorIcons.length ) {
			tErrorIcons.remove();
		}

		// Remplacement des input de type text en type number sur mobile pour faire apparaître par défaut le clavier numérique sur les champs ciblés
		if ( isMobile.any() ) {
			$( '[data-type="number"]' ).attr( 'type', "number" );
		}
		
		/* ============================ CHECKED-BLOCK  ================================= */
		
		$( '.checked-block input' ).click( function ( event ) {
			var n = $(this).attr('name');
			var b = $(this).parents('.checked-block');
			
			$('[name='+n+']').parents('.checked-block').removeClass('checked');
			if(b.hasClass('checked'))  b.removeClass('checked'); else b.addClass('checked');
		} );
		

		// ------------------------
		// ------ WEBCALLBACK -----
		// ------------------------
		//
		if ( $( "[data-webcallback]" ).length ) {

			// Chargement de la fonction loader
			addLoader( '.webcallbackLoader', false );

			var $webcallback = $( '[data-webcallback]' ),
					$webcallbackMobile = $( '[data-webcallback-mobile]' ),
					$webcallbackModal = $( $webcallbackMobile.find('.head button').data('target') );


			// On construit le comportement pour chaque bloc de webcallback de la page
			$webcallback.each( function () {

				// On initialise les variable communes
				var timerWebcallback,
					$thisModule = $( this ), // On stock la référence à l'objet en cours
					initDelay = $thisModule.data( 'webcallback-timer' ) ? parseInt( $thisModule.data( 'webcallback-timer' ) ) : 2000; // On détermine le delai d'initialisation

				// Au bout d'un certain nombre de seconde $initDelay on masque completement le bloc
				timerWebcallback = setTimeout( function () {
					$thisModule.find( '.contenu' ).collapse( 'toggle' );
				}, initDelay );

				// Si avant la fin du timer on clique sur le header..
				$thisModule.on( 'click', '.head button', function ( event ) {
					event.stopPropagation(); // On arrête la propagation de l'événement
					clearTimeout( timerWebcallback ); // On arrête le time
					$thisModule.find( '.contenu' ).collapse( 'toggle' ); // Et on montre le bloc en totalité
				} );

			} );

			// Gestion de l'aspect responsive au resize
			// On utilise debounce pour déclencher le gestionnaire d'évenement à la fin du resize
			$( window ).on( 'resize', debounce(function(event) {

				// Si on est pas sur mobile
				if ($(this).width() > 767) {
					$webcallbackMobile.hide(); // On cache le bouton de la modale
					if ($webcallbackModal.is(':visible')) { // Si la modal du WBC est visible
						$webcallbackModal.modal('hide'); // On la cache
						$webcallback.find('.contenu').collapse('show'); // On montre son équivalent desktop
					}
					else {
						$webcallback.find('.contenu').collapse('hide'); // Sinon on cache synchronise l'état côté desktop
					}
				}
				else { // Sinon on est tsur mobile
					$(this).trigger('scroll'); // On déclenche un scroll pour déterminer s'il faut afficher ou pas le bouton mobile
					if ($webcallback.find('.contenu').hasClass("in") && !($webcallback.find('.init-block').hasClass("init"))) { // Si la version desktop est déplié
						$webcallbackModal.modal('show'); // On montre l'équivalent mobile
					}
				}
			}, 500)); // On détermine un délai entre chaque déclenchement

			// Quand on scroll sur la page
			// [Performances] On temporise l'évenement scroll pour le traiter une fois toutes les 100ms
			$( window ).on( 'scroll', throttle(
				function (event) {
					var scroll_top = $( document ).scrollTop(); // Position verticale de la scrollbar

					// Soit on est sur dekstop (le module est visible)
					if ( $webcallback.is( ':visible' ) ) {
						var window_h = $( window ).height(),
							footer_offset = $( 'footer' ).length ? $( 'footer' ).offset().top : false,
							footerHeight = $('footer').outerHeight();

						// Si on est en bas de page, on fixe le bloc au top du footer
						if ( footer_offset !== false && scroll_top + window_h >= footer_offset ) {
							$webcallback.addClass( 'bottom' );
							$webcallback.css( 'bottom', footerHeight+'px' );
						}
						// Sinon on retire la class qui fixe le bloc
						else {
							$webcallback.removeClass( 'bottom' );
							$webcallback.css( 'bottom' , '' );
						}
					}

					// Si a un module mobile chargé
					else if ( $webcallbackMobile.length ) {
						// On vérifie si le bloc qui sert de repère pour afficher et masquer le webcallback-mobile est présent et visible
						var conseiller_offset =
							$( '.conseiller-entreprise' ).length && $( '.conseiller-entreprise' ).is( ':visible' ) ? $( '.conseiller-entreprise' ).offset().top : false;

						// S'il est présent et visible..
						if ( conseiller_offset !== false ) {
							// S'il est hors de la zone visible actuellement
							if ( scroll_top >= conseiller_offset ) {
								$webcallbackMobile.show().addClass( 'visible' ); // On montre le webcallbackMobile
							}
							// Sinon
							else {
								$webcallbackMobile.hide().removeClass( 'visible' ); // On cache le webcallbackMobile
							}
						}
					}

				}, 150 ) );
		}
	} );

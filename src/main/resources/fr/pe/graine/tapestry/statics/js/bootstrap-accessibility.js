/*global jQuery:true, window:true, document:true */
/*jslint node: true */

/* ========================================================================
 * Extends Bootstrap v3.1.1
 * Copyright (c) <2015> PayPal
 * All rights reserved.
 * Neither the name of PayPal or any of its subsidiaries or affiliates nor the names of its contributors may be used to endorse or promote products derived from this software without specific prior written permission.
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * ======================================================================== */

 // Décommenter pendant les modifications pour tester le code en mode strict
 // "use strict";

jQuery(document).ready(function ($) {
    // GENERAL UTILITY FUNCTIONS
    // ===============================

    var uniqueId = function(prefix) {
        return (prefix || 'ui-id') + '-' + Math.floor((Math.random() * 1000) + 1);
    };


    var removeMultiValAttributes = function(el, attr, val) {
        var describedby = (el.attr(attr) || "").split(/\s+/),
            index = $.inArray(val, describedby);
        if (index !== -1) {
            describedby.splice(index, 1);
        }

        describedby = $.trim(describedby.join(" "));

        if (describedby) {
            el.attr(attr, describedby);
        } else {
            el.removeAttr(attr);
        }
    };

    // selectors  Courtesy: https://github.com/jquery/jquery-ui/blob/master/ui/core.js
    var focusable = function(element, isTabIndexNotNaN) {
            var map, mapName, img,
                nodeName = element.nodeName.toLowerCase();

            if ("area" === nodeName) {
                map = element.parentNode;
                mapName = map.name;
                if (!element.href || !mapName || map.nodeName.toLowerCase() !== "map") {
                    return false;
                }

                img = $("img[usemap='#" + mapName + "']")[0];
                return !!img && visible(img);
            }

            return (/input|select|textarea|button|object/.test(nodeName) ?
                !element.disabled :
                "a" === nodeName ?
                element.href || isTabIndexNotNaN : isTabIndexNotNaN) && visible(element); // the element and all of its ancestors must be visible
        },
        visible = function(element) {
            return $.expr.filters.visible(element) &&
                !$(element).parents().addBack().filter(function() {
                    return $.css(this, "visibility") === "hidden";
                }).length;
        };

    $.extend($.expr[":"], {
        data: $.expr.createPseudo ?
            $.expr.createPseudo(function(dataName) {
                return function(elem) {
                    return !!$.data(elem, dataName);
                };
            }) :
            // support: jQuery <1.8
            function(elem, i, match) {
                return !!$.data(elem, match[3]);
            },

        focusable: function(element) {
            return focusable(element, !isNaN($.attr(element, "tabindex")));
        },

        tabbable: function(element) {
            var tabIndex = $.attr(element, "tabindex"),
                isTabIndexNaN = isNaN(tabIndex);
            return (isTabIndexNaN || tabIndex >= 0) && focusable(element, !isTabIndexNaN);
        }
    });

    // Modal Extension
    // ===============================

    $('.modal-dialog').attr({
        'role': 'document'
    });
    var modalhide = $.fn.modal.Constructor.prototype.hide;
    $.fn.modal.Constructor.prototype.hide = function() {
        modalhide.apply(this, arguments);
        $(document).off('keydown.bs.modal');
    };

    var modalfocus = $.fn.modal.Constructor.prototype.enforceFocus;
    $.fn.modal.Constructor.prototype.enforceFocus = function() {
        var focEls = this.$element.find(":tabbable"),
            lastEl = focEls[focEls.length - 1];
        $(document).on('keydown.bs.modal', $.proxy(function(ev) {
            if (!this.$element.has(ev.target).length && ev.shiftKey && ev.keyCode === 9) {
                lastEl.focus();
                ev.preventDefault();
            }
        }, this));

        modalfocus.apply(this, arguments);
    };

    // Tab Extension
    // ===============================

    var $tablist = jQuery('.nav-tabs, .nav-pills'),
        $lis = $tablist.children('li'),
        $tabs = $tablist.find('[data-toggle="tab"], [data-toggle="pill"]');

    if ($tabs) {
        $tablist.attr('role', 'tablist');
        // $lis.attr('role', 'presentation');
        $tabs.attr('role', 'tab');
    }

    $tabs.each(function(index) {

        var tab = jQuery(this),
            tabpanel = jQuery(tab.attr('href')),
            tabid = tab.attr('id') || uniqueId('ui-tab');

        tab.attr('id', tabid);

        if (tab.parent().hasClass('active')) {
            tab.attr({
                'tabIndex': '0',
                'aria-selected': 'true',
                'aria-controls': tab.attr('href').substr(1)
            });
            tabpanel.attr({
                'role': 'tabpanel',
                'tabIndex': '0',
                'aria-hidden': 'false',
                'aria-labelledby': tabid
            });
        } else {
            tab.attr({
                'tabIndex': '-1',
                'aria-selected': 'false',
                'aria-controls': tab.attr('href').substr(1)
            });
            tabpanel.attr({
                'role': 'tabpanel',
                'tabIndex': '-1',
                'aria-hidden': 'true',
                'aria-labelledby': tabid
            });
        }
    });

    jQuery.fn.tab.Constructor.prototype.keydown = function(e) {
        var $this = jQuery(this),
            $items,
            $ul = $this.closest('ul[role=tablist] '),
            index,
            k = e.which || e.keyCode;

        if (!/(37|38|39|40)/.test(k)) return;

        $items = $ul.find('[role=tab]:visible');
        index = $items.index($items.filter(':focus'));

        if (k == 38 || k == 37) index--; // up & left
        if (k == 39 || k == 40) index++; // down & right


        if (index < 0) index = $items.length - 1;
        if (index == $items.length) index = 0;

        var nextTab = $items.eq(index);
        if (nextTab.attr('role') === 'tab') {

            nextTab.tab('show') //Comment this line for dynamically loaded tabPabels, to save Ajax requests on arrow key navigation
                .focus();
        }
        // nextTab.focus()

        e.preventDefault();
        e.stopPropagation();
    };

    jQuery(document).on('keydown.tab.data-api', '[data-toggle="tab"], [data-toggle="pill"]', jQuery.fn.tab.Constructor.prototype.keydown);

    var tabactivate = jQuery.fn.tab.Constructor.prototype.activate;
    jQuery.fn.tab.Constructor.prototype.activate = function(element, container, callback) {
        var $active = container.find('> .active');
        $active.find('[data-toggle=tab], [data-toggle=pill]').attr({
            'tabIndex': '-1',
            'aria-selected': false
        });
        $active.filter('.tab-pane').attr({
            'aria-hidden': true,
            'tabIndex': '-1'
        });

        tabactivate.apply(this, arguments);

        element.addClass('active');
        element.find('[data-toggle=tab], [data-toggle=pill]').attr({
            'tabIndex': '0',
            'aria-selected': true
        });
        element.filter('.tab-pane').attr({
            'aria-hidden': false,
            'tabIndex': '0'
        });
    };

    // DROPDOWN Extension
    // ===============================

    var toggle = '[data-toggle=dropdown]',
        $par,
        firstItem,
        focusDelay = 200,
        menus = jQuery(toggle).parent().find('ul').attr('role', 'menu'),
        // lis = menus.find('li').attr('role', 'presentation');
        lis = menus.find('li');

    // add menuitem role and tabIndex to dropdown links
    lis.find('a').attr({
        'role': 'menuitem',
        'tabIndex': '-1'
    });
    // add aria attributes to dropdown toggle
    jQuery(toggle).attr({
        'aria-haspopup': 'true',
        'aria-expanded': 'false'
    });

    jQuery(toggle).parent()
        // Update aria-expanded when open
        .on('shown.bs.dropdown', function(e) {
            $par = jQuery(this);
            var $toggle = $par.find(toggle);
            $toggle.attr('aria-expanded', 'true');
            $toggle.on('keydown.bs.dropdown', jQuery.proxy(function(ev) {
                setTimeout(function() {
                    firstItem = jQuery('.dropdown-menu [role=menuitem]:visible', $par)[0];
                    try {
                        firstItem.focus();
                    } catch (ex) {}
                }, focusDelay);
            }, this));

        })
        // Update aria-expanded when closed
        .on('hidden.bs.dropdown', function(e) {
            $par = jQuery(this);
            var $toggle = $par.find(toggle);
            $toggle.attr('aria-expanded', 'false');
        });

    // Close the dropdown if tabbed away from
    jQuery(document)
        .on('focusout.dropdown.data-api', '.dropdown-menu', function(e) {
            var $this = jQuery(this),
                that = this;
            // since we're trying to close when appropriate,
            // make sure the dropdown is open
            if (!$this.parent().hasClass('open')) {
                return;
            }
            setTimeout(function() {
                if (!jQuery.contains(that, document.activeElement)) {
					if(!jQuery('.modal-open').length)
						$this.parent().find('[data-toggle=dropdown]').dropdown('toggle');
                }
            }, 150);
        })
        .on('keydown.bs.dropdown.data-api', toggle + ', [role=menu]', jQuery.fn.dropdown.Constructor.prototype.keydown);

    // Collapse Extension
    // ===============================

    var $colltabs = $('[data-toggle="collapse"]');
    $colltabs.each(function(index) {
        var colltab = $(this),
            collpanel = (colltab.attr('data-target')) ? $(colltab.attr('data-target')) : $(colltab.attr('href')),
            parent = colltab.attr('data-parent'),
            collparent = parent && $(parent),
            collid = colltab.attr('id') || uniqueId('ui-collapse');

        colltab.attr('id', collid);

        if (collparent) {
            colltab.attr({
                'role': 'tab',
                'aria-selected': 'false',
                'aria-expanded': 'false'
            });
            // $(collparent).find('div:not(.collapse,.panel-body), h4').attr('role', 'presentation');
            collparent.attr({
                'role': 'tablist',
                'aria-multiselectable': 'true'
            });

            if (collpanel.hasClass('in')) {
                colltab.attr({
                    'aria-controls': collpanel.attr('id'),
                    'aria-selected': 'true',
                    'aria-expanded': 'true',
                    'tabindex': '0'
                });
                collpanel.attr({
                    'role': 'tabpanel',
                    'tabindex': '0',
                    'aria-labelledby': collid,
                    'aria-hidden': 'false'
                });
            } else {
                colltab.attr({
                    'aria-controls': collpanel.attr('id'),
                    'tabindex': '-1'
                });
                collpanel.attr({
                    'role': 'tabpanel',
                    'tabindex': '-1',
                    'aria-labelledby': collid,
                    'aria-hidden': 'true'
                });
            }
        }else{
    // Add by pierre ALBERT GFX
    // ajout etat de base du aria-hidden...
            if (colltab.attr('aria-expanded') === 'false') {
                collpanel.attr({
                    'aria-hidden': 'true'
                });
            }
        }
    //... + gestion lors de l'ouverture
        colltab.on('click', function() {
          var state = $(this).attr('aria-expanded') === 'false' ? true : false;
          collpanel.attr('aria-hidden', !state);
        });

    });

    var collToggle = $.fn.collapse.Constructor.prototype.toggle;
    $.fn.collapse.Constructor.prototype.toggle = function() {
        var prevTab = this.$parent && this.$parent.find('[aria-expanded="true"]'),
            href;

        if (prevTab) {
            var prevPanel = prevTab.attr('data-target') || (href = prevTab.attr('href')) && href.replace(/.*(?=#[^\s]+$)/, ''),
                $prevPanel = $(prevPanel),
                $curPanel = this.$element,
                par = this.$parent,
                curTab;

            if (this.$parent) curTab = this.$parent.find('[data-toggle=collapse][href="#' + this.$element.attr('id') + '"]');

            collToggle.apply(this, arguments);

            if ($.support.transition) {
                this.$element.one($.support.transition.end, function() {

                    prevTab.attr({
                        'aria-selected': 'false',
                        'aria-expanded': 'false',
                        'tabIndex': '-1'
                    });
                    $prevPanel.attr({
                        'aria-hidden': 'true',
                        'tabIndex': '-1'
                    });

                    curTab.attr({
                        'aria-selected': 'true',
                        'aria-expanded': 'true',
                        'tabIndex': '0'
                    });

                    if ($curPanel.hasClass('in')) {
                        $curPanel.attr({
                            'aria-hidden': 'false',
                            'tabIndex': '0'
                        });
                    } else {
                        curTab.attr({
                            'aria-selected': 'false',
                            'aria-expanded': 'false'
                        });
                        $curPanel.attr({
                            'aria-hidden': 'true',
                            'tabIndex': '-1'
                        });
                    }
                });
            }
        } else {
            collToggle.apply(this, arguments);
        }
    };

    $.fn.collapse.Constructor.prototype.keydown = function(e) {
        var $this = $(this),
            $items,
            $tablist = $this.closest('div[role=tablist] '),
            index,
            k = e.which || e.keyCode;

        if (!/(32|37|38|39|40)/.test(k)) return;
        if (k == 32) $this.click();

        $items = $tablist.find('[role=tab]');
        index = $items.index($items.filter(':focus'));

        if (k == 38 || k == 37) index--; // up & left
        if (k == 39 || k == 40) index++; // down & right
        if (index < 0) index = $items.length - 1;
        if (index == $items.length) index = 0;

        $items.eq(index).focus();

        e.preventDefault();
        e.stopPropagation();

    };

    $(document).on('keydown.collapse.data-api', '[data-toggle="collapse"]', $.fn.collapse.Constructor.prototype.keydown);

});

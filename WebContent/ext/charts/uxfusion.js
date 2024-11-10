/* global Ext */
/*
 * @class Ext.ux.Chart.Fusion
 * Version:  2.1
 * Author: Doug Hendricks. doug[always-At]theactivegroup.com
 * Copyright 2007-2009, Active Group, Inc.  All rights reserved.
 *
 ************************************************************************************
 *   This file is distributed on an AS IS BASIS WITHOUT ANY WARRANTY;
 *   without even the implied warranty of MERCHANTABILITY or
 *   FITNESS FOR A PARTICULAR PURPOSE.
 ************************************************************************************

     License: Ext.ux.Chart.Fusion is licensed under the
     terms of : GNU Open Source GPL 3.0 license:

     This program is free software for non-commercial use: you can redistribute
     it and/or modify it under the terms of the GNU General Public License as
     published by the Free Software Foundation, either version 3 of the License, or
     any later version.

     This program is distributed in the hope that it will be useful,
     but WITHOUT ANY WARRANTY; without even the implied warranty of
     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
     GNU General Public License for more details.

     You should have received a copy of the GNU General Public License
     along with this program.  If not, see < http://www.gnu.org/licenses/gpl.html>.

   Donations are welcomed: http://donate.theactivegroup.com
   Commercial use is prohibited without a Commercial License. See http://licensing.theactivegroup.com.

 Version:  2.1

 Fusion Chart wrapper support for Fusion Free, 3.0, 3.1

 Component Config Options:

   chartUrl    : the URL of the desired Fusion_Chart_Object.swf
   dataXML     : String  XML stream containing chart layout config and data series.
   dataURL     : the URL of a remote dataXML resource
   loadMask    : loadMask config, true, or false. applied during data load operations.
   mediaMask   : loadMask config, true, or false. applied while the SWF object is loading( not the data)

   fusionCfg  : {  //optional
            id    : String   id of <object> tag
            style : Obj  optional DomHelper style object
            params: {

                flashVars : {
                    chartWidth  : defaults to SWF Object geometry
                    chartHeight : defaults to SWF Object geometry
                    debugMode   : Fusion debug mode (0,1)
                    DOMId       : DOM Id of SWF object (defaults to assigned macro '@id')
                    registerWithJS: Fusion specific (0,1)
                    scaleMode   : 'noScale',
                    lang        : default 'EN',
                    dataXML     : An XML string representing the chart canvas config and data series
                    dataUrl     : A Url to load an XML resource (dataXML)
                }
            }
        }

 This class inherits from (thus requires) the ux.Media(uxmedia.js) and ux.Media.Flash (uxflash.js) classes

*/
(function(){
    Ext.namespace("Ext.ux.Chart.Fusion");

    var chart = Ext.ux.Chart;

    /**
     * @class Ext.ux.Chart.Fusion.Adapter
     * @extends Ext.ux.Chart.FlashAdapter
     * @version 2.1
     * @author Doug Hendricks. doug[always-At]theactivegroup.com
     * @copyright 2007-2009, Active Group, Inc.  All rights reserved.
     * @donate <a target="tag_donate" href="http://donate.theactivegroup.com"><img border="0" src="http://www.paypal.com/en_US/i/btn/x-click-butcc-donate.gif" border="0" alt="Make a donation to support ongoing development"></a>
     * @license <a href="http://www.gnu.org/licenses/gpl.html">GPL 3.0</a>
     * @constructor
     * @param {Object} config The config object
     */
    Ext.ux.Chart.Fusion.Adapter = Ext.extend( Ext.ux.Chart.FlashAdapter, {

       /**
        * @cfg {String|Float} requiredVersion The required Flash version necessary to support the Chart object.
        * @memberof Ext.ux.Chart.Fusion.Adapter
        * @default 18
        */
       requiredVersion : 8,

        /**
        * @cfg {Mixed} blankChartData The default data value used to render an empty/blank chart.
        * @default '<chart></chart>'
        * @memberof Ext.ux.Chart.Fusion.Adapter
        */

       blankChartData : '<chart></chart>',

        /**
        * If specified, this value is loaded by the Fusion chart object itself when rendered.
        * @cfg {XMLString} chartData  Raw XML to load
        */
       chartData       : null,

       /**
        * @cfg {boolean} disableCaching Disable browser caching of URLs
        * Fusion already prevents caching internally
        */
       disableCaching  : false,

       /**
        * If specified, the URL is loaded by the Fusion chart object itself when rendered.
        * @cfg {String} dataURL Url of the XML Document to initially load.
        */
       dataURL         : null,

        /**
         * @cfg {Object/String/Function} autoLoad
         * A valid url spec according to the {@link #Ext.ux.Chart.FlashAdapter-load} method.
         * If autoLoad is not null, the panel will attempt to load its chart data
         * via an Ext.Ajax request immediately upon render.<p>
         * The URL will become the default dataURL for this componenent's chart.
         * </p>
         */
       autoLoad        : null,

       /**
        * Chart configuration options may be overriden by supplying alternate values only as necessary.
        * <br />See {@link Ext.ux.Media.Flash} for additional config options.
        * @cfg {Object} chartCfg/fusionCfg Flash configuration options.
        * @example chartCfg  : {
            id    : {String}  //id of &lt;object&gt; tag (auto-generated if not specified)
            name  : {String}  //should match the id value (defaults to the 'id')
            style : {Object}  //optional DomHelper style object
            params: {
                  start    : true,
                  controls : true,
                  height  : null,
                  width   : null,
                  autoSize : true, //if true, chart object consumes the entire component body  
                  autoScale : false, //Fusion specific option, scales the chart text to size of its container
                  renderOnResize:true, //if true, the chart will be rendered again after component resize
                  scripting : 'always',
                  cls     :'x-media x-media-swf x-chart-fusion',
                  flashVars : {
                    chartWidth  : defaults to Component actual size metrics (macro @width)
                    chartHeight : defaults to Component actual size metrics (macro @height)
                    debugMode   : Fusion debug mode (0,1)
                    DOMId       : DOM Id of SWF object (macro '@id')
                    scaleMode   : 'noScale' (set based on autoScale value or may be overridden)
                    registerWithJS: Fusion specific (0,1) default is 1
                    lang        : default 'EN',
                    dataXML     : An XML string representing the chart canvas config and data series (.chartData)
                    dataUrl     : A Url to load an XML resource (.dataURL)
                }
            }
        }
        */
       chartCfg       : null,


       /**
        * @cfg {String} autoScroll
        * @default "true"
        */
       autoScroll      : true,

       /** @private
        * default mediaCfg(fusionCfg) for a FusionChart object
        */
       mediaCfg        : {url      : null,
                          id       : null,
                          start    : true,
                          controls : true,
                          height  : null,
                          width   : null,
                          autoSize : true,
                          autoScale : false,
                          renderOnResize:true, //Fusion required after reflow for < Fusion 3.1 (use when autoScale is false)
                          scripting : 'always',
                          cls     :'x-media x-media-swf x-chart-fusion',
                          params  : {
                              wmode     :'opaque',
                              salign      : null
                               },
                          boundExternals :
                                ['print',
                                'saveAsImage',
                                'setDataXML',
                                'setDataURL',
                                'getDataAsCSV',
                                'getXML',
                                'getChartAttribute',
                                'hasRendered',
                                'signature',
                                'exportChart'
                                ]
        },

       /** @private */
       initMedia   : function(){

           this.addEvents(
            //Defined in FlashAdaper superclass
              /**
               * Fires when the underlying chart component reports an initialized state
               * @event chartload
               * @param {Ext.ux.Chart.Fusion} this
               * @param {object} the underlying chart component DOM reference
               */

             /**
              * Fires when the underlying chart component has rendered its chart series data.
              * @event chartrender
              * @param {Ext.ux.Chart.Fusion} this
              * @param {object} the underlying chart component DOM reference
              */

              /**
               * Fires when the data of the chart has finished loading - (when loaded by the Fusion chart only)
               * @event dataloaded
               * @param {Ext.ux.Chart} chart this Chart Component
               * @param {Element} chartObject the underlying chart component DOM reference
               */

              'dataloaded',

             /**
              * Fires when When there was an error in loading data from the specified URL (when loaded by the Fusion chart only)
              * @event dataloaderror
              * @param {Ext.ux.Chart} chart this Chart Component
              * @param {Element} chartObject the underlying chart component DOM reference
              */
              'dataloaderror',

              /**
                * Fires when the XML data loaded by chart didn't contain any data to display.
                * @event nodatatodisplay
                * @param {Ext.ux.Chart} chart this Chart Component
                * @param {Element} chartObject the underlying chart component DOM reference
                */
              'nodatatodisplay',

               /**
                * Fires when the XML data loaded by chart was invalid (wrong XML structure)
                * @event dataxmlinvalid
                * @param {Ext.ux.Chart} chart this Chart Component
                * @param {Element} chartObject the underlying chart component DOM reference
                */
              'dataxmlinvalid',
              
               /**
                * Fires when a server-side export is completed
                * @event exported
                * @param {Ext.ux.Chart} chart this Chart Component
                * @param {Element} chartObject the underlying chart component DOM reference
                */
              'exported',
              /**
                * Fires when a client-side export is ready for a save operation
                * @event exportready
                * @param {Ext.ux.Chart} chart this Chart Component
                * @param {Element} chartObject the underlying chart component DOM reference
                */
              'exportready'
            );
            //For compat with previous versions < 2.1
           this.chartCfg || (this.chartCfg = this.fusionCfg || {});

           chart.Fusion.Adapter.superclass.initMedia.call(this);

       },

       /** @private called just prior to rendering the media */
       onBeforeMedia: function(){

         /* assemble a valid mediaCfg for use with Fusion defined Chart SWF variables */
           var mc = this.mediaCfg;
           var cCfg = this.chartCfg || (this.chartCfg = {});

           cCfg.params                = this.assert( cCfg.params,{});
           cCfg.params[this.varsName] = this.assert( cCfg.params[this.varsName],{});

           cCfg.params[this.varsName] = Ext.apply({
              chartWidth  :  '@width' ,
              chartHeight :  '@height',
              scaleMode   : mc.autoScale ? 'exactFit' : 'noScale',
              debugMode   : 0,
              DOMId       : '@id',
            registerWithJS: 1,
         allowScriptAccess: "@scripting" ,
              lang        : 'EN',
              dataXML     : this.dataURL ? null : 
                     this.assert(this.dataXML || this.chartData || this.blankChartData,null),
              dataURL     : this.dataURL ? encodeURI(this.prepareURL(this.dataURL)) : null
          }, cCfg.params[this.varsName]);
          
          
          chart.Fusion.Adapter.superclass.onBeforeMedia.call(this);

      },

      /**
       * Set/update the current chart with a new XML Data series
       * @param {XMLString} xml The XML stream to update with.
       * @param {Boolean} immediate false to defer rendering the new data until the next chart rendering.
       * @default true
       */
      setChartData : function(xml, immediate){
           var o;
           this.chartData = xml;
           this.dataURL = null;
           
           if( immediate !== false && (o = this.getInterface())){

              if( 'setDataXML' in o ){
                   o.setDataXML(xml);
              } else { //FC Free Interface
                   this.setVariable("_root.dataURL","");
                   //Set the flag
                   this.setVariable("_root.isNewData","1");
                    //Set the actual data
                   this.setVariable("_root.newData",xml);
                   //Go to the required frame
                   if('TGotoLabel' in o){
                       o.TGotoLabel("/", "JavaScriptHandler");
                   }
              }
           }
           o = null;
           return this;
        },


       /**
       * Set/update the current chart with a new XML Data series
       * @param {String} url The URL of the XML stream to update with.
       * @param {Boolean} immediate false to defer rendering the new data until the next chart rendering.
       * @default true
       */
       setChartDataURL  : function(url, immediate){
          var o;
          this.dataURL = url;
          if(immediate !== false && (o = this.getInterface())){
              'setDataURL' in o ?
                 o.setDataURL(url) :
                   //FusionCharts Free has no support for dynamic loading of URLs
                   this.load({url:url, nocache:this.disableCaching} );
              o=null;
          }
        },

        /**
        * <b>Not implemented by Fusion Charts</b><p>
        * Returns the release number of the Chart object.
        * @return {string}
        */
       getChartVersion  :  function(){
            return '';

       }


    });

    window.FC_Rendered = window.FC_Rendered ? window.FC_Rendered.createInterceptor(chart.FlashAdapter.chartOnRender):chart.FlashAdapter.chartOnRender;
    window.FC_Loaded   = window.FC_Loaded   ? window.FC_Loaded.createInterceptor(chart.FlashAdapter.chartOnLoad):chart.FlashAdapter.chartOnLoad;


    /*
     * The following callbacks-to-events are only supported by Fusion Charts 3.1 or higher.
     */
    var dispatchEvent = function(name, id){
        
        var c, d = Ext.get(id);
        if(d && (c = d.ownerCt)){
           c.fireEvent.apply(c, [name, c, c.getInterface()].concat(Array.prototype.slice.call(arguments,2)));
        }
        c = d =null;
    };

    //Bind Fusion callbacks to an Ext.Event for the corresponding chart.
    Ext.each(['FC_DataLoaded', 'FC_DataLoadError' ,
        'FC_NoDataToDisplay','FC_DataXMLInvalid','FC_Exported','FC_ExportReady'],

      function(fnName){
        var cb = dispatchEvent.createDelegate(null,[fnName.toLowerCase().replace(/^FC_/i,'')],0);
        window[fnName] = typeof window[fnName] == 'function' ? window[fnName].createInterceptor(cb): cb ;

     });



    /**
     * @class Ext.ux.Chart.Fusion.Component
     * @extends Ext.ux.Chart.Fusion.Adapter
     * @version 2.1
     * @author Doug Hendricks. doug[always-At]theactivegroup.com
     * @copyright 2007-2009, Active Group, Inc.  All rights reserved.
     * @donate <a target="tag_donate" href="http://donate.theactivegroup.com"><img border="0" src="http://www.paypal.com/en_US/i/btn/x-click-butcc-donate.gif" border="0" alt="Make a donation to support ongoing development"></a>
     * @license <a href="http://www.gnu.org/licenses/gpl.html">GPL 3.0</a>
     * @constructor
     * @param {Object} config The config object
     * @base Ext.ux.Media.Flash.Component
     */
    Ext.ux.Chart.Fusion.Component = Ext.extend(Ext.ux.Media.Flash.Component, {
        ctype : 'Ext.ux.Chart.Fusion.Component' ,
        mediaClass  : Ext.ux.Chart.Fusion.Adapter
        });

    Ext.reg('fusion', chart.Fusion.Component);
    /**
     * @class Ext.ux.Chart.Fusion.Panel
     * @extends Ext.ux.Chart.Fusion.Adapter
     * @version 2.1
     * @author Doug Hendricks. doug[always-At]theactivegroup.com
     * @donate <a target="tag_donate" href="http://donate.theactivegroup.com"><img border="0" src="http://www.paypal.com/en_US/i/btn/x-click-butcc-donate.gif" border="0" alt="Make a donation to support ongoing development"></a>
     * @copyright 2007-2009, Active Group, Inc.  All rights reserved.
     * @license <a href="http://www.gnu.org/licenses/gpl.html">GPL 3.0</a>
     * @constructor
     * @param {Object} config The config object
     * @base Ext.ux.Media.Flash.Panel
     */
    Ext.ux.Chart.Fusion.Panel = Ext.extend(Ext.ux.Media.Flash.Panel, {
        ctype : 'Ext.ux.Chart.Fusion.Panel',
        mediaClass  : Ext.ux.Chart.Fusion.Adapter
        });

    Ext.reg('fusionpanel', chart.Fusion.Panel);

    /**
     * @class Ext.ux.Chart.Fusion.Portlet
     * @extends Ext.ux.Chart.Fusion.Adapter
     * @version 2.1
     * @author Doug Hendricks. doug[always-At]theactivegroup.com
     * @donate <a target="tag_donate" href="http://donate.theactivegroup.com"><img border="0" src="http://www.paypal.com/en_US/i/btn/x-click-butcc-donate.gif" border="0" alt="Make a donation to support ongoing development"></a>
     * @copyright 2007-2009, Active Group, Inc.  All rights reserved.
     * @license <a href="http://www.gnu.org/licenses/gpl.html">GPL 3.0</a>
     * @constructor
     * @param {Object} config The config object
     * @base Ext.ux.Media.Flash.Panel
     */
    Ext.ux.Chart.Fusion.Portlet = Ext.extend(Ext.ux.Media.Flash.Panel, {
        anchor      : '100%',
        frame       : true,
        collapseEl  : 'bwrap',
        collapsible : true,
        draggable   : true,
        cls         : 'x-portlet x-chart-portlet',
        ctype       : 'Ext.ux.Chart.Fusion.Portlet',
        mediaClass  : Ext.ux.Chart.Fusion.Adapter
    });

    Ext.reg('fusionportlet', chart.Fusion.Portlet);

    /**
     * @class Ext.ux.Chart.Fusion.Window
     * @extends Ext.ux.Chart.Fusion.Adapter
     * @version 2.1
     * @author Doug Hendricks. doug[always-At]theactivegroup.com
     * @donate <a target="tag_donate" href="http://donate.theactivegroup.com"><img border="0" src="http://www.paypal.com/en_US/i/btn/x-click-butcc-donate.gif" border="0" alt="Make a donation to support ongoing development"></a>
     * @copyright 2007-2009, Active Group, Inc.  All rights reserved.
     * @license <a href="http://www.gnu.org/licenses/gpl.html">GPL 3.0</a>
     * @constructor
     * @param {Object} config The config object
     * @base Ext.ux.Media.Flash.Window
     */

    Ext.ux.Chart.Fusion.Window = Ext.extend(Ext.ux.Media.Flash.Window, {
        ctype : "Ext.ux.Chart.Fusion.Window",
        mediaClass  : chart.Fusion.Adapter
        });

    Ext.reg('fusionwindow', Ext.ux.Chart.Fusion.Window);
})();

if (Ext.provide) { Ext.provide('uxfusion');}
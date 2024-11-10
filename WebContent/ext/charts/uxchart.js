/* @copyright 2007-2009, Active Group, Inc. All rights reserved.*/
 /**
  * @class Ext.ux.Chart.FlashAdapter
  * @extends Ext.ux.Media.Flash
  * @version 2.2
  * @donate <a target="tag_donate" href="http://donate.theactivegroup.com"><img border="0" src="http://www.paypal.com/en_US/i/btn/x-click-butcc-donate.gif" border="0" alt="Make a donation to support ongoing development"></a>
  * @license <a href="http://www.gnu.org/licenses/gpl.html">GPL 3.0</a>
  * @author Doug Hendricks,.doug[always-At]theactivegroup.com
  * @desc
  * Abstract Chart Class which defines the standard interface for all Flash charts supported by the ChartPack series.
  */

(function(){

    Ext.namespace("Ext.ux.Chart");
    var chart = Ext.ux.Chart;
    var flash = Ext.ux.Media.Flash;

    Ext.ux.Chart.FlashAdapter = Ext.extend( Ext.ux.Media.Flash, {

       /**
        * @cfg {String|Float} requiredVersion The required Flash version necessary to support the Chart object.
        * @memberof Ext.ux.Chart.FlashAdapter
        * @default 8
        */
       requiredVersion : 8,


       /**
        * @cfg {Mixed} unsupportedText Text Markup/DOMHelper config displayed when the Flash Plugin is not available
        */
       unsupportedText : {cn:['The Adobe Flash Player{0}is required.',{tag:'br'},{tag:'a',cn:[{tag:'img',src:'http://www.adobe.com/images/shared/download_buttons/get_flash_player.gif'}],href:'http://www.adobe.com/shockwave/download/download.cgi?P1_Prod_Version=ShockwaveFlash',target:'_flash'}]},

       /**
        * @cfg {String} chartURL Url of the Flash Chart object.
        */
       chartURL        : null,

       /**
        * @cfg {Object/JSONstring/Function} chartData Chart data series to load when initially rendered. <p> May be an object, JSONString, or Function that returns either.
        */
       chartData       : null,

       /**
        * @cfg {String} dataURL Url of the chart series to load when initially rendered.
        */
       dataURL         : null,

       /**
        * @cfg {String/Object} autoLoad Url string or{@link #Ext.ux.Chart.FlashAdapter-load} method config object.
        */
       autoLoad        : null,

       /**
        * @cfg {Boolean/Object} loadMask True to mask the Component while the Chart object itself is being
        * initially loaded.  See{@link Ext.ux.IntelliMask} for configuration options.
        */
       loadMask        : null,

       /**
        * @cfg {Boolean/Object} mediaMask True to mask the Component while the Chart class loads data to update the
        * chart during {@link #Ext.ux.Chart.FlashAdapter-load} operations.  See {@link Ext.ux.IntelliMask} for configuration options.
        */
       mediaMask       : null,

       autoMask        : null,
       
       /**
        * @cfg {Boolean} disableCaching true to append a unique param to all URLs to defeat browser caching problems.
        * @default true
        */
       disableCaching : true,

       /**
        * @cfg {Mixed} blankChartData The default data value (or a Function that returns the value) used to render an empty/blank chart.
        */
       blankChartData  : '',


       /**
        * @cfg {string} externalsNamespace ExternalInterface commands for all Flash charts will be bound here.<p>This value becomes the secondary interface
        * for calling object methods defined by the chart object vendor that are not standardized by the ChartPack series.</p>
        * @default chart
        * @example
        * //invoke a chart's Flash ExternalInterface method
        * Ext.getCmp('salesChart').chart.print();
        */
       externalsNamespace  : 'chart',

       /**
        * @cfg {Object} chartCfg Flash configuration options.
        * @example chartCfg  : {  //optional
            id    : String   id of <object> tag
            style : Obj  optional DomHelper style object
            params: {

                flashVars : {
                    chartWidth  : defaults to SWF Object geometry
                    chartHeight : defaults to SWF Object geometry
                    DOMId       : DOM Id of SWF object (defaults to assigned macro '@id')
                    dataXML     : An XML string representing the chart canvas config and data series
                    dataUrl     : A Url to load an XML resource (dataXML)
                }
            }
        }
        */
       chartCfg       : null,

       /**
       * The default Flash Externalinterface object as defined by the{@link #Ext.ux.Chart.FlashAdapter-externalsNamespace} config value.
       *<p>Use this object to access public methods/properties defined by the Flash Chart implementation.
       * @property {DOMElement} chart
       *
       */

       chart          : null,


       /** @private
        * default mediaCfg(chartCfg) for a Chart object
        */
       mediaCfg        : {url      : null,
                          id       : null,
                          start    : true,
                          controls : true,
                          height  : null,
                          width   : null,
                          autoSize : true,
                          renderOnResize:false,
                          scripting : 'always',
                          cls     :'x-media x-media-swf x-chart',
                          params  : {
                              allowscriptaccess : '@scripting',
                              wmode     :'opaque',
                              scale     :'exactfit',
                              scale       : null,
                              salign      : null
                           }
        },

       /** @private */
       initMedia   : function(){

           this.addEvents(

               /**
                * Fires after a succesfull{@link #Ext.ux.Chart.FlashAdapter-load} method attempt but before the retrieved data is submitted to the chart.
                * @event beforeload
                * @param {Ext.ux.Chart} chart this chart component
                * @param {Element} chartObject the underlying chart component DOM reference
                * @param {Object} response The response object from the Ajax request.
                * @param {Object} options The request options used to perform the Ajax request.
                * Returning a value of false from this event prevents updating of the chart.
                */

               'beforeload',

               /**
                * Fires when the the{@link #Ext.ux.Chart.FlashAdapter-load} method attempt reports an error condition.
                * @event loadexception
                * @param {Ext.ux.Chart} chart this chart component
                * @param {Element} chartObject the underlying chart component DOM reference
                * @param {Object} response The response object from the Ajax request.
                * @param {Object} options The request options used to perform the Ajax request.
                * @param {Error} exception The reported exception.
                */

               'loadexception',

               /**
                * If supported by the chart (or can be determined by the chart class),
                * fires when the underlying chart component reports that it has loaded its chart series data.
                * @event chartload
                * @param {Ext.ux.Chart} chart this chart component
                * @param {Element} chartObject the underlying chart component DOM reference
                */

               'chartload',

              /**
               * If supported by the chart or can be determined by the chart class,
               * fires when the underlying chart component has rendered its chart series data.
               * @event chartrender
               * @param {Ext.ux.Chart} chart This chart component
               * @param {Element} chartObject The underlying chart component DOM reference
               */
               'chartrender'
            );


           this.mediaCfg.renderOnResize =
                this.mediaCfg.renderOnResize || (this.chartCfg || {}).renderOnResize;

           chart.FlashAdapter.superclass.initMedia.call(this);

           if(this.autoLoad){
                this.on('mediarender', this.doAutoLoad, this, {single:true} );
           }
       },

       /** @private called just prior to rendering the media
        * Merges chartCfg with default Flash mediaCfg for charting
        */
       onBeforeMedia: function(){

          /* assemble a valid mediaCfg */
          var mc =  this.mediaCfg;
          var mp = mc.params||{};
          delete mc.params;
          var mv = mp[this.varsName]||{};
          delete mp[this.varsName];

          //chartCfg
          var cCfg = Ext.apply({},this.chartCfg || {});

           //chart params
          var cp = Ext.apply({}, this.assert( cCfg.params,{}));
          delete cCfg.params;

           //chart.params.flashVars
          var cv = Ext.apply({}, this.assert( cp[this.varsName],{}));
          delete cp[this.varsName];

          Ext.apply(mc , cCfg, {
              url  : this.assert(this.chartURL, null)
          });

          mc.params = Ext.apply(mp,cp);
          mc.params[this.varsName] = Ext.apply(mv,cv);

          chart.FlashAdapter.superclass.onBeforeMedia.call(this);

      },

     /**
      * Set/update the current chart with a new Data series URL.<p>Override the subclass for a chart-specific implementation.
      * @param {String} url The URL of the stream to update with.
      * @param {Boolean} immediate false to defer rendering the new data until the next chart rendering.
      * @default true
      * @return {ux.Chart.Component} this
      */
     setChartDataURL  : function(url, immediate){

           return this;

         },

     /**
       * Performs an <b>asynchronous</b> request, updating this chart Element with the response.
       * If params are specified it uses POST, otherwise it uses GET.<br><br>
       * <b>Note:</b> Due to the asynchronous nature of remote server requests, the Chart
       * will not have been fully updated when the function returns. To post-process the returned
       * data, use the callback option, or an <b><tt>{@link #Ext.ux.Chart.FlashAdapter-beforeload}</tt></b> event handler.
       * @param {Object} options A config object containing any of the following options:<ul>
       * <li>url : <b>String/Function</b><p class="sub-desc">The URL to request or a function which
       * <i>returns</i> the URL </p></li>
       * <li>method : <b>String</b><p class="sub-desc">The HTTP method to
       * use. Defaults to POST if the <tt>params</tt> argument is present, otherwise GET.</p></li>
       * <li>params : <b>String/Object/Function</b><p class="sub-desc">The
       * parameters to pass to the server (defaults to none). These may be specified as a url-encoded
       * string, or as an object containing properties which represent parameters,
       * or as a function, which returns such an object.</p></li>
       * <li>callback : <b>Function</b><p class="sub-desc">A function to
       * be called when the response from the server arrives. Returning a false value from the callback prevents loading of the chart data.<p>
       * The following parameters are passed:<ul>
       * <li><b>Component</b> : ux.Chart.Component<p class="sub-desc">The Element being updated.</p></li>
       * <li><b>success</b> : Boolean<p class="sub-desc">True for success, false for failure.</p></li>
       * <li><b>response</b> : XMLHttpRequest<p class="sub-desc">The XMLHttpRequest which processed the update.</p></li>
       * <li><b>options</b> : Object<p class="sub-desc">The config object passed to the load call.</p></li></ul>
       * </p></li>
       * <li>scope : <b>Object</b><p class="sub-desc">The scope in which
       * to execute the callback (The callback's <tt>this</tt> reference.) If the
       * <tt>params</tt> argument is a function, this scope is used for that function also.</p></li>
       * <li>timeout : <b>Number</b><p class="sub-desc">The number of seconds to wait for a response before
       * timing out (defaults to 30 seconds).</p></li>
       * <li>text : <b>String</b><p class="sub-desc">The text to use to override the default loadMask text of the
       * {@link #Ext.ux.Chart.FlashAdapter-loadMask} div if{@link #Ext.ux.Chart.FlashAdapter-autoMask} is enabled.</p></li>
       * <li>disableCaching: <b>Boolean</b><p class="sub-desc">Only needed for GET
       * requests, this option causes an extra, auto-generated parameter to be appended to the request
       * to defeat caching (defaults to{@link #Ext.ux.Chart.FlashAdapter-disableCaching}).</p></li></ul>
       * <p>
      *
      * @param {Object/String/Function} url A config object containing any of the following options:
      * @param {Object/Function} params A config object containing parameter definitions or a Function that does, if any.
      * @param {Function} callback Optional callback Function called upon completion of the request.
      * @param {Object} scope The scope of the callback Function.
      * @return {Ext.Data.Connection} The Ext.data.Connection instance used to make the request. (Useful for abort operations)
      * @example
        var requestConnection = chartComponent.load({
           url: "your-url.php",
           method  : "POST",
           params: {param1: "foo", param2: "bar"}, // or a URL encoded string
           callback: yourFunction,
           scope: yourObject, // optional scope for the callback
           disableCaching : true,
           timeout: 30,
           text   : 'Loading Sales Data...',  //optional loadMask text override
       });
        */

       load :  function(url, params, callback, scope){

           if(!url){return null;}

           this.connection || (this.connection = new Ext.data.Connection() );

           if(this.loadMask && this.autoMask && !this.loadMask.active ){

                this.loadMask.show({
                     msg : url.text || null
                    ,fn : arguments.callee.createDelegate(this,arguments)
                    ,fnDelay : 100
                 });
                return this.connection;
           }

           var method , dataUrl, cfg, callerScope,timeout,disableCaching ;

           if(typeof url === "object"){ // must be config object
               cfg = Ext.apply({},url);
               dataUrl = cfg.url;
               params = params || cfg.params;
               callback = callback || cfg.callback;
               callerScope = scope || cfg.scope;
               method = cfg.method || params ? 'POST': 'GET';
               disableCaching = cfg.disableCaching ;
               timeout = cfg.timeout || 30;
           } else {
               dataUrl  = url;
           }

           //resolve Function if supplied
           if(!(dataUrl = this.assert(dataUrl, null)) ){return null;}

           method = method || (params ? "POST" : "GET");
           if(method === "GET"){
               dataUrl= this.prepareURL(dataUrl, disableCaching );
           }
           var o;
           o = Ext.apply(cfg ||{}, {
               url : dataUrl,
               params:  params,
               method: method,
               success: function(response, options){
                    o.loadData = this.fireEvent('beforeload', this, this.getInterface(), response, options) !== false;
               },
               failure: function(response, options){
                    this.fireEvent('loadexception', this, this.getInterface(), response, options);
                   },
               scope: this,
               //Actual response is managed here
               callback: function(options, success, response ) {
                   o.loadData = success;
                   if(callback){
                       o.loadData = callback.call(callerScope , this, success, response, options )!== false;
                   }
                   if(success && o.loadData){
                        /* If either the callback or the beforeload event provide a
                         * options.chartResponse property, use that instead of responseText
                         */
                        this.setChartData(options.chartResponse || response.responseText);
                    }
                   if(this.autoMask){ this.onChartLoaded(); }

               },
               timeout: (timeout*1000),
               argument: {
                "options"   : cfg,
                "url"       : dataUrl,
                "form"      : null,
                "callback"  : callback,
                "scope"     : callerScope ,
                "params"    : params
               }
           });

           this.connection.request(o);
           return this.connection;
      },
      /**
       * Set/update the current chart with a new data series
       * @param {Mixed} data The chart series data (string, Function, or JSON object depending on the needs of the chart) to update with.
       */
      setChartData  : function(data){

          return this;
      },
       /** @private */
      setMask  : function(ct) {

          chart.FlashAdapter.superclass.setMask.apply(this, arguments);

          //loadMask reserved for data loading operations only
          //see: @cfg:mediaMask for Chart object masking
          var lm=this.loadMask;
          if(lm && !lm.disabled){
              lm.el || (this.loadMask = lm = new Ext.ux.IntelliMask( this[this.mediaEl] || ct, 
                 Ext.isObject(lm) ? lm : {msg : lm}));
          }

      },

       /** @private */
      doAutoLoad  : function(){
          this.load (
           typeof this.autoLoad === 'object' ?
               this.autoLoad : {url: this.autoLoad});

          this.autoLoad = null;
      },


       /** @private */
       onChartRendered   :  function(){
        
             this.fireEvent('chartrender', this, this.getInterface());
             if(this.loadMask && this.autoMask){this.loadMask.hide();}
       },
       /** @private */
       onChartLoaded   :  function(){
        
            this.fireEvent('chartload', this, this.getInterface());
            if(this.loadMask && this.autoMask){this.loadMask.hide();}
       },

       /** @private  this function is designed to be used when a chart object notifies the browser
        * if its initialization state.  Raised Asynchronously.
        */
       onFlashInit  :  function(id){
           chart.FlashAdapter.superclass.onFlashInit.apply(this,arguments);
           this.fireEvent.defer(1,this,['chartload',this, this.getInterface()]);
       },

       loadMask : false,

       /**
        * @returns {Mixed} Returns the release number of the Chart object.
        * <p>Note: Override for chart object's specific support for it.
        */

       getChartVersion :  function(){}

    });


    /** @private  Class method callbacks */
    chart.FlashAdapter.chartOnLoad = function(DOMId){

        var c, d = Ext.get(DOMId);
        if(d && (c = d.ownerCt)){
            c.onChartLoaded.defer(1, c);
            c = d=null;
            return false;
        }
        d= null;
    };

    /** @private  Class method callbacks */
    chart.FlashAdapter.chartOnRender = function(DOMId){
         
        var c, d = Ext.get(DOMId);
        
        if(d && (c = d.ownerCt)){
            c.onChartRendered.defer(1, c);
            c = d = null;
            return false;
        }
        d= null;
    };

    Ext.apply(Ext.util.Format , {
       /**
         * Convert certain characters (&, <, >, and ') to their HTML character equivalents for literal display in web pages.
         * @param {String} value The string to encode
         * @return {String} The encoded text
         */
        xmlEncode : function(value){
            return !value ? value : String(value)
                .replace(/&/g, "&amp;")
                .replace(/>/g, "&gt;")
                .replace(/</g, "&lt;")
                .replace(/"/g, "&quot;")
                .replace(/'/g, "&apos;");
        },

        /**
         * Convert certain characters (&, <, >, and ') from their HTML character equivalents.
         * @param {String} value The string to decode
         * @return {String} The decoded text
         */
        xmlDecode : function(value){
            return !value ? value : String(value)
                .replace(/&gt;/g, ">")
                .replace(/&lt;/g, "<")
                .replace(/&quot;/g, '"')
                .replace(/&amp;/g, "&")
                .replace(/&apos;/g, "'");

        }

    });

})();
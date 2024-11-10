var IndexPage = Ext.extend(Ext.Viewport, {
	top : {
            cls: 'app-header',
            region: 'north',
            height: 70,
            html: '<table width="100%" height="100%" border="0" cellspacing="0" cellpadding="0" style="table-layout:fixed;">'+
				  '<tr><td width="254px" style="background:url(../../img/top_left.jpg)">&nbsp;</td>'+
				  '<td style="background:url(../../img/top_center.jpg)">&nbsp;</td>'+
				  '<td width="544px" style="background:url(../../img/top_right.jpg)"><div id="clock" class="clock"></div></td>'+
				  '</tr>'+
				'</table>',
            margins: '5 5 5 5'
    },
	west : {
			id:'west-panel',
			region : 'west',
			title : "导航",
			split : true,
			width : 180,
	        minSize: 150,
	        maxSize: 300,
	        margins: '0 3 5 5',
			iconCls : 'menu-navigation',
			autoScroll : false,
			layout : 'accordion',
			collapsible : true
	},
	center:{
			region : 'center',
			margins: '0 5 5 0',
			border: false,
			html : '<iframe id="main" src="../kucun/kcsearch.jsp" width="100%" height="100%" scrolling="auto" frameborder="0" ></iframe>'
	},
	south : {
			region : 'south',
			height : 30,
			tbar : [{
						text : '退出系统',
						iconCls : 'btn-logout',
						handler : function() {
							App.Logout();
						}
					},'-',{
						tag:'div',
						html:" 欢迎您！"+log_name
					},'->', {
						xtype : 'combo',
						mode : 'local',
						editable : false,
						value : '切换皮肤',
						width : 100,
						triggerAction : 'all',
						store : [['ext-all', '默认风格'], 
								['xtheme-gray', '银白风格'],
								['xtheme-purple', '紫色风格'], 
								['xtheme-olive', '绿色风格'],
								['xtheme-darkgray', '灰色风格']],
						listeners : {
							scope : this,
							select : function(d, b, c) {
								if (d.value != "") {
									var xthemem = "../../ext/resources/css/"+ d.value + ".css";
									App.setCookie("mystock-xtheme",xthemem,7);
									Ext.util.CSS.swapStyleSheet("theme", xthemem);
									var iframe = document.getElementById("main");
									iframe.src = "../kucun/kcsearch.jsp";
								}
							}
						}
					}]
	},
	constructor : function() {
		IndexPage.superclass.constructor.call(this, {
					layout : 'border',
					items : [this.top,this.west,this.center,this.south]
				});
		var clock = new Clock();
    	clock.display(document.getElementById("clock"));
		this.loadMenu();
	},
	loadMenu : function(){
		var toolBar = Ext.getCmp("top-toolbar");
		Ext.Ajax.request({
			url : "user_getMenuBuf.do",
            scope: this,
			success : function(response, options) {
				var menudata = eval(response.responseText);
				if(menudata){
					var westPanel = Ext.getCmp("west-panel");
					for (var i = 0; i < menudata.length; i++) {
						var panel = new Ext.tree.TreePanel({
							id : menudata[i].id,
							title : menudata[i].text,
							iconCls : menudata[i].iconCls,
							layout : "fit",
							animate : true,
							border : false,
							autoScroll : true,
							loader : new Ext.tree.TreeLoader(),
							root : new Ext.tree.AsyncTreeNode({
										expanded: true,
										children: menudata[i].children
									}),
							listeners : {
								scope : this,
								click:  this.clickMenu
							},
							rootVisible : false
						});
						westPanel.add(panel);
						westPanel.doLayout();
					}
				}
			},
			failure: function(){
				Ext.Msg.alert("错误提示","菜单初始化失败!");
			}
		});
	},
	clickMenu : function(n){
		var iframe = document.getElementById("main");
		iframe.src = n.attributes.url;
	}
});
var Clock = function() {
	var date = new Date();
	this.year = date.getFullYear();
	this.month = date.getMonth() + 1;
	this.date = date.getDate();
	this.day = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六")[date.getDay()];
	this.hour = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
	this.minute = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
	this.second = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

	this.toString = function() {
		return "当前日期：" + this.year + "年" + this.month + "月" + this.date + "日 " + this.hour + ":" + this.minute + ":" + this.second + " " + this.day; 
	};
	
	this.toSimpleDate = function() {
		return this.year + "-" + this.month + "-" + this.date;
	};
	
	this.toDetailDate = function() {
		return this.year + "-" + this.month + "-" + this.date + " " + this.hour + ":" + this.minute + ":" + this.second;
	};
	
	this.display = function(ele) {
		var clock = new Clock();
		ele.innerHTML = clock.toString();
		window.setTimeout(function() {clock.display(ele);}, 1000);
	};
}
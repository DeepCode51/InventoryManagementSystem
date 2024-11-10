Ext.ns("App");
App.init = function () {
	Ext.QuickTips.init();
	//主框架
	var main = new IndexPage();
};
App.showMask = function() {
	if (!this.loadMask) {
		this.loadMask = new Ext.LoadMask(Ext.getBody());
		this.loadMask.show();
	}
};
App.hideMask = function() {
	if (this.loadMask) {
		this.loadMask.hide();
		this.loadMask = null;
	}
};
App.getCurrentSize = function(){
    var comSize={height:0,width:0};
	var tabPanel = Ext.getCmp('centerTabPanel');
	var h = tabPanel.getInnerHeight();
	var w = tabPanel.getInnerWidth();
	comSize.height=h-1;
	comSize.width=w-1;
    return comSize;
};
App.getCurrentHeight = function(){
  var comSize = this.getCurrentSize();
  return comSize.height;
};
App.getCurrentWidth = function(){
  var comSize = this.getCurrentSize();
  return comSize.width;
};
App.setCookie = function(name, value, day){//三个参数，一个是cookie的名字，一个是值，一个保存天数
	var Days = day; //此 cookie 将被保存的天数
	var exp = new Date(); //new Date("December 31, 9998");
	exp.setTime(exp.getTime() + Days * 1 * 60 * 60 * 1000);
	document.cookie = name + "=" + escape(value) + ";expires=" + exp.toGMTString();
}
App.getCookie = function(name){//取cookies函数
	var arr = document.cookie.match(new RegExp("(^| )" + name + "=([^;]*)(;|$)"));
	if (arr != null)
		return unescape(arr[2]);
	return null;
};
App.Logout = function() {
	window.location.href = "/login.jsp";
};
App.logoutSystem = function(){
	if ( window.confirm("你确定要注销系统吗? "))  {
		window.parent.location.href="../zuxiao.jsp";
	}
};
Ext.onReady(App.init);


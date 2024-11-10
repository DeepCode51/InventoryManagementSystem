var zhMoney = function(v) {
    v = (Math.round((v-0)*100))/100;
    v = (v == Math.floor(v)) ? v + ".00" : ((v*10 == Math.floor(v*10)) ? v + "0" : v);
    v = String(v);
    var ps = v.split('.'),
        whole = ps[0],
        sub = ps[1] ? '.'+ ps[1] : '.00',
        r = /(\d+)(\d{3})/;
    while (r.test(whole)) {
        whole = whole.replace(r, '$1' + ',' + '$2');
    }
    v = whole + sub;
    if (v.charAt(0) == '-') {
        return '-￥' + v.substr(1);
    }
    return "￥" +  v;
}

var showNotification = function(config){
	var win = new Ext.ux.Notification(Ext.apply({
		animateTarget: 'xxx'
		, autoDestroy: true
		, hideDelay: 5000
		, html: ''
		, iconCls: 'x-icon-waiting'
		, title: ''
	}, config));
	win.show();

	return win;
};
var hideNotification = function(win, delay){
	if(win){
		(function(){ win.hide(); }).defer(delay || 3000);
	}
};
/*!
 * 当前库存查询
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var v_lbid="0",v_lbname="所有类别",v_start=0, v_limit=20;
		
	//商品类别树窗口
    var splbTreeWindow = new Ext.Window({
		width:240,
		height:260,
		closeAction:"hide",
		layout : "fit",
		buttonAlign : "center",
		items : [{
			xtype:"treepanel",
			useArrows: true,
	        autoScroll:true,
	        enableDD:false,
	        containerScroll: true,
	        dataUrl: "splb_findSplbTree.do",
	        root: {
	            nodeType: "async",
	            text: "所有类别",
	            select:true,
	            draggable: false,
	            id: "0"
	        },
	        listeners:{
	        	load:function(){
	        		this.getSelectionModel().select(this.root);
	        	},
	        	click:function(n){
	        		v_lbid = n.id;
	        		v_lbname = n.text;
	        	},
	        	dblclick:function(n){
	        		v_lbid = n.id;
	        		v_lbname = n.text;
	        		splbTreeWindow.hide();
					searchForm.getForm().findField("lbid").setValue(v_lbid);
					searchForm.getForm().findField("lbname").setValue(v_lbname);
	        	}
	        	
	        }
		}],
		listeners:{
			beforeshow:function(){
				var xy = Ext.getCmp("lbtext").getPosition();
				splbTreeWindow.setPosition(xy[0],xy[1]+25);
			},
        	show:function(){
        		this.items.get(0).getRootNode().expand();
        	}
        },
		buttons : [{
			width:60,
			text : "选择",
			handler : function() {
				splbTreeWindow.hide();
				searchForm.getForm().findField("lbid").setValue(v_lbid);
				searchForm.getForm().findField("lbname").setValue(v_lbname);
			}
		}, {
			width:60,
			text : "取消",
			handler : function() {
				splbTreeWindow.hide();
			}
		}]
	});
	
	//查询表单
	var searchForm = new Ext.FormPanel({
		region:"north",
		height: 80,
		border : false,
		layout : "form",
		padding : "5 20 0 20",
		items:[{
			xtype:"fieldset",
			title:"查询设置",
			padding:"0 20 0 15",
			items:[{
				layout:"column",
				defaults:{
					xtype:"container",
					autoEl:"div",
					labelAlign:"right",
					layout:"form"
				},
				items:[{
					width:190,
					labelWidth:60,
					items:[{
						width:100,
						id:"lbtext",
						xtype : "textfield",
						name:"lbname",
						fieldLabel:"商品类别",
						value:"所有类别",
						maxLength :50,
						enableKeyEvents:true,
						listeners:{
							focus:function(){
								splbTreeWindow.show();
							}
						}
					}]
				},{
					width:310,
					items:[{
						width:180,
						labelWidth:100,
						xtype:"textfield",
						name:"search",
						fieldLabel:"商品编号或名称"
					}]
				},{
					width:250,
					items:[{
						width:75,
						xtype:"button",
						iconCls:"btn-list",
						text:"查询",
						handler:function(){
							var f = searchForm.getForm();
							if (f.isValid()) {
								kcspStore.load({params:f.getValues()});
							}
						}
					}]
				}]
			},{
				xtype:'hidden',
				name:'lbid',
				value:'0'
			}]
		}]
	});
	
	var DqkcObj = [
		{ name:'spid', type:'string'},
		{ name:'spname', type:'string'},
		{ name:'lbname', type:'string'},
		{ name:'xinghao', type:'string'},
		{ name:'kcsl', type:'int'},
		{ name:'xsll', type:'int'},
		{ name:'scjj', type:'double'},
		{ name:'jhprice', type:'double'},
		{ name:'chprice', type:'double'},
		{ name:'kczj', type:'double'},
		{ name:'dw', type:'string'},
		{ name:'csname', type:'string'},
		{ name:'zj', type:'string'}
	];
	
	//库存数据
	var kcspStore = new Ext.data.JsonStore({
	    url: 'search_findKcByParams.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields: DqkcObj
	});
	
	//库存列表
    var kcspGrid = new Ext.grid.GridPanel({
    	id:"djspGrid",
        store: kcspStore,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[
				{header: '商品编号', width: 100, sortable:true, dataIndex: 'spid'},
	            {header: '商品名称', width: 130, sortable:true, dataIndex: 'spname'},
	            {header: '类别', width: 60, sortable:true, dataIndex: 'lbname'},
	            {header: '型号', width: 80, sortable:true, dataIndex: 'xinghao'},
	            {header: '库存数量', width: 80, sortable:true, dataIndex: 'kcsl'},
	            {header: '销售总数', width: 80, sortable:true, dataIndex: 'xsll'},
	            {header: '上次进价', width: 60, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'scjj'},
	            {header: '成本均价', width: 60, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'jhprice'},
	            {header: '销售价', width: 60, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'chprice'},
	            {header: '库存总值', width: 60, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'kczj'},
	            {header: '单位', width: 60, sortable:true, dataIndex: 'dw'},
	            {header: '生产厂商', width: 100, sortable:true, dataIndex: 'csname'},
	            {header: '备注', width: 200, sortable:true, dataIndex: 'bz'}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        height:150,
        margins:"10 20 5 20",
        style:"border:1px solid",
		region:"center",
        iconCls:"",
        listeners:{
        	render:function(){
        		kcspStore.load({params:searchForm.getForm().getValues()});
        	}
        }
    });
    
	
	//布局
    new Ext.Viewport({
		layout:"fit",
		items:[{
			frame:true,
			title:"当前库存查询",
			iconCls:'menu-15',
			layout:"border",
			items:[searchForm,kcspGrid]
		}]
	});

});
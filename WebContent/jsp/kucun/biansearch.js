/*!
 * 报损报溢查询
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var v_start=0, v_limit=20;
	
	//查询表单
	var ckdForm = new Ext.FormPanel({
		region:'north',
		height: 80,
		border : false,
		layout : 'form',
		padding : '5 20 0 20',
		items:[{
			id:"ckdfieldset",
			xtype:"fieldset",
			title:"查询设置",
			padding:'0 20 0 15',
			items:[{
				layout:"column",
				defaults:{
					xtype:"container",
					autoEl:"div",
					labelAlign:'right',
					layout:"form"
				},
				items:[{
					width:60,
					style:'padding-top:5',
					xtype:'tbtext',
					text:'查询日期:'
				},{
					width:100,
					xtype:"datefield",
					name:'startdate',
					format:'Y-m-d',
					allowBlank : false,
					value:new Date()
				},{
					width:28,
					style:'padding:5 0 0 8',
					xtype:'label',
					text:'至'
				},{
					width:100,
					xtype:"datefield",
					name:'enddate',
					format:'Y-m-d',
					allowBlank : false,
					value:new Date()
				},{
					width:30
				},{
					width:250,
					items:[{
						id:'tab_comb',
						width : 100,
						xtype:"combo",
						hiddenName:'tab',
						fieldLabel:"单据类型",
   						mode: 'local',
   						triggerAction: 'all',
						valueField :"value",
   						displayField: "show",
   						emptyText: '请选择',
   						value:'Bsd',
   						editable : false,
						store : new Ext.data.SimpleStore({
						    fields: ['value', 'show'],
						    data : [['Bsd','报损单'],['Byd','报溢单']]
						})
					}]
				},{
					columnWidth:0.2,
					items:[{
						width:75,
						xtype:"button",
						text:'查询',
						handler:function(){
							var f = ckdForm.getForm();
							if (f.isValid()) {
								ckdStore.load({params:f.getValues()});
							}
						}
					}]
				}]
			}]
		}]
	});
	
	var DjObj = [
		{ name:'djid', type:'string'},
		{ name:'riqi', type:'date', mapping : 'riqi.time', dateFormat : 'time' },
		{ name:'username', type:'string'},
		{ name:'bz', type:'string'}
	];
	
	//销售单数据
	var ckdStore = new Ext.data.JsonStore({
	    url: "../bsy_findDjByParams.do",
	    root: 'root',
	    totalProperty: 'total',
	    fields: DjObj
	});
	
	//销售单列表
    var ckdGrid = new Ext.grid.GridPanel({
        store: ckdStore,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[new Ext.grid.RowNumberer(),
	            {header: '单号', width: 140, sortable:true, align:'center', dataIndex: 'djid'},
	            {header: '日期', width: 100, sortable:true, align:'center', renderer : Ext.util.Format.dateRenderer('Y-m-d'),  dataIndex: 'riqi'},
	            {header: '类型', width: 100, sortable:true, align:'center', dataIndex: 'type',
	            	renderer : function(v,c,r) {
	            		var djid = r.get("djid");
	            		if(djid.indexOf("BS")!=-1){
		            		return "报损单"
	            		}else if (djid.indexOf("BY")!=-1){
	            			return "报溢单"
	            		}
	            	}
	            },
	            {header: '操作员', width: 100, sortable:true, align:'center', dataIndex: 'username'},
	            {header: '备注', width: 260, dataIndex: 'bz'}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        margins:'10 20 0 20',
        style:'border:1px solid',
		region:'center',
        iconCls:'',
        
        listeners:{
        	rowclick:function(a,b){
        		var tab = Ext.getCmp("tab_comb").getValue()+"sp";
        		var djid = ckdStore.getAt(b).get("djid");
        		ckspStore.load({params:{tab:tab,djid:djid}});
        	}
        }
    });
	
	
	var SpxxObj = [
		{ name:'spid', type:'string'},
		{ name:'spname', type:'string'},
		{ name:'spxinghao', type:'string'},
		{ name:'spdw', type:'string'},
		{ name:'dj', type:'double'},
		{ name:'sl', type:'string'},
		{ name:'zj', type:'double'}
	];
	
	//销售商品数据
	var ckspStore = new Ext.data.JsonStore({
	    url: "../bsy_findDjByParams.do",
	    root: 'root',
	    totalProperty: 'total',
	    fields: SpxxObj
	});
	
	//销售商品列表
    var ckspGrid = new Ext.grid.GridPanel({
    	id:'djspGrid',
        store: ckspStore,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[new Ext.grid.RowNumberer(),
				{header: '商品编号', width: 100, sortable:true, dataIndex: 'spid'},
	            {header: '商品名称', width: 200, sortable:true, dataIndex: 'spname'},
	            {header: '商品型号', width: 150, sortable:true, dataIndex: 'spxinghao'},
	            {header: '单位', width: 100, sortable:true, dataIndex: 'spdw'},
	            {header: '采购价', width: 100, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'dj'},
	            {header: '数量', width: 100, sortable:true, align:'center', dataIndex: 'sl'},
	            {header: '总金额', width: 100, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'zj'}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        height:150,
        margins:'10 20 5 20',
        style:'border:1px solid',
		region:'south',
        iconCls:''
    });
    
	
	//布局
    new Ext.Viewport({
		layout:'fit',
		items:[{
			frame:true,
			title:'报损报溢查询',
			iconCls:'menu-13',
			layout:'border',
			items:[ckdForm,ckdGrid,ckspGrid]
		}]
	});

});
/*!
 * 进货单据查询
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var v_start=0, v_limit=20;
	var date = new Date();
	date.setMonth(date.getMonth()-1);
	//查询表单
	var jhdForm = new Ext.FormPanel({
		region:'north',
		height: 80,
		border : false,
		layout : 'form',
		padding : '5 20 0 20',
		items:[{
			id:"jhdfieldset",
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
					value:date
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
						labelWidth:100,
						xtype:"textfield",
						name:'search',
						fieldLabel:"供应商/单据编号",
						anchor:"90%"
					}]
				},{
					width:180,
					labelWidth:60,
					items:[{
							xtype:'combo',
							hiddenName:'jystate',
							fieldLabel:'是否付款',
							mode: 'local',
							triggerAction: 'all',
							valueField :'value',
							displayField: 'text',
							editable : false,
							store : new Ext.data.SimpleStore({
							    fields: ['value', 'text'],
							    data : [['','全部'],['1','已付'],['0','未付']]
							}),
							width:80
					}]
				},{
					width:100,
					items:[{
						width:75,
						xtype:"button",
						text:'查询',
						handler:function(){
							var f = jhdForm.getForm();
							if (f.isValid()) {
								jhdStore.load({params:f.getValues()});
							}
						}
					}]
				},{
					width:100,
					items:[{
						width:75,
						xtype:"button",
						text:'删除',
						handler:function(){
							var record= jhdGrid.getSelectionModel().getSelected();
							if(!record){
			                	Ext.Msg.alert('信息提示','请选择要删除的进货单');  
							}else{
								Ext.MessageBox.confirm('删除提示', '删除进货单后将无法恢复，是否删除？', function(c) {
								   if(c=='yes'){
								   		Ext.Ajax.request({
								   			url : "jh_deleteJhd.do",
								   			params:{ djid : record.get("djid") },
								   			success : function() {
								   				jhdStore.reload();
								   				jhspStore.removeAll();
								   			}
								   		});
								    }
								});
							}
						}
					}]
				}]
			}]
		}]
	});
	
	var JhdObj = [
		{ name:'djid', type:'string'},
		{ name:'gysid', type:'int'},
		{ name:'gysname', type:'string'},
		{ name:'riqi', type:'date', mapping : 'riqi.time', dateFormat : 'time' },
		{ name:'yfje', type:'string'},
		{ name:'username', type:'string'},
		{ name:'bz', type:'string'}
	];
	
	//进货单数据
	var jhdStore = new Ext.data.JsonStore({
	    url: "../jh_findDjByParams.do?tab=Jhd",
	    root: 'root',
	    totalProperty: 'total',
	    fields: JhdObj
	});
	
	//进货单列表
    var jhdGrid = new Ext.grid.GridPanel({
        store: jhdStore,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[new Ext.grid.RowNumberer(),
				{header: '供应商名称', width: 120, sortable:true, dataIndex: 'gysname'},
	            {header: '日期', width: 80, sortable:true, align:'center', renderer : Ext.util.Format.dateRenderer('Y-m-d'),  dataIndex: 'riqi'},
	            {header: '单号', width: 120, sortable:true, align:'center', dataIndex: 'djid'},
	            {header: '类型', width: 100, sortable:true, align:'center', dataIndex: 'type',
	            	renderer : function() {return "进货入库"}
	            },
	            {header: '进货金额', width: 100, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'yfje'},
	            {header: '操作员', width: 80, sortable:true, align:'center', dataIndex: 'username'},
	            {header: '备注', width: 220, dataIndex: 'bz'}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        margins:'10 20 0 20',
        style:'border:1px solid',
		region:'center',
        iconCls:'',
        
        listeners:{
        	rowclick:function(a,b){
        		jhspStore.load({params:{djid:jhdStore.getAt(b).get("djid")}});
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
	
	//进货商品数据
	var jhspStore = new Ext.data.JsonStore({
	    url: "../jh_findDjspByParams.do?tab=Jhdsp&info=jhd",
	    root: 'root',
	    totalProperty: 'total',
	    fields: SpxxObj
	});
	
	//进货商品列表
    var jhspGrid = new Ext.grid.GridPanel({
    	id:'djspGrid',
        store: jhspStore,
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
			title:'进货单据查询',
			iconCls:'menu-13',
			layout:'border',
			items:[jhdForm,jhdGrid,jhspGrid]
		}],
		listeners:{
			render:function(){
			}
		}
	});

});
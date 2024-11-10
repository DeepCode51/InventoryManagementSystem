/*!
 * 商品销售统计
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var v_start=0, v_limit=20;
	var date = new Date();
	date.setMonth(date.getMonth()-1);
	//商品类别树窗口
    var splbTreeWindow = new Ext.Window({
		width:200,
		height:300,
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
	            draggable: false,
	            id: "0"
	        },
	        listeners:{
	        	click:function(n){
	        		splbTreeWindow.hide();
	        		djmxForm.getForm().findField("lbid").setValue(n.id);
					djmxForm.getForm().findField("lbname").setValue(n.text);
	        	}
	        }
		}],
		listeners:{
			beforeshow:function(){
				var xy = Ext.getCmp("spcgtj_splb").getPosition();
				splbTreeWindow.setPosition(xy[0],xy[1]+25);
			},
        	show:function(){
        		this.items.get(0).getRootNode().expand();
        	}
        }
	});
	//查询表单
	var djmxForm = new Ext.FormPanel({
		region:'north',
		height: 80,
		border : false,
		layout : 'form',
		padding : '5 20 0 20',
		items:[{
			id:"khfieldset",
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
					width:250,
					items:[{
						id:'spcgtj_splb',
						labelWidth:100,
						xtype:"textfield",
						name:'lbname',
						fieldLabel:"商品类别",
						readOnly:true,
						value:'所有类别',
						anchor:"90%",
						listeners:{
							focus:function(){
								splbTreeWindow.show();
							}
						}
					}]
				},{
					width:250,
					items:[{
						labelWidth:100,
						xtype:"textfield",
						name:'search',
						fieldLabel:"商品编号或名称",
						anchor:"90%"
					}]
				},{
					width:100,
					items:[{
						width:75,
						xtype:"button",
						text:'查询',
						handler:function(){
							var f = djmxForm.getForm();
							if (f.isValid()) {
								djmxStore.load({params:f.getValues()});
							}
						}
					}]
				}]
			}]
		},{
			xtype : "hidden",
		    name : "lbid",
		    value : '0'
		}]
	});
	
	var DjxmObj = [
		{ name:'id',type:'string'},
		{ name:'djid',type:'string'},
		{ name:'riqi', type:'date', mapping : 'riqi.time', dateFormat : 'time'},
		{ name:'gyskhid',type:'int'},
		{ name:'gyskhname',type:'string'},
		{ name:'lbid',type:'int'},
		{ name:'lbname',type:'string'},
		{ name:'spid',type:'string'},
		{ name:'spname',type:'string'},
		{ name:'spdw',type:'string'},
		{ name:'spxinghao',type:'string'},
		{ name:'dj',type:'double'},
		{ name:'sl',type:'int'},
		{ name:'zj',type:'double'}
	];
	
	//销售单据
	var djmxStore = new Ext.data.JsonStore({
	    url: "../tongji_findSpxstj.do",
	    root: 'root',
	    totalProperty: 'total',
	    fields: DjxmObj,
	    sortInfo: {field: "riqi", direction: "DESC"}
	});
	
	//进货单列表
    var djmxGrid = new Ext.grid.GridPanel({
        store: djmxStore,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[new Ext.grid.RowNumberer(),
	            {header: '单号', width: 120, sortable:true, align:'center', dataIndex: 'djid'},
	            {header: '日期', width: 80, sortable:true, align:'center', renderer : Ext.util.Format.dateRenderer('Y-m-d'),  dataIndex: 'riqi'},
				{header: '供应商', width: 120, sortable:true, dataIndex: 'gyskhname'},
				{header: '商品编号', width: 80, sortable:true, dataIndex: 'spid'},
				{header: '商品名称', width: 130, sortable:true, dataIndex: 'spname'},
				{header: '商品型号', width: 80, sortable:true, dataIndex: 'spxinghao'},
				{header: '商品类别', width: 80, sortable:true, dataIndex: 'lbname'},
				{header: '单位', width: 60, sortable:true, dataIndex: 'spdw'},
				{header: '单价', width: 60, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'dj'},
				{header: '数量', width: 60, sortable:true, align:'center', dataIndex: 'sl'},
	            {header: '总金额', width: 100, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'zj'}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        margins:'10 20 0 20',
        style:'border:1px solid',
		region:'center',
        iconCls:''
    });
	
	//布局
    new Ext.Viewport({
		layout:'fit',
		items:[{
			frame:true,
			title:'商品销售统计',
			iconCls:'menu-41',
			layout:'border',
			items:[djmxForm,djmxGrid]
		}],
		listeners:{
			render:function(){
			}
		}
	});

});
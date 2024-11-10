/*!
 * 库存管理
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	var v_start=0,v_limit=20;
	
	//商品信息
	var SpxxObj = [
		{ name:'spid', type:'string'},
		{ name:'spname', type:'string'},
		{ name:'tiaoma', type:'string'},
		{ name:'xinghao', type:'string'},
		{ name:'dw', type:'string'},
		{ name:'jhprice', type:'string'},
		{ name:'chprice', type:'string'},
		{ name:'minnum', type:'string'},
		{ name:'csname', type:'string'},
		{ name:'bz', type:'string'},
		{ name:'lbid', type:'int'},
		{ name:'lbname', type:'string'}
	];
	
	//库存
	var KcObj = [
		{ name:'sl', type:'string'},
		{ name:'cbj', type:'double'},
		{ name:'zj', type:'double'},
		{ name:'spid', type:'string'},
		{ name:'lbname', type:'string'},
		{ name:'spname', type:'string'},
		{ name:'xinghao', type:'string'},
		{ name:'dw', type:'string'}
	];
	
	//商品信息数据源
	var spxxStore = new Ext.data.JsonStore({
		autoLoad:true,
	    url: 'spxx_findKcPageSpxx.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields: SpxxObj,
	    listeners:{beforeload:function(a){a.baseParams={start:v_start, limit:v_limit};}}
	});
	
	//期初库存数据源
	var kcStore = new Ext.data.JsonStore({
	    url: 'kc_findPageKc.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields: KcObj,
	    listeners:{beforeload:function(a){a.baseParams={start:v_start, limit:v_limit};}}
	});
	
	//商品信息列表
	var spxxGrid = new Ext.grid.GridPanel({
        store: spxxStore,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[new Ext.grid.RowNumberer(),
	            {header: '商品编号', width: 100, sortable:true, dataIndex: 'spid'},
	            {header: '商品名称', width: 150, sortable:true, dataIndex: 'spname'},
	            {header: '单位', width: 60, sortable:true, dataIndex: 'dw'},
	            {header: '商品型号', width: 80, sortable:true, dataIndex: 'xinghao'},
	            {header: '类别', width: 80, sortable:true, dataIndex: 'lbname'}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
		frame : true,
        title:'商品信息',
        iconCls:'menu-53',
        
        tbar:[{
        	xtype:"label",
        	width:20
        },{
			xtype:"tbtext",
			text:"商品编号或名称:"
		},{
			xtype:"textfield",
			enableKeyEvents:true,
			listeners:{
				keyup:function(btn){
					var search = btn.getValue();
					spxxStore.load({params:{search:search}});
				}
			}
		},' ',{
        	text:'增加到仓库',
        	iconCls:'btn-add',
        	handler: function(){
        		var record= spxxGrid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert('信息提示','请选择先商品');
				}else{
	        		addWindow.show();
	        		addForm.getForm().reset();
        			addForm.getForm().loadRecord(record);
        			addForm.getForm().findField("addupdate").setRawValue("add");
        			addForm.getForm().findField("jhprice").setRawValue(zhMoney(record.get("jhprice")));
        			addForm.getForm().findField("cbj").setRawValue(record.get("jhprice"));
				}
        	}
        }],
        
        bbar: new Ext.PagingToolbar({
            pageSize: v_limit,
            store: spxxStore,
            displayInfo: true
        }),
        viewConfig : {
            forceFit: true,
            scrollOffset: 2
        },
        
        listeners:{
        	rowdblclick:function(){
        		var record= spxxGrid.getSelectionModel().getSelected(); 
				if(record){
	        		addWindow.show();
	        		addForm.getForm().reset();
        			addForm.getForm().loadRecord(record);
        			addForm.getForm().findField("addupdate").setRawValue("add");
        			addForm.getForm().findField("jhprice").setRawValue(zhMoney(record.get("jhprice")));
        			addForm.getForm().findField("cbj").setRawValue(record.get("jhprice"));
				}
        	}
        }
    });	
	
	//期初库存列表
    var kcGrid = new Ext.grid.GridPanel({
        store: kcStore,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[new Ext.grid.RowNumberer(),
	            {header: '商品编号', width: 100, sortable:true, dataIndex: 'spid'},
	            {header: '商品名称', width: 150, sortable:true, dataIndex: 'spname'},
	            {header: '单位', width: 60, sortable:true, dataIndex: 'dw'},
	            {header: '商品型号', width: 80, sortable:true, dataIndex: 'xinghao'},
	            {header: '类别', width: 80, sortable:true, dataIndex: 'lbname'},
	            {header: '库存数量', width: 80, sortable:true, align:'center', dataIndex: 'sl'},
	            {header: '成本价', width: 80, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'cbj'},
	            {header: '库存金额', width: 80, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'zj'}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
		frame : true,
        title:'期初库存',
        iconCls:'menu-54',
        
        tbar:[{
        	xtype:"label",
        	width:20
        },{
        	text:'修改数量或成本价',
        	iconCls:'btn-edit',
        	handler: function(){
        		var record= kcGrid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要修改的数据');  
				}else{
	        		addWindow.show();
					addForm.getForm().reset();
        			addForm.getForm().loadRecord(record);
        			addForm.getForm().findField("addupdate").setRawValue("update");
        			addForm.getForm().findField("jhprice").setRawValue(zhMoney(record.get("cbj")));
				}
        	}
        },{
        	text:'删除',
        	iconCls:'btn-remove',
        	handler: function(){
        		var record= kcGrid.getSelectionModel().getSelected();
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要删除的数据');  
				}else{
					Ext.MessageBox.confirm('删除提示', '是否删除该记录？', function(c) {
					   if(c=='yes'){
					   		Ext.Ajax.request({
					   			url : "kc_deleteKc.do",
					   			params:{ spid : record.get("spid") },
					   			success : function(response) {
					   				var Result=Ext.decode(response.responseText);
					   			    if(!Result.success){
					   			    	Ext.Msg.alert("信息提示",Result.error);
					   			    	return;
					   			    }
					   				kcStore.reload();
					   				spxxStore.reload();
					   			}
					   		});
					    }
					});
				}
        	}
        }],
        
        bbar: new Ext.PagingToolbar({
            pageSize: v_limit,
            store: kcStore,
            displayInfo: true
        }),
        viewConfig : {
            forceFit: true,
            scrollOffset: 2
        },
        
        listeners:{
        	rowdblclick:function(){
        		var record= kcGrid.getSelectionModel().getSelected(); 
				if(record){
	        		addWindow.show();
					addForm.getForm().reset();
        			addForm.getForm().loadRecord(record);
        			addForm.getForm().findField("addupdate").setRawValue("update");
        			addForm.getForm().findField("jhprice").setRawValue(zhMoney(record.get("cbj")));
				}
        	}
        }
    });
	
	//期初库存表单
	var addForm = new Ext.FormPanel({
		layout : 'form',
		baseCls: 'x-plain',
		border : false,
		labelAlign:'right',
		labelWidth:60,
		padding : 10,
		layout:'column',
		items:[{
			xtype:"fieldset",
			title:"商品信息",
			frame:true,
			layout:"column",
			padding:'0 20 0 15',
			columnWidth:1,
			items:[{
				xtype:"container",
				autoEl:"div",
				columnWidth:0.5,
				labelAlign:'right',
				layout:"form",
				items:[{
						xtype:"textfield",
						style:'background:#F6F6F6',
						fieldLabel:"商品编号",
						name:'spid',
						readOnly:true,
						anchor:"100%"
				},{
						xtype:"textfield",
						style:'background:#F6F6F6',
						name:'dw',
						fieldLabel:"商品单位",
						readOnly:true,
						anchor:"100%"
				}]
			},{
				xtype:"container",
				autoEl:"div",
				columnWidth:0.5,
				layout:"form",
				items:[{
						xtype:"textfield",
						style:'background:#F6F6F6',
						name:'spname',
						fieldLabel:"商品名称",
						readOnly:true,
						anchor:"100%"
				},{
						xtype:"textfield",
						style:'background:#F6F6F6',
						name:'jhprice',
						fieldLabel:"成本价",
						readOnly:true,
						value:"￥0.00",
						anchor:"100%"
				}]
			}]
		},{
			columnWidth:0.5,
			xtype:"container",
			autoEl:"div",
			style:'padding:10 0 0 15',
			layout:"form",
			items:[{
					xtype:"numberfield",
					name:'sl',
					fieldLabel:"期初数量",
					allowBlank : false,
					allowDecimals:false,
					maxLength :10,
					value:0,
					anchor:"100%"
			}]
		},{
			columnWidth:0.5,
			xtype:"container",
			autoEl:"div",
			style:'padding:10 20 0 0',
			layout:"form",
			items:[{
					xtype:"numberfield",
					name:'cbj',
					fieldLabel:"成本价",
					allowBlank : false,
					maxLength :10,
					value:0,
					anchor:"100%"
			}]
		},{
			xtype : 'hidden',
			name : 'addupdate'
		}]
	});
	
	//增加期初库存窗口
    var addWindow = new Ext.Window({
		title : '库存商品期初建账',
		width:400,
		height:230,
		closeAction:'hide',
		modal : true,
		resizable : false,
		layout : 'fit',
		buttonAlign : 'center',
		items : [addForm],
		buttons : [{
			text : '保存',
			handler : function() {
				if (addForm.getForm().isValid()) {
					addForm.getForm().submit({
						url : 'kc_saveOrUpdateKc.do',
						success : function(form, action) {
							addWindow.hide();
							kcStore.reload();
							spxxStore.reload();
						},
						failure : function(form, action) {
							if(action.result&&action.result.errors){
								addWindow.hide();
								Ext.Msg.alert('信息提示',action.result.errors);
							}else{
								Ext.Msg.alert('信息提示','连接失败');
							}
						},
						waitTitle : '提交',
						waitMsg : '正在保存数据，稍后...'
					});
				}
			}
		}, {
			text : '取消',
			handler : function() {
				addWindow.hide();
			}
		}]
	});
    
	//布局
    new Ext.Viewport({
		layout:'fit',
		items:[{
 			frame:true,
			layout:'border',
			items:[{
				region:'west',
				width:'40%',
				layout:'fit',
				items:spxxGrid
			},{
				region:'center',
				padding:'0 0 0 5',
				layout:'fit',
				items:kcGrid
			}]
		}],
		listeners:{
			render:function(){
				kcStore.load();
			}
		}
	});

});
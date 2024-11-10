/*!
 * 供应商管理
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var GysObj = [
		{ name:'gysid', type:'int'},
		{ name:'name', type:'string'},
		{ name:'lxren', type:'string'},
		{ name:'lxtel', type:'string'},
		{ name:'address', type:'string'},
		{ name:'bz', type:'string'}
	];
	
	var store = new Ext.data.JsonStore({
	    url: 'gys_findPageGys.do',
	    root: 'root',
	    totalProperty: 'total',
	    autoLoad: {params:{start:0, limit:15}},
	    fields: GysObj
	});
	
    var grid = new Ext.grid.GridPanel({
        store: store,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[new Ext.grid.RowNumberer(),
	            {header: '供应商名称', width: 150,align:'center', dataIndex: 'name'},
	            {header: '联系人', width: 150,align:'center', dataIndex: 'lxren'},
	            {header: '联系电话', width: 150, align:'center',dataIndex: 'lxtel'},
	            {header: '联系地址', width: 150, align:'center',dataIndex: 'address'},
	            {id:'gysbz',header: '备注', align:'center',dataIndex: 'bz'}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        autoExpandColumn: 'gysbz', //自动扩展列
		loadMask : true,	//加载时的遮罩
		frame : true,
        title:'供应商管理',
        iconCls:'menu-51',
        
        tbar:[{
        	text:'增加',
        	iconCls:'btn-add',
        	handler: function(){
        		addWindow.show();
        		addForm.getForm().reset();
        	}
        },'-',{
        	text:'修改',
        	iconCls:'btn-edit',
        	handler: function(){
        		var record= grid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要修改的供应商');
				}else{
	        		addWindow.show();
					addForm.getForm().loadRecord(record);
				}
        	}
        },'-',{
        	text:'删除',
        	iconCls:'btn-remove',
        	handler: function(){
        		var record= grid.getSelectionModel().getSelected();
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要删除的供应商');  
				}else{
					Ext.MessageBox.confirm('删除提示', '是否删除该供应商？', function(c) {
					   if(c=='yes'){
					   		Ext.Ajax.request({
					   			url : "gys_deleteGys.do",
					   			params:{ gysid : record.get("gysid") },
					   			success : function() {
					   				store.reload();
					   			}
					   		});
					    }
					});
				}
        	}
        }],
        
        bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: store,
            displayInfo: true
        })
    });

    var addForm = new Ext.FormPanel({
		layout : 'form',
		baseCls:'x-plain',
		labelWidth:70,
		border : false,
		padding : '15 10 0 8',
		defaults : {
			anchor : '100%',
			xtype : 'textfield'
		},
		items:[{
				name:'name',
				fieldLabel:'供应商名称',
				maxLength :50,
				allowBlank : false
			},{
				name:'lxren',
				fieldLabel:'联系人',
				maxLength :30
			},{
				name:'lxtel',
				fieldLabel:'联系电话',
				maxLength :30
			},{
				name:'address',
				fieldLabel:'联系地址',
				maxLength :50
			},{
				xtype:'textarea',
				name:'bz',
				fieldLabel:'备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注',
				height:80,
				maxLength :200
			},{
				xtype : 'hidden',
			    name : 'gysid'
			}]
	});
    
    var addWindow = new Ext.Window({
		title : '添加窗口',
		width:400,
		height:300,
		closeAction:'hide',
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [addForm],
		buttons : [{
			text : '保存',
			handler : function() {
				if (addForm.getForm().isValid()) {
					addForm.getForm().submit({
						url : 'gys_saveOrUpdateGys.do',
						success : function(form, action) {
							Ext.Msg.alert('信息提示',action.result.message);
							addWindow.hide();
							store.reload();
						},
						failure : function(form, action) {
							if(action.result.errors){
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
    
    new Ext.Viewport({
		layout:'border',
		items:[{
			region:'center',
			layout:'fit',
			border:false,
			items:grid
		}]
	});

});
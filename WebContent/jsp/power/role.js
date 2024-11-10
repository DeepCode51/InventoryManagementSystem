/*!
 * 角色管理
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var RoleObj = [
		{ name:'roleid', type:'int'},
		{ name:'rolename', type:'string'},
		{ name:'bz', type:'string'}
	];
	
	var store = new Ext.data.JsonStore({
	    url: 'role_findPageRole.do',
	    root: 'root',
	    totalProperty: 'total',
	    autoLoad: {params:{start:0, limit:15}},
	    fields: RoleObj
	});

    var grid = new Ext.grid.GridPanel({
        store: store,
      	columns : [new Ext.grid.RowNumberer(), {
			header : '角色名称',
			width : 200,
			align : 'center',
			menuDisabled : true,
			dataIndex : 'rolename'
		}, {
			id : 'rolebz',
			header : '角色说明',
			align : 'center',
			menuDisabled : true,
			dataIndex : 'bz'
		},{
            xtype: 'actioncolumn',
            header : '权限设置',
            width: 150,
            align : 'center',
			menuDisabled : true,
            items: [{
                icon   : '../../img/btn/edit2.gif',  // Use a URL in the icon config
                tooltip: '权限设置',
                handler: function(grid, rowIndex, colIndex) {
                    var record = store.getAt(rowIndex);
                    var roleid = record.get('roleid')
                	roleMenuWindow.show(null,function(){
						tree.loader.dataUrl = '../role_findRoleMenu.do?roleid='+ roleid
						tree.getLoader().load(tree.root, function() {
							tree.root.expand(true, true);
						});
                	});
                	roleMenuWindow.roleid = roleid;
                }
            }]
        }],
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        autoExpandColumn: 'rolebz', //自动扩展列
		loadMask : true,	//加载时的遮罩
		frame : true,
        title:'角色管理',
        iconCls:'menu-61',
        
        tbar:['->',{
        	text:'增加',
        	iconCls:'btn-add',
        	handler: function(){
        		roleWindow.show();
        		roleForm.getForm().reset();
        	}
        },'-',{
        	text:'修改',
        	iconCls:'btn-edit',
        	handler: function(){
        		var record= grid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要修改的数据');
				}else{
	        		roleWindow.show();
					roleForm.getForm().loadRecord(record);
				}
        	}
        },'-',{
        	text:'删除',
        	iconCls:'btn-remove',
        	handler: function(){
        		var record= grid.getSelectionModel().getSelected();
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要删除的数据');  
				}else{
					Ext.MessageBox.confirm('删除提示', '是否删除该角色？', function(c) {
					   if(c=='yes'){
					   		Ext.Ajax.request({
					   			url : "../role_deleteRole.do",
					   			params:{ roleid : record.get("roleid") },
					   			success : function(response) {
					   			    var Result=Ext.decode(response.responseText);
					   			    if(!Result.success){
					   			    	Ext.Msg.alert("信息提示",Result.error);
					   			    	return;
					   			    }
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

    var roleForm = new Ext.FormPanel({
		layout : 'form',
		baseCls:'x-plain',
		labelWidth:60,
		border : false,
		padding : '20 10 0 8',
		defaults : {
			anchor : '100%',
			xtype : 'textfield'
		},
		items:[{
				name:'rolename',
				fieldLabel:'角色名称',
				maxLength :20,
				allowBlank : false
			},{
				xtype:'textarea',
				name:'bz',
				fieldLabel:'角色说明',
				height:80,
				maxLength :100
			},{
				xtype : 'hidden',
			    name : 'roleid'
			}]
	});
    
    var roleWindow = new Ext.Window({
		title : '添加窗口',
		width:400,
		height:230,
		closeAction:'hide',
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [roleForm],
		buttons : [{
			text : '保存',
			handler : function() {
				if (roleForm.getForm().isValid()) {
					roleForm.getForm().submit({
						url : 'role_saveOrUpdateRole.do',
						success : function(form, action) {
							Ext.Msg.alert('信息提示',action.result.message);
							roleWindow.hide();
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
				roleWindow.hide();
			}
		}]
	});
	
	var tree = new Ext.tree.TreePanel({
        autoScroll:true,
        animate:false,
        enableDD:false,
        rootVisible: false,
        containerScroll: true,
        checkModel : 'cascade',
        frame:true,
        root: new Ext.tree.AsyncTreeNode({
			draggable : false,
			checked : false,
			id : '-1'
		}),
        loader : new Ext.tree.TreeLoader({
			baseAttrs : {
				uiProvider : Ext.tree.TreeCheckNodeUI
			},
			dataUrl : '..',
			baseParams : {}
		})
	});
	
	var roleMenuWindow = new Ext.Window({
		layout : 'fit',
		width : 400,
		height : 500,
		title : '分配功能',
		closeAction : 'hide',
		modal : true,
		items : [tree],
		buttons : [{
			text : '提交',
			pressed : true,
			enableToggle : true,
			iconCls : 'grid-save',
			handler : function() {
				var menuids = "";
				var childNodes = tree.getChecked();
				for (var i = 0; i < childNodes.length; i++) {
					if(i!=0){
						menuids += ",";	
					}
					menuids += childNodes[i].id;
				}
				Ext.Ajax.request({
		   			url : "role_saveRoleMenu.do",
		   			params:{ 
		   				roleid : roleMenuWindow.roleid, 
		   				menuids : menuids 
		   			},
		   			success : function(response, action) {
						Ext.Msg.alert('信息提示',response.responseText);
		   				roleMenuWindow.hide();
		   			}
		   		});
			}
		}, {
			text : '关闭',
			pressed : true,
			enableToggle : true,
			iconCls : 'grid-close',
			handler : function() {
				roleMenuWindow.hide();
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
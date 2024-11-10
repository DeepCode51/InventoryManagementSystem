/*!
 * 用户管理
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var UuserObj = [
		{ name:'userid', type:'int'},
		{ name:'logincode', type:'string'},
		{ name:'password', type:'string'},
		{ name:'username', type:'string'},
		{ name:'roleid', type:'int'},
		{ name:'rolename', type:'string'},
		{ name:'state', type:'int'},
		{ name:'bz', type:'string'}
	];
	
	var store = new Ext.data.JsonStore({
	    url: 'user_findPageUser.do',
	    root: 'root',
	    totalProperty: 'total',
	    autoLoad: {params:{start:0, limit:15}},
	    fields: UuserObj
	});
	
    var grid = new Ext.grid.GridPanel({
        store: store,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[new Ext.grid.RowNumberer(),
	            {header: '登录账号', width: 150,align:'center', dataIndex: 'logincode'},
	            {header: '用户姓名', width: 150,align:'center', dataIndex: 'username'},
	            {header: '角色', width: 150, align:'center',dataIndex: 'rolename'},
	            {id:'userbz',header: '用户说明', align:'center',dataIndex: 'bz'}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        autoExpandColumn: 'userbz', //自动扩展列
		loadMask : true,	//加载时的遮罩
		frame : true,
		title:'用户管理',
        iconCls:'menu-62',
        
        tbar:['->',{
        	text:'增加',
        	iconCls:'btn-add',
        	handler: function(){
        		uWindow.show();
        		uForm.getForm().reset();
        		uForm.getForm().findField("logincode").setDisabled(false);
        	}
        },'-',{
        	text:'修改',
        	iconCls:'btn-edit',
        	handler: function(){
        		var record= grid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要修改的数据');
				}else{
	        		uWindow.show();
					uForm.getForm().loadRecord(record);
					uForm.getForm().findField("logincode").setDisabled(true);
					if(record.get("roleid")==0)
						uForm.getForm().findField("roleid").setValue();
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
					if(record.get("logincode") == "admin"){
						Ext.Msg.alert('信息提示','admin用户不能被删除');
						return;
					}
					Ext.MessageBox.confirm('删除提示', '是否删除该用户？', function(c) {
					   if(c=='yes'){
					   		Ext.Ajax.request({
					   			url : "user_deleteUser.do",
					   			params:{ userid : record.get("userid") },
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
    
    var roleStore = new Ext.data.JsonStore({
	    url: 'role_findRoleType.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields : ["value", "text"]
	});
	
	roleStore.load();

    var uForm = new Ext.FormPanel({
		layout : 'form',
		baseCls:'x-plain',
		labelWidth:60,
		border : false,
		padding : '15 10 0 8',
		defaults : {
			anchor : '100%',
			xtype : 'textfield'
		},
		items:[{
				name:'logincode',
				fieldLabel:'登录账号',
				maxLength :20,
				allowBlank : false
			},{
				inputType: 'password',
				name:'password',
				fieldLabel:'密码',
				maxLength :20,
				allowBlank : false
			},{
				name:'username',
				fieldLabel:'用户姓名',
				maxLength :20,
				allowBlank : false
			},{
				xtype:'combo',
				hiddenName:'roleid',
				fieldLabel:'角色',
				mode: 'local',
				triggerAction: 'all',
				valueField :'value',
				displayField: 'text',
				emptyText: '请选择',
				allowBlank : false,
				editable : false,
				store : roleStore
			},{
				xtype:'textarea',
				name:'bz',
				fieldLabel:'用户说明',
				height:80,
				maxLength :100
			},{
				xtype : 'hidden',
			    name : 'userid'
			}]
	});
    
    var uWindow = new Ext.Window({
		title : '添加窗口',
		width:400,
		height:300,
		closeAction:'hide',
		modal : true,
		layout : 'fit',
		buttonAlign : 'center',
		items : [uForm],
		buttons : [{
			text : '保存',
			handler : function() {
				if (uForm.getForm().isValid()) {
					uForm.getForm().submit({
						url : 'user_saveOrUpdateUser.do',
						success : function(form, action) {
							Ext.Msg.alert('信息提示',action.result.message);
							uWindow.hide();
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
				uWindow.hide();
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
/*!
 * 商品报溢
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
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
	
	//报溢单商品数据
	var bydspStore = new Ext.data.JsonStore({
	    root: 'root',
	    totalProperty: 'total',
	    fields: SpxxObj
	});
	
	//报溢单商品列表
    var bydspGrid = new Ext.grid.GridPanel({
    	id:'djspGrid',
        store: bydspStore,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[new Ext.grid.RowNumberer(),
				{header: '商品编号', width: 100, sortable:true, dataIndex: 'spid'},
	            {header: '商品名称', width: 200, sortable:true, dataIndex: 'spname'},
	            {header: '商品型号', width: 150, sortable:true, dataIndex: 'xinghao'},
	            {header: '单位', width: 100, sortable:true, dataIndex: 'dw'},
	            {header: '采购价', width: 100, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'cbj'},
	            {header: '数量', width: 100, sortable:true, align:'center', dataIndex: 'sl'},
	            {header: '总金额', width: 100, sortable:true, align:'right', renderer:zhMoney, dataIndex: 'zj'}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
        margins:'20',
        style:'border:1px solid',
		region:'center',
        iconCls:'',
        
        tbar:[{
        	text:'添加',
        	iconCls:'btn-add',
        	handler: function(){
        		bsspWindow.show();
        	}
        },'-',{
        	text:'修改',
        	iconCls:'btn-edit',
        	handler: function(){
        		var record= bydspGrid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要修改的商品');
				}else{
	        		addJhWindow.show();
	        		addJhWindow.buttons[0].setVisible(false);
	        		record.set("update","true");
					addJhForm.getForm().loadRecord(record);
				}
        	}
        },'-',{
        	text:'删除',
        	iconCls:'btn-remove',
        	handler: function(){
        		var record= bydspGrid.getSelectionModel().getSelected();
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要删除的商品');  
				}else{
					Ext.MessageBox.confirm('删除提示', '是否删除该记录？', function(c) {
						bydspStore.remove(record);
					});
				}
        	}
        }],
        
        listeners:{
        	rowdblclick:function(){
        		var record= bydspGrid.getSelectionModel().getSelected(); 
				if(record){
	        		addJhWindow.show();
	        		addJhWindow.buttons[0].setVisible(false);
	        		record.set("update","true");
					addJhForm.getForm().loadRecord(record);
				}
        	}
        }
    });
		
	//销售表单
	var bydForm = new Ext.FormPanel({
		region:'north',
		height: 110,
		border : false,
		layout : 'form',
		labelWidth:60,
		padding : 20,
		items:[{
			id:"bydfieldset",
			xtype:"fieldset",
			title:"单号：",
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
					columnWidth:0.2,
					items:[{
						id:'bydriqi',
						xtype:"datefield",
						name:'riqi',
						fieldLabel:"开单日期",
						format:'Y-m-d',
						allowBlank : false,
						value:new Date(),
						anchor:"90%",
						listeners:{
							select : function(a,b){
								getCode(b.format("Y-m-d"));
							}
						}
					}]
				},{
					columnWidth:0.6,
					items:[{
						xtype:"textfield",
						name:'bz',
						fieldLabel:"备&nbsp;&nbsp;&nbsp;注",
						anchor:"80%",
						maxLength :200
					}]
				},{
					columnWidth:.2,
					items:[{
						width:75,
						xtype:"button",
						text:'保存',
						handler:function(){
							var f = bydForm.getForm();
							if (f.isValid()) {
								if(bydspStore.getCount()<=0){
									Ext.Msg.alert("信息提示","请添加商品");
									return;
								}
								var jsonArray = [];
						        bydspStore.each(function(item) {
						            jsonArray.push(item.data);
						        });
								f.submit({
									url : 'bsy_saveOrUpdateByd.do',
									params :{djsps:Ext.encode(jsonArray) },
									success : function(form, action) {
										Ext.Msg.alert("信息提示","数据保存成功",function(){
											getCode();
											f.findField("bz").setValue("");
											bydspStore.removeAll();
										});
									},
									failure : function(form, action) {
										if(action.result && action.result.errors){
											Ext.Msg.alert('信息提示',action.result.errors);
										}else{
											Ext.Msg.alert('信息提示','连接失败');
										}
									}
								});
							}
						}
					}]
				}]
			},{
				xtype:'hidden',
				name:'djid'
			},{
				xtype:'hidden',
				name:'khname'
			},{
				xtype:'hidden',
				name:'kfname'
			},{
				xtype:'hidden',
				name:'ygname'
			}]
		}]
	});
	//设置单据编号
	var getCode = function(){
		var ymd = Ext.getCmp("bydriqi").getValue().format("Y-m-d");
		Ext.Ajax.request({
   			url : "bsy_getDjCode.do",
   			params : {tab:'Byd',ymd:ymd},
   			success : function(o) {
   				if(o.responseText){
   					var code = "BY"+o.responseText;
   					Ext.getCmp("bydfieldset").setTitle("单号："+code);
   					bydForm.getForm().findField("djid").setValue(code);
   				}
   			}
   		});
	};
	
	//布局
    new Ext.Viewport({
		layout:'fit',
		items:[{
			frame:true,
			title:'商品报溢',
			iconCls:'menu-32',
			layout:'border',
			items:[bydForm,bydspGrid]
		}],
		listeners:{
			render:function(){
				getCode();
			}
		}
	});
	

	

});
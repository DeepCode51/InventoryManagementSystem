/*!
 * 进货入库
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var SpxxObj = [
		{ name:'spid', type:'string'},
		{ name:'spname', type:'string'},
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
	
	//商品数据
	var jhspStore = new Ext.data.JsonStore({
	    url: 'spxx_findPageSpxx.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields: SpxxObj,
	    listeners:{beforeload:function(a){a.baseParams={start:v_start, limit:v_limit};}}
	});
	
	//商品列表
    var jhspGrid = new Ext.grid.GridPanel({
    	id:'djspGrid',
        store: jhspStore,
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
        		var record= jhspGrid.getSelectionModel().getSelected(); 
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
        		var record= jhspGrid.getSelectionModel().getSelected();
				if(!record){
                	Ext.Msg.alert('信息提示','请选择要删除的商品');  
				}else{
					Ext.MessageBox.confirm('删除提示', '是否删除该记录？', function(c) {
						var djForm = Ext.getCmp("djForm").getForm();
						var yfje = djForm.findField("yfje").getValue();
						var sfje = djForm.findField("sfje").getValue();
						djForm.findField("yfje").setValue(yfje-record.get("zj"));
						djForm.findField("sfje").setValue(sfje-record.get("zj"));
						jhspStore.remove(record);
					});
				}
        	}
        }],
        
        listeners:{
        	rowdblclick:function(){
        		var record= jhspGrid.getSelectionModel().getSelected(); 
				if(record){
	        		addJhWindow.show();
	        		addJhWindow.buttons[0].setVisible(false);
	        		record.set("update","true");
					addJhForm.getForm().loadRecord(record);
				}
        	}
        }
    });
    
	//供应商下拉数据
    var gysStore = new Ext.data.JsonStore({
		url: 'gys_findGysComb.do',
	    root: 'root',
	    totalProperty: 'total',
	    fields: ['value','text'],
	    listeners:{
	    	load:function(s){
	    		r = s.getAt(0);
	    		if(r){
	    			Ext.getCmp("gyscombo").onSelect(r, 0);
	    		}
	    	}
	    }
	});
	
    //进货表单
	var jhdForm = new Ext.FormPanel({
		id:'djForm',
		region:'north',
		height: 110,
		border : false,
		layout : 'form',
		labelWidth:60,
		padding : 20,
		items:[{
			id:"jhdfieldset",
			xtype:"fieldset",
			title:"单号：",
			padding:'0 20 0 15',
			items:[{
				layout:"column",
				defaults:{
					xtype:"container",
					autoEl:"div",
					columnWidth:0.2,
					labelAlign:'right',
					layout:"form"
				},
				items:[{
					items:[{
							width:100,
							id:'gyscombo',
							xtype:'combo',
							hiddenName:'gysid',
							fieldLabel:'供应商',
							mode: 'local',
							triggerAction: 'all',
							valueField :'value',
							displayField: 'text',
							allowBlank : false,
							editable : false,
							store : gysStore,
							listeners:{
								select : function(a,b){
									jhdForm.getForm().findField("gysname").setValue(b.data.text);
								}
							}
					}]
				},{
					items:[{
						xtype:"numberfield",
						name:'yfje',
						fieldLabel:"应付金额",
						style:"background:#F6F6F6",
						readOnly:true,
						anchor:"90%",
						maxLength :200
					}]
				},{
					items:[{
						xtype:"numberfield",
						name:'sfje',
						fieldLabel:"实付金额",
						allowBlank : false,
						anchor:"90%",
						maxLength :200
					}]
				},{columnWidth:0.1},{
					items:[{
						id:'jhriqi',
						xtype:"datefield",
						name:'riqi',
						fieldLabel:"收货日期",
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
				}]
			},{
				layout:"column",
				defaults:{
					xtype:"container",
					autoEl:"div",
					labelAlign:'right',
					layout:"form"
				},
				items:[{
					columnWidth:0.4,
					items:[{
						xtype:"textfield",
						name:'bz',
						fieldLabel:"备&nbsp;&nbsp;&nbsp;注",
						anchor:"95%",
						maxLength :200
					}]
				},{
					columnWidth:0.4,
					items:[{
						xtype:'combo',
						hiddenName:'jystate',
						fieldLabel:'是否付款',
						mode: 'local',
						triggerAction: 'all',
						valueField :'value',
						displayField: 'text',
						allowBlank : false,
						editable : false,
						anchor:"45%",
						value:'1',
						store : new Ext.data.SimpleStore({
						    fields: ['value', 'text'],
						    data : [['1','已付'],['0','未付']]
						})
					}]
				},{
					columnWidth:.2,
					items:[{
						width:75,
						xtype:"button",
						text:'保存',
						handler:function(){
							var f = jhdForm.getForm();
							if (f.isValid()) {
								if(jhspStore.getCount()<=0){
									Ext.Msg.alert("信息提示","请添加商品");
									return;
								}
								var jsonArray = [];
						        jhspStore.each(function(item) {
						            jsonArray.push(item.data);
						        });
								f.submit({
									url : 'jh_saveOrUpdateJhd.do',
									params :{djsps:Ext.encode(jsonArray) },
									success : function(form, action) {
										Ext.Msg.alert("信息提示","数据保存成功",function(){
											getCode();
											f.findField("bz").setValue("");
											f.findField("yfje").setValue("");
											f.findField("sfje").setValue("");
											f.clearInvalid();
											jhspStore.removeAll();
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
				name:'gysname'
			}]
		}]
	});
	//设置单据编号
	var getCode = function(){
		var ymd = Ext.getCmp("jhriqi").getValue().format("Y-m-d");
		Ext.Ajax.request({
   			url : "jh_getDjCode.do",
   			params : {tab:'Jhd',ymd:ymd},
   			success : function(o) {
   				if(o.responseText){
   					var code = "JH"+o.responseText;
   					Ext.getCmp("jhdfieldset").setTitle("单号："+code);
   					jhdForm.getForm().findField("djid").setValue(code);
   				}
   			}
   		});
	};
	
	//布局
    new Ext.Viewport({
		layout:'fit',
		items:[{
			frame:true,
			title:'进货入库',
			iconCls:'menu-11',
			layout:'border',
			items:[jhdForm,jhspGrid]
		}],
		listeners:{
			render:function(){
				getCode();
				gysStore.load();
			}
		}
	});
	

	

});
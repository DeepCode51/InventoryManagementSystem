	var v_lbid="0", v_lbname="", v_start=0, v_limit=20;
	
	//左侧商品类别树
	var splbTree = new Ext.tree.TreePanel({
		title:"商品类别",
		region : "west",
		width : 180,
        minSize: 150,
        maxSize: 300,
        split : true,
		useArrows: true,
        autoScroll:true,
        animate:true,
        enableDD:false,
        containerScroll: true,
        frame:true,
        dataUrl: "splb_findSplbTree.do",
        root: {
            nodeType: "async",
            text: "所有类别",
            draggable: false,
            id: "0"
        },
        buttonAlign : "left",
        buttons:[{
        	text:"新增类别",
        	handler:function(){
        		splbWindow.show();
        		splbForm.getForm().reset();
        	}
        },{
        	text:"删除类别",
        	disabled:true,
        	handler:function(){
        		if(v_lbid){
	        		var node = splbTree.getNodeById(v_lbid);
	        		var pnode = node.parentNode;
					Ext.MessageBox.confirm("删除提示", "是否删除\""+node.text+"\"类别？", function(c) {
					   if(c=="yes"){
					   		Ext.Ajax.request({
					   			url : "splb_deleteSplb.do",
					   			params:{ lbid : v_lbid },
					   			success : function(o) {
					   				if(o.responseText=="false"){
					   					Ext.Msg.alert("信息提示","该类别有商品信息不能删除");
					   				}else{
						   				v_lbid = "0";     //设为默认节点
						   				v_lbname = "";
						   				splbTree.buttons[1].setDisabled(true);  //禁用删除按钮
						   				pnode.removeChild(node);	//删除节点
						   				if(pnode.childNodes.length==0){	//如果无子节点则修改属性
						   					pnode.leaf = true;
						   				}
						   				var wtree = splbTreeWindow.get(0);
						   				wtree.getLoader().load(wtree.root); //更新类别编辑窗口的树
					   				}
					   			}
					   		});
					    }
					});
				}
        	}
        }],
        listeners:{
        	click:function(n){
        		v_lbid = n.id;
        		v_lbname = n.text;
        		//设置删除按钮状态
        		if(!n.leaf||v_lbid=="0"){
        			splbTree.buttons[1].setDisabled(true);
        		}else{
        			splbTree.buttons[1].setDisabled(false);
        		}
        		//更新商品数据
        		spxxStore.load({params:{start:v_start, limit:v_limit,lbid:v_lbid}});
        	}
        }
	});
	
	//商品类别表单
	var splbForm = new Ext.FormPanel({
		layout : "form",
		baseCls: "x-plain",
		labelWidth:60,
		border : false,
		padding : 20,
		items:[{
			xtype:"textfield",
			anchor : "100%",
			name:"lbname",
			fieldLabel:"商品类别",
			allowBlank : false,
			maxLength :20
		},{
			xtype : "hidden",
		    name : "lbid"
		}]
	});
    
	//增加商品类别窗口
    var splbWindow = new Ext.Window({
		title : "增加类别",
		width:250,
		height:140,
		closeAction:"hide",
		modal : true,
		resizable : false,
		layout : "fit",
		buttonAlign : "center",
		items : [splbForm],
		buttons : [{
			text : "保存",
			handler : function() {
				if (splbForm.getForm().isValid()) {
					splbForm.getForm().submit({
						url : "splb_saveOrUpdateSplb.do",
						params:{pid : v_lbid},
						success : function(form, action) {
							splbWindow.hide();
							var id = action.result.message;
							//创建新节点
							var node = new Ext.tree.TreeNode({
								id:id,
								text:form.findField("lbname").getValue(),
								iconCls:"menu-folder",
								leaf:true
							});
							//修改父节点
							var pnode = splbTree.getNodeById(v_lbid);
							pnode.appendChild(node);
							pnode.leaf=false;
							pnode.expand();
							//更新类别编辑窗口的树
							var wtree = splbTreeWindow.get(0);
						   	wtree.getLoader().load(wtree.root); 
						},
						failure : function(form, action) {
							if(action.result.errors){
								Ext.Msg.alert("信息提示",action.result.errors);
							}else{
								Ext.Msg.alert("信息提示","连接失败");
							}
						},
						waitTitle : "提交",
						waitMsg : "正在保存数据，稍后..."
					});
				}
			}
		}, {
			text : "取消",
			handler : function() {
				splbWindow.hide();
			}
		}]
	});

	
	
	var SpxxObj = [
		{ name:"spid", type:"string"},
		{ name:"spname", type:"string"},
		{ name:"tiaoma", type:"string"},
		{ name:"xinghao", type:"string"},
		{ name:"dw", type:"string"},
		{ name:"jhprice", type:"double"},
		{ name:"chprice", type:"double"},
		{ name:"scjj", type:"double"},
		{ name:"kcsl", type:"int"},
		{ name:"minnum", type:"string"},
		{ name:"csname", type:"string"},
		{ name:"bz", type:"string"},
		{ name:"lbid", type:"int"},
		{ name:"lbname", type:"string"}
	];
	
	//商品数据
	var spxxStore = new Ext.data.JsonStore({
	    url: "spxx_findPageSpxx.do",
	    root: "root",
	    totalProperty: "total",
	    fields: SpxxObj,
	    listeners:{beforeload:function(a){a.baseParams={start:v_start, limit:v_limit};}}
	});
	
	//右侧商品列表
    var spxxGrid = new Ext.grid.GridPanel({
        store: spxxStore,
        cm: new Ext.grid.ColumnModel({
			defaults: {	menuDisabled : true},//禁止表头菜单
			columns:[
				{header: "商品编号", width: 85, sortable:true, dataIndex: "spid"},
	            {header: "商品名称", width: 130, sortable:true, dataIndex: "spname"},
	            {header: "商品型号", width: 80, sortable:true, dataIndex: "xinghao"},
	            {header: "单位", width: 50, sortable:true, dataIndex: "dw"},
	            {header: "成本均价", width: 60, sortable:true, align:"right", renderer:zhMoney, dataIndex: "jhprice"},
	            {header: "销售单价", width: 60, sortable:true, align:"right", renderer:zhMoney, dataIndex: "chprice"},
	            {header: "库存数量", width: 60, sortable:true, align:"right", dataIndex: "kcsl"}]
        }),
        stripeRows: true, 	//行分隔符
        columnLines : true, //列分隔符
		frame:true,
		region:"center",
		title:"商品信息",
        iconCls:"",
        
        tbar:[{
        	text:"选择",
        	iconCls:"btn-add",
        	handler: function(){
        		var record= spxxGrid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert("信息提示","请选择商品");
				}else{
	        		addJhWindow.show();
					addJhForm.getForm().loadRecord(record);
					addJhForm.getForm().findField("chprice").setRawValue("￥"+record.get("chprice"));
					addJhForm.getForm().findField("cbj").setRawValue(record.get("chprice"));
				}
        	}
        },"-",{
        	text:"增加",
        	iconCls:"btn-add",
        	handler: function(){
        		addSpWindow.show();
        		addSpForm.getForm().reset();
        		addSpForm.getForm().findField("addupdate").setValue("add");
        		if(v_lbid!="0"){
					addSpForm.getForm().findField("lbid").setValue(v_lbid);
					addSpForm.getForm().findField("lbname").setValue(v_lbname);
				}
        	}
        },"-",{
        	text:"查看",
        	iconCls:"btn-edit",
        	handler: function(){
        		var record= spxxGrid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert("信息提示","请选择要修改的商品");
				}else{
	        		addSpWindow.show();
					addSpForm.getForm().loadRecord(record);
					addSpForm.getForm().findField("addupdate").setValue("update");
				}
        	}
        }],
        
        bbar: new Ext.PagingToolbar({
            pageSize: 15,
            store: spxxStore,
            displayInfo: true
        }),
        
        listeners:{
        	rowdblclick:function(){
        		var record= spxxGrid.getSelectionModel().getSelected(); 
				if(!record){
                	Ext.Msg.alert("信息提示","请选择商品");
				}else{
	        		addJhWindow.show();
					addJhForm.getForm().loadRecord(record);
					addJhForm.getForm().findField("chprice").setRawValue("￥"+record.get("chprice"));
					addJhForm.getForm().findField("cbj").setRawValue(record.get("chprice"));
				}
        	}
        }
    });
    
    //商品单位下拉数据
    var spdwStore = new Ext.data.JsonStore({
	    xtype:"jsonstore",
		url: "spdw_findAllSpdw.do",
	    root: "root",
	    totalProperty: "total",
	    fields: [{name:"dwid",type:"int"},"dwname"]
	});
	
	//商品表单
    var addSpForm = new Ext.FormPanel({
		layout : "form",
		frame:true,
		labelWidth:60,
		border : false,
		padding : "15 10 0 8",
		defaults : {
			anchor : "100%"
		},
		items:[{
			layout:"column",
			items:[{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor : "95%"
				},
				items:[{
					layout:"column",
					items:[{
						layout:"form",
						width : 218,
						items:[{
							id:"lbtext",
							width : 145,
							xtype : "textfield",
							name:"lbname",
							fieldLabel:"所属类别",
							allowBlank : false,
							maxLength :50,
							enableKeyEvents:true,
							listeners:{
								focus:function(){
									splbTreeWindow.show();
								},
								blur:function(){
									addSpForm.getForm().clearInvalid();
								}
							}
						}]
					},{
						width : 20,
						height : 20,
						xtype:"button",
						iconCls : "btn-list",
						handler:function(){
							splbTreeWindow.show();
						}
					}]
				},{
					xtype : "textfield",
					name:"spname",
					fieldLabel:"商品名称",
					allowBlank : false,
					maxLength :20
				},{
					xtype : "textfield",
					name:"xinghao",
					fieldLabel:"商品型号",
					maxLength :20
				},{
					xtype : "numberfield",
					name:"jhprice",
					fieldLabel:"采购价",
					maxLength :10
				},{
					xtype : "numberfield",
					name:"minnum",
					fieldLabel:"库存下限",
					maxLength :10
				}]
			},{
				columnWidth:.5,
				layout:"form",
				defaults:{
					anchor : "95%"
				},
				items:[{
					xtype : "textfield",
					name:"spid",
					fieldLabel:"商品编码",
					allowBlank : false,
					maxLength :10
				},{
					xtype : "textfield",
					name:"tiaoma",
					fieldLabel:"商品条码",
					maxLength :20
				},{
					layout:"column",
					items:[{
						layout:"form",
						width : 218,
						items:[{
							width : 145,
							xtype:"combo",
							name:"dw",
							fieldLabel:"单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;位",
							mode: "local",
							triggerAction: "all",
							valueField :"dwid",
							displayField: "dwname",
							allowBlank : false,
							editable : false,
							store : spdwStore
						}]
					},{
						width : 20,
						height : 20,
						xtype:"button",
						iconCls : "btn-list",
						handler:function(){
							SpdwWindow.show();
						}
					}]
				},{
					xtype : "numberfield",
					name:"chprice",
					fieldLabel:"销售价",
					maxLength :10
				}]
			}]
		},{
			xtype : "textfield",
			name:"csname",
			fieldLabel:"生产厂商",
			maxLength :50
		},{
			xtype:"textarea",
			name:"bz",
			fieldLabel:"备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注",
			height:100,
			maxLength :200
		},{
			xtype : "hidden",
		    name : "lbid"
		},{
			xtype : 'hidden',
			name : 'addupdate'
		}]
	});
    
	//增加商品窗口
    var addSpWindow = new Ext.Window({
		title : "增加商品",
		width:550,
		height:370,
		closeAction:"hide",
		modal : true,
		border:false,
		resizable : false,
		layout : "fit",
		buttonAlign : "center",
		items : [addSpForm],
		buttons : [{
			text : "保存",
			handler : function() {
				if (addSpForm.getForm().isValid()) {
					addSpForm.getForm().submit({
						url : "spxx_saveOrUpdateSpxx.do",
						success : function(form, action) {
							Ext.Msg.alert("信息提示",action.result.message);
							addSpWindow.hide();
							spxxStore.reload({params:{lbid:v_lbid}});
						},
						failure : function(form, action) {
							if(action.result.errors){
								Ext.Msg.alert("信息提示",action.result.errors);
							}else{
								Ext.Msg.alert("信息提示","连接失败");
							}
						},
						waitTitle : "提交",
						waitMsg : "正在保存数据，稍后..."
					});
				}
			}
		}, {
			text : "取消",
			handler : function() {
				addSpWindow.hide();
			}
		}],
		listeners:{
			render:function(){
				spdwStore.load();
			}
		}
	});
	
    
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
	        		if(n.id=="0"){
	        			splbTreeWindow.buttons[0].setDisabled(true);
	        		}else{
		        		splbTreeWindow.buttons[0].setDisabled(false);
		        		v_lbid = n.id;
		        		v_lbname = n.text;
	        		}
	        	},
	        	dblclick:function(n){
	        		if(n.id=="0"){
	        			splbTreeWindow.buttons[0].setDisabled(true);
	        		}else{
		        		splbTreeWindow.buttons[0].setDisabled(false);
		        		v_lbid = n.id;
		        		v_lbname = n.text;
		        		splbTreeWindow.hide();
						addSpForm.getForm().findField("lbid").setValue(v_lbid);
						addSpForm.getForm().findField("lbname").setValue(v_lbname);
	        		}
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
			disabled : true,
			handler : function() {
				splbTreeWindow.hide();
				addSpForm.getForm().findField("lbid").setValue(v_lbid);
				addSpForm.getForm().findField("lbname").setValue(v_lbname);
			}
		}, {
			width:60,
			text : "取消",
			handler : function() {
				splbTreeWindow.hide();
				v_lbid = "0";
		        v_lbname = "";
			}
		}]
	});
	
	//商品单位编辑窗口
	var SpdwWindow = new Ext.Window({
		xtype:"window",
		title:"单位",
		width:250,
		height:250,
		resizable : false,
		closeAction:"hide",
		layout:"fit",
		items:[{
			xtype:"grid",
			store:spdwStore,
			columns:[new Ext.grid.RowNumberer(),{
					header:"单位名称",
					sortable:true,
					menuDisabled : true,
					dataIndex:"dwname",
					width:190
				}],
			tbar:[{
					text:"增加",
					handler:function(){
						SpdwAddWin.show();
					}
				},"-",{
					text:"删除",
					handler:function(){
						var dwGrid = SpdwWindow.get(0);
						var record= dwGrid.getSelectionModel().getSelected();
						if(!record){
		                	Ext.Msg.alert("信息提示","请选择要删除的单位");  
						}else{
							Ext.MessageBox.confirm("删除提示", "是否删除该单位？", function(c) {
							   if(c=="yes"){
							   		Ext.Ajax.request({
							   			url : "spdw_deleteSpdw.do",
							   			params:{ dwid : record.get("dwid") },
							   			success : function() {
							   				dwGrid.getStore().load();
							   			}
							   		});
							    }
							});
						}
					}
				},"-",{
					text:"确定",
					handler:function(){
						var record= SpdwWindow.get(0).getSelectionModel().getSelected();
						if(!record){
		                	Ext.Msg.alert("信息提示","请选择单位");  
						}else{
							addSpForm.getForm().findField("dw").setValue(record.get("dwname"));
							SpdwWindow.hide();
						}
					}
				},"-",{
					text:"取消",
					handler:function(){
						SpdwWindow.hide();
					}
				}]
		}]
	});
	
	//商品单位添加窗口
	var SpdwAddWin = new Ext.Window({
		title:"增加单位",
		width:250,
		height:140,
		resizable : false,
		closeAction:"hide",
		layout:"fit",
		fbar:[{
				text:"保存",
				handler:function(){
					var form = SpdwAddWin.get(0).getForm();
					if (form.isValid()) {
							form.submit({
								url : "spdw_saveOrUpdateSpdw.do",
								success : function(form, action) {
									SpdwAddWin.hide();
									spdwStore.load();
								},
								failure : function(form, action) {
									if(action.result.errors){
										Ext.Msg.alert("信息提示",action.result.errors);
									}else{
										Ext.Msg.alert("信息提示","连接失败");
									}
								}
							});
						}
				}
			},{
				text:"取消",
				handler:function(){
					SpdwAddWin.hide();
				}
		}],
		items:[{
				id:"dwform",
				xtype:"form",
				baseCls: "x-plain",
				labelWidth:60,
				padding:20,
				layout:"form",
				items:[{
						xtype:"textfield",
						name:"dwname",
						fieldLabel:"单位名称",
						allowBlank : false,
						maxLength :20,
						anchor:"100%"
				}]
		}]
	});
	
	//期初库存表单
	var addJhForm = new Ext.FormPanel({
		layout : "form",
		baseCls: "x-plain",
		border : false,
		labelAlign:"right",
		labelWidth:60,
		padding : 10,
		layout:"column",
		items:[{
			xtype:"fieldset",
			title:"商品信息",
			frame:true,
			layout:"column",
			padding:"0 20 0 15",
			columnWidth:1,
			items:[{
				xtype:"container",
				autoEl:"div",
				columnWidth:0.5,
				labelAlign:"right",
				layout:"form",
				items:[{
						xtype:"textfield",
						style:"background:#F6F6F6",
						fieldLabel:"商品编号",
						name:"spid",
						readOnly:true,
						anchor:"100%"
				},{
						xtype:"textfield",
						style:"background:#F6F6F6",
						name:"xinghao",
						fieldLabel:"型&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号",
						readOnly:true,
						value:"￥0.00",
						anchor:"100%"
				},{
						xtype:"textfield",
						style:"background:#F6F6F6",
						name:"chprice",
						fieldLabel:"销售单价",
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
						style:"background:#F6F6F6",
						name:"spname",
						fieldLabel:"商品名称",
						readOnly:true,
						anchor:"100%"
				},{
						xtype:"textfield",
						style:"background:#F6F6F6",
						name:"dw",
						readOnly:true,
						anchor:"100%"
				},{
						xtype:"textfield",
						style:"background:#F6F6F6",
						name:"kcsl",
						fieldLabel:"当前库存",
						readOnly:true,
						anchor:"100%"
				}]
			}]
		},{
			columnWidth:0.5,
			xtype:"container",
			autoEl:"div",
			style:"padding:5 0 0 15",
			layout:"form",
			items:[{
					xtype:"numberfield",
					name:"cbj",
					fieldLabel:"单价",
					allowBlank : false,
					maxLength :10,
					value:0,
					anchor:"100%"
			}]
		},{
			columnWidth:0.5,
			xtype:"container",
			autoEl:"div",
			style:"padding:5 20 0 0",
			layout:"form",
			items:[{
					xtype:"numberfield",
					name:"sl",
					fieldLabel:"数量",
					allowBlank : false,
					allowDecimals:false,
					maxLength :10,
					value:0,
					anchor:"100%"
			}]
		},{
			xtype:"hidden",
			name:"update"
		},{
			xtype : 'hidden',
			name : 'jhprice'
		},{
			xtype : 'hidden',
			name : 'lbid'
		},{
			xtype : 'hidden',
			name : 'lbname'
		}]
	});
	
	//增加进货商品窗口
    var addJhWindow = new Ext.Window({
		title : "商品信息",
		width:400,
		height:250,
		closeAction:"hide",
		modal : true,
		resizable : false,
		layout : "fit",
		buttonAlign : "center",
		items : [addJhForm],
		buttons : [{
			text : "新增下一商品",
			handler : function() {
				addJhWindow.hide();
				addJhsp();
			}
		},{
			text : "保存",
			handler : function() {
				addJhWindow.hide();
				bsspWindow.hide();
				addJhsp();
			}
		}, {
			text : "取消",
			handler : function() {
				addJhWindow.hide();
			}
		}],
		listeners:{
			show:function(){
				addJhForm.getForm().reset();
				addJhWindow.buttons[0].setVisible(true);
			}
		}
	});
	
	//添加到进货商品列表
	var addJhsp = function(){
		var g = Ext.getCmp("djspGrid");
		var s = g.getStore();
		var f = addJhForm.getForm();
		var v = f.getValues();
		var cbj = v.cbj;
		var sl = v.sl;
		var zj = cbj*sl;
		var jhprice = v.jhprice;
		var cbzj = jhprice*sl;
		if(f.findField("update").getValue()=="true"){
			var r= g.getSelectionModel().getSelected(); 
			if(r){
				var ozj = r.get("zj");
				var ocbzj = r.get("cbzj");
				r.set("cbj",cbj);
				r.set("sl",sl);
				r.set("zj",zj);
				g.stopEditing();
				zj = zj - ozj;
				cbzj = cbzj - ocbzj;
			}
		}else{
			var r = new s.recordType(v);
			r.set("zj",zj);
			r.set("cbzj",cbzj);
			g.stopEditing();
			s.add(r);
		}
		var djForm = Ext.getCmp("djForm").getForm();
		var yfje = djForm.findField("yfje").getValue()+zj;
		var cbje = djForm.findField("cbje").getValue()+cbzj;
		djForm.findField("yfje").setValue(yfje);
		djForm.findField("sfje").setValue(yfje);
		djForm.findField("cbje").setValue(cbje);
	}
	
	//查询工具条
	var northPanel = new Ext.Panel({
		region :"north",
		height:50,
		baseCls:"x-plain",
		padding:"15 0 0 20",
		layout:"column",
		items:[{
			baseCls:"x-plain",
			columnWidth:0.8,
			layout:'form',
			items:[new Ext.ux.form.SearchField({
					fieldLabel:"商品编号或名称",
	                store: spxxStore,
	                width:320
			})]
		},{
			baseCls:"x-plain",
			columnWidth:0.2,
			items:[{
				width:80,
				xtype:'button',
				text:'关闭',
				handler:function(){
					bsspWindow.hide();
				}
			}]
		}]
	});
	
	//备选商品窗口
	var bsspWindow = new Ext.Window({
		title:"库存商品选择框",
		width:760,
		height:500,
		layout:"border",
		closeAction:"hide",
		items:[northPanel,splbTree,spxxGrid],
		listeners:{
			show:function(){
				splbTree.root.expand(true, true);
			}
		}
	});


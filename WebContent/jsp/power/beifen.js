/*!
 * 备份/恢复数据库
 */
Ext.onReady(function(){
	
	Ext.QuickTips.init();
	
	var bfForm = new Ext.FormPanel({
		height: 110,
		border : false,
		layout : 'form',
		labelWidth:60,
		padding : 20,
		items:[{
			xtype:"fieldset",
			title:"备份数据库",
			padding:'20',
			layout:"column",
			defaults:{
				xtype:"container",
				autoEl:"div",
				labelAlign:'right',
				layout:"form"
			},
			items:[{
				columnWidth:.6,
				items:[{
					id:'datapath',
					xtype : 'textfield',
					inputType : 'textfield',
					fieldLabel : '备份路径',
					name : 'datapath',
					readOnly:true,
					value: 'D:\\MyStockData\\',
					anchor : '100%'
				}]
			},{
				columnWidth:.1,
				style:'padding-left:2px',
				html:'<input type="button" value="浏览..." onclick="browseFolder()" style="height:22px;width:60px;font-size:12px"></input>'
			},{
				items:[{
					width:75,
					xtype:"button",
					text:'备份',
					handler:function(){
						var f = bfForm.getForm();
						var datapath = f.findField("datapath").getValue();
						if (datapath) {
							Ext.Ajax.request({
					   			url : "dataCopy_backup.do",
					   			params:{ datapath : datapath },
					   			success : function(o) {
						   			if(o.responseText=="true"){
						   				Ext.Msg.alert("信息提示","备份完成");
					   				}else{
					   					Ext.Msg.alert("信息提示","备份失败");
					   				}
					   			}
					   		});
						}else{
							Ext.Msg.alert('信息提示','备份路径不能为空');
						}
					}
				}]
			}]
		},{
			xtype:"fieldset",
			title:"恢复数据库",
			padding:'20',
			layout:"column",
			defaults:{
				xtype:"container",
				autoEl:"div",
				labelAlign:'right',
				layout:"form"
			},
			items:[{
				columnWidth:.7,
				items:[{
					xtype : 'textfield',
					inputType : 'file',
					fieldLabel : '恢复文件',
					name : 'datafile',
					anchor : '95%'
				}]
			},{
				items:[{
					width:75,
					xtype:"button",
					text:'恢复',
					handler:function(){
						var f = bfForm.getForm();
						var datafile = f.findField("datafile").getValue();
						if(datafile.substring(datafile.length-4,datafile.length)!=".sql"){
							Ext.Msg.alert("信息提示","请选择已 .sql 结尾的文件");
							return ;
						}
						if (datafile) {
							Ext.Ajax.request({
					   			url : "dataCopy_load.do",
					   			params:{ datafile : datafile },
					   			success : function(o) {
						   			if(o.responseText=="true"){
						   				Ext.Msg.alert("信息提示","恢复完成");
					   				}else{
					   					Ext.Msg.alert("信息提示","恢复失败");
					   				}
					   			}
					   		});
						}else{
							Ext.Msg.alert('信息提示','恢复文件不能为空');
						}
					}
				}]
			}]
		},{
			xtype:"fieldset",
			title:"系统初始化",
			padding:'20',
			layout:"column",
			defaults:{
				xtype:"container",
				autoEl:"div",
				labelAlign:'right',
				layout:"form"
			},
			items:[{
				columnWidth:.7,
				items:[{
				    xtype: 'radiogroup',
					items:[
						{ name: 'delstate', boxLabel: '删除所有营业数据，只保留基础数据', inputValue: '1', checked : true},
						{ name: 'delstate', boxLabel: '删除所有数据（admin密码为admin）', inputValue: '2' }
					]
			   }]
			},{
				items:[{
					width:75,
					xtype:"button",
					text:'系统初始化',
					handler:function(){
						var f = bfForm.getForm();
						var delstate = f.findField("delstate").getValue();
						Ext.Ajax.request({
				   			url : "dataCopy_delete.do",
				   			params:{ delstate : delstate },
				   			success : function(o) {
					   			if(o.responseText=="true"){
					   				Ext.Msg.alert("信息提示","系统初始化完成");
				   				}else{
				   					Ext.Msg.alert("信息提示","系统初始化失败");
				   				}
				   			}
				   		});
					}
				}]
			}]
		}]
	});
	
	//布局
    new Ext.Viewport({
		layout:'fit',
		items:[{
			frame:true,
			title:'数据库管理',
			iconCls:'menu-63',
			layout:'fit',
			items:[bfForm]
		}],
		listeners:{
			render:function(){
			}
		}
	});

});

var browseFolder = function () {
    try {
        var Message = "\u8bf7\u9009\u62e9\u6587\u4ef6\u5939";  //选择框提示信息
        var Shell = new ActiveXObject("Shell.Application");
        var Folder = Shell.BrowseForFolder(0, Message, 64, 17);//起始目录为：我的电脑
        if (Folder != null) {
             Folder = Folder.items();  // 返回 FolderItems 对象
             Folder = Folder.item();  // 返回 Folderitem 对象
             Folder = Folder.Path;   // 返回路径
             if (Folder.charAt(Folder.length - 1) != "\\") {
                 Folder = Folder + "\\";
             }
             document.getElementById("datapath").value = Folder;
            return Folder;
         }
     }
    catch (e) {
         alert(e.message);
     }
}
<%--
  Created by IntelliJ IDEA.
  User: huxudong
  Date: 19-3-4
  Time: 下午6:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="easyui-layout" data-options="fit:true">
    <!-- Begin of toolbar -->
    <div id="wu-toolbar">
        <div class="wu-toolbar-button">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
        </div>
        <div class="wu-toolbar-search">
            <label>菜单名称：</label><input class="wu-text" style="width:100px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar"></table>
</div>
<!-- Begin of easyui-dialog -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:400px; padding:10px;">
    <form id="wu-form-2" method="post">
        <table>
            <tr>
                <td width="60" align="right" height="24">菜单名称:</td>
                <td><input type="text" name="name" class="wu-text" height="24"/></td>
            </tr>
            <tr>
                <td align="right">上级菜单:</td>
                <td><select class="easyui-combobox" panelHeight="auto" style="width:100px">
                    <option value="0">选择用户组</option>
                    <option value="1">黄钻</option>
                    <option value="2">红钻</option>
                    <option value="3">蓝钻</option>
                </select></td>
            </tr>
            <tr>
                <td align="right">菜单URL:</td>
                <td><input type="text" name="url" class="wu-text" /></td>
            </tr>
            <tr>
                <td valign="top" align="right">菜单图标:</td>
                <td><input type="text" name="icon" class="wu-text" /></td>
            </tr>
        </table>
    </form>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
    /**
     * Name 添加记录
     */
    function add(){
        $('#wu-form-2').form('submit', {
            url:'',
            success:function(data){
                if(data){
                    $.messager.alert('信息提示','提交成功！','info');
                    $('#add-dialog').dialog('close');
                }
                else
                {
                    $.messager.alert('信息提示','提交失败！','info');
                }
            }
        });
    }

    /**
     * Name 修改记录
     */
    function edit(){
        $('#wu-form-2').form('submit', {
            url:'',
            success:function(data){
                if(data){
                    $.messager.alert('信息提示','提交成功！','info');
                    $('#add-dialog').dialog('close');
                }
                else
                {
                    $.messager.alert('信息提示','提交失败！','info');
                }
            }
        });
    }

    /**
     * Name 删除记录
     */
    function remove(){
        $.messager.confirm('信息提示','确定要删除该记录？', function(result){
            if(result){
                var items = $('#wu-datagrid-2').datagrid('getSelections');
                var ids = [];
                $(items).each(function(){
                    ids.push(this.productid);
                });
                //alert(ids);return;
                $.ajax({
                    url:'',
                    data:'',
                    success:function(data){
                        if(data){
                            $.messager.alert('信息提示','删除成功！','info');
                        }
                        else
                        {
                            $.messager.alert('信息提示','删除失败！','info');
                        }
                    }
                });
            }
        });
    }

    /**
     * Name 打开添加窗口
     */
    function openAdd(){
        $('#wu-form-2').form('clear');
        $('#add-dialog').dialog({
            closed: false,
            modal:true,
            title: "添加信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: add
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#add-dialog').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 打开修改窗口
     */
    function openEdit(){
        $('#wu-form-2').form('clear');
        var item = $('#add-datagrid').datagrid('getSelected');
        //alert(item.productid);return;
        $.ajax({
            url:'',
            data:'',
            success:function(data){
                if(data){
                    $('#add-dialog').dialog('close');
                }
                else{
                    //绑定值
                    $('#wu-form-2').form('load', data)
                }
            }
        });
        $('#add-dialog').dialog({
            closed: false,
            modal:true,
            title: "修改信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: edit
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#wu-dialog-2').dialog('close');
                }
            }]
        });
    }

    /**
     * Name 分页过滤器
     */
    function pagerFilter(data){
        if (typeof data.length == 'number' && typeof data.splice == 'function'){// is array
            data = {
                total: data.length,
                rows: data
            }
        }
        var dg = $(this);
        var opts = dg.datagrid('options');
        var pager = dg.datagrid('getPager');
        pager.pagination({
            onSelectPage:function(pageNum, pageSize){
                opts.pageNumber = pageNum;
                opts.pageSize = pageSize;
                pager.pagination('refresh',{pageNumber:pageNum,pageSize:pageSize});
                dg.datagrid('loadData',data);
            }
        });
        if (!data.originalRows){
            data.originalRows = (data.rows);
        }
        var start = (opts.pageNumber-1)*parseInt(opts.pageSize);
        var end = start + parseInt(opts.pageSize);
        data.rows = (data.originalRows.slice(start, end));
        return data;
    }

    /**
     * Name 载入数据
     */
    $('#data-datagrid').datagrid({
        url:'temp/datagrid.php',
        loadFilter:pagerFilter,
        rownumbers:true,
        singleSelect:false,
        pageSize:20,
        pagination:true,
        multiSort:true,
        fitColumns:true,
        fit:true,
        columns:[[
            { checkbox:true},
            { field:'productid',title:'productid',width:100,sortable:true},
            { field:'productname',title:'productname',width:180,sortable:true},
            { field:'unitcost',title:'unitcost',width:100},
            { field:'listprice',title:'listprice',width:100},
            { field:'attr1',title:'attr1',width:100},
            { field:'itemid',title:'itemid',width:100},
            { field:'status',title:'status',width:100}
        ]]
    });
</script>
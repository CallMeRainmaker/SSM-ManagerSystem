<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <div id="wu-toolbar-2">
        <div class="wu-toolbar-button">
            <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="openAdd()" plain="true">添加</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="openEdit()" plain="true">修改</a>
            <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="remove()" plain="true">删除</a>
        </div>
        <div class="wu-toolbar-search">
            <label>用户组：</label>
            <select class="easyui-combobox" panelHeight="auto" style="width:100px">
                <option>选择上级分类</option>
                <c:forEach items="${topList}" var="menu">
                    <option value="${menu.id}">${menu.name}</option>
                </c:forEach>
            </select>
            <label>菜单名称：</label><input class="wu-text" style="width:100px">
            <a href="#" class="easyui-linkbutton" iconCls="icon-search">搜索</a>
        </div>
    </div>
    <!-- End of toolbar -->
    <table id="data-datagrid" class="easyui-datagrid" toolbar="#wu-toolbar-2"></table>
</div>
<!-- Begin of easyui-dialog -->
<div id="add-dialog" class="easyui-dialog" data-options="closed:true,iconCls:'icon-save'" style="width:450px; padding:10px;">
    <form id="add-form" method="post">
        <table>
            <tr>
                <td width="60" align="right">菜单名称:</td>
                <td><input type="text" name="name" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写菜单名称'" /></td>
            </tr>
            <tr>
                <td align="right">上级菜单:</td>
                <td>
                    <select name="parentId" class="easyui-combobox" panelHeight="auto" style="width:268px">
                        <option value="0">顶级分类</option>
                        <c:forEach items="${topList }" var="menu">
                            <option value="${menu.id }">${menu.name }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td align="right">菜单URL:</td>
                <td><input type="text" name="url" class="wu-text" /></td>
            </tr>
            <tr>
                <td align="right">菜单图标:</td>
                <td>
                    <input type="text" id="add-icon" name="icon" class="wu-text easyui-validatebox" data-options="required:true, missingMessage:'请填写菜单图标'" />
                    <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="selectIcon()" plain="true">选择</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<!-- End of easyui-dialog -->
<script type="text/javascript">
    /**
     *  添加记录
     */
    function add(){
        var validate = $("#add-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#add-form").serialize();
        $.ajax({
            url:'../menu/add',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','添加成功！','info');
                    $('#add-dialog').dialog('close');
                    $('#data-datagrid').treegrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }

    function selectIcon(){
        if($("#icons-table").children().length <= 0){
            $.ajax({
                url:'../menu/get_icons',
                dataType:'json',
                type:'post',
                success:function(data){
                    if(data.type == 'success'){
                        var icons = data.content;
                        var table = '';
                        for(var i=0;i<icons.length;i++){
                            var tbody = '<td class="icon-td"><a onclick="selected(this)" href="javascript:void(0)" class="' + icons[i] + '">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></td>';
                            if(i == 0){
                                table += '<tr>' + tbody;
                                continue;
                            }
                            if((i+1)%24 === 0){
                                //console.log(i + '---' + i%12);
                                table += tbody + '</tr><tr>';
                                continue;
                            }
                            table += tbody;
                        }
                        table += '</tr>';
                        $("#icons-table").append(table);
                    }else{
                        $.messager.alert('信息提示',data.msg,'warning');
                    }
                }
            });
        }

        $('#select-icon-dialog').dialog({
            closed: false,
            modal:true,
            title: "选择icon信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function(){
                    var icon = $(".selected a").attr('class');
                    $("#add-icon").val(icon);
                    $("#edit-icon").val(icon);
                    $("#add-menu-icon").val(icon);
                    $('#select-icon-dialog').dialog('close');
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#select-icon-dialog').dialog('close');
                }
            }]
        });
    }

    function selected(e){
        $(".icon-td").removeClass('selected');
        $(e).parent("td").addClass('selected');

    }

    /**
     * Name 修改记录
     */
    function edit(){
        var validate = $("#edit-form").form("validate");
        if(!validate){
            $.messager.alert("消息提醒","请检查你输入的数据!","warning");
            return;
        }
        var data = $("#edit-form").serialize();
        $.ajax({
            url:'../../admin/menu/edit',
            dataType:'json',
            type:'post',
            data:data,
            success:function(data){
                if(data.type == 'success'){
                    $.messager.alert('信息提示','修改成功！','info');
                    $('#edit-dialog').dialog('close');
                    $('#data-datagrid').treegrid('reload');
                }else{
                    $.messager.alert('信息提示',data.msg,'warning');
                }
            }
        });
    }

    /**
     * 删除记录
     */
    function remove(){
        $.messager.confirm('信息提示','确定要删除该记录？', function(result){
            if(result){
                var item = $('#data-datagrid').datagrid('getSelected');
                $.ajax({
                    url:'../../admin/menu/delete',
                    dataType:'json',
                    type:'post',
                    data:{id:item.id},
                    success:function(data){
                        if(data.type == 'success'){
                            $.messager.alert('信息提示','删除成功！','info');
                            $('#data-datagrid').treegrid('reload');
                        }else{
                            $.messager.alert('信息提示',data.msg,'warning');
                        }
                    }
                });
            }
        });
    }

    //添加菜单弹框
    function openAddMenu(){
        //$('#add-form').form('clear');
        var item = $('#data-datagrid').treegrid('getSelected');
        if(item == null || item.length == 0){
            $.messager.alert('信息提示','请选择要添加菜单的数据！','info');
            return;
        }
        if(item.parentId == 0){
            $.messager.alert('信息提示','请选择二级菜单！','info');
            return;
        }
        var parent = $('#data-datagrid').treegrid('getParent',item.id);
        if(parent.parentId != 0){
            $.messager.alert('信息提示','请选择二级菜单！','info');
            return;
        }
        $('#add-menu-dialog').dialog({
            closed: false,
            modal:true,
            title: "添加按钮信息",
            buttons: [{
                text: '确定',
                iconCls: 'icon-ok',
                handler: function(){
                    var validate = $("#add-menu-form").form("validate");
                    if(!validate){
                        $.messager.alert("消息提醒","请检查你输入的数据!","warning");
                        return;
                    }
                    var data = $("#add-menu-form").serialize();
                    $.ajax({
                        url:'add',
                        dataType:'json',
                        type:'post',
                        data:data,
                        success:function(data){
                            if(data.type == 'success'){
                                $.messager.alert('信息提示','添加成功！','info');
                                $('#add-menu-dialog').dialog('close');
                                $('#data-datagrid').treegrid('reload');
                            }else{
                                $.messager.alert('信息提示',data.msg,'warning');
                            }
                        }
                    });
                }
            }, {
                text: '取消',
                iconCls: 'icon-cancel',
                handler: function () {
                    $('#add-menu-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                $("#parent-menu").val(item.name);
                $("#add-menu-parent-id").val(item.id);
            }
        });
    }

    /**
     * Name 打开添加窗口
     */
    function openAdd(){
        //$('#add-form').form('clear');
        $('#add-dialog').dialog({
            closed: false,
            modal:true,
            title: "添加菜单信息",
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
     * 打开修改窗口
     */
    function openEdit(){
        //$('#edit-form').form('clear');
        var item = $('#data-datagrid').treegrid('getSelected');
        if(item == null || item.length == 0){
            $.messager.alert('信息提示','请选择要修改的数据！','info');
            return;
        }

        $('#edit-dialog').dialog({
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
                    $('#edit-dialog').dialog('close');
                }
            }],
            onBeforeOpen:function(){
                $("#edit-id").val(item.id);
                $("#edit-name").val(item.name);
                $("#edit-parentId").combobox('setValue',item.parentId);
                $("#edit-parentId").combobox('readonly',false);
                //判断是否是按钮
                var parent = $('#data-datagrid').treegrid('getParent',item.id);
                if(parent != null){
                    if(parent.parentId != 0){
                        $("#edit-parentId").combobox('setText',parent.name);
                        $("#edit-parentId").combobox('readonly',true);
                    }
                }

                $("#edit-url").val(item.url);
                $("#edit-icon").val(item.icon);
            }
        });
    }


    //搜索按钮监听
    $("#search-btn").click(function(){
        $('#data-datagrid').treegrid('reload',{
            name:$("#search-name").val()
        });
    });

    /**
     * 载入数据
     */
    $('#data-datagrid').datagrid({
        url:'../menu/list',
        rownumbers:true,
        singleSelect:true,
        pageSize:20,
        pagination:true,
        multiSort:true,
        fitColumns:true,
        idField:'id',
        treeField:'name',
        fit:true,
        columns:[[
            { field:'name',title:'菜单名称',width:100,sortable:true},
            { field:'url',title:'菜单URL',width:100,sortable:true},
            { field:'icon',title:'图标icon',width:100,formatter:function(value,index,row){
                    var test = '<a class="' + value + '">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>'
                    return test + value;
                }}
        ]]
    });
</script>
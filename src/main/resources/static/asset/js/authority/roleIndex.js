layui.config({
    base: '/asset/js/'
    , version: 'v1'
}).use('admin');
layui.use(['form', 'table', 'jquery', 'admin', 'layer'], function () {
    var $ = layui.jquery,
        form = layui.form,
        table = layui.table,
        layer = layui.layer,
        admin = layui.admin;

    form.verify({
        sourceName: function (value) {
            var reg = /^[\u4E00-\u9FA5A-Za-z0-9_]{3,17}$/;
            if (!isEmptyString(value) && !reg.test(value)) {
                return "角色名格式不合法";
            }
        }
    });

    var queryParams = function () {
        var param = {roleSearchForm: {}};
        param.roleSearchForm.orderColumn = "whenCreated";
        param.roleSearchForm.orderDir = "asc";
        param.roleSearchForm.roleName = $("#roleName").val();
        return param;
    };

    var roleTable = table.render({
        elem: '#rolesList',
        limits: [20, 40, 60, 100, 150, 300],
        limit: 20,
        skin: '#1E9FFF', //自定义选中色值
        loading: true,
        method: 'POST',
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        page: true, //开启分页
        cols: [[
            {field: 'id', title: 'ID', width: '10%'},
            {field: 'roleName', title: '资源名称', width: '35%'},
            {field: 'adminNum', title: '用户数', width: '10%'},
            {field: 'whenUpdated', title: '修改时间', width: '15%'},
            {field: 'whenCreated', title: '创建时间', width: '15%'},
            {title: '操作', width: '15%', templet: '#operation'}
        ]],
        url: '/authority/AdminRole/list',
        where: queryParams()
    });

    form.on('submit(searchBut)', function (data) {
        roleTable.reload({
            where: queryParams()
        });
    });

    table.on('tool(rolesList)', function (obj) { //注：tool是工具条事件名，resourcesList是table原始容器的属性 lay-filter="对应的值"
        var layEvent = obj.event; //获得 lay-event 对应的值
        var data = obj.data; //获得当前行数据
        var dataId = data.id;
        console.log(layEvent + '-----' + dataId);
        if (layEvent === 'edit') {
            addPage('修改权限角色', '/authority/AdminRole/edit/' + dataId);
        }
        if (layEvent === 'del') {
            layer.open({
                content: '您确定要删除 ' + data.roleName + ' 吗?',
                icon: 3,
                btn: ['确定', '取消'],
                yes: function (index, layero) {
                    $.ajax({
                        url:'/authority/AdminRole/del/'+dataId,
                        method:'GET',
                        success:function(data){
                            if(data.errcode === 0){
                                layer.msg(data.msg, {time: 2000, icon:1});
                                roleTable.reload({
                                    where: queryParams()
                                });
                            }else{
                                layer.msg(data.msg, {time: 2000, icon:5});
                            }
                        },
                        error:function (error) {
                            data = JSON.parse(error.responseText);
                            if(data.detail === 1){
                                var errmsgs = data.msg;
                                var errstr = '';
                                for (var i in errmsgs) {
                                    errstr += errmsgs[i] + '<br />';
                                }
                                layer.alert(errstr, {icon: 5});
                            } else {
                                if (!isEmptyString(data.msg))  {
                                    layer.msg(data.msg, {time: 2000, icon:5});
                                } else {
                                    layer.msg("操作失败", {time: 2000, icon:5});
                                }
                            }
                        }
                    });
                }
            });
        }


    });


});
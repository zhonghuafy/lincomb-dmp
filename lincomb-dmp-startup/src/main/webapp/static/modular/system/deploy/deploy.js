/**
 * 系统管理--信息部署管理的单例对象
 */
var Deploy = {
    id: "managerTable",//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Deploy.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {field: 'Number', title: '序号', formatter: function (value, row, index) {
            return Feng.getTableLineNo(Deploy.id, index);
        }
        },
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '主机名称', field: 'slave_name', align: 'center', valign: 'middle', sortable: true},
        {title: '备注', field: 'remark', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'create_time', align: 'center', valign: 'middle', sortable: true},
        {title: '修改时间', field: 'update_time', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'orders', align: 'center', valign: 'middle', sortable: true},
        {title: '服务器开关', field: 'slave_status', align: 'center', valign: 'middle', sortable: true,
            formatter:function (value) {
                if (value == 0){
                    return "关"
                }else{
                    return "开"
                }
            }
        }];
    return columns;
};

/**
 * 删除信息
 */
Deploy.delDeploy = function () {
    if (this.check()) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/deploy/remove", function () {
                Feng.success("删除成功!");
                Deploy.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("id", Deploy.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否删除信息 " + Deploy.seItem.slave_name + "?",operation);
    }
};

/**
 * 检查是否选中
 */
Deploy.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Deploy.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加信息
 */
Deploy.openAddDeploy = function () {
    var index = layer.open({
        type: 2,
        title: '添加信息',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deploy/deploy_add'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 */
Deploy.openChangeDeploy = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改信息',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deploy/deploy_edit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};

Deploy.resetSearch = function () {
    $("#slaveName").val("");
    $("#slaveStatus").val("");
    $("#createTime").val("");

    Deploy.search();
}

Deploy.search = function () {
    var queryData = {};

    queryData['slaveName'] = $("#slaveName").val();
    queryData['slaveStatus'] = $("#slaveStatus").val();
    queryData['createTime'] = $("#createTime").val();

    Deploy.table.refresh({query: queryData});
}

Deploy.onClickDept = function (e, treeId, treeNode) {
    Deploy.deptid = treeNode.id;
    Deploy.search();
};

$(function () {
    var defaultColunms = Deploy.initColumn();
    var table = new BSTable("managerTable", "/deploy/list", defaultColunms);
    Deploy.table = table.init();
});
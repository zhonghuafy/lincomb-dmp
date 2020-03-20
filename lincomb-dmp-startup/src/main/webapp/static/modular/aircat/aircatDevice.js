/**
 * 字典管理初始化
 */
var Aircat = {
    id: "AircatDeviceTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Aircat.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {field: 'Number', title: '序号', formatter: function (value, row, index) {return Feng.getTableLineNo(Aircat.id, index);}},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '设备MAC', field: 'mac', align: 'center', valign: 'middle', sortable: false},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: false,sortName:'create_time'},
        {title: '修改时间', field: 'updateTime', align: 'center', valign: 'middle', sortable: false,sortName:'update_time'}];
};

/**
 * 检查是否选中
 */
Aircat.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
        Aircat.seItem = selected[0];
        return true;
    }
};

/**
 * 查看详情
 */
/*Aircat.openLogDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['1500px', '800px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/aircat/detail/' + Aircat.seItem.mac
        });
        this.layerIndex = index;
    }
};*/

/**
 * 查询空气猫设备列表
 */
Aircat.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Aircat.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Aircat.initColumn();
    var table = new BSTable(Aircat.id, "/aircatDevice/list", defaultColunms);
    Aircat.table = table.init();
});

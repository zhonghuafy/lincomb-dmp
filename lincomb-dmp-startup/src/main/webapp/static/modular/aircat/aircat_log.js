/**
 * 字典管理初始化
 */
var Aircat = {
    id: "AircatLogTable",	//表格id
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
        {title: '温度', field: 'temperature', align: 'center', valign: 'middle', sortable: false},
        {title: '湿度', field: 'humidity', align: 'center', valign: 'middle', sortable: false},
        {title: '甲醛', field: 'hcho', align: 'center', valign: 'middle', sortable: false},
        {title: 'PM2.5', field: 'pmValue', align: 'center', valign: 'middle', sortable: false},
        {title: '体感温度', field: 'kinect', align: 'center', valign: 'middle', sortable: false},
        {title: '位置', field: 'position', align: 'center', valign: 'middle', sortable: false},
        {title: '时间', field: 'catTime', align: 'center', valign: 'middle', sortable: false,sortName:'cat_time'}];
};

/**
 * 初始化请求历史数据
 */
$(function () {
    var mac = $("#mac").val();
    var defaultColunms = Aircat.initColumn();
    var table = new BSTable(Aircat.id, "/aircat/listLog/"+mac, defaultColunms);
    Aircat.table = table.init();
});

/**
 * 信息详情对话框（可用于添加和修改对话框）
 */
var DeployInfoDlg = {
    deployInfoData: {},
    validateFields: {
        slaveName: {
            validators: {
                notEmpty: {
                    message: '主机名称不能为空'
                }
            }
        },
        remark: {
            validators: {
                notEmpty: {
                    message: '备注不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
DeployInfoDlg.clearData = function () {
    this.deployInfoData = {};
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeployInfoDlg.set = function (key, val) {
    this.deployInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
};

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeployInfoDlg.get = function (key) {
    return $("#" + key).val();
};

/**
 * 关闭此对话框
 */
DeployInfoDlg.close = function () {
    parent.layer.close(window.parent.Deploy.layerIndex);
};

/**
 * 收集数据
 */
DeployInfoDlg.collectData = function () {
    this.set('id').set('orders').set('slaveName').set('remark').set('slaveStatus');
};

/**
 * 验证数据是否为空
 */
DeployInfoDlg.validate = function () {
    $('#deployInfoForm').data("bootstrapValidator").resetForm();
    $('#deployInfoForm').bootstrapValidator('validate');
    return $("#deployInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加用户
 */
DeployInfoDlg.addSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deploy/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Deploy.table.refresh();
        DeployInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deployInfoData);
    ajax.start();
};

/**
 * 提交修改
 */
DeployInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/deploy/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Deploy.table.refresh();
        DeployInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deployInfoData);
    ajax.start();
};

$(function () {
    Feng.initValidator("deployInfoForm", DeployInfoDlg.validateFields);

    //初始化状态选项
    $("#slaveStatus").val($("#slaveStatusValue").val());
});
@/*
    表单中iconselect框标签中各个参数的说明:
    id : input框id
    name : input框名称
    clickFun : 点击事件的方法名
    style : 附加的css属性
    button :
@*/


@if(isNotEmpty(button)){
    <div class="input-group">
            <div class="input-group-btn">
                <button data-toggle="dropdown" class="btn btn-white dropdown-toggle"
                        type="button">
                    <i id="${id}_icon" class="fa ${icon!}" ></i>
                    ${name}
                </button>
            </div>
            <input type="text" class="form-control" id="${id}"
                   @if(isNotEmpty(value)){
                        value="${tool.dateType(value)}"
                   @}
                   placeholder="${placeholder!}"
            />

    </div>
@}else{
    <div class="form-group">
            <label class="col-sm-3 control-label">
                <label id="${id}_icon"
                       @if(isNotEmpty(value)){
                       class="fa ${tool.dateType(value)}"
                       @}
                ></label>
                ${name}
            </label>
            <div class="col-sm-9">
                <input class="form-control" id="${id}" name="${id}" placeholder="${placeholder!}"
                       @if(isNotEmpty(value)){
                       value="${tool.dateType(value)}"
                       @}
                       @if(isNotEmpty(type)){
                       type="${type}"
                       @}else{
                       type="text"
                       @}
                >
            </div>
    </div>
@}


<script type="text/javascript">
    $("#${id}").click(function(){
        //最大化当前窗口
        if(window.parent.Menu && window.parent.Menu.layerIndex){
            parent.layer.full(window.parent.Menu.layerIndex)//获取窗口索引;
        }

        var index = layer.open({
            type: 2,
            title: '双击选择图标',
            area: ['768px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/fontawesome?id=${id}'
        });
        this.layerIndex = index;
    });
</script>



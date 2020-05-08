
function delLog(id) {
    console.log("id->"+id);
    $.ajax({
        type: "post",
        url: "/admin/delLog",
        data: {
            "id":id
        },
        success: function ( ) {
            alert("删除成功！")
            findAllLog();
        }
    });
}

var vm2 = new Vue({
    el: '#admintable',
   // data: dataArray2,
    data : {mydata  : []},
    methods: {
        removeList: function (index) {
            console.log("id "+ index);
            delLog(index);
            alert('你删除了第' + index + '个');
        }
    }
});
function findAllLog() {
    var findnameortime = document.getElementById('findnameortime').value;
    $.ajax({
        type: "GET",
        url: "/admin/allLog",
        dataType: "json",
        data: {
            "findnameortime": findnameortime,
        },
        success: function (data) {
            console.log("data:====>"+data);
            vm2.mydata = data;
            $("#admintable").addClass("show");
        }
    })
}

$(document).ready(function () {
    $('#myModalAdmin').on('show.bs.modal', function (event) {
        var logId = $(event.relatedTarget).context.dataset.logid;
        //console.log(logId);
        $("#hiddenID").val(logId);
        //changeAdminLog(logId);
    });
});
/**新增日志**/
function addLog() {
    //获取表单id为addfrom的表单
    var addFrom = document.addfrom;
    var uid = addFrom.uid;
    var workdate = addFrom.workdate;
    var describe = addFrom.describe;
    var worktime = addFrom.worktime;
    var difficulty = addFrom.difficulty;
    var remark = addFrom.remark;
    var dd = {
        uid: uid.value,
        workdate: workdate.value,
        describe: describe.value,
        worktime: worktime.value,
        difficulty: difficulty.value,
        remark: remark.value
    }
    $.ajax({
        type: "POST",
        url: "addlog",
        dataType: "json",
        data: dd,
        success: function (msg) {
            if (msg) {
                alert("提交成功");
                findLog();
            } else {
                alert("提交失败");
            }

        }
    });
    //清空模态框的数据
    uid = addFrom.uid;
    describe.value = "";
    remark.value = "";
    //关闭模态框
    $("#myModal").click();
}
function changeAdminLog() {
    //获取表单id为addfrom的表单
    var addFrom = document.addfrom2;
    console.log(addFrom);
    var uid = addFrom.uid;
    var workdate = addFrom.workdate;
    var describe = addFrom.describe;
    var worktime = addFrom.worktime;
    var difficulty = addFrom.difficulty;
    var remark = addFrom.remark;
    var hiddenID = addFrom.hiddenID;
    var dd = {
        hiddenID:hiddenID.value,
        uid: uid.value,
        workdate: workdate.value,
        describe: describe.value,
        worktime: worktime.value,
        difficulty: difficulty.value,
        remark: remark.value
    }
    $.ajax({
        type: "POST",
        url: "/admin/changeAdminLog",
        dataType: "json",
        data: dd,
        success: function (msg) {
            if (msg) {
                alert("更新成功");
                findAllLog();
            } else {
                alert("更新失败");
            }

        }
    });
    //清空模态框的数据
    uid = addFrom.uid;
    describe.value = "";
    remark.value = "";
    //关闭模态框
    $("#myModalAdmin").click();
}
function exportXls() {
    window.location.href = "/exportExcel2";
}

function exportAllToXls() {
    window.location.href = "/exportExcel";
}






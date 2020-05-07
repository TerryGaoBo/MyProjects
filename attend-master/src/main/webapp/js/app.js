

/**
 * Created by tao on 2017/4/5 0005.
 */
function getCookie(Name) {
    var search = Name + "="//查询检索的值
    var returnvalue = "";//返回值
    if (document.cookie.length > 0) {
        sd = document.cookie.indexOf(search);
        if (sd != -1) {
            sd += search.length;
            end = document.cookie.indexOf(";", sd);
            if (end == -1)
                end = document.cookie.length;
            //unescape() 函数可对通过 escape() 编码的字符串进行解码。
            returnvalue = unescape(document.cookie.substring(sd, end))
        }
    }
    return returnvalue;
}
//相当于一个对象dataArray
var dataArray = {
    //名-值 对的形式定义 logs是名字，[] 代表是数组
    logs : []
};
var vm = new Vue({
    el: '#table',
    data: dataArray
});

function findLog() {
    var workdate = document.getElementById('findtime').value;
    console.log(workdate);
    $.ajax({
        type: "GET",
        url: "findlog",
        data: {
            uid: getCookie("userId"),
            "workdate": workdate,
        },
        dataType: "json",
        //返回的data是一个数组
        success: function (data) {
            console.log("data:"+data);
            console.log("length:"+data.length);
            vm.logs = data;
            //这里获取到数据展示到前台
            $("#table").addClass("show");
        },

        error: function (data) {
            console.log("data:"+data);
            alert("请求失败");
        },
    })
}

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

/**提交日志**/
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
$(document).ready(function () {
    $('#myModalAdmin').on('show.bs.modal', function (event) {
        var logId = $(event.relatedTarget).context.dataset.logid;
        //console.log(logId);
        $("#hiddenID").val(logId);
        //changeAdminLog(logId);
    });
    // $('#myModalAdmin').modal("hide");
    // function Values(ID) {
    //     console.log(ID);
    //     $("#hiddenID").val(ID);
    // }
})

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
    $("#myModal").click();
}
function exportXls() {
    window.location.href = "/exportExcel2";
}

function exportAllToXls() {
    window.location.href = "/exportExcel";
}






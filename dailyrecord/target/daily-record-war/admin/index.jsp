
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>管理员登录界面</title>
    <!-- HTTP 1.1 -->
    <meta http-equiv="pragma" content="no-cache">
    <!-- HTTP 1.0 -->
    <meta http-equiv="cache-control" content="no-cache">
    <!-- Prevent caching at the proxy server -->
    <meta http-equiv="expires" content="0">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9" />
    <link rel="stylesheet" href="../css/bootstrap.min.css"/>
    <link rel="stylesheet" href="../css/bootstrap-table.min.css"/>
    <link rel="stylesheet" href="../css/bootstrap-combined.min.css"/>
    <link rel="stylesheet" href="../css/bootstrap-datetimepicker.min.css"/>
    <link rel="stylesheet" href="../css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

</head>
<body>
<div id="content">
    <div class="title">

    </div>
    <div class="display">
        <p><b>管理员<%=session.getAttribute("adminName")%></b></p>
    </div>
    <div class="serch">
            <input type="text" id = "findnameortime" name="serach" class="text" placeholder="按时间或者姓名查找"/>
            <button class="submit" onclick="findAllLog()">查看</button>
            <button class="submit" onclick="exportAllToXls()">导出全部</button>
            <button  class="submit" id="testBtn" onclick=" exportXls()">导出自己</button>
            <button class="submit" data-toggle="modal" data-target="#myModalAdmin" onclick="">新增</button>
    </div>
    <div class="table-responsive">
        <table id="admintable" class="table table-striped table-hover" style="display: none">
            <thead>
            <tr>
                <th data-field="id">序号</th>
                <th data-field="uid">工号</th>
                <th data-field="workdate">日期</th>
                <th data-field="desribe">内容</th>
                <th data-field="worktime">时长</th>
                <th data-field="difficulty">难度</th>
                <th data-field="remark">备注</th>
                <th data-field="operation">操作</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="log in mydata" >
                <td>{{mydata.indexOf(log)+1}} <span style="display: none">{{log.id}}</span></td>
                <td>{{log.uid}}</td>
                <td>{{log.workdate}}</td>
                <td>{{log.desribe}}</td>
                <td>{{log.worktime}}</td>
                <td>{{log.difficulty}}</td>
                <td>{{log.remark}}</td>
                <td>
                    <span style="display: none">{{log.id}}</span>
                    <button class="btn btn-danger" data-toggle="modal" data-target="#myModalAdmin" :data-logid="log.id"> 修改</button>
                    <button class="btn btn-danger" @click="removeList(log.id)">删除</button>
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <!-- 模态框（Modal） -->
    <div class="modal fade" id="myModalAdmin" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <%--<div class="modal-dialog">--%>
        <div class="modal-content">
            <div class="modal-header">
                <button id="closeModel" type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title" id="myModalLabel">个人工作日志</h2>
            </div>

            <div class="modal-body">
                <%--<form id="addfrom2" name="addfrom2" class="form-horizontal" role="from" method="get" action="/addlog" >--%>
                    <form id="addfrom2" name="addfrom2" class="form-horizontal" method="get" >
                    <fieldset>
                        <div class="control-group" style="display: none">
                            <!-- Text input-->
                            <label class="control-label" ></label>
                            <div class="controls">
                                <input id="uid" name="uid" value='<%=session.getAttribute("adminId")%>' hidden>
                                <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="control-group">
                            <!-- Text input-->
                            <label class="control-label" >姓名</label>
                            <div class="controls">
                                <input class="input-mini" value='<%=session.getAttribute("adminName")%>' readonly disabled>
                                <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">日期</label>
                            <div class="controls">
                                <input id="workdate" type="date" name="workdate"/>
                                <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">时长</label>
                            <div class="controls">
                                <select name="worktime" id="">
                                    <option value="1">1</option>
                                    <option value="2">2</option>
                                    <option value="3">3</option>
                                    <option value="4">4</option>
                                    <option value="5">5</option>
                                    <option value="6">6</option>
                                    <option value="7">7</option>
                                    <option value="8" selected>8</option>
                                    <option value="9">9</option>
                                </select>
                                <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="control-group">
                            <!-- Textarea -->
                            <label class="control-label">内容</label>
                            <div class="controls">
                                <div class="textarea">
                                    <textarea type="" class="form-control" name="describe" rows="5" cols="10" > </textarea>
                                </div>
                            </div>
                        </div>
                        <div class="control-group">
                            <label class="control-label">难度</label>
                            <div class="controls">
                                <select name="difficulty" >
                                    <option value="易" selected>易</option>
                                    <option value="中">中</option>
                                    <option value="难">难</option>
                                </select>
                                <p class="help-block"></p>
                            </div>
                        </div>
                        <div class="control-group">
                            <!-- Textarea -->
                            <label class="control-label">备注</label>
                            <div class="controls">
                                <div class="textarea">
                                    <textarea type="" class="form-control" name="remark" rows="4" cols="10" > </textarea>
                                </div>
                            </div>
                            <input type="hidden" id="hiddenID" name="hiddenID" value=""/>
                        </div>
                    </fieldset>
                </form>
            </div>
            <div class="modal-footer">

                <button id="closeAddFrom" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="submint" class="btn btn-primary" onclick="changeAdminLog()">提交</button>
                <input type="reset" name="reset" style="display: none;" />
            </div>

        </div><!-- /.modal-content -->
        <%--</div><!-- /.modal -->--%>
    </div>
</div>
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<%--<script src="../js/bootstrap-table.js"></script>--%>
<%--<script src="../js/bootstrap-datetimepicker.min.js"></script>--%>
<script src="../js/vue.js"></script>
<script src="../js/app.js"></script>
<script>
    $(document).ready(function () {
        var time = new Date();
        var day = ("0" + time.getDate()).slice(-2);
        var month = ("0" + (time.getMonth() + 1)).slice(-2);
        var today = time.getFullYear() + "-" + (month) + "-" + (day);
        $('#workdate').val(today);
    })
</script>
</body>
</html>

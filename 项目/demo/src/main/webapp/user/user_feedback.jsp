<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html class="ax-vertical-centered">
<head>
    <title>图书馆管理系统</title>
    <jsp:include page="../common/css.jsp"></jsp:include>
    <style>
        body {
            background-image: url("static/images/04.jpg");
        }
    </style>
</head>

<body class="bootstrap-admin-with-small-navbar">
<jsp:include page="../common/top.jsp"></jsp:include>

<div class="container">
    <!-- left, vertical navbar & content -->
    <div class="row">
        <jsp:include page="../common/user_left.jsp"></jsp:include>

        <!-- content -->
        <div class="col-md-10">
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default bootstrap-admin-no-table-panel">
                        <div class="panel-heading">
                            <div class="text-muted bootstrap-admin-box-title">读者评价查询</div>
                        </div>
                        <div
                                class="bootstrap-admin-no-table-panel-content bootstrap-admin-panel-content collapse in">
                            <form class="form-horizontal" action="/books/problem?method=listByPage"
                                  method="post">
                                <div class="col-lg-8 form-group">
                                    <label class="col-lg-4 control-label" for="query_bname">评价信息</label>
                                    <div class="col-lg-8">
                                        <input class="form-control" id="bookName" name="name"
                                               type="text" value=""> <label class="control-label"
                                                                            for="query_bname" style="display: none;"></label>
                                    </div>
                                </div>
                                <div class="col-lg-4 form-group">
                                    <button type="submit" class="btn btn-primary" id="btn_query">查询</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <table id="data_list" class="table table-hover table-bordered"
                           cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th>ID</th>
                            <th>读者ID</th>
                            <th>标题</th>
                            <th>评价图书</th>
                            <th>评价内容</th>
                            <th>评价用户</th>
                            <th>评价时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pList}" var="problem" varStatus="status">
                            <tr>
                                <td>${start + status.index+1}</td>
                                <td>${problem.account}</td>
                                <td>${problem.title}</td>
                                <td>${problem.book}</td>
                                <td>${problem.content}</td>
                                <td>${problem.name}</td>
                                <td>${problem.time}</td>

                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    ${requestScope.pagation}
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="../common/userInfo.jsp"></jsp:include>
<jsp:include page="../common/js.jsp"></jsp:include>
</body>

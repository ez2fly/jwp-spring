<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="kr">
<head>
    <%@ include file="/include/header.jspf" %>
</head>
<body>
<%@ include file="/include/navigation.jspf" %>

<div class="container" id="main">
    <div class="col-md-6 col-md-offset-3">
        <div class="panel panel-default content-main">
        <form:form modelAttribute="user" name="userform" method="post" action="/users/create">
       			<div class="form-group">
                   <label for="userId">사용자 아이디</label>
                   <form:input path="userId" class="form-control" placeholder="User ID"/>
               </div>
               <div class="form-group">
                   <label for="password">비밀번호</label>
                   <form:input path="password" class="form-control" placeholder="Password" type="password" />
               </div>
               <div class="form-group">
                   <label for="name">이름</label>
                   <form:input path="name" class="form-control" placeholder="Name" />
               </div>
               <div class="form-group">
                   <label for="email">이메일</label>
                   <form:input path="email" class="form-control" placeholder="Email" type="email"/>
               </div>
               <button type="submit" class="btn btn-success clearfix pull-right">회원가입</button>
               <div class="clearfix" />
        </form:form>
        </div>
    </div>
</div>

<%@ include file="/include/footer.jspf" %>
</body>
</html>

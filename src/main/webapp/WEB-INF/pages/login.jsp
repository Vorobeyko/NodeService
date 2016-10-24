<%@ page language="java" contentType="text/html; charser=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en"
  ng-app="LoginPage">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/resources/css/style.css">
    <link rel="stylesheet" href="/resources/bootstrap/css/bootstrap.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>
    <script async src="/resources/jquery/jquery.js"></script>
    <script src="<c:url value="/resources/js/myAngular.js"/>"></script>
    <script async src="/resources/bootstrap/js/bootstrap.js"></script>
    <script async src="/resources/script.js"></script>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body
  ng-controller="LoginBody">
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-animate.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-aria.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-messages.min.js"></script>
<form class="parentForm" role="form"
  ng-submit="checkUsers()">
    <div  class="blockForm">
        <div class="form-group">
            <label>Windows login</label>
            <input type="text" name="username" class="form-control" placeholder="Login"
              ng-model="login">
        </div>
        <button type="submit" class="btn btn-default" value="login">Submit</button>
        <p id="errorCheckUser">{{loginError}}</p>
    </div>
</form>
</body>
</html>

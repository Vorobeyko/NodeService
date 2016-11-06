<%@ page language="java" contentType="text/html; charser=UTF-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="en"
  ng-app="LoginPage">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="resources/image/favicon.ico">
    <link rel="stylesheet" href="/resources/css/style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>
    <script async src="/resources/jquery/jquery.js"></script>
    <script src="<c:url value="/resources/js/controllers/loginController.js"/>"></script>
    <script async src="/resources/bootstrap/js/bootstrap.js"></script>
    <meta charset="UTF-8">
    <title>Login Page</title>
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

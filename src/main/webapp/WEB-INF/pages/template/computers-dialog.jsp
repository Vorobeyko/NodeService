<%--
  Created by IntelliJ IDEA.
  User: avorobey
  Date: 07.02.2017
  Time: 11:38
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charser=UTF-8" pageEncoding="utf-8" %>
<div class="modal-content" style="width: 464px;"
     ng-controller="SourceOperations" >
    <!-- ---------- Форма для отправления на сервер инфвормации о источнике ---------- -->
    <form id="form" role="form" name="sourceForm">
        <div class="input-group" ng-class="sourceForm.nameSourceIP.$invalid ? 'has-error has-feedback' : 'has-success  has-feedback'">
            <span class="input-group-addon" >IP</span>
            <input type="text" name="nameSourceIP" class="form-control"
                   ng-model="computer.computerIP"
                   ng-pattern="regexSourceIP"
                   required>
        </div>
        <div class="input-group" ng-class="sourceForm.nameSourceModel.$invalid ? 'has-error has-feedback' : 'has-success  has-feedback'">
            <span class="input-group-addon">Имя компьютера</span>
            <input type="text" class="form-control" name="nameSourceModel"
                   ng-model="computer.computerName"
                   required>
        </div>
        <div class="input-group">
            <span class="input-group-addon">Описание</span>
            <input type="text" class="form-control"
                   ng-model="computer.computerDescription">
        </div>
        <div class="input-group">
            <span class="input-group-addon">Владелец</span>
            <input type="text" class="form-control"
                   ng-model="computer.owner">
        </div>
        <!-- ------Кнопки для добавления, обновления и удаления записей ------ -->
        <div class="input-group">
            <button type="submit"  class="btn btn-success" id="succes"
                    ng-disabled="sourceForm.nameSourceIP.$invalid || sourceForm.nameSourceModel.$invalid"
                    ng-click="addComputer()">
                <span class="glyphicon glyphicon-plus" style="margin-right: 5px;" aria-hidden="true"></span>Добавить
            </button>
            <div id="close" class="btn btn-default"  style="margin-left: 5px;" ng-click="closeSourceInfoModal('#computers-dialog')">
                <span class="glyphicon glyphicon-remove"  style="margin-right: 5px;" aria-hidden="true"></span>Закрыть
            </div>
        </div>
        <p id="addSourceError">{{addSourceError}}</p>
    </form>
</div>

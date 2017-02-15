<%--
  Created by IntelliJ IDEA.
  User: avorobey
  Date: 07.02.2017
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charser=UTF-8" pageEncoding="utf-8" %>
<div class="modal-content"
     ng-controller="SourceOperations" >
    <script>
        $(document).ready(function(){
            $('[data-toggle="tooltip"]').tooltip();
        });
    </script>
    <!-- ---------- Форма для отправления на сервер инфвормации о источнике ---------- -->
    <form id="form" role="form" name="sourceForm">
        <div class="input-group" ng-class="sourceForm.nameSourceIP.$invalid ? 'has-error has-feedback' : 'has-success  has-feedback'">
            <span class="input-group-addon" >IP</span>
            <input type="text" name="nameSourceIP" class="form-control" ng-model="ch_computer.computerIP" ng-pattern="regexSourceIP"
                   required disabled>
        </div>
        <div class="input-group" ng-class="sourceForm.nameSourceModel.$invalid ? 'has-error has-feedback' : 'has-success  has-feedback'">
            <span class="input-group-addon">Имя компьютера</span>
            <input type="text" class="form-control" name="nameSourceModel" ng-model="ch_computer.computerName" required>
        </div>
        <div class="input-group">
            <span class="input-group-addon">Описание</span>
            <input type="text" class="form-control" ng-model="ch_computer.computerDescription">
        </div>
        <div class="input-group">
            <span class="input-group-addon">Владелец</span>
            <input type="text" class="form-control" ng-model="ch_computer.owner">
        </div>
        <!-- ------Кнопки для добавления, обновления и удаления записей ------ -->
        <div class="input-group">
            <button type="submit" class="btn btn-info" id="Update" name="Update" value="Update"
                    ng-disabled="sourceForm.nameSourceIP.$invalid || sourceForm.nameSourceModel.$invalid"
                    ng-click="updateComputer()"
                    data-toggle="tooltip" data-placement="top" title="Обновить информацию о компьютере">
                <span class="glyphicon glyphicon-refresh" style="margin-right: 5px;" aria-hidden="true"></span>Изменить
            </button>
            <div  class="btn btn-danger show-modal" id="delete"
                  ng-disabled="sourceForm.nameSourceIP.$invalid
                || sourceForm.nameSourceModel.$invalid"
                  ng-click="showCheckDeleteSource('#checkDeleteComputer')"
                  data-toggle="tooltip" data-placement="top" title="Удалить компьютер">
                <span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
            </div>
            <div id="close" class="btn btn-default" style="margin-left: 5px;" ng-click="closeSourceInfoModal('#change-computers-dialog')"
                 data-toggle="tooltip" data-placement="top" title="Закрыть модальное окно">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </div>
            <div ng-show="isRequest" style="display:inline-block">
                <span class="glyphicon glyphicon-refresh glyphicon-refresh-animate" style="margin-left: 5px;" aria-hidden="true"></span>
            </div>
        </div>
        <p id="addSourceError">{{addSourceError}}</p>
        <p id="addSourceSuccess">{{addSourceSuccess}}</p>
        <!-- ------ Модальное окно для подтверждения удаления источника ------  -->
        <div class="modal fade" id="checkDeleteComputer" tabindex="-1" role="dialog">
            <div class="modal-dialog checkDeleteSource">
                <div class="modal-content" style="width: 180px;">
                    <div class="checkContent">
                        <div>
                            <p style="text-align: center">Ты уверен, что хочешь удалить устройство?</p>
                        </div>
                        <button type="submit" class="btn btn-danger" name="delete" ng-click="deleteComputer()">
                            <span class="glyphicon glyphicon-trash" style="margin-right: 5px;" aria-hidden="true"></span>Да
                        </button>
                        <div id="close" class="btn btn-default" onclick="$('#checkDeleteComputer').modal('hide')">
                            <span class="glyphicon glyphicon-remove" style="margin-right: 5px;" aria-hidden="true"></span>Нет
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </form>
</div>

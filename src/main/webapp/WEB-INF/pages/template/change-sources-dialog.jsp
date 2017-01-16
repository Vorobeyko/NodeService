<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charser=UTF-8" pageEncoding="utf-8" %>
<div class="modal-content"
  ng-controller="SourceOperations" >
    <!-- ---------- Форма для отправления на сервер инфвормации о источнике ---------- -->
    <form id="form" role="form" name="sourceForm">
        <div class="input-group" ng-class="sourceForm.nameSourceIP.$invalid ? 'has-error has-feedback' : 'has-success  has-feedback'">
            <span class="input-group-addon" >IP</span>
            <input type="text" name="nameSourceIP" class="form-control"
              ng-model="sources.sourceIP"
              ng-pattern="regexSourceIP"
              required>
        </div>
        <div class="input-group" ng-class="sourceForm.nameSourceModel.$invalid ? 'has-error has-feedback' : 'has-success  has-feedback'">
            <span class="input-group-addon">Модель</span>
            <input type="text" class="form-control" name="nameSourceModel"
              ng-model="sources.sourceModel"
              required>
        </div>
        <div class="input-group">
            <span class="input-group-addon">Описание</span>
            <input type="text" class="form-control"
              ng-model="sources.sourceDescription">
        </div>
        <div class="input-group">
            <span class="input-group-addon">Комментарий</span>
            <input type="text" class="form-control"
              ng-model="sources.comments">
        </div>
        <div class="input-group date">
            <span class="input-group-addon">Срок</span>
            <input type="datetime" class="form-control" id="datetimepicker" name="nameDueData"
              ng-model="sources.dueData">
            <span class="glyphicon form-control-feedback glyphicon-remove" style="display:none" aria-hidden="true"></span>
        </div>

        <!-- ------Кнопки для добавления, обновления и удаления записей ------ -->
        <div class="input-group">
            <button type="submit" class="btn btn-info" id="Update" name="Update" value="Update"
              ng-disabled="sourceForm.nameSourceIP.$invalid
                || sourceForm.nameSourceModel.$invalid"
              ng-click="updateSource()">
                <span class="glyphicon glyphicon-refresh" style="margin-right: 5px;" aria-hidden="true"></span>Обновить
            </button>
            <button type="submit" class="btn btn-warning" id="removeFromReservation" name="removeFromReservation" value="removeFromReservation"
              ng-disabled="sourceForm.nameSourceIP.$invalid
                || sourceForm.nameSourceModel.$invalid
                || sources.dueData == undefined
                || sources.dueData == ''"
              ng-click="removeFromReservation()">
                <span class="glyphicon glyphicon-refresh" style="margin-right: 5px;" aria-hidden="true"></span>Сбросить
            </button>
            <div  class="btn btn-danger show-modal" id="delete"
              ng-disabled="sourceForm.nameSourceIP.$invalid
                || sourceForm.nameSourceModel.$invalid"
              ng-click="(sourceForm.nameSourceIP.$invalid || sourceForm.nameSourceModel.$invalid)
                ? null : showCheckDeleteSource()">
                <span class="glyphicon glyphicon-trash" style="margin-right: 5px;" aria-hidden="true"></span>Удалить
            </div>
            <div id="close" class="btn btn-default"  style="margin-left: 5px;" ng-click="closeSourceInfoModal('#change-sources-dialog')"">
                <span class="glyphicon glyphicon-remove" " style="margin-right: 5px;" aria-hidden="true"></span>Закрыть
            </div>
        </div>
        <p id="addSourceError">{{addSourceError}}</p>
        <!-- ------ Модальное окно для подтверждения удаления источника ------  -->
        <div class="modal fade" id="checkDeleteSource" tabindex="-1" role="dialog">
            <div class="modal-dialog checkDeleteSource">
                <div class="modal-content" style="width: 180px;">
                    <div class="checkContent">
                        <div>
                            <p style="text-align: center">Ты уверен, что хочешь удалить устройство?</p>
                        </div>
                        <button type="submit" class="btn btn-danger" name="delete" ng-click="deleteSource()">
                            <span class="glyphicon glyphicon-trash" style="margin-right: 5px;" aria-hidden="true"></span>Да
                        </button>
                        <div id="close" class="btn btn-default" onclick="$('#checkDeleteSource').modal('hide')">
                            <span class="glyphicon glyphicon-remove" style="margin-right: 5px;" aria-hidden="true"></span>Нет
                        </div>
                    </div>
                </div>
            </div>
        </div>
      </form>
</div>

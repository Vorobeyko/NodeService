<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charser=UTF-8" pageEncoding="utf-8" %>
<div class="modal-content" style="width: 464px;"
  ng-controller="SourceOperations" >
    <!-- ---------- Форма для отправления на сервер инфвормации о источнике ---------- -->
    <form id="form" role="form" name="sourceForm">
      <div class="input-group">
        <span class="input-group-addon">
          <input type="radio" name="sourceType" ng-model="sourceType" value="PTZ"> Поворотная камера
        </span>
        <span class="input-group-addon">
          <input type="radio" name="sourceType" ng-model="sourceType" value="Stationary"> Стационарная камера
        </span>
      </div>
      <div class="input-group" ng-class="sourceForm.nameSourceIP.$invalid ? 'has-error has-feedback' : 'has-success  has-feedback'">
          <span class="input-group-addon" >IP</span>
          <input type="text" name="nameSourceIP" class="form-control"
            ng-model="addSource.sourceIP"
            ng-pattern="regexSourceIP"
            required>
      </div>
      <div class="input-group" ng-class="sourceForm.nameSourceModel.$invalid ? 'has-error has-feedback' : 'has-success  has-feedback'">
          <span class="input-group-addon">Модель</span>
          <input type="text" class="form-control" name="nameSourceModel"
            ng-model="addSource.sourceModel"
            required>
      </div>
      <div class="input-group">
          <span class="input-group-addon">Описание</span>
          <input type="text" class="form-control"
            ng-model="addSource.sourceDescription">
      </div>
        <div class="input-group">
            <span class="input-group-addon">Аудио-кодек</span>
            <input type="text" class="form-control"
                   ng-model="addSource.audioCodec">
        </div>
      <div class="input-group">
          <span class="input-group-addon">Комментарий</span>
          <input type="text" class="form-control"
            ng-model="addSource.comments">
      </div>
      <!-- ------Кнопки для добавления, обновления и удаления записей ------ -->
      <div class="input-group">
          <button type="submit"  class="btn btn-success" id="succes"
            ng-disabled="sourceForm.nameSourceIP.$invalid || sourceForm.nameSourceModel.$invalid || (sourceType == undefined || sourceType == false)"
            ng-click="addSource()">
              <span class="glyphicon glyphicon-plus" style="margin-right: 5px;" aria-hidden="true"></span>Добавить
          </button>
          <div id="close" class="btn btn-default"  style="margin-left: 5px;" ng-click="closeSourceInfoModal('#add-sources-dialog')">
              <span class="glyphicon glyphicon-remove"  style="margin-right: 5px;" aria-hidden="true"></span>Закрыть
          </div>
      </div>
      <p id="addSourceError">{{addSourceError}}</p>
    </form>
</div>

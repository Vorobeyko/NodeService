<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charser=UTF-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en"
  ng-app="WelcomePage">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="resources/image/favicon.ico">
    <link rel="stylesheet" href="resources/css/style.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
    <link rel="stylesheet" href="resources/jquery/jquery.datetimepicker.css" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link href="https://fonts.googleapis.com/css?family=Ubuntu:500" rel="stylesheet">
    <script src="resources/jquery/jquery.js"></script>
    <script src="resources/jquery/jquery.datetimepicker.full.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>
    <script src="<c:url value="/resources/js/controllers/welcomePageController.js"/>"></script>
    <script src="<c:url value="/resources/js/factories.js"/>"></script>
    <script src="<c:url value="/resources/js/directives.js"/>"></script>
    <title>Nodes Page Java</title>
</head>
<body
  ng-controller="WelcomePageBody">
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-animate.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-aria.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-messages.min.js"></script>
  <script src="https://code.angularjs.org/1.5.8/angular-cookies.js"></script>
<ul class="nav nav-tabs header" role="tablist">
    <li role="presentation" class="active"><a href="#ptz" aria-controls="ptz" role="tab" data-toggle="tab">Поворотные камеры</a></li>
    <li role="presentation"><a href="#stationary" aria-controls="stationary" role="tab" data-toggle="tab">Стационарные камеры</a></li>
    <li role="presentation"><a href="#computers" aria-controls="computers" role="tab" data-toggle="tab"
      ng-click="clickOnTabComputers()">Компьютеры</a></li>
    <button type="sudmit" class="btn btn-primary btn-group-sm btn-elvees"  id="button"
      ng-click="showDialogWithSourceInfo()">
        <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
    </button>
    <button type="sudmit" class="btn btn-warning btn-group-sm btn-elvees show-modal"
      ng-click="showHistory()">
        <span class="glyphicon glyphicon-time" aria-hidden="true"></span>
    </button>
    <div class="current-user">
      <ul class="nav nav-pills">
        <li class="dropdown" ng-init="currentUser = '${user.currentUser}'">
          <a class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
            <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
            {{currentUser}}</a>
          <ul class="dropdown-menu">
            <li><a ng-click="signOut()">Выйти из учетной записи</a></li>
          </ul>
        </li>
      </ul>
    </div>
</ul>
<div class="tab-content">
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="ptz">
            <div>
                <table class="table table-bordered sortIp" id="selectTr">
                    <thead>
                    <tr>
                        <th>IP<span class="glyphicon glyphicon-chevron-down" style="float: right;" aria-hidden="true"></span></th>
                        <th>Модель<span class="glyphicon glyphicon-chevron-down" style="float: right;" aria-hidden="true"></span></th>
                        <th>Описание</th>
                        <th>Забронировал<span class="glyphicon glyphicon-chevron-down" style="float: right;" aria-hidden="true"></span></th>
                        <th id="th-Comments">Комментарий</th>
                        <th>Срок<span class="glyphicon glyphicon-chevron-down" style="float: right;" aria-hidden="true"></span></th>
                        <th>Состояние<span class="glyphicon glyphicon-chevron-down" style="float: right;" aria-hidden="true"></span></th>
                    </tr>
                    </thead>
                    <tbody id="serviceNote" >
                            <tr name="data"
                              ng-class="sourceInfo.state === 'busy' ? 'busy-tr' : 'free-tr'"
                              ng-repeat="sourceInfo in sourcesInfo | orderBy:'sourceIp' "
                              ng-click="" ns-hover-note click-note>
                                <td id="td_1"><a href="http://{{sourceInfo.sourceIp}}">{{sourceInfo.sourceIp}}</a></td>
                                <td id="td_2">{{sourceInfo.sourceModel}}</td>
                                <td id="td_3"  class="tdDescription">{{sourceInfo.sourceDescription}}</td>
                                <td id="td_4">{{sourceInfo.ownBy}}</td>
                                <td id="td_5">{{sourceInfo.comments}}</td>
                                <td id="due-data">{{sourceInfo.dueData | date:'yyyy-MM-dd HH:mm:ss'}}</td>
                                <td id="td-state">{{sourceInfo.state}}</td>
                            </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="stationary"> В табе пока нет ничего, но вероятно появится скоро)) .</div>
        <div role="tabpanel" class="tab-pane" id="computers" >
              <table class="table table-bordered">
                  <thead>
                  <tr>
                      <th>IP</th>
                      <td>Имя</th>
                      <th>Описание</th>
                      <th>Владелец</th>
                  </tr>
                  </thead>
                  <tbody>
                          <tr name="data"
                            ng-repeat="comps in computers">
                              <td class="tab-computers-td-ip">{{comps.computerIP}}</td>
                              <td id="test" class="tab-computers-td-name"
                                ng-class="click.yes ? 'min-padding' : 'max-padding'">
                                <span
                                  ng-click="click.yes = true"
                                  ng-hide="click.yes"
                                  ng-show="!click.yes">{{comps.computerName}}</span>
                                  <div ng-show="click.yes" ng-hide="!click.yes" class="input-group">
                                    <textarea class="tab-computers-input">{{comps.computerName}}</textarea>
                                    <span class="input-group-btn">
                                      <button class="btn btn-default" type="button" ng-click="click.yes = false">X</button>
                                      <button class="btn btn-default" type="button">V</button>
                                    </span>
                                  </div>
                              </td>
                              <td class="tab-computers-td-description">
                                <span
                                  ng-click="click.yes = true"
                                  ng-hide="click.yes"
                                  ng-show="!click.yes">{{comps.computerDescription}}</span>
                                </td>
                                <td class="tab-computers-td-description">
                                  <span>{{comps.owner}}</span>
                                  </td>
                          </tr>
                  </tbody>
              </table>
        </div>
    </div>
</div>
<div class="modal fade " id="myModal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 464px;"
          ng-controller="SourceOperations">
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
                <div class="input-group checkbox">
                    <label>
                        <input type="checkbox"
                          ng-model="isReserv"> Хочешь забронировать источник? Отметь CheckBox!
                    </label>
                </div>
                <div class="input-group date">
                    <span class="input-group-addon">Срок</span>
                    <input type="datetime" class="form-control" id="datetimepicker"
                      ng-disabled="!isReserv"
                      ng-model="sources.dueData">
                    <span class="glyphicon form-control-feedback glyphicon-remove" style="display:none" aria-hidden="true"></span>
                </div>

                <!-- ------Кнопки для добавления, обновления и удаления записей ------ -->
                <div class="input-group">
                    <button type="submit"  class="btn btn-success" id="succes"
                      ng-disabled="sourceForm.nameSourceIP.$invalid || sourceForm.nameSourceModel.$invalid"
                      ng-click="addSource()" >
                        <span class="glyphicon glyphicon-plus" style="margin-right: 5px;" aria-hidden="true"></span>Добавить
                    </button>
                    <button type="submit" class="btn btn-info" id="Update" name="Update" value="Update"
                      ng-disabled="sourceForm.nameSourceIP.$invalid || sourceForm.nameSourceModel.$invalid"
                      ng-click="updateSource()">
                        <span class="glyphicon glyphicon-refresh" style="margin-right: 5px;" aria-hidden="true"></span>Обновить
                    </button>
                    <%-- data-toggle="modal" data-target="#checkDeleteSource" --%>
                    <div  class="btn btn-danger show-modal" id="delete"
                      ng-disabled="sourceForm.nameSourceIP.$invalid || sourceForm.nameSourceModel.$invalid"
                      ng-click="(sourceForm.nameSourceIP.$invalid || sourceForm.nameSourceModel.$invalid) ? null : showCheckDeleteSource()">
                        <span class="glyphicon glyphicon-trash" style="margin-right: 5px;" aria-hidden="true"></span>Удалить
                    </div>
                    <div id="close" class="btn btn-default"  style="margin-left: 5px;" ng-click="closeSourceInfoModal()"">
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
    </div>
</div>
<div class="modal fade " id="historyModal" tabindex="-1" role="dialog" aria-labelledby="checkDeleted">
    <div class="modal-dialog history" style="min-width: 1260px!important;max-width: 1500px!important;">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" ng-click="closeHistoryModal()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="gridSystemModalLabel">История для выбранного источника: {{historySelectedSource}}</h4>
          </div>
            <div role="tabpanel" class="tab-pane active" id="home" style="    margin: 10px;">
                <div>
                    <table class="table table-bordered" id="selectTr">
                      <thead>
                      <tr>
                        <th>Последнее обновление</th>
                        <th>IP</th>
                        <th>Модель</th>
                        <th>Описание</th>
                        <th style="width: 70px;">Забронировал</th>
                        <th id="th-Comments">Комментарий</th>
                        <th>Срок</th>
                      </tr>
                      </thead>
                        <tbody id="historyTable">
                          <tr name="data"
                            ng-repeat="history in sourceHistory">
                              <td>{{history.lastUpdated | date:'yyyy-MM-dd HH:mm:ss'}}</a></td>
                              <td>{{history.sourceIp}}</a></td>
                              <td>{{history.sourceModel}}</td>
                              <td class="tdDescription">{{history.sourceDescription}}</td>
                              <td>{{history.ownBy}}</td>
                              <td>{{history.comments}}</td>
                              <td>{{history.dueData | date:'yyyy-MM-dd HH:mm:ss   '}}</td>
                          </tr>
                        </tbody>
                    </table>
                    <p id="historyError"></p>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>

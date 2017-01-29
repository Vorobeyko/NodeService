<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charser=UTF-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en"
  ng-app="WelcomePage">
<head>
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
    <title>Сервис для бронирования камер</title>
    <script>
        $(document).ready(function(){
            $('[data-toggle="tooltip"]').tooltip();
        });
    </script>
</head>
<body
  ng-controller="WelcomePageBody">
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-animate.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-aria.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-messages.min.js"></script>
  <script src="https://code.angularjs.org/1.5.8/angular-cookies.js"></script>

  <ul class="nav nav-tabs header" role="tablist">
    <li role="presentation" class="active"><a href="#ptz" aria-controls="ptz"  role="tab" data-toggle="tab">Поворотные камеры</a></li>
    <li role="presentation"><a href="#stationary" aria-controls="stationary" role="tab" data-toggle="tab">Стационарные камеры</a></li>
    <!-- <li role="presentation"><a href="#computers" aria-controls="computers" role="tab" data-toggle="tab"
      ng-click="clickOnTabComputers()">Компьютеры</a>
    </li> -->
      <button type="submit"  class="btn btn-success btn-elvees" id="succes"
        ng-click="showDialogWithSourceInfo(null,'#add-sources-dialog')" data-toggle="tooltip" data-placement="bottom" title="Добавить новое устройство">
          <span class="glyphicon glyphicon-plus" style="margin-right: 5px;" aria-hidden="true"></span>Добавить устройство
      </button>
    <button type="submit"  class="btn btn-info btn-elvees"
            ng-click="updateTable()" data-toggle="tooltip" data-placement="bottom" title="Обновить содержимое таблицы">
        <span class="glyphicon glyphicon-refresh" id="shows" aria-hidden="true"></span>
    </button>
    <button type="sudmit" class="btn btn-warning btn-group-sm btn-elvees show-modal"
      ng-click="showHistory()" data-toggle="tooltip" data-placement="bottom" title="Посмотреть историю устройств">
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
  <div role="tabpanel" class="tab-pane active" id="ptz">
    <table class="table table-bordered sortIp" id="selectTr">
      <thead>
      <tr>
        <th>IP</th>
        <th>Модель</th>
        <th>Описание</th>
        <th>Забронировал</th>
        <th >Комментарий</th>
        <th>Срок</th>
        <th>Состояние</th>
      </tr>
      </thead>
      <tbody id="serviceNote" >
        <tr name="data"
          ng-class="sourceInfo.state === 'busy' ? 'busy-tr' : 'free-tr'"
          ng-repeat="sourceInfo in sourcesInfo | filter: { sourceType: 'PTZ'}"
          ng-click="showDialogWithSourceInfo(sourceInfo, '#change-sources-dialog')" click-note >
            <td><a href="http://{{sourceInfo.sourceIp}}">{{sourceInfo.sourceIp}}</a></td>
            <td>{{sourceInfo.sourceModel}}</td>
            <td>{{sourceInfo.sourceDescription}}</td>
            <td>{{sourceInfo.ownBy}}</td>
            <td>{{sourceInfo.comments}}</td>
            <td id="due-data">{{sourceInfo.dueData | date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td id="td-state">{{sourceInfo.state}}</td>
        </tr>
      </tbody>
    </table>
  </div>
  <div role="tabpanel" class="tab-pane" id="stationary">
    <table class="table table-bordered sortIp" id="selectTr">
      <thead>
      <tr>
        <th>IP</th>
        <th>Модель</th>
        <th>Описание</th>
        <th>Забронировал</th>
        <th>Комментарий</th>
        <th>Срок</th>
        <th>Состояние</th>
      </tr>
      </thead>
      <tbody id="serviceNote" >
        <tr name="data"
          ng-class="sourceInfo.state === 'busy' ? 'busy-tr' : 'free-tr'"
          ng-repeat="sourceInfo in sourcesInfo | filter: { sourceType: 'Stationary'}"
          ng-click="showDialogWithSourceInfo(sourceInfo, '#change-sources-dialog')" click-note>
            <td><a href="http://{{sourceInfo.sourceIp}}">{{sourceInfo.sourceIp}}</a></td>
            <td>{{sourceInfo.sourceModel}}</td>
            <td>{{sourceInfo.sourceDescription}}</td>
            <td>{{sourceInfo.ownBy}}</td>
            <td>{{sourceInfo.comments}}</td>
            <td id="due-data">{{sourceInfo.dueData | date:'yyyy-MM-dd HH:mm:ss'}}</td>
            <td id="td-state">{{sourceInfo.state}}</td>
        </tr>
      </tbody>
    </table>
  </div>
  <div role="tabpanel" class="tab-pane" id="computers">
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

        <!-- ng-click="clickOnRowComputersTable($index, $event, comps.computerName)" -->
        <tr name="data"
          ng-repeat="comps in computers">
            <td class="tab-computers-td-ip">{{comps.computerIP}}</td>
            <td id="test" class="tab-computers-td-name">
              <span
                ng-click="'computerNameId_{{$index}}' = true">{{comps.computerName}}</span>
                <div class="input-group"
                ng-show="'computerNameId_{{$index}}'"
                ng-hide="!'computerNameId_{{$index}}'">
                  <textarea class="tab-computers-input"></textarea>
                  <span class="input-group-btn">
                    <button class="btn btn-default" type="button"
                    >X</button>
                    <button class="btn btn-default" type="button">V</button>
                  </span>
                </div>
            </td>
            <td class="tab-computers-td-description"
              ng-click="clickOnRowComputersTable2($index, $event, comps.computerDescription)">
              <span>{{comps.computerDescription}}</span>
              </td>
              <td class="tab-computers-td-description">
                <span>{{comps.owner}}</span>
                </td>
        </tr>
      </tbody>
    </table>
      <button type="sudmit" class="btn btn-warning btn-group-sm btn-add-computers-node"
        ng-click="addComputers()">
          <span class="glyphicon glyphicon-plus" aria-hidden="true">
      </button>
  </div>
</div>

<!-- Add Sources Dialog Template -->
<div class="modal fade " id="add-sources-dialog" tabindex="-1" role="dialog">
    <div class="modal-dialog" add-sources-dialog></div>
</div>
<!-- End Template -->

<!-- Update Sources Dialog Template -->
<div class="modal fade " id="change-sources-dialog" tabindex="-1" role="dialog">
    <div class="modal-dialog" change-sources-dialog></div>
</div>
<!-- End Template -->

<div class="modal fade " id="historyModal" tabindex="-1" role="dialog" aria-labelledby="checkDeleted">
    <div class="modal-dialog history" style="min-width: 1260px!important;max-width: 1500px!important;">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" ng-click="closeHistoryModal()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="gridSystemModalLabel">История для выбранного источника:
                <select class="form-control form-control-override" ng-model="data.singleSelect" ng-change="showHistoryForSource(data.singleSelect)">
                    <optgroup label="Поворотные видеокамеры">
                        <option ng-repeat="sourceInfo in sourcesInfo | filter: { sourceType: 'PTZ'}" >{{sourceInfo.sourceIp}}</option>
                    </optgroup>
                    <optgroup label="Стационарные видеокамеры">
                        <option ng-repeat="sourceInfo in sourcesInfo | filter: { sourceType: 'Stationary'}" >{{sourceInfo.sourceIp}}</option>
                    </optgroup>
                </select>
            </h4>
          </div>
            <div role="tabpanel" class="tab-pane active" id="home" style="    margin: 10px;">
                <div>
                    <table class="table table-bordered" id="selectTr">
                      <thead>
                      <tr>
                          <th>Последнее обновление</th>
                          <th>Изменил</th>
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
                              <td>{{history.lastUpdated | date:'yyyy-MM-dd HH:mm:ss'}}</td>
                              <td>{{history.whoUpdated}}</td>
                              <td>{{history.sourceIp}}</td>
                              <td>{{history.sourceModel}}</td>
                              <td class="tdDescription">{{history.sourceDescription}}</td>
                              <td>{{history.ownBy}}</td>
                              <td>{{history.comments}}</td>
                              <td>{{history.dueData | date:'yyyy-MM-dd HH:mm:ss'}}</td>
                          </tr>
                        </tbody>
                    </table>
                    <p id="historyError"></p>
                </div>
            </div>
        </div>
    </div>
</div>
  <footer class="navbar-fixed-bottom footer bs-docs-footer">
      <div class="container">
          <p class="bs-docs-footer-p"><a href="mailto:avorobey@elvees.com">@Alexandr Vorobey</a></p>
          <ul class="bs-docs-footer-links">
              <li><a href="#">Предложить дороботку</a></li>
              <li><hr width="1" size="5"></li>
              <li><a href="/faq">FAQ</a></li>
          </ul>
      </div>
  </footer>
</body>
</html>

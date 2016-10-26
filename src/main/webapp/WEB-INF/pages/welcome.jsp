<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charser=UTF-8" pageEncoding="utf-8" %>
<!DOCTYPE html>
<html lang="en"
  ng-app="WelcomePage">
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="resources/image/favicon.ico">
    <link rel="stylesheet" href="resources/css/style.css">
    <link rel="stylesheet" href="resources/bootstrap/css/bootstrap.css" />
    <link rel="stylesheet" href="resources/jquery/jquery.datetimepicker.css" />
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <script src="resources/jquery/jquery.js"></script>
    <script src="resources/jquery/jquery.tablesorter.js"></script>
    <script src="resources/jquery/jquery.metadata.js"></script>
    <script src="resources/datetimeformat.js"></script>
    <script src="resources/jquery/jquery.datetimepicker.full.js"></script>
    <script src="resources/jquery/jquery.datetimepicker.js"></script>
    <script src="resources/bootstrap/js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular.min.js"></script>
    <script src="<c:url value="/resources/js/myAngular.js"/>"></script>
    <title>Nodes Page Java</title>
</head>
<body
  ng-controller="WelcomePageBody">
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-animate.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-aria.min.js"></script>
  <script src="http://ajax.googleapis.com/ajax/libs/angularjs/1.5.8/angular-messages.min.js"></script>
<ul class="nav nav-tabs" role="tablist">
    <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">PTZ</a></li>
    <li role="presentation"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Stationary</a></li>
    <button type="sudmit" class="btn btn-warning btn-group-sm btn-elvees show-modal" style="right: 70px"
      ng-click="showHistory()">
        <span class="glyphicon glyphicon-time" aria-hidden="true"></span>
    </button>
    <button type="sudmit" class="btn btn-primary btn-group-sm btn-elvees"  id="button"
      ng-click="showDialogWithSourceInfo()">
        <span class="glyphicon glyphicon-cog" aria-hidden="true"></span>
    </button>
</ul>
<div class="tab-content">
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <div>
                <table class="table table-bordered sortIp" id="selectTr">
                    <thead>
                    <tr>
                        <th>Ip <span class="glyphicon glyphicon-chevron-down" style="float: right;" aria-hidden="true"></span></th>
                        <th>Model<span class="glyphicon glyphicon-chevron-down" style="float: right;" aria-hidden="true"></span></th>
                        <th>Description</th>
                        <th>Own By<span class="glyphicon glyphicon-chevron-down" style="float: right;" aria-hidden="true"></span></th>
                        <th id="th-Comments">Comments</th>
                        <th>Due Data<span class="glyphicon glyphicon-chevron-down" style="float: right;" aria-hidden="true"></span></th>
                        <th>State<span class="glyphicon glyphicon-chevron-down" style="float: right;" aria-hidden="true"></span></th>
                    </tr>
                    </thead>
                    <tbody id="serviceNote" >
                            <tr name="data"
                              ng-class="sourceInfo.state === 'busy' ? 'busy-tr' : 'free-tr'"
                              ng-repeat="sourceInfo in sourcesInfo | orderBy:'sourceIp' " ns-hover-note click-note>
                                <td id="td_1"><a href="http://{{sourceInfo.sourceIp}}">{{sourceInfo.sourceIp}}</a></td>
                                <td id="td_2">{{sourceInfo.sourceModel}}</td>
                                <td id="td_3" class="tdDescription">{{sourceInfo.sourceDescription}}</td>
                                <td id="td_4">{{sourceInfo.ownBy}}</td>
                                <td id="td_5">{{sourceInfo.comments}}</td>
                                <td id="due-data">{{sourceInfo.dueData| date:'yyyy-MM-dd HH:mm:ss'}}</td>
                                <td id="td-state">{{sourceInfo.state}}</td>
                            </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div role="tabpanel" class="tab-pane" id="profile"> В табе пока нет ничего, но вероятно появится скоро)) .</div>
    </div>
</div>
<div class="modal fade " id="myModal" tabindex="-1" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content"
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
                    <span class="input-group-addon">Model</span>
                    <input type="text" class="form-control" name="nameSourceModel"
                      ng-model="sources.sourceModel"
                      required>
                </div>
                <div class="input-group">
                    <span class="input-group-addon">Description</span>
                    <input type="text" class="form-control"
                      ng-model="sources.sourceDescription">
                </div>
                <div class="input-group">
                    <span class="input-group-addon">Comment</span>
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
                    <span class="input-group-addon">Due Data</span>
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
                        <span class="glyphicon glyphicon-plus" style="margin-right: 5px;" aria-hidden="true"></span>Add source
                    </button>
                    <button type="submit" class="btn btn-info" id="Update" name="Update" value="Update"
                      ng-disabled="sourceForm.nameSourceIP.$invalid || sourceForm.nameSourceModel.$invalid"
                      ng-click="updateSource()">
                        <span class="glyphicon glyphicon-refresh" style="margin-right: 5px;" aria-hidden="true"></span>Update source
                    </button>
                    <%-- data-toggle="modal" data-target="#checkDeleteSource" --%>
                    <div  class="btn btn-danger show-modal" id="delete"
                      ng-disabled="sourceForm.nameSourceIP.$invalid || sourceForm.nameSourceModel.$invalid"
                      ng-click="(sourceForm.nameSourceIP.$invalid || sourceForm.nameSourceModel.$invalid) ? null : showCheckDeleteSource()">
                        <span class="glyphicon glyphicon-trash" style="margin-right: 5px;" aria-hidden="true"></span>Delete source
                    </div>
                    <div id="close" class="btn btn-default"  style="margin-left: 5px;" ng-click="closeSourceInfoModal()"">
                        <span class="glyphicon glyphicon-remove" " style="margin-right: 5px;" aria-hidden="true"></span>Close
                    </div>
                </div>
                <p id="addSourceError">{{addSourceError}}</p>
                <!-- ------ Модальное окно для подтверждения удаления источника ------  -->
                <div class="modal fade" id="checkDeleteSource" tabindex="-1" role="dialog">
                    <div class="modal-dialog checkDeleteSource">
                        <div class="modal-content">
                            <div class="checkContent">
                                <div>
                                    <p style="text-align: center">Are you sure?</p>
                                </div>
                                <button type="submit" class="btn btn-danger" name="delete" ng-click="deleteSource()">
                                    <span class="glyphicon glyphicon-trash" style="margin-right: 5px;" aria-hidden="true"></span>Yes
                                </button>
                                <div id="close" class="btn btn-default" onclick="$('#checkDeleteSource').modal('hide')">
                                    <span class="glyphicon glyphicon-remove" style="margin-right: 5px;" aria-hidden="true"></span>No
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
    <div class="modal-dialog history">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" ng-click="closeHistoryModal()" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title" id="gridSystemModalLabel">История для выбранного источника: {{historySelectedSource}}</h4>
          </div>
            <div role="tabpanel" class="tab-pane active" id="home" style="    margin: 10px;">
                <div>
                    <table class="table table-bordered" id="selectTr">
                        <tbody id="historyTable">
                          <tr name="data"
                            ng-repeat="history in sourceHistory">
                              <td>{{history.sourceIp}}</a></td>
                              <td>{{history.sourceModel}}</td>
                              <td class="tdDescription">{{history.sourceDescription}}</td>
                              <td>{{history.ownBy}}</td>
                              <td>{{history.comments}}</td>
                              <td>{{history.dueData| date:'yyyy-MM-dd HH:mm:ss   '}}</td>
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

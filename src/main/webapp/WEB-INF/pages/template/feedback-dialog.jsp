<%--
  Created by IntelliJ IDEA.
  User: VorobeyAlex
  Date: 30.01.2017
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charser=UTF-8" pageEncoding="utf-8" %>
<script>
    $(document).ready(function(){
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>
<div class="modal-content" ng-controller="SourceOperations">
    <!-- ---------- Форма для отправления на сервер инфвормации о источнике ---------- -->
    <form id="form" role="form" name="feedbackForm">
        <div class="input-group">
            <label for="message-text" class="control-label">Сообщение:</label>
            <textarea class="form-control" ng-model="feedbackMessage" name="feedbackMessage" id="message-text" style="margin: 0px; height: 139px; width: 485px; resize:vertical;"></textarea>
        </div>
        <!-- ------Кнопки для добавления, обновления и удаления записей ------ -->
        <div class="input-group">
            <button type="submit" class="btn btn-info" ng-click="sendFeedbackMessage()"
                    data-toggle="tooltip" data-placement="top" title="Отправить предложение"
                    ng-disabled="feedbackMessage == '' ||  feedbackMessage == undefined">
                <span class="glyphicon glyphicon-envelope" style="margin-right: 5px;" aria-hidden="true"></span>Отправить
            </button>
            <div id="close" class="btn btn-default"  style="margin-left: 5px;" ng-click="closeSourceInfoModal('#feedback-dialog')"
                 data-toggle="tooltip" data-placement="top" title="Закрыть модальное окно">
                <span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
            </div>
            <div ng-show="isRequest" style="display:inline-block">
                <span class="glyphicon glyphicon-refresh glyphicon-refresh-animate" style="margin-left: 5px;" aria-hidden="true"></span>
            </div>
        </div>
    </form>
</div>


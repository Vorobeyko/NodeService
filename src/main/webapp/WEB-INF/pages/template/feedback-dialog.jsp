<%--
  Created by IntelliJ IDEA.
  User: VorobeyAlex
  Date: 30.01.2017
  Time: 22:48
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charser=UTF-8" pageEncoding="utf-8" %>
<div class="modal-content">
    <script>
        $(document).ready(function(){
            $('[data-toggle="tooltip"]').tooltip();
        });
    </script>
    <!-- ---------- Форма для отправления на сервер инфвормации о источнике ---------- -->
    <form id="form" role="form" name="feedbackDialog">
        <div class="input-group">
            <label for="message-text" class="control-label">Message:</label>
            <textarea class="form-control" id="message-text"></textarea>
        </div>
        <!-- ------Кнопки для добавления, обновления и удаления записей ------ -->
        <div class="input-group">
            <button type="submit" class="btn btn-info" id="Update" name="Update" value="Update"
                    data-toggle="tooltip" data-placement="top" title="Забронировать устройство">
                <span class="glyphicon glyphicon-refresh" style="margin-right: 5px;" aria-hidden="true"></span>Забронировать
            </button>
        </div>
    </form>
</div>


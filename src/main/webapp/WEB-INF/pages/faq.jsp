<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: avorobey
  Date: 28.01.2017
  Time: 23:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Node Service - FAQ</title>
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
    <script>
        $('.collapse').collapse();

        // For tooltip
        $(document).ready(function(){
            $('[data-toggle="tooltip"]').tooltip();
        });
    </script>
</head>
<body class="faq">
<button type="submit" onclick="location.href = '/welcome';" class="btn btn-faq-previous" id="succes" data-toggle="tooltip" data-placement="right    " title="Вернуться на главную">
    <span class="glyphicon glyphicon-arrow-left" style="top: 0!important;" aria-hidden="true"></span>
</button>
<div class="faq-container">
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
    <div class="panel panel-default">
        <div class="panel-heading" id="headingOne"
             role="button" data-toggle="collapse" data-parent="#accordion"
             data-target="#collapseOne" aria-expanded="false" aria-controls="collapseOne">
            <h4 class="panel-title">Бронирование устройства</h4>
        </div>
        <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
            <div class="panel-body">
                <p> Для бронирования устройства необходимо сделать следующее:</p>
                <ul class="faq-reservation-ul">
                    <li>На главной старнице сервиса выбрать камеру со списка кликом ЛКМ по записи. </li>
                    <li>После клика появится диалоговое окно с возможностью ввести модель устройства, описание, комментарий и указать срок до которого необходимо забронировать устройство.</li>
                    <li>Для бронирования необходимо ЛКМ кликнуть в поле "Срок" (Рис. 1)</li>
                    <img src="<c:url value="/resources/image/faq/faq-reservation-1.JPG"/>" alt=""/>
                    <p>Рис. 1</p>
                    <li>Появится календарь с возможностью выбора даты и времени бронирования (Рис. 2).</li>
                    <img src="<c:url value="/resources/image/faq/faq-reservation-calendar.JPG"/>" alt="">
                    <p>Рис. 2</p>
                    <li>После чего необходимо выбрать дату и время до которого нужно забронировать устройство. И нажать на кнопку "Забронировать" (Рис. 3)</li>
                    <img src="<c:url value="/resources/image/faq/faq-reservation-btn.JPG"/>" alt="">
                    <p>Рис. 3</p>
                    <li>Если операция бронирования выполнена успешно, то появится сообщение "Операция выполнена успешна". </li>
                </ul>
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading" id="headingTwo"
             role="button" data-toggle="collapse" data-parent="#accordion" data-target="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
            <h4 class="panel-title">Добавление нового устройства</h4>
        </div>
        <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
            <div class="panel-body">
                <p>Для добавления нового устройства необходимо:</p>
                <ul class="faq-reservation-ul">
                    <li>Кликнуть на кнопку в правом верхнем углу - "Добавить устройство" (Рис. 1)</li>
                    <img src="<c:url value="/resources/image/faq/faq-add-device-btn.JPG"/>" alt="">
                    <p>Рис. 1</p>
                    <li>Появится диалоговое окно с формой для заполнения информации об устройстве (Рис. 2).</li>
                    <img src="<c:url value="/resources/image/faq/faq-add-device-modal.JPG"/>" alt="">
                    <p>Рис. 2</p>
                    <li>После чего небходимо выбрать тип устройства (Рис. 3)</li>
                    <img src="<c:url value="/resources/image/faq/faq-add-device-type.JPG"/>" alt="">
                    <p>Рис. 3</p>
                    <li>Заполнить все поля: Ip устройства, модель, описание и комментарий. Поля IP и модель обязательны для заполнения.</li>
                    <li>Нажать на кнопку "Добавить". При успешном добавлении, появится сообщение "Операция выполнена успешно".</li>
                </ul>
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading" id="headingThree"
             role="button" data-toggle="collapse" data-parent="#accordion"
             data-target="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
            <h4 class="panel-title">Удаление устройства</h4>
        </div>
        <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
            <div class="panel-body">
                <p>Для удаления устройства необходимо:</p>
                <ul class="faq-reservation-ul">
                    <li>На главной старнице сервиса выбрать камеру со списка кликом ЛКМ по записи. </li>
                    <li>После клика появится диалоговое окно с возможностью ввести модель устройства, описание, комментарий и указать срок до которого необходимо забронировать устройство.</li>
                    <li>Для удаления необходимо ЛКМ кликнуть на кнопку с изображением "мусорного ведра" (Рис. 1)</li>
                    <img src="<c:url value="/resources/image/faq/faq-delete-device-modal-btn.JPG"/>" alt=""/>
                    <p>Рис. 1</p>
                    <li>Появится окно с подтверждением удаления. При нажатии на кнопку "Да" устройство удалится из сервиса, "Нет" - не удалится.</li>
                </ul>
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading"  id="headingFour"
             role="button" data-toggle="collapse" data-parent="#accordion" data-target="#collapseFour"
             aria-expanded="false" aria-controls="collapseFour">
            <h4 class="panel-title">Сброс бронирования устройства</h4>
        </div>
        <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
            <div class="panel-body">
                <p>Для сброс бронирования необходимо:</p>
                <ul class="faq-reservation-ul">
                    <li>На главной старнице сервиса выбрать камеру со списка кликом ЛКМ по записи. </li>
                    <li>После клика появится диалоговое окно с возможностью ввести модель устройства, описание, комментарий и указать срок до которого необходимо забронировать устройство.</li>
                    <li>Для удаления необходимо ЛКМ кликнуть на кнопку "Сбросить бронь" (Рис. 1)</li>
                    <img src="<c:url value="/resources/image/faq/faq-remove-reservation-btn.JPG"/>" alt=""/>
                    <p>Рис. 1</p>
                    <li>Бронь сбросится. После успешного сброса появится сообщение "Операция выполнена успешно".</li>
                </ul>
            </div>
        </div>
    </div>

    <div class="panel panel-default">
        <div class="panel-heading" id="history-panel"
             role="button" data-toggle="collapse" data-parent="#accordion" data-target="#collapse-history"
             aria-expanded="false" aria-controls="collapse-history">
            <h4 class="panel-title">Просмотр истории бронирования устройства и изменения информации об устройстве. </h4>
        </div>
        <div id="collapse-history" class="panel-collapse collapse" role="tabpanel" aria-labelledby="history-panel">
            <div class="panel-body">
                <p>Для просмотра истории бронирования и изменения информации устройства необходимо:</p>
                <ul class="faq-reservation-ul">
                    <li>На главной странице кликнуть на кнопку с изображением часов (Рис. 1)</li>
                    <img src="<c:url value="/resources/image/faq/faq-show-history-btn.JPG"/>" alt="">
                    <p>Рис. 1</p>
                    <li>Появится диалоговое окно. В нем необходимо кликнуть на выпадающий список (Рис. 2) и выбрать интересующее устройство.</li>
                    <img src="<c:url value="/resources/image/faq/faq-show-history-select.jpg"/>" alt="">
                    <p>Рис. 2</p>
                    <li>Псле выбора устройства в диалоговом окне появится 5 последних записей с изменениями для выбранного источника (Рис. 3).</li>
                    <img src="" alt="">
                </ul>
            </div>
        </div>
    </div>
</div>
</div>
</body>
</html>
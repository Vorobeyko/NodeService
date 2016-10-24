function getHistory(item){
    var xmlResponse = item.getElementsByTagName("Cameras")[0];
    if (xmlResponse.innerHTML != "null" && xmlResponse.innerHTML != "not_exists") {
        var historyTable = $("tbody#historyTable");
        var cameras = xmlResponse.getElementsByTagName("Camera");
        for (i = 0; i < cameras.length; i++){
            var lastUpdated = cameras[i].getElementsByTagName("LastUpdated");
            var sourceIp = cameras[i].getElementsByTagName("SourceIp");
            var sourceModel = cameras[i].getElementsByTagName("SourceModel");
            var sourceDescription = cameras[i].getElementsByTagName("SourceDescription");
            var ownBy = cameras[i].getElementsByTagName("OwnBy");
            var comments = cameras[i].getElementsByTagName("Comments");
            var dueData = cameras[i].getElementsByTagName("DueData");
            var nodeCameras =
                "<tr name='data'  value='active'>" +
                "<td id='historyTd_1'>" + lastUpdated[0].innerHTML + "</td>" +
                "<td id='historyTd_2'>" + sourceIp[0].innerHTML + "</td>" +
                "<td id='historyTd_3'>" + sourceModel[0].innerHTML + "</td>" +
                "<td id='historyTd_4' class='tdDescription'>" + sourceDescription[0].innerHTML + "</td>" +
                "<td id='historyTd_5'>" + ownBy[0].innerHTML + "</td>" +
                "<td id='historyTd_6'>" + comments[0].innerHTML + "</td>" +
                "<td id='historyTd_due-data'>" + dueData[0].innerHTML + "</td>" +
                "</tr>";
                historyTable.append(nodeCameras);
          }
    } else if (xmlResponse.innerHTML == "not_exists"){
        var historyTable = $("#historyError");
        historyTable.html(error);
    }else {
        var historyTable = $("#historyError");
        historyTable.html(error);
    }
}

$(document).ready(function(){
    /*Сортировка таблицы*/
    $("#selectTr").tablesorter();
    var isPaused = true; // Флаг для паузы таймера updatePage
    var updatePage = setInterval(function() {
      if (!isPaused){
        var tdDueData = document.querySelectorAll("td#due-data");
        var currentDate = new Date();
        for(i = 0; i < tdDueData.length; i++){
            if (tdDueData[i].innerHTML != "") {
                if(new Date(tdDueData[i].innerHTML) < currentDate){
                    document.location.reload();
                }
            }
          }
        }
    }, 10000);

    function datetime(startTime){
        jQuery('#datetimepicker').datetimepicker({
            format: "Y-m-d H:i:s",
            startDate:startTime,
            step: 5
        });
    }

    // /* Функция, которая отображает информацию записи в модальном окне */
    // $(function(){$("button#button").click(function(e) {
    //     inputSourceIp = $("input.sourceIp");
    //     inputSourceModel = $("input.sourceModel");
    //     inputSourceDueData = $("input.dueData");
    //     isPaused = true;
    //
    //     if ($("#selectTr tr.myactive").attr("class") == "myactive") {
    //         e.preventDefault();
    //         if ($("#selectTr tr.myactive td:eq(0)").text() != null){
    //             inputSourceIp.val($("#selectTr tr.myactive td:eq(0)").text());
    //             validate(inputSourceIp);
    //         } else {
    //             validate(inputSourceIp);
    //         }
    //         if ($("#selectTr tr.myactive td:eq(1)").text() != null){
    //             inputSourceModel.val($("#selectTr tr.myactive td:eq(1)").text());
    //             validate(inputSourceModel);
    //         } else {
    //             validate(inputSourceModel);
    //         }
    //         $("input.sourceDescription").val($("#selectTr tr.myactive td:eq(2)").text());
    //        // inputSourceOwnBy.val($("#selectTr tr.myactive td:eq(3)").text())
    //         $("input.comments").val($("#selectTr tr.myactive td:eq(4)").text());
    //         inputSourceDueData.val($("#selectTr tr.myactive td:eq(5)").text());
    //         var s = inputSourceDueData.val();
    //         datetime(s);
    //     } else {
    //         datetime(new Date());
    //     }
    //
    //     /*Проверяю на корректное заполнение формы, и в случае, если все заполнено корректно - кнопки активирую*/
    //     function buttonEnabled(bol){
    //         if (bol){
    //             $("button#succes").removeAttr("disabled");
    //             $("button#Update").removeAttr("disabled");
    //             $("div#delete").removeAttr("disabled");
    //         }else {
    //             $("button#succes").attr("disabled","");
    //             $("button#Update").attr("disabled","");
    //             $("div#delete").attr("disabled","");
    //         }
    //     }
    //
    //     /*Проверка корректности полей формы*/
    //     function validate(input){
    //         spanGlyphicon = input.parent().find(":last-child");
    //         spanInputGroupAddon = input.parent().find(":first-child");
    //         if (input.val() == "" ){
    //             spanGlyphicon.css("display","");
    //             input.removeClass("inputSuccess");
    //             input.addClass("inputError");
    //             input.css("background-color","#ffe5e5");
    //             spanGlyphicon.addClass("glyphicon-remove");
    //             spanInputGroupAddon.css("background-color", "#fbc6c6")
    //             spanInputGroupAddon.css("border-color","red");
    //             buttonEnabled(false);
    //
    //         } else {
    //             spanGlyphicon.css("display","");
    //             spanGlyphicon.removeClass('glyphicon-remove');
    //             spanGlyphicon.addClass("glyphicon-ok");
    //             input.removeClass("inputError");
    //             input.addClass("inputSuccess");
    //             input.css("background-color","#faffbd");
    //             spanInputGroupAddon.css("background-color", "#dff0d8")
    //             spanInputGroupAddon.css("border-color","green");
    //             buttonEnabled(true);
    //         }
    //
    //     }
    //
    //     /*При потери фокуса в форме вызывается метод validate()*/
    //     inputSourceIp.keyup(function(){
    //         validate(inputSourceIp);
    //     });
    //
    //     inputSourceModel.keyup(function(){
    //         validate(inputSourceModel);
    //     });
    //
    //     /*каждые 100мс проверяет изменения в форме и вызывает метод buttonEnabled()*/
    //     var timerId = setInterval(function(){
    //         if (inputSourceIp.val() != "" && inputSourceModel.val() != ""){
    //             buttonEnabled(true);
    //         } else {
    //             buttonEnabled(false);
    //         }
    //     },100)
    // })
    // });
});

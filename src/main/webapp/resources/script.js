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
});

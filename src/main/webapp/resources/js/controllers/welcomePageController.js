var welcomePage = angular.module('WelcomePage',['ngCookies']);
var nodes = 'Hello'
welcomePage.controller('WelcomePageBody', ['serv', '$scope', '$window', '$cookies', '$interval','$http', '$filter',
 function(serv, $scope, $window, $cookies, $interval, $http, $filter){
	angular.element(document).ready(function () {
		$http.get("/welcome/sources/get-note").then(function(response){
			$scope.sourcesInfo = response.data;
		});
		$interval(callAtInterval, 10000);
		function callAtInterval() {
	        var tdDueData = document.querySelectorAll("td#due-data");
	        for(i = 0; i < tdDueData.length; i++){
	            if (tdDueData[i].innerHTML != "")
	                if(new Date(tdDueData[i].innerHTML) < new Date()) document.location.reload();
					}
		}
	});

	$scope.showHistory = function(){
		$('#historyModal').modal({
			backdrop: 'static',
			keyboard: false
		})

		$scope.historySelectedSource = "Источник не выбран.";
		if ($('#serviceNote').find('.myactive').length != 0) {
			var selectedNote = $("#selectTr tr.myactive td");
			var sourceIp = selectedNote.eq(0).text()
			$scope.historySelectedSource = sourceIp;
			$http.post('/welcome/sources/get-history',sourceIp).then(function(response){
				$scope.sourceHistory = response.data;
			})
		}
		$scope.sourceHistory = null;
	}

	$scope.closeHistoryModal = function(){
		$('#historyModal').modal('hide')
	}

	/*
	* Показать диалоговое окно
	*/
  $scope.fDueDataIntoTheForm = false;
	$scope.showDialogWithSourceInfo = function(source_info, viewId){
		if (source_info != null){
			$scope.sources = {
					sourceIP: source_info.sourceIp,
					sourceModel: source_info.sourceModel,
					sourceDescription: source_info.sourceDescription,
					comments: source_info.comments,
					dueData: $filter('date')(source_info.dueData, 'yyyy-MM-dd HH:mm:ss', '+0300'),
			}
      if ($scope.sources.dueData != null) $scope.fDueDataIntoTheForm = true;
      else $scope.fDueDataIntoTheForm = false;
		}
		datetime();
		$(viewId).modal({
			backdrop: 'static',
			keyboard: false
		})
	}

	$scope.signOut = function(){
		$cookies.remove('username');
		$window.location.href = '/login';
	}

	function datetime(startTime){
			jQuery('#datetimepicker').datetimepicker({
					format: "Y-m-d H:i:s",
					startDate:startTime,
					step: 30
			});
	}

	$scope.clickOnTabComputers = function(){
		$http.get("/welcome/computers").then(function(response){
			$scope.computers = response.data;
		});
	}

	$scope.addComputers = function(){
		var newRow = {
			computerIP: "IP",
			computerName: "Имя",
			computerDescription: "Описание",
			owner: "Владелец"
		}

		$scope.computers.push(newRow)
	}

	$scope.clickOnRowComputersTable = function($index, event, n){
		console.log(n);
		console.log($index);
		console.log(event.target.id);
	}
	$scope.clickOnRowComputersTable2 = function($index, $event, n){
		console.log(n);
		console.log($index);
		console.log($event.target);
	}
}])
welcomePage.controller('SourceOperations',['$document', '$scope', '$http', '$window', function($document, $scope, $http, $window) {
	$scope.regexSourceIP = /\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}/;
  $scope.sourceType = undefined;

	$scope.addSource = function() {POSTRequest('/welcome/sources/add-source', getFormInput($scope, $scope.addSource), $http)}
 	$scope.updateSource = function(){POSTRequest('/welcome/sources/update-source', getFormInput($scope, $scope.sources), $http)}
	$scope.deleteSource = function(){POSTRequest('/welcome/sources/delete-source', getFormInput($scope, $scope.sources), $http)}
  $scope.removeFromReservation = function(){POSTRequest('/welcome/sources/remove-from-reservation', getFormInput($scope, $scope.sources), $http)}

 	function getFormInput($scope, args){
		var sourceInfo = {
			sourceIp: args.sourceIP,
			sourceModel: args.sourceModel,
			sourceDescription: args.sourceDescription,
			comments: args.comments
		}
    if ($scope.sourceType == "PTZ"){
      sourceInfo.sourceType = "PTZ";
    } else if ($scope.sourceType == "Stationary") {
      sourceInfo.sourceType = "Stationary";
    }
    if (args.dueData != "" && args.dueData != undefined)
		   sourceInfo.dueData = args.dueData;
		return sourceInfo;
 	}

 	function POSTRequest(url, sourceInfo, $http){
		$http.post(url, sourceInfo).then(function(response){
			$window.location.href = '/welcome';
		}, function(response){
			if(response.status == 601){
				$scope.addSourceError = "Устройство уже добавленно в БД";
				console.log("Код ошибки " + response.status + " - устройство уже добавлено в БД.");
			}
			if(response.status == 602 || response.status == 603)
				$scope.addSourceError = "При добавлении\\обновлении источника произошла ошибка с кодом " + response.status;
		})
	}

	$scope.showCheckDeleteSource = function(){
    /*Показать диалоговое окно подтверждения удаления*/
		$('#checkDeleteSource').modal({
			backdrop: 'static',
			keyboard: false
		})
	}

  $scope.closeSourceInfoModal = function(viewId){
    /*Закрыть диалоговое окно*/
    $(viewId).modal('hide');
    $scope.isReserv = false;;
  }
}])

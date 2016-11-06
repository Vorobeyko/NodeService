var welcomePage = angular.module('WelcomePage',['ngCookies']);
var nodes = 'Hello'
welcomePage.controller('WelcomePageBody', ['serv', '$scope', '$window', '$cookies', '$interval','$http', function(serv, $scope, $window, $cookies, $interval, $http){
	angular.element(document).ready(function () {
		$http.get("/getNote").then(function(response){
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
			$http.post('/getHistory',sourceIp).then(function(response){
				$scope.sourceHistory = response.data;
			})
		}
		$scope.sourceHistory = null;
	}

	$scope.closeHistoryModal = function(){
		$('#historyModal').modal('hide')
	}

	$scope.showDialogWithSourceInfo = function(){
		datetime();
		$('#myModal').modal({
			backdrop: 'static',
			keyboard: false
		})
		if ($('#serviceNote').find('.myactive')) {
			var selectedNote = $("#selectTr tr.myactive td");
			$scope.sources = {
					sourceIP: selectedNote.eq(0).text(),
					sourceModel: selectedNote.eq(1).text(),
					sourceDescription: selectedNote.eq(2).text(),
					comments: selectedNote.eq(4).text(),
					dueData: selectedNote.eq(5).text(),
			}
		}
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
		console.log("nen");
    $http.get("/welcome/computers").then(function(response){
			console.log(response.data);
			$scope.computers = response.data;
		});
  }
}])

welcomePage.controller('SourceOperations',['$document', '$scope', '$http', '$window', function($document, $scope, $http, $window) {
	$scope.regexSourceIP = /\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}/;

	$scope.addSource = function() { POSTRequest('/addSource', getFormInput($scope), $http) }
 	$scope.updateSource = function(){ POSTRequest('/updateSource', getFormInput($scope), $http) }
	$scope.deleteSource = function(){ POSTRequest('/deleteSource', getFormInput($scope), $http)	}

	$scope.showCheckDeleteSource = function(){
		$('#checkDeleteSource').modal({
			backdrop: 'static',
			keyboard: false
		})
	}
 	function getFormInput($scope){
		var sourceInfo = {
			sourceIp: $scope.sources.sourceIP,
			sourceModel: $scope.sources.sourceModel,
			sourceDescription: $scope.sources.sourceDescription,
			comments: $scope.sources.comments
		}
		if ($scope.isReserv){
			sourceInfo.dueData = $scope.sources.dueData;
		}
		return sourceInfo;
 	}
	$scope.closeSourceInfoModal = function(){
		$('#myModal').modal('hide');
		$scope.isReserv = false;;
	}

 	function POSTRequest(url, sourceInfo, $http){
		$http.post(url, sourceInfo).then(function(){
			$window.location.href = '/welcome';
		}, function(){
			if(response.status == 601){
				$scope.addSourceError = "Устройство уже добавленно в БД";
				console.log("Код ошибки " + response.status + " - устройство уже добавлено в БД.");
			}
			if(response.status == 602 || response.status == 603)
				$scope.addSourceError = "При добавлении\\обновлении источника произошла ошибка с кодом " + response.status;
		})
	}
}])

var welcomePage = angular.module('WelcomePage',['ngCookies']);

welcomePage.controller('WelcomePageBody', ['serv', '$scope', '$window', '$cookies', '$interval','$http', '$filter',
	function(serv, $scope, $window, $cookies, $interval, $http, $filter){
	$scope.updateTable = function(){
        angular.element('#shows').addClass('glyphicon-refresh-animate');
		$http.get("/welcome/sources/get-note").then(function(response){
			$scope.sourcesInfo = response.data;
		});
        $http.get("/welcome/computers").then(function(response){
            $scope.computers = response.data;
        });
        angular.element('#shows').removeClass('glyphicon-refresh-animate');
	};

	angular.element(document).ready(function () {
    $scope.updateTable();
		$interval(callAtInterval, 10000);
		function callAtInterval() {
	        var tdDueData = document.querySelectorAll("td#due-data");
	        for(var i = 0; i < tdDueData.length; i++){
	            if (tdDueData[i].innerHTML != "")
	                if(new Date(tdDueData[i].innerHTML) < new Date()) $scope.updateTable();
			}
		}
	});

	$scope.showHistory = function() {
		$('#historyModal').modal({
		 backdrop: 'static',
		 keyboard: false
		 });
    };
	$scope.showHistoryForSource = function(_sourceInfo){
		if (_sourceInfo != undefined && _sourceInfo != "") {
            $scope.historySelectedSource = "Источник не выбран.";
            $http.post('/welcome/sources/get-history', _sourceInfo).then(function (response) {
                $scope.sourceHistory = response.data;
            });
            $scope.sourceHistory = null;
        }
	};

	$scope.closeHistoryModal = function(){
        $('#historyModal').modal('hide');
		if ($scope.data.singleSelect != undefined) $scope.data.singleSelect = undefined;
        if ($scope.sourceHistory != null || $scope.sourceHistory != undefined) $scope.sourceHistory != null;
	};
	/*
	* Показать диалоговое окно
	*/
	$scope.showDialogWithSourceInfo = function(source_info, viewId){
		if (source_info != null){
			$scope.sources = {
					sourceIP: source_info.sourceIp,
					sourceModel: source_info.sourceModel,
					sourceDescription: source_info.sourceDescription,
                	audioCodec: source_info.audioCodec,
					comments: source_info.comments,
					dueData: $filter('date')(source_info.dueData, 'yyyy-MM-dd HH:mm:ss', '+0300')
			};
		}
		datetime();
		$(viewId).modal({
			backdrop: 'static',
			keyboard: false
		})
	};

	$scope.showDialogWithComputerInfo = function(computer_info, viewId){
		$scope.ch_computer = {
            computerIP: computer_info.computerIP,
            computerName: computer_info.computerName,
            computerDescription: computer_info.computerDescription,
            owner: computer_info.owner
		};

		$(viewId).modal({
			backdrop: 'static',
			keyboard: false
		})
	};

	$scope.openFeedbackDialog = function (viewId) {
        $(viewId).modal({
            backdrop: 'static',
            keyboard: false
        })
    };

	$scope.signOut = function(){
		$cookies.remove('username');
		$window.location.href = '/login';
	};

	function datetime(startTime){
			jQuery('#datetimepicker').datetimepicker({
					format: "Y-m-d H:i:s",
					startDate:startTime,
					step: 30
			});
	}

	$scope.showComputersDialog = function(viewId){
        $(viewId).modal({
            backdrop: 'static',
            keyboard: false
        });
	};
/*
------------------------------
End WelcomePageBody Controller
------------------------------
*/
}]);

welcomePage.controller('SourceOperations',['$document', '$scope', '$http', function($document, $scope, $http) {
	$scope.regexSourceIP = /\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}/;
	$scope.sourceType = undefined;
	$scope.isRequest = false; // Перменная необходима для отображения вращающейся иконки ожидания

	$scope.addSource = function() {POSTRequest('/welcome/sources/add-source', getFormInput($scope, $scope.addSource), $http)};
 	$scope.updateSource = function(){POSTRequest('/welcome/sources/update-source', getFormInput($scope, $scope.sources), $http)};
 	$scope.deleteSource = function(){
 		POSTRequest('/welcome/sources/delete-source', getFormInput($scope, $scope.sources), $http);
        $scope.closeCheckDeleteSource('#checkDeleteSource');
        $scope.closeSourceInfoModal('#change-sources-dialog');
 	};
 	$scope.addComputer = function(){POSTRequest('/welcome/computers/add-computer', getComputersForm($scope, $scope.computer), $http)};
	$scope.updateComputer = function () {POSTRequest('/welcome/computers/update-computer', getComputersForm($scope, $scope.ch_computer), $http)};

 	$scope.removeFromReservation = function(){ POSTRequest('/welcome/sources/remove-from-reservation/', getFormInput($scope, $scope.sources), $http)};

    $scope.sendFeedbackMessage = function () {var feedback = {message:  $scope.feedbackMessage}; POSTRequest('/welcome/feedback-message', feedback, $http)};

    function getComputersForm($scope, args){
        var computerInfo = {
            computerIP: args.computerIP,
            computerName: args.computerName,
            computerDescription: args.computerDescription,
            owner: args.owner
        };
        return computerInfo;
	}


	function getFormInput($scope, args){
		var sourceInfo = {
			sourceIp: args.sourceIP,
			sourceModel: args.sourceModel,
			sourceDescription: args.sourceDescription,
            audioCodec: args.audioCodec,
			comments: args.comments
		};
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
		$scope.isRequest = true;
		console.log("Tyt");
		$http.post(url, sourceInfo).then(function(response){
			$scope.isRequest = false;
			$scope.updateTable();
            $scope.addSourceSuccess = "Операция успешно выполнена.";
		}, function(response){
			$scope.isRequest = false;
			if(response.status == 601){
				$scope.addSourceError = "Устройство уже добавлено в БД";
				console.log("Код ошибки " + response.status + " - устройство уже добавлено в БД.");
			}
			if(response.status == 602 || response.status == 603)
				$scope.addSourceError = "При добавлении\\обновлении источника произошла ошибка с кодом " + response.status;
		})
	}

	$scope.closeSourceInfoModal = function(viewId){
		/*Закрыть диалоговое окно*/
		$(viewId).modal('hide');
		if ($scope.sourceType) $scope.sourceType = false;
		if ($scope.addSource.sourceIP != "") $scope.addSource.sourceIP = "";
		if ($scope.addSource.sourceModel != "") $scope.addSource.sourceModel = "";
		if ($scope.addSource.sourceDescription != "") $scope.addSource.sourceDescription = "";
        if ($scope.addSource.audioCodec != "") $scope.addSource.audioCodec = "";
		if ($scope.addSource.comments != "") $scope.addSource.comments = "";
        if ($scope.computer.computerIP != "") $scope.computer.computerIP = "";
        if ($scope.computer.computerName != "") $scope.computer.computerName = "";
        if ($scope.computer.computerDescription != "") $scope.computer.computerDescription = "";
        if ($scope.computer.owner != "") $scope.computer.owner = "";
		if ($scope.addSourceError != "") $scope.addSourceError = "";
        if ($scope.addSourceSuccess != "") $scope.addSourceSuccess = "";
	};

    $scope.showCheckDeleteSource = function(){
		/*Показать диалоговое окно подтверждения удаления*/
        $('#checkDeleteSource').modal({
            backdrop: 'static',
            keyboard: false
        })
    };

    $scope.showCheckDeleteComputer = function(){
		/*Показать диалоговое окно подтверждения удаления*/
        $('#checkDeleteComputer').modal({
            backdrop: 'static',
            keyboard: false
        })
    };


    $scope.closeCheckDeleteSource = function (viewId){
        $(viewId).modal('hide');
    };
/*
------------------------------
End SourceOperations Controller
------------------------------
*/
}]);

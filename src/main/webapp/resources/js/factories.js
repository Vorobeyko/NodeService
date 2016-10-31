welcomePage.factory('serv', function($http){
    return {
      list: function(callback){
        $http.get("/getNote").then(callback);
      }
    }
})

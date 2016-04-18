/**
 * Created by lijunying on 15/10/24.
 * 除了queryResult外
 * <html ng-app="indexApp">
 * <body ng-controller="indexController">
 * 这两个不能少
 *
 */
var cFlag = true;
angular.module("indexApp",[])
    .factory("queryFactory",function ($http, $q){
       return{
    	   //ajax & $http demo for angularJs
    	   connectServerToFindOutQueryTimes : function(uniqueKey){
               //
               var deferred = $q.defer();
               return $http.get("../../../qrCodeQueryServlet"+"?uniqueKey="+uniqueKey).success(function(response){
               	  deferred.resolve(response);
               })
                   .error(function(response){
                      console.log("Error happened");
                   });
           },
    	   
           getUniqueKeyFromUrl : function(urlStr){
               var uniqueKey='';
               try{
            	   uniqueKey = urlStr.split("?")[1];
               }catch(e){
            	cFlag=false;
   			    alert("Error");
   			    
   		    }
               return uniqueKey
           }
    	   
       }//end of return
       
    })
    .controller("indexController",function($scope,$window,queryFactory){
    	//http://www.315cck.com/6ebe7af5-437c-4999-9a1c-84181089889b/je6MbWyY
    	//http://localhost:8080/iSearch/index.html?queryid=6ebe7af5-437c-4999-9a1c-84181089889b&uniqueCode=2059467068
        var uniqueKey = queryFactory.getUniqueKeyFromUrl($window.location.href);
        if(cFlag){
            queryFactory.connectServerToFindOutQueryTimes(uniqueKey).then(function(result){
                if(result){
                    $("#queryResult").append(result.data.queryContent);
                }

            })
        }
        
});
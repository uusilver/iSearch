/**
 * Created by lijunying on 15/10/24.
 */
var cFlag = true;
angular.module("indexApp",[])
    .factory("queryFactory",function ($http, $q){
       return{
    	   //ajax & $http demo for angularJs
    	   connectServerToFindOutQueryTimes : function(queryId, uniqueKey){
               //
               var deferred = $q.defer();
               return $http.get("../../../qrCodeQueryServlet"+"?queryId="+queryId+"&uniqueKey="+uniqueKey).success(function(response){
               	  deferred.resolve(response);
               })
                   .error(function(response){
                      console.log("Error happened");
                   });
           },
    	   
           getQueryIdFromUrl : function(urlStr){
               var queryId='';
               try{
                   queryId = urlStr.split("?")[1].split("&")[0].split("=")[1];
               }catch(e){
            	cFlag=false;
   			    alert("Error!");
   			    
   		    }
               return queryId
           },
           
           getUniqueKeyFromUrl : function(urlStr){
               var uniqueKey='';
               try{
            	   uniqueKey = urlStr.split("?")[1].split("&")[1].split("=")[1];
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
        var queryId = queryFactory.getQueryIdFromUrl($window.location.href);
        var uniqueKey = queryFactory.getUniqueKeyFromUrl($window.location.href);
        if(cFlag){
        	$scope.indentityCode = uniqueKey;
            queryFactory.connectServerToFindOutQueryTimes(queryId,uniqueKey).then(function(result){
                if(result.data==0){
                    $scope.queryTimes = "≥ı¥Œ≤È—Ø";
                }else{
                    $scope.queryTimes = result.data+"¥Œ";
                }

            })
        }
        
});
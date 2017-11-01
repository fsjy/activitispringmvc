<%--
  Created by IntelliJ IDEA.
  User: Yanglu
  Date: 2017/10/26
  Time: 下午2:08

--%>
<%@page session="false" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <title>Local Model Procedure</title>

    <c:url var="home" value="/" scope="request"/>
    <c:set var="baseURL" value="${pageContext.request}"/>

    <spring:url value="/resources/core/css/hello.css" var="coreCss"/>
    <spring:url value="/resources/core/css/bootstrap.min.css"
                var="bootstrapCss"/>

    <link href="${bootstrapCss}" rel="stylesheet"/>
    <link href="${coreCss}" rel="stylesheet"/>


    <spring:url value="/resources/core/js/jquery.1.10.2.min.js"
                var="jqueryJs"/>
    <script src="${jqueryJs}"></script>

    <script>


    </script>


    <!-- Angular Js -->
    <spring:url value="/resources/core/js/angular.1.2.32.js"
                var="angularJs"/>
    <script src="${angularJs}"></script>


    <script>

        var myAPP = angular.module("myAPP", []);
        var baseUrl = '${pageContext.request.contextPath}';

        var inputModels = {};


        myAPP.controller("myController", function ($scope, $http) {


            $scope.Deploy = function (modelId) {

                //alert(modelId);

                var sentUrl = baseUrl + "/local/deploy"

                var senddata = {modelId : ""};

                senddata.modelId = modelId;

                $http({
                    method: 'POST'
                    , url: sentUrl
                    , params: senddata

                }).then(function successCallback(response) {

                    $scope.models = response.data.data;

                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    $scope.data = response.data || 'Request failed';
                    $scope.status = response.status;

                    //alert($scope.data);
                    //alert($scope.status);
                    alert("failed");
                });


            };



            $scope.createModel = function () {

                window.open(baseUrl + "/model/create?name=" + $scope.inputModels.nameLike + "&key=" + $scope.inputModels.key + "&description=" + $scope.inputModels.description + "");

            };


            $scope.SendData = function (url) {

                var sentUrl = baseUrl + url;

                var sendModels = {size: 200};
                /**
                 * Delete the key which has no data accordingly
                 * Params of Angular $http receive the object instead of json, and
                 * acvitivi-rest API can not check null data, so we should delete
                 * the unnecessary key whose val is null.
                 *
                 * */
                // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
                if (typeof $scope.inputModels !== "undefined") {

                    inputModels = $scope.inputModels;
                    Object.keys(inputModels).forEach(function (key) {
                        if (!inputModels[key]) {
                            // delete inputModels[key];
                        } else {

                            sendModels[key] = inputModels[key];
                            if (key.endsWith("Like")) {
                                sendModels[key] = sendModels[key] + "%";
                            }
                        }
                    });
                }
                // _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/

                $http({
                    method: 'GET'
                    , url: sentUrl
                    , params: sendModels

                }).then(function successCallback(response) {
                    // this callback will be called asynchronouslyZ
                    // when the response is available

                    // Json response data sample:
                    /**
                     _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
                     {
                         "data":[
                         {
                             "name":"Model name",
                             "key":"Model key",
                             "category":"Model category",
                             "version":2,
                             "metaInfo":"Model metainfo",
                             "deploymentId":"7",
                             "id":"10",
                             "url":"http://localhost:8182/repository/models/10",
                             "createTime":"2013-06-12T14:31:08.612+0000",
                             "lastUpdateTime":"2013-06-12T14:31:08.612+0000",
                             "deploymentUrl":"http://localhost:8182/repository/deployments/7",
                             "tenantId":null
                         },

                         ...

                     ],
                         "total":2,
                         "start":0,
                         "sort":"id",
                         "order":"asc",
                         "size":2
                     }
                     _/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/_/
                     */
                    $scope.models = response.data.data;

                }, function errorCallback(response) {
                    // called asynchronously if an error occurs
                    // or server returns response with an error status.
                    $scope.data = response.data || 'Request failed';
                    $scope.status = response.status;

                    alert($scope.data);
                    alert($scope.status);
                    alert("failed");
                });

            };


        });


    </script>

</head>

<body>

<div class="container">
    <!-- Page Navigation -->

    <nav class="navbar navbar-inverse">
        <div class="container">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">Local Model Procedure Page</a>
            </div>
        </div>
    </nav>


    <!-- Model Table -->

    <form class="form-inline">

    </form>

    <div ng-app="myAPP" ng-controller="myController">

        <h2>Operate</h2>
        <form class="form-inline">
            <div class="form-group">

                <input type="text" class="form-control" id="model_name" ng-model="inputModels.nameLike"
                       placeholder="Model Name">
            </div>
            <div class="form-group">

                <input type="text" class="form-control" id="model_key" ng-model="inputModels.key"
                       placeholder="Model Key">
            </div>

            <button type="button" id="bth-activiti" class="btn btn-default" ng-click="SendData('/repository/models')">
                Search Model
            </button>
            <div class="form-group">

                <input type="text" class="form-control" id="model_description" ng-model="inputModels.description"
                       placeholder="Model Description">
            </div>

            <button type="button" id="bth-activiti-create" class="btn btn-default" ng-click="createModel()">
                Create Model
            </button>
        </form>

        <form class="form-inline">
            <div class="form-group">

                <input type="text" class="form-control" id="model_id" ng-model="modelId"
                       placeholder="Model ID">
            </div>

            <button type="button" id="bth-activiti-deploy" class="btn btn-default" ng-click="Deploy(modelId)">
                Create Model
            </button>

        </form>

        <h2>List</h2>
        <div class="row">

            <div class="table-responsive">
                <table class="table table-bordered table-hover">


                    <tr class="info">
                        <th>Name</th>
                        <th>Key</th>
                        <th>Category</th>
                        <th>Version</th>
                        <!-- <th>MetaInfo</th> -->
                        <th>DeploymentID</th>
                        <th>ID</th>
                        <!-- <th>URL</th> -->
                        <th>CreateTime</th>
                        <!-- <th>LastUpdateTime</th> -->
                        <th>DeploymentUrl</th>
                        <th>TenantID</th>
                        <th>CHECK</th>
                    </tr>

                    <!-- Pay attention to the uppercase and lowercase-->
                    <tr ng-repeat="model in models">
                        <td>{{ model.name }}</td>
                        <td>{{ model.key }}</td>
                        <td>{{ model.category }}</td>
                        <td>{{ model.version }}</td>
                        <!-- <td>{{ model.metaInfo }}</td> -->
                        <td>{{ model.deploymentId }}</td>
                        <td>{{ model.id }}</td>
                        <!-- <td>{{ model.url }}</td> -->
                        <td>{{ model.createTime }}</td>
                        <!-- <td >{{ model.lastUpdateTime }}</td> -->
                        <td>{{ model.deploymentUrl }}</td>
                        <td>{{ model.tenantId }}</td>
                        <td> button</td>
                    </tr>
                </table>
            </div>

        </div>
    </div>


</div>

</body>


</html>
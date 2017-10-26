<%--
  Created by IntelliJ IDEA.
  User: yl
  Date: 2017/10/24
  Time: 下午2:08
  To change this template use File | Settings | File Templates.
--%>
<%@page session="false"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title>Spring MVC 4 + Ajax + Activiti Hello World</title>

    <c:url var="home" value="/" scope="request" />


    <spring:url value="/resources/core/css/hello.css" var="coreCss" />
    <spring:url value="/resources/core/css/bootstrap.min.css"
                var="bootstrapCss" />

    <link href="${bootstrapCss}" rel="stylesheet" />
    <link href="${coreCss}" rel="stylesheet" />
    <!--

    <link href="${pageContext.request.contextPath}/resources/core/css/hello.css" rel="stylesheet" >
    <link href="${pageContext.request.contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet" >
    -->

    <spring:url value="/resources/core/js/jquery.1.10.2.min.js"
                var="jqueryJs" />
    <script src="${jqueryJs}"></script>
</head>

<nav class="navbar navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Spring 4 MVC Ajax Activiti Hello World</a>
        </div>
    </div>
</nav>

<div class="container" style="min-height: 500px">

    <div class="starter-template">
        <h1>Search Form</h1>
        <br>

        <div id="feedback"></div>

        <form class="form-horizontal" id="search-form">
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">Username</label>
                <div class="col-sm-10">
                    <input type=text class="form-control" id="username">
                </div>
            </div>
            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">Email</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="email">
                </div>
            </div>

            <div class="form-group form-group-lg">
                <label class="col-sm-2 control-label">Phone</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" id="phone">
                </div>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <button type="submit" id="bth-search"
                            class="btn btn-primary btn-lg">Search</button>

                    <button type="button" id="bth-activiti"
                            class="btn btn-primary btn-lg">Activiti panel</button>
                </div>
            </div>
        </form>

    </div>

</div>

<div class="container">
    <footer>
        <p>

        </p>
    </footer>
</div>

<script>
    jQuery(document).ready(function($) {

        $("#search-form").submit(function(event) {

            // Disble the search button
            enableSearchButton(false);

            // Prevent the form from submitting via the browser.
            event.preventDefault();

            searchViaAjax();

        });

        $("#bth-activiti").click(function (event) {

            //alert();
            window.open("model/create?name=20171024-1&key=20171024-1&description=testForIntegration");

        });


    });

    function searchViaAjax() {

        var search = {}
        search["username"] = $("#username").val();
        search["email"] = $("#email").val();

        $.ajax({
            type : "POST",
            contentType : "application/json",
            url : "${home}search/api/getSearchResult",
            data : JSON.stringify(search),
            dataType : 'json',
            timeout : 100000,
            success : function(data) {
                console.log("SUCCESS: ", data);
                display(data);
            },
            error : function(e) {
                console.log("ERROR: ", e);
                display(e);
            },
            done : function(e) {
                console.log("DONE");
                enableSearchButton(true);
            }
        });

    }

    function enableSearchButton(flag) {
        $("#btn-search").prop("disabled", flag);
    }

    function display(data) {

        //alert(data.result[0].username);

        if (data.result != null) {
            $('#email').val(data.result[0].email);
            $('#phone').val(data.result[0].phone);
        }
        var json = "<h4>Ajax Response</h4><pre>"
            + JSON.stringify(data, null, 4) + "</pre>";
        $('#feedback').html(json);

    }
</script>

</body>
</html>
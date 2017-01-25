<#import "/spring.ftl" as spring>

<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset=utf-8/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

        <link rel='stylesheet' href='/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css'>

        <script src='/webjars/jquery/3.1.1/jquery.min.js'></script>
        <script src='/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js'></script>

        <title><@spring.message "title.registration.heading"/></title>
    </head>
<body>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li><a href="/list">All Persons</a></li>
            <li class="active"><a href="/new">New Person</a></li>
        </ul>
    </div>
</nav>
<div class="well">
    <section class="content">
        <h2><@spring.message "title.registration.heading"/></h2>

        <form method="POST" modelAttribute="person">
            <input type="hidden" path="id" id="id"/>
            <div class="row">
                <div class="col-md-6">
                    <div class="row">
                        <div class="col-sm-4 read-only-header"><label for="title">Title: </label></div>
                        <div class="col-sm-4"><input type="text" name="title" id="title" required="true" autofocus="autofocus"/></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 read-only-header"><label for="author">Author: </label></div>
                        <div class="col-sm-4"><input type="text" name="author" id="author" required="true"/></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 read-only-header"><label for="isbn">ISBN: </label></div>
                        <div class="col-sm-4"><input type="text" name="isbn" id="isbn" required="true"/></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4 read-only-header"><label for="ssn">On Loan: </label></div>
                        <div class="col-sm-4"><input type="text" name="isLent" id="isLent" required="true"/></div>
                    </div>
                    <div class="row">
                        <div class="col-sm-4"/>
                        <div class="col-sm-4">
                            <#if shouldEdit>
                                <button class="btn btn-default" type="submit">Update</button>
                            <#else>
                                <button class="btn btn-default" type="submit">Register</button>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </section>
</div>
</body>
</html>



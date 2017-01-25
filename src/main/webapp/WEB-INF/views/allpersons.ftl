<#import "/spring.ftl" as spring>

<!DOCTYPE html>

<html lang="en">
    <head>
        <meta charset=utf-8/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0"/>

        <link rel='stylesheet' href='/webjars/bootstrap/3.3.7-1/css/bootstrap.min.css'>

        <script src='/webjars/jquery/3.1.1/jquery.min.js'></script>
        <script src='/webjars/bootstrap/3.3.7-1/js/bootstrap.min.js'></script>

        <title><@spring.message "title.all.persons.heading"/></title>
    </head>
<body>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
            <li class="active"><a href="/list">All Persons</a></li>
            <li><a href="/new">New Person</a></li>
        </ul>
    </div>
</nav>

<div class="well">
    <div class="container">
        <h2><@spring.message "title.all.persons.heading"/></h2>

        <table class="table table-condensed">
            <thead>
            <tr>
                <th>Name</th>
                <th>Phone</th>
                <th>Email</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </thead>
            <tbody>
            <#list persons as person>
                <tr>
                    <td>${person.name}</td>
                    <td>${person.phone}</td>
                    <td>${person.email}</td>
                    <td><a href="/edit-${person.id}-person">${person.id}</a></td>
                    <td><a href="/delete-${person.id}-person">delete</a></td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>



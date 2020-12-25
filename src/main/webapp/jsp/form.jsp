<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <style>
    .content {
      position: absolute;
      left: 50%;
      top: 50%;
      -webkit-transform: translate(-50%, -50%);
      transform: translate(-50%, -50%);
      align-items: center;
    }
    </style>
    <meta charset="UTF-8">
    <title>${title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
</head>
<body>
    <h1 align="center">${title}</h1>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="container-fluid">
            <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
                <div class="navbar-nav">
                    <a class="nav-link" href="/">Home</a>
                    <a class="nav-link ${index[0]}" href="/Disk">Disk</a>
                    <a class="nav-link ${index[1]}" href="/DiskType">DiskType</a>
                    <a class="nav-link ${index[2]}" href="/InformationType">InformationType</a>
                </div>
            </div>
        </div>
    </nav>
    <div class="border justify-content-center">
    <form action="/${url}" method="POST">
        <button type="submit" name="create_request" class="btn btn-primary">Add new row</button>
    </form>
    <form action="/${url}" method="POST">
        <c:set var="position" value="0" scope="page" />
        <c:forEach var="label" items="${labels}">
            <c:set var="position" value="${position + 1}" scope="page"/>
            <c:if test="${position <= fn:length(labels)}" >
                <div class="form-group">
                    <label for="${position}">${label}</label>
                    <c:if test="${not empty data}" >
                        <input type="text" class="form-control" name="${position}" placeholder="${label}" id="${position}" value="${data[position]}">
                    </c:if>
                    <c:if test="${empty data}" >
                        <input type="text" class="form-control" name="${position}" placeholder="${label}" id="${position}">
                    </c:if>
                </div>
            </c:if>
        </c:forEach>
        <c:if test="${not empty data}" >
            <input type="hidden" name="id" id="u_id" value="${data[0]}"/>
        </c:if>
      <button type="submit" name="${type}" class="btn btn-success">${submitText}</button>
    </div>
    </form>
</body>
</html>
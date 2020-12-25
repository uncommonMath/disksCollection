<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
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
    <c:if test="${error == true}">
        <div class="alert alert-danger" role="alert">
            <p>Input data error.</p>
            <p>Please check your input and try again.</p>
            <c:if test="${not empty message}" >
                <p>Message: ${message}</p>
            </c:if>
        </div>
    </c:if>
    <center>
        <div>
            <p>Вариант 7
            <p>Разработайте приложение по учету домашней коллекции компакт дисков.
            <p>Коллекция состоит из дисков разных типов. Типы носителей – DVD, CD-R, mini-disc.
            <p>Информация на одном носителе может соответствовать нескольким категориям – фильмы, музыка, ПО и т.п.
            <p>Каждый носитель характеризуется дополнительным описанием (текстовое полеб description).
            <p>Разрабатываемое приложение должно уметь добавлять/удалять/редактировать как состав коллекции, так и словари приложения – состав типов носителей и категории информации.
            <p>Приветствуется реализация механизма поиска носителя по полю дополнительного описания.
        </div>
    </center>
</body>
</html>
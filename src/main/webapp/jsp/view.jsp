<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <div>
	    <p><b>Statistics:</b></p>
	    <p><i>Database returned ${fn:length(data)} rows</i></p>
    </div>
	<table class="table">
		<thead>
		    <tr>
                <th scope="col"></th>
                <c:forEach var="label" items="${labels}">
                    <th scope="col">${label}</th>
                </c:forEach>
                <th scope="col">Edit</th>
                <th scope="col">Delete</th>
		    </tr>
		</thead>
		<tbody>
            <c:forEach var="row" items="${data}">
			<tr>
				<th scope="row"></th>
				<c:set var="position" value="0" scope="page" />
				<c:forEach var="cell" items="${row}">
				    <c:set var="position" value="${position + 1}" scope="page"/>
				    <c:if test="${position < fn:length(row)}" >
				        <td><xmp>${row[position]}</xmp></td>
                    </c:if>
				</c:forEach>
				<td>
				    <form action="/${url}" method="POST">
				        <input type="hidden" name="id" id="u_id" value="${row[0]}"/>
				        <button type="submit" name="update_request" class="btn btn-warning">Edit</button>
				    </form>
				</td>
				<td>
				    <form action="/${url}" method="POST">
				        <input type="hidden" name="id" id="d_id" value="${row[0]}"/>
				        <button type="submit" name="delete" class="btn btn-danger">Delete</button>
				    </form>
				</td>
			</tr>
            </c:forEach>
		</tbody>
	</table>
</body>
</html>
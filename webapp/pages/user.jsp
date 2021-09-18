<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lang" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title><lang:print message="Cinema"/></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
                          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
                          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                          <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
                          <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
    <p><a href="/app">
        <lang:print message="Login_page"/>
    </p></a>

   <table>
        <tr>
            <th><lang:print message="Date"/></th>
            <th><lang:print message="Time"/></th>
            <th><lang:print message="Title"/></th>
            <th><lang:print message="Price"/></th>
            <th><lang:print message="Room_capacity"/></th>
            <th><lang:print message="Free_places"/></th>
        </tr>
        <c:forEach var="seance" items="${seances}">
   			<tr>
   				<td>${seance.date}</td>
   				<td>${seance.time}</td>
   				<td>${seance.movie.title}</td>
   				<td>${seance.price}</td>
   				<td>${seance.seatingCapacity}</td>
   				<td>${seance.freePlaces}</td>
   			</tr>
   		</c:forEach>
   	</table>


    <ul class="pagination">
      <c:forEach var="page" items="${pageAndFirstValue}">
      <li class="page-item"><a class="page-link" href="/app/cinema/seance/page?value=${page.value}">${page.key}</a></li>
      </c:forEach>
    </ul>




   	<style>
    table {
    font-family: "Lucida Sans Unicode", "Lucida Grande", Sans-Serif;
    font-size: 14px;
    border-collapse: collapse;
    text-align: center;
    }
    th, td:first-child {
    background: #AFCDE7;
    color: white;
    padding: 10px 20px;
    }
    th, td {
    border-style: solid;
    border-width: 0 1px 1px 0;
    border-color: white;
    }
    td {
    background: #D8E6F3;
    }
    th:first-child, td:first-child {
    text-align: left;
    }
    </style>

    </body>
</html>
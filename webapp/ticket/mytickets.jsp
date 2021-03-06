<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="/WEB-INF/tag/language.tld" prefix="lang" %>
<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title><lang:print message="My_tickets"/></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
                          <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
                          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
                          <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
                          <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    </head>
    <body>
   	<p><a href="/app/cinema/user/logout">
       <lang:print message="Log_out"/>
    </p></a>
    <p><a href="/app/home/client.jsp">
        <lang:print message="Home_page"/>
    </p></a>
   <table>
        <tr>
            <th><lang:print message="Ticket_ID"/></th>
            <th><lang:print message="Date"/></th>
            <th><lang:print message="Time"/></th>
            <th><lang:print message="Title"/></th>
            <th><lang:print message="Price"/></th>
        </tr>
        <c:forEach var="ticket" items="${tickets}">
   			<tr>
   				<td>${ticket.ticket.id}</td>
   				<td>${ticket.seance.date}</td>
   				<td>${ticket.seance.time}</td>
   				<td>${ticket.seance.movie.title}</td>
   				<td>${ticket.seance.price}</td>
   			</tr>
   		</c:forEach>
   	</table>


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
</html
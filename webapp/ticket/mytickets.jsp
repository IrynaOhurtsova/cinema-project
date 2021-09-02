<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cinema</title>
        <meta charset="UTF-8">
    </head>
    <body>
   <table>
        <tr>
            <th>Ticket ID</th>
            <th>DATE</th>
            <th>TIME</th>
            <th>TITLE</th>
            <th>PRICE</th>
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

    </body>
</html
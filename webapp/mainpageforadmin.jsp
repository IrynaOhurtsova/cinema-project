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
            <th>DATE</th>
            <th>TIME</th>
            <th>TITLE</th>
            <th>PRICE</th>
            <th>SEATING CAPACITY</th>
            <th>FREE PLACES</th>
            <th>SOLD TICKETS</th>
        </tr>
        <c:forEach var="seance" items="${seances}">
   			<tr>
   				<td>${seance.date}</td>
   				<td>${seance.time}</td>
   				<td>${seance.movie.title}</td>
   				<td>${seance.price}</td>
   				<td>${seance.seatingCapacity}</td>
   				<td>${seance.freePlaces}</td>
   				<td>${seance.seatingCapacity - seance.freePlaces}</td>
   				<td>
   				    <form method="POST" action="/app/cinema/seance/delete">
                              <input type="hidden" name="seanceId" value="${seance.id}">
                              <input type="submit" value="delete">
                    </form>
   				</td>
   			</tr>
   		</c:forEach>
   	</table>

    </body>
</html>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cinema</title>
        <meta charset="UTF-8">
    </head>
    <body>
   <table>
   <p>start table</p>
        <c:forEach var="seance" items="${schedule.seances}">
   			<tr>
   				<td>${seance.id}</td>
   			</tr>
   		</c:forEach>
   	</table>

    </body>
</html
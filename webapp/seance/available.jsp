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

    <button onclick="sortTable(0)"><lang:print message="Sort_date"/></button>
    <button onclick="sortTable(2)"><lang:print message="Sort_title"/></button>
    <button onclick="sortTable(3)"><lang:print message="Sort_price"/></button>
    <button onclick="sortTable(5)"><lang:print message="Sort_free_places"/></button>
    <form method="POST" action="/app/cinema/seance/available">
        <button><lang:print message="Available_seances"/></button>
    </form>

    <form method="GET" action="/app/cinema/seance/available/page">
            <input class="page-item" type="hidden" name="value" value="0">
            <button class="page-link" type="submit"><lang:print message="Display_by"/></button>
    </form>
   <table id="myTable">
        <tr>
            <th><lang:print message="Date"/></th>
            <th><lang:print message="Time"/></th>
            <th><lang:print message="Title"/></th>
            <th><lang:print message="Price"/></th>
            <th><lang:print message="Room_capacity"/></th>
            <th><lang:print message="Free_places"/></th>
            <th><lang:print message="Buy"/></th>
        </tr>
        <c:forEach var="seance" items="${seances}">
   			<tr>
   				<td>${seance.date}</td>
   				<td>${seance.time}</td>
   				<td>${seance.movie.title}</td>
   				<td>${seance.price}</td>
   				<td>${seance.seatingCapacity}</td>
   				<td>${seance.freePlaces}</td>
   				<td>
   				    <form method="POST" action="/app/cinema/ticket/buy"
   				    onsubmit="return confirm('<lang:print message="Do_you_want_buy_ticket_on"/>${seance.movie.title}?')" >
                              <input type="hidden" name="seanceId" value="${seance.id}">
                              <input type="submit" value=<lang:print message="buy"/>>
                    </form>
   				</td>
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
</html>

<script>
function sortTable(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("myTable");
  switching = true;
  // Set the sorting direction to ascending:
  dir = "asc";
  /* Make a loop that will continue until
  no switching has been done: */
  while (switching) {
    // Start by saying: no switching is done:
    switching = false;
    rows = table.rows;
    /* Loop through all table rows (except the
    first, which contains table headers): */
    for (i = 1; i < (rows.length - 1); i++) {
      // Start by saying there should be no switching:
      shouldSwitch = false;
      /* Get the two elements you want to compare,
      one from current row and one from the next: */
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /* Check if the two rows should switch place,
      based on the direction, asc or desc: */
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          // If so, mark as a switch and break the loop:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /* If a switch has been marked, make the switch
      and mark that a switch has been done: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      // Each time a switch is done, increase this count by 1:
      switchcount ++;
    } else {
      /* If no switching has been done AND the direction is "asc",
      set the direction to "desc" and run the while loop again. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}
</script>
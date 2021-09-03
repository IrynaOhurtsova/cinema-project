<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Cinema</title>
        <meta charset="UTF-8">
    </head>
    <body>
    <button onclick="sortTable(0)">SORT DATE</button>
    <button onclick="sortTable(2)">SORT TITLE</button>
    <button onclick="sortTable(3)">SORT PRICE</button>
    <button onclick="sortTable(5)">SORT FREE PLACES</button>

   <table id="myTable">
        <tr>
            <th>DATE</th>
            <th>TIME</th>
            <th>TITLE</th>
            <th>PRICE</th>
            <th>SEATING CAPACITY</th>
            <th>FREE PLACES</th>
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
   				    <form method="POST" action="/app/cinema/ticket/buy">
                              <input type="hidden" name="seanceId" value="${seance.id}">
                              <input type="submit" value="buy">
                    </form>
   				</td>
   			</tr>
   		</c:forEach>
   	</table>
    <form method="POST" action="/app/cinema/seance/available">

         <input type="submit" value="AVAILABLE SEANCES">
    </form>
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

<script>
const myFunction = () => {
  const trs = document.querySelectorAll('#myTable tr:not(.header)');
  const filter = document.querySelector('#myInput').value;
  const regex = new RegExp(filter, 'i');
  const isFoundInTds = (td) => regex.test(td.innerHTML);
  const isFound = (childrenArr) => childrenArr.some(isFoundInTds);
  const setTrStyleDisplay = ({ style, children }) => {
    style.display = isFound([...children]) ? '' : 'none';
  };

  trs.forEach(setTrStyleDisplay);
}
</script>
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
                    <form method="POST" action="/app/cinema/seance/freeplaces">
                              <input type="hidden" name="seanceId" value="${seance.id}">
                              <input type="submit" value="free places">
                    </form>
                </td>
   				<td>
   				    <form method="POST" action="/app/cinema/ticket/buy">
                              <input type="hidden" name="seanceId" value="${seance.id}">
                              <input type="submit" value="buy">
                    </form>
   				</td>
   			</tr>
   		</c:forEach>
   	</table>

    </body>
</html>

<script>
function sortTable(n) {
  var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
  table = document.getElementById("myTable");
  switching = true;
  // Set the sorting direction to ascending:
  dir = "asc";
  /* Сделайте цикл, который будет продолжаться до тех пор, пока
  никакого переключения не было сделано: */
  while (switching) {
    // Начните с того, что скажите: переключение не выполняется:
    switching = false;
    rows = table.rows;
    /* Цикл через все строки таблицы (за исключением
    во-первых, который содержит заголовки таблиц): */
    for (i = 1; i < (rows.length - 1); i++) {
      // Начните с того, что не должно быть никакого переключения:
      shouldSwitch = false;
      /* Получите два элемента, которые вы хотите сравнить,
      один из текущей строки и один из следующей: */
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /* Проверьте, должны ли две строки поменяться местами,
      основанный на направлении, asc или desc: */
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // Если это так, отметьте как переключатель и разорвать цикл:
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          // Если это так, отметьте как переключатель и разорвать цикл:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /* Если переключатель был отмечен, сделайте переключатель
      и отметьте, что переключатель был сделан: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      // Каждый раз, когда выполняется переключение, увеличьте это число на 1:
      switchcount ++;
    } else {
      /* Если переключение не было сделано и направление "asc",
      установите направление на "desc" и снова запустите цикл while. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}
</script>
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
  /* �������� ����, ������� ����� ������������ �� ��� ���, ����
  �������� ������������ �� ���� �������: */
  while (switching) {
    // ������� � ����, ��� �������: ������������ �� �����������:
    switching = false;
    rows = table.rows;
    /* ���� ����� ��� ������ ������� (�� �����������
    ��-������, ������� �������� ��������� ������): */
    for (i = 1; i < (rows.length - 1); i++) {
      // ������� � ����, ��� �� ������ ���� �������� ������������:
      shouldSwitch = false;
      /* �������� ��� ��������, ������� �� ������ ��������,
      ���� �� ������� ������ � ���� �� ���������: */
      x = rows[i].getElementsByTagName("TD")[n];
      y = rows[i + 1].getElementsByTagName("TD")[n];
      /* ���������, ������ �� ��� ������ ���������� �������,
      ���������� �� �����������, asc ��� desc: */
      if (dir == "asc") {
        if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
          // ���� ��� ���, �������� ��� ������������� � ��������� ����:
          shouldSwitch = true;
          break;
        }
      } else if (dir == "desc") {
        if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
          // ���� ��� ���, �������� ��� ������������� � ��������� ����:
          shouldSwitch = true;
          break;
        }
      }
    }
    if (shouldSwitch) {
      /* ���� ������������� ��� �������, �������� �������������
      � ��������, ��� ������������� ��� ������: */
      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
      switching = true;
      // ������ ���, ����� ����������� ������������, ��������� ��� ����� �� 1:
      switchcount ++;
    } else {
      /* ���� ������������ �� ���� ������� � ����������� "asc",
      ���������� ����������� �� "desc" � ����� ��������� ���� while. */
      if (switchcount == 0 && dir == "asc") {
        dir = "desc";
        switching = true;
      }
    }
  }
}
</script>
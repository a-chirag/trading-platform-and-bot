 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Pending</h1>
<div class="table-responsive">
	<table class="table table-striped" id = 'pendingtable'>
		<thead>
			<tr>
				<th onclick="sortTable('pendingtable',0)">#</th>
				<th onclick="sortTable('pendingtable',1)">Instrument</th>
				<th onclick="sortTable('pendingtable',2)">Side</th>
				<th onclick="sortTable('pendingtable',3)">Price</th>
				<th onclick="sortTable('pendingtable',4)">Size</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userdata.pendingOrders}" var="orders">
				<tr>
					<td>${orders.value.orderId}</td>
					<td>${orders.value.instrument}</td>
					<td>${orders.value.side}</td>
					<td>${orders.value.price}</td>
					<td>${orders.value.size}</td>
					<td><button id="cancel" onclick="cancel(${userdata.userId},${orders.value.orderId});" type="button" class="btn btn-danger btn-xs">cancel</button>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
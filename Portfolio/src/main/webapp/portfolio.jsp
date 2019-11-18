 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<h1>Portfolio</h1>
<div class="table-responsive">
	<table class="table table-striped">
		<thead>
			<tr>
				<th>#</th>
				<th>Instrument</th>
				<th>Quantity</th>
				<th>Price</th>
				<th></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${userdata.portfolio}" var="orders" varStatus="loop">
				<tr>
					<td>${loop.index}</td>
					<td>${orders.key}</td>
					<td>${orders.value}</td>
					<td></td>
					<td></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</div>
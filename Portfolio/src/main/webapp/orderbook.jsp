<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<button class="btn btn-success btn-sm" onclick="overview()">back</button>
<hr>
<div>
<h1>${orderbook.instrument}</h1>
<h5>${orderbook.desc }</h5>
</div>
<c:forEach items="${orderbook.prices}" var="entry" varStatus="loop">
  <c:if test="${loop.last}"><button class="btn btn-success btn-sm">$ ${entry.value}</button></c:if>
  <c:if test="${loop.last}"><button class="btn btn-success btn-sm" hidden id="curPrice" value="${entry.value}"></button></c:if>
</c:forEach>
<div class="row">
	<div class="col-md-7">
		<form method="post" action="" id="${orderbook.instrument }over"
			name="overviewadder">
			<br> <input id="instrument" name="instrument"
				value="${orderbook.instrument }" hidden="">
			<p>
				<label for="buyprice">Buy: $</label> <input type="number" id="buyprice" name="buyprice" value="document.getElementById('curPrice').value">
				<label for="sellprice">Sell: $</label> <input type="number" id="sellprice" name="sellprice"
					>
					<br>
							</p>
			<div id="${orderbook.instrument }over-slider-range"></div>
			<p>
				<label for="size">Size: #</label> <input type="number" id="size" name="size" >
			</p>
			<div id="${orderbook.instrument }over-size-slider"></div>
			<input type="button" class="btn btn-success" value="Buy"
				onclick="buy(document.getElementById('${orderbook.instrument }over'))"><input
				type="button" class="btn btn-danger" value="Sell"
				onclick="sell(document.getElementById('${orderbook.instrument }over'))">
		</form>
		</div>
		<div class="col-md-3" id="${orderbook.instrument }graph"></div>
	<div class="col-md-6">
		<center>Buy</center>
		<table class="table table-striped table-condensed">
			<tr>
				<th>Price</th>
				<th>Size</th>
				<th>Status</th>
			</tr>
			<c:forEach items="${orderbook.buy}" var="orders">
				<tr>
					<td>${orders.value.price}</td>
					<td>${orders.value.size}</td>
					<td></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<div class="col-md-6">
		<center>Sell</center>
		<table class="table table-striped table-condensed">
			<tr>
				<th>Price</th>
				<th>Size</th>
				<th>Status</th>
			</tr>
			<c:forEach items="${orderbook.sell}" var="orders">
				<tr>
					<td>${orders.value.price}</td>
					<td>${orders.value.size}</td>
					<td></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</div>
<script src="/book/utils.js"></script>
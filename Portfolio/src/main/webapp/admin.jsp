<form method="post" action="/book/v1/postResult" id="admin" name="form">
	<table>
		<tr>
			<td>Username</td>
			<td><input id="username" name="username" /></td>
		</tr>
		<tr>
			<td>Password</td>
			<td><input id="password" name="password" /></td>
		</tr>
		<tr>
			<td><input type="radio" name="result" value="won"> Won</td>
			<td><input type="radio" name="result" value="lost"> Lost</td>
		</tr>
		<tr>
			<td>Instrument</td>
			<td><input id="instrument" name="instrument"
				value=${instrument.instrument } /></td>
		</tr>
		<tr>
		<td>Win case</td>
		<td>${instrument.winCase }</td>
		</tr>
		<tr>
			<td><input type="submit" value="submit" class="submit"></td>
		</tr>
	</table>
</form>
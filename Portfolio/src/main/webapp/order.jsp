
<style>
.switch {
  position: relative;
  display: inline-block;
  width: 60px;
  height: 34px;
}

.switch input {display:none;}

.slider {
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color:green ;
  -webkit-transition: .4s;
  transition: .4s;
}

.slider:before {
  position: absolute;
  content: "";
  height: 26px;
  width: 26px;
  left: 4px;
  bottom: 4px;
  background-color: white;
  -webkit-transition: .4s;
  transition: .4s;
}

input:checked + .slider {
  background-color:RED ;
}

input:focus + .slider {
  box-shadow: 0 0 1px RED;
}

input:checked + .slider:before {
  -webkit-transform: translateX(26px);
  -ms-transform: translateX(26px);
  transform: translateX(26px);
}

/* Rounded sliders */
.slider.round {
  border-radius: 34px;
}

.slider.round:before {
  border-radius: 50%;
}
</style>

<form method="post" action="" id="addorderform" name="form">
	<table>
		<tr>
			<td>Size</td>
			<td><input id="size" name="size" /></td>
		</tr>
		<tr>
			<td>Price</td>
			<td><input id="price" name="price" /></td>
		</tr>
		<tr>
			<td>BUY</td><td><label class="switch">
  <input type="checkbox" id="side">
  <span class="slider round"></span>
</label>
			</td><td>SELL</td></tr>
		<tr>
			<td>Instrument</td>
			<td><input id="instrument" name="instrument" /></td>
		</tr>
		<tr>
			<td><input type="button" value="submit" class="submit"
				onclick="submitForm()"></td>
		</tr>
		<tr>
			<td><input type="button" value="check" class="check"
				onclick="check()"></td>
		</tr>
	</table>
</form>
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <div class="modal-content addOrder" id="addOrder">
      <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" align='right'>&times;</button>
             
          <h6 class="modal-title">Order Added</h6>
          </div>
      </div>
      
    </div>
  </div>
				<div class="form-group" style="visibility: hidden; height:0px;">
					<div class="col-md-2">
						<label>Id</label>
					</div>
					<div class="col-md-10">
						<input name="id" value="<?php echo $id;?>"></input>
					</div>
				</div>
			
				<div class="form-group">
					<div class="col-md-2">
						<label>Autor</label>
					</div>
					<div class="col-md-10">
						<input name="autor" value="<?php echo $autor;?>" maxlength="255" list="autores"></input>
						<datalist id="autores">
						<?php
						$sqlAutores="select distinct autor from libro order by autor";
						$result=mysql_query($sqlAutores);
						while ($row = mysql_fetch_row($result)) { 
							echo "<option value='$row[0]'/>";
						}
						?>
						</datalist>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-md-2">
						<label>Título</label>
					</div>
					<div class="col-md-10">
						<input name="titulo"  maxlength="255" size="32" value="<?php echo $titulo;?>"></input>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-md-2">
						<label>Género</label>
					</div>
					<div class="col-md-10">
						<select name="genero" ">
							<?php
								$generos=[
									"",
									"Negra",
									"Fantástico",
									"Humor",
									"Aventura",
									"Histórica",
									"Narrativa",
									"Romántica",
									"Drama",
									"Ciencia Ficción",
									"Cuento",
									"Otros"
								];
								foreach($generos as $x) {
									echo "<option value='$x' ";
									if($genero==$x) {
										echo "selected";
									}
									echo ">${x}</option>";
								}
							?>
						</select>
					</div>
				</div>				
				
				<!--
				<div class="form-group">
					<div class="col-md-2">
						<label>Valoración</label></div>
					<div class="col-md-10">
						<input type="number" size="1" maxlength="1" min="1" max="5" name="valoracion" value="<?php echo $valoracion;?>"></input>
					</div>
				</div>
				-->
				
				<div class="form-group">
					<div class="col-md-2">
						<label>Valoración</label></div>
					<div class="col-md-10"><?php
						for($x=0; $x<=5; $x++) {
							echo "<input type='radio' name='valoracion' ";
							if($x==$valoracion) {
								echo " checked ";
							}
							echo "value='$x' > ";
							if($x==0) {
								echo "Sin leer";
							} else {
								echo "<span class='valor'>";
								for($i=1; $i<=$x; $i++) {
									echo "☆";
								}
								echo "</span>";
							}
							echo " ";
							echo "</input>";
						}
						?>
					</div>
				</div>
				
				
				<div class="form-group">
					<div class="col-md-2">
						<label>Comentarios</label>
					</div>
					<div class="col-md-10">				
						<textarea rows="4" cols="50"  maxlength="4000" name="comentarios"><?php echo $comentarios;?></textarea>
					</div>
				</div>
				
				<div class="form-group">
					<div class="col-md-2">
						<label>ISBN</label>
					</div>
					<div class="col-md-10">				
						<input name="isbn"  maxlength="13" size="32" value="<?php echo $isbn;?>"></input>
					</div>
				</div>				
				
				<div class="form-group">
					<div class="col-md-2">
						<label>Enlace</label>
					</div>
					<div class="col-md-10">
						<input name="url"  maxlength="255" size="32" value="<?php echo $url;?>"></input>
					</div>
				</div>

				<div class="form-group">
					<div class="col-md-2">
						<label>Fecha</label>
					</div>
					<div class="col-md-10">
						<input name="fecha"  maxlength="255" size="32" value="<?php echo $fecha;?>" readonly="readonly"></input>
					</div>
				</div>				
				
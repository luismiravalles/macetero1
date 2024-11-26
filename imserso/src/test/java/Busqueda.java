import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByClassName;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class Busqueda {
	private static final int TIEMPO_CORTO = 1000;
	private static final int TIEMPO_MUY_CORTO = 200;
    private static final int TIEMPO_EXTRA_CORTO = 100;

	public final static String SANTANDER="Santander-42";
	public final static String BILBAO="Bilbao-7";
    public final static String LEON="León-25";
	
	private String modalidad;
	private String zona;
	private String transporte;
	private String provincia;
	private PrintWriter salida;
	private List<Resultado> resultados;
	private boolean listaEspera=true;
	private boolean disponible=true;
	private String origen="Oviedo-(Asturias)-35";
    private WebTest webTest;
	
	private String fechaMin;
	
	SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");		
	
	public static Busqueda instance(PrintWriter salida, List<Resultado> resultados) { 
		Busqueda busqueda=new Busqueda();
		busqueda.salida=salida;
		busqueda.resultados=resultados;
		return busqueda;
	}
	public Busqueda modalidad(String modalidad) 	{	this.modalidad=modalidad; return this;	}		
	public Busqueda zona(String valor) 				{	this.zona=valor; return this;	}
	public Busqueda provincia(String valor) 		{	this.provincia=valor; return this;	}
	public Busqueda transporte(String valor) 		{	this.transporte=valor; return this;	}
	public Busqueda listaEspera(boolean valor)		{	this.listaEspera=valor; return this; }
	public Busqueda disponible(boolean valor)		{	this.disponible=valor; return this; }
	public Busqueda fechaMin(String fecha) 			{ 	this.fechaMin=fecha; return this; }
	public Busqueda origen(String origen)			{ 	this.origen=origen; return this; }
    public Busqueda webTest(WebTest webTest)        {   this.webTest=webTest; return this; }


    public void buscar() throws MalformedURLException {
        this.buscar(this.webTest);
    }
	
	public void buscar(WebTest w) throws MalformedURLException {
		WebDriver driver=w.getDriver();
		
		TestTurismoSocial.LOGGER.info("Buscando desde "
                    + this.origen + " en "
                    + this.zona + " " + this.provincia + " " + this.modalidad 
					+ " Transporte " + this.transporte 
					+ " listaEspera " + this.listaEspera
					+ " disponible " + this.disponible
					+ " fechaMin " + this.fechaMin
					+ " ...");
		
		driver.get("https://reservasacc.turismosocial.com/");
		
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		
		// Aceptar las Cookies
		sleep(2 * TIEMPO_CORTO);
		try  {
			WebElement botonAceptarCookies=driver.findElement(w.byId("onetrust-accept-btn-handler"));
			botonAceptarCookies.click();
		} catch(NoSuchElementException e) {
			// NO pasa nada.
		}
		
		sleep(TIEMPO_CORTO);
		driver.findElement(w.byId("origenId-button")).click();
		driver.findElement(w.byId(origen)).click();	
		sleep(TIEMPO_MUY_CORTO);
		
		driver.findElement(w.byInputId("plazasId")).sendKeys("2");
		sleep(TIEMPO_MUY_CORTO);
			
		if(modalidad!=null) {
			driver.findElement(byId("modelidadId-button")).click();
			sleep(TIEMPO_EXTRA_CORTO);
			driver.findElement(byId(modalidad + "-")).click();	
			sleep(TIEMPO_CORTO);
		}		
		if(zona!=null) {
			driver.findElement(byId("zonaId-button")).click();
			sleep(TIEMPO_EXTRA_CORTO);
			driver.findElement(byId(zona + "-")).click();
			sleep(TIEMPO_CORTO);
		}
		if(transporte!=null) {
			driver.findElement(byId("transporteId-button")).click();
			sleep(TIEMPO_EXTRA_CORTO);
			driver.findElement(byId(transporte + "-")).click();
			sleep(TIEMPO_EXTRA_CORTO);
		}			
		if(provincia!=null) {
			driver.findElement(byId("provinciaId-button")).click();
			sleep(TIEMPO_EXTRA_CORTO);
			driver.findElement(byId(provincia + "-")).click();
			sleep(TIEMPO_CORTO);
		}
		
		if(fechaMin!=null) {
			driver.findElement(w.byInputId("fechaDesdeIdDiv")).sendKeys(fechaMin);
		}
		
		WebElement el	=driver.findElement(By.xpath("//button[contains(@aria-label,'Buscar')]"));
		el.sendKeys("");
		sleep(2000);
		el.submit();			
		
		w.sleep(5);
		if(listaEspera) {
			buscarFecha(w, driver, "waitinglist", "Lista de espera");
		}
		if(disponible) {
			buscarFecha(w, driver, "available", "Disponible");
		}
		log.info("-----");
	}
	
	private void sleep(int milis) {
		try {
			TimeUnit.MILLISECONDS.sleep(milis);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException(e);
		}
	}
	
	public By byId(String id) {
		return By.xpath("//*[starts-with(@id,'" + id + "')]");
	}
	
	public void imprimir(String contenido) {
		System.out.println(contenido);
		salida.println(contenido.replaceAll("-[0-9]", ""));
		salida.flush();
	}

	private boolean esEspecial(Resultado res) {
		return 
			res.esNavidad() && res.conTransporte() ||
			res.esTenerife() ||
			res.esPrimavera() && res.conTransporte();
	}
	
	private void buscarFecha(WebTest w, WebDriver driver, final String tipo, final String debeContener) {	
		final String eliminar="Transporte Folleto PDF Añadir";
		final String eliminarOrigen="Origen: (Oviedo \\(Asturias\\)|Sin transporte)";
		final String eliminarHabInd="Hab.Ind: ..";
		final String eliminarAccesibilidad="Accesibilidad UNE: ..";
		final String eliminarGuiones="-[0-9]";
	
		
		for(WebElement el:driver.findElements(ByClassName.className(tipo))) {
			el.click();
			w.sleep(1);			
			
			for(WebElement campo:driver.findElements(By.xpath("//span[contains(text(),'Fecha:')]/parent::div/parent::div/parent::div"))) {
				String contenido=campo.getText().replaceAll("[€\n]", " ").replaceAll(eliminar, "")						
						.replaceAll(eliminarHabInd, "")
						.replaceAll(eliminarAccesibilidad, "")
						.replaceAll(eliminarGuiones, "")
						.trim();
				if(contenido.contains(debeContener)) {
					String []campos=contenido.split("(Fecha|Origen|Destino|Días|Estado|Accesibilidad UNE|Hotel|Precio|Hab.Ind):");
					
					final int PRECIO=7;
					campos[PRECIO]=campos[PRECIO].replace(".", ",");

					
					for(int i=0; i<campos.length; i++) {
						campos[i]=campos[i].trim();
					}
					String result=StringUtils.join(campos, "</td><td>");

					String estilo="";
					if("Si".equals(transporte)) {
						estilo +=" background: lightgreen;";
					} else {
						estilo +=" background: beige;";							
					}
					if(result.contains("Disponible")) {
						estilo += "color: darkred; font-weight: bold;";
					}

					String fecha=diaSemana(campos[1])+ " " + campos[1];

					Resultado res=new Resultado();
					res.setFecha(campos[1]);
					res.setDestino(campos[3]);
					res.setDias(campos[4]);
					res.setEstado(campos[5]);
					res.setHotel(campos[6]);
					res.setPrecio(campos[7]);
					res.setTransporte(transporte);
					res.setZona(zona);
                    res.setOrigen(this.origen);

					if(esEspecial(res)) {
						resultados.add(res);	
					}
				
					imprimir("<tr style='" + estilo + "'>" + 
						td(fecha) + 
                        td(StringUtils.substringBefore(origen, "-")) +
						td(google(res.getDestino())) +
						td(res.getDias()) +
						td(res.getEstado()) +
						td(google(res.getHotel(), res.getHotel() + " " + res.getDestino())) +
						td(res.getPrecio()) +
						td(transporte) +
						td(zona) +
						"</tr>");
				}
			}				
		}					
	}

	private String google(String contenido) {
		return google(contenido, contenido);
	}

	private String google(String contenido, String busqueda) {
		String enlace=URLEncoder.encode(busqueda);
		return "<a href='https://www.google.com/search?q=" + enlace + "'>" + contenido + "</a>";
	}

	private String td(String cadena) {
		return "<td>" + cadena + "</td>";
	}

	public static String diaSemana(String fecha) {
		// Definir el formato de la fecha
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		// Convertir la cadena a LocalDate
		LocalDate localDate = LocalDate.parse(fecha, formatter);
		
		// Obtener el día de la semana
		DayOfWeek dayOfWeek = localDate.getDayOfWeek();

		return dayOfWeek.getDisplayName(TextStyle.FULL, TestTurismoSocial.SPANISH);
	}
}
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.support.ui.Select;

import lombok.extern.apachecommons.CommonsLog;

@CommonsLog
public class Busqueda {
	private static final int TIEMPO_CORTO = 800;
	private static final int TIEMPO_MUY_CORTO = 200;
    private static final int TIEMPO_EXTRA_CORTO = 100;

	public final static String OVIEDO="QOC_ORI";

	private String modalidad;
	private EZona zona;
	private ETransporte transporte;
	private EProvincia provincia;
	private List<EProvincia> provincias;
	private PrintWriter salida;
	private List<Resultado> resultados;
	private boolean listaEspera=true;
	private boolean disponible=true;
	private EOrigen origen;
	private String web="turismosocial.es";
    private WebTest webTest;
	private Predicate<LocalDate> filtroFecha;
		
	private String fechaMin;

	public static Locale SPANISH = Locale.forLanguageTag("ES");
	
	SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");		
	
	public static Busqueda instance(PrintWriter salida, List<Resultado> resultados) { 
		Busqueda busqueda=new Busqueda();
		busqueda.salida=salida;
		busqueda.resultados=resultados;
		return busqueda;
	}
	public Busqueda modalidad(String modalidad) 	{	this.modalidad=modalidad; return this;	}		
	public Busqueda zona(EZona valor) 				{	this.zona=valor; return this;	}
	public Busqueda provincia(EProvincia valor) 	{	this.provincia=valor; return this;	}
	public Busqueda provincias(List<EProvincia> provincias) { this.provincias=provincias; return this; }
	public Busqueda transporte(ETransporte valor) 		{	this.transporte=valor; return this;	}
	public Busqueda listaEspera(boolean valor)		{	this.listaEspera=valor; return this; }
	public Busqueda disponible(boolean valor)		{	this.disponible=valor; return this; }
	public Busqueda fechaMin(String fecha) 			{ 	this.fechaMin=fecha; return this; }
	public Busqueda origen(EOrigen origen)			{ 	this.origen=origen; return this; }
    public Busqueda webTest(WebTest webTest)        {   this.webTest=webTest; return this; }
	public Busqueda web(String web)					{ 	this.web=web; return this;}
	public Busqueda filtroFecha(Predicate<LocalDate> filtro) { this.filtroFecha=filtro; return this;}


	static final Log LOGGER = LogFactory.getLog(WebTest.class);

    public void buscar() throws MalformedURLException {
        this.buscar(this.webTest);
    }
	
	public void buscar(WebTest w)  {
		WebDriver driver;
		try {
			 driver=w.getDriver();
		} catch(MalformedURLException e) {
			throw new IllegalStateException(e);
		}
		
		LOGGER.info("");
		LOGGER.info("=============================================================================");
		LOGGER.info("Buscando desde "
                    + this.origen + " en "
                    + this.zona + " " + this.provincia 
					+ " Transporte " + this.transporte 
					+ " listaEspera " + this.listaEspera
					+ " disponible " + this.disponible
					+ " ...");
		
		driver.get("https://" + this.web);		
		driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);		
		TestTurismoSocial.LOGGER.info("==> Hemos navegado a " + web);
		
		// Aceptar las Cookies
		sleep(TIEMPO_CORTO);
		String botonCookies="onetrust-accept-btn-handler";
		if("mundicolor.es". equals(web)) {
			botonCookies="acceptCookiesAllBtn";
		}
		try  {
			WebElement botonAceptarCookies=driver.findElement(w.byId(botonCookies));
			botonAceptarCookies.click();
			LOGGER.info("==> Aceptadas las cookies.");
		} catch(NoSuchElementException e) {			
		}

		driver.get("https://www." + web + "/scheduler");

		LOGGER.info("==> Ahora estamos dentro de Programación");
		sleep(TIEMPO_CORTO);

		if(transporte!=null) {
			sleep(TIEMPO_CORTO);
			Select select=new Select(driver.findElement(byId(ETransporte.ID)));
			select.selectByValue(transporte.getCodigo());
			sleep(TIEMPO_CORTO);
			LOGGER.info("==> Seleccionado Transporte "  + transporte);
		}
		
		if(origen!=null) {		
			sleep(TIEMPO_CORTO);
			Select select=new Select(driver.findElement(byId(EOrigen.ID)));
			select.selectByValue(origen.getCodigo());
			sleep(TIEMPO_CORTO);
			LOGGER.info("==> Seleccionado origen "  + origen);
		}
		
		if(zona!=null) {
			sleep(TIEMPO_CORTO);
			LOGGER.info("==> Eligiendo zona " + zona);
			WebElement we=driver.findElement(By.id(EZona.ID));
			we.click();
			Select select=new Select(we);
			select.selectByValue(zona.getCodigo());
			LOGGER.info("==> Elegiza zona " + zona);
		}

		List<EProvincia> provs=provincias==null?Arrays.asList(this.provincia):provincias;

		for(EProvincia prov:provs) {

			sleep(TIEMPO_CORTO);
			LOGGER.info("==> Eligiendo provincia " + prov);
			WebElement we=driver.findElement(By.id(EProvincia.ID));
			we.click();
			Select select=new Select(we);
			LOGGER.debug("Opciones de Provincia: " + select.getOptions());		
			try {	
				select.selectByValue(prov.getCodigo());
			} catch(NoSuchElementException e) {
				LOGGER.info("==> No existen destinos para la provincia " + prov.getNombre());
				return;
			}
			LOGGER.info("==> Elegida provincia " + prov);		
			driver.findElement(By.id("product-searcher-btn-scheduler")).click();
			LOGGER.info("==> Pulsado Buscar");
			
			sleep(3 * TIEMPO_CORTO);
			if(listaEspera) {
				buscarFecha(w, driver, prov, "lista-espera", "Lista de espera");
			}
			if(disponible) {
				buscarFecha(w, driver, prov, "disponible", "Disponible");
			}
		}
		log.info("==> Fin Busqueda en zona " + zona);
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
	
	private void imprimir(String contenido) {
		salida.println(contenido);
		salida.flush();
	}

	private boolean esEspecial(Resultado res) {
		return res.getOrigen().contains("Oviedo") && res.conTransporte() &&
			(		
			res.esTenerife() ||
			res.esPrimavera()
			);
	}

	private Resultado crearResultado(EProvincia provincia, WebElement el) {
		String fecha=el.getAttribute("data-date");
		Resultado res=new Resultado();
		res.setFecha(fecha);
		res.setDestino(provincia.getNombre());
		res.setDias("?");
		res.setEstado("?");
		res.setTransporte(transporte==null?"Sí":transporte.getCodigo());
		res.setZona(zona.getNombre());
		res.setOrigen(this.origen==null?"?":this.origen.getNombre());
		return res;

	}
	
	private void buscarFecha(WebTest w, WebDriver driver, final EProvincia provincia, final String tipo, final String debeContener) {	
		final String eliminar="Transporte Folleto PDF Añadir";
		final String eliminarOrigen="Origen: (Oviedo \\(Asturias\\)|Sin transporte)";
		final String eliminarHabInd="Hab.Ind: ..";
		final String eliminarAccesibilidad="Accesibilidad UNE: ..";
		final String eliminarGuiones="-[0-9]";
	
		List<WebElement> diasEncontrados=driver.findElements(ByClassName.className(tipo));
		//diasEncontrados.stream()
		//	.forEach(el -> LOGGER.info("Encontrados " + el.getAttribute("data-date") ));
		
		for(WebElement el:diasEncontrados) {
			
			Resultado nuevoResultado=crearResultado(provincia, el);

			if(filtroFecha!=null && !filtroFecha.test(LocalDate.parse(nuevoResultado.getFecha()))) {
				continue;
			}

			if(fechaMin!=null && fechaMin.compareTo(nuevoResultado.getFecha())>0) {
				continue;
			}

			LOGGER.info("==> ATENCION: Hay algo disponible en " + nuevoResultado.getDestino()  + " fecha: " + nuevoResultado.getFecha());		

			el.click();
			w.sleep(1);	

			try {				
				WebElement divDisponible=driver.findElement(By.className("availability"));
				nuevoResultado.setHtmlCompleto(divDisponible.getAttribute("outerHTML"));
			} catch (NoSuchElementException e) {
				// Nada que hacer. Simplemente no lo cojo.
				LOGGER.info("=====> NO SE encontró nada disponible.");
			}
			resultados.add(nuevoResultado);
			imprimir(nuevoResultado.getHtmlCompleto());
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

		return dayOfWeek.getDisplayName(TextStyle.FULL, SPANISH);
	}
}
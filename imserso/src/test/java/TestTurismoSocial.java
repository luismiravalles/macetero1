

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByClassName;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;




/**
 * 	MONTAJE:
 * 
 * 	Este Test Busca los destinos de Turismos Social que sean interesantes y los deja en un fichero html
 *  destinos-imserso.html en el NextCloud.
 *  
 *  <p>
 *  Paralelamente ese fichero en la RaspBerry Pi está linkado hacia /var/wwww/html para poder ofrecerlo
 *  de forma públic a travésde una URL. Hay un proceso cron que le da permisos de lectura cada 2 minutos para poder
 *  ofrecerlo a través de http.
 *  <p>
 * 
 * 
 * 
 *
 */
public class TestTurismoSocial {	

	private static final Log LOGGER = LogFactory.getLog(WebTest.class);

	
	private static final String CIRCUITOS = "Turismo-de-Escapada";

	private static final String CATALUNYA = "Cataluña";

	private static final String COMUNIDAD_VALENCIANA = "Comunidad-Valenciana";

	private static final String MALLORCA = "Mallorca";

	private static final String BALEARES = "Baleares";

	private static final String NO = "No";

	private static final String COSTAS = "Costa-Peninsular";

	private static final String ANDALUCIA = "Andalucía";

	private static final String SI = "Si";

	private static final String TENERIFE = "Tenerife";

	private static final String CANARIAS = "Canarias";

	private static final String ISLAS = "Costa-Insular";

	private static Locale SPANISH = Locale.forLanguageTag("ES");

	
	private PrintWriter salida;

	MailSender mailSender=new MailSender("luismiravalles@gmail.com");

	
	// Fw-Uniovi de Testing.
	WebTest webTest=new WebTest();
	
	public TestTurismoSocial() {
		System.setProperty("webdriver.gecko.driver", "/java/webdriver/geckodriver.exe");
	}
	
	@Before public void init() throws MalformedURLException, FileNotFoundException {
		System.out.println("Obteniendo el driver...");
		webTest.getDriver();
	}
	
	private void abrirSalida(String nombre) throws FileNotFoundException {
		if(nombre==null) {
			salida=new PrintWriter(System.out);
		} else {
			salida=new PrintWriter(
					new OutputStreamWriter(
					new FileOutputStream(nombre), StandardCharsets.UTF_8
					));
		}
		salida.println("<html>"
				+ "<head>"
				+ " <meta charset='UTF-8'> "
				+ "<style> body { font-family: Arial; } table, th, td {   border: 1px solid black;  border-collapse: collapse; padding:5px;	} </style>"
				+ "</head>"
				+ "<body>");
		
		salida.println("<p>Datos obtenidos a fecha : " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "</p>");
		salida.println("<table>");
		salida.println("<tr>");
		salida.println("<th>Fecha</th><th>Destino</th><th>Dias</th><th>Estado</th><th>Hotel</th><th>Precio</th><th>Transporte</th><th>Zona</th>");
	}
		
	
	@After public void end() {
		if(salida!=null) {
			salida.println("</table></body></html>");
			salida.close();
			webTest.closeAndQuit();
		}
	}

	@Test public void testEnviarResultados() {
		System.out.println("Ejecutando test enviar resultados");

		Resultado res=new Resultado();
		res.setFecha("24/12/2024");
		res.setDestino("Tenerife");
		res.setDias("8");
		res.setEstado("Lista de espera");
		res.setHotel("Reconquista");
		res.setPrecio("100");
		res.setTransporte("Si");
		res.setZona("Canarias");
		List<Resultado> resultados=new ArrayList<>();
		resultados.add(res);
		resultados.add(res);

		enviarResultados(resultados);
	}
	

	
	
	@Test public void run()  throws InterruptedException, IOException {
		//abrirSalida("/Users/luis/Nextcloud/Compartir/public/destinos-imserso.html");
		//abrirSalida("C:\\Users\\luis\\OneDrive - Universidad de Oviedo\\destinos-imserso.html");
		abrirSalida("destinos-imserso.html");

		List<Resultado> resultados=new ArrayList<>();
		
		Busqueda.instance(salida, resultados)
				.modalidad(ISLAS)
				.zona(CANARIAS)
				// .provincia(TENERIFE)
				.transporte(SI)
				.buscar(webTest);
		
		/**
		 * Sin transporte NO INTERESA porque es muy caro...
		 */
//		Busqueda.instance(salida)
//				.modalidad(ISLAS)
//				.zona(CANARIAS)
//				.provincia(TENERIFE)
//				.transporte(NO)
//				.buscar(webTest);		
		
		Busqueda.instance(salida, resultados)
				.modalidad(ISLAS)
				.zona(BALEARES)
				.transporte(SI)
				// .fechaMin("21/03/2025")
				.buscar(webTest);		
		
		Busqueda.instance(salida,resultados)
				.modalidad(COSTAS)
				.zona(ANDALUCIA)
				.transporte(SI)
				.buscar(webTest);
		
		// Ya no interesa ir a Andalucia sin Transporte pq ya vamos a Conil :-)
		Busqueda.instance(salida, resultados)
				.modalidad(COSTAS)
				.zona(ANDALUCIA)
				.fechaMin("21/03/2025")
				.transporte(NO)
				// .listaEspera(true)
				.buscar(webTest);
		
		// ---- Busueda en COSTAS minimo en Primavera, aunque sean sin transporte 
		// Ya no me vale sin transporte. Tiene que ser CON transporte 
		for(String zona: Arrays.asList(COMUNIDAD_VALENCIANA, CATALUNYA, ANDALUCIA)) {
			Busqueda.instance(salida, resultados)
					.modalidad(COSTAS)
					.zona(zona)
					.transporte(SI)
					// .fechaMin("21/03/2025")
					.buscar(webTest);
			
			// No interesa sin transporte a no ser que sea a partir de marzo
			Busqueda.instance(salida, resultados)
					.modalidad(COSTAS)
					.zona(zona)
					.transporte(NO)
					.fechaMin("15/03/2025")
					.buscar(webTest);			
		}
			
		
		// Buscar En Circuitos a partir de Primavera también
		Busqueda.instance(salida, resultados)
				.modalidad(CIRCUITOS)
				// .fechaMin("01/02/2025")
				.buscar(webTest);

		
		// Finalmente, si tenemos resltados especiales en resultados entonces los enviamos por correo.
		if(resultados.size()>0) {
			enviarResultados(resultados);
		}

	}
	
	private void enviarResultados(List<Resultado> resultados) {
		StringBuilder cuerpo=new StringBuilder();

		cuerpo.append("<html>");
		cuerpo.append("<body>");
		cuerpo.append("<p>Se han encontrado viajes del Imserso</p>");
		for(Resultado res:resultados) {
			cuerpo.append("<table>");
			cuerpo.append(res.toHtmlTable());
			cuerpo.append("</table>");
			cuerpo.append("<hr>");
		}
		cuerpo.append("</body>");
		cuerpo.append("</html>");

		try {
			mailSender.sendEmail("luismiravalles@gmail.com;vizcarrmen@gmail.com", "Resultados Imserso", cuerpo.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static class Busqueda {
		private static final int TIEMPO_CORTO = 1500;
		private static final int TIEMPO_MUY_CORTO = 200;
		
		private String modalidad;
		private String zona;
		private String transporte;
		private String provincia;
		private PrintWriter salida;
		private List<Resultado> resultados;
		private boolean listaEspera=true;
		private boolean disponible=true;
		
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
		
		public void buscar(WebTest w) throws MalformedURLException {
			WebDriver driver=w.getDriver();
			
			LOGGER.info("Buscando " + this.zona + " " + this.provincia + " " + this.modalidad 
						+ " Transporte " + this.transporte 
						+ " listaEspera " + this.listaEspera
						+ " disponible " + this.disponible
						+ " fechaMin " + this.fechaMin
						+ " ...");
			
			driver.get("https://reservasacc.turismosocial.com/");
			
			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
			
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
			driver.findElement(w.byId("Oviedo-(Asturias)-35")).click();	
			sleep(TIEMPO_CORTO);
			
			driver.findElement(w.byInputId("plazasId")).sendKeys("2");
			sleep(TIEMPO_CORTO);
				
			if(modalidad!=null) {
				driver.findElement(byId("modelidadId-button")).click();
				sleep(TIEMPO_MUY_CORTO);
				driver.findElement(byId(modalidad + "-")).click();	
				sleep(TIEMPO_CORTO);
			}		
			if(zona!=null) {
				driver.findElement(byId("zonaId-button")).click();
				sleep(TIEMPO_MUY_CORTO);
				driver.findElement(byId(zona + "-")).click();
				sleep(TIEMPO_CORTO);
			}
			if(transporte!=null) {
				driver.findElement(byId("transporteId-button")).click();
				sleep(TIEMPO_MUY_CORTO);
				driver.findElement(byId(transporte + "-")).click();
				sleep(TIEMPO_CORTO);
			}			
			if(provincia!=null) {
				driver.findElement(byId("provinciaId-button")).click();
				sleep(TIEMPO_MUY_CORTO);
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
				res.esNavidad() ||
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
							.replaceAll(eliminarOrigen, "")
							.replaceAll(eliminarHabInd, "")
							.replaceAll(eliminarAccesibilidad, "")
							.replaceAll(eliminarGuiones, "")
							.trim();
					if(contenido.contains(debeContener)) {
						String []campos=contenido.split("(Fecha|Origen|Destino|Días|Estado|Accesibilidad UNE|Hotel|Precio|Hab.Ind):");
						
						final int PRECIO=6;
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
						res.setDestino(campos[2]);
						res.setDias(campos[3]);
						res.setEstado(campos[4]);
						res.setHotel(campos[5]);
						res.setPrecio(campos[6]);
						res.setTransporte(transporte);
						res.setZona(zona);

						if(esEspecial(res)) {
							resultados.add(res);	
						}
					
						imprimir("<tr style='" + estilo + "'>" + 
							td(fecha) + 
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

			return dayOfWeek.getDisplayName(TextStyle.FULL, SPANISH);
		}
	}
	
}

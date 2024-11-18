

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;




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

	static final Log LOGGER = LogFactory.getLog(WebTest.class);

	
	public static final String CIRCUITOS = "Turismo-de-Escapada";

	public static final String CATALUNYA = "Cataluña";

	public static final String COMUNIDAD_VALENCIANA = "Comunidad-Valenciana";

	public static final String MALLORCA = "Mallorca";

	public static final String BALEARES = "Baleares";

	public static final String NO = "No";

	public static final String COSTAS = "Costa-Peninsular";

	public static final String ANDALUCIA = "Andalucía";

	public static final String SI = "Si";

	public static final String TENERIFE = "Tenerife";

	public static final String CANARIAS = "Canarias";

	public static final String ISLAS = "Costa-Insular";

	public static Locale SPANISH = Locale.forLanguageTag("ES");

	
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
		/**
		 * Si la salida ya está abierta, la dejamos como está para poder ejectar tests
		 * que al final guarden todo en un unico fichero de salida.
		 */
		if(salida!=null) {
			return;
		}
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
		salida.println("<th>Fecha</th><th>Origen</th><th>Destino</th><th>Dias</th><th>Estado</th><th>Hotel</th><th>Precio</th><th>Transporte</th><th>Zona</th>");
	}
	
	
	private void cerrarSalida() {
		if(salida!=null) {
			salida.println("</table>");
			salida.println("<p>Fin de la búsqueda : " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "</p>");
			salida.println("</body></html>");
			salida.close();
			salida=null;
		}		
	}
	
	@After public void end() {
		if(salida!=null) {
			cerrarSalida();
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

		enviarResultados(resultados, "Oviedo");
	}

	public void runDesde(String origen) throws InterruptedException, IOException {
		abrirSalida(StringUtils.substringBefore(origen, "-")  + ".html");
		List<Resultado> resultados=new ArrayList<>();

		Busqueda base=Busqueda.instance(salida, resultados)
			.webTest(webTest)
			.origen(origen)
			.transporte(SI);

		base.modalidad(ISLAS).zona(CANARIAS).buscar();
		base.modalidad(ISLAS).zona(BALEARES).buscar();			
		base.modalidad(COSTAS).zona(CATALUNYA).buscar();
		base.modalidad(COSTAS).zona(ANDALUCIA).buscar();			
		base.modalidad(COSTAS).zona(COMUNIDAD_VALENCIANA).buscar();			
		
		// Finalmente, si tenemos resltados especiales en resultados entonces los enviamos por correo.
		if(resultados.size()>0) {
			enviarResultados(resultados, StringUtils.substringBefore(origen, "-"));
		}
	}
	
	@Test public void runSantander()  throws InterruptedException, IOException {
		runDesde(Busqueda.SANTANDER);		
	}

	@Test public void runBilbao()  throws InterruptedException, IOException {
		runDesde(Busqueda.BILBAO);
	}
		
	@Test public void run()  throws InterruptedException, IOException {
		abrirSalida("destinos-imserso.html");
		runBilbao();
		runSantander();
		runDesde(Busqueda.LEON);
		runOviedo();
	}
	
	@Test public void runOviedo()  throws InterruptedException, IOException {
		abrirSalida("destinos-imserso.html");

		List<Resultado> resultados=new ArrayList<>();
		
		Busqueda.instance(salida, resultados)
				.modalidad(ISLAS)
				.zona(CANARIAS)
				.transporte(SI)
				.buscar(webTest);
		
		Busqueda.instance(salida, resultados)
				.modalidad(ISLAS)
				.zona(BALEARES)
				.transporte(SI)
				.fechaMin("21/03/2025")
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
		for(String zona: Arrays.asList(COMUNIDAD_VALENCIANA,  ANDALUCIA)) {
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
				.fechaMin("01/03/2025")
				.buscar(webTest);

		
		// Finalmente, si tenemos resltados especiales en resultados entonces los enviamos por correo.
		if(resultados.size()>0) {
			enviarResultados(resultados, "Oviedo");
		}

	}
	
	private void enviarResultados(List<Resultado> resultados, String origen) {
		StringBuilder cuerpo=new StringBuilder();

		cuerpo.append("<html>");
		cuerpo.append("<body>");
		cuerpo.append("<p>Se han encontrado viajes del Imserso desde " + origen + " </p>");
		for(Resultado res:resultados) {
			cuerpo.append("<table>");
			cuerpo.append(res.toHtmlTable());
			cuerpo.append("</table>");
			cuerpo.append("<hr>");
		}
		cuerpo.append("</body>");
		cuerpo.append("</html>");

		try {
			mailSender.sendEmail("luismiravalles@gmail.com;vizcarrmen@gmail.com", "Resultados Imserso desde " + origen, cuerpo.toString());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

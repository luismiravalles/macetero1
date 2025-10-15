import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import lombok.extern.apachecommons.CommonsLog;





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
@CommonsLog
public class TestTurismoSocial {	

	static final Log LOGGER = LogFactory.getLog(WebTest.class);

	static final String DESTINATARIOS = System.getProperty("destinatarios");

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
				+ "<link rel='stylesheet' href='src/main/resources/estilos.css'>"
				+ "</head>"
				+ "<body>\n"
				+ "<h1>" + nombre.replace(".html","") + "</h1>\n");
		
		salida.println("<p>Datos obtenidos a fecha : " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "</p>");

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

	@Test public void capitales() throws IOException {
		abrirSalida("capitales.html");	
		List<Resultado> resultados=new ArrayList<>();
		Busqueda.instance(salida,resultados)
			.transporte(ETransporte.NO)
			.zona(EZona.CAPITALES)
			.provincias(EProvincia.capitales())
			.listaEspera(false)
			.buscar(webTest);
		enviarResultados(resultados, "Capitales Provincia Sin Transporte");				
	}

	@Test public void sinTransporte() throws IOException {
		abrirSalida("sinTransporte.html");		
		List<Resultado> resultados=new ArrayList<>();
		EProvincia.costas().forEach(
			provincia ->  {
				Busqueda.instance(salida,resultados)
					.transporte(ETransporte.NO)
					.zona(EZona.COSTAS)
					.provincia(provincia)
					.listaEspera(false)
					.buscar(webTest);

			}
		);
		enviarResultados(resultados, "Sin Transporte");				
	}


	@Test public void islas() throws IOException {
		abrirSalida("islas.html");		
		// Canarias
		for(EOrigen origen: Arrays.asList(EOrigen.OVIEDO_ISLAS, EOrigen.CANTABRIA_ISLAS)) {			
			List<Resultado> resultados=new ArrayList<>();			
			Busqueda.instance(salida,resultados)
				.web("mundicolor.es")
				.origen(origen)
				.zona(EZona.CANARIAS)
				.provincias(EProvincia.canarias())
				.listaEspera(false)
				.buscar(webTest);
			enviarResultados(resultados, origen.getNombre());				
		}

		// Baleares
		for(EOrigen origen: Arrays.asList(EOrigen.OVIEDO_ISLAS, EOrigen.CANTABRIA_ISLAS)) {			
			List<Resultado> resultados=new ArrayList<>();
			Busqueda.instance(salida,resultados)
				.web("mundicolor.es")
				.origen(origen)
				.zona(EZona.BALEARES)
				.provincias(EProvincia.baleares())
				.listaEspera(false)
				.buscar(webTest);
			enviarResultados(resultados, origen.getNombre());				
		}		
	}

	@Test public void costas() throws InterruptedException, IOException {
		abrirSalida("costas.html");		

		for(EOrigen origen: Arrays.asList( EOrigen.OVIEDO, EOrigen.CANTABRIA )) {
			List<Resultado> resultados=new ArrayList<>();
			Busqueda.instance(salida,resultados)
					.origen(origen)			
					.zona(EZona.COSTAS)
					.provincias(EProvincia.costas())
					.filtroFecha(fecha -> 
						fecha.getMonth()!=Month.FEBRUARY &&
						fecha.getMonth()!=Month.NOVEMBER
						)
					.listaEspera(true)
					.buscar(webTest);
			enviarResultados(resultados, origen.getNombre());				
		}

	}

	private void cargar(String fichero, StringBuilder cuerpo) {
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(fichero),
                        StandardCharsets.UTF_8))) {
			cuerpo.append(reader.lines().collect(Collectors.joining("\n")));
		} catch(IOException e) {
			// Nada que hacer,
		}
	}
	
	
	private void enviarResultados(List<Resultado> resultados, String origen) {

		if(resultados.isEmpty()) {
			try {
				// mailSender.sendEmail(DESTINATARIOS, "No hay destinos IMSERSO desde " + origen, "");
			} catch(Exception e) {
				LOGGER.error(e);
			}
			return;
		}

		StringBuilder cuerpo=new StringBuilder();

		cuerpo.append("<html>");
		cuerpo.append("<head>");
		
		cuerpo.append("<style>");
		cargar("estilos.css", cuerpo);
		cuerpo.append("</style>");
		cuerpo.append("</head>");
		

		cuerpo.append("<body>");
		cuerpo.append("<p>Se han encontrado viajes del Imserso desde " + origen + " </p>");
		for(Resultado res:resultados) {
			cuerpo.append("<table>");
			cuerpo.append(res.getHtmlCompleto());
			cuerpo.append("</table>");
			cuerpo.append("<hr>");
		}
		cuerpo.append("</body>");
		cuerpo.append("</html>");

		SimpleDateFormat formato=new SimpleDateFormat("dd/MM/yyyy HH:mm");

		try {
			// Deshabilitamos lo de enviar pq ya no merece la pena.
			if(DESTINATARIOS!=null && DESTINATARIOS.length()>0) {
				mailSender.sendEmail(DESTINATARIOS, "Imserso desde " + origen + " " + formato.format(new Date()), 
							cuerpo.toString() );
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}

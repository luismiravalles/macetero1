import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;

/**
 * Contiene métodos de utilidad para la relación de tests Web con 
 * Selenium y utilizando convenios e infraestructura típica del FW.
 * <p>
 * Para utilizar esta clase debemos tener en cuenta la systemProperty
 * "env" cuyo valor determina a su vez el resto de propiedades que serán
 * cargadas del classpath con el nombre ${env}.properties.
 * <p>
 * 
 * 
 * @author <a href="mailto:miravallesluis@uniovi.es">Luis Miravalles</a>
 *
 */
public class WebTest {
	
	private static final Log LOGGER = LogFactory.getLog(WebTest.class);
	
	/**
	 * El driver obtenido con getDriver.
	 */
	private WebDriver driver=null;
	
	/**
	 * Configuración.
	 */
	private Properties properties=new Properties();
	
	// Contador de fotos.
	private int nFoto;
	
	// Nombre de la foto que se emitira si no se especifica un nombre.	
	private String nombreFotoSiguiente="sinNombre";
	
	// Tiempo maximo de espera en opperaciones wait.
	private int timeout=60;
	
	/**
	 * Constructor por defecto. Carga las propiedades
	 * en funcion de la System property "env".
	 */
	public WebTest() {
		this(System.getProperty("env"));
		// Al margen de lo que haya configurado, en el caso de este tipo de tests
		// es muy interesante activar el LOG a nivel de INFO para todo...
		
	}
	
	/**
	 * Constructor que recibe el entorno como parámertro, por ejemplo
	 * "IC", "LOCAL", etc. Carga el fichero de properties en función de este valor.
	 * 
	 * @param env
	 */
	public WebTest(String env) {
		if(env==null) {
			// Si estamos en Windows entonces no estamos en IC y vamos a poner LOCAL
			// por defecto.
			if(SystemUtils.IS_OS_WINDOWS) {
				env="LOCAL";
			} else {
				env="IC";
			}
		}
		LOGGER.info("Construyendo WebTest con entorno '" + env + "'");
		try {
			this.properties.load(this.getClass().getResourceAsStream("/" + env + ".properties"));
		} catch(IOException e) {
			throw new IllegalStateException("No encontradas las properties");
		}		
	}

	/**
	 * Retorna la URL del driver remoto para las pruebas con Selenium.
	 * Ahora mismo retorna una cadena fija, pero esto deberia obtenerlo
	 * de alguna manera de un properties o algo así, u opcionalmente
	 * de una propiedad del sistema.
	 * 
	 * @return la URL del Driver remoto.
	 * @throws MalformedURLException
	 */
	public URL getDriverURL() throws MalformedURLException {
		String url=properties.getProperty("webtest.driver.url","http://selenium:4444/wd/hub"); 
		return new URL(url);
	}
	
	/**
	 * Retorna un RemoteWebDriver de acuerdo a lo que tenemos instalado
	 * en la infraestructura. Aqui se decide la URL del driver e incluso
	 * el tipo, que por defecto es chrome.
	 * 
	 * @return
	 * @throws MalformedURLException 
	 */
	public WebDriver getDriver() throws MalformedURLException {
		if(this.driver!=null) {
			return this.driver;
		}
		String tipo=properties.getProperty("webtest.driver");
		if("chrome".equals(tipo)) {
			String path=properties.getProperty("webtest.driver.path");
			if(path!=null) {
				System.setProperty("webdriver.chrome.driver", path);
			}
			LOGGER.info("Obteniendo driver Chrome Local");			
			this.driver=new ChromeDriver();
		} else 	if("firefox".equals(tipo)) {
			String path=properties.getProperty("webtest.driver.path");
			if(path!=null) {
				System.setProperty("webdriver.gecko.driver", path);
			}
			LOGGER.info("Obteniendo driver Firefox Local");			
			this.driver=new FirefoxDriver();
		} else  {
			URL url=getDriverURL();
			LOGGER.info("Obteniendo driver remoto de la URL " + url);
			ChromeOptions options=new ChromeOptions();
			options.addArguments("--lang=es");
			DesiredCapabilities dc=DesiredCapabilities.chrome();
			dc.setCapability(ChromeOptions.CAPABILITY, options);
			this.driver=new RemoteWebDriver(url, dc);
			LOGGER.info("Driver remoto obtenido " + url);
		}		
		return this.driver;
	}
	
	/**
	 * Retorna la URL de la aplicación. Se obtiene de la property
	 * webtest.app.url 
	 * @return
	 */
	public String getAppUrl() {
		return properties.getProperty("webtest.app.url");
	}
	
	/**
	 * Retorna una expresión By de Selenium basada en el id del elemento
	 * a buscar de tipo input. Como trabajamos con JSF los ids de los elementos están
	 * prefijados con multitud de cosas que molestan, por eso la búsqueda
	 * se hace realmente por xpath.
	 * 
	 * @param id
	 * @return
	 */
	public By byInputId(String id) {
		return By.xpath("//input[contains(@id,'" + id + "')]");
	}
	
	
	/**
	 * Busca cualquier elemento cuyo id contenga el recibido como parámetro 
	 * 
	 * @param id El id del elemento a buscar.
	 * @return
	 */
	public By byId(String id) {
		return By.xpath("//*[contains(@id,'" + id + "')]");
	}
	
	/**
	 * Busca cualquier elemento del tipo especificado cuyo id contenga el recibido como parámetro 
	 * 
	 * @param tipo El tipo del elemento, por ejemplo label, o input...
	 * @param id El id del elemento a buscar.
	 * @return
	 */
	public By byId(String tipo, String id) {
		return By.xpath("//" + tipo + "[contains(@id,'" + id + "')]");
	}
	
	/**
	 * Busca cualquier elemento del tipo especificado cuyo atributo especificado contenga el recibido como parámetro 
	 * 
	 * @param tipo El tipo del elemento, por ejemplo label, o input...
	 * @param att El atributo a considerar
	 * @param valor El valor que tiene que contener . (Se usa contains),
	 * @return
	 */
	public By byAtt(String tipo, String att, String valor) {
		return By.xpath("//" + tipo + "[contains(@" + att + ",'" + valor+ "')]");
	}	
		
	
	/**
	 * Busca cualquier elemento cuyo texto sea exactamente
	 * el recibido como parámetro.
	 * <p>
	 * La comparación se realiza exactamente quitando espacios por delante y por detrás.
	 * 
	 * @param texto El texto que debe contener el elemento
	 */
	public By byText(String texto) {
		return By.xpath("//*[normalize-space() = '" + texto + "']");
	}
	
	/**
	 * Busca cualquier elemento del tipo especificado cuyo texto sea exactamente
	 * el recibido como parámetro.
	 * <p>
	 * La comparación se realiza exactamente quitando espacios por delante y por detrás.
	 * 
	 * @param tipo El tipo de elemento a buscar, por ejemplo "a".
	 * @param texto El texto que debe contener el elemento
	 */
	public By byText(String tipo, String texto) {
		return By.xpath("//" + tipo+ "[normalize-space() = '" + texto + "']");
	}
	
	/**
	 * Busca cualquier elemento del tipo especificado cuyo texto CONTENGA el
	 * recibido como parámetro.
	 * 
	 * <p>
	 * 
	 * 
	 * @param tipo El tipo de elemento a buscar, por ejemplo "a".
	 * @param texto El texto que debe contener el elemento
	 */
	public By byTextContains(String tipo, String texto) {
		return By.xpath("//" + tipo+ "[contains(.,'" + texto  + "')]");
	}		
	
	
	/**
	 * Escribe el texto indicado sobre el input cuyo nombre contenga el id.
	 * Es equivalente a driver.findElement(byInputId(id)).sendKeys(id).
	 * Se hace log a nivel de INFO en el logger. 
	 * 
	 * @param id El id del elemento
	 * @param texto El texto a enviar.
	 */
	public WebTest sendKeys(String id, String texto) {
		findElement(byId(id)).sendKeys(texto);
		LOGGER.info(String.format("Escrito texto '%s' sobre input con id '%s'", texto, id));
		nombreFotoSiguiente=id;
		return this;
	}
	
	/**
	 * Limpia el contenido del elemento
	 * 
	 * @param id El id del elemento
	 */
	public WebTest clear(String id) {
		findElement(byId(id)).clear();
		LOGGER.info(String.format("Limpiado contenido de input con id '%s'",  id));
		nombreFotoSiguiente=id;
		return this;
	}
	
	
	/**
	 * Hace click sobre un elemento que tenga exactamente el contenido especificado
	 * 
	 * <p>Es equivalente a driver.findElement(byText(texto)).click()
	 * <p>Se hace log a nivel de INFO en el logger.
	 * 
	 * @param contenido El contenido que debe tener el elemento
	 */
	public WebTest click(String contenido) {		
		findElement(byText(contenido)).click();
		LOGGER.info(String.format("Hecho click sobre elemento con contenido '%s'", contenido));
		nombreFotoSiguiente=contenido;
		return this;
	}
	
	/**
	 * Hace click sobre un elemento que tenga exactamente el contenido especificado
	 * <p>Es equivalente a driver.findElement(byText(texto)).click()
	 * <p>Se hace log a nivel de INFO en el logger.
	 * 
	 * @param El tipo del elemento.
	 * @param contenido El contenido que debe tener el elemento
	 */
	public WebTest click(String tipo, String contenido) {		
		findElement(byText(tipo, contenido)).click();
		LOGGER.info(String.format("Hecho click sobre elemento tipo '%s', con contenido '%s'", tipo, contenido));
		nombreFotoSiguiente=contenido;
		return this;
	}	
	
	/**
	 * Hace click sobre un elemento que Conntenga el contenido especificado
	 * 
	 * @param El tipo del elemento.
	 * @param contenido El contenido que debe tener el elemento
	 */
	public WebTest clickContains(String tipo, String contenido) {		
		findElement(byTextContains(tipo, contenido)).click();
		LOGGER.info(String.format("Hecho click sobre elemento tipo '%s', con contenido '%s'", tipo, contenido));
		nombreFotoSiguiente=contenido;
		return this;
	}		
	
	/**
	 * Hace click sobre un elementod de tipo 'a' cuyo atributo href contenga
	 * lo recibido como parámetro
	 * 
	 * @param href El contenido de href a buscar:
	 * @return
	 */
	public WebTest clickByHref(String href) {
		findElement(By.xpath("//a[contains(@href,'" + href + "')]")).click();
		LOGGER.info(String.format("Hecho click sobre elemento 'a' con href '%s'", href));
		nombreFotoSiguiente=href;
		return this;		
	}
	
	
	/**
	 * Hace click en un elemento buscado a partir de una parte de su id.
	 * 
	 * @param parteId
	 * @return
	 */
	public WebTest clickById(String parteId) {
		findElement(byId(parteId)).click();
		LOGGER.info(String.format("Hecho click sobre elemento con id '%s'", parteId));
		nombreFotoSiguiente=parteId;		
		return this;
	}
	
	/**
	 * Hace click sobre un radio de tipo Primefaces.
	 * 
	 * @param parteId parte del Id.
	 * @return 
	 */
	public WebTest clickRadio(String parteId) {
		findElement(byAtt("label", "for", parteId)).click();
		LOGGER.info(String.format("Hecho click sobre elemento con id '%s'", parteId));
		nombreFotoSiguiente=parteId;		
		return this;
	}
	
	
	/**
	 * Hace click en el Link cuyo contenido sea el especificado.
	 * 
	 * @param contenido Contenido que debe tener el link
	 * @return
	 */
	public WebTest link(String contenido) {
		findElement(By.linkText(contenido)).click();
		LOGGER.info(String.format("Hecho click sobre elemento con contenido '%s'", contenido));
		nombreFotoSiguiente=contenido;
		return this;
	}
	
	/**
	 * Toma una captura de pantalla del estado actual del navegador.
	 * <p>Se guarda en el target del proyecto con el nombre del ultimo
	 * elemento clickado o introducido
	 * 
	 * @throws IOException
	 */
	public WebTest foto() {
		foto(nombreFotoSiguiente);
		return this;
	}
	
	/**
	 * Sacar una foto 
	 * @param nombre
	 * @throws IOException 
	 */
	public WebTest foto(String nombre)  {	
		sleep(2);	// Dos segundos antes de sacar la foto para que no salga movida :-)
		File file=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		String salida=String.format("target/%03d-%s.png", nFoto, nombre.replaceAll(" ", "-"));
		try {
			FileUtils.copyFile(file, new File(salida));
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}
		
		String prefijoFoto=System.getProperty("prefijo.foto", "file://" + Paths.get("").toAbsolutePath() + "/");
		LOGGER.info("Foto " + prefijoFoto + salida);
		nFoto++;
		return this;
	}
	
	
	/**
	 * Hacer login sobre la página del CAS 
	 */
	public WebTest casLogin(String username, String password) {
		try {
			WebElement mostrarUsuario=driver.findElement(By.id("botonMostarUsuario"));
			mostrarUsuario.click();
		} catch(NoSuchElementException e) {
			// Nada que hacer.
		}
		
		findElement(By.id("username")).sendKeys(username);
		findElement(By.id("password")).sendKeys(password);
		findElement(By.xpath("//*[@name='submit']")).click();
		return this;
	}
	
	/**
	 * Selecciona una opcion del menu que esté visible a partir su contenido.
	 * <p>Es util para aplicaciones JSF2 que usan los tags uo:menu... <p>
	 * 
	 * @param contenido Alguna parte de su contenido.
	 * @return
	 */
	public WebTest menu(String contenido) {
		findElement(By.xpath("//div[@id='collapseMenu']//a[contains(.,'" + contenido + "')]")).click();
		LOGGER.info("Pulsada opcion " + contenido);
		nombreFotoSiguiente=contenido;
		sleep(1);
		return this;
	}
	
	/**
	 * Espera unos segundos a la reacción del navegador.
	 */
	public WebTest sleep(int segundos) {
		try {
			TimeUnit.SECONDS.sleep(segundos);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new IllegalStateException(e);
		}
		return this;
	}
	
	/**
	 * Cambia la pestaña actual
	 */
	public WebTest selectTab() {
		String actual=driver.getWindowHandle();
		for(String otro:driver.getWindowHandles()) {
			if(!otro.equals(actual)) {
				driver.switchTo().window(otro);
			}
		}
		return this;		
	}
	
	/**
	 * Busca el elemento especificado por el parámetro by. Hace lo mismo que el método
	 * homónimo de Selenium, pero si falla saca una foto del navegador en este instante
	 * para ayudar a interpretar el problema.
	 * 
	 * @param by
	 * @return El elemento buscado
	 * @Throws NoSuchElement
	 */
	public WebElement findElement(By by) {
		try {
			return driver.findElement(by);
		} catch(NoSuchElementException e) {
			LOGGER.error("No se ha encontrado el elemento buscado: " + by.toString());
			foto("errorNoEncontrado");
			throw e;
		}
	}
	
	/**
	 * En aplicaciones JSF2 con componentes sofisticados como p:selectOneMenu resulta 
	 * muy dificil seleccionar un elemento mediante el driver ya que por debajo se crean
	 * unas extarañas estructuras. 
	 * <p>
	 * Con este método se facilita la labor.
	 * <p>
	 * 
	 * @param id El identificador del elemento p:selectOneMenu
	 * @param valor El valor a elegir de la lista.
	 * @return el propio WebTest...
	 */
	public WebTest select(String id, String valor) {
		WebElement selector=findElement(By.xpath("//label[contains(@id, ':" + id + "_label')]"));
		selector.click();
		sleep(1); // Por verlo...
		
		WebElement elemento=findElement(By.xpath("//ul[contains(@id,':" + id + "_items')]/li[normalize-space() = '" + valor + "']"));
		elemento.click();
		
		LOGGER.info("Selecionada opcion " + valor + " del selector " + id);
		nombreFotoSiguiente=valor;		
		return this;
	}
	
	/**
	 * Retorna true si se ha producido una excepción. Esto se verifica buscando
	 * en la pantalla un texto "Se ha producido una excepción".
	 * 
	 * @return true si se se produjo excepción.
	 */
	public boolean checkException() {
		try {
			driver.findElement(By.xpath("//*[normalize-space() = 'Se ha producido una excepción']"));
		} catch(NoSuchElementException e) {
			return false;
		}
		return true;
	}
	
	/**
	 * Espera un tiempo hasta que aparezca un determinado componente. Hace una comprobación
	 * cada segundo hasta un limite por defecto de 60 segundos.
	 * <p>
	 * Una vez pasdo el limite de tiempo si no lo encuentra lanzará excepción dando por fallido el test.
	 * 
	 * @param by El elemento a buscar.
	 * 
	 * @return El elemento encontrado. Nunca retorna null. En caso de no encontrarlo se lanza excepción NoSuchElementException
	 * 
	 */
	public WebElement wait(By by) {
		int i=0;
		while(true) {
			sleep(1);
			try {
				return driver.findElement(by);
			} catch(NoSuchElementException e) {
				if(i>=timeout) {
					throw e;
				}
			}		
			i++;
		}
	}
	
	/**
	 * Cerrar y salir del navegador.
	 */
	public void closeAndQuit() {
		if(driver!=null) {			
			driver.close();
			try {
				driver.quit();
			} catch(Exception e) {
				// NO hace falta hacer nada.
			}
		}
	}
	
	
}

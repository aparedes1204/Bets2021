package test.gui;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.swing.core.matcher.JButtonMatcher.withName;
import static org.assertj.swing.core.matcher.JButtonMatcher.withText;

import java.awt.Color;
import java.awt.Frame;
import java.net.URL;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.assertj.swing.edt.FailOnThreadViolationRepaintManager;
import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import dao.DAOManager;
import dataAccess.DataAccess;
import dataAccess.DataAccessDAO;
import dataAccess.DataAccessInterface;
import gui.MainGUI;
/**
 * @see https://www.jc-mouse.net/
 * @author mouse
 */
public class TestApp {

    private FrameFixture window;

    /**
     * Constructor de clase
     */
    public TestApp() {}

    /**
     * Fuerza a una prueba a fallar si el acceso a los componentes de la GUI
     * no se realiza en el EDT (Event Dispatch Thread)
     */
    @BeforeClass
    public static void setUpOnce() {
        FailOnThreadViolationRepaintManager.install();
    }

    /**
     * Inicializa los dispositivos de prueba, se ejecuta cada vez 
     * que se ejecute un método de prueba
     */
    @Before
    public void setUp() {
    	//Frame frame=configureStart();
        MainGUI frame = GuiActionRunner.execute(() -> new MainGUI());
        configureStart(frame);
        window = new FrameFixture(frame);
        window.show();
        try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

    /**     
     * Limpia los recursos utilizados después de ejecutar cada método de prueba
     * y libera el bloqueo de teclado y moyse para la siguiente prueba     
     */
    @After
    public void tearDown() {
        window.cleanUp();
    }
    
    @Test
    public void ConversionOctal() {                
        //window.textBox("numero").enterText("45");
        //window.comboBox("opcion").selectItem("Octal");        
        //window.button(withName("convertir")).click();       
        //realiza la comparación de resultados
        //assertThat(window.textBox("resultado").text()).isEqualTo("55");        
    }
  /*  
    @Test
    public void conversionHexadecimal() {                
        window.textBox("numero").enterText("26");
        window.comboBox("opcion").selectItem("Hexadecimal");        
        window.button(withName("convertir")).click();         
        //realiza la comparación de resultados
        assertThat(window.textBox("resultado").text()).isEqualTo("1A");        
    }
     
    @Test
    public void numeroFueraDerango() {
        window.textBox("numero").enterText("226");
        window.comboBox("opcion").selectItem("Hexadecimal");
        window.button(withName("convertir")).click();
        //cierra la ventana de alerta
        window.dialog().button().click();
        //verifica que controles se reinicien a cero "0"
        assertThat(window.textBox("numero").text()).isEqualTo("0");     
        assertThat(window.textBox("resultado").text()).isEqualTo("0");     
    }

    @Test
    public void valorNumericoNoValido() {
        window.textBox("numero").enterText("");
        window.comboBox("opcion").selectItem("Octal");        
        window.button("convertir").click();
        //Cierra ventana de alerta
        window.dialog().button(withText("No lo vuelvo hacer")).click();
        //se comprueba que controles esten deshabilitados
        window.textBox("numero").requireDisabled();
        window.comboBox("opcion").requireDisabled();
        window.button("convertir").requireDisabled();
    }
    
    @Test
    public void errorCritico() {
        window.textBox("numero").enterText("98899979941993239211992309990991");
        window.comboBox("opcion").selectItem("Hexadecimal");
        window.button("convertir").click();
        //cierra ventana de alerta
        window.dialog().button().click();        
        //verifica que textbox se haya reiniciado a cero "0"
        assertThat(window.textBox("numero").text()).isEqualTo("0");    
    }
*/
    public void configureStart(JFrame frame) {
    	
    	ConfigXML c=ConfigXML.getInstance();
    	
		System.out.println(c.getLocale());
		
		Locale.setDefault(new Locale(c.getLocale()));
		
		System.out.println("Locale: "+Locale.getDefault());
		
		
		frame.setVisible(true);


		try {
			
			BLFacade appFacadeInterface;
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel");
//			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
			
			if (c.isBusinessLogicLocal()) {
				
				//In this option the DataAccess is created by FacadeImplementationWS
				//appFacadeInterface=new BLFacadeImplementation();

				//In this option, you can parameterize the DataAccess (e.g. a Mock DataAccess object)
				String dbManagerClassName=c.getDAOManagerClassName();
				DataAccessInterface da;
				if (dbManagerClassName.length()==0)
					 da= new DataAccess();
				else {
					DAOManager daoManager=(DAOManager)Class.forName("dao."+dbManagerClassName).newInstance();
					da=new DataAccessDAO(daoManager);
				} 					
				
				appFacadeInterface=new BLFacadeImplementation(da);

				
			}
			
			else { //If remote
				
				 String serviceName= "http://"+c.getBusinessLogicNode() +":"+ c.getBusinessLogicPort()+"/ws/"+c.getBusinessLogicName()+"?wsdl";
				 
				//URL url = new URL("http://localhost:9999/ws/ruralHouses?wsdl");
				URL url = new URL(serviceName);

		 
		        //1st argument refers to wsdl document above
				//2nd argument is service name, refer to wsdl document above
//		        QName qname = new QName("http://businessLogic/", "FacadeImplementationWSService");
		        QName qname = new QName("http://businessLogic/", "BLFacadeImplementationService");
		 
		        Service service = Service.create(url, qname);

		         appFacadeInterface = service.getPort(BLFacade.class);
			} 
			/*if (c.getDataBaseOpenMode().equals("initialize")) 
				appFacadeInterface.initializeBD();
				*/
			MainGUI.setBussinessLogic(appFacadeInterface);
 } catch (Exception e) {
		//a.jLabelSelectOption.setText("Error: "+e.toString());
		//a.jLabelSelectOption.setForeground(Color.RED);	
		
		System.out.println("Error in ApplicationLauncher: "+e.toString());
	}
}
}



package anmao.mc.nekoui.config;

import anmao.mc.nekoui.NekoUI;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;

public class CConfig {
    private static String DIR_RUN;
    private static String DIR_CONFIG;

    private static String FILE_CONFIG_ITEM;
    public static int[][] info;
    public static void _start(){
        DIR_RUN = System.getProperty("user.dir");
        DIR_CONFIG = DIR_RUN+"\\NekoConfig";
        File folder = new File(DIR_CONFIG);
        if (!folder.exists()){
            if (!folder.mkdir()){
                NekoUI.LOGGER.debug("Create Dir failed");
                System.exit(-9999);
            }
        }
        FILE_CONFIG_ITEM = DIR_CONFIG +"\\NekoUI_Hud_Item.xml";
        d();
    }
    /*
    <config>
    <database>
        <host>localhost</host>
        <port>3306</port>
        <username>myuser</username>
        <password>mypassword</password>
    </database>
</config>

     */
    private static void d(){
        File itemConfig = new File(FILE_CONFIG_ITEM);
        if (!itemConfig.exists()){
            try {
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document document = dBuilder.newDocument();

                Element rootElement = document.createElement("config");
                document.appendChild(rootElement);

                Element databaseElement = document.createElement("database");
                rootElement.appendChild(databaseElement);

                Element hostElement = document.createElement("host");
                Text hostValue = document.createTextNode("newhost");
                hostElement.appendChild(hostValue);
                databaseElement.appendChild(hostElement);

                Element portElement = document.createElement("port");
                Text portValue = document.createTextNode("5432");
                portElement.appendChild(portValue);
                databaseElement.appendChild(portElement);

                Element usernameElement = document.createElement("username");
                Text usernameValue = document.createTextNode("newuser");
                usernameElement.appendChild(usernameValue);
                databaseElement.appendChild(usernameElement);

                Element passwordElement = document.createElement("password");
                Text passwordValue = document.createTextNode("newpassword");
                passwordElement.appendChild(passwordValue);
                databaseElement.appendChild(passwordElement);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                StreamResult result = new StreamResult(new File("config.xml"));
                transformer.transform(source, result);

                System.out.println("XML configuration file updated successfully.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void _load(){
        try {
            File configFile = new File("config.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document document = dBuilder.parse(configFile);

            Element rootElement = document.getDocumentElement();
            Element databaseElement = (Element) rootElement.getElementsByTagName("database").item(0);

            String host = databaseElement.getElementsByTagName("host").item(0).getTextContent();
            int port = Integer.parseInt(databaseElement.getElementsByTagName("port").item(0).getTextContent());
            String username = databaseElement.getElementsByTagName("username").item(0).getTextContent();
            String password = databaseElement.getElementsByTagName("password").item(0).getTextContent();

            System.out.println("Database Host: " + host);
            System.out.println("Database Port: " + port);
            System.out.println("Database Username: " + username);
            System.out.println("Database Password: " + password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

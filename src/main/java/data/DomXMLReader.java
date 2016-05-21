package data;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import model.field.Field.States;
import model.game.Game;

/**
 * This class is for reading an xml file and set up a new game according to xml.
 * 
 * @author ShadowJabtko
 *
 * @param <T>
 *            Object which extends {@code Game}.
 */
public class DomXMLReader<T extends Game<?>> {
	/**
	 * Object for holding the {@code Game}.
	 */
	T game;

	/**
	 * Constructs a newly allocated {@code DomXMLReader}.
	 * 
	 * @param game
	 *            The {@code Game} we want to set up.
	 */
	public DomXMLReader(T game) {
		this.game = game;
	}

	/**
	 * Set up {@code Game} according to the given xml. 
	 * 
	 * @param folder
	 *            The folder that contains the xml.
	 * @param fileName
	 *            The file name of the xml.
	 */
	public void setGameFieldFromXML(String folder, String fileName) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();

			Document doc = null;
			if (folder.equals("maps")) {
				String resourcePath = "/" + folder + "/" + fileName;
				InputStream is = getClass().getResourceAsStream(resourcePath);
				doc = builder.parse(is);
			} else if (folder.equals("custom_maps")) {
				Path fullPath = Paths.get(System.getProperty("user.home"), "Hexxagon Game", folder, fileName);
				doc = builder.parse(fullPath.toFile());
			}

			Element root = (Element) doc.getElementsByTagName("game").item(0);
			int sizeX = Integer.parseInt(root.getElementsByTagName("sizeX").item(0).getTextContent());
			int sizeY = Integer.parseInt(root.getElementsByTagName("sizeY").item(0).getTextContent());
			game.setSizeX(sizeX);
			game.setSizeY(sizeY);

			NodeList fields = root.getElementsByTagName("field");
			for (int i = 0; i < fields.getLength(); i++) {
				Element field = (Element) fields.item(i);
				int axisX = Integer.parseInt(field.getElementsByTagName("axisX").item(0).getTextContent());
				int axisY = Integer.parseInt(field.getElementsByTagName("axisY").item(0).getTextContent());
				String fieldMode = field.getAttribute("mode");
				game.getField(axisX, axisY).setState(States.valueOf(fieldMode));
			}

		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

package data;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.game.Game;

/**
 * This class is for write out a valid xml file for {@code DomXMLReader}.
 * 
 * @author ShadowJabtko
 *
 * @param <T>
 *            Object which extends {@code Game}.
 * 
 * @see data.DomXMLReader
 */
public class DomXMLWriter<T extends Game<?>> {

	/**
	 * Object for holding the {@code Game}.
	 */
	T game;
	/**
	 * The file name of the xml document.
	 */
	String fileName;
	/**
	 * This variable is for decide if the user want to rewrite a file or not.
	 */
	boolean reWrite;

	/**
	 * Constructs a newly allocated {@code DomXMLWriter} and creates the game
	 * folder to user home directory if it is need.
	 * 
	 * @param game
	 *            The {@code Game} we want to write out.
	 */
	public DomXMLWriter(T game) {
		this.game = game;
		this.fileName = new String("");
		reWrite = false;
		Path path = Paths.get(System.getProperty("user.home"), "Hexxagon Game");
		if (!path.toFile().exists()) {
			path.toFile().mkdir();
			path = Paths.get(System.getProperty("user.home"), "Hexxagon Game", "custom_maps");
			path.toFile().mkdir();
		}
	}

	/**
	 * Sets the name of the file.
	 * 
	 * @param fileName
	 *            The file name.
	 */
	public void setFileName(String fileName) {
		if (!this.fileName.equals(fileName)) {
			reWrite = false;
		}
		this.fileName = fileName;
	}

	/**
	 * Returns the file name.
	 * 
	 * @return The name of the file,
	 */
	public String getFileName() {
		return this.fileName;
	}

	/**
	 * Creates a {@code Path} according to user gave ".xml" ending to file name
	 * or not.
	 * 
	 * @return {@code Path} where the file will be saved.
	 */
	private Path getPath() {
		if (fileName.endsWith(".xml")) {
			return Paths.get(System.getProperty("user.home"), "Hexxagon Game", "custom_maps", fileName);
		} else {
			return Paths.get(System.getProperty("user.home"), "Hexxagon Game", "custom_maps", fileName + ".xml");
		}
	}

	/**
	 * Examine if the file exist or not.
	 * 
	 * @return {@code true} if exists; {@code false} otherwise;
	 */
	public boolean isExist() {
		if (getPath().toFile().exists()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns the variable reWrite.
	 * 
	 * @return reWrite.
	 */
	public boolean getReWrite() {
		return reWrite;
	}

	/**
	 * Sets the reWrite variable.
	 * 
	 * @param value
	 *            ...
	 */
	public void setReWrite(boolean value) {
		reWrite = value;
	}

	/**
	 * Write out the {@code Game} to the specified path location.
	 */
	public void writeOut() {
		writeOut(getPath().toFile());
	}

	/**
	 * Write out the {@code Game} to the specified file.
	 * 
	 * @param file
	 *            The file where want to save.
	 */
	public void writeOut(File file) {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("game");

			Element sizeX = doc.createElement("sizeX");
			sizeX.appendChild(doc.createTextNode(new Integer(game.getSizeX()).toString()));
			root.appendChild(sizeX);

			Element sizeY = doc.createElement("sizeY");
			sizeY.appendChild(doc.createTextNode(new Integer(game.getSizeY()).toString()));
			root.appendChild(sizeY);

			Element next = doc.createElement("next");
			next.appendChild(doc.createTextNode(game.getPlayer().getPlayer().toString()));
			root.appendChild(next);

			Attr gameMode = doc.createAttribute("mode");
			gameMode.setValue("map");
			root.setAttributeNode(gameMode);

			doc.appendChild(root);

			Element fields = doc.createElement("fields");
			for (int i = 0; i < game.getSizeX(); i++) {
				for (int j = 0; j < game.getSizeY(); j++) {
					Element field = doc.createElement("field");
					Attr fieldMode = doc.createAttribute("mode");
					fieldMode.setValue(game.getField(i, j).getState().toString());
					field.setAttributeNode(fieldMode);
					Element axisX = doc.createElement("axisX");
					Element axisY = doc.createElement("axisY");
					axisX.appendChild(doc.createTextNode(new Integer(i).toString()));
					axisY.appendChild(doc.createTextNode(new Integer(j).toString()));
					field.appendChild(axisX);
					field.appendChild(axisY);
					fields.appendChild(field);
				}
			}
			root.appendChild(fields);

			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer trans = tFactory.newTransformer();
			trans.setOutputProperty(OutputKeys.INDENT, "yes");
			trans.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");

			DOMSource source = new DOMSource(doc);

			StreamResult result = new StreamResult(file);
			trans.transform(source, result);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

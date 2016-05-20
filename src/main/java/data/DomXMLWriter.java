package data;

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

public class DomXMLWriter<T extends Game<?>> {
	T game;
	String fileName;
	boolean reWrite;

	public DomXMLWriter(T game) {
		this.game = game;
		this.fileName = new String("");
		reWrite = false;
		Path path = Paths.get(System.getProperty("user.home"), "Hexxagon Game","custom_maps");
		if(!path.toFile().exists()){
			path.toFile().mkdir();
		}
	}
	
	public void setFileName(String fileName){
		if (!this.fileName.equals(fileName)) {
			reWrite = false;
		}
		this.fileName = fileName.replace(" ", "_");
	}
	
	public String getFileName(){
		return this.fileName;
	}
	
	private Path getPath(){
		if (fileName.endsWith(".xml")) {
			return Paths.get(System.getProperty("user.home"), "Hexxagon Game",fileName);
		} else {
			return Paths.get(System.getProperty("user.home"), "Hexxagon Game",fileName + ".xml");
		}
	}
	
	public boolean isExist(){
		if (getPath().toFile().exists()) {
			return true;
		}
		return false;
	}
	
	public boolean getReWrite(){
		return reWrite;
	}
	
	public void setReWrite(boolean value){
		reWrite = value;
	}
	
	public void writeOut() {
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
			Path path = getPath();

			StreamResult result = new StreamResult(path.toFile());
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

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

public class DomXMLReader<T extends Game<?>> {
	
	T game;
	
	public DomXMLReader(T game){
		this.game = game;
	}
	
	public void setGameFieldFromXML(String path){
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			//Path path = Paths.get("/maps", "map1.xml");
			//System.out.println(path.toString());
			//InputStream is = getClass().getResourceAsStream("/maps/map1.xml");
			//tring asd = path.toString();
			//asd = asd.replace("\\", "/");
			//System.out.println(asd);
			//InputStream is = getClass().getResourceAsStream(asd);
			//System.out.println("\\maps\\map1.xml");
			
			//Document doc = builder.parse(filePath.toFile());
			String[] splitted = path.split(" ");
			Document doc = null;
			if (splitted[0].equals("maps")) {
				String resourcePath = "/" + splitted[0] + "/" + splitted[1];
				InputStream is = getClass().getResourceAsStream(resourcePath);
				doc = builder.parse(is);
			} else if(splitted[0].equals("custom_maps")){
				Path fullPath = Paths.get(System.getProperty("user.home"), "Hexxagon Game", splitted[0],splitted[1]);
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
				/*
				States state = States.SELECT;
				if (fieldMode.equals("delete")) {
					state = States.DELETE;
				} else if (fieldMode.equals("red_player")){
					state = States.RED_PLAYER;
				} else if (fieldMode.equals("green_player")){
					state = States.GREEN_PLAYER;
				}
				*/
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

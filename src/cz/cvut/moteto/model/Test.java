/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.model;

import java.io.File;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Jan Zdrha
 */
public class Test implements Serializable {

    private String path;
    private String name;
    List<Task> tasks;
    List<String> participants;
    HashMap<String, Marker> markers;

    public Test(String path) {
        this.path = path;
        this.name = new File(path).getName().replace(".xml", "");

    }

    public void open() throws Exception {
    	Document doc = getDocument();
    	markers = loadMarkers(doc);
    	tasks = loadTasks(doc);
    	participants = loadParticipants(doc);
    }
    
    public Document getDocument() throws Exception {
    	return WorkSpace.loadXML(path);
    }

    public List<Task> getTasks() {
		return tasks;
	}

	public List<String> getParticipants() {
		return participants;
	}

	public HashMap<String, Marker> getMarkers() {
		return markers;
	}

	@Override
    public String toString() {
        return name;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    public List<Task> loadTasks(Document doc) {
       List<Task> tasks = new LinkedList<Task>();

       NodeList nodeList = doc.getElementsByTagName("task");

       for (int i = 0; i < nodeList.getLength(); i++) {

			Node node = nodeList.item(i);

			Element el = (Element) node;

			String markerId = el.getAttribute("markers");
			Marker marker;
			if (markers.containsKey(markerId)) {
				marker = markers.get(markerId);
			} else if (markers.size() > 0) {
				marker = markers.values().iterator().next();
			} else {
				marker = new Marker();
			}
			
			tasks.add(new Task(i+1, el.getAttribute("name"), marker.getChildren()));
        }

        return tasks;
    }

    public List<String> loadParticipants(Document doc) {
      List<String> participants = new LinkedList<String>();

      NodeList nodeList = doc.getElementsByTagName("participant");

      for (int i = 0; i < nodeList.getLength(); i++) {

			Node node = nodeList.item(i);

			Element el = (Element) node;

			participants.add(el.getAttribute("name"));
      }

      return participants;
    }

    public List<Session> getSessions() {
       List<Session> sessions = new LinkedList<Session>();

       File f = new File(getSessionsDir());

       File[] fileArray =  f.listFiles();
       
       if (fileArray != null) {
    	   List<File> files = Arrays.asList(fileArray);
    	   Collections.sort(files);
    	   for (File file : files) {
    		   sessions.add(new Session(this, file.getPath()));
    	   }
       }

       return sessions;
    }
    
    public String getSessionsDir() {
    	return new File(path).getParent()+"/"+name+"-sessions";
    }

    public Session getNewSession(String participant){
    	String sessionPath = String.format("%s/%02d-%s.xml", getSessionsDir(), getSessions().size()+1, participant);
        return new Session(this, sessionPath, participant);
    }

    public HashMap<String, Marker> loadMarkers(Document doc) {
        HashMap<String, Marker> map = new HashMap<String, Marker>();
        
        Node markerNode = doc.getElementsByTagName("markers").item(0);
        Marker marker = new Marker();
        
        loadMarkersR(marker, markerNode, map);

        return map;
    }
    
    private void loadMarkersR(Marker marker, Node markerNode, HashMap<String, Marker> map) {
    	NodeList children = markerNode.getChildNodes();
    	for (int i = 0, j = 0; i < children.getLength(); i+=1) {
    		Node childNode = children.item(i);
    		if (childNode.getNodeType() != Node.ELEMENT_NODE) {
    			continue;
    		}
    		Element childElement = (Element) childNode;
    		
    		String gesture = childElement.getAttribute("gesture");
    		if (gesture == null) {
    			gesture = "marker"+(j+1);
    		}
    		j += 1;
    		     		
    		Marker childMarker = new Marker(childElement.getAttribute("name"), gesture);
    		if (childNode.hasChildNodes()) {
    			loadMarkersR(childMarker, childNode, map);
    		}
    		marker.addChild(childMarker);
    		if (childElement.hasAttribute("id")) {
    			map.put(childElement.getAttribute("id"), childMarker);
    		}
    	}
    }
}

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
	//private Document document = null;

    public Test(String path) {
        this.path = path;

    }

    public void open() {
        // TODO
    }

    public Document load() {
        String path = WorkSpace.getInstance().getWorkspaceFolder()+this.path+".xml";
        return WorkSpace.loadXML(path);
    }

    public Document getDocument() {
    	//if (this.document == null) {
    	//	this.load();
    	//}
    	//return this.document;
    	return this.load();
    }


    @Override
    public String toString() {
        return this.getPath();
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    public List<Task> getTasks() {
        List<Task> tasks = new LinkedList<Task>();

       Document doc = this.getDocument();
       NodeList nodeList = doc.getElementsByTagName("task");
       HashMap<String,Marker> markers = getMarkers();

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
//         tasks.add(new Task("Task 1"));
//         tasks.add(new Task("Task 2"));
//         tasks.add(new Task("Task 3"));

        return tasks;
    }

//     public List<String> getTaskNames() {
//         List<String> tasklist = new LinkedList<String>();
//         for(Task t : getTasks()) {
//         	tasklist.add(t.getPath());
//         }
//         return tasklist;
//     }

    public List<String> getParticipants() {
        List<String> participants = new LinkedList<String>();

      Document doc = this.getDocument();
      NodeList nodeList = doc.getElementsByTagName("participant");

      for (int i = 0; i < nodeList.getLength(); i++) {

			Node node = nodeList.item(i);

			Element el = (Element) node;

			participants.add(el.getAttribute("name"));
      }

//        participants.add("Pepa");
//        participants.add("Rudolf");
//        participants.add("Robert");

        return participants;
    }

    public List<Session> getSessions() {
       List<Session> sessions = new LinkedList<Session>();

//         sessions.add(new Session(test, test.toString() + "session 1", ""));
//         sessions.add(new Session(test, test.toString() + "session 2", ""));
//         sessions.add(new Session(test, test.toString() + "session 3", ""));

       File f = new File(WorkSpace.getInstance().getWorkspaceFolder()+this.path+"-sessions");

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

    public Session getNewSession(String participant){
    	String sessionPath = String.format("%s/%s-sessions/%02d-%s.xml", WorkSpace.getInstance().getWorkspaceFolder(), path, getSessions().size()+1, participant);
        return new Session(this, sessionPath, participant);
    }

//    public void addMarker(Task task, Marker marker) {
//        if (!this.markers.containsKey(task)) {
//            this.markers.put(task, new LinkedList<Marker>());
//        }
//        this.markers.get(task).add(marker);
//    }

    public HashMap<String, Marker> getMarkers() {
        HashMap<String, Marker> map = new HashMap<String, Marker>();

        Document doc = this.getDocument();
        
        Node markerNode = doc.getElementsByTagName("markers").item(0);
        Marker marker = new Marker();
        
        loadMarkers(marker, markerNode, map);
        
        map.put("default", marker);

        return map;
    }
    
    public void loadMarkers(Marker marker, Node markerNode, HashMap<String, Marker> map) {
    	NodeList children = markerNode.getChildNodes();
    	for (int i = 0, j = 0; i < children.getLength(); i+=1) {
    		Node childNode = children.item(i);
    		if (childNode.getNodeType() != Node.ELEMENT_NODE) {
    			continue;
    		}
    		Element childElement = (Element) childNode;
    		String gesture = childElement.getAttribute("gesture");
    		if (gesture == null) {
    			if (j < Marker.defaultGestures.length) {
    				gesture = Marker.defaultGestures[j];
    			} else {
    				gesture = "";
    			}
    		}
    		j += 1;
    		     		
    		Marker childMarker = new Marker(childElement.getAttribute("name"), gesture);
    		if (childNode.hasChildNodes()) {
    			loadMarkers(childMarker, childNode, map);
    		}
    		marker.addChild(childMarker);
    		if (childElement.hasAttribute("id")) {
    			map.put(childElement.getAttribute("id"), childMarker);
    		}
    	}
    }

//    public boolean hasMarker(Task task) {
//        return !this.getMarkers(task).isEmpty();
//    }
}

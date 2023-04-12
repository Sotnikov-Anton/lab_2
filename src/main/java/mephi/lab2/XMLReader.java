package mephi.lab2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

public class XMLReader implements FileReader{
    private DataStorage ds;
    private Reactor reactor;
    
    public XMLReader() {
        this.ds = new DataStorage();
    }
    public DataStorage getDs() {
        return ds;
    }
    @Override
    public void readFile(String path) {
        ds.setSource("xml");
        XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
        try {
            XMLEventReader reader = xmlInputFactory.createXMLEventReader(new FileInputStream(path));
            while (reader.hasNext())
            {
                XMLEvent xmlEvent = reader.nextEvent();
                if (xmlEvent.isStartElement()) {
                    StartElement startElement = xmlEvent.asStartElement();
                    if (startElement.getName().getLocalPart().equals("Reactor")) {
                        reactor = new Reactor();
                    } else if (startElement.getName().getLocalPart().equals("type")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setType(xmlEvent.asCharacters().getData());
                    } else if (startElement.getName().getLocalPart().equals("burnup")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setBurnup(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("kpd")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setKpd(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("enrichment")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setEnrichment(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("thermal_capacity")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setThermal_capacity(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("electrical_capacity")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setElectrical_capacity(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("life_time")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setLife_time(Integer.parseInt(xmlEvent.asCharacters().getData()));
                    } else if (startElement.getName().getLocalPart().equals("first_load")) {
                        xmlEvent = reader.nextEvent();
                        reactor.setFirst_load(Double.parseDouble(xmlEvent.asCharacters().getData()));
                    }
                }
                if (xmlEvent.isEndElement()) {
                    EndElement endElement = xmlEvent.asEndElement();
                    if (endElement.getName().getLocalPart().equals("Reactor")) {
                        ds.getReactors().add(reactor);
                    }
                }
            }
        } catch (FileNotFoundException | XMLStreamException e) {
            e.printStackTrace();
        }
    }

    @Override
    public DefaultMutableTreeNode buildTree() {
        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Reactors");
        for (Reactor reactor : ds.getReactors()) {
            rootNode.add(reactor.getNode());
        }
        return rootNode;
    }
}

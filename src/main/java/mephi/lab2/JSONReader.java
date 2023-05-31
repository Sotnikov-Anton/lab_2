package mephi.lab2;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

public class JSONReader implements FileReader{
    private DataStorage ds;
    private Reactor reactor;
    private FileReader neighbour;
    
    public JSONReader() {
        this.ds = new DataStorage();
    }
    public DataStorage getDs() {
        return ds;
    }
    public FileReader getNeighbour() {
        return neighbour;
    }
    public void setNeighbour(FileReader neighbour) {
        this.neighbour = neighbour;
    }

    @Override
    public ArrayList<Reactor> readFile(String path) {
        ds.setSource("json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            ArrayList<Reactor> reactors = (ArrayList<Reactor>)mapper.readValue(new File(path), new TypeReference<List<Reactor>>() {});
            ds.setReactors(reactors);
        } catch (JsonParseException je) {
            return neighbour.readFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ds.getReactors();
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
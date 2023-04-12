package mephi.lab2;

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
    
    public JSONReader() {
        this.ds = new DataStorage();
    }
    public DataStorage getDs() {
        return ds;
    }

    @Override
    public void readFile(String path) {
        ds.setSource("json");
        ObjectMapper mapper = new ObjectMapper();
        try {
            ArrayList<Reactor> reactors = (ArrayList<Reactor>)mapper.readValue(new File(path), new TypeReference<List<Reactor>>() {});
            ds.setReactors(reactors);
        } catch (IOException e) {
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

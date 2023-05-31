package mephi.lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.error.YAMLException;

public class YAMLReader implements FileReader{
    private DataStorage ds;
    private Reactor reactor;
    private FileReader neighbour;
    
    public YAMLReader() {
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
        ds.setSource("yaml");
        Yaml yaml = new Yaml(new ListConstructor<>(Reactor.class));
        try {
            ArrayList<Reactor> reactors = (ArrayList<Reactor>)yaml.load(new FileInputStream(new File(path)));
            ds.setReactors(reactors);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (YAMLException ye) {
            return neighbour.readFile(path);
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
package mephi.lab2;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import org.yaml.snakeyaml.Yaml;

public class YAMLReader implements FileReader{
    private DataStorage ds;
    private Reactor reactor;
    
    public YAMLReader() {
        this.ds = new DataStorage();
    }
    public DataStorage getDs() {
        return ds;
    }

    @Override
    public void readFile(String path) {
        ds.setSource("yaml");
        Yaml yaml = new Yaml(new ListConstructor<>(Reactor.class));
        try {
            ArrayList<Reactor> reactors = (ArrayList<Reactor>)yaml.load(new FileInputStream(new File(path)));
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
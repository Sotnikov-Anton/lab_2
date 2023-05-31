package mephi.lab2;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

public class NULLReader implements FileReader{

    @Override
    public ArrayList<Reactor> readFile(String path) {
        System.out.println("Unknown file format!");
        return null;
    }
    @Override
    public DefaultMutableTreeNode buildTree() {
        return null;
    }
}
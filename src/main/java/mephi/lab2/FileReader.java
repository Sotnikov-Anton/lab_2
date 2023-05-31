package mephi.lab2;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

public interface FileReader {
    public ArrayList<Reactor> readFile(String path);
    public DefaultMutableTreeNode buildTree();
}
package mephi.lab2;

import javax.swing.tree.DefaultMutableTreeNode;

public interface FileReader {
    public void readFile(String path);
    public DefaultMutableTreeNode buildTree();
}

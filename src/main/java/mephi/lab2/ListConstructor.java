package mephi.lab2;

import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.nodes.Node;
import org.yaml.snakeyaml.nodes.SequenceNode;

public class ListConstructor<Reactor> extends Constructor{
    private final Class<Reactor> clazz;

  public ListConstructor(final Class<Reactor> clazz) {
    this.clazz = clazz;
  }

  @Override
  protected Object constructObject(final Node node) {
    if (node instanceof SequenceNode && isRootNode(node)) {
      ((SequenceNode) node).setListType(clazz);
    }
    return super.constructObject(node);
  }

  private boolean isRootNode(final Node node) {
    return node.getStartMark().getIndex() == 0;
  }
}
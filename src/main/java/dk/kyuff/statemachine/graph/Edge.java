package dk.kyuff.statemachine.graph;

/**
 * Created by sokwi on 30-03-2015.
 */
public class Edge<E> {

    private E value;
    private boolean defined;

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.defined = true;
        this.value = value;
    }

    public boolean isEmpty() {
        return value == null;
    }

    public boolean isDefined() {
        return defined;
    }
}

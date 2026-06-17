package TDAs.structure.implementation.node;

public class Edge<T> {
    private T origin;
    private T destination;
    private int weight;

    public Edge(T origin, T destination, int weight) {
        this.origin = origin;
        this.destination = destination;
        this.weight = weight;
    }

    public T getOrigin() {
        return origin;
    }

    public void setOrigin(T origin) {
        this.origin = origin;
    }

    public T getDestination() {
        return destination;
    }

    public void setDestination(T destination) {
        this.destination = destination;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}

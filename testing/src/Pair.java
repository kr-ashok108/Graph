

public class Pair<T, U> {
    final T neighbor;
    final U weight;

    Pair(T neighbor, U weight) {
        this.neighbor = neighbor;
        this.weight = weight;
    }

    public T getNeighbor() {
        return neighbor;
    }

    public U getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "(:" + neighbor + ", " + weight + ")";
    }
}
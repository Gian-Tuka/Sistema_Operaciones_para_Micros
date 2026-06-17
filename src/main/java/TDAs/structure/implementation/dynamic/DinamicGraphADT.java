package TDAs.structure.implementation.dynamic;


import TDAs.structure.definition.GraphADT;
import TDAs.structure.definition.SetADT;
import TDAs.structure.implementation.node.NodeGraph;

public class DinamicGraphADT<T> implements GraphADT<T>{

    private final SetADT<T> vertices;
    private NodeGraph<T> head;

    public DinamicGraphADT() {
        this.vertices = new DinamicSetADT<T>();
        this.head = null;
    }

    @Override
    public SetADT<T> getVertxs() {
        return vertices;
    }

    @Override
    public void addVertx(T vertex) {
        if (vertex == null) {
            throw new TDAs.exceptions.GenericADTException("El vertice no puede ser nulo");
        }
        if (vertices.exist(vertex)) {
            throw new TDAs.exceptions.GenericADTException("El vertice ya existe");
        }

        vertices.add(vertex);
        NodeGraph<T> newVertex = new NodeGraph<T>(vertex);

        if (head == null) {
            head = newVertex;
            return;
        }

        NodeGraph<T> pointer = head;
        while (pointer.getPointer() != null) {
            pointer = pointer.getPointer();
        }
        pointer.setPointer(newVertex);
    }

    @Override
    public void removeVertx(T vertex) {
        if (!vertices.exist(vertex)) {
            throw new TDAs.exceptions.ElementNotFoundADTException("El vertice no existe");
        }

        removeEdgeFromAll(vertex);
        vertices.remove(vertex);

        if (head == null) {
            return;
        }

        if (head.getValue() == vertex) {
            head = head.getPointer();
            return;
        }

        NodeGraph<T> pointer = head;
        while (pointer.getPointer() != null && pointer.getPointer().getValue() != vertex) {
            pointer = pointer.getPointer();
        }

        if (pointer.getPointer() != null) {
            pointer.setPointer(pointer.getPointer().getPointer());
        }
    }

    @Override
    public void addEdge(T vertxOne, T vertxTwo, int weight) {
        addVertx(vertxOne);
        addVertx(vertxTwo);

        addDirectedEdge(vertxOne, vertxTwo, weight);
        if (vertxOne != vertxTwo) {
            addDirectedEdge(vertxTwo, vertxOne, weight);
        }
    }

    @Override
    public void removeEdge(T vertxOne, T vertxTwo) {
        if (!vertices.exist(vertxOne) || !vertices.exist(vertxTwo)) {
            throw new TDAs.exceptions.ElementNotFoundADTException("Uno o ambos vertices no existen");
        }

        removeDirectedEdge(vertxOne, vertxTwo);
        if (vertxOne != vertxTwo) {
            removeDirectedEdge(vertxTwo, vertxOne);
        }
    }

    @Override
    public boolean existsEdge(T vertxOne, T vertxTwo) {
        if (!vertices.exist(vertxOne) || !vertices.exist(vertxTwo)) {
            throw new TDAs.exceptions.ElementNotFoundADTException("Uno o ambos vertices no existen");
        }
        NodeGraph<T> vertexNode = findVertexNode(vertxOne);

        NodeGraph<T> adjacency = vertexNode.getAdjacent();
        while (adjacency != null) {
            if (adjacency.getValue() == vertxTwo) {
                return true;
            }
            adjacency = adjacency.getPointer();
        }
        return false;
    }

    @Override
    public int edgeWeight(T vertxOne, T vertxTwo) {
        if (!vertices.exist(vertxOne) || !vertices.exist(vertxTwo)) {
            throw new TDAs.exceptions.ElementNotFoundADTException("Uno o ambos vertices no existen");
        }
        NodeGraph<T> vertexNode = findVertexNode(vertxOne);

        NodeGraph<T> adjacency = vertexNode.getAdjacent();
        while (adjacency != null) {
            if (adjacency.getValue() == vertxTwo) {
                return adjacency.getWeight();
            }
            adjacency = adjacency.getPointer();
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    private NodeGraph<T> findVertexNode(T vertex) {
        NodeGraph<T> pointer = head;
        while (pointer != null) {
            if (pointer.getValue() == vertex) {
                return pointer;
            }
            pointer = pointer.getPointer();
        }
        return null;
    }

    private void addDirectedEdge(T from, T to, int weight) {
        NodeGraph<T> fromNode = findVertexNode(from);
        if (fromNode == null) {
            return;
        }

        NodeGraph<T> adjacency = fromNode.getAdjacent();
        if (adjacency == null) {
            fromNode.setAdjacent(new NodeGraph<T>(to, weight));
            return;
        }

        NodeGraph<T> pointer = adjacency;
        NodeGraph<T> previous = null;
        while (pointer != null) {
            if (pointer.getValue() == to) {
                pointer.setWeight(weight);
                return;
            }
            previous = pointer;
            pointer = pointer.getPointer();
        }

        previous.setPointer(new NodeGraph<T>(to, weight));
    }

    private void removeDirectedEdge(T from, T to) {
        NodeGraph<T> fromNode = findVertexNode(from);
        if (fromNode == null) {
            return;
        }

        NodeGraph<T> adjacency = fromNode.getAdjacent();
        if (adjacency == null) {
            return;
        }

        if (adjacency.getValue() == to) {
            fromNode.setAdjacent(adjacency.getPointer());
            return;
        }

        NodeGraph<T> pointer = adjacency;
        while (pointer.getPointer() != null && pointer.getPointer().getValue() != to) {
            pointer = pointer.getPointer();
        }

        if (pointer.getPointer() != null) {
            pointer.setPointer(pointer.getPointer().getPointer());
        }
    }

    private void removeEdgeFromAll(T vertex) {
        NodeGraph<T> pointer = head;
        while (pointer != null) {
            removeDirectedEdge(pointer.getValue(), vertex);
            pointer = pointer.getPointer();
        }
    }

}

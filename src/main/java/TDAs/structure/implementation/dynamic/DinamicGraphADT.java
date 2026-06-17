package TDAs.structure.implementation.dynamic;


import TDAs.structure.definition.GraphADT;
import TDAs.structure.definition.LinkedListADT;
import TDAs.structure.definition.SetADT;
import TDAs.structure.implementation.node.Edge;
import TDAs.structure.implementation.node.NodeGraph;

public class DinamicGraphADT<T> implements GraphADT<T> {

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

        if (head.getValue().equals(vertex)) {
            head = head.getPointer();
            return;
        }

        NodeGraph<T> pointer = head;
        while (pointer.getPointer() != null && !pointer.getPointer().getValue().equals(vertex)) {
            pointer = pointer.getPointer();
        }

        if (pointer.getPointer() != null) {
            pointer.setPointer(pointer.getPointer().getPointer());
        }
    }

    @Override
    public void addEdge(T vertxOne, T vertxTwo, int weight) {
        if (!vertices.exist(vertxOne)) {
            addVertx(vertxOne);
        }
        if (!vertices.exist(vertxTwo)) {
            addVertx(vertxTwo);
        }

        NodeGraph<T> fromNode = findVertexNode(vertxOne);
        NodeGraph<T> toNode = findVertexNode(vertxTwo);

        // Check for duplicates
        LinkedListADT<Edge<T>> outgoing = fromNode.getOutgoingEdges();
        for (int i = 0; i < outgoing.size(); i++) {
            if (outgoing.get(i).getDestination().equals(vertxTwo)) {
                throw new TDAs.exceptions.GenericADTException("Ya existe una ruta desde el origen al destino indicado.");
            }
        }

        Edge<T> newEdge = new Edge<>(vertxOne, vertxTwo, weight);
        fromNode.getOutgoingEdges().add(newEdge);
        toNode.getIncomingEdges().add(newEdge);
    }

    @Override
    public void removeEdge(T vertxOne, T vertxTwo) {
        if (!vertices.exist(vertxOne) || !vertices.exist(vertxTwo)) {
            throw new TDAs.exceptions.ElementNotFoundADTException("Uno o ambos vertices no existen");
        }

        NodeGraph<T> fromNode = findVertexNode(vertxOne);
        NodeGraph<T> toNode = findVertexNode(vertxTwo);

        // Remove from outgoing
        LinkedListADT<Edge<T>> outgoing = fromNode.getOutgoingEdges();
        for (int i = 0; i < outgoing.size(); i++) {
            if (outgoing.get(i).getDestination().equals(vertxTwo)) {
                outgoing.remove(i);
                break;
            }
        }

        // Remove from incoming
        LinkedListADT<Edge<T>> incoming = toNode.getIncomingEdges();
        for (int i = 0; i < incoming.size(); i++) {
            if (incoming.get(i).getOrigin().equals(vertxOne)) {
                incoming.remove(i);
                break;
            }
        }
    }

    @Override
    public boolean existsEdge(T vertxOne, T vertxTwo) {
        if (!vertices.exist(vertxOne) || !vertices.exist(vertxTwo)) {
            throw new TDAs.exceptions.ElementNotFoundADTException("Uno o ambos vertices no existen");
        }

        NodeGraph<T> fromNode = findVertexNode(vertxOne);
        LinkedListADT<Edge<T>> outgoing = fromNode.getOutgoingEdges();
        for (int i = 0; i < outgoing.size(); i++) {
            if (outgoing.get(i).getDestination().equals(vertxTwo)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int edgeWeight(T vertxOne, T vertxTwo) {
        if (!vertices.exist(vertxOne) || !vertices.exist(vertxTwo)) {
            throw new TDAs.exceptions.ElementNotFoundADTException("Uno o ambos vertices no existen");
        }

        NodeGraph<T> fromNode = findVertexNode(vertxOne);
        LinkedListADT<Edge<T>> outgoing = fromNode.getOutgoingEdges();
        for (int i = 0; i < outgoing.size(); i++) {
            if (outgoing.get(i).getDestination().equals(vertxTwo)) {
                return outgoing.get(i).getWeight();
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return vertices.isEmpty();
    }

    public NodeGraph<T> findVertexNode(T vertex) {
        NodeGraph<T> pointer = head;
        while (pointer != null) {
            if (pointer.getValue().equals(vertex)) {
                return pointer;
            }
            pointer = pointer.getPointer();
        }
        return null;
    }

    private void removeEdgeFromAll(T vertex) {
        NodeGraph<T> targetNode = findVertexNode(vertex);
        if (targetNode == null) return;

        // Remove all incoming edges to this vertex from their respective origins
        LinkedListADT<Edge<T>> incoming = targetNode.getIncomingEdges();
        for (int i = 0; i < incoming.size(); i++) {
            Edge<T> edge = incoming.get(i);
            NodeGraph<T> originNode = findVertexNode(edge.getOrigin());
            if (originNode != null) {
                LinkedListADT<Edge<T>> out = originNode.getOutgoingEdges();
                for (int j = 0; j < out.size(); j++) {
                    if (out.get(j).getDestination().equals(vertex)) {
                        out.remove(j);
                        break;
                    }
                }
            }
        }

        // Remove all outgoing edges from this vertex from their respective destinations
        LinkedListADT<Edge<T>> outgoing = targetNode.getOutgoingEdges();
        for (int i = 0; i < outgoing.size(); i++) {
            Edge<T> edge = outgoing.get(i);
            NodeGraph<T> destNode = findVertexNode(edge.getDestination());
            if (destNode != null) {
                LinkedListADT<Edge<T>> in = destNode.getIncomingEdges();
                for (int j = 0; j < in.size(); j++) {
                    if (in.get(j).getOrigin().equals(vertex)) {
                        in.remove(j);
                        break;
                    }
                }
            }
        }
    }
}

package storyfive.graph;

public enum VertexType {
    FILE{ @Override public String toString() {return "file";}},
    METHOD { @Override public String toString() {return "method";}},
    PACKAGE{ @Override public String toString() {return "package";}}
}

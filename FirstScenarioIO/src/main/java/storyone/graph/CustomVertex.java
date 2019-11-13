package storyone.graph;

public class CustomVertex {
        private String name;
        private String weight;

        public CustomVertex(String name, String weight) {
            this.weight = weight;
            this.name = name;
        }

        //https://jgrapht.org/guide/VertexAndEdgeTypes#all-keys
        @Override
        public String toString() {
            return name + "\n" + weight;
        }

        @Override
        public int hashCode() {
            return toString().hashCode();
        }

        @Override
        public boolean equals(Object o) {
            return (o instanceof CustomVertex) && (toString().equals(o.toString()));
        }
}

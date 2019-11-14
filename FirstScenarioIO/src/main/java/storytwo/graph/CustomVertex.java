package storytwo.graph;

public class CustomVertex { // Class of a vertex which stores its name and weight(callingCounter from NodeFunction class)
        private String name;
        private String weight;

        public CustomVertex(String name, String weight) {
            this.weight = weight;
            this.name = name;
        }

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

package storyfive.graph;

public class CustomVertex {
    private String name;
    private String weight;
    private VertexType type;
    private String packageName;                                                                                         //uzupelniane dla metod i pakietów

    public CustomVertex(String name, String weight, VertexType type) {
        this.weight = weight;
        this.name = name;
        this.type=type;
        this.packageName=null;
    }

    public CustomVertex(String nameAndWeight, VertexType type) {                                                        //konstruktor dla wierzchołków z hist1
        String[] split= nameAndWeight.split("\\n");
        this.name = split[0];
        if(split.length==1)
            this.weight = "";
        else
            this.weight = split[1];
        this.type=type;
    }

    public void setPackageName(String packName) { this.packageName=packName; }

    public String getPackageName() { return packageName; }

    public void setType(VertexType type) { this.type=type; }

    public VertexType getType() {
    return type;
}

    public String getName() { return name; }

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

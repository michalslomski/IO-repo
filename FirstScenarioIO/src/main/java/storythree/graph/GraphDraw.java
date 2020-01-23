package storythree.graph;

import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.util.mxCellRenderer;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import storythree.DependencyObject;
import storythree.Readable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GraphDraw {
    public void drawGraph(List<DependencyObject> dependencyObjectList) throws IOException {
        List<DependencyObject> packages=new ArrayList<>();
        List<DependencyObject> methods=new ArrayList<>();
        Packages p = new Packages();
        packages= p.getPackages(dependencyObjectList);
        Methods m = new Methods();
        methods= m.getMethods(dependencyObjectList);
        GraphCreate g = new GraphCreate();

        DirectedWeightedMultigraph<DependencyObject, CustomEdge> graph = g.createGraph(methods, packages);

        String projectPath=System.getProperty("user.dir");
        File imgFile = new File(projectPath+"\\src\\main\\resources\\graph3.png");

        JGraphXAdapter<DependencyObject, CustomEdge> graphAdapter = new JGraphXAdapter<>(graph);

        mxParallelEdgeLayout layout = new mxParallelEdgeLayout(graphAdapter);
        layout.setUseBoundingBox(false);

        int i=0;
        int j[]= new int[packages.size()];
        int k=0;
        int l=0;
        HashMap<String, Integer> groups=new HashMap<>();
        for (Map.Entry vortex:graphAdapter.getVertexToCellMap().entrySet()) {
            Readable pack = (Readable) vortex.getKey();
            try {
                k=groups.get(pack.getPackageName());
            }
            catch(NullPointerException e){
                k=groups.size();
                j[k]=0;
                groups.put(pack.getPackageName(), k);
            }
            if(pack.getMethodName()=="") {
                layout.setVertexLocation(vortex.getValue(), 2 + (k*250), 150);
                i++;
            }else{
                layout.setVertexLocation(vortex.getValue(),2+((k)*250)+j[k]*20,200+((j[k]%4)*60));
                j[k]++;
                l++;
            }
            for (Map.Entry edge : graphAdapter.getEdgeToCellMap().entrySet()){
                layout.setOrthogonalEdge(edge.getValue(), true);
            }
            layout.execute(graphAdapter.getDefaultParent());

        }


        BufferedImage img = mxCellRenderer.createBufferedImage(graphAdapter, null, 2, Color.WHITE, true, null);
        ImageIO.write(img, "PNG", imgFile);




    }
}

package story6;
/*
Author: BeGieU
Date: 04.12.2019
*/

import story6.graph.GraphContainer;
import story6.graph.GraphCreator;

import java.io.IOException;

//TODO graph has
public class StorySix {
    private static final String TEST_PATH = "D:\\Studia\\sem_5\\IO\\IO-repo-actual\\FirstScenarioIO\\src\\main\\java\\";
    private static final String SAVE_TO = "D:\\Studia\\sem_5\\IO\\IO-repo-actual\\FirstScenarioIO\\src\\main\\resources\\storysix-graph.png";

    public static void run6Story() throws IOException {
        GraphCreator graphCreator =new GraphCreator();
        GraphContainer graph= graphCreator.makeGraph(TEST_PATH);
        graph.createPNG(SAVE_TO);
    }

    public static void main(String[] args) throws IOException {
        run6Story();
    }
}

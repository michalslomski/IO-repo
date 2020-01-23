package story6;
/**
Author: Marcin Pływacz
Date: 04.12.2019
*/

import story6.graph.GraphContainer;
import story6.graph.GraphCreator;

import java.io.IOException;

public class StorySix {
    private static final String PROJECT_PATH_PREFIX = System.getProperty("user.dir");

    private static final String TEST_PATH = PROJECT_PATH_PREFIX + "\\src\\main\\java\\";
    private static final String SAVE_TO = PROJECT_PATH_PREFIX + "\\src\\main\\resources\\graph6.png";

    public static void run6Story() throws IOException {
        GraphCreator graphCreator = new GraphCreator();
        GraphContainer graph = graphCreator.makeGraph(TEST_PATH);
        graph.createPNG(SAVE_TO);
    }

    public static void main(String[] args) throws IOException {
        run6Story();
    }
}

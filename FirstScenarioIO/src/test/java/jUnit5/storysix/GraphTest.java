package jUnit5.storysix;

import org.junit.jupiter.api.Test;
import story6.graph.GraphContainer;
import story6.graph.GraphCreator;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GraphTest {

    @Test
    public void shouldCreateGraph(){
        String PROJECT_PATH_PREFIX = System.getProperty("user.dir");
        String TEST_PATH = PROJECT_PATH_PREFIX + "\\src\\main\\java\\";
        String SAVE_TO = PROJECT_PATH_PREFIX + "\\src\\main\\resources\\storysix-graph_test.png";

        try {
            GraphCreator graphCreator = new GraphCreator();
            GraphContainer graph = graphCreator.makeGraph(TEST_PATH);

            graph.createPNG(SAVE_TO);
        }catch(IOException exception){
            exception.printStackTrace();
        }
    }

    @Test
    public void shouldThrowIOException(){
        Exception exception = assertThrows(IOException.class, () -> {
            String PROJECT_PATH_PREFIX = System.getProperty("user.dir");
            String TEST_PATH = PROJECT_PATH_PREFIX + "\\src\\main\\java\\";
            String SAVE_TO = "";

            GraphCreator graphCreator = new GraphCreator();
            GraphContainer graph = graphCreator.makeGraph(TEST_PATH);

            graph.createPNG(SAVE_TO);

        });
    }
}

package jUnit5.storytwo;

import org.junit.jupiter.api.Test;
import storytwo.run.StoryTwo;

import javax.imageio.IIOException;
import java.io.IOException;

public class GraphTwoTest {

    @Test
    public void shouldNotThrowAnyException(){

        try{
            StoryTwo.runSecondStory();

        }catch (IOException exception){
            exception.printStackTrace();
        }
    }
}

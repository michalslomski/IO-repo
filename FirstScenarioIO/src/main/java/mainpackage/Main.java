package mainpackage;

import storyfive.run.StoryFive;
import storyone.run.StoryOne;
import storythree.run.StoryThree;
import storytwo.run.StoryTwo;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        //stworzone grafy znajdują sie w /src/main/resources
        StoryOne.runFirstStory();
        StoryTwo.runSecondStory();
        StoryThree.runThirdStory();
        StoryFive.runFifthStory();
    }
}

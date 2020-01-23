package storyfive.fileoperations;

import storyone.fileoperations.DependenciesCounter;
import storyone.fileoperations.FileParser;
import storyone.fileoperations.FileReader;
import storyone.graph.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FileDependencyFinder {                                                                                     //fragment kodu z storyone/run/StoryOne

    public List<Node> fileDependencies() throws IOException {                                                           //zwraca listę obiektów z hist1
        DependenciesCounter dependenciesCounter = new DependenciesCounter();
        FileReader fileReader = new FileReader();
        FileParser fileParser = new FileParser();
        List<File> fileList;
        List<Node> nodeList= new ArrayList<>();

        String projectPath = System.getProperty("user.dir");            //Project path
        String path = projectPath+"\\src\\main\\java\\storyone";                      //lokalizacja plikow projektu
        fileList=fileReader.findAllFilesInDepth(path);

        //tworzenie listy Nodów, rozmiar jest równy liczbie plików w projekcie
        for (File f : fileList) {
            HashMap<String,Integer> map1 = new HashMap<>();     //mapa na pliki z projektu
            HashMap<String,Integer> map2 = new HashMap<>();     //mapa na pliki zaimportowane
            Node n= new Node();

            n.file=f;       //ustalenie jaki plik odpowiada danemu Nodowi

            map1=dependenciesCounter.findAllDependencies(fileReader.getAllClassesNamesInsideProject(path), f);      //java files dependencies znalezione w pliku f(File)

            List<String> dependenciesList = fileParser.parseFile(f);                    //lista otrzymana z storyone.fileoperations.FileParser, zawiera nazwy znalezionych zależności w pliku f

            map2=dependenciesCounter.findAllDependencies(dependenciesList, f);                                      //file dependencies znalezione w pliku f(File)

            map1.putAll(map2);                      //merge map1 i map2

            n.dependencies=map1;                    //przypisanie wszystkich zależności

            n.fileSize=f.length();                  //ustawienie wielkosci pliku

            nodeList.add(n);
        }
        return nodeList;
    }

}

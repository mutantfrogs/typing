package com.company;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WordList {

    List<String> hardWords = Files.readAllLines(Paths.get("dictionary.txt"), Charset.defaultCharset()); // adds txt file of all words into a list
    List<String> easyWords = new ArrayList<>();;
    List<String> mediumWords = new ArrayList<>();;

    public WordList() throws IOException {
        for(String hardWord : hardWords) {
            if (hardWord.length() < 6) {
                easyWords.add(hardWord);
            }
            if (hardWord.length() < 8) {
                mediumWords.add(hardWord);
            }
        }
        //System.out.println("Hard Words: " + hardWords.size() + "\nMedium Words: " + mediumWords.size() + "\nEasy Words: " + easyWords.size());
    }


}

package com.parser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Parser {

    public static void main(String[] args) throws IOException {
        try {
            GoogleSearch googleSearch = new GoogleSearch("AIzaSyBA_szHtsptnZb3bGWU1xSgIQ0Pc5w35ek", "e6f5749830a8d48ce");
            List<String> urlToParse = googleSearch.search("inurl:.php?l=product_detail");
            List<String> parsedUrls = new ArrayList<>();
            List<String> ignoredUrls = loadIgnoredUrlsFromFile("src/main/resources/ignor.txt");

            for (String url : urlToParse) {
                if (!parsedUrls.contains(url) && !ignoredUrls.contains(url)) {
                    parsedUrls.add(url);
                    // Вы можете добавить проверку, чтобы убедиться, что URL соответствует вашим требованиям (дорка)
                    System.out.println(url);
                    Thread.sleep(1000);
                }
            }
            saveToFileResult(parsedUrls);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    private static void saveToFileResult(List<String> urlList) {
        File file = new File("src/main/resources/urls.txt");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            for (String url : urlList) {
                bufferedWriter.write(url);
                bufferedWriter.newLine();
            }
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<String> loadIgnoredUrlsFromFile(String fileName) {
        List<String> urlList = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(fileName))) {
            while (scanner.hasNextLine()) {
                String url = scanner.nextLine();
                urlList.add(url);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return urlList;
    }


//    private static ArrayList<String> notUrlReplay(ArrayList<List> parsedUrls){
//        ArrayList<String> notReplayURL = new ArrayList<>();
//        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("src/main/resources/urls.txt"));){
//            for(String url: notReplayURL){
//                bufferedWriter.newLine();
//            }
//        }catch (IOException e){
//            e.printStackTrace();
//        }
//    }
}

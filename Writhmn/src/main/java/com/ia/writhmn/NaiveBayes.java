/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ia.writhmn;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

/**
 *
 * @author abraham
 */
public class NaiveBayes {
    
    
    
    public static void Train(String dataPath) throws IOException{
        System.out.println("Entranando modelo...");
        if(!DataBase.LoadData(FileManager.ReadFile(dataPath))){
            System.out.println("-Error entrenando modelo.");
            return;
        }
        computeClassWordCount();
        computeTagProbabilities();
        computeWordsProbabilities();
        System.out.println("Completado!");
    }
    
    
    public static void Classify(String document){
        String[] docWords = Arrays.stream(document.split(Pattern.quote(" ")))
                    .map(String::trim)
                    .map(String::toLowerCase)
                    .map(next -> next.replaceAll("\\.", ""))
                    .map(next -> next.replaceAll(",", ""))
                    .map(next -> next.replaceAll("'", ""))
                    .map(next -> next.replaceAll("\"", ""))
                    .map(next -> next.replaceAll("á", "a"))
                    .map(next -> next.replaceAll("é", "e"))
                    .map(next -> next.replaceAll("í", "i"))
                    .map(next -> next.replaceAll("ú", "u"))
                    .map(next -> next.replaceAll("ó", "o"))
                    .map(next -> next.replaceAll("-n", ""))
                    .map(next -> next.replaceAll("\\)", " "))
                    .map(next -> next.replaceAll("\\(", " "))
                    .map(next -> next.replaceAll("!", " "))
                    .map(next -> next.replaceAll("¡", " "))   
                    .filter(next -> !next.isEmpty())
                    .filter(next -> next != null)
                    .toArray(String[]::new);
        
        String finalTag = "";
        Double probability = 0.0;
        HashMap<String, Double> results = new HashMap<>();
        
        for (String tagkey: DataBase.tagProbabilities.keySet()){
            Double pTag = DataBase.tagProbabilities.get(tagkey);
            for (String word : docWords){
                pTag *= DataBase.uniqueWords.containsKey(word) ? DataBase.wordsProbabilities.get(tagkey).get(word) : 0.001;
            }
            
            if (pTag > probability){
                finalTag = tagkey;
                probability = pTag;
            }
            
            results.put(tagkey, pTag);
        }
        
        Double count = results.values().stream().reduce(0.0, Double::sum);
        
        System.out.println("Resultados:");
        
        if (document.contains("-n")){            
            results.keySet().forEach((tagResult) -> {
                System.out.println("\t(" + tagResult + " = " + results.get(tagResult) + ")");
            });
        }        
        else{
            results.keySet().forEach((tagResult) -> {
                System.out.println("\t(" + tagResult + " = " + results.get(tagResult) / count  + ")");
            });
            probability /= count;
        }
        
        System.out.println("Respuesta: \n\t " + finalTag + " = " + probability);
    }
    
    private static Integer getWordFrequenciesForClass(String classTag, String word){
        return DataBase.tagClasses.get(classTag).containsKey(word) ? DataBase.tagClasses.get(classTag).get(word): 0;
    }
    
    private static void computeClassWordCount(){
        Integer count = 0;
        for (String tagkey : DataBase.tagClasses.keySet()){
            HashMap<String, Integer> words = DataBase.tagClasses.get(tagkey);
            DataBase.tagClassWordCount.put(tagkey, words.keySet().stream().map((word) -> words.get(word)).reduce(count, Integer::sum) + DataBase.uniqueWords.keySet().size());
        }
    }
    
    //P(word|tag)
    private static void computeWordsProbabilities()
    {
        Integer denominator=0;
        for(String tagkey:DataBase.tagClasses.keySet())
        {
            denominator = DataBase.tagClassWordCount.get(tagkey);
            
            for(String word: DataBase.uniqueWords.keySet())
            {
                HashMap<String,Double> map;
                if (DataBase.wordsProbabilities.containsKey(tagkey)){
                    map = DataBase.wordsProbabilities.get(tagkey);
                    map.put(word, (1+ getWordFrequenciesForClass(tagkey,word))/ denominator.doubleValue() );
                }
                else{
                   map =new HashMap<>();
                   map.put(word, (1+ getWordFrequenciesForClass(tagkey,word))/ denominator.doubleValue() );
                }
                DataBase.wordsProbabilities.put(tagkey, map);

            }
        }
    }
    
    
    
    
   //p(tag)
    private static void computeTagProbabilities(){
        
        Double grandTotal=0.0;
        for(String tagkey :DataBase.tagClasses.keySet())
        {
             grandTotal+=DataBase.tagClassWordCount.get(tagkey);
        }
        
        for (String tagkey : DataBase.tagClasses.keySet())
        {
            DataBase.tagProbabilities.put(tagkey, DataBase.tagClassWordCount.get(tagkey) / grandTotal);
        }
    }
}

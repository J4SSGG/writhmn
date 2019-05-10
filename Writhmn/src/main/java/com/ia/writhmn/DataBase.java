/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ia.writhmn;

import java.util.HashMap;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author abraham
 */
public class DataBase {
    private static HashMap<String, Character> uniqueWords = new HashMap<>();
    private static HashMap<String, HashMap<String, Integer>> tagClasses = new HashMap<>();

    
    
    public boolean LoadData(List<String> documents){
        documents.forEach((doc) -> {
            
            // Split data to get Words and Tags from documents...
            String[] docSplited = Arrays.stream(doc.split(Pattern.quote("|")))
                    .map(String::trim)
                    .filter(next -> !next.isEmpty())
                    .toArray(String[]::new);
            
            String[] docWords = Arrays.stream(docSplited[0].split(Pattern.quote(" ")))
                    .map(String::trim)
                    .filter(next -> !next.isEmpty())
                    .toArray(String[]::new);
            
            String[] docTags = Arrays.stream(docSplited[1].split(Pattern.quote(" ")))
                    .map(String::trim)
                    .filter(next -> !next.isEmpty())
                    .toArray(String[]::new);
            /*
            for (String docWord : docWords) {
                if (!words.containsKey(docWord))
                    words.put(docWord, words.size() + 1);
                
                for (String docTag : docTags ) {
                    if (!tags.containsKey(docTag))
                       tags.put(docTag, tags.size() + 1);
                    
                    String MultiKey = docWord + ":" + docTag;
                    if (!coincidences.containsKey(MultiKey)){
                        coincidences.put(MultiKey, 1);
                    }else{
                        
                    }
                    
                }
            }
            */
            for (String docTag : docTags){ // for each tag read from doc...
                if (tagClasses.containsKey(docTag)){ // if tag exists, then update its words..
                    HashMap<String, Integer> tagClass = tagClasses.get(docTag); // get tag's HashMap of words...
                    
                    for (String docWord : docWords){ // update occurrences for each word 
                        insertUniqueWord(docWord); // update uniqueWords HashMap
                        if (tagClass.containsKey(docWord)){
                            updateHash(docTag, docWord, tagClass.get(docWord) + 1); // increments frequency
                        }   
                    }
                }
                else{
                    // tag does not exist
                    tagClasses.put(docTag, new HashMap<String, Integer>()); // create new tag entry
                    
                    for (String docWord : docWords){ // associate words for the tag... 
                        insertUniqueWord(docWord);
                        updateHash(docTag, docWord, 1); // default value of one, because it is the first ocurrence...
                    }
                }
            }
            
        });
        
        return true;
    }
    
    
    private void updateHash(String docTag, String docWord, Integer value){
        HashMap<String, Integer> tagClass = tagClasses.get(docTag);
        tagClass.put(docWord, value);
        tagClasses.put(docTag, tagClass);
    }
    
    private void insertUniqueWord(String docWord){
        if (uniqueWords.containsKey(docWord)) return;
        
        uniqueWords.put(docWord, '.');
    }
}

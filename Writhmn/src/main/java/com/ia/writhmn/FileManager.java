
package com.ia.writhmn;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;




/**
 *
 * @author J4SSGG
 */
public class FileManager {
    
    public static void PrintFile(String path) throws FileNotFoundException, IOException{
        File file = new File(path); 
        
        // file exists? 
        if(!file.exists()){
            System.out.println("-File not found.");
            return;
        }
        
        // file is empty? 
        if(file.length() == 0){
            System.out.println("-File is empty.");
            return;
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
        

        String line; 
        while ((line = reader.readLine()) != null)
          System.out.println(line);
        
        reader.close();
        
        
         
    }
    
    public static List<String> ReadFile(String path) throws FileNotFoundException, IOException{
        File file = new File(path); 
        
        // file exists? 
        if(!file.exists()){
            System.out.println("-File not found.");
            return null;
        }
        
        // file is empty? 
        if(file.length() == 0){
            System.out.println("-File is empty.");
            return null;
        }
        
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF8"));
        
        List<String> buffer = new LinkedList<String>();
        String line; 
        while ((line = reader.readLine()) != null)
          buffer.add(line);
        
        reader.close();
        return buffer;
        
    }
    
    
    
    
}
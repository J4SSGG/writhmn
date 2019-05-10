package com.ia.writhmn;

import java.io.IOException;
import java.util.Scanner;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        
       
        Scanner scanner = new Scanner(System. in); 
        
        
         //NaiveBayes.Train("naiveTestSimple");
         while(true){
             NaiveBayes.Classify(scanner.nextLine());
         }
    }
}

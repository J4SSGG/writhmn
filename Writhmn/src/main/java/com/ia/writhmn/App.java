package com.ia.writhmn;

import java.io.IOException;



/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        
        //FileManager.PrintFile("naiveTest");
        DataBase db = new DataBase();
        
        db.LoadData(FileManager.ReadFile("naiveTest"));
        System.out.println("done");
    }
}

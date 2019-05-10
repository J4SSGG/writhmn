package com.ia.writhmn;

import java.io.IOException;
import java.util.Scanner;



/**
 * Hello world!
 *
 */
public class App 
{
     
    
    private static String readConsole(Scanner in ) {
        return in.nextLine().trim();
    }
    
    private static void printMenu (){
        System.out.print("\n\n1. Entrenar\n"
                + "2. Clasificar\n"
                + "0. Salir\n"
                + "Command: ");
        
    }
    private static void useCommand(String command, Scanner in) throws IOException{
        switch (command){
            case "1":
                System.out.print("Ingrese ruta del archivo: ");
                NaiveBayes.Train(readConsole(in));
                break;
            case "2":
                System.out.print("Clasificar frase: ");
                NaiveBayes.Classify(readConsole(in));
                break;
                
            case "0":
                System.out.print("Saliendo...");
                System.exit(0);
                break;
            default:
                System.out.print("-Comando desconcido");
                break;
        }
    }
    
    public static void main( String[] args ) throws IOException
    {
        
       
        Scanner reader = new Scanner(System.in);
        String command = "";
        
         
        while(!command.equalsIgnoreCase("0")){
            printMenu();
            command = readConsole(reader);
            useCommand(command, reader);
        }
    }
}

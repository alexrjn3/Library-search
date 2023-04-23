package carti;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {

	public static void main(String[] args) {
		  int N;
		  Scanner sc=new Scanner(System.in);
		  Socket cs=null;
		  BufferedReader bfr=null;
		  PrintWriter pw=null;
		  try{
		   cs=new Socket("localhost",5000) ;
		   //pt a scrie texte catre server si a primi texte
		   InputStreamReader isr=
		         new InputStreamReader(cs.getInputStream());
		   bfr=new BufferedReader(isr);
		   pw=new PrintWriter(cs.getOutputStream());
		   System.out.println("S-a setat reteaua");
		   
		   
		   for(;;){

				Scanner sc2=new Scanner(System.in);
				//citim autor
				System.out.print("Autor: ");
				String nume=sc2.nextLine();
				//daca a fost scris STOP trimitem string gol si flush pt a fi scris imediat
				if(nume.equals("STOP")) {pw.println(""); pw.flush();break;}
				else {
				
				String nume_titlu;  //pt concatenare nume+titlu
				//daca nu e scris "STOP" citim si titlul
				System.out.print("Titlu: ");
				String titlu=sc2.nextLine();
				nume_titlu=nume+"space"+titlu;
				//trimitem nume_titlu catre server
				pw.println(nume_titlu); pw.flush();}
		      //Citim raspuns server:
		       String textIn=bfr.readLine();
		       if(textIn==null)break; //daca a fost tastat STOP...
		       System.out.println(textIn); //daca a fost gasita cartea
		   }//for;;
		   }catch( IOException e){
		       e.printStackTrace();
		   }
		   System.out.println("Client s-a deconectat !");

	}

}

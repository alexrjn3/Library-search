package carti;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

import javax.xml.stream.*;
import javax.xml.stream.events.*;



public class Meniu {
	static String v[]=new String[3000]; //vector introducere date din fisier pt text
	static String v2[]=new String[3000]; //vector introducere date din fisier pt textsortat
	//v2 se va folosi pt cautareFisierTextSortat.v pentru CautareFisierText. Daca fol. doar vectorul v il vom modifica in metoda cautareFisierTextSortat(). 
	//Daca apelam din nou metoda cautareFisierText nu va mai fi o cautare liniara a textului nostru ci o
	//cautare liniara a textului nostru dupa ce a fost sortat prin metoda cautareFisierTextSortat().
	static int contor=0; //contor pt a afla dim vectorului.
	public static void main(String[] args) throws XMLStreamException {
		int optiune = 0;
		while (true) {
			afisareMeniuPrincipal();
			Scanner scanner = new Scanner(System.in);
			System.out.print("Selectare optiune: ");
			optiune = scanner.nextInt();

			if (optiune == 1) {
				cautareFisierText();
			} else if (optiune == 2) {
				cautareFisierTextSortat();
			} else if (optiune == 3) {
				cautareFisierXML();
			} else if (optiune == 4) {
				ModificaText();
			}  else if (optiune == 5) {
				break;
			}

		}
	}

	public static void afisareMeniuPrincipal() {
		try {
			//adaugam datele din fisier in vectorul String v[]=new String[3000];
			FileReader fr=new FileReader("src\\carti\\carti.txt");
			BufferedReader bfr=new BufferedReader(fr);	
			for(;;) {
				v[contor]=bfr.readLine(); //autor
				if(v[contor]==null)break;
					contor++;
				v[contor]=bfr.readLine(); //titlu
					contor++;
				v[contor]=bfr.readLine(); //nrbuc
					contor++;
				}
				bfr.close(); fr.close();
			}catch(IOException e) {
				System.out.println("eroare fisier");
				System.exit(1);		
		}
		
		//trimitem datele din vectorul v si in vectorul v2
		for (int i = 0; i < v.length; i++) {     
            v2[i] = v[i];     
        }      
		System.out.println("1 - Cautare fisier text");
		System.out.println("2 - Cautare fisier text sortat");
		System.out.println("3 - Cautare fisier XML");
		System.out.println("4 - Actualizeaza/Sterge nr. exemplare carte");
		System.out.println("5 - Iesire");
	}

	public static void cautareFisierText() {
		Scanner sc=new Scanner(System.in);
		System.out.print("Nume autor: ");
		String nume=sc.nextLine();
		System.out.print("Nume titlu: ");
		String titlu=sc.nextLine();
		int nrBucati=-1;
		for(int i=0;i<contor;i+=3)
		{
			if(v[i].compareTo(nume)==0) //comparam nume cu ce avem in vector pe pozitia nume autor
				if(v[i+1].compareTo(titlu)==0)//comparam titlu cu ce avem in vector pe pozitia titlu
					nrBucati=Integer.parseInt(v[i+2]); //daca se trece de if-urile anterioare trimitem nr de exemplare in var. nrBucati

		}
		if(nrBucati==-1)System.out.println("Nu exista cartea !");
		else System.out.println("nr. exemplare din carte = "+nrBucati);
	}

	public static void cautareFisierTextSortat() {
			//sortam vectorul in ordine alfabetica.
			for(int i=0;i<contor-1;i+=3)
				for(int j=i+3;j<contor;j+=3)
				{
					//comparam autor.daca este > interschimbam autor, titlu si nr. buc de la i cu cele de la j
					if(v2[i].compareTo(v2[j])>0) {
						String aux=v2[i];
						v2[i]=v2[j];
						v2[j]=aux;
						
						String aux2=v[i+1];
						v2[i+1]=v2[j+1];
						v2[j+1]=aux2;
						
						String aux3=v[i+2];
						v2[i+2]=v2[j+2];
						v2[j+2]=aux3;	
					}
					//daca nu, comparam si titlu si interschimbam autor, titlu si nr. buc daca este >
					else if(v2[i].compareTo(v2[j])==0) {
						if(v2[i+1].compareTo(v2[j+1])>0) {
							String aux=v2[i];
							v2[i]=v2[j];
							v2[j]=aux;
							
							String aux2=v2[i+1];
							v2[i+1]=v2[j+1];
							v2[j+1]=aux2;
							
							String aux3=v2[i+2];
							v2[i+2]=v2[j+2];
							v2[j+2]=aux3;	
						}
						
					}
				}
		
			
			/* Daca vrem sa afisam vectorul v2 dupa sortare
			for(int k=0;k<contor;k++)
				System.out.println(v[k]+" ");
			*/
			
			Scanner sc=new Scanner(System.in);
			System.out.print("Nume autor: ");
			String nume=sc.nextLine();
			System.out.print("Nume titlu: ");
			String titlu=sc.nextLine();
			int nrBucati=-1;
			//algo cautare binara:
		    int S = 0;
			int D = contor/3-1; //de ex sunt 4 carti in total dar sunt introduse de la 0 la 3 in vector de aceea -1 la final. contor/3 = total elemente din vector/3(autor,titlu,nr,buc)
			for(;;) {
				int M = (S+D)/2;
				if(D<S)break;
				//comparam autor cu ce avem la Mijloc:
				else if(nume.compareTo(v2[M*3])==0) //gasit autor
					// comparam titlu cu ce avem la Mijloc:
					if(titlu.compareTo(v2[M*3+1])==0){ //gasit titlu
						nrBucati=Integer.parseInt(v2[M*3+2]); break;} //trimitem nr exemplare in nrBucati
					else if(titlu.compareTo(v2[M*3+1])<0)D=M-1; //nu s-a gasit titlu(este mai < decat M titlu) cautam in partea stanga
					else S=M+1; //nu s-a gasit titlu(este mai > decat M titlu) cautam in partea dreapta
				else if(nume.compareTo(v2[M*3])<0)D=M-1; //nu s-a gasit autor(este mai < decat M autor) cautam in partea stanga
				else S=M+1; //nu s-a gasit autor(este mai < decat M autor) cautam in partea stanga
			}
			if(nrBucati==-1)System.out.println("Nu exista cartea !");
			else System.out.println("nr. exemplare din carte = "+nrBucati);
			
	}

	public static void cautareFisierXML()throws XMLStreamException  { 
		ArrayList<Carti> carti = new ArrayList<Carti>();
		Carti c=null;
		
		Scanner sc=new Scanner(System.in);
		System.out.print("Nume autor: ");
		String nume=sc.nextLine();
		System.out.print("Nume titlu: ");
		String titlu=sc.nextLine();
		int nrBucati=-1;
		
		int i=0; //contor pt case characters
		try {
			//pt citire fisier xml
			XMLInputFactory xmlInputFactory = XMLInputFactory.newFactory();
			XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new FileInputStream("src\\carti\\carti.xml"));
			while(xmlStreamReader.hasNext()) //cat timp exista linie in xml:
			{
				int eventType = xmlStreamReader.getEventType(); //var. pt a vedea ce tip de event este pe linia curenta(start_document,start_element,end_element_characters,etc...)
				switch(eventType) { //cream switch cu toate evenimentele posibile(aproape).
					case XMLEvent.START_DOCUMENT:
						//System.out.println("Start Document");
						break;
					case XMLEvent.START_ELEMENT:
							//System.out.println("Start Element: " + xmlStreamReader.getName());
							String s = xmlStreamReader.getLocalName();
							if (s.equals("carte"))c=new Carti(); //daca s-a gasit o <carte> 
						break;
					case XMLEvent.CHARACTERS:
						String content=xmlStreamReader.getText().trim(); //adaugam in variabila content caracterele intre start elem si end elem.
						if(!content.isEmpty()) {
							if(i==3)i=0; //contor pt a vedea cand se termina o carte(ver autor,titlu si nrbuc). cand am terminat de parcurs trecem la urmatoarea si setam i=0
							i++;
							if(i==1) //am gasit un autor
							{
								c.setAutor(xmlStreamReader.getText().trim()); //adaugam autor in Carti c				
							}
							else if(i==2) //am gasit un titlu
							{
								c.setTitlu(xmlStreamReader.getText().trim()); //adaugam titlu in Carti c
							}
							else if(i==3) //am gasit nrBucati
							{
								c.setNrEx(xmlStreamReader.getText().trim()); //adaugam nrBucati in Carti c
							}
						}
						break;
					case XMLEvent.END_ELEMENT:
							String s1 = xmlStreamReader.getLocalName(); 
							if (s1.equals("carte"))carti.add(c); //am terminat de parcurs cartea(am ajuns la al doilea tag de <carti>)adaugam Carti c in ArrayList carti.
						break;
				}
				xmlStreamReader.next();
			}
			if(xmlStreamReader.getEventType() == XMLEvent.END_DOCUMENT) {
				//System.out.println("End Document");
			}
			xmlStreamReader.close();
		}
		catch(IOException e){
			System.out.println("eroare citire fisier");
			System.exit(1);		
		}
		
		//Cautam in ArrayList Carti daca se potriveste un autor si titlu ce cautam in variabila nume si titlu.
		for(int j=0;j<carti.size();j++)
		{
			if(carti.get(j).getAutor().compareTo(nume)==0)
				if(carti.get(j).getTitlu().compareTo(titlu)==0) nrBucati=Integer.parseInt(carti.get(j).getNrEx());
		}
		
		if(nrBucati==-1)System.out.println("Nu exista cartea !");
		else System.out.println("nr. exemplare din carte = "+nrBucati);
		
	}
	
	public static void ModificaText()
	{
		
		Scanner sc = new Scanner(System.in);
		System.out.print("Autor: ");
		String autor = sc.nextLine();
		System.out.print("Titlu: ");
		String titlu = sc.nextLine();
		System.out.print("Nr. bucati noi(daca vrei sa stergi cartea scrie 0):");
		String nrbuc = sc.nextLine();
		
		
		CreateNewFile(autor,titlu,nrbuc);
		 
	     //pt stergere fisier carti si redenumire fisier carti2 in carti:
		File file = new File("src\\carti\\carti.txt");
		file.delete();
			
		File file1 = new File("src\\carti\\carti2.txt");
		File file2 = new File("src\\carti\\carti.txt");
		file1.renameTo(file2);
	}
	
	
	public static void CreateNewFile(String autor,String titlu,String nrbuc)
	{
		
		
		try {
			//pt scriere fisier:
			FileWriter fw=new FileWriter("src\\carti\\carti2.txt");
			BufferedWriter bfw=new BufferedWriter(fw);
				try {
					//pt citire fisier
					FileReader fr=new FileReader("src\\carti\\carti.txt");
					BufferedReader bfr=new BufferedReader(fr);
					
					for(;;) {
						String autorF=bfr.readLine();
						if(autorF==null)break;
						if(!autor.equals(autorF)) {
							//daca nu este egal cu autorul cautat adaugam linia in fisierul nou(carti2) impreuna cu titlul si nr. buc
							bfw.write(autorF);
							bfw.newLine();
							String titluF=bfr.readLine();
							bfw.write(titluF);
							bfw.newLine();
							String nrBucatiF=bfr.readLine();
							bfw.write(nrBucatiF);
							bfw.newLine();
						}else {
							//este acelasi autor, deci citesc si titlul:
							String titluF=bfr.readLine();							
							if(titlu.equals(titluF)) {
								//pt stergere:
								if(nrbuc.equals("0")) bfr.readLine(); //sarim peste
								//pt modificare nr. buc:
								else {
									//scriem in fisierul nou(carti2) autorul,titlul si nr de exemplare dorite din variabila nrbuc
									bfw.write(autorF); 
									bfw.newLine();
									bfw.write(titluF);
									bfw.newLine();
									bfr.readLine();
									bfw.write(nrbuc);
									bfw.newLine();
								}
								
							}else {
								//daca este acelasi autor dar nu este acelasi titlu adaugam autorul,titlul si nr. buc in fisierul nou creat
								bfw.write(autorF);
								bfw.newLine();
								bfw.write(titluF);
								bfw.newLine();
								String nrBucatiF=bfr.readLine();
								bfw.write(nrBucatiF);
								bfw.newLine();}
							}
						}
					bfr.close(); fr.close();
					}catch(IOException e) {
						System.out.println("eroare citire fisier");
						System.exit(1);		
					}
				bfw.close();  fw.close();
		}catch(IOException e) {
			System.out.println("eroare scriere fisier");
			System.exit(1);
		}
       
	}
	
	
	
	
}
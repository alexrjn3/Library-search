package carti;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class FirClient extends Thread{
	   private Socket cs;
	   private BufferedReader bfr;
	   private PrintWriter pw;
	   //constructorul clasei:
	   public FirClient(Socket clientsock)
	   {
	     try{
	      cs=clientsock;
	      pw=new PrintWriter(cs.getOutputStream()); //pt a putea scrie texte catre socket
	    //pt a putea primi texte din socket:
	      InputStreamReader isr=
	             new InputStreamReader(cs.getInputStream());
	      bfr=new BufferedReader(isr); 
	     }catch(Exception e){
	            e.printStackTrace();
	     }
	   }

	   public void run()
	   {
	     String textIn;
	     try{
	      for(;;){
	        textIn=bfr.readLine(); //primim string text de la client
	        if(textIn==null)break;
	        if(textIn.equals(""))break;//s-a deconectat clientul 
	                                                  //( a tastat STOP);
	         int rez=nrBucati(textIn); //apelam metoda nrBucati
	         if(rez==-1)pw.println("Nu exista cartea !");
	         else pw.println("nr. exemplare din carte = " +rez);
	       
	         pw.flush();
	      }//for;;
	     }catch(Exception e){
	            e.printStackTrace();
	     }
	   }//run

	  int nrBucati(String s){
		  String[] v=s.split("space");  //separam autorul si titlul
		 int nrBucati=-1;
		 
		 //cautam in fisier text
		 try {
				FileReader fr=new FileReader("src\\carti\\carti.txt");
				BufferedReader bfr=new BufferedReader(fr);
				//cautare liniara
				for(;;) {
					String autorF=bfr.readLine();
					if(autorF==null)break;
					
					if(!v[0].equals(autorF)) { //nu este acelasi autor
						//trec peste urmatoarele 2 linii:
						bfr.readLine();
						bfr.readLine();
					}else {
						//este acelasi autor, deci citesc si titlul:
						String titluF=bfr.readLine();
						if(v[1].equals(titluF)) {
							String sNrBucati=bfr.readLine();
							nrBucati=Integer.parseInt(sNrBucati); //am gasit nr de exemplare
							
						}else {
							//nu este acelasi titlu, deci sar peste linia urmatoare:
							bfr.readLine();}
						}
					}
					bfr.close(); fr.close();
				}catch(IOException e) {
					System.out.println("eroare fisier");
					System.exit(1);		
			}
		 	return nrBucati;
	  }
	}
package carti;

import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		System.out.println("Am pornit server...");
		try {
			ServerSocket ss = new ServerSocket(5000); //cream server si asteptam conexiuni client
			for (;;) {
				Socket cs = ss.accept(); //cream socket client cand se realizeaza o conexiune client
				FirClient firPtClient = new FirClient(cs); //initializam socket cs pt a scrie texte catre socket si a primi texte
				firPtClient.start();
				System.out.println("Avem o conectare!");
			} // for;;
		} catch (Exception e) {
			e.printStackTrace();
		}
	

	}

}

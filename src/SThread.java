/**
 *
 * @author Louis
 * adaptación del código hecho por imran
 * https://github.com/Imran92/Java-UDP-Video-Stream-Server
 */
import java.io.BufferedReader;
import java.io.DataOutputStream;

class SThread extends Thread {

	public static String clientSentence;
	int srcid;
	BufferedReader inFromClient = JavaServer.inFromClient[srcid];
	DataOutputStream outToClient[] = JavaServer.outToClient;

	public SThread(int a) {
		srcid = a;
	}

	public void run() {
		while (true) {
			try {

				clientSentence = inFromClient.readLine();

				System.out.println("From Client " + srcid + ": " + clientSentence);
				//Canvas101.ta.append("From Client " + srcid + ": " + clientSentence + "\n");
				
				for(int i=0; i<JavaServer.i; i++){
                    
                    if(i!=srcid)
                        outToClient[i].writeBytes("Client "+srcid+": "+clientSentence + '\n');	//'\n' is necessary
                }
				
				Canvas101.myjp.revalidate();
				Canvas101.myjp.repaint();

					} catch (Exception e) {
			}

		}
	}
}
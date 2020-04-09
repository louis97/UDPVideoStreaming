/**
 *
 * @author Louis
 * adaptación del código hecho por imran
 * https://github.com/Imran92/Java-UDP-Video-Stream-Server
 */

class Sentencefromserver extends Thread {

	public static String sendingSentence;
	public Sentencefromserver() {
	}
	
	public void run() {
		while (true) {
			try {
				if(sendingSentence.length()>0)
				{
					System.out.println("entra a sendingSentence.length()>0");
					for (int i = 0; i < JavaServer.i; i++) {
						JavaServer.outToClient[i].writeBytes("From Server: "+sendingSentence+'\n');
					}
					sendingSentence = null;
				}
			} catch (Exception e) {}
		}
	}
}
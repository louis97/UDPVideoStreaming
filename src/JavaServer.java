/**
 *
 * @author Louis
 * adaptación del código hecho por imran
 * https://github.com/Imran92/Java-UDP-Video-Stream-Server
 */

import com.sun.jna.Native;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.imageio.ImageIO;

import javax.swing.*;

import com.sun.jna.NativeLibrary;
import java.nio.file.Files;
import uk.co.caprica.vlcj.binding.LibVlc;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;
import uk.co.caprica.vlcj.runtime.x.LibXUtil;
//import uk.co.caprica.vlcj.runtime.windows.WindowsRuntimeUtil;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;


public class JavaServer {
	
	public static InetAddress[] inet;
	public static int[] port;
	public static int i;
	static int count = 0;
	public static BufferedReader[] inFromClient;
	public static DataOutputStream[] outToClient;
	
	public static void main(String[] args) throws Exception
	{
		JavaServer jv = new JavaServer();
	}

	public JavaServer() throws Exception {
		
		NativeLibrary.addSearchPath("libvlc", "C:\\Program Files\\VideoLAN\\VLC");

		//grupo de IPs
		JavaServer.inet = new InetAddress[30];
		System.out.println("grupo de IPs: "+JavaServer.inet);
		port = new int[30];
		System.out.println("grupo de puertos: "+port);

		ServerSocket welcomeSocket = new ServerSocket(6782);
		System.out.println(welcomeSocket.isClosed());
		Socket connectionSocket[] = new Socket[30];
		inFromClient = new BufferedReader[30];
		outToClient = new DataOutputStream[30];

		DatagramSocket serv = new DatagramSocket(4321);

		byte[] buf = new byte[62000];

		DatagramPacket dp = new DatagramPacket(buf, buf.length);

		Canvas101 canv = new Canvas101();
		System.out.println("Canvas creado");

		i = 0;
		//hilos de servidor
		SThread[] st = new SThread[30];
		

		while (true) {
			//se verifica la conexion al cliente por udp
			System.out.println("new DatagramSocket(4321).getPort(): "+serv.getPort());
			serv.receive(dp);
			System.out.println("Lo primero que recibe el servidor: "+new String(dp.getData()));
			buf = "starts".getBytes();
			
			//asigna puerto y ip a los clientes respectivos
			inet[i] = dp.getAddress();
			port[i] = dp.getPort();

			DatagramPacket dsend = new DatagramPacket(buf, buf.length, inet[i], port[i]);
			serv.send(dsend);
			
			//Inicializa el hilo del video
			VidThread sendvid = new VidThread(serv);
			
			//Se conecta tcp al cliente
			System.out.println("waiting\n ");
			connectionSocket[i] = welcomeSocket.accept();
			System.out.println("connected client " + i);
			
			// confirmacion de conexion tcp
			inFromClient[i] = new BufferedReader(new InputStreamReader(connectionSocket[i].getInputStream()));
			outToClient[i] = new DataOutputStream(connectionSocket[i].getOutputStream());
			outToClient[i].writeBytes("Connected: from Server\n");

			//inicia el hilo del servidor
			st[i] = new SThread(i);
			st[i].start();
			
			if(count == 0)
			{
				Sentencefromserver sen = new Sentencefromserver();
				sen.start();
				count++;
			}

			System.out.println("En el JavaServer inet[i]: "+inet[i]);
			//Se envia video
			sendvid.start();
			//siguiente cliente, aumenta en 1 el contador
			i++;

			if (i == 30) {
				break;
			}
		}
	}
}
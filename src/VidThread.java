/**
 *
 * @author Louis
 * adaptación del código hecho por imran
 * https://github.com/Imran92/Java-UDP-Video-Stream-Server
 */
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

class VidThread extends Thread {

	int clientno;
	// InetAddress iadd = InetAddress.getLocalHost();
	JFrame jf = new JFrame("scrnshots before sending");
	JLabel jleb = new JLabel();

	DatagramSocket soc;

	Robot rb = new Robot();

	byte[] outbuff = new byte[62000];

	BufferedImage mybuf;
	ImageIcon img;
	Rectangle rc;
	
	int bord = Canvas101.panel.getY() - Canvas101.frame.getY();
	public VidThread(DatagramSocket ds) throws Exception {
		soc = ds;

		System.out.println(soc.getPort());
		jf.setSize(500, 400);
		jf.setLocation(500, 400);
		jf.setVisible(true);
	}

	public void run() {
		while (true) {
			try {

				int num = JavaServer.i;

				rc = new Rectangle(new Point(Canvas101.frame.getX() + 8, Canvas101.frame.getY() + 27),
						new Dimension(Canvas101.panel.getWidth(), Canvas101.frame.getHeight() / 2));

				// System.out.println("another frame sent ");

				mybuf = rb.createScreenCapture(rc);

				img = new ImageIcon(mybuf);

				jleb.setIcon(img);
				jf.add(jleb);
				jf.repaint();
				jf.revalidate();
				// jf.setVisible(true);
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				
				ImageIO.write(mybuf, "jpg", baos);
				
				outbuff = baos.toByteArray();

				for (int j = 0; j < num; j++) {
					DatagramPacket dp = new DatagramPacket(outbuff, outbuff.length, JavaServer.inet[j],
							JavaServer.port[j]);
					//System.out.println("Frame Sent to: " + JavaServer.inet[j] + " port: " + JavaServer.port[j]
						//	+ " size: " + baos.toByteArray().length);
					soc.send(dp);
					baos.flush();
				}
				Thread.sleep(15);

			} catch (Exception e) {

			}
		}

	}

}
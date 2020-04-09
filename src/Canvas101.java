/**
 *
 * @author Louis
 * adaptación del código hecho por imran
 * https://github.com/Imran92/Java-UDP-Video-Stream-Server
 */
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;
import uk.co.caprica.vlcj.player.embedded.videosurface.CanvasVideoSurface;

class Canvas101 {

	// Create a media player factory
	private MediaPlayerFactory mediaPlayerFactory;

	// Create a new media player instance for the run-time platform
	private EmbeddedMediaPlayer mediaPlayer;

	public static JPanel panel;
	public static JPanel myjp;
	private Canvas canvas;
	public static JFrame frame;
	//public static JTextArea ta;
	//public static JTextArea txinp;
	public static int xpos = 0, ypos = 0;
	String url = "C:\\Users\\ADMIN\\Desktop\\expoandes\\UdpJavaStreaming\\data\\";

	// Constructor
	public Canvas101() {

		// Creating a panel that while contains the canvas
		panel = new JPanel();
		panel.setLayout(new BorderLayout());

		JPanel mypanel = new JPanel();
		mypanel.setLayout(new GridLayout(2, 1));

		// Creating the canvas and adding it to the panel :
		canvas = new Canvas();
		canvas.setBackground(Color.BLACK);
		//canvas.setSize(640, 480);
		panel.add(canvas, BorderLayout.CENTER);
	
		// panel.revalidate(); va en SThread en un while True
		// panel.repaint(); va en SThread en un while True

		// Creation a media player :
		mediaPlayerFactory = new MediaPlayerFactory();
		mediaPlayer = mediaPlayerFactory.newEmbeddedMediaPlayer();
		CanvasVideoSurface videoSurface = mediaPlayerFactory.newVideoSurface(canvas);
		mediaPlayer.setVideoSurface(videoSurface);

		// Construction of the jframe :
		frame = new JFrame("Louis' Player");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(200, 0);
		frame.setSize(640, 960);
		frame.setAlwaysOnTop(true);

		// Adding the panel to the frame
		mypanel.add(panel);
		frame.add(mypanel);
		frame.setVisible(true);
		xpos = frame.getX();
		ypos = frame.getY();

		// Playing the video

		myjp = new JPanel(new GridLayout(1, 1));
		myjp.setSize(640,50);
		Button bn = new Button("Choose File");
		myjp.add(bn);
		

		//JScrollPane jpane = new JScrollPane();
		//jpane.setSize(300, 200);
		/*
		Button sender = new Button("Send Text");
		ta = new JTextArea();
		txinp = new JTextArea();
		jpane.add(ta);
		jpane.setViewportView(ta);
		myjp.add(txinp);
		ta.setText("Initialized");
		ta.setCaretPosition(ta.getDocument().getLength());
		myjp.add(sender);
		*/
		//jpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		//myjp.add(jpane);
		mypanel.add(myjp);
		mypanel.revalidate();
		mypanel.repaint();

		bn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser jf = new JFileChooser(url);
				jf.showOpenDialog(frame);
				File f;
				f = jf.getSelectedFile();
				url = f.getPath();
				System.out.println(url);
				/*
				ta.setText("check text\n");
				ta.append(url+"\n");
				*/
				mediaPlayer.playMedia(url);
			}
		});
		/*
		sender.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
				Sentencefromserver.sendingSentence = txinp.getText();
				txinp.setText(null);
				Canvas101.ta.append("From Myself: " + Sentencefromserver.sendingSentence + "\n");
				
				Canvas101.myjp.revalidate();
				Canvas101.myjp.repaint();
			}
		});
		*/
		

	}
}
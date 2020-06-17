package pwr.edu.app.library;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.event.ActionEvent;

interface MyListener {
	void messageReceived(String theLine);
}

class Receiver {

	RSAKeyGenerator keygen= new RSAKeyGenerator();
	ReadKeys x = new ReadKeys();
	Encryption encr= new Encryption();

	private List<MyListener> ml = new ArrayList<MyListener>();
	private Thread t = null;
	private int port = 0;
	private ServerSocket s = null;
	private boolean end = false;
	private static String chat_participan_pubKey=null;
	private String prvKey= x.getPrvKey("RSA/privateKey");


	public void stop() {
			t.interrupt();
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	}

	public void start() {
		end = false;
		t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					s = new ServerSocket(port);
					while (true) {
						Socket sc = s.accept();
							InputStream is = sc.getInputStream();
							InputStreamReader isr = new InputStreamReader(is);
							BufferedReader br = new BufferedReader(isr);
							String theLine = br.readLine();

							if(chat_participan_pubKey==null)
								{
								setChat_participan_pubKey(theLine);
								keygen.writeToFile("RSAParticipiant/publicKey",theLine.getBytes());
								}
							else
								{
								ml.forEach((item) -> {
									try {
											item.messageReceived(encr.decrypt(theLine,prvKey));
										} catch (IllegalBlockSizeException e) {
										e.printStackTrace();
									} catch (InvalidKeyException e) {
										e.printStackTrace();
									} catch (BadPaddingException e) {
										e.printStackTrace();
									} catch (NoSuchAlgorithmException e) {
										e.printStackTrace();
									} catch (NoSuchPaddingException e) {
											e.printStackTrace();
									}
								});
								}
							sc.close();
						}
				} catch(SocketException e){
					// TODO - podczas przerywania w�tku metoda accept zg�osi wyj�tek
					// z wiadomo�ci�: socket closed
				}
				catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		t.start();
	}

	public void addMyListener(MyListener m) {
		ml.add(m);
	}
	public void setChat_participan_pubKey(String s){
		this.chat_participan_pubKey=s;
	}
	public String getChat_participian_pubKey(){
		return this.chat_participan_pubKey;
	}
	public void removeMyListener(MyListener m) {
		ml.remove(m);
	}

	Receiver(int port) throws Exception {
		this.port = port;
		this.chat_participan_pubKey=null;
	}

}

public class PReceiver extends JPanel implements MyListener {

	private JTextField txtPort;
	private Receiver r = null;
	private JTextArea txtMessage;
	private JScrollPane sp;
	String chatMessageCache="";

	/**
	 * Create the panel.
	 */
	public PReceiver() throws Exception {

		setLayout(null);
		txtPort = new JTextField();
		txtPort.setBounds(300, 30, 62, 22);
		add(txtPort);
		// txtPort.setColumns(10);

		JLabel lblPort = new JLabel("port:");
		lblPort.setBounds(300, 16, 35, 16);
		add(lblPort);

		JToggleButton btnListen = new JToggleButton("Listen");

		btnListen.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (btnListen.isSelected()) {
					try {
						r = new Receiver(Integer.parseInt(txtPort.getText()));
					} catch (Exception e) {
						e.printStackTrace();
					}
					r.addMyListener(PReceiver.this);
					r.start();
				} else {
					r.stop();
				}
			}
		});
		btnListen.setBounds(300, 60, 79, 25);
		add(btnListen);
		
		JLabel lblMessage = new JLabel("message from chat mate");
		lblMessage.setBounds(12, 4, 220, 16);
		add(lblMessage);
		
		txtMessage = new JTextArea();
		JScrollPane scrollPane = new JScrollPane(txtMessage);
		scrollPane.setBounds(10,20,275,90);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		txtMessage.setBounds(12, 20, 275, 80);
		//add(txtMessage);
		add(scrollPane);
		//txtMessage.setColumns(10);


	}

	@Override
	public void messageReceived(String theLine) {
		Date date = new Date();
		chatMessageCache+="\n"+date.getHours()+":"+date.getMinutes()+":"+date.getSeconds()+": "+theLine;
		txtMessage.setText(chatMessageCache);
	}
}

package pwr.edu.app.library;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.swing.JLabel;

class Sender{
	private List<MyListener> ml = new ArrayList<MyListener>();
	public void send(String message, String host, int port,String own_private_key){
		Socket s;
		try {
			s = new Socket(host,port);
			OutputStream out = s.getOutputStream();
			PrintWriter pw = new PrintWriter(out, false);
			pw.println(message);
			pw.flush();
			pw.close();
			s.close();

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
public class PSender extends JPanel {
	private JTextField txtMessage;
	private JTextField txtHost;
	private JTextField txtPort;
	private JTextField pubKey;
	private JLabel lblMessage;

	private RSAKeyGenerator keyPairGenerator = new RSAKeyGenerator();
	private ReadKeys rk= new ReadKeys();
	private Encryption encr = new Encryption();

	private String pubKeyChatMate;
	private boolean keysInitialized=false;

	/**
	 * Create the panel.
	 */
	public void setKeysInitialized(boolean s){
		this.keysInitialized=s;
	}
	public boolean getKeysInitialized(){
		return this.keysInitialized;
	}

	public PSender(String host, int port) throws Exception {
		setLayout(null);
		keyPairGenerator.writeToFile("RSA/publicKey", keyPairGenerator.getPublicKey().getEncoded());
		keyPairGenerator.writeToFile("RSA/privateKey", keyPairGenerator.getPrivateKey().getEncoded());

		txtMessage = new JTextField();
		txtMessage.setBounds(12, 77, 216, 22);
		add(txtMessage);

		//send public Key to second user
		JButton btnInit = new JButton("Init connection");
		btnInit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					new Sender().send(Base64.getEncoder().encodeToString(keyPairGenerator.getPublicKey().getEncoded()),txtHost.getText(),Integer.parseInt(txtPort.getText()),Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded()));
					setKeysInitialized(true);
				}
				catch(NumberFormatException e){
					e.printStackTrace();
				}}});
		btnInit.setBounds(225, 46, 180, 25);
		add(btnInit);

		//sending message
		JButton btnSend = new JButton("Send");
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
             try {
             	if(keysInitialized) pubKeyChatMate =rk.getPubKey("RSAParticipiant/publicKey");
				 String encryptedString = Base64.getEncoder().encodeToString(encr.encrypt(txtMessage.getText(), pubKeyChatMate));
				new Sender().send(encryptedString,txtHost.getText(),Integer.parseInt(txtPort.getText()),Base64.getEncoder().encodeToString(keyPairGenerator.getPrivateKey().getEncoded()));

             }
             catch(NumberFormatException e){
            	 e.printStackTrace();
             } catch (Exception e) {
				 e.printStackTrace();
			 }
			}});
		btnSend.setBounds(268, 76, 78, 25);
		add(btnSend);
		
		txtHost = new JTextField();
		txtHost.setBounds(51, 13, 149, 22);
		add(txtHost);

		
		txtPort = new JTextField();
		txtPort.setBounds(268, 13, 78, 22);
		add(txtPort);


		pubKey = new JTextField();
		pubKey.setBounds(268, 13, 78, 22);
		add(pubKey);

		JLabel lblHost = new JLabel("host:");
		lblHost.setBounds(12, 16, 35, 16);
		add(lblHost);
		
		JLabel lblPort = new JLabel("port:");
		lblPort.setBounds(231, 16, 35, 16);
		add(lblPort);
		
		lblMessage = new JLabel("message");
		lblMessage.setBounds(12, 58, 56, 16);
		add(lblMessage);


	}
}

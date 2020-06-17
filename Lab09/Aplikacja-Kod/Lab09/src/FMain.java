import pwr.edu.app.library.PReceiver;
import pwr.edu.app.library.PSender;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;

public class FMain extends JFrame {

	private JPanel contentPane=null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		System.setSecurityManager(new SecurityManager());
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pwr.edu.app.library.FMain frame = new pwr.edu.app.library.FMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FMain() throws Exception {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 508, 321);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		PReceiver receiver = new PReceiver();
		receiver.setBorder(new LineBorder(new Color(0, 0, 0)));
		receiver.setBounds(35, 13, 423, 106);
		contentPane.add(receiver);

		PSender sender = new PSender((String) null, 0);
		sender.setBorder(new LineBorder(new Color(0, 0, 0)));
		sender.setBounds(35, 132, 423, 129);
		contentPane.add(sender);
	}
}

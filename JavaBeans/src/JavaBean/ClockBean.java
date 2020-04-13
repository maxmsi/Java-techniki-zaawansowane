package JavaBean;

import JavaBean.InnerClasses.ClockPanel;
import JavaBean.InnerClasses.Counter;
import JavaBean.InnerClasses.MyTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.*;
import java.io.Serializable;
import java.util.Date;


public class ClockBean extends JPanel implements ActionListener, Serializable, PropertyChangeListener {

    private MyTimer myTimer;
    public JButton startButton = new JButton("Start");
    private Font myFont = new Font("Segoe Print", Font.PLAIN, 14);
    private Color myBackground = Color.decode("0xff0000");
    private ClockPanel clockPanel;
    private String title = "Alarm Clock";

    private PropertyChangeSupport changes = new PropertyChangeSupport(this);
    private VetoableChangeSupport vetoes = new VetoableChangeSupport(this);

    public ClockBean() throws PropertyVetoException {
        super(new BorderLayout(10,10));
        startButton.addActionListener(this);

        clockPanel = new ClockPanel(this);
        myTimer = new MyTimer(this);

        setMyBackground("0xff0000");
        setTitle("JavaBean-Budzik");
        setMyFonts(myFont.getFontName());

        addPropertyChangeListener(this);
        addVetoableChangeListener(evt -> {
            String color = evt.getNewValue().toString();
            if(Color.decode(color).equals(Color.BLACK) || Color.decode(color).equals(Color.WHITE))
                throw new PropertyVetoException("Kolor tła nie może się być czarny lub biały", evt);
        });

        add(clockPanel, BorderLayout.CENTER);
        add(startButton, BorderLayout.PAGE_END);
        add(myTimer, BorderLayout.PAGE_START);
    }

    public void addPropertyChangeListener(PropertyChangeListener property){
        changes.addPropertyChangeListener(property);
    }
    public void removePropertyChangeListener(PropertyChangeListener property){
        changes.removePropertyChangeListener(property);
    }
    public void addVetoableChangeListener(VetoableChangeListener veto){
        vetoes.addVetoableChangeListener(veto);
    }
    public void removeVetoableChangeListener(VetoableChangeListener veto){
        vetoes.removeVetoableChangeListener(veto);
    }


    public String getMyBackground() {
        String r, g, b;
        if (myBackground.getRed()<16) r = Integer.toHexString(myBackground.getRed()) + "0"; else r = Integer.toHexString(myBackground.getRed());
        if (myBackground.getGreen()<16) g = Integer.toHexString(myBackground.getGreen()) + "0"; else g = Integer.toHexString(myBackground.getGreen());
        if (myBackground.getBlue()<16) b = Integer.toHexString(myBackground.getBlue()) + "0"; else b = Integer.toHexString(myBackground.getBlue());
        return ("0x" + r + g + b);
    }

    public ClockPanel getClockPanel() {
        return clockPanel;
    }

    public void setMyBackground(String color) throws PropertyVetoException {
        String oldColor = this.myBackground.toString();
        vetoes.fireVetoableChange("myBackground", oldColor, color);
        myBackground = Color.decode(color);
        setBackground(myBackground);
        clockPanel.setBackg(myBackground);
    }

    public void setTitle(String str) {
        String oldTitle = getTitle();
        this.title = str;
        this.clockPanel.setTitle(str);
        changes.firePropertyChange("title", oldTitle, str);
    }

    public String getTitle() {
        return title;
    }


    public void setMyFonts(String strFont){
        Font font = new Font(strFont, Font.PLAIN, 14);
        this.myFont = font;
        startButton.setFont(font);
        clockPanel.setFonts(font.deriveFont(14f));
        myTimer.setFont(font.deriveFont(24f));
    }

    public String getMyFonts() {
        return myFont.getFontName();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();

        if (source.equals(startButton)) {
            {
                    Date time = new Date();
                    time.setHours(clockPanel.getSelectedHour());
                    time.setMinutes(clockPanel.getSelectedMinute());
                    time.setSeconds(clockPanel.getSelectedSecond());

                    if(!new Date().before(time)) time.setDate(new Date().getDate()+1);
                    Counter cnt = new Counter(this, time);
                    cnt.setFonts(myFont);
                    clockPanel.add(cnt);
            }
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (getTitle()) {
            case "czerwony":
                try {
                    setMyBackground("0xff0000");
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
                break;
            case "zielony":
                try {
                    setMyBackground("0x00ff00");
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
                break;
            case "niebieski":
                try {
                    setMyBackground("0x0000ff");
                } catch (PropertyVetoException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    public static void main(String[] args) throws PropertyVetoException {
        JFrame window = new JFrame();
        ClockBean clock = new ClockBean();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(275, 400);
        window.setContentPane(clock);
        window.setVisible(true);
    }
}

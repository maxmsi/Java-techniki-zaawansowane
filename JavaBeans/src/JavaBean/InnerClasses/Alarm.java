package JavaBean.InnerClasses;

import JavaBean.ClockBean;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Alarm extends TimerTask
{
    private ClockBean panel;
    private Timer timer;
    private Counter counter;

    public Alarm(ClockBean panel, Timer timer, Counter counter) {
        this.panel = panel;
        this.timer = timer;
        this.counter = counter;
    }

    public void endAlarm(){
        timer.cancel();
    }

    public void run()
    {
        Toolkit.getDefaultToolkit().beep();
        String[] options = {"Drzemka", "Wyłącz"};
        int x = JOptionPane.showOptionDialog(panel, "Alarm uruchomiony", "Budzik", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if(x==1) { endAlarm(); counter.setClosed(true); }
        else {
            counter.setEndTimeMin(counter.getEndTimeMin()+1);
            timer.schedule(new Alarm(panel, timer, counter), counter.getEndTime());
        }
    }
}
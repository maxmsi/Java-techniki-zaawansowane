package JavaBean.InnerClasses;

import JavaBean.ClockBean;

import javax.swing.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static java.lang.Thread.sleep;

public class MyTimer extends JLabel implements Runnable {

    private String time;
    private LocalDate localDate = LocalDate.now();
    private DayOfWeek dayOfWeek = DayOfWeek.from(localDate);

    public MyTimer(ClockBean panel) {
        super(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")), SwingConstants.CENTER);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                sleep(1000);
                time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
                super.setText(time+" "+dayOfWeek.name()+", "+dayOfWeek.getValue()+ " dzień tygodnia." );
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

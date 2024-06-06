import java.applet.Applet;
import java.awt.Button;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*<applet code="StopwatchApplet.class" width="300" height="300"></applet> */

public class StopwatchApplet extends Applet implements ActionListener, Runnable {
    Button startBtn, stopBtn, resetBtn;
    Thread thread;
    int hours, minutes, seconds, milliseconds;
    boolean isRunning;

    public void init() {
        startBtn = new Button("Start");
        stopBtn = new Button("Stop");
        resetBtn = new Button("Reset");

        startBtn.addActionListener(this);
        stopBtn.addActionListener(this);
        resetBtn.addActionListener(this);

        add(startBtn);
        add(stopBtn);
        add(resetBtn);

        isRunning = false;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startBtn) {
            if (!isRunning) {
                isRunning = true;
                thread = new Thread(this);
                thread.start();
            }
        } else if (e.getSource() == stopBtn) {
            isRunning = false;
        } else if (e.getSource() == resetBtn) {
            isRunning = false;
            hours = minutes = seconds = milliseconds = 0;
            repaint();
        }
    }

    public void run() {
        try {
            while (isRunning) {
                Thread.sleep(10);
                milliseconds++;
                if (milliseconds == 100) {
                    milliseconds = 0;
                    seconds++;
                }
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                if (minutes == 60) {
                    minutes = 0;
                    hours++;
                }
                repaint();
            }
        } catch (InterruptedException e) {
            System.out.println("Thread Interrupted");
        }
    }

    public void paint(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("Times New Roman", Font.BOLD, 20));
        g.drawString(String.format("%02d:%02d:%02d:%02d", hours, minutes, seconds, milliseconds / 10), 100, 100);
    }
}

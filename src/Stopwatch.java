import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Stopwatch implements ActionListener {
    private int counter = 0;
    int totalPomoTimes = 0; // total sudah berapa kali istirahat pendek
    JFrame frame = new JFrame();
    JButton startButton = new JButton("START");
    JButton resetButton = new JButton("RESET");

    JButton buttonPomoTime = new JButton("ENTER");
    JButton buttonShortTime = new JButton("ENTER");
    JButton buttonLongTime = new JButton("ENTER");

    JLabel shortTimeLabel = new JLabel();
    JLabel pomoTimeLabel = new JLabel();
    JLabel longTimeLabel = new JLabel();

    JTextField setPomoField = new JTextField();
    JTextField setShortField = new JTextField();
    JTextField setLongField = new JTextField();

    int pomoSeconds = 0, pomoMinutes = 0, pomoHours = 0;
    int shortSeconds = 0, shortMinutes = 0, shortHours = 0;
    int longSeconds = 0, longMinutes = 0, longHours = 0;

    String pomoSec_str = String.format("%02d", pomoSeconds);
    String pomoMin_str = String.format("%02d", pomoMinutes);
    String pomoHours_str = String.format("%02d", pomoHours);

    String longSec_str = String.format("%02d", longSeconds);
    String longMin_str = String.format("%02d", longMinutes);
    String longHours_str = String.format("%02d", longHours);

    String shortSec_str = String.format("%02d", shortSeconds);
    String shortMin_str = String.format("%02d", shortMinutes);
    String shortHours_str = String.format("%02d", shortHours);

    int pomoTotalTimeMillis = 0;
    int shortTotalTimeMillis = 0;
    int longTotalTimeMillis = 0;

    JLabel pomoDescription = new JLabel();
    JLabel shortDescription = new JLabel();
    JLabel longDescription = new JLabel();
    JLabel title = new JLabel();
    String audioFilePath = "E:\\My Project\\Pomodoro-Timer\\src\\alarm.wav";

    Timer timerPomo = new Timer(1000, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            pomoTotalTimeMillis =  pomoTotalTimeMillis - 1000;
            pomoSeconds = (pomoTotalTimeMillis/1000) % 60;
            pomoMinutes = (pomoTotalTimeMillis/60000) % 60;
            pomoHours = (pomoTotalTimeMillis/3600000);

            pomoSec_str = String.format("%02d", pomoSeconds);
            pomoMin_str = String.format("%02d", pomoMinutes);
            pomoHours_str = String.format("%02d", pomoHours);

            pomoTimeLabel.setText(pomoHours_str+":"+pomoMin_str+":"+pomoSec_str);

            if (pomoHours == 0 && pomoMinutes == 0 && pomoSeconds == 0) {
                pomoStop();
                totalPomoTimes++;
                playMusic(audioFilePath);
                if (totalPomoTimes % 4 == 0) {
                    longStart();
                } else {
                    shortStart();
                }
            }
        }
    });


    Timer timerLong = new Timer(1000, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            longTotalTimeMillis =  longTotalTimeMillis - 1000;
            longSeconds = (longTotalTimeMillis/1000) % 60;
            longMinutes = (longTotalTimeMillis/60000) % 60;
            longHours = (longTotalTimeMillis/3600000);

            longSec_str = String.format("%02d", longSeconds);
            longMin_str = String.format("%02d", longMinutes);
            longHours_str = String.format("%02d", longHours);

            longTimeLabel.setText(longHours_str + ":"+ longMin_str + ":" + longSec_str);

            if (longHours == 0 && longMinutes == 0 && longSeconds == 0) {
                longStop();
                counter = 0;
                startButton.setText("START");
                playMusic(audioFilePath);
            }
        }
    });

    Timer timerShort = new Timer(1000, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            shortTotalTimeMillis =  shortTotalTimeMillis - 1000;
            shortSeconds = (shortTotalTimeMillis/1000) % 60;
            shortMinutes = (shortTotalTimeMillis/60000) % 60;
            shortHours = (shortTotalTimeMillis/3600000);

            shortSec_str = String.format("%02d", shortSeconds);
            shortMin_str = String.format("%02d", shortMinutes);
            shortHours_str = String.format("%02d", shortHours);

            shortTimeLabel.setText(shortHours_str+":"+shortMin_str+":"+shortSec_str);

            if (shortHours == 0 && shortMinutes == 0 && shortSeconds == 0) {
                shortStop();
                counter = 0;
                startButton.setText("START");
                playMusic(audioFilePath);
            }
        }
    });


    Stopwatch() {
        title.setBounds(321,26,579,61);
        title.setText("Focus on Your Task Dude!");
        title.setFont(new Font("Source Sans Pro", Font.BOLD, 40));
        title.setForeground(Color.white);
        title.setBackground(new Color(217, 85, 80, 0));
        title.setHorizontalAlignment(JTextField.CENTER);

        pomoDescription.setText("POMODORO TIME (MINUTES)");
        pomoDescription.setBounds(48,477,305,35);
        pomoDescription.setBorder(BorderFactory.createBevelBorder(1));
        pomoDescription.setOpaque(true);
//        pomoDescription.setBackground(new Color(217, 85, 8));
        pomoDescription.setBackground(new Color(217, 85, 80, 0));
        pomoDescription.setFont(new Font("Source Sans Pro",Font.PLAIN,15));
        pomoDescription.setHorizontalAlignment(JTextField.LEFT);
        pomoDescription.setForeground(Color.white);

        shortDescription.setText("SHORT BREAK TIME (MINUTES)");
        shortDescription.setBounds(464,477,305,35);
        shortDescription.setBorder(BorderFactory.createBevelBorder(1));
        shortDescription.setOpaque(true);
        shortDescription.setBackground(new Color(217, 85, 80, 0));
        shortDescription.setFont(new Font("Source Sans Pro",Font.PLAIN,15));
        shortDescription.setHorizontalAlignment(JTextField.LEFT);
        shortDescription.setForeground(Color.white);

        longDescription.setText("LONG BREAK TIME (MINUTES)");
        longDescription.setBounds(879,477,305,35);
        longDescription.setBorder(BorderFactory.createBevelBorder(1));
        longDescription.setOpaque(true);
        longDescription.setBackground(new Color(217, 85, 80, 0));
        longDescription.setFont(new Font("Source Sans Pro",Font.PLAIN,15));
        longDescription.setHorizontalAlignment(JTextField.LEFT);
        longDescription.setForeground(Color.white);

        setPomoField.setBounds(147,518,170,51);
        setPomoField.setBackground(new Color(217, 217, 217));
        setPomoField.setFont(new Font("Consolas", Font.PLAIN, 20));
        setPomoField.setText("25");

        setShortField.setBounds(562,518,170,51);
        setShortField.setBackground(new Color(217, 217, 217));
        setShortField.setFont(new Font("Consolas", Font.PLAIN, 20));
        setShortField.setText("5");

        setLongField.setBounds(977,518,170,51);
        setLongField.setBackground(new Color(217, 217, 217));
        setLongField.setFont(new Font("Consolas", Font.PLAIN, 20));
        setLongField.setText("15");

        // BUTTON enter the time
        buttonPomoTime.setBounds(48,518,99,51);
        buttonPomoTime.setFont(new Font("ArialRounded", Font.BOLD, 15));
        buttonPomoTime.setBackground(new Color(64, 64, 64));
        buttonPomoTime.setForeground(Color.white);
        buttonPomoTime.setFocusable(false);
        buttonPomoTime.addActionListener(this);

        buttonShortTime.setBounds(464,518,99,51);
        buttonShortTime.setFont(new Font("ArialRounded", Font.BOLD, 15));
        buttonShortTime.setBackground(new Color(64, 64, 64));
        buttonShortTime.setForeground(Color.white);
        buttonShortTime.setFocusable(false);
        buttonShortTime.addActionListener(this);

        buttonLongTime.setBounds(879,518,99,51);
        buttonLongTime.setFont(new Font("ArialRounded", Font.BOLD, 15));
        buttonLongTime.setBackground(new Color(64, 64, 64));
        buttonLongTime.setForeground(Color.white);
        buttonLongTime.setFocusable(false);
        buttonLongTime.addActionListener(this);

        ImageIcon image = new ImageIcon("E:\\My Project\\Pomodoro-Timer\\src\\clock-hour-9.png");

        // tabel  output timer
        shortTimeLabel.setText(shortHours_str+":"+shortMin_str+":"+shortSec_str);
        shortTimeLabel.setBounds(465,170, 270,270);
        shortTimeLabel.setBorder(BorderFactory.createBevelBorder(1));
        shortTimeLabel.setOpaque(true);
        shortTimeLabel.setHorizontalAlignment(JTextField.CENTER);
        shortTimeLabel.setFont(new Font("Courier New",Font.PLAIN,30));
        shortTimeLabel.setBackground(new Color(64, 64, 64));
        shortTimeLabel.setForeground(Color.white);

        // label short time
        pomoTimeLabel.setText(pomoHours_str+":"+pomoMin_str+":"+pomoSec_str);
        pomoTimeLabel.setBounds(92,170, 270,270);
        pomoTimeLabel.setBorder(BorderFactory.createBevelBorder(1));
        pomoTimeLabel.setOpaque(true);
        pomoTimeLabel.setHorizontalAlignment(JTextField.CENTER);
        pomoTimeLabel.setFont(new Font("Courier New",Font.PLAIN,30));
        pomoTimeLabel.setBackground(new Color(64, 64, 64));
        pomoTimeLabel.setForeground(Color.white);


        // label long time
        longTimeLabel.setText(longHours_str+":"+longMin_str+":"+longSec_str);
        longTimeLabel.setBounds(838,170, 270,270);
        longTimeLabel.setBorder(BorderFactory.createBevelBorder(1));
        longTimeLabel.setOpaque(true);
        longTimeLabel.setHorizontalAlignment(JTextField.CENTER);
        longTimeLabel.setFont(new Font("Courier New",Font.PLAIN,30));
        longTimeLabel.setBackground(new Color(64, 64, 64));
        longTimeLabel.setForeground(Color.white);

        // button start
        startButton.setBounds(394,  600,176,55);
        startButton.setFont(new Font("ArialRounded", Font.BOLD,30));
        startButton.setBackground(new Color(217, 217, 217));
        startButton.setFocusable(false);
        startButton.addActionListener(this);

        // button stop
        resetButton.setBounds(629,600,176,55);
        resetButton.setFont(new Font("ArialRounded", Font.BOLD,30));
        resetButton.setBackground(new Color(217, 217, 217));
        resetButton.setFocusable(false);
        resetButton.addActionListener(this);

        frame.add(title);

        frame.add(setPomoField);
        frame.add(setShortField);
        frame.add(setLongField);
        frame.add(buttonPomoTime);
        frame.add(buttonShortTime);
        frame.add(buttonLongTime);

        frame.add(pomoDescription);
        frame.add(shortDescription);
        frame.add(longDescription);
        frame.add(startButton);
        frame.add(resetButton);
        frame.add(pomoTimeLabel);
        frame.add(longTimeLabel);
        frame.add(shortTimeLabel);
        frame.setIconImage(image.getImage());
        frame.setTitle("Pomodoro Timer");
        frame.getContentPane().setBackground(new Color(217, 85, 80));
        frame.setSize(1200,750);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setVisible(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==buttonPomoTime) {
            pomoMinutes = Integer.parseInt(setPomoField.getText());
            pomoMin_str = String.format("%02d", pomoMinutes);
            pomoTotalTimeMillis = pomoHours * 3600000 + pomoMinutes * 60000 + pomoSeconds * 1000;
        }

        if (e.getSource()==buttonShortTime) {
            shortMinutes = Integer.parseInt(setShortField.getText());
            shortMin_str = String.format("%02d", shortMinutes);
            shortTotalTimeMillis = shortHours * 3600000 + shortMinutes * 60000 + shortSeconds * 1000;
        }

        if (e.getSource()==buttonLongTime) {
            longMinutes = Integer.parseInt(setLongField.getText());
            longMin_str = String.format("%02d", longMinutes);
            longTotalTimeMillis = longHours * 3600000 + longMinutes * 60000 + longSeconds * 1000;
        }

        if (e.getSource() == startButton) {
            if (pomoTotalTimeMillis == 0 || longTotalTimeMillis == 0 || shortTotalTimeMillis == 0) {
                JOptionPane.showMessageDialog(null,"You must enter the time before you start","Error",JOptionPane.ERROR_MESSAGE);
            } else {
                counter++;
                if (counter % 2 != 0) {
                    pomoStart();
                    startButton.setText("STOP");
                } else {
                    pomoStop();
                    startButton.setText("START");
                }
            }
            System.out.println("you press start button");
        }

        if (e.getSource() == resetButton) {
            timerPomo.stop();
            timerShort.stop();
            timerLong.stop();
            pomoTotalTimeMillis = 0;
            shortTotalTimeMillis = 0;
            longTotalTimeMillis = 0;
            pomoSeconds = 0;
            pomoHours = 0;
            pomoMinutes = 0;
            shortSeconds = 0;
            shortMinutes = 0;
            shortHours = 0;
            longSeconds = 0;
            longMinutes = 0;
            longHours = 0;

            pomoSec_str = String.format("%02d", pomoSeconds);
            pomoMin_str = String.format("%02d", pomoMinutes);
            pomoHours_str = String.format("%02d", pomoHours);
            pomoTimeLabel.setText(pomoHours_str+":"+pomoMin_str+":"+pomoSec_str);

            shortSec_str = String.format("%02d", shortSeconds);
            shortMin_str = String.format("%02d", shortMinutes);
            shortHours_str = String.format("%02d", shortHours);
            shortTimeLabel.setText(shortHours_str+":"+shortMin_str+":"+shortSec_str);

            longSec_str = String.format("%02d", longSeconds);
            longMin_str = String.format("%02d", longMinutes);
            longHours_str = String.format("%02d", longHours);
            longTimeLabel.setText(longHours_str + ":"+ longMin_str + ":" + longSec_str);

            counter = 0;
            startButton.setText("START");

            System.out.println("you press stop button");
        }

        pomoTimeLabel.setText(pomoHours_str+":"+pomoMin_str+":"+pomoSec_str);
        shortTimeLabel.setText(shortHours_str+":"+shortMin_str+":"+shortSec_str);
        longTimeLabel.setText(longHours_str+":"+longMin_str+":"+longSec_str);

    }

    void playMusic(String filePath) {
        try {
            File file = new File(filePath);
            if (file.exists()) {
                AudioInputStream audio = AudioSystem.getAudioInputStream(file);
                Clip clip = AudioSystem.getClip();
                clip.open(audio);
                clip.start();
            } else {
                System.out.println("Can't find the file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void pomoStart() {
        timerPomo.start();
    }
    void pomoStop() {
        timerPomo.stop();
    }

    void shortStart() {
        timerShort.start();
    }
    void shortStop() {
        timerShort.stop();
    }

    void longStart() {
        timerLong.start();
    }
    void longStop() {
        timerLong.stop();
    }
}
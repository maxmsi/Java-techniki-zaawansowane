package pwr.edu.pl;

import javax.imageio.ImageIO;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

class ImageTransformingPanel extends JPanel{
    private JButton nextImgBtn;
    private JButton prevImgBtn;
    private JButton setPathBtn;
    private JLabel imgLbl = new JLabel();
    private JLabel pathLbl = new JLabel("Ustaw sciezke do folderu z obrazami: ");
    private JTextField pathFld = new JTextField("");
    private BufferedImage actualImg;

    private int imageIndex;
    private ArrayList<BufferedImage> imgList = new java.util.ArrayList<>();


    public ImageTransformingPanel(){
        super(new BorderLayout(10, 10));
        imageIndex = 0;
        File directory = new File("./");
        System.out.println(directory.getAbsolutePath());
        loadImgs(directory.getAbsolutePath()+"/src/img");

        final JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 3));
        JPanel action_panel = new JPanel();
        action_panel.setLayout(new GridLayout(2, 2));

        panel.add(pathLbl);
        panel.add(pathFld);
        JButton setPathBtn = new JButton("Zatwierdź sciężke");
        panel.add(setPathBtn);
        setPathBtn.addActionListener(e->
        {
            loadImgs(pathFld.getText());
        });


        JButton prevImgBtn = new JButton("Poprzedni obrazek");
        action_panel.add(prevImgBtn);
        prevImgBtn.addActionListener(e ->
        {
            --imageIndex;
            if(imageIndex < 0) { imageIndex = imgList.size() - 1; }
            initListImages();
        });

        JButton nextImgBtn = new JButton("Następny obrazek");
        action_panel.add(nextImgBtn);
        nextImgBtn.addActionListener(e->
        {
            ++imageIndex;
            if(imageIndex > imgList.size())imageIndex=0;
            initListImages();
        });




        JComboBox<String> algComboBox = new JComboBox<>();
        algComboBox.addItem("sepia");
        algComboBox.addItem("usunCzerwony");
        algComboBox.addItem("cien");
        algComboBox.addItem("skalaSzarosci");
        algComboBox.setSelectedIndex(0);

        JButton applyButton = new JButton("Przetwarzaj obraz");
        panel.add(new Label("Wybierz algorytm przetwarzania obrazu :"));
        panel.add(algComboBox);
        panel.add(applyButton);
        applyButton.addActionListener(e ->
        {

            ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
            ScriptEngine engine = scriptEngineManager.getEngineByName("nashorn");
            try {   
                engine.eval(new FileReader(directory.getAbsolutePath()+"/src/js/ImageTranforming.js"));
                Invocable invocable = (Invocable) engine;
                actualImg = (BufferedImage) invocable.invokeFunction((String)algComboBox.getSelectedItem(), actualImg, actualImg.getWidth(), actualImg.getHeight());
                imgLbl.setIcon(new ImageIcon(actualImg));

            } catch (ScriptException | NoSuchMethodException | IOException e1) {
                e1.printStackTrace();
            }

            
        });

        add(panel, BorderLayout.NORTH);
        add(imgLbl, BorderLayout.CENTER);
        add(action_panel, BorderLayout.SOUTH);
        setVisible(true);
        setPreferredSize(new Dimension(500, 600));
    }


    void loadImgs(String path) {
        File dir = new File(path);
        File[] files = dir.listFiles((dir1, name) -> name.toLowerCase().endsWith(".jpg"));
        for (File file : files) {
            try {
                imgList.add(ImageIO.read(file));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        initListImages();
    }

    void initListImages()
    {
        actualImg = imgList.get(imageIndex);
        ImageIcon icon = new ImageIcon(actualImg);
        imgLbl.setIcon(icon);
    }

}

public class ImageTransforming {

    public static void main(String[] args) {

        ImageTransforming imageTranforming= new ImageTransforming();
}
    public ImageTransforming() {
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(800, 600);
        window.setContentPane(new ImageTransformingPanel());
        window.setVisible(true);

    }
}

package ui;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by tanxiaocan on 2016/3/21.
 */
public class MyPanel extends JPanel {
    @Override
    public void paintComponent(Graphics g) { /* 重绘函数 */
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
// String directory = ".//images//background3.jpg";
        URL imgURL = this.getClass().getResource(".//images/background.JPG");
// this.getClass().getClassLoader().getResource(directory);
        String picPath = "src/main/resources/images/backageground.JPG";
        Image img = Toolkit.getDefaultToolkit().createImage(picPath);
// Image img = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource(directory));
// // 在面板上绘制背景图片/////////
        g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}

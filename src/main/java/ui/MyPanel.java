package ui;

import utils.ResourceUtil;
import javax.swing.*;
import java.awt.*;

/**
 * Created by tanxiaocan on 2016/3/21.
 */
public class MyPanel extends JPanel {
    @Override
    public void paintComponent(Graphics g) { /* 重绘函数 */
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
// String directory = ".//images//background3.jpg";
// this.getClass().getClassLoader().getResource(directory);
//        Image img = Toolkit.getDefaultToolkit().createImage(ResourceUtil.getPropertiesFileForJar("images/background.JPG").getPath());
// Image img = Toolkit.getDefaultToolkit().getImage(this.getClass().getClassLoader().getResource(directory));
// // 在面板上绘制背景图片/////////
//        g2.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}

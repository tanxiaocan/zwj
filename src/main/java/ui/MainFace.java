package main.java.ui;

import main.java.domain.FinalInfo;
import main.java.service.DomResolveServcie;
import main.java.service.ItemsService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by zhaiwenjie on 2016/3/20.
 */
public class MainFace extends JFrame{
    JPanel panelContainer;//主面板
    private ButtonGroup buttonGroup;
    private JPanel topPanel;
    private JPanel middlePanel;
    private JPanel bottomPanel;
    private JButton chooseDirBtn;//选取要解析的文件夹按钮
    private JTextField dirPathText;//要解析的目录
    private JButton startBtn;//开始按钮
    private JButton addBtn;//添加按钮
    private JTextField newItemText;//新的规则输入框
    private JPanel chooseDirPanel;//选取目录的panel
    private JPanel addNewPanel;//添加新规则的panel
    private JTextArea showMessageArea;//显示操作信息的文字域
    private JPanel exportPanel;//设置导出文件名称Panel
    private JLabel exportNameLable;//导出文件标签
    private JTextField exportNameText;//导出文件名称

    public MainFace(String title){
        super();
        this.setTitle(title);
        this.setSize(800,600);
        BoxLayout boxLayout = new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS);
        this.getContentPane().setLayout(boxLayout);

    }
    public void showMainFace(){
        createTopPanel();
        createMiddlePanel();
        createBottomPanel();
        panelContainer = new MyPanel();
        //panelContainer 的布局为 GridBagLayout
        panelContainer.setLayout(new GridBagLayout());
        panelContainer.setBackground(new Color(84,245,22));
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0;
        c1.gridy = 0;
        c1.weightx = 1.0;
        c1.weighty = 0;
        c1.fill = GridBagConstraints.BOTH ;
        // 加入 topPanel
        panelContainer.add(topPanel,c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 1;
        c2.weightx = 1.0;
        c2.weighty = 1.0;
        c2.fill = GridBagConstraints.HORIZONTAL ;
        // 加入 middlePanel
        panelContainer.add(middlePanel,c2);

        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 2;
        c3.weightx = 1.0;
        c3.weighty = 0;
        c3.fill = GridBagConstraints.HORIZONTAL ;
        // 加入 bottomPanel
        panelContainer.add(bottomPanel,c3);

        this.setContentPane(panelContainer);
        this.setVisible(true);
    }

    private void  createTopPanel(){
        java.util.List<String> items  = ItemsService.getItems();
        topPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(topPanel,BoxLayout.Y_AXIS);
        topPanel.setLayout(boxLayout);
        topPanel.setBackground(new Color(84,245,22));
        topPanel.add(Box.createVerticalStrut(10));
        buttonGroup = new ButtonGroup();
        for(int i = 0;i < items.size();i ++){
            JRadioButtonMenuItem jRadioButtonMenuItem = new JRadioButtonMenuItem(items.get(i));
            jRadioButtonMenuItem.setBackground(new Color(84,245,22));
            buttonGroup.add(jRadioButtonMenuItem);
            topPanel.add(jRadioButtonMenuItem);
        }

        topPanel.add(Box.createHorizontalStrut(10));
        createAddNewPanel();
        topPanel.add(addNewPanel);
    }

    private void createMiddlePanel(){
        middlePanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(middlePanel,BoxLayout.Y_AXIS);
        middlePanel.setLayout(boxLayout);
        middlePanel.setBackground(new Color(84,245,22));
        JLabel jLabel = new JLabel("控制台信息");
        middlePanel.add(jLabel);
        middlePanel.add(Box.createVerticalStrut(10));
        showMessageArea = new JTextArea();
        showMessageArea.setLineWrap(true);
        showMessageArea.setRows(10);
        JScrollPane jScrollPane = new JScrollPane(showMessageArea);
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        middlePanel.add(jScrollPane);
    }

    private void createBottomPanel(){
        bottomPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(bottomPanel,BoxLayout.Y_AXIS);
        bottomPanel.setLayout(boxLayout);
        bottomPanel.setBackground(new Color(84,245,22));
        createChooseDirPanel();
        bottomPanel.add(chooseDirPanel);
        bottomPanel.add(Box.createVerticalStrut(10));
        startBtn = new JButton("开始");
        bottomPanel.add(startBtn);
        bottomPanel.add(Box.createVerticalStrut(50));

        chooseDirBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseDir();
            }
        });

        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startParse();
            }
        });
    }
    private void createChooseDirPanel(){
        chooseDirPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(chooseDirPanel,BoxLayout.X_AXIS);
        chooseDirPanel.setLayout(boxLayout);
        chooseDirBtn = new JButton("选取目录");
        chooseDirPanel.add(chooseDirBtn);
        chooseDirPanel.add(Box.createHorizontalStrut(10));
        dirPathText = new JTextField();
        dirPathText.setEnabled(false);
        chooseDirPanel.add(dirPathText);
        bottomPanel.add(Box.createVerticalStrut(10));
        createExportPanel();
        bottomPanel.add(exportPanel);
        bottomPanel.add(Box.createVerticalStrut(10));
    }

    private void createAddNewPanel(){
        addNewPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(addNewPanel,BoxLayout.X_AXIS);
        addNewPanel.setLayout(boxLayout);
        addBtn = new JButton("添加");
        addBtn.setToolTipText("点击此按钮添加新的导出规则");
        addNewPanel.add(addBtn);
        addNewPanel.add(Box.createHorizontalStrut(10));
        newItemText = new JTextField();
        addNewPanel.add(newItemText);
        
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addNewRule(newItemText.getText().trim());
            }
        });
    }

    private void addNewRule(String rule) {
        if(rule == null || "".equals(rule)){
            JOptionPane.showMessageDialog(null,"规则不能为空");
            return;
        }
        rule.replaceAll(",","，");
        Map<String,Object> resp = ItemsService.addItem(rule);
        if(resp.get("code").equals("0")){
            this.showMainFace();
            String newConsoleMessage = "\n" + "新的规则：" + resp.get("data").toString() + " 添加成功";
            showMessageArea.append(newConsoleMessage);
        }else{
            JOptionPane.showMessageDialog(null,resp.get("message").toString());
            return;
        }

    }

    private void createExportPanel(){
        exportPanel = new JPanel();
        BoxLayout boxLayout = new BoxLayout(exportPanel,BoxLayout.X_AXIS);
        exportPanel.setLayout(boxLayout);
        exportNameLable = new JLabel("导出文件名称");
        exportPanel.add(exportNameLable);
        exportPanel.add(Box.createHorizontalStrut(10));
        exportNameText = new JTextField();
        exportPanel.add(exportNameText);
    }

    private void chooseDir(){
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("请选择要解析的目录");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = jfc.showOpenDialog(this);
        File file = null;
        if(JFileChooser.APPROVE_OPTION == result) {
            file = jfc.getSelectedFile();
            if(!file.isDirectory()) {
                JOptionPane.showMessageDialog(null, "你选择的目录不存在");
                return ;
            }
            String dirName = file.getAbsolutePath();
            dirPathText.setText(dirName);
//            String path = file.getAbsolutePath();
//            file = new File(path + "\\"+ "www_NoExist.xls");
//            if(!file.isFile()) {
//                if(!file.isFile()) {
//                    JOptionPane.showMessageDialog(null, "文件不存在");
//                    return ;
//                }
//            }
        } else {
            return ;
        }
    }
    private void startParse(){
        String selectedItem = "";
        Enumeration<AbstractButton> enu = buttonGroup.getElements();
        while (enu.hasMoreElements()) {
            JRadioButtonMenuItem radioButton = (JRadioButtonMenuItem) enu.nextElement();
            if(radioButton.isSelected()){
                selectedItem = radioButton.getText().trim();
            }
        }
        if(selectedItem.equals("")){
            JOptionPane.showMessageDialog(null,"请选择一个解析规则！");
            return;
        }
        String[] items = selectedItem.split("，");
        java.util.List<String> itemList = new ArrayList<String>();
        for(String item : items){
            itemList.add(item);
        }
        String exportName = exportNameText.getText().trim();
        if(exportName == null || exportName.equals("")){
            JOptionPane.showMessageDialog(null,"导出文件名不能为空！");
            return;
        }
        exportName = "d:/" + exportName + ".xls";
        String dirName = dirPathText.getText().trim();
        Map<String,Object> resp = DomResolveServcie.resolveDomToExcel(dirName,exportName,itemList);
        String consoleMessage = "";
        if(resp.get("code").equals("0")){
            FinalInfo info = (FinalInfo) resp.get("data");
            java.util.List<String> successList = info.getSuccessList();
            java.util.List<String> failList = info.getFailList();
            consoleMessage += "成功解析文件：" + successList.size() + "\n";
            for(String msg : successList){
                consoleMessage += msg + "\n";
            }

            consoleMessage += "失败解析文件：" + failList.size() + "\n";
            for(String msg : failList){
                consoleMessage += msg + "\n";
            }
        }
        showMessageArea.setText(consoleMessage);
    }
    public static void main(String[] args){
        new MainFace("word解析器").showMainFace();
    }
}

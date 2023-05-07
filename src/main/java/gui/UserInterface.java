package gui;

import sim.Simulation;
import java.awt.*;
import java.io.Serial;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import static java.lang.Integer.parseInt;

public class UserInterface extends JFrame {

    @Serial
    private static final long serialVersionUID = 1L;
    public Simulation simulation = new Simulation(this);
    private JPanel content = new JPanel(new GridLayout(0, 1));
    private final JLabel label = new JLabel("");
    private int queNr;

    Color panelColor = new Color(252, 208, 128);

    //input
    private final JTextField text1 = new JTextField(10) ;
    private final JTextField text2 = new JTextField(10) ;
    private final JTextField text3 = new JTextField(10) ;
    private final JTextField text4 = new JTextField(10) ;
    private final JTextField text5 = new JTextField(10) ;
    private final JTextField text6 = new JTextField(10) ;
    private final JTextField text7 = new JTextField(10) ;


    private JPanel panel2 = new JPanel(new FlowLayout());
    private JLabel clientsLabel = new JLabel("Number of clients(max 1000):");
    private JPanel panel3 = new JPanel(new FlowLayout());
    private JLabel qsLabel = new JLabel("Number of queues(max 5):");
    private JPanel panel11 = new JPanel(new FlowLayout());
    private JLabel simTimeLabel = new JLabel("Simulation time:");
    private JPanel panel12 = new JPanel(new FlowLayout());
    private JLabel minArrLabel = new JLabel("Minimum arrival time:");
    private JPanel panel13 = new JPanel(new FlowLayout());
    private JLabel maxArrLabel = new JLabel("Maximum arrival time:");
    private JPanel panel14 = new JPanel(new FlowLayout());
    private JLabel minSerLabel = new JLabel("Minimum service time:");
    private JPanel panel15 = new JPanel(new FlowLayout());
    private JLabel maxSerLabel = new JLabel("Maximum service time:");
    private JLabel inputLabel = new JLabel("Input your data:");
    private JPanel panel16 = new JPanel(new FlowLayout());

    //queues
    ArrayList<JTextField> qfield = new ArrayList<JTextField>();
    ArrayList<JPanel> panelQ = new ArrayList<JPanel>();
    ArrayList<JLabel> labelQ = new ArrayList<>();

    private final JButton simBtn = new JButton("Simulate");
    private JPanel simBtnPanel = new JPanel(new FlowLayout());

    public UserInterface(String name) {
        super(name);
        queNr = 0;
        simBtn.addActionListener(simulation);

        panel2.add(clientsLabel);
        panel2.add(text1);

        panel3.add(qsLabel);
        panel3.add(text2);

        panel11.add(simTimeLabel);
        panel11.add(text3);

        panel12.add(minArrLabel);
        panel12.add(text4);

        panel13.add(maxArrLabel);
        panel13.add(text5);

        panel14.add(minSerLabel);
        panel14.add(text6);

        panel15.add(maxSerLabel);
        panel15.add(text7);

        panel16.add(inputLabel);

        simBtnPanel.add(simBtn);
        simBtn.setBackground(panelColor);

        JPanel inputPanel = new JPanel(new GridLayout(0,1));
        inputPanel.add(panel16);
        inputPanel.add(panel2);
        inputPanel.add(panel3);
        inputPanel.add(panel11);
        inputPanel.add(panel12);
        inputPanel.add(panel13);
        inputPanel.add(panel14);
        inputPanel.add(panel15);
        inputPanel.add(simBtnPanel);
        inputPanel.add(label);

        JPanel panel1 = new JPanel(new GridLayout(0, 1));
        panel1.setBackground(panelColor);
        panel2.setBackground(panelColor);
        panel3.setBackground(panelColor);
        panel11.setBackground(panelColor);
        panel12.setBackground(panelColor);
        panel13.setBackground(panelColor);
        panel14.setBackground(panelColor);
        panel15.setBackground(panelColor);
        panel16.setBackground(panelColor);
        inputPanel.setBackground(panelColor);
        simBtnPanel.setBackground(panelColor);

        content.add(inputPanel);
        this.add(content);
    }

    public JButton getsimBtn(){
        return simBtn;
    }

    public String getClients(){
        return text1.getText();
    }

    public String getQueues(){
        return text2.getText();
    }

    public String getSim(){
        return text3.getText();
    }
    public String getMinA(){
        return text4.getText();
    }
    public String getMaxA(){
        return text5.getText();
    }
    public String getMinS(){
        return text6.getText();
    }
    public String getMaxS(){
        return text7.getText();
    }
    public void setLabel(String s){
        label.setText(s);
    }

    public void setQfield(int  pos, String qfield) {
        this.qfield.get(pos).setText(qfield);
    }

    public void setPanelQ(ArrayList<JPanel> panelQ) {
        this.panelQ = panelQ;
    }

    public void setOptionPane(String s){
        JOptionPane.showMessageDialog(content, s);
    }

    public void reset(){
        for(int i = 0; i < Integer.parseInt(getQueues()); i++){
            qfield.get(i).setText("");
        }
        text1.setText("");
        text2.setText("");
        text3.setText("");
        text4.setText("");
        text5.setText("");
        text6.setText("");
        text7.setText("");
    }

    public void actionPerformed(){
        JPanel panelS = new JPanel(new GridLayout(0,1));
        panelS.setPreferredSize(panelS.getPreferredSize());
        for(int i = 0; i < Integer.parseInt(getQueues()); i++){
            qfield.add(new JTextField(50));
            qfield.get(i).setEditable(false);
        }
        for(int i = 0; i < Integer.parseInt(getQueues()); i++){
            labelQ.add(new JLabel());
        }
        for(int i = 0; i < Integer.parseInt(getQueues()); i++){
            panelQ.add(new JPanel(new FlowLayout()));
            panelQ.get(i).add(labelQ.get(i));
            panelQ.get(i).add(qfield.get(i));
            panelQ.get(i).setBackground(panelColor);
            panelS.add(panelQ.get(i));
        }
        panelS.setBackground(panelColor);
        content.add(panelS);
    }
}


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;



/**
 * @author Zhang.YM
 *
 */
public class UI implements ActionListener {
    private JFrame frame = null;
    private JPanel mainPanel = null;
    private JPanel controlPanel = null;
    private Canvas canvas;
    private FlowLayout f = new FlowLayout();


    private JButton startBtn;
    private JButton pauseBtn;
    private JButton stopBtn;

    //ComboBox -- Virus option
    private JComboBox cb;
    private static JLabel l;
    //JTextField --- R0 COVID19
    protected JLabel rc;
    protected static JTextField r0c;
    //JTextField --- R0 SARS
    protected JLabel rs;
    protected static JTextField r0s;
    //JTextField --- Initial case
    protected JLabel initialLabel;
    protected static JTextField initialCase;
    //JTextField --- Population
    protected JLabel populationLabel;
    protected static JTextField population;
    //JTextField --- Mask(effectiveness)
    protected JLabel m;
    protected JLabel meLabel;
    protected static JTextField me;
    //JTextField --- Mask(initial_usage_perc)
    protected JLabel mpLabel;
    protected static JTextField mp;
    //JTextField --- Vaccine(up_limit)
    protected JLabel v;
    protected JLabel vlLabel;
    protected static JTextField vl;
    //JTextField --- Vaccine(efficiency)
    protected JLabel veLabel;
    protected static JTextField ve;
    //JTextField --- Vaccine(created day)
    protected JLabel vdLabel;
    protected static JTextField vd;
    //JTextField --- Vaccine(all_injected_day)
    protected JLabel viLabel;
    protected static JTextField vi;
    //JTextField --- Cure(cure_basic_perc)
    protected JLabel c;
    protected JLabel cureLabel;
    protected static JTextField cure;
    //JTextField --- Cure(cure_need_days)
    protected JLabel cnLabel;
    protected static JTextField cn;
    //JTextField --- Test(percentage)
    protected JLabel t;
    protected JLabel tpLabel;
    protected static JTextField tp;
    //JTextField --- Test(efficiency)
    protected JLabel teLabel;
    protected static JTextField te;
    //JTextField --- Trace(percentage)
    protected JLabel trace;
    protected JLabel tcLabel;
    protected static JTextField tc;
    //JTextField --- Trace(efficiency)
    protected JLabel treLabel;
    protected static JTextField tre;
    //JTextField --- BARRIER(suspect)
    protected JLabel b;
    protected JLabel bsLabel;
    protected static JTextField bs;
    //JTextField --- Trace(patient)
    protected JLabel bpLabel;
    protected static JTextField bp;
    //JTextField --- Trace(ventilation)
    protected JLabel bvLabel;
    protected static JTextField bv;
    private String option;


    /**
     * Constructor
     */
    public UI() {
        initGUI();
    }
    //Build needed GUI components
    private void initGUI() {
        frame = new JFrame();
        frame.setSize(1000, 1000);
        frame.setTitle("COVID-19 Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Press red button to exit app
        frame.setLayout(new BorderLayout());

        //Add panels to the frame
        frame.add(getMainPanel(),BorderLayout.NORTH);
        frame.add(getControlPanel(),BorderLayout.SOUTH);
        canvas = new Canvas();// Build a custom JPanel that shows instruction
        frame.add(canvas,BorderLayout.CENTER);
        frame.setVisible(true);
    }

    /**
     * Build a Main panel
     * @return a JPanel with content
     */
    public JPanel getMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setBackground(Color.DARK_GRAY);

        //create three buttons
        startBtn = new JButton("Run");
        pauseBtn = new JButton("Clear");
        stopBtn = new JButton("Default");

        startBtn.addActionListener(this);
        pauseBtn.addActionListener(this);
        stopBtn.addActionListener(this);

        mainPanel.setLayout(f);//Widgets will flow from left to tight

        //Add buttons to the panel
        mainPanel.add(startBtn);
        mainPanel.add(pauseBtn);
        mainPanel.add(stopBtn);

        //JBomboBox --- Virus
        l = new JLabel("Virus");
        l.setForeground(Color.white);
        cb = new JComboBox<String>();
        mainPanel.add(cb);
        cb.addItem("Option");
        cb.addItem("Covid-19");
        cb.addItem("SARS");
        mainPanel.add(l);
        mainPanel.add(cb);
        cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                if (e.getStateChange() == ItemEvent.SELECTED) {

                    switch((String)(e.getItem())) {
                        case "Covid-19": {
                            option = (String)(e.getItem());
                            System.out.println("Choosing " + option);
                            break;
                        }

                        case "SARS": {
                            option = (String)(e.getItem());
                            System.out.println("Choosing " + e.getItem());
                            break;
                        }
                    }
                }
            }

        });

        return mainPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("Action Event: " + e);
        if(e.getActionCommand().equalsIgnoreCase("Run")) {
            System.out.println("Run pressed ！！！");
            listener();
        }else if(e.getActionCommand().equalsIgnoreCase("Clear")) {
            System.out.println("Clear pressed ！！！");
            ClearBtnAction();
        }else if(e.getActionCommand().equalsIgnoreCase("Default")) {
            System.out.println("Default pressed ！！！");
            defaultBtnAction();
        }
    }

    /**
     * Default button: set all parameters default value
     */
    public void defaultBtnAction() {
        //JTextField --- Covid19 R0
        double d1 = 2;
        r0c.setText(String.valueOf(d1));
        //JTextField --- SARS R0
        double d2 = 3;
        r0s.setText(String.valueOf(d2));
        //JTextField --- Initial case
        int d3 = 10;
        initialCase.setText(String.valueOf(d3));
        //JTextField --- Population
        int d4 = (int) 3E8;
        population.setText(String.valueOf(d4));
        //JTextField --- Mask
        double d5 = 0.5;
        me.setText(String.valueOf(d5));

        double d6 = 0.9;
        mp.setText(String.valueOf(d6));
        //JTextField --- Vaccine
        double dvl = 0.8;
        vl.setText(String.valueOf(dvl));

        double d7 = 0.8;
        ve.setText(String.valueOf(d7));

        double d8 = 300;
        vd.setText(String.valueOf(d8));

        double d9 = 480;
        vi.setText(String.valueOf(d9));
        //JTextField --- Cure
        double d10 = 0.3;
        cure.setText(String.valueOf(d10));

        double dcn = 20;
        cn.setText(String.valueOf(dcn));
        //JTextField --- Test
        double d11 = 0.2;
        tp.setText(String.valueOf(d11));

        double d12 = 0.9;
        te.setText(String.valueOf(d12));
        //JTextField --- Trace
        double d13 = 0.5;
        tc.setText(String.valueOf(d13));

        double d14 = 0.3;
        tre.setText(String.valueOf(d14));
        //JTextField --- Barrier
        double d15 = 0.3;
        bs.setText(String.valueOf(d15));

        double d16 = 0.5;
        bp.setText(String.valueOf(d16));

        double d17 = 0.2;
        bv.setText(String.valueOf(d17));

    }

    /**
     * Clear button: reset all parameter
     */
    public void ClearBtnAction() {

        cb.setSelectedIndex(0);
        //JTextField --- Covid19 R0
        r0c.setText("");
        //JTextField --- SARS R0
        r0s.setText("");
        //JTextField --- Initial case
        initialCase.setText("");
        //JTextField --- Population
        population.setText("");
        //JTextField --- Mask
        me.setText("");
        mp.setText("");
        //JTextField --- Vaccine
        vl.setText("");
        ve.setText("");
        vd.setText("");
        vi.setText("");
        //JTextField --- Cure
        cn.setText("");
        cure.setText("");
        //JTextField --- Test
        tp.setText("");
        te.setText("");
        //JTextField --- Trace
        tc.setText("");
        tre.setText("");
        //JTextField --- Barrier
        bs.setText("");
        bp.setText("");
        bv.setText("");

    }
    /**
     * ActionListener of JTextField
     */
    private void listener() {

        //JTextField --- Covid19 R0
        String l1 = r0c.getText();
        double d1 = Double.parseDouble(l1) ;
        if(d1 < 2 || d1 >3 ) {
            r0c.setText("Input 2~3 !!!");
            JOptionPane.showMessageDialog(r0c, "Covid19 R0 should be 2~3 !!!", "Error Alert", JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Covid19 R0 is "+ l1);

        //JTextField --- SARS R0
        String l2 = r0s.getText();
        double d2 = Double.parseDouble(l2) ;
        if(d2 < 1.8 || d2 > 3.6 ) {
            r0s.setText("Input 1.8~3.6 !!!");
            JOptionPane.showMessageDialog(r0s, "SARS R0 should be 1.8~3.6 !!!", "Error Alert", JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("SARS R0 is "+ l2);

        //JTextField --- Initial case
        String l3 = initialCase.getText();
        double d3 = Double.parseDouble(l3) ;
        if(d3 < 0 || d3 >1000 ) {
            initialCase.setText("Input 0~1000");
            JOptionPane.showMessageDialog(initialCase, "Initial case should be  0~1000 !!!", "Error Alert", JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Initial case is "+ l3);

        //JTextField --- Population
        String l4 = population.getText();
        double d4 = Double.parseDouble(l4) ;
        if(d4 < 10000 ) {
            population.setText("Input > 10000");
            JOptionPane.showMessageDialog(population, "Population should be > 10000 !!!", "Error Alert",JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Population at risk is "+ l4);

        //JTextField --- Mask
        String l5 = me.getText();
        double d5 = Double.parseDouble(l5) ;
        if(d5  < 0 || d5 > 1) {
            me.setText("Input 0~1");
            JOptionPane.showMessageDialog(me, "Mask effectiveness should be 0~1 !!!", "Error Alert",JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Mask effectiveness is "+ l5);

        String l6 = mp.getText();
        double d6 = Double.parseDouble(l6) ;
        if(d6 < 0 || d6 > 1 ) {
            mp.setText("Input 0~1");
            JOptionPane.showMessageDialog(mp, "Mask Up Limit should be 0~1 !!!", "Error Alert", JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Mask Up Limit is "+ l6);

        //JTextField --- Vaccine
        String up_limit = vl.getText();
        double dl = Double.parseDouble(up_limit) ;
        if(dl < 0 || dl > 1 ) {
            vl.setText("Input 0~1");
            JOptionPane.showMessageDialog(vl, "Vaccine Up Limit should be 0~1 !!!", "Error Alert", JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Vaccine Up Limit is "+ up_limit);

        String l7 = ve.getText();
        double d7 = Double.parseDouble(l7) ;
        if(d7 < 0 || d7 > 1 ) {
            ve.setText("Input 0~1");
            JOptionPane.showMessageDialog(ve, "Vaccine Efficiency should be 0~1 !!!", "Error Alert",JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Vaccine Efficiency is "+ l7);

        String l8 = vd.getText();
        double d8 = Double.parseDouble(l8) ;
        if(d8 < 0 ) {
            vd.setText("Input > 0");
            JOptionPane.showMessageDialog(vd, "Vaccine Created Day should be > 0 !!!", "Error Alert",JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Vaccine Created Day is "+ l8);

        String l9 = vi.getText();
        double d9 = Double.parseDouble(l9) ;
        if(d9 < d8 ) {
            vi.setText("Input > Created Days");
            JOptionPane.showMessageDialog(vi, "Vaccine Injected Day should be > Created Days !!!", "Error Alert",JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Vaccine Injected Day is "+ l9);

        //JTextField --- Cure
        String l10 = cure.getText();
        double d10 = Double.parseDouble(l10) ;
        if(d10 < 0 || d10 > 1) {
            cure.setText("Input 0~1");
            JOptionPane.showMessageDialog(cure, "Cure Bsic Perc should be 0~1 !!!", "Error Alert", JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Cure Bsic Perc is "+ l10);

        String lnd = cn.getText();
        double dnd = Double.parseDouble(lnd) ;
        if(dnd < 0 ) {
            cn.setText("Input >0");
            JOptionPane.showMessageDialog(cn, "Cure needed day should be >0 !!!", "Error Alert",JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Cure needed day is "+ lnd);


        //JTextField --- Test
        String l11 = tp.getText();
        double d11 = Double.parseDouble(l11) ;
        if(d11 < 0 || d11 > 1) {
            tp.setText("Input 0~1");
            JOptionPane.showMessageDialog(tp, "Test percentage should be 0~1 !!!", "Error Alert",JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Test percentage is "+ l11);

        String l12 = te.getText();
        double d12 = Double.parseDouble(l12) ;
        if(d12 < 0 || d12 > 1) {
            te.setText("Input 0~1");
            JOptionPane.showMessageDialog(te, "Test Efficiency should be 0~1 !!!", "Error Alert", JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Test Efficiency is "+ l12);

        //JTextField --- Trace
        String l13 = tc.getText();
        double d13 = Double.parseDouble(l13) ;
        if(d13 < 0 || d13 > 1) {
            tc.setText("Input 0~1");
            JOptionPane.showMessageDialog(tc, "Trace Percentage should be 0~1 !!!", "Error Alert",JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Trace Percentage is "+ l13);

        String l14 = tre.getText();
        double d14 = Double.parseDouble(l14) ;
        if(d14 < 0 || d14 > 1) {
            tre.setText("Input 0~1");
            JOptionPane.showMessageDialog(tre, "Trace Efficiency should be 0~1 !!!", "Error Alert", JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Trace Efficiency is "+ l14);

        //JTextField --- Barrier
        String l15 = bs.getText();
        double d15 = Double.parseDouble(l15) ;
        if(d15 < 0 || d15 > 1) {
            bs.setText("Input 0~1");
            JOptionPane.showMessageDialog(bs, "Barrier quarantine suspectible should be 0~1 !!!", "Error Alert",JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Barrier quarantine suspectible is "+ l15);

        String l16 = bp.getText();
        double d16 = Double.parseDouble(l16) ;
        if(d16 < 0 || d16 > 1) {
            bp.setText("Input 0~1");
            JOptionPane.showMessageDialog(bp, "Barrier Patient should be 0~1 !!!", "Error Alert", JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Barrier Patient is "+ l16);

        String l17 = bv.getText();
        double d17 = Double.parseDouble(l17) ;
        if(d17 < 0 || d17 > 1) {
            bv.setText("Input 0~1");
            JOptionPane.showMessageDialog(bv, "Barrier Ventilation should be 0~1 !!!", "Error Alert",JOptionPane.WARNING_MESSAGE);
            throw new IllegalArgumentException("Invalid value");
        }
        System.out.println("Barrier Ventilation is "+ l17);


    }

    /**
     * Build a panel for settings
     * @return
     */
    public JPanel getControlPanel() {

        controlPanel = new JPanel();
        controlPanel.setBackground(Color.DARK_GRAY);
        controlPanel.setLayout(f);
        controlPanel.setPreferredSize(new Dimension(1000,300));

        frame.add(controlPanel);

        //JTextField --- Covid19 R0
        rc = new JLabel("  Covid19 R0: ");
        rc.setForeground(Color.white);
        controlPanel.add(rc);
        r0c= new JTextField();
        r0c.setForeground(Color.gray);
        r0c.setText("Input 2-3");
        r0c.setPreferredSize(new Dimension(120,24));
        controlPanel.add(r0c);

        //JTextField --- SARS R0
        rs = new JLabel("SARS R0: ");
        rs.setForeground(Color.white);
        controlPanel.add(rs);
        r0s= new JTextField();
        r0s.setForeground(Color.gray);
        r0s.setText("Input 1.8-3.6");
        r0s.setPreferredSize(new Dimension(120,24));
        controlPanel.add(r0s);

        //JTextField --- Initial case
        initialLabel= new JLabel("Initial Case: ");
        initialLabel.setForeground(Color.white);
        controlPanel.add(initialLabel);
        initialCase = new JTextField();
        initialCase .setForeground(Color.gray);
        initialCase .setText("Input 0-1000");
        initialCase .setPreferredSize(new Dimension(115,24));
        controlPanel.add(initialCase);

        //JTextField --- Population
        populationLabel= new JLabel("Population: ");
        populationLabel.setForeground(Color.white);
        controlPanel.add(populationLabel);
        population = new JTextField();
        population .setForeground(Color.gray);
        population .setText("Input > 1000");
        population .setPreferredSize(new Dimension(115,24));
        controlPanel.add(population);
        JLabel b1 = new JLabel("   ");
        b1.setPreferredSize(new Dimension(149,24));
        controlPanel.add(b1);

        //JTextField --- Mask
        m =  new JLabel("Mask: ");
        m.setForeground(Color.white);
        controlPanel.add(m);
        meLabel = new JLabel("* Effectiveness  ");
        meLabel.setForeground(Color.white);
        controlPanel.add(meLabel);
        me = new JTextField();
        me.setForeground(Color.gray);
        me .setText("Input 0-1");
        me .setPreferredSize(new Dimension(100,24));
        controlPanel.add(me);

        mpLabel = new JLabel("* Up Limit   ");
        mpLabel.setForeground(Color.white);
        controlPanel.add(mpLabel);
        mp = new JTextField();
        mp.setForeground(Color.gray);
        mp .setText("Input 0-1");
        mp .setPreferredSize(new Dimension(100,24));
        controlPanel.add(mp);
        JLabel b2 = new JLabel("   ");
        b2.setPreferredSize(new Dimension(518,24));
        controlPanel.add(b2);

        //JTextField --- Vaccine
        v =  new JLabel(" Vaccine: ");
        v.setForeground(Color.white);
        controlPanel.add(v);
        vlLabel = new JLabel("* Up Limit      ");
        vlLabel.setForeground(Color.white);
        controlPanel.add(vlLabel);
        vl = new JTextField();
        vl.setForeground(Color.gray);
        vl.setText("Input 0-1");
        vl.setPreferredSize(new Dimension(100,24));
        controlPanel.add(vl);

        veLabel = new JLabel("* Efficiency  ");
        veLabel.setForeground(Color.white);
        controlPanel.add(veLabel);
        ve = new JTextField();
        ve.setForeground(Color.gray);
        ve.setText("Input 0-1");
        ve.setPreferredSize(new Dimension(100,24));
        controlPanel.add(ve);

        vdLabel= new JLabel("* Created Day");
        vdLabel.setForeground(Color.white);
        controlPanel.add(vdLabel);
        vd = new JTextField();
        vd.setForeground(Color.gray);
        vd.setText("Input > 0");
        vd.setPreferredSize(new Dimension(120,24));
        controlPanel.add(vd);

        viLabel= new JLabel("* Injected Day");
        viLabel.setForeground(Color.white);
        controlPanel.add(viLabel);
        vi = new JTextField();
        vi.setForeground(Color.gray);
        vi.setText(" > Created Day ");
        vi.setPreferredSize(new Dimension(120,24));
        controlPanel.add(vi);
        JLabel b3 = new JLabel("    ");
        b3.setPreferredSize(new Dimension(90,24));
        controlPanel.add(b3);

        //JTextField --- Test
        t =  new JLabel(" Test: ");
        t.setForeground(Color.white);
        controlPanel.add(t);
        tpLabel= new JLabel("* Pecentage       ");
        tpLabel.setForeground(Color.white);
        controlPanel.add(tpLabel);
        tp = new JTextField();
        tp.setForeground(Color.gray);
        tp.setText("Input 0-1 ");
        tp.setPreferredSize(new Dimension(100,24));
        controlPanel.add(tp);

        teLabel = new JLabel(" * Efficiency ");
        teLabel.setForeground(Color.white);
        controlPanel.add(teLabel);
        te = new JTextField();
        te.setForeground(Color.gray);
        te.setText("Input 0-1 ");
        te.setPreferredSize(new Dimension(100,24));
        controlPanel.add(te);
        JLabel b4 = new JLabel("    ");
        b4.setPreferredSize(new Dimension(518,24));
        controlPanel.add(b4);

        //JTextField --- Trace
        trace =  new JLabel(" Trace: ");
        trace.setForeground(Color.white);
        controlPanel.add(trace);
        tcLabel = new JLabel("*Pecentage       ");
        tcLabel.setForeground(Color.white);
        controlPanel.add(tcLabel);
        tc = new JTextField();
        tc.setForeground(Color.gray);
        tc.setText("Input 0-1 ");
        tc.setPreferredSize(new Dimension(100,24));
        controlPanel.add(tc);

        treLabel = new JLabel("* Efficiency ");
        treLabel.setForeground(Color.white);
        controlPanel.add(treLabel);
        tre = new JTextField();
        tre.setForeground(Color.gray);
        tre.setText("Input 0-1 ");
        tre.setPreferredSize(new Dimension(100,24));
        controlPanel.add(tre);
        JLabel b5 = new JLabel("    ");
        b5.setPreferredSize(new Dimension(525,24));
        controlPanel.add(b5);

        //JTextField --- Barrier
        b = new JLabel(" Barrier: ");
        b.setForeground(Color.white);
        controlPanel.add(b);
        bsLabel = new JLabel("* Suspectible  ");
        bsLabel.setForeground(Color.white);
        controlPanel.add(bsLabel);
        bs = new JTextField();
        bs.setForeground(Color.gray);
        bs.setText("Input 0-1 ");
        bs.setPreferredSize(new Dimension(100,24));
        controlPanel.add(bs);

        bpLabel = new JLabel("* Patient     ");
        bpLabel.setForeground(Color.white);
        controlPanel.add(bpLabel);
        bp = new JTextField();
        bp.setForeground(Color.gray);
        bp.setText("Input 0-1 ");
        bp.setPreferredSize(new Dimension(100,24));
        controlPanel.add(bp);

        bvLabel = new JLabel("* Ventilation ");
        bvLabel.setForeground(Color.white);
        controlPanel.add(bvLabel);
        bv = new JTextField();
        bv.setForeground(Color.gray);
        bv.setText("Input 0-1 ");
        bv.setPreferredSize(new Dimension(100,24));
        controlPanel.add(bv);
        JLabel b6 = new JLabel("    ");
        b6.setPreferredSize(new Dimension(325,24));
        controlPanel.add(b6);

        //JTextField --- Cure
        c = new JLabel("Cure: ");
        c.setForeground(Color.white);
        controlPanel.add(c);
        cureLabel = new JLabel("* Cure Bsic Perc ");
        cureLabel.setForeground(Color.white);
        controlPanel.add( cureLabel);
        cure = new JTextField();
        cure.setForeground(Color.gray);
        cure.setText("Input 0-1 ");
        cure.setPreferredSize(new Dimension(100,24));
        controlPanel.add(cure);

        cnLabel = new JLabel("* Cure Needed Days ");
        cnLabel.setForeground(Color.white);
        controlPanel.add(cnLabel);
        cn = new JTextField();
        cn.setForeground(Color.gray);
        cn.setText("Input > 0 ");
        cn.setPreferredSize(new Dimension(100,24));
        controlPanel.add(cn);
        JLabel b7 = new JLabel("    ");
        b7.setPreferredSize(new Dimension(454,24));
        controlPanel.add(b7);

        frame.validate();
        frame.setVisible(true);

        return controlPanel;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {

        UI myApp = new UI();
        System.out.println("UI is existing !!!");

    }
}

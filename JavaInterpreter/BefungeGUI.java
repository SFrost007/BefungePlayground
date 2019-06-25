import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.URL;
import java.awt.font.*;

public class BefungeGUI extends JPanel implements ActionListener{

    private JPanel main = new JPanel();
    int xChars = 70, yChars = 30;
    private static JMenuBar menuBar = new JMenuBar();
      private static JMenu fileMen = new JMenu("File");
      private static JMenu editMen = new JMenu("Edit");
      private static JMenu progMen = new JMenu("Program");
      private static JMenu helpMen = new JMenu("Help");
      
    private JPanel topPan = new JPanel(new BorderLayout());
    private JPanel toolbarPan = new JPanel();
    private JToolBar toolBar = new JToolBar("Toolbar");
      
    private JSplitPane topbotSplit;
    private JSplitPane leftrightSplit;
    private JTextArea outputArea = new JTextArea(3,10);
    private JScrollPane outputScroll = new JScrollPane(outputArea);
    private JTextArea progArea = new JTextArea(10,10);
    private JScrollPane progScroll = new JScrollPane(progArea);
    private JTextArea stackArea = new JTextArea(10,10);
    private JScrollPane stackScroll = new JScrollPane(stackArea);
    
    private JPanel southPan = new JPanel(new BorderLayout());
    private JLabel statusLbl = new JLabel("Status: OK");
    private befEngine prog = new befEngine();
    
    
    public BefungeGUI(){
        //////////////////////
        // Setup splitpanes //
        //////////////////////
        setLayout(new BorderLayout());
        stackArea.setText("Stack window");
        stackArea.setFont(new Font("Monospaced",1,12));
        stackArea.setEditable(false);
        stackScroll.setMinimumSize(new Dimension(200,300));
        progArea.setText("Program window");
        progArea.setFont(new Font("Monospaced",1,12));
        progScroll.setMinimumSize(new Dimension(400,300));
        outputArea.setText("Output window");
        outputArea.setFont(new Font("Monospaced",1,12));
        outputArea.setEditable(false);
        outputScroll.setMinimumSize(new Dimension(700,80));
        leftrightSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,progScroll,stackScroll);
        leftrightSplit.setOneTouchExpandable(true);
        leftrightSplit.setPreferredSize(new Dimension(500,700));
        topbotSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT,leftrightSplit,outputScroll);
        
        ///////////////////
        // Setup toolbar //
        ///////////////////
        add(toolbarPan,"North");
        toolBar.add(makeToolbarButton("general","New24","newMen","New Program"));
        toolBar.add(makeToolbarButton("general","Open24","openMen","Open Program"));
        toolBar.add(makeToolbarButton("general","Save24","saveMen","Save Program"));
        toolBar.add(new JToolBar.Separator());
        toolBar.add(makeToolbarButton("general","Cut24","cutMen","Cut"));
        toolBar.add(makeToolbarButton("general","Copy24","copyMen","Copy"));
        toolBar.add(makeToolbarButton("general","Paste24","pasteMen","Paste"));
        toolBar.add(new JToolBar.Separator());
        toolBar.add(makeToolbarButton("media","Play24","runMen","Run Program"));
        toolBar.add(makeToolbarButton("media","StepForward24","stepMen","Step Forward"));
        toolBar.add(makeToolbarButton("media","Stop24","resetMen","Reset"));
        toolBar.setFloatable(false);
        toolbarPan.add(toolBar);
        add(topbotSplit,"Center");
        
        southPan.add(statusLbl,"West");
        add(southPan,"South");
    }
    
    
    
    
    ///////////////////////////
    // ActionListener method //
    ///////////////////////////
    
    public void actionPerformed(ActionEvent e){
        if(e.getActionCommand() == "runMen"){
            prog = new befEngine(progArea.getText(),xChars,yChars);
            progArea.setEnabled(false);
            prog.run();
            stackArea.setText(prog.stack.toString());
            outputArea.setText(prog.output);
            progArea.setEnabled(true);
        }else if(e.getActionCommand() == "newMen"){
            if(progArea.getText() != ""){
                int temp=JOptionPane.showConfirmDialog(null,"Are you sure you want to create a blank program?","Are you sure?",JOptionPane.YES_NO_OPTION);
                if(temp==0){
                    progArea.setText("");
                }
            }
        }else if(e.getActionCommand() == "stepMen"){
            if(prog.isRunning()==false) prog = new befEngine(progArea.getText(),xChars,yChars);
            prog.step();
            stackArea.setText(prog.stack.toString());
            outputArea.setText(prog.output);
            if(prog.isRunning()==false){
                progArea.setEnabled(true);
                statusLbl.setText("Status: OK");
            }else{
                statusLbl.setText("Status: Running");
            }            
        }else if(e.getActionCommand() == "resetMen"){
            prog.stop();
            progArea.setEnabled(true);
            stackArea.setText("Stack window");
            outputArea.setText("Output window");
            statusLbl.setText("Status: OK");
        }else if(e.getActionCommand() == "exitMen"){
            System.exit(0);
        }
    }
    

    
    ///////////////////////////////////////
    // Menu and toolbar Creation methods //
    ///////////////////////////////////////
    
    protected JButton makeToolbarButton(String cat, String imageName, String actionCommand, String altText) {
        String imgLocation = "toolbarButtonGraphics/"+cat+"/" + imageName + ".gif";
        URL imageURL = BefungeGUI.class.getResource(imgLocation);
        JButton button = new JButton();
        button.setActionCommand(actionCommand);
        button.addActionListener(this);
        button.setToolTipText(altText);
        if (imageURL != null) {
            button.setIcon(new ImageIcon(imageURL, altText));
        }
        return button;
    }
    
    protected JMenuItem makeMenuItem(String cat, String imageName, String actionCommand, String caption,KeyStroke key) {
        String imgLocation = "toolbarButtonGraphics/"+cat+"/" + imageName + ".gif";
        URL imageURL = BefungeGUI.class.getResource(imgLocation);
        JMenuItem menuItem = new JMenuItem(caption);
        menuItem.setActionCommand(actionCommand);
        menuItem.setAccelerator(key);
        menuItem.addActionListener(this);
        if (imageURL != null) {
            menuItem.setIcon(new ImageIcon(imageURL));
        }
        return menuItem;
    }
    
    protected JMenuItem makeMenuItem(String cat, String imageName, String actionCommand, String caption) {
        String imgLocation = "toolbarButtonGraphics/"+cat+"/" + imageName + ".gif";
        URL imageURL = BefungeGUI.class.getResource(imgLocation);
        JMenuItem menuItem = new JMenuItem(caption);
        menuItem.setActionCommand(actionCommand);
        menuItem.addActionListener(this);
        if (imageURL != null) {
            menuItem.setIcon(new ImageIcon(imageURL));
        }
        return menuItem;
    }
    
    public JMenuBar createMenu(){
        menuBar.add(fileMen);
         fileMen.add(makeMenuItem("general","New16","newMen","New",KeyStroke.getKeyStroke(KeyEvent.VK_N,ActionEvent.CTRL_MASK)));
         fileMen.add(makeMenuItem("general","Open16","openMen","Open",KeyStroke.getKeyStroke(KeyEvent.VK_O,ActionEvent.CTRL_MASK)));
         fileMen.add(makeMenuItem("general","Save16","saveMen","Save",KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK)));
         fileMen.addSeparator();
         fileMen.add(makeMenuItem("general",null,"exitMen","Exit"));
        menuBar.add(editMen);
         editMen.add(makeMenuItem("general","Cut16","cutMen","Cut",KeyStroke.getKeyStroke(KeyEvent.VK_X,ActionEvent.CTRL_MASK)));
         editMen.add(makeMenuItem("general","Copy16","copyMen","Copy",KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK)));
         editMen.add(makeMenuItem("general","Paste16","pasteMen","Paste",KeyStroke.getKeyStroke(KeyEvent.VK_V,ActionEvent.CTRL_MASK)));
        menuBar.add(progMen);
         progMen.add(makeMenuItem("media","Play16","runMen","Run"));
         progMen.add(makeMenuItem("media","StepForward16","stepMen","Step Forward"));
         progMen.add(makeMenuItem("media","Stop16","resetMen","Reset"));
        menuBar.add(helpMen);
         helpMen.add(makeMenuItem("general","About16","aboutMen","About"));
        return menuBar;
    }
    
}
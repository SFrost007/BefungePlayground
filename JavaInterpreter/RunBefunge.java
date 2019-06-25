import javax.swing.*;
import java.awt.*;

public class RunBefunge {
       public static void main(String[] args){
           try{
               UIManager.setLookAndFeel(new com.shfarr.ui.plaf.fh.FhLookAndFeel());
               //UIManager.setLookAndFeel(new com.sun.java.swing.plaf.motif.MotifLookAndFeel());
           }catch(UnsupportedLookAndFeelException exception) {
               exception.printStackTrace();
           }
           
           JFrame frame = new JFrame();
           BefungeGUI bef = new BefungeGUI();
           frame.setTitle("Java Befunge");
           frame.setSize(700,500);
           frame.getContentPane().add(bef);
           frame.setJMenuBar(bef.createMenu());
           frame.setLocation(200,150);
           frame.setVisible(true);
       }
}
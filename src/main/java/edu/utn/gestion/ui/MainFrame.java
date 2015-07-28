package edu.utn.gestion.ui;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import org.apache.log4j.Logger;

/**
 *
 * @author gerardo
 */
public class MainFrame extends JFrame {
    private static final Logger LOGGER = Logger.getLogger(MainFrame.class);
    private static final String LOOK_AND_FEEL_DEFAULT_VALUE = "Nimbus";
    private static final MainFrame INSTANCE = new MainFrame();

    /**
        * Creates new form MainFrame
        */
    private MainFrame() {
        this.initLookAndFeel();
        this.initComponents();        
    }
    
    public static MainFrame getInstance() {
        return INSTANCE;
    }

    /**
        * This method is called from within the constructor to initialize the form.
        * WARNING: Do NOT modify this code. The content of this method is always
        * regenerated by the Form Editor.
        */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        optionExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("GestionApp");

        jMenu1.setText("File");

        optionExit.setText("Exit");
        jMenu1.add(optionExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem1.setText("About");
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 707, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 481, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(717, 532));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
        *  Executes the UI.
        * 
        * @param args 
        */
    public void execute(String args[]) {
        this.main(args);
    }
    
    /**
        * @param args the command line arguments
        */
    private void main(String args[]) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                INSTANCE.setVisible(true);
            }
        });
    }
    
    /**
        *  Sets the default look and feel for the application.
        */
    private void initLookAndFeel() {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if (LOOK_AND_FEEL_DEFAULT_VALUE.equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem optionExit;
    // End of variables declaration//GEN-END:variables
}

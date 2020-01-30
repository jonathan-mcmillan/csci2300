
import java.awt.*;
import javax.swing.*;

public class PacMan extends JFrame {

    public PacMan() {
        
        initUI();
    }
    
    private void initUI() {
        
        add (new Board());
        
        setTitle ("PacMan");
        setDefaultCloseOperation (EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo (null);
        setVisible (true);        
    }

    public static void main (String[] args) {

        EventQueue.invokeLater (new Runnable() {
        	public void run() {
        		PacMan ex = new PacMan();
        		ex.setVisible (true);
        	}
        });
    }
}


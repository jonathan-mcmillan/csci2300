import javax.swing.*;
import java.awt.event.*;

public class QuizGUI
{
	
    public static void main (String args[])
    {
        final JFrame frame = new JFrame ("Quiz");
        
        int numCorrect = 0;
        
        JPanel panel = new JPanel ();
        frame.add (panel);

        JButton wButton = new JButton ("Start Quiz");
        panel.add (wButton);

        JLabel text = new JLabel ("This quiz has 6 questions");
        panel.add (text);

        wButton.addActionListener (
        		new ActionListener()
		{
			Object selected;

			public void actionPerformed (ActionEvent e)
			{
				String question = ("Total correct");
				Object options[] = { "Ok"};
				
				selected = JOptionPane.showOptionDialog (
					null,
					question,
					"Results ",
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, null );

				System.out.println ("Chose:  " + selected);	
			}
			
		});
        
        wButton.addActionListener (
        		new ActionListener()
		{
			int qNum = 6;
			Object selected;

			public void actionPerformed (ActionEvent e)
			{
				String question = "T/F:  The earth is flat.";
				Object options[] = { "True", "False" };

				selected = JOptionPane.showOptionDialog (
					null,
					question,
					"Question " + qNum,
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, null );

				System.out.println ("Chose:  " + selected);
			}
			
		});
        
        wButton.addActionListener (
        		new ActionListener()
		{
			int qNum = 5;
			Object selected;

			public void actionPerformed (ActionEvent e)
			{
				String question = "The main keyboard layout on the majority of keyboards is:";
				Object options[] = { "Dvroak", "QWERTY" };

				selected = JOptionPane.showOptionDialog (
					null,
					question,
					"Question " + qNum,
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, null );

				System.out.println ("Chose:  " + selected);
			}
			
		});
        
        wButton.addActionListener (
        		new ActionListener()
		{
			int qNum = 4;
			Object selected;

			public void actionPerformed (ActionEvent e)
			{
				String question = "The current President of the United States is:";
				Object options[] = { "George H.W. Bush", "Barack Obama", "Bill CLinton", "Donald Trump" };
				
				selected = JOptionPane.showOptionDialog (
					null,
					question,
					"Question " + qNum,
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, null );

				System.out.println ("Chose:  " + selected);
			}
			
		});
        
        wButton.addActionListener (
        		new ActionListener()
		{
			int qNum = 3;
			Object selected;

			public void actionPerformed (ActionEvent e)
			{
				String question = "T/F This class is held in room 115";
				Object options[] = { "True", "False" };
				
				selected = JOptionPane.showOptionDialog (
					null,
					question,
					"Question " + qNum,
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, null );

				System.out.println ("Chose:  " + selected);
			}
			
		});
        
        wButton.addActionListener (
        		new ActionListener()
		{
			int qNum = 2;
			Object selected;

			public void actionPerformed (ActionEvent e)
			{
				String question = "2 + 2 =";
				Object options[] = { "1234", "9678", "4", "21" };
				
				selected = JOptionPane.showOptionDialog (
					null,
					question,
					"Question " + qNum,
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, null );

				System.out.println ("Chose:  " + selected);
			}
			
		});
        
        wButton.addActionListener (
        		new ActionListener()
		{
			int qNum = 1;
			Object selected;

			public void actionPerformed (ActionEvent e)
			{
				String question = "Please choose A";
				Object options[] = { "A", "B", "C", "D" };
				
				selected = JOptionPane.showOptionDialog (
					null,
					question,
					"Question " + qNum,
					JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
					null, options, null );

				System.out.println ("Chose:  " + selected);
				
			}
			
			
		});

        
        frame.setSize (200, 200);
        frame.setVisible (true);
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    }
}

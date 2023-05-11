import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

//create class and extend with JFrame
public class Instructions extends JFrame 
{
	private JPanel contentPane;

	public static void main(String[] args)
	{
	  
		EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				//try - catch block
				try 
				{
					//Creates the instructions page
					Instructions frame = new Instructions();
					frame.setVisible(true);					
				} 
				catch (Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Instructions()//constructor 
	{
		//set frame title
		setTitle("Instructions");
		//set default close operation
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//set bounds of the frame
		setBounds(100, 100, 450, 300);
		//create object of JPanel
		contentPane = new JPanel();
		//set border
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		//set ContentPane
		setContentPane(contentPane);
		//set null
		contentPane.setLayout(null);
		
		//create object of JButton and set label on it
		JButton btnNewFrame = new JButton("Play");
		//add actionListener
		btnNewFrame.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent arg0)
			{
			//Once the button is pressed the game frame is created and the player can begin
				Game game = new Game();
				game.setVisible(true);
				//set default close operation
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}
		});
		//set font of the Button
		btnNewFrame.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 12));
		//set bounds of the Button
		btnNewFrame.setBounds(180, 195, 78, 25);
		//add Button into contentPane
		contentPane.add(btnNewFrame);
		
		//set Label in the frame
		JLabel lblThisIsOld = new JLabel("Clown Maze");
		//set foreground color to the label
		lblThisIsOld.setForeground(Color.BLUE);
		//set font of that label
lblThisIsOld.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
		//set bound of the label
		lblThisIsOld.setBounds(127, 82, 239, 39);
		//add label to the contentPane
		contentPane.add(lblThisIsOld);

        //instructions
		//Creates a JTextArea and adds its text
        JTextArea area = new JTextArea("     Controls\nLeft arrow key to turn left\nRight arrow key to turn right\nUp arrow to move forwards\nDown arrow to move backwards");
        //Sets the position of the text area
		area.setBounds(10,150,175,200);
		//Adds the text area
        contentPane.add(area);

        //objective
		//Same process
        JTextArea objective = new JTextArea("     Objective\nAvoid the clown\nFind the key to unlock the door");
        objective.setBounds(270,150,175,200);
        contentPane.add(objective);
	}
}

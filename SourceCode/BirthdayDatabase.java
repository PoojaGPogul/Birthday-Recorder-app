import java.sql.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;

class MainFrame extends JFrame implements ActionListener
{
	JButton add,update,delete,view,exit,search;
	JLabel img;
	
	ImageIcon i;
	JComboBox<String> option;

	public MainFrame()
	{
		setLayout(new BorderLayout());
		setContentPane(new JLabel(new ImageIcon("bg.jpg")));
    		
		i=new ImageIcon("cake.jpg");
		img=new JLabel(i);
		add=new JButton("Store Information");
		update=new JButton("Update Information");
		delete=new JButton("Delete Information");
		view=new JButton("Show Information");

		search=new JButton("Search");
		String str[]={"By Name","By Date"};
		option=new JComboBox<String>(str);

		exit=new JButton("Exit");

		setLayout(null);
		add.setBounds(100,90,200,30);
		update.setBounds(100,150,200,30);
		delete.setBounds(100,210,200,30);
		view.setBounds(100,270,200,30);
	
		search.setBounds(100,330,90,30);
		option.setBounds(200,330,100,30);

		exit.setBounds(100,390,200,30);		
		img.setBounds(200,3,500,500);

		
		add.addActionListener(this);
		update.addActionListener(this);
		delete.addActionListener(this);
		view.addActionListener(this);
		search.addActionListener(this);
		exit.addActionListener(this);

		add(add);
		add(update);
		add(delete);
		add(view);
		add(search);
		add(option);		
		add(exit);
		add(img);

		JLabel title=new JLabel("Birthday Recorder");
		title.setBounds(150,20,400,30);
		title.setFont(new Font("Times New Roman",1,30));
		add(title);

		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent w)
			{
				System.exit(0);
			}
		});
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		try{
		if(ae.getSource()==add)
		{
			AddElements a=new AddElements();
			a.setVisible(true);
			a.setSize(700,500);
			MainFrame.this.setVisible(false);		
		}
		if(ae.getSource()==update)
		{
			UpdateData a=new UpdateData();
			a.setVisible(true);
			a.setSize(700,500);
			MainFrame.this.setVisible(false);
		}
		if(ae.getSource()==delete)
		{
			DeleteData a=new DeleteData();
			a.setVisible(true);
			a.setSize(700,500);
			MainFrame.this.setVisible(false);				
		}
		if(ae.getSource()==view)
		{
			ViewData a=new ViewData();
			a.setVisible(true);
			a.setSize(700,500);
			MainFrame.this.setVisible(false);				
		}
		if(ae.getSource()==search)
		{
			int i=option.getSelectedIndex();
			SearchData a=new SearchData(i);
			a.setVisible(true);
			a.setSize(700,500);
			MainFrame.this.setVisible(false);				
		}
		if(ae.getSource()==exit)
		{
			System.exit(0);
		}}
		catch(Exception e)
		{
			System.out.println(""+e);
		}
	}
}



class BirthdayDatabase extends JFrame
{
	
	static BirthdayDatabase b;
	public BirthdayDatabase()
	{
		MainFrame a=new MainFrame();
		a.setVisible(true);
		a.setSize(800,500);
	}


  
  public static void main(String[] args) throws ClassNotFoundException,SQLException
  {
	b=new BirthdayDatabase();
}	
}





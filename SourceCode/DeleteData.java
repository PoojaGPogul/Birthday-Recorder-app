import java.sql.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Properties;
import java.io.*;


class DeleteData extends JFrame implements ActionListener
{
	JLabel lname;
	JTextField tname;
	JComboBox<String> cmonth;
	JButton button,clear,exit;
	Properties prop;
	FileInputStream fis;
	String dburl,user,pass;
	private static final String dbClassName = "com.mysql.jdbc.Driver";

	public DeleteData() throws Exception
	{
		prop=new Properties();
		fis=new FileInputStream("db.properties");
		prop.load(fis);
		dburl=prop.getProperty("dburl");
		user=prop.getProperty("user");
		pass=prop.getProperty("pass");
		
		setLayout(new BorderLayout());
		setContentPane(new JLabel(new ImageIcon("bg.jpg")));

		lname=new JLabel("Enter Name : ");
		tname=new JTextField("");
		
		button=new JButton("Delete");
		clear=new JButton("Clear");
		exit=new JButton("Exit");

		setLayout(null);
		lname.setBounds(100,100,200,30);
		tname.setBounds(210,100,200,30);

		button.setBounds(100,200,100,30);
		clear.setBounds(210,200,100,30);
		exit.setBounds(320,200,100,30);

		button.addActionListener(this);
		clear.addActionListener(this);
		exit.addActionListener(this);


		add(lname);
		add(tname);
		add(button);
		add(clear);
		add(exit);

		JLabel title=new JLabel("Delete Data");
		title.setBounds(150,20,400,30);
		title.setFont(new Font("Times New Roman",1,30));
		add(title);


		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent w)
			{
				DeleteData.this.setVisible(false);
				MainFrame a=new MainFrame();
				a.setVisible(true);
				a.setSize(700,500);
			}
		});
		

		
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getSource()==button)
		{
			String name=tname.getText();

			//Checks if name contains any digits
			if(name.equals("") || name.contains("0") || name.contains("1") || name.contains("2") || name.contains("3") || name.contains("4") || name.contains("5") || name.contains("6") || name.contains("7") || name.contains("8") || name.contains("9"))
			{
			JOptionPane.showMessageDialog(this,"Please enter proper data...","Incompatiable Data",JOptionPane.INFORMATION_MESSAGE);
			}
			else
			{
				try
				{
				Class.forName(dbClassName);
				Connection c = DriverManager.getConnection(dburl,user,pass);	
				Statement st=c.createStatement();
				String sql="delete from birthday where name='"+name+"'";
				int sss=st.executeUpdate(sql);
				if(sss>0)
				JOptionPane.showMessageDialog(this,"Data deleted...","BirthDay Data",JOptionPane.INFORMATION_MESSAGE);	
				else
				JOptionPane.showMessageDialog(this,"Data not deleted...","BirthDay Data",JOptionPane.INFORMATION_MESSAGE);	
				 }
             			catch(Exception e)
				{
					System.out.println(e);
					JOptionPane.showMessageDialog(this,"Error occured...","Error",JOptionPane.INFORMATION_MESSAGE);
				}
				tname.setText("");
			}
		}
		if(ae.getSource()==clear)
		{
			tname.setText("");
		}

		if(ae.getSource()==exit)
		{
			this.setVisible(false);
			MainFrame a=new MainFrame();
			a.setVisible(true);
			a.setSize(700,500);

		}
	}
}




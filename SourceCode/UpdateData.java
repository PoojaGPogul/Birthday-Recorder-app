import java.sql.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Properties;
import java.io.*;


class UpdateData extends JFrame implements ActionListener
{
	JLabel lname, lbdate;
	JTextField tname,tdate,tyear;
	JComboBox<String> cmonth;
	JButton button,clear,exit;
	Properties prop;
	FileInputStream fis;
	String dburl,user,pass;
	String arr[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};	
  	private static final String dbClassName = "com.mysql.jdbc.Driver";

	public UpdateData() throws Exception
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
		lbdate=new JLabel("Enter DOB to update : ");
		
		tname=new JTextField("");
		tdate=new JTextField("");
		tyear=new JTextField("");

		cmonth=new JComboBox<String>(arr);
		
		button=new JButton("Update");
		clear=new JButton("Clear");
		exit=new JButton("Exit");

		setLayout(null);

		lname.setBounds(100,100,200,30);
		lbdate.setBounds(35,150,200,30);

		tname.setBounds(210,100,200,30);
		tdate.setBounds(210,150,60,30);
		cmonth.setBounds(280,150,60,30);
		tyear.setBounds(350,150,60,30);

		button.setBounds(100,250,100,30);
		clear.setBounds(210,250,100,30);
		exit.setBounds(320,250,100,30);

		button.addActionListener(this);
		clear.addActionListener(this);
		exit.addActionListener(this);


		add(lname);
		add(lbdate);
		add(tname);
		add(tdate);
		add(cmonth);
		add(tyear);
		add(button);
		add(clear);
		add(exit);

		JLabel title=new JLabel("Update Data");
		title.setBounds(150,20,400,30);
		title.setFont(new Font("Times New Roman",1,30));
		add(title);

		
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent w)
			{
				UpdateData.this.setVisible(false);
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
			String day=tdate.getText();
			String m=(String)cmonth.getItemAt(cmonth.getSelectedIndex());
			int month=cmonth.getSelectedIndex();
			String datestr="";
			String year=tyear.getText();
		
			//Checks if name contains any digits
			if(name.equals("") || name.contains("0") || name.contains("1") || name.contains("2") || name.contains("3") || name.contains("4") || name.contains("5") || name.contains("6") || name.contains("7") || name.contains("8") || name.contains("9"))
			{
			JOptionPane.showMessageDialog(this,"Please enter proper data...","Incompatiable Data",JOptionPane.INFORMATION_MESSAGE);
			}

			else
			{
				//Check if day and year fields are empty
				if(day.equals("") ||year.equals(""))
				{
			JOptionPane.showMessageDialog(this,"Please enter proper data...","Incompatiable Data",JOptionPane.INFORMATION_MESSAGE);
				}
				else
				{
					
					//Check if day and year fields contain digits
					if(isNotDigit(day) || isNotDigit(year))
					{
			JOptionPane.showMessageDialog(this,"Please enter proper data...","Incompatiable Data", JOptionPane.INFORMATION_MESSAGE);
			
						return; 
					}
					int dd=Integer.parseInt(day);
					int yy=Integer.parseInt(year);
					boolean flag=false;
					//Check correct no.of days in month
					switch(m)
					{
						case "Mar":
						case "May":
						case "Jul":
						case "Aug":
						case "Oct":
						case "Dec":
						case "Jan":if(dd<1 || dd>31)   flag=true;break;
						case "Feb":if(yy%4!=0)         {  if(dd<1 || dd>28)   flag=true;      }
							   else		       {  if(dd<1 || dd>29)   flag=true;      }  break;
						case "Apr":
						case "Jun":
						case "Sep":
						case "Nov":if(dd<1 || dd>30)   flag=true;break;
					}
									
					int cc=month+1;
					boolean flag1=false;
					try
					{
					//Validate the date
					 datestr=""+yy+"-"+cc+"-"+dd;
					java.sql.Date start=java.sql.Date.valueOf("1901-01-01");
					java.sql.Date cur=java.sql.Date.valueOf(datestr);
	
					if(cur.compareTo(start)==1)
						flag1=false;
					else flag1=true;	
						System.out.println(""+start+"    "+cur+"   "+cur.compareTo(start));
					}
					catch(Exception e)
					{
				JOptionPane.showMessageDialog(this,"Please enter correct date...","Error", JOptionPane.INFORMATION_MESSAGE);
				
					}
					if(flag || flag1)
				JOptionPane.showMessageDialog(this,"Please enter correct date...","Error", JOptionPane.INFORMATION_MESSAGE);

					else
					{
						String toInsert=name+"::"+datestr+"\n";
						try
						{
							Class.forName(dbClassName);
							Connection c = DriverManager.getConnection(dburl,user,pass);	
							Statement st=c.createStatement();
							datestr=dd+"-"+m+"-"+yy;
							String sql="update birthday set date='"+datestr+"' where name='"+name+"'";
							int sss=st.executeUpdate(sql);
							if(sss>0)
				JOptionPane.showMessageDialog(this,"Data updated...","BirthDay Data",JOptionPane.INFORMATION_MESSAGE);	
							else
				JOptionPane.showMessageDialog(this,"Data not updated...","BirthDay Data",JOptionPane.INFORMATION_MESSAGE);	
						
						 }
             					catch(Exception e)
						{
						System.out.println(e);
						JOptionPane.showMessageDialog(this,"Error occured...","Error",JOptionPane.INFORMATION_MESSAGE);
						}
					tname.setText("");
					tdate.setText("");
					tyear.setText("");
					cmonth.setSelectedIndex(0);     
				
						
					}
				}			
			}
		}
		if(ae.getSource()==clear)
		{
			tname.setText("");
			tdate.setText("");
			tyear.setText("");	
			cmonth.setSelectedIndex(0);
		}

		if(ae.getSource()==exit)
		{
			this.setVisible(false);
			MainFrame a=new MainFrame();
			a.setVisible(true);
			a.setSize(700,500);
		}
	}

	public boolean isNotDigit(String name)
	{
		
		for(int i=0;i<name.length();i++)
		{
			char ch=name.charAt(i);
			if((ch>=65 && ch<=90) || (ch>=97 && ch<=122))
				return true;
		}
			return false;
	}

	
	

}




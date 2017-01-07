import java.sql.*;
import java.awt.*;
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Properties;
import java.io.*;


class SearchData extends JFrame implements ActionListener
{
	JLabel lname, lbdate;
	JTextField tname,tdate,tyear;
	JComboBox<String> cmonth;
	JButton button,clear,exit;
	String arr[]={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};	
	int opt=0;

	public SearchData(int opt)
	{
		setLayout(new BorderLayout());
		setContentPane(new JLabel(new ImageIcon("bg.jpg")));

		lname=new JLabel("Enter Name : ");
		lbdate=new JLabel("Enter DOB : ");
		tname=new JTextField("");
		tdate=new JTextField("");
		tyear=new JTextField("");
		cmonth=new JComboBox<String>(arr);
		
		button=new JButton("Search");
		clear=new JButton("Clear");
		exit=new JButton("Exit");

		setLayout(null);
		if(opt==0)
		{
			lname.setBounds(100,150,200,30);
			tname.setBounds(210,150,200,30);
			add(lname);
			add(tname);
		}
		else
		{
			lbdate.setBounds(100,150,200,30);
			tdate.setBounds(210,150,60,30);
			cmonth.setBounds(280,150,60,30);
			tyear.setBounds(350,150,60,30);
			add(lbdate);
			add(tdate);
			add(cmonth);
			add(tyear);
		}
		
		button.setBounds(100,250,100,30);
		clear.setBounds(210,250,100,30);
		exit.setBounds(320,250,100,30);
		button.addActionListener(this);
		clear.addActionListener(this);
		exit.addActionListener(this);

		add(button);		
		add(clear);
		add(exit);

		JLabel title=new JLabel("Search Data");
		title.setBounds(150,20,400,30);
		title.setFont(new Font("Times New Roman",1,30));
		add(title);


		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent w)
			{
				SearchData.this.setVisible(false);
				MainFrame a=new MainFrame();
				a.setVisible(true);
				a.setSize(700,500);
			}
		});
		this.opt=opt;
		
	}
	public void actionPerformed(ActionEvent ae)
	{
		if(ae.getActionCommand().equals("Search"))
		{
			String name,m,year,day,datestr="";
			int month;
			String data="";
			
			//Get use inputted data

			if(opt==0)			
			{
				name=tname.getText();
	
			//Checks if name contains any digits
				if(name.equals("") || name.contains("0") || name.contains("1") || name.contains("2") || name.contains("3") || name.contains("4") || name.contains("5") || name.contains("6") || name.contains("7") || name.contains("8") || name.contains("9"))
				{
				JOptionPane.showMessageDialog(this,"Please enter proper data...","Incompatiable Data",JOptionPane.INFORMATION_MESSAGE);
				return;
				}
				data=name;
			}
			else if(opt==1)			
			{
				day=tdate.getText();
				m=(String)cmonth.getItemAt(cmonth.getSelectedIndex());
				month=cmonth.getSelectedIndex();
				datestr="";
				year=tyear.getText();
	
				//Check if day and year fields are empty
				if(day.equals("") ||year.equals(""))
				{
			JOptionPane.showMessageDialog(this,"Please enter proper data...","Incompatiable Data",JOptionPane.INFORMATION_MESSAGE);
				return;
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

					}
					catch(Exception e)
					{
				JOptionPane.showMessageDialog(this,"Please enter correct date...","Error", JOptionPane.INFORMATION_MESSAGE);
					return;
					}
					if(flag || flag1)
					{
				JOptionPane.showMessageDialog(this,"Please enter correct date...","Error", JOptionPane.INFORMATION_MESSAGE);
					return;
					}
					data=dd+"-"+m+"-"+yy;
				}						
			}
			try{
			this.setVisible(false);
			ViewSearchData a=new ViewSearchData(opt,data);
			a.setVisible(true);
			a.setSize(700,500);
			}
			catch(Exception e)
			{
				System.out.println(""+e);
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



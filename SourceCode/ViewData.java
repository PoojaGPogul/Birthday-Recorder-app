import java.sql.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.table.*;
import java.io.*;



public class ViewData  extends JFrame
{
	ResultSet rs;
	ResultSetMetaData rsmd;
	JTable jtable;
	Connection con;
	Statement st1;
	Container c;
	Properties prop;
	FileInputStream fis;
	String dburl,user,pass;

	private static final String dbClassName = "com.mysql.jdbc.Driver";
	
	JScrollPane s;
	
	public ViewData() throws Exception
	{
		prop=new Properties();
		fis=new FileInputStream("db.properties");
		prop.load(fis);
		dburl=prop.getProperty("dburl");
		user=prop.getProperty("user");
		pass=prop.getProperty("pass");



		c=getContentPane();
		try
      		 {	
			Class.forName(dbClassName);
			con=DriverManager.getConnection(dburl,user,pass);	
		   	st1=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE);
			String str="";		 
	 
		   	rs=st1.executeQuery("select * from birthday;");
		   	rs.first();
		   	jtable=new JTable(build(rs));
			s=new JScrollPane(jtable);
			c.add(s);
			rs.close();
			st1.close();
			con.close();
		  }
		  catch(Exception e)
		  {
			 
 			  JOptionPane.showMessageDialog(this,"Table is empty...","Caution!",JOptionPane.INFORMATION_MESSAGE);
				ViewData.this.setVisible(false);
				MainFrame a=new MainFrame();
				a.setVisible(true);
				a.setSize(700,500);
			  System.out.println(e);
			
		  }
				
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent w)
			{
				ViewData.this.setVisible(false);
				MainFrame a=new MainFrame();
				a.setVisible(true);
				a.setSize(700,500);
			}
		});
	}
		
	


	public DefaultTableModel build(ResultSet rs) throws SQLException 
	{

		ResultSetMetaData metaData = rs.getMetaData();

	    // names of columns
	    Vector<String> columnNames = new Vector<String>();
	    int columnCount = metaData.getColumnCount();
	    for (int column = 1; column <= columnCount; column++) 
	    {
	    	    columnNames.add(metaData.getColumnName(column));
    	    }
		columnNames.setElementAt("Name",0);
		columnNames.setElementAt("Date Of Birth",1);
	
		rs.first();
	   
	 // data of the table
	    Vector<Vector<Object>> data = new Vector<Vector<Object>>();
	    int iiii=0;
	    Vector<Object> vector1 = new Vector<Object>();
            for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
	    {
            	vector1.add("        "+rs.getObject(columnIndex));
            } 
        	data.add(vector1);

	 while (rs.next()) 
	{
		 iiii=1;
        	Vector<Object> vector = new Vector<Object>();
        	for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
		{
            		vector.add("        "+rs.getObject(columnIndex));
       		}
	        data.add(vector);
    	}
	return new DefaultTableModel(data, columnNames);

}



			
}

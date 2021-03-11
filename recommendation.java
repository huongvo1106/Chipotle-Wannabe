import javax.swing.JOptionPane;
import java.sql.*;
import java.util.*;
import java.util.Comparator;

public class recommendation 
{
	//Initializers
	dbSetup my = new dbSetup();
	Connection conn = null;
	recommendation()
	{
		
	    // clone connection
	    try
	    {
	      Class.forName("org.postgresql.Driver");
	      conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db905_group17_project2", my.user,
	        my.pswd);
	    }
	    catch (Exception e) 
	    {
	      e.printStackTrace();
	      System.err.println(e.getClass().getName() + ": " + e.getMessage());
	      System.exit(0);

	    } // end try catch
	}
	String getName(String type, Integer num, Connection conn)
	{
		try 
		{
			String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Item_ID\" = '"+type+(num+1)+"'";
			Statement stmt = conn.createStatement();
			ResultSet e_name = stmt.executeQuery(sqlStatement);
			e_name.next();
			String name = e_name.getString("Name");
			//System.out.println("Name of "+ type+(num+1) + "=" + name);
			return name;
			
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Failed to get Name of "+ type+(num+1));
			e.printStackTrace();
		}
		
		return null;
	}

	Integer getStock(String item, Connection conn) //Get stock of an item
	{
		try 
		{
			String sqlStatement = "SELECT \"Stock\" FROM \"Menu\" WHERE \"Name\" = '" + item + "'";
			Statement stmt = conn.createStatement();
			ResultSet e_stock = stmt.executeQuery(sqlStatement);
			e_stock.next();
			Integer stock = e_stock.getInt("Stock");
			//System.out.println("Stock of "+ item + "=" + stock);
			return stock;
			
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Failed to get Stock of "+ item);
			e.printStackTrace();
		}
		
		return 0;
	}
	
	Double getPrice(String item, Connection conn) //Get price of an item
	{
		try 
		{
			String sqlStatement = "SELECT \"Price\" FROM \"Menu\" WHERE \"Name\" = '" + item + "'";
			Statement stmt = conn.createStatement();
			ResultSet e_price = stmt.executeQuery(sqlStatement);
			e_price.next();
			Double price = e_price.getDouble("Price");
			//System.out.println("price of "+ item + "=" + price);
			return price;
			
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			System.out.println("Failed to get price of "+ item);
			e.printStackTrace();
		}
		
		return 0.0;
	}
	
	Integer getTypeSize(String type, Connection conn) //Get size of a type "D" for Drink or "E" for Entree etc.
	{
		if(type == "D")
			type = "Dessert";
		else if(type == "E")
			type = "Entree";
		else if(type == "B")
			type = "Drink";
		else if(type == "S")
			type = "Side";
		try 
		{
			String sqlStatement = "SELECT * FROM \"Menu\" WHERE \"Type\" = '" + type + "'";
			Statement stmt = conn.createStatement();
			ResultSet e_num = stmt.executeQuery(sqlStatement);
			Integer num = 0;
			while (e_num.next())
            {
              num++;
            }
			//System.out.println("Number of "+ type + "=" + num);
			return num;
			
		} catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	ArrayList<String> getHighestStock(String type, Connection conn) //returns items with highest stock
	{
		ArrayList<String> items = new ArrayList<String>();
		ArrayList<Integer> stocks = new ArrayList<Integer>();
		Integer numItems = getTypeSize(type, conn);
		for(Integer i = 0; i < numItems; i++)
		{
			String name = getName(type, i, conn);
			//System.out.println(name);
			items.add(name);
			stocks.add(getStock(name,conn));
			
		}
		ArrayList<String> highest_stocks = new ArrayList<String>();
		Integer max = Collections.max(stocks);
		if (max == 0)
		{
			return null;
		}
		//System.out.println(max);
		for(Integer i = 0; i < getTypeSize(type, conn); i++)
		{
			String name = items.get(i);//(type, i, conn);
			Integer stock = stocks.get(i);//(name,conn);
			//System.out.println(stock);
			if(stock == max)
			{
				//System.out.println("here");
				highest_stocks.add(name);
			}
		}
		return highest_stocks;
	}
	
	ArrayList<String> getHighestPrice(String type, Connection conn) //Returns items with highest price
	{
		ArrayList<String> items = new ArrayList<String>();
		ArrayList<Double> prices = new ArrayList<Double>();
		Integer numItems = getTypeSize(type, conn);
		for(Integer i = 0; i < numItems; i++)
		{
			String name = getName(type, i, conn);
			//System.out.println(name);
			items.add(name);
			prices.add(getPrice(name,conn));
			
		}
		ArrayList<String> highest_prices = new ArrayList<String>();
		Double max = Collections.max(prices);
		for(Integer i = 0; i < getTypeSize(type, conn); i++)
		{
			String name = items.get(i);//(type, i, conn);
			Double price = prices.get(i);//(name,conn);
			//System.out.println(price);
			if(price - max==0)
			{
				//System.out.println("here");
				highest_prices.add(name);
			}
		}
		return highest_prices;
	}
	
	ArrayList<String> recToCustomer(String type, Integer priority, Connection conn) //Recommends items to customers depending on type
	{
		ArrayList<String> recommended = new ArrayList<String>();
		ArrayList<String> stocks = getHighestStock(type, conn); //list with highest stocks
		ArrayList<String> price = getHighestPrice(type, conn); // list with highest price
		
		if(stocks == null)
		{
			recommended.add("Out of Stock, Nothing to Recommend");
			return recommended;
		}
		ArrayList<String> common = new ArrayList<String>(stocks);
		common.retainAll(price);
		
		if(!common.isEmpty())
		{
			recommended = common; //Return it if item has highest price or stock
		}
		else if(priority == 0)
		{
			recommended = stocks;
		}
		else if(priority == 1)
		{
			recommended = price;
		}
		
		return recommended;
	}
	
	
}
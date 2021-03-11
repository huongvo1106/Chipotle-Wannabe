import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.awt.*;
import java.sql.*;
import java.util.*;

//import java.sql.DriverManager;

/*
CSCE 315
9-25-2019
 */
public class jdbcpostgreSQLGUI
{
  public static void main(String args[])
  {
    dbSetup my = new dbSetup();
    // Building the connection
    Connection conn = null;

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

    JFrame f= new JFrame("Chipotle Wannabe POS");
    f.setSize(800, 500);
    f.setLayout(null);
    f.setVisible(true);
    f.setResizable(false);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    f.setLocation(dim.width/2-f.getSize().width/2, dim.height/2-f.getSize().height/2);
    f.getContentPane().setBackground(new Color(198, 235, 190));

    String[] buttons = { "Admin", "Customer" };  // user selection
    int answer = JOptionPane.showOptionDialog(f, "Are you a customer or admin?", "Chipotle Wannabe",
      JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);

    String sqlStatement = "";
    boolean cont = true;

    if (answer == 1)  // Customer
    {
      Double price = 0.00;  // total price of order
      String c_name = JOptionPane.showInputDialog(f, "Customer Info \nName: ");  // get customer name

      // Order is filled in the following order: entree, side, drink, dessert
      ArrayList<Integer> entree = new ArrayList<Integer>();
      ArrayList<Integer> side = new ArrayList<Integer>();
      ArrayList<Integer> drink = new ArrayList<Integer>();
      ArrayList<Integer> dessert = new ArrayList<Integer>();
      ArrayList<String> orderedItems = new ArrayList<String>();

      try
      {
        while (cont == true)  // continue ordering more items
        {
          String[] food_choice = { "Entree", "Side", "Drink", "Dessert" };
          int item_type = JOptionPane.showOptionDialog(f, "What do you want to order?", "Menu",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, food_choice, food_choice[0]);
          String menu_item = "";

          if (item_type == 0)  // order entree
          {
            // create a statement object
            Statement stmt = conn.createStatement();
            // create an SQL statement
            sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Entree\' ORDER BY \"Item_ID\"";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            // retrieve all menu items that are entrees
            ArrayList<String> arr = new ArrayList<String>();
            while (result.next())
            {
              menu_item = result.getString("Name");
              arr.add(menu_item);
            }

            // store entree menu items in array list to display as buttons
            String[] entree_items = new String[arr.size()];
            for (int i = 0; i < arr.size(); i++)
            {
              entree_items[i] = arr.get(i);
            }

            // display entree item buttons
            int e_choice = JOptionPane.showOptionDialog(f, "Select entree: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, entree_items, entree_items[0]);

            entree.add(e_choice+1);  // store the customer's entree choice
            orderedItems.add(entree_items[e_choice]);

            Statement stmt1 = conn.createStatement();
            sqlStatement = "SELECT \"Price\" FROM \"Menu\" WHERE \"Item_ID\" = 'E"+(e_choice+1)+"'";
            ResultSet e_price = stmt1.executeQuery(sqlStatement);

            while (e_price.next())
            { price += e_price.getDouble("Price"); }
          } 
          else if (item_type == 1)  // order side
          {
            // create a statement object
            Statement stmt = conn.createStatement();
            // create an SQL statement
            sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Side\'";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            // retrieve all menu items that are sides
            ArrayList<String> arr2 = new ArrayList<String>();
            while (result.next())
            {
              menu_item = result.getString("Name");
              arr2.add(menu_item);
            }

            // store side menu items in array list to display as buttons
            String[] side_items = new String[arr2.size()];
            for (int i = 0; i < arr2.size(); i++)
            {
              side_items[i] = arr2.get(i);
            }

            // display side item buttons
            int s_choice = JOptionPane.showOptionDialog(f, "Select side: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, side_items, side_items[0]);
            
            side.add(s_choice+1);  // store the customer's side choice
            orderedItems.add(side_items[s_choice]);

            Statement stmt1 = conn.createStatement();
            sqlStatement = "SELECT \"Price\" FROM \"Menu\" WHERE \"Item_ID\" = 'S"+(s_choice+1)+"'";
            ResultSet result1 = stmt1.executeQuery(sqlStatement);

            while (result1.next())
            { price += result1.getDouble("Price"); }
          }
          else if (item_type == 2)  // order drink
          {
            // create a statement object
            Statement stmt = conn.createStatement();
            // create an SQL statement
            sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Drink\'";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            // retrieve all side items from menu table
            ArrayList<String> arr3 = new ArrayList<String>();
            while (result.next())
            {
              menu_item = result.getString("Name");
              arr3.add(menu_item);
            }

            // store side menu items in array list to display as buttons
            String[] drink_items = new String[arr3.size()];
            for (int i = 0; i < arr3.size(); i++)
            {
              drink_items[i] = arr3.get(i);
            }

            int b_choice = JOptionPane.showOptionDialog(f, "Select a drink: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, drink_items, drink_items[0]);

            drink.add(b_choice+1);  // store customer's drink choice
            orderedItems.add(drink_items[b_choice]);

            Statement stmt1 = conn.createStatement();
            sqlStatement = "SELECT \"Price\" FROM \"Menu\" WHERE \"Item_ID\" = 'B"+(b_choice+1)+"'";
            ResultSet result1 = stmt1.executeQuery(sqlStatement);

            while (result1.next())
            { price += result1.getDouble("Price"); }
          } 
          else if (item_type == 3)  // order dessert
          {
            // create a statement object
            Statement stmt = conn.createStatement();
            // create an SQL statement
            sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Dessert\'";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            // retrieve all side items from menu table
            ArrayList<String> arr4 = new ArrayList<String>();
            while (result.next())
            {
              menu_item = result.getString("Name");
              arr4.add(menu_item);
            }

            // store side menu items in array list to display as buttons
            String[] dessert_items = new String[arr4.size()];
            for (int i = 0; i < arr4.size(); i++)
            {
              dessert_items[i] = arr4.get(i);
            }

            int d_choice = JOptionPane.showOptionDialog(f, "Select dessert: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, dessert_items, dessert_items[0]);

            dessert.add(d_choice+1);    // store customer's dessert choice
            orderedItems.add(dessert_items[d_choice]);

            Statement stmt1 = conn.createStatement();
            sqlStatement = "SELECT \"Price\" FROM \"Menu\" WHERE \"Item_ID\" = 'D"+(d_choice+1)+"'";
            ResultSet result1 = stmt1.executeQuery(sqlStatement);

            while (result1.next())
            { price += result1.getDouble("Price"); }
          }

          // Continue the order
          String[] moreDone = { "Yes", "No" };
          int md = JOptionPane.showOptionDialog(f, "Total Price: "+price+"\nWould you like to order more?", null, JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);

          if (md == 1)  // done ordering
          {
        	  do
        	  {
        		  // display order and price here
            	  for (int i = 0; i < orderedItems.size(); i++)
            	  {
            		  System.out.println(orderedItems.get(i));
            	  }
            	  
                  String [] addRemove = {"Add", "Remove", "Complete Order"};
                  md = JOptionPane.showOptionDialog(f, "Would you like to add or remove any items?", null, JOptionPane.DEFAULT_OPTION,
                  JOptionPane.QUESTION_MESSAGE, null, addRemove, addRemove[0]);
                  
                  if (md == 1)  // remove items
                  {
                	  String ordered[] = new String[orderedItems.size()];              
                	  for(int j =0;j<orderedItems.size();j++){
                		  ordered[j] = orderedItems.get(j);
            		  }

                      int remove_choice = JOptionPane.showOptionDialog(f, "Which items would you like to remove?", "Menu", JOptionPane.DEFAULT_OPTION,
                              JOptionPane.QUESTION_MESSAGE, null, ordered, ordered[0]);
                      System.out.println("Remove choice is: " + remove_choice);
                  }
                  else if (md == 2)  // complete order
                  {
                	  cont = false;
                  }
                  
        	  } while (md == 1);
          }
        } // end while loop

        Array e_arr = conn.createArrayOf("INTEGER", entree.toArray());
        Array s_arr = conn.createArrayOf("INTEGER", side.toArray());
        Array b_arr = conn.createArrayOf("INTEGER", drink.toArray());
        Array d_arr = conn.createArrayOf("INTEGER", dessert.toArray());

        // GET NEXT ORDER ID
        int  orderID = 0;
        Statement stmt2 = conn.createStatement();
        sqlStatement = "SELECT \"Order_ID\" FROM \"Orders\" ORDER BY \"Order_ID\" DESC LIMIT 1";
        ResultSet result2 = stmt2.executeQuery(sqlStatement); 
        while (result2.next())
        {
        	orderID = result2.getInt("Order_ID") + 1;
        }

        // PLACE ORDER
        Statement stmt1 = conn.createStatement();
        sqlStatement = "INSERT INTO \"Orders\" (\"Order_ID\", \"Name\", \"Date\", \"Price_Total\", \"Entree\", \"Sides\", \"Desert\", \"Beverage\") VALUES ('"+orderID+"', '"+c_name+"', '"+java.time.LocalDate.now()+"', '"+price+"', '"+e_arr+"', '"+s_arr+"', '"+d_arr+"', '"+b_arr+"')";
        stmt1.executeUpdate(sqlStatement);

        // print out the order
        //print out entree
        
        Statement stm = conn.createStatement();
        String sql = "SELECT * FROM \"Orders\" ORDER BY \"Order_ID\" DESC LIMIT 1";
        ResultSet r = stm.executeQuery(sql);
        String summarize = "";
        summarize += "Customer's name: " + r.getString(4) + "\n";
        System.out.println("check");
        for(int s : entree){
          
          //create statement object
          Statement stmt3 = conn.createStatement();
          // create sql statement
          String sqlStatement1 = "SELECT \"Name\" FROM \"Menu\" WHERE \"Item_ID\" = \'E"+s+"\'";
          //create send statement to DBMS
          ResultSet result = stmt3.executeQuery(sqlStatement1);
          String value = "";
          while(result.next()){
            value = result.getString(1);
            System.out.println(value);
          }
          summarize += value + "\n"; 
        }
        //print out side items
        for(int s : side){
          
          //create statement object
          Statement stmt3 = conn.createStatement();
          // create sql statement
          String sqlStatement1 = "SELECT \"Name\" FROM \"Menu\" WHERE \"Item_ID\" = \'S"+s+"\'";
          //create send statement to DBMS
          ResultSet result = stmt3.executeQuery(sqlStatement1);
          String value = "";
          while(result.next()){
            value = result.getString(1);
            System.out.println(value);
          }
          summarize += value + "\n"; 
        }
        //print out drink
        for(int s : drink){
          
          //create statement object
          Statement stmt3 = conn.createStatement();
          // create sql statement
          String sqlStatement1 = "SELECT \"Name\" FROM \"Menu\" WHERE \"Item_ID\" = \'B"+s+"\'";
          //create send statement to DBMS
          ResultSet result = stmt3.executeQuery(sqlStatement1);
          String value = "";
          while(result.next()){
            value = result.getString(1);
            System.out.println(value);
          }
          summarize += value + "\n"; 
        }
        //print out dessert
        for(int s : dessert){
          //create statement object
          Statement stmt3 = conn.createStatement();
          // create sql statement
          String sqlStatement1 = "SELECT \"Name\" FROM \"Menu\" WHERE \"Item_ID\" = \'D"+s+"\'";
          //create send statement to DBMS
          ResultSet result = stmt3.executeQuery(sqlStatement1);
          String value = "";
          while(result.next()){
            value = result.getString(1);
            System.out.println(value);
          }
          summarize += value + "\n"; 
        }
        summarize += r.getDouble(5) + "\n";
        //print out the whole order
        JOptionPane.showMessageDialog(null, summarize);
        
        JOptionPane.showMessageDialog(f, "Thank you for choosing our service! Hope to see you soon.");
      } // end try
      catch (Exception e)
      {
        JOptionPane.showMessageDialog(f, "Error accessing Database.");
      }
    } // end customer




    else if (answer == 0)  // Admin
    {
      JOptionPane.showMessageDialog(f, "Admin");
      String c_name = JOptionPane.showInputDialog("Admin Info \nName: ");

      try {
        while (cont == true)  // continue making changes
        {
          String[] food_choice = { "Entree", "Side", "Drink", "Dessert" };
          int choice = JOptionPane.showOptionDialog(f, "Which item do you want to change price?", "Menu",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, food_choice, food_choice[0]);
          String menu_item = "";

          if (choice == 0) // order entree
          {
            // create a statement object
            Statement stmt = conn.createStatement();
            // create an SQL statement
            String sqlStatement2 = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Entree\' ORDER BY \"Item_ID\"";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement2);

            // retrieve all entree items from menu table
            ArrayList<String> arr1 = new ArrayList<String>();
            while (result.next())
            {
              menu_item = result.getString("Name");
              arr1.add(menu_item);
            }

            // store entree menu items in array list to display as buttons
            String[] entree_choice = new String[arr1.size()];
            for (int i = 0; i < arr1.size(); i++)
            {
              entree_choice[i] = arr1.get(i);
            }

            int e_choice = JOptionPane.showOptionDialog(f, "Select entree: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, entree_choice, entree_choice[0]);

            Statement stmt1 = conn.createStatement();
            String price = JOptionPane.showInputDialog("New price: ");
            double newprice = Double.parseDouble(price);

            String[] confirmCancel = { "Confirm", "Cancel" };
            int confirm = JOptionPane.showOptionDialog(f, "Changing price of "+entree_choice[e_choice]+" to $"+price, null, JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, confirmCancel, confirmCancel[0]);

            if (confirm == 0)  // update price
            {
                String sqlStatement1 = "UPDATE \"Menu\" SET \"Price\" = '"+newprice+"' WHERE \"Item_ID\" = 'E"+(e_choice+1)+"'";
                stmt1.executeUpdate(sqlStatement1);
                JOptionPane.showMessageDialog(f, "Successfully updated price of "+entree_choice[e_choice]+" to $"+price+".");
            }
            else
            {
            	JOptionPane.showMessageDialog(f, "Price change canceled.");
            }

            String[] moreDone = { "Yes", "No" };
            int md = JOptionPane.showOptionDialog(f, "Do you want to make more changes?", null, JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);

            if (md == 1)
            { cont = false; }
          }
        }
      }//end try
      catch (Exception e) {
        JOptionPane.showMessageDialog(f, "Error accessing Database.");
      } 
      
      
    } // end admin
    /*
     * 
     * //closing the connection JOptionPane.showMessageDialog(null,"Order Info");
     * String c_name = JOptionPane.showInputDialog("Name: "); try{
     * 
     * String [] food_choice = {"Dessert","Drink"}; int choice =
     * JOptionPane.showOptionDialog(null, "Are you a customer or admin?",
     * "something", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
     * buttons, buttons[0]); if(choice == 0) { //create a statement object Statement
     * stmt = conn.createStatement(); //create an SQL statement String sqlStatement
     * = "SELECT * FROM \"Menu\" WHERE \"Type\" =\'Dessert\'"; //send statement to
     * DBMS ResultSet result = stmt.executeQuery(sqlStatement);
     * 
     * //OUTPUT
     * JOptionPane.showMessageDialog(null,"Customer Last names from the Database.");
     * 
     * System.out.println("______________________________________");
     * 
     * while (result.next()) { //System.out.println("get in the loop"); menu_item +=
     * result.getString("Name")+"\n"; System.out.println(result.getString("Name"));
     * 
     * } }
     * 
     * } catch (Exception e){
     * JOptionPane.showMessageDialog(null,"Error accessing Database."); }
     * JOptionPane.showMessageDialog(null,menu_item); try { conn.close();
     * JOptionPane.showMessageDialog(null,"Connection Closed."); } catch(Exception
     * e) { JOptionPane.showMessageDialog(null,"Connection NOT Closed."); }//end try
     * catch
     */
  }// end main
}// end Class
import javax.swing.JOptionPane;
import java.sql.*;
import java.util.*;

// import javax.swing.JFrame;
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
    //Recommendation engine
    recommendation rec = new recommendation(); //Start Recommendation Engine (NOT IN USE YET)

    String[] buttons = { "Admin", "Customer" };  // user selection
    int answer = JOptionPane.showOptionDialog(null, "Are you a customer or admin?", "Chipotle Wannabe",
      JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);

    String sqlStatement = "";
    boolean cont = true;

    if (answer == 1)  // Customer
    {
    	Double price = 0.00;  // total price of order
      String c_name = JOptionPane.showInputDialog("Customer Info \nName: ");  // get customer name

      // Order is filled in the following order: entree, side, drink, dessert
      ArrayList<Integer> entree = new ArrayList<Integer>();
      ArrayList<Integer> side = new ArrayList<Integer>();
      ArrayList<Integer> drink = new ArrayList<Integer>();
      ArrayList<Integer> dessert = new ArrayList<Integer>();

      try
      {
        while (cont == true)  // continue ordering more items
        {
          String[] food_choice = { "Entree", "Side", "Drink", "Dessert" };
          int item_type = JOptionPane.showOptionDialog(null, "What do you want to order?", "Menu",
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
            int e_choice = JOptionPane.showOptionDialog(null, "Select entree: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, entree_items, entree_items[0]);
            
            entree.add(e_choice+1);  // store the customer's entree choice
            
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
            String[] side_choice = new String[arr2.size()];
            for (int i = 0; i < arr2.size(); i++)
            {
              side_choice[i] = arr2.get(i);
            }

            // display side item buttons
            int s_choice = JOptionPane.showOptionDialog(null, "Select side: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, side_choice, side_choice[0]);
            
            side.add(s_choice+1);  // store the customer's side choice
            
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
            String[] drink_choice = new String[arr3.size()];
            for (int i = 0; i < arr3.size(); i++)
            {
              drink_choice[i] = arr3.get(i);
            }

            int b_choice = JOptionPane.showOptionDialog(null, "Select a drink: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, drink_choice, drink_choice[0]);
            
            drink.add(b_choice+1);  // store customer's drink choice
            
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
            String[] dessert_choice = new String[arr4.size()];
            for (int i = 0; i < arr4.size(); i++)
            {
              dessert_choice[i] = arr4.get(i);
            }

            int d_choice = JOptionPane.showOptionDialog(null, "Select dessert: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, dessert_choice, dessert_choice[0]);
            
            dessert.add(d_choice+1);    // store customer's dessert choice
            
            Statement stmt1 = conn.createStatement();
            sqlStatement = "SELECT \"Price\" FROM \"Menu\" WHERE \"Item_ID\" = 'D"+(d_choice+1)+"'";
            ResultSet result1 = stmt1.executeQuery(sqlStatement);
            
            while (result1.next())
            { price += result1.getDouble("Price"); }
          }

          // Continue the order
          String[] moreDone = { "No", "Yes" };
          int md = JOptionPane.showOptionDialog(null, "Are you ready to pay?", null, JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);
          
          if (md == 1)  // complete order
          { cont = false; }

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
        sqlStatement = "INSERT INTO \"Orders\" (\"Order_ID\", \"Name\", \"Date\", \"Price_Total\", \"Entree\", \"Sides\", \"Dessert\", \"Beverage\") VALUES ('"+orderID+"', '"+c_name+"', '"+java.time.LocalDate.now()+"', '"+price+"', '"+e_arr+"', '"+s_arr+"', '"+d_arr+"', '"+b_arr+"')";
        stmt1.executeUpdate(sqlStatement);
        
        JOptionPane.showMessageDialog(null, "Thank you for choosing our service! Hope to see you soon.");

      } // end try
      catch (Exception e)
      {
        JOptionPane.showMessageDialog(null, "Error accessing Database.");
      }
    } // end customer

    else if (answer == 0)  // Admin
    {
      JOptionPane.showMessageDialog(null, "Admin");
      String c_name = JOptionPane.showInputDialog("Admin Info \nName: ");

      try {
        while (cont == true)  // continue making changes
        {
          String[] food_choice = { "Entree", "Side", "Drink", "Dessert" };
          int choice = JOptionPane.showOptionDialog(null, "Which item do you want to change price?", "Menu",
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

            int e_choice = JOptionPane.showOptionDialog(null, "Select entree: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, entree_choice, entree_choice[0]);
            
            Statement stmt1 = conn.createStatement();
            String price = JOptionPane.showInputDialog("New price: ");
            double newprice = Double.parseDouble(price);
            
            String sqlStatement1 = "UPDATE \"Menu\" SET \"Price\" = '"+newprice+"' WHERE \"Item_ID\" = 'E"+(e_choice+1)+"'";
            stmt1.executeUpdate(sqlStatement1);
            
            String[] moreDone = { "Yes", "No" };
            int md = JOptionPane.showOptionDialog(null, "Do you make more changes?", null, JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);
            
            if (md == 1)
            { cont = false; }
          } 
          
        }
      }//end try
      catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error accessing Database.");
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

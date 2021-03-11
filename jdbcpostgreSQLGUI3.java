import javax.swing.JOptionPane;
import java.sql.*;
import java.util.*;

// import javax.swing.JFrame;
//import java.sql.DriverManager;

/*
CSCE 315
9-25-2019
 */
public class jdbcpostgreSQLGUI {
  public static void main(String args[]) {
    dbSetup my = new dbSetup();
    // Building the connection
    Connection conn = null;

    // clone connection
    try {
      Class.forName("org.postgresql.Driver");
      conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db905_group17_project2", my.user,
        my.pswd);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println(e.getClass().getName() + ": " + e.getMessage());
      System.exit(0);
    } // end try catch

    JOptionPane.showMessageDialog(null, "Opened database successfully");

    String[] buttons = { "Admin", "Customer" }; // button option
    int answer = JOptionPane.showOptionDialog(null, "Are you a customer or admin?", "Chipotle Wannabe",
        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);

    if (answer == 1) // Customer
    {
    	Double price = 0.00;
      // JFrame frame = new JFrame();
      // JOptionPane.showMessageDialog(null,"Order Info");
      // String c_name = JOptionPane.showInputDialog("Name: ");

      // Order is filled in the following order: entree, side, drink, dessert
      ArrayList<ArrayList<Integer>> orders = new ArrayList<ArrayList<Integer>>();
      ArrayList<Integer> entree = new ArrayList<Integer>();
      ArrayList<Integer> side = new ArrayList<Integer>();
      ArrayList<Integer> drink = new ArrayList<Integer>();
      ArrayList<Integer> dessert = new ArrayList<Integer>();

      String c_name = JOptionPane.showInputDialog("Customer Info \nName: ");
      boolean order = true;

      try {
        while (order == true) // continue ordering more items
        {
          String[] food_choice = { "Entree", "Side", "Drink", "Dessert" };
          int choice = JOptionPane.showOptionDialog(null, "What do you want to order?", "Menu",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, food_choice, food_choice[0]);
          String menu_item = "";

          if (choice == 0) // order entree
          {
            // create a statement object
            Statement stmt = conn.createStatement();
            // create an SQL statement
            String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Entrée\'";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
            
            // OUTPUT
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
              System.out.println(entree_choice[i]);
            }
            
            int e_choice = JOptionPane.showOptionDialog(null, "Select Entrée: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, entree_choice, entree_choice[0]);
            
            entree.add(e_choice+1);  // store the customer's entree choice
            
            String choice1 = "E"+(e_choice+1);
            Statement stmt1 = conn.createStatement();
            String sqlStatement1 = "SELECT \"Price\" FROM \"Menu\" WHERE \"Item_ID\" = '"+choice1+"'";
            ResultSet result1 = stmt1.executeQuery(sqlStatement1);
            
            while (result1.next())
            {
            	price += result1.getDouble("Price");
            }
            
            // JOptionPane.showMessageDialog(null,menu_item);
            // Continue the order
            String[] moreDone = { "No", "Yes" };
            int md = JOptionPane.showOptionDialog(null, "Are you ready to pay?", null, JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);
            if (md == 0) {
              order = true;
            } else {
              order = false;
              orders.add(entree);
              orders.add(side);
              orders.add(drink);
              orders.add(dessert);
            }
          } 
          else if (choice == 1)  // order side
          {
            // create a statement object
            Statement stmt = conn.createStatement();
            // create an SQL statement
            String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Side\'";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            // OUTPUT
            // retrieve all side items from menu table
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

            int s_choice = JOptionPane.showOptionDialog(null, "Select side: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, side_choice, side_choice[0]);
            
            side.add(s_choice+1);  // store the customer's side choice
            
            String choice1 = "S"+(s_choice+1);
            Statement stmt1 = conn.createStatement();
            String sqlStatement1 = "SELECT \"Price\" FROM \"Menu\" WHERE \"Item_ID\" = '"+choice1+"'";
            ResultSet result1 = stmt1.executeQuery(sqlStatement1);
            
            while (result1.next())
            {
            	price += result1.getDouble("Price");
            }
            
            // Continue the order
            String[] moreDone = { "No", "Yes" };
            int md = JOptionPane.showOptionDialog(null, "Are you ready to pay?", null, JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);
            if (md == 0) {
              order = true;
            } else {
              order = false;
              orders.add(entree);
              orders.add(side);
              orders.add(drink);
              orders.add(dessert);
            }
          }
          else if (choice == 2)  // order drink
          {
            // create a statement object
            Statement stmt = conn.createStatement();
            // create an SQL statement
            String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Drink\'";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            // OUTPUT
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

            int d_choice = JOptionPane.showOptionDialog(null, "Select a drink: ", "Menu", JOptionPane.DEFAULT_OPTION,
              JOptionPane.QUESTION_MESSAGE, null, drink_choice, drink_choice[0]);
            
            drink.add(d_choice+1);  // store customer's drink choice
            
            side.add(d_choice+1);  // store the customer's side choice
            
            String choice1 = "B"+(d_choice+1);
            Statement stmt1 = conn.createStatement();
            String sqlStatement1 = "SELECT \"Price\" FROM \"Menu\" WHERE \"Item_ID\" = '"+choice1+"'";
            ResultSet result1 = stmt1.executeQuery(sqlStatement1);
            
            while (result1.next())
            {
            	price += result1.getDouble("Price");
            }
            
            // Continue the order
            String[] moreDone = { "No", "Yes" };
            int md = JOptionPane.showOptionDialog(null, "Are you ready to pay?", null, JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);
            if (md == 0) {
              order = true;
            } else {
              order = false;
              orders.add(entree);
              orders.add(side);
              orders.add(drink);
              orders.add(dessert);
            }
          } 
          else if (choice == 3)  // order dessert
          {
            // create a statement object
            Statement stmt = conn.createStatement();
            // create an SQL statement
            String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Dessert\'";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            // OUTPUT
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
            
            String choice1 = "D"+(d_choice+1);
            Statement stmt1 = conn.createStatement();
            String sqlStatement1 = "SELECT \"Price\" FROM \"Menu\" WHERE \"Item_ID\" = '"+choice1+"'";
            ResultSet result1 = stmt1.executeQuery(sqlStatement1);
            
            while (result1.next())
            {
            	price += result1.getDouble("Price");
            }
            
            // Continue the order
            String[] moreDone = { "No", "Yes" };
            int md = JOptionPane.showOptionDialog(null, "Are you ready to pay?", null, JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);
            if (md == 0) {
              order = true;
            } else {
              order = false;
              orders.add(entree);
              orders.add(side);
              orders.add(drink);
              orders.add(dessert);
            }
          }
        } // end while loop

        Array e_arr = conn.createArrayOf("INTEGER", entree.toArray());
        Array s_arr = conn.createArrayOf("INTEGER", side.toArray());
        Array b_arr = conn.createArrayOf("INTEGER", drink.toArray());
        Array d_arr = conn.createArrayOf("INTEGER", dessert.toArray());

        // GET NEXT ORDER ID
        int  orderID = 0;
        Statement stmt2 = conn.createStatement();
        String sqlStatement2 = "SELECT \"Order_ID\" FROM \"Orders\" ORDER BY \"Order_ID\" DESC LIMIT 1";
        ResultSet result2 = stmt2.executeQuery(sqlStatement2); 
        while (result2.next())
        {
        	orderID = result2.getInt("Order_ID") + 1;
        }
        
        // PLACE ORDER
        Statement stmt1 = conn.createStatement();
        String sqlStatement1 = "INSERT INTO \"Orders\" (\"Order_ID\", \"Name\", \"Date\", \"Price_Total\", \"Entree\", \"Sides\", \"Desert\", \"Beverage\") VALUES ('"+orderID+"', '"+c_name+"', '"+java.time.LocalDate.now()+"', '"+price+"', '"+e_arr+"', '"+s_arr+"', '"+d_arr+"', '"+b_arr+"')";
        stmt1.executeUpdate(sqlStatement1);
        // print out the order

        //print out side items
        String summarize = "";
        for(int s : side){
          
          //create statement object
          Statement stmt3 = conn.createStatement();
          // create sql statement
          String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Item_ID\" = \'S"+s+"\'";
          //create send statement to DBMS
          ResultSet result = stmt3.executeQuery(sqlStatement);
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
          String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Item_ID\" = \'S"+s+"\'";
          //create send statement to DBMS
          ResultSet result = stmt3.executeQuery(sqlStatement);
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
          String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Item_ID\" = \'S"+s+"\'";
          //create send statement to DBMS
          ResultSet result = stmt3.executeQuery(sqlStatement);
          String value = "";
          while(result.next()){
            value = result.getString(1);
            System.out.println(value);
          }
          summarize += value + "\n"; 
        }

        //print out the whole order
        JOptionPane.showMessageDialog(null, summarize);
        JOptionPane.showMessageDialog(null, "Thank you for choosing our service! Hope to see you soon.");

      } // end try
      catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error accessing Database.");
      }
    } // end customer

    else if (answer == 0)  // Admin
    {
      JOptionPane.showMessageDialog(null, "Admin");
      String c_name = JOptionPane.showInputDialog("Admin Info \nName: ");
      boolean order = true;
      try {
        while (order == true) // continue ordering more items
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
            String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Entrée\'";
            // send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);

            // OUTPUT
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
            
            
            String choice1 = "E"+(e_choice+1);
            //System.out.println("get choice" + choice1);
            Statement stmt1 = conn.createStatement();
            String price = JOptionPane.showInputDialog("New price: ");
            //double new_price = (double) c_price;
            double newprice = Double.parseDouble(price);
            // Double d= new Double("6.35");
            // double newprice = d.parseDouble(price);
            //System.out.println(new_price);
            //UPDATE "Menu" SET "Price" =  1.50 where "Item_ID" = 'B1';
            
            String sqlStatement1 = "UPDATE \"Menu\" SET \"Price\" = '"+newprice+"' WHERE \"Item_ID\" = '"+choice1+"'";
            stmt1.executeUpdate(sqlStatement1);
            
            // JOptionPane.showMessageDialog(null,menu_item);
            // Continue the order
            String[] moreDone = { "No", "Yes" };
            int md = JOptionPane.showOptionDialog(null, "Do you make more changes?", null, JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);
            if (md == 0) {
              order = false;
            } else {
              order = true;
            }
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
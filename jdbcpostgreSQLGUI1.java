import java.sql.*;
import javax.swing.*;    
import javax.swing.JOptionPane;
import javax.swing.JFrame;
//import java.sql.DriverManager;
/*
CSCE 315
9-25-2019
 */
public class jdbcpostgreSQLGUI {
  public static void main(String args[]) {
    dbSetup my = new dbSetup();
    //Building the connection
     Connection conn = null;

     //clone connection
     try {
        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection("jdbc:postgresql://csce-315-db.engr.tamu.edu/db905_group17_project2",
           my.user, my.pswd);
     } catch (Exception e) {
        e.printStackTrace();
        System.err.println(e.getClass().getName()+": "+e.getMessage());
        System.exit(0);
     }//end try catch


     JOptionPane.showMessageDialog(null,"Opened database successfully");
     
     String [] buttons = {"Admin","Customer"}; //button option
     int answer = JOptionPane.showOptionDialog(null, "Are you a customer or admin?", "Chipotle Wannabe",
        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
      if(answer == 1) { // Customer
        // JFrame frame = new JFrame();
        //JOptionPane.showMessageDialog(null,"Order Info");
        // String c_name = JOptionPane.showInputDialog("Name: ");
        String c_name = JOptionPane.showInputDialog("Customer Info \nName: ");
        boolean order = true;
        try{
          while(order == true){  //if customer want to continue to order
            String [] food_choice = {"Dessert","Drink","Side","Entree"};
            int choice = JOptionPane.showOptionDialog(null, "What do you want to order?", "Menu",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, food_choice, food_choice[0]);
            String cus_lname = "";
            if(choice == 0) {
              
              //create a statement object
              Statement stmt = conn.createStatement();
              //create an SQL statement
              String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Dessert\'";
              //send statement to DBMS
              ResultSet result = stmt.executeQuery(sqlStatement);
      
              //OUTPUT
              JOptionPane.showMessageDialog(null,"Dessert");
              
              System.out.println("______________________________________");
              String [] dessert_choice = {};
              while (result.next()) {
                //System.out.println("get in the loop");
               // cus_lname += result.getString("Name")+"\n";
                cus_lname = result.getString("Name");
                dessert_choice = ArrayUtils.add(dessert_choice,cus_lname);
                //System.out.println(result.getString("Name"));
              }
              //JOptionPane.showMessageDialog(null,cus_lname);
              // Continue the order
              
              String [] moreDone = {"No","Yes"};
              int md = JOptionPane.showOptionDialog(null, "Are you ready to pay?", null,
              JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);
              if (md == 0){
                  order = true;
              }
              else {
                  order = false;
              }

            } 
            else if(choice == 1) {
              
              //create a statement object
              Statement stmt = conn.createStatement();
              //create an SQL statement
              String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Drink\'";
              //send statement to DBMS
              ResultSet result = stmt.executeQuery(sqlStatement);
      
              //OUTPUT
              JOptionPane.showMessageDialog(null,"Drink");
              
              System.out.println("______________________________________");
            
              while (result.next()) {
                //System.out.println("get in the loop");
                cus_lname += result.getString("Name")+"\n";
                System.out.println(result.getString("Name"));
              }
              JOptionPane.showMessageDialog(null,cus_lname);
              // Continue the order
              String [] moreDone = {"No","Yes"};
              int md = JOptionPane.showOptionDialog(null, "Are you ready to pay?", null,
              JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);
              if (md == 0){
                order = true;
              }
              else {
                order = false;
              }
              
            } 
          else if(choice == 3) {
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Entrée\'";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
    
            //OUTPUT
            JOptionPane.showMessageDialog(null,"Entree");
            
            System.out.println("______________________________________");
          
            while (result.next()) {
              //System.out.println("get in the loop");
              cus_lname += result.getString("Name")+"\n";
              System.out.println(result.getString("Name"));
            }
            JOptionPane.showMessageDialog(null,cus_lname);
            // Continue the order
            String [] moreDone = {"No","Yes"};
            int md = JOptionPane.showOptionDialog(null, "Are you ready to pay?", null,
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);
            if (md == 0){
              order = true;
            }
            else {
              order = false;
            }
          } 
          else if(choice == 2) {
            //create a statement object
            Statement stmt = conn.createStatement();
            //create an SQL statement
            String sqlStatement = "SELECT \"Name\" FROM \"Menu\" WHERE \"Type\" =\'Side\'";
            //send statement to DBMS
            ResultSet result = stmt.executeQuery(sqlStatement);
    
            //OUTPUT
            JOptionPane.showMessageDialog(null,"Side");
            
            System.out.println("______________________________________");
          
            while (result.next()) {
              //System.out.println("get in the loop");
              cus_lname += result.getString("Name")+"\n";
              System.out.println(result.getString("Name"));
            }
            JOptionPane.showMessageDialog(null,cus_lname);
            // Continue the order
            String [] moreDone = {"No","Yes"};
            int md = JOptionPane.showOptionDialog(null, "Are you ready to pay?", null,
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, moreDone, moreDone[0]);
            if (md == 0){
              order = true;
            }
            else {
              order = false;
            }
          } 
          
        } //while loop
        JOptionPane.showMessageDialog(null,"Thank you for choosing our service! Hope to see you soon.");
      } //try loop 
        catch (Exception e){
          JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        
      
    } //for customer
      


      else if (answer == 0) { //Admin
        JOptionPane.showMessageDialog(null,"Admin");
      }
    /*
     
    //closing the connection
    JOptionPane.showMessageDialog(null,"Order Info");
        String c_name = JOptionPane.showInputDialog("Name: ");
        try{
        
          String [] food_choice = {"Dessert","Drink"};
          int choice = JOptionPane.showOptionDialog(null, "Are you a customer or admin?", "something",
          JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
          if(choice == 0) {
            //create a statement object
              Statement stmt = conn.createStatement();
              //create an SQL statement
              String sqlStatement = "SELECT * FROM \"Menu\" WHERE \"Type\" =\'Dessert\'";
              //send statement to DBMS
              ResultSet result = stmt.executeQuery(sqlStatement);
      
              //OUTPUT
              JOptionPane.showMessageDialog(null,"Customer Last names from the Database.");
              
              System.out.println("______________________________________");
            
              while (result.next()) {
                //System.out.println("get in the loop");
                cus_lname += result.getString("Name")+"\n";
                System.out.println(result.getString("Name"));
                

              }
          } 
          
        }  catch (Exception e){
          JOptionPane.showMessageDialog(null,"Error accessing Database.");
        }
        JOptionPane.showMessageDialog(null,cus_lname);
    try {
      conn.close();
      JOptionPane.showMessageDialog(null,"Connection Closed.");
    } catch(Exception e) {
      JOptionPane.showMessageDialog(null,"Connection NOT Closed.");
    }//end try catch
  */
  }//end main
}//end Class

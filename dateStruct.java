public class dateStruct 
{
	//Data
	String day;
	String month;
	String year;
	String date;
	dateStruct(Integer day_, Integer month_, Integer year_)
	{
		if(String.valueOf(day_).length()==1)
			day = "0" + day_.toString();
		else if(String.valueOf(day_).length()==2)
			day = day_.toString();
		else
			System.err.println("Incorrect date format");
		
		if(String.valueOf(month_).length()==1)
			month = "0" + month_.toString();
		else if(String.valueOf(month_).length()==2)
			month = month_.toString();
		else
			System.err.println("Incorrect date format");
		
		if(String.valueOf(year_).length()==4)
			year = year_.toString();
		else
			System.err.println("Incorrect date format");
		
		date = year + "-" + month + "-" + day;
	}
	void printDate()
	{
		System.out.println(date);
	}
	
}

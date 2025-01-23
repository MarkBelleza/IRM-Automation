package UI;

import java.lang.reflect.Method;

import org.testng.annotations.DataProvider;

public class Data {
	
	@DataProvider(name="locations")
	public Object[] dataSet(Method m) {
		
		if (m.getName().equals("test")) {
			return new Object[] 
					{"ALGOMA TREATMENT & REMAND CTR-ADULT",
					"BROCKVILLE JAIL - ADULT",
					 "CENTRAL EAST CORRECTIONAL CENTRE - ADULT"};
		}
		else {
			return new Object[][]
				{{"ALGOMA TREATMENT & REMAND CTR-ADULT", "1"},
				{"BROCKVILLE JAIL - ADULT", "2"},
				{"CENTRAL EAST CORRECTIONAL CENTRE - ADULT", "3"}};
		}
	}
}

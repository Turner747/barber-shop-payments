// Programmer: Joshua Turner s0258441
// Course: Programming Fundamentals COIT 11222 T320
// File: CustomerHaircut.java
// Purpose: Assignment Three -- Project: The Den Barber Shop GUI program
// Date: 31 January 2021
// Class for services received from The Den Barbershop


public class CustomerHaircut
{
	// Class attributes
	private String customerName;
	private String customerHairOption;
	private String customerFaceOption;
	private boolean customerMonobrowOption;
	private boolean customerFullEyebrowsOption;

	// constant attributes
	private static double STANDARD_CUT_COST = 25.00;	// cost for standard haircut
	private static double SKIN_FADE_COST = 35.00;		// cost for skin fade haircut
	private static double STYLE_CUT_COST = 30.00;		// cost for style cut haircut

	private static double FULL_SHAVE_COST = 20.00;		// cost for full face shave
	private static double BEARD_TRIM_COST = 15.00;		// cost for beard trim

	private static double MONOBROW_COST = 5.00;			// cost for monobrow waxing
	private static double FULL_EYEBROWS_COST = 10.00;	// cost for full eyebrows waxing

	// Full parametised constructor
	public CustomerHaircut(String name, String hair, String face,
								boolean monobrow, boolean eyebrows)
	{
		customerName = name;
		customerHairOption = hair;
		customerFaceOption = face;
		customerMonobrowOption = monobrow;
		customerFullEyebrowsOption = eyebrows;
	}

	// Empty constructor
	public CustomerHaircut()
	{
		customerName = "null";
		customerHairOption = "No Option";
		customerFaceOption = "No Option";
		customerMonobrowOption = false;
		customerFullEyebrowsOption = false;
	}

	// Mutator methods

	public void setCustomerName(String name)
	{
		customerName = name;
	}

	public void setCustomerHairOption(String hair)
	{
		customerHairOption = hair;
	}

	public void setCustomerFaceOption(String face)
	{
		customerFaceOption = face;
	}

	public void setCustomerMonobrowOption(boolean monobrow)
	{
		customerMonobrowOption = monobrow;
	}

	public void setcustomerFullEyebrowsOption(boolean eyebrows)
	{
		customerFullEyebrowsOption = eyebrows;
	}

	// Accessor methods

	public String getCustomerName()
	{
		return customerName;
	}

	public String getCustomerHairOption()
	{
		return customerHairOption;
	}

	public String getCustomerFaceOption()
	{
		return customerFaceOption;
	}

	public boolean getCustomerMonobrowOption()
	{
		return customerMonobrowOption;
	}

	public boolean getCustomerFullEyebrowsOption()
	{
		return customerFullEyebrowsOption;
	}


	// price accessor methods

	public static double getStandardCutPrice()
	{
		return STANDARD_CUT_COST;
	}

	public static double getStyleCutPrice()
	{
		return STYLE_CUT_COST;
	}

	public static double getSkinFadePrice()
	{
		return SKIN_FADE_COST;
	}


	public static double getFullShavePrice()
	{
		return FULL_SHAVE_COST;
	}

	public static double getBeardTrimPrice()
	{
		return BEARD_TRIM_COST;
	}


	public static double getMonobrowPrice()
	{
		return MONOBROW_COST;
	}

	public static double getFullEyebrowsPrice()
	{
		return FULL_EYEBROWS_COST;
	}


	// Cost calculation method

	public double calculateServiceCharge()
	{
		double serviceCharge = 0.0;

		switch(customerHairOption)		// add the cost of the customer hair option to the service charge
		{
			case "No Option":
				break;
			case "Standard":
				serviceCharge += STANDARD_CUT_COST;
				break;
			case "Skin Fade":
				serviceCharge += SKIN_FADE_COST;
				break;
			case "Style Cut":
				serviceCharge += STYLE_CUT_COST;
				break;
		}

		switch(customerFaceOption)		// add the cost of the customer face option to the service charge
		{
			case "No Option":
				break;
			case "Full Shave":
				serviceCharge += FULL_SHAVE_COST;
				break;
			case "Beard Trim":
				serviceCharge += BEARD_TRIM_COST;
				break;
		}

		if(customerMonobrowOption)					// add cost of monobrow waxing if customer has chosen this option
		{
			serviceCharge += MONOBROW_COST;
		}

		if(customerFullEyebrowsOption)				// add cost of full eyebrows waxing if customer has chosen this option
		{
			serviceCharge += FULL_EYEBROWS_COST;
		}

		return serviceCharge;
	}
}
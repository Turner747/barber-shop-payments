public class HaircutTest
{
	public static void main(String[] args)
	{
		CustomerHaircut test1 = new CustomerHaircut("Joshua",
				"SKIN_FADE", "BEARD_TRIM", false, true);

		System.out.printf("%s's service charge is $%.2f\n", test1.getCustomerName(), test1.calculateServiceCharge());

	}
}
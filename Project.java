// Programmer: Joshua Turner s0258441
// Course: Programming Fundamentals COIT 11222 T320
// File: Project.java
// Purpose: Assignment Three -- Project: The Den Barber Shop GUI program
// Date: 6 February 2021
// GUI constructor and main method for program

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class Project extends JFrame implements ActionListener
{
	final int MAX_HAIRCUTS = 8; // Maximum number of haircuts that can be provided (N = highest number in student ID)
	private int currentHaircut = 0; // varible used to track the current haircut
	private CustomerHaircut[] haircutArray = new CustomerHaircut[MAX_HAIRCUTS]; // object array declaration and initialisation

	private JLabel titleLabel = new JLabel("The Den Barbershop Management System"); // program title
	private JLabel nameLabel = new JLabel("Customer name: ");// label for customer name field
	private JTextField nameField = new JTextField(28);      // field for customer name input

	private JTextArea displayTextArea = new JTextArea("", 20, 41); // declare text area
	private JScrollPane scrollPane; // scroll pane for the text area

	// declare JComboBoxes
	private JLabel hairLabel = new JLabel(String.format("Hair options:   Standard Cut $%.0f  |  Skin Fade $%.0f  |  Style Cut $%.0f   ",
									CustomerHaircut.getStandardCutPrice(), CustomerHaircut.getSkinFadePrice(),
												CustomerHaircut.getStyleCutPrice()));	// label for drop down box with prices
	private JComboBox<String> hairChoice = new JComboBox<String>();	// Drop down box for selecting hair options

	private JLabel faceLabel = new JLabel(String.format("Face options:   Full Shave $%.0f  |  Beard Trim $%.0f   ",
									CustomerHaircut.getFullShavePrice(), CustomerHaircut.getBeardTrimPrice()));// label for drop down box with prices
	private JComboBox<String> faceChoice = new JComboBox<String>();	// Drop down box for selecting face options

	// declare button group
	private JLabel extrasLabel = new JLabel(String.format("%25s", "Extras: "));	// Extras label for check box options
	private ButtonGroup extrasChoice = new ButtonGroup();	// create button group extras option check boxes
	private JCheckBox monobrowBox = new JCheckBox(String.format("Monobrow Wax $%.0f",
													CustomerHaircut.getMonobrowPrice()));	// monobow option check box
	private JCheckBox fullEyebrowsBox = new JCheckBox(String.format("Full Eyebrow Wax $%.0f",
													CustomerHaircut.getFullEyebrowsPrice()));	// full eyebrow option check box
	private JCheckBox invisibleBox = new JCheckBox(""); 	// for no selection is made

	//  declare all of the buttons
	private JPanel buttonPanel = new JPanel();	// used to format buttons together
	private JButton enterButton = new JButton("Enter");
	private JButton displayButton = new JButton("Display All");
	private JButton searchButton = new JButton("Search");
	private JButton clearButton = new JButton("Clear");
	private JButton exitButton = new JButton("Exit");

	public Project() // Constructor for Project GUI
	{
		this.setLayout(new FlowLayout());			// set JFrame to FlowLayout

		titleLabel.setFont(new Font("Ariel", Font.BOLD, 22));	// Set title format
		add(titleLabel);										// add title label to frame

		add(nameLabel);		// add name label to frame
		add(nameField);		// add name field to frame

		add(hairLabel);						// add hair option label to frame
		add(hairChoice);					// add hair choice combo box to frame
		hairChoice.addItem("No Option");	// add option to hair choice combo box
		hairChoice.addItem("Standard");		// add option to hair choice combo box
		hairChoice.addItem("Skin Fade");	// add option to hair choice combo box
		hairChoice.addItem("Style Cut");	// add option to hair choice combo box

		add(faceLabel);						// add face option label to frame
		add(faceChoice);					// add face choice combo box to frame
		faceChoice.addItem("No Option");	// add option to face choice combo box
		faceChoice.addItem("Full Shave");	// add option to face choice combo box
		faceChoice.addItem("Beard Trim");	// add option to face choice combo box

		add(extrasLabel);					// add extras label to frame
		extrasChoice.add(monobrowBox);		// add check box to button group
		extrasChoice.add(fullEyebrowsBox);	// add check box to button group
		extrasChoice.add(invisibleBox);		// add invisible check box to button group
		invisibleBox.setSelected(true);		// sets the invisible checkbox to be selected, used for error message in enter()
		add(monobrowBox);					// add check box to frame
		add(fullEyebrowsBox);				// add check box to frame
		add(invisibleBox);					// add invisible check box to frame
		invisibleBox.setVisible(false);


		// set text area to a monospaced font so the columns can be aligned using a format string
		displayTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
		displayTextArea.setEditable(false); 			// make text area read only
		scrollPane = new JScrollPane(displayTextArea); 	// add text area to the scroll pane
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // just need vertical scrolling
		add(scrollPane);


		// add buttons to frame
		add(buttonPanel);
		buttonPanel.add(enterButton);
		buttonPanel.add(displayButton);
		buttonPanel.add(searchButton);
		buttonPanel.add(clearButton);
		buttonPanel.add(exitButton);


		// add the action listener to the buttons
		enterButton.addActionListener(this);
		displayButton.addActionListener(this);
		searchButton.addActionListener(this);
		clearButton.addActionListener(this);
		exitButton.addActionListener(this);

		// action taken when user presses window close button (x in top right corner)
		addWindowListener(
			new WindowAdapter()
			{
				public void windowClosing(WindowEvent e)
				{
					exit();			// run exit() method below
				}
			}
		);
	}


	public void actionPerformed(ActionEvent e)
	{ // processes the user button clicks
		String command = e.getActionCommand();

		if (command.compareTo("Enter") == 0)
			enter();
		else if (command.compareTo("Display All") == 0)
			displayAll();
		else if (command.compareTo("Search") == 0)
			search();
		else if (command.compareTo("Clear") == 0)
			clear();
		else if (command.compareTo("Exit") == 0)
			exit();
	}


	// Button Methods

	private void enter()
	{ // action for when enter button is pressed

		// Error Conditions
		if(currentHaircut >= MAX_HAIRCUTS)	// maximum customer error message
		{
			displayError("Maximum number of customers has been reached");
			return;
		}

		if(nameField.getText().compareTo("") == 0)	// no customer name entered error message
		{
			displayError("You must enter a customer name");
			return;
		}

		if(hairChoice.getSelectedItem().toString().compareTo("No Option") == 0 &&
			faceChoice.getSelectedItem().toString().compareTo("No Option") == 0 &&
			invisibleBox.isSelected()) // if no services are selected entered error message
		{
			displayError("You must select a service");
			return;
		}

		// Enter Button functions

		String customerName = nameField.getText();
		String customerHair = hairChoice.getSelectedItem().toString();
		String customerFace = faceChoice.getSelectedItem().toString();
		boolean customerMonobrow = monobrowBox.isSelected();
		boolean customerEyebrows = fullEyebrowsBox.isSelected();
		haircutArray[currentHaircut] = new CustomerHaircut(customerName, customerHair,
											customerFace, customerMonobrow, customerEyebrows); // Constructs new array element

		clear();
		displayCustomerHaircut(currentHaircut); // displays customer details that were just inputted

		nameField.requestFocus();	// returns focus to the customer name field

		++currentHaircut;	// increments haircut counter for next array element
	}

	private void displayAll()
	{ // displays all haircutArray elements, average service cost and total sales

		// Error message conditions
		if(currentHaircut == 0)	// no customer entered error
		{
			displayError("No customers entered");
			return;
		}

		// display all button functions
		clear();

		double totalSales = 0.0;	// used to track sales totals across all orders

		for(int i = 0; i < currentHaircut; ++i)	// loop to display all orders
		{
			displayCustomerHaircut(i);
			appendLine();

			totalSales += haircutArray[i].calculateServiceCharge();	// adds each haircutArray elements charge to the total sales tracker
		}

		double averageServiceCharge = totalSales / currentHaircut;	// average cost of each customers service calculation

		displayTextArea.append(String.format("Average charge per customer: $%.2f\nTotal Sales: $%.2f",
											averageServiceCharge, totalSales));	// display average meals and total sales in text area

		nameField.requestFocus();	// returns focus to the customer name field
	}

	private void search()
	{ // allows user to search for a customer's service details by searching the customer name

		// Error message conditions
		if(currentHaircut == 0)	// no customers entered error
		{
			displayError("No customers entered");
			return;
		}

		// Search button functions
		boolean nameFound = false;	// used in search loop to determine if a name has been found
		int searchCounter = 0;	// used to increment through array elements in the search loop


		String nameSearch = JOptionPane.showInputDialog(rootPane, "Enter customer name to search",
									"The Den Barbershop Management System", JOptionPane.QUESTION_MESSAGE);	// search input dialog box

		if(nameSearch.compareTo("") == 0)	// no name entered in search input dialog error message
		{
			displayError("You must enter a name to search");
			return;
		}

		while(!nameFound && searchCounter < currentHaircut)	// Name search loop
		{
			if(nameSearch.equalsIgnoreCase(haircutArray[searchCounter].getCustomerName()))
			{	// action for if name has been found
				nameFound = true;
			}
			else
			{	// action for if name has not been found
				++searchCounter;	// increments counter for next pass through loop
			}
		}

		if(nameFound)
		{	// display successful search results or error message
			clear();
			displayCustomerHaircut(searchCounter);
		}
		else
		{	// error message if search has been unsuccessful
			displayError(nameSearch + " not found");
			return;
		}
	}

	private void clear()
	{ // clears all input and output text area
		nameField.setText("");				// clears customer name field
		hairChoice.setSelectedIndex(0);		// resets hair choice combo box
		faceChoice.setSelectedIndex(0);		// resets face choice combo box
		invisibleBox.setSelected(true);		// resets monobrow and eyebrow check box by selecting invisible box
		displayTextArea.setText("");		// clears large text display area

		nameField.requestFocus();			// returns focus to the customer name field

	}

	private void exit()
	{ // standard exit operation
		JOptionPane.showMessageDialog(rootPane, "Thank you for using The Den Barbershop Management System",
								"The Den Barbershop Management System", JOptionPane.INFORMATION_MESSAGE);	// thank you message

		System.exit(0);
	}


	// Text area display methods

	private void appendLine()
	{	// Append line in output text area
		displayTextArea.append("-----------------------------------------\n");
	}

	private void displayCustomerHaircut(int index)	// index = haircutArray element that will be display
	{	// displays the haircutArray element details in the output text area
		displayTextArea.append(String.format("%s%s\n%-32s%s\n\n", "Customer name: ",
								haircutArray[index].getCustomerName(), "Services", "Price")); // displays customer name

		switch(haircutArray[index].getCustomerHairOption())
		{	// display hair option customer has chosen along with price
			case "No Option":
				break;
			case "Standard":
				displayTextArea.append(String.format("%-32s$%.2f\n", "Standard cut",
												CustomerHaircut.getStandardCutPrice()));
				break;
			case "Skin Fade":
				displayTextArea.append(String.format("%-32s$%.2f\n", "Skin fade",
												CustomerHaircut.getSkinFadePrice()));
				break;
			case "Style Cut":
				displayTextArea.append(String.format("%-32s$%.2f\n", "Style cut",
												CustomerHaircut.getStyleCutPrice()));
				break;
		}

		switch(haircutArray[index].getCustomerFaceOption())
		{	// display face option customer has chosen along with price
			case "No Option":
				break;
			case "Full Shave":
				displayTextArea.append(String.format("%-32s$%.2f\n", "Full shave",
												CustomerHaircut.getFullShavePrice()));
				break;
			case "Beard Trim":
				displayTextArea.append(String.format("%-32s$%.2f\n", "Beard trim",
												CustomerHaircut.getBeardTrimPrice()));
				break;
		}

		if(haircutArray[index].getCustomerMonobrowOption())
		{ // displays if customer has received monobrow wax service
			displayTextArea.append(String.format("%-32s$%.2f\n", "Monobrow wax",
												CustomerHaircut.getMonobrowPrice()));
		}

		if(haircutArray[index].getCustomerFullEyebrowsOption())
		{	// displays if customer has received full eyebrows wax service
			displayTextArea.append(String.format("%-32s$%.2f\n", "Full eyebrows wax",
												CustomerHaircut.getFullEyebrowsPrice()));
		}

		displayTextArea.append(String.format("\n%-32s$%.2f\n", "Total",
								haircutArray[index].calculateServiceCharge()));	// displays customer total
	}


	// Error message method
	private void displayError(String message) // message = the message that is displayed in dialog box
	{
		displayTextArea.setText("");
		JOptionPane.showMessageDialog(rootPane, message,
							"The Den Barbershop Management System",
									JOptionPane.ERROR_MESSAGE);
		nameField.requestFocus();
	}

	// Main method the program runs from
	public static void main(String[] args)
	{ // Create the instance of the GUI class above
		Project p = new Project();								// Creates instance GUI class above
		p.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);	// Allow the code to close the program
		p.setBounds(1000, 500, 490, 590);						// Define position and size of app
		p.setTitle("The Den Barbershop Management System");		// Sets the title of the frame
		p.setVisible(true);										// Sets frame to be visible
		p.setResizable(false);									// Does not allow user to resize frame
	}
}
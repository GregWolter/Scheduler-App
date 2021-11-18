import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;

public class SchedulerGUI extends JFrame implements ActionListener {

	private JButton loadFileButton, createNewScheduleButton, saveFileButton, addHolidayButton, clearButton;
	private JTextArea newSchedule, holidayList;
	private JScrollPane scrollPane, scrollPane2;
	private JTextField fileNameToLoadTextField, classStartDateTextField, classEndDateTextField, holidayLabelTextField,
			meetingDayLabelTextField;
	private JRadioButton mondayRadioButton, tuesdayRadioButton, wednesdayRadioButton, thursdayRadioButton,
			fridayRadioButton;
	private final JFileChooser fileChooser = new JFileChooser();
	private static final int WIDTH = 1000; // width of GUI
	private static final int HEIGHT = 700; // height of GUI
	Timestamp timestamp, holidayTimestamp;
	JDatePickerImpl startDatePicker, endDatePicker, holidayPicker;

	public SchedulerGUI() {

		JFrame frame = new JFrame("Class Scheduler");
		frame.setLayout(null);
		frame.getContentPane().setBackground(Color.WHITE);
		// Serif is the generic name for Times New Roman
		Font font = new Font("Serif", Font.PLAIN, 12);
		frame.setSize(WIDTH, HEIGHT);

		// Create JTextField objects
		fileNameToLoadTextField = new JTextField();
		fileNameToLoadTextField.setBounds(400, 70, 200, 30);
		fileNameToLoadTextField.setFont(font);
		fileNameToLoadTextField.setDisabledTextColor(Color.BLACK);
		fileNameToLoadTextField.setEnabled(false);

		classStartDateTextField = new JTextField("Pick Class Start Date");
		classStartDateTextField.setBounds(100, 120, 200, 30);
		classStartDateTextField.setFont(font);
		classStartDateTextField.setDisabledTextColor(Color.BLACK);
		classStartDateTextField.setEnabled(false);

		classEndDateTextField = new JTextField("Pick Class End Date");
		classEndDateTextField.setBounds(325, 120, 200, 30);
		classEndDateTextField.setFont(font);
		classEndDateTextField.setDisabledTextColor(Color.BLACK);
		classEndDateTextField.setEnabled(false);

		holidayLabelTextField = new JTextField("Pick Holidays");
		holidayLabelTextField.setBounds(550, 120, 200, 30);
		holidayLabelTextField.setFont(font);
		holidayLabelTextField.setDisabledTextColor(Color.BLACK);
		holidayLabelTextField.setEnabled(false);

		meetingDayLabelTextField = new JTextField("Pick Meeting Days");
		meetingDayLabelTextField.setBounds(775, 120, 125, 30);
		meetingDayLabelTextField.setFont(font);
		meetingDayLabelTextField.setDisabledTextColor(Color.BLACK);
		meetingDayLabelTextField.setEnabled(false);

		// Create JButton objects
		loadFileButton = new JButton("Load File");
		loadFileButton.addActionListener(this);
		loadFileButton.setBounds(400, 30, 200, 30);
		loadFileButton.setFont(font);

		createNewScheduleButton = new JButton("Create New Schedule");
		createNewScheduleButton.addActionListener(this);
		createNewScheduleButton.setBounds(400, 320, 200, 30);
		createNewScheduleButton.setFont(font);

		saveFileButton = new JButton("Save File");
		saveFileButton.addActionListener(this);
		saveFileButton.setBounds(400, 600, 200, 30);
		saveFileButton.setFont(font);

		addHolidayButton = new JButton("Add Holiday");
		addHolidayButton.addActionListener(this);
		addHolidayButton.setBounds(550, 180, 200, 30);
		addHolidayButton.setFont(font);

		clearButton = new JButton("Clear");
		clearButton.addActionListener(this);
		clearButton.setBounds(100, 550, 200, 30);
		clearButton.setFont(font);
		clearButton.setVisible(false);

		// Creates JDatePickerImpl objects
		// https://www.codejava.net/java-se/swing/how-to-use-jdatepicker-to-display-calendar-component
		UtilDateModel model = new UtilDateModel();
		JDatePanelImpl datePanel = new JDatePanelImpl(model);
		startDatePicker = new JDatePickerImpl(datePanel);
		startDatePicker.setBounds(100, 150, 200, 30);
		startDatePicker.setFont(font);

		UtilDateModel modelTwo = new UtilDateModel();
		JDatePanelImpl datePanelTwo = new JDatePanelImpl(modelTwo);
		endDatePicker = new JDatePickerImpl(datePanelTwo);
		endDatePicker.setBounds(325, 150, 200, 30);

		UtilDateModel modelThree = new UtilDateModel();
		JDatePanelImpl datePanelThree = new JDatePanelImpl(modelThree);
		holidayPicker = new JDatePickerImpl(datePanelThree);
		holidayPicker.setBounds(550, 150, 200, 30);

		// Creates JRadioButton objects
		mondayRadioButton = new JRadioButton("Monday");
		mondayRadioButton.setBounds(775, 150, 125, 30);
		mondayRadioButton.setFont(font);
		tuesdayRadioButton = new JRadioButton("Tuesday");
		tuesdayRadioButton.setBounds(775, 180, 125, 30);
		tuesdayRadioButton.setFont(font);
		wednesdayRadioButton = new JRadioButton("Wednesday");
		wednesdayRadioButton.setBounds(775, 210, 125, 30);
		wednesdayRadioButton.setFont(font);
		thursdayRadioButton = new JRadioButton("Thursday");
		thursdayRadioButton.setBounds(775, 240, 125, 30);
		thursdayRadioButton.setFont(font);
		fridayRadioButton = new JRadioButton("Friday");
		fridayRadioButton.setBounds(775, 270, 125, 30);
		fridayRadioButton.setFont(font);

		// Create text area with scroll
		newSchedule = new JTextArea();
		newSchedule.setEnabled(true);
		newSchedule.setFont(font);
		newSchedule.setText("New Class Schedule will be displayed here. " + frame.getHeight());
		newSchedule.setDisabledTextColor(Color.BLACK);
		scrollPane = new JScrollPane(newSchedule, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(100, 350, 800, 200);

		holidayList = new JTextArea();
		holidayList.setEnabled(false);
		holidayList.setFont(font);
		holidayList.setDisabledTextColor(Color.BLACK);
		scrollPane2 = new JScrollPane(holidayList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane2.setBounds(550, 210, 200, 40);

		// Adds the components to the GUI
		frame.add(loadFileButton);
		frame.add(fileNameToLoadTextField);
		frame.add(scrollPane);
		frame.add(scrollPane2);
		frame.add(createNewScheduleButton);
		frame.add(saveFileButton);
		frame.add(addHolidayButton);
		frame.add(clearButton);
		frame.add(startDatePicker);
		frame.add(endDatePicker);
		frame.add(holidayPicker);
		frame.add(classStartDateTextField);
		frame.add(classEndDateTextField);
		frame.add(holidayLabelTextField);
		frame.add(meetingDayLabelTextField);
		frame.add(mondayRadioButton);
		frame.add(tuesdayRadioButton);
		frame.add(wednesdayRadioButton);
		frame.add(thursdayRadioButton);
		frame.add(fridayRadioButton);

		// Set the title of the window
		setTitle("Class Scheduler");

		// Set the size of the window and display it
		// frame.setSize(WIDTH, HEIGHT);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	// Parameters are a File object and JTextArea object
	// The method displays the text in the file in the JTextArea passed as parameter
	// Return type is void
	public void printSchedule(File filename, JTextArea jTextArea) {
		jTextArea.setText("");
		Scanner myReader = null;
		try {
			myReader = new Scanner(filename);
			Calendar calendar = Calendar.getInstance();
			Calendar endCalendar = Calendar.getInstance();
			timestamp = new Timestamp(new Date().getTime());
			Date selectedDate = (Date) startDatePicker.getModel().getValue();
			Date endDate = (Date) endDatePicker.getModel().getValue();
			calendar.setTime(selectedDate);
			endCalendar.setTime(endDate);
			// Loops through the file line by line
			while (myReader.hasNextLine()) {
				// Backend code to handle all functions
				String entryLine = myReader.nextLine();
				// https://javacodex.com/Date-and-Time/Add-Time-To-A-Timestamp
				timestamp = new Timestamp(calendar.getTime().getTime());
				jTextArea.append(timestamp.toString().split(" ")[0] + " " + entryLine + "\n");
				calendar.add(Calendar.DAY_OF_MONTH, getNextDay(calendar.get(Calendar.DAY_OF_WEEK)));
				// Checks if the next day is a holiday, if it is go to the next meet day.
				for (int index = 0; index < getHolidayList().size(); index++) {
					timestamp = new Timestamp(calendar.getTime().getTime());
					if (timestamp.toString().split(" ")[0].compareTo(getHolidayList().get(index)) == 0) {
						calendar.add(Calendar.DAY_OF_MONTH, getNextDay(calendar.get(Calendar.DAY_OF_WEEK)));
					}
				}
				// Informs user that the calendar exceeded the end date input by user.
				if (calendar.after(endCalendar)) {
					JOptionPane.showMessageDialog(null,
							"There are more sessions, than available dates. You will have to manually edit the schedule.",
							"Alert", JOptionPane.INFORMATION_MESSAGE);
				}

			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (myReader != null)
				myReader.close();
		}
	}

	// No parameters
	// Checks if radio button is selected, and if so adds appropriate number to
	// ArrayList
	// Return type is ArrayList of numbers associated with radio buttons selected.
	public ArrayList<Integer> getRadioButtonDay() {
		ArrayList<Integer> buttonDays = new ArrayList<Integer>();
		buttonDays.add(0);
		if (mondayRadioButton.isSelected()) {
			buttonDays.add(2);
		}
		if (tuesdayRadioButton.isSelected()) {
			buttonDays.add(3);
		}
		if (wednesdayRadioButton.isSelected()) {
			buttonDays.add(4);
		}
		if (thursdayRadioButton.isSelected()) {
			buttonDays.add(5);
		}
		if (fridayRadioButton.isSelected()) {
			buttonDays.add(6);
		}
		return buttonDays;
	}

	// Parameter is an int value associated with start date.
	// Loops through the ArrayList returned by the getRadioButtonDay method to
	// determine next meet day.
	// Return type is an int value associated with next meeting date.
	public int getNextDay(int startDate) {
		int nextDay = 8;
		// Handles when the next meet day is in the same week.
		for (int index = 1; index < getRadioButtonDay().size(); index++) {
			if (getRadioButtonDay().get(index) > startDate) {
				int tempNextDay = getRadioButtonDay().get(index) - startDate;
				// Keeps track of the "earliest" next day.
				if (tempNextDay < nextDay) {
					nextDay = tempNextDay;
				}
			}
		}
		// Handles when the next meet day is in the next week.
		if (nextDay > 7) {
			for (int index = 1; index < getRadioButtonDay().size(); index++) {
				if (getRadioButtonDay().get(index) <= startDate) {
					int tempNextDay = getRadioButtonDay().get(index) - startDate + 7;
					// Keeps track of the "earliest" next day.
					if (tempNextDay < nextDay) {
						nextDay = tempNextDay;
					}
				}
			}
		}
		return nextDay;
	}

	// The method saves the text in the JTextArea to a textfile in the project
	// directory
	// No parameters
	// Return type is void
	public void saveFile() {
		// https://docs.oracle.com/javase/tutorial/uiswing/components/
		File openDirectory = new File(System.getProperty("user.dir"));
		fileChooser.setCurrentDirectory(openDirectory);
		int value = fileChooser.showSaveDialog(SchedulerGUI.this);
		if (value == JFileChooser.APPROVE_OPTION) {
			File outputFile = fileChooser.getSelectedFile();
			// Handles if user tries to save as anything but a .txt file.
			if (outputFile.getName().endsWith(".txt") == false) {
				JOptionPane.showMessageDialog(null,
						"Error, You did not select a proper file type to save. Please save as a .txt file.", "alert",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			BufferedWriter writer = null;
			try {
				writer = new BufferedWriter(new FileWriter(outputFile));
				writer.append(newSchedule.getText());
				writer.close();
				// Informs user that file was successfully saved.
				JOptionPane.showMessageDialog(null,
						"File saved successfully. File saved to " + outputFile.getAbsolutePath(), "Alert",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				System.out.print("Unable to save file with that directory.");
			}
		}
	}

	// No parameters
	// Adds the calendar selected to the holidayList JTextArea
	// Return type is void
	public void addHolidayToList() {
		Calendar calendar = Calendar.getInstance();
		holidayTimestamp = new Timestamp(new Date().getTime());
		Date selectedDate = (Date) holidayPicker.getModel().getValue();
		calendar.setTime(selectedDate);
		holidayTimestamp = new Timestamp(calendar.getTime().getTime());
		holidayList.append(holidayTimestamp.toString().split(" ")[0] + "\n");
	}

	// No parameters
	// If end date is before start date return false else return true.
	// Return type is boolean.
	public boolean checkEndHoliday() {
		Date startDate = (Date) startDatePicker.getModel().getValue();
		Date endDate = (Date) endDatePicker.getModel().getValue();
		if (endDate.before(startDate) == true) {
			return false;
		} else {
			return true;
		}
	}

	// No parameters
	// Takes the text in the holidayList JTextArea and stores in a List
	// Return type is List<String>
	public List<String> getHolidayList() {
		String temp = holidayList.getText();
		String[] holidayStringArray = temp.split("\n");
		List<String> holidayStringList = Arrays.asList(holidayStringArray);
		return holidayStringList;
	}

	// Handles user actions (button clicks) in the GUI
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() instanceof JButton) {
			// Creates an instance of a JButton object
			JButton clickedButton = (JButton) e.getSource();
			// Handles when the "Load File" button is clicked
			// Similar code used in CS 405 Project 2
			if (clickedButton == loadFileButton) {
				File openDirectory = new File(System.getProperty("user.dir"));
				fileChooser.setCurrentDirectory(openDirectory);
				int value = fileChooser.showOpenDialog(SchedulerGUI.this);
				if (value == JFileChooser.APPROVE_OPTION) {
					File fileToTest = fileChooser.getSelectedFile();
					fileNameToLoadTextField.setText(fileToTest.getName());
				}
			}
			// Handles when the "Create New Schedule Button" is clicked
			if (clickedButton == createNewScheduleButton) {
				// Handles when user forgot to select a file.
				if (fileNameToLoadTextField.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Error, You did not select a file to load. Please try again.",
							"alert", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Handles when user attempts to load a non .txt file.
				if (fileNameToLoadTextField.getText().endsWith(".txt") == false) {
					JOptionPane.showMessageDialog(null,
							"Error, You did not select a proper file type to load. Please load a .txt file.", "alert",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				File file = new File(fileNameToLoadTextField.getText());
				// Handles when user forgot to select meet day/s.
				if (getRadioButtonDay().size() == 1) {
					JOptionPane.showMessageDialog(null, "Error, You did not select a meeting day. Please try again.",
							"alert", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Handles when user forgot to select a start date
				if (startDatePicker.getModel().getValue() == null) {
					JOptionPane.showMessageDialog(null, "Error, You did not select a start date. Please try again.",
							"alert", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Handles when user forgot to select an end date
				if (endDatePicker.getModel().getValue() == null) {
					JOptionPane.showMessageDialog(null, "Error, You did not select an end date. Please try again.",
							"alert", JOptionPane.ERROR_MESSAGE);
					return;
				}
				// Handles if user selected an end date before start date.
				if (checkEndHoliday() == false) {
					JOptionPane.showMessageDialog(null,
							"Error, Your end date is before your start date. Please try again.", "alert",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				printSchedule(file, newSchedule);
				clearButton.setVisible(true);
			}
			// Handles when the "Save File Button" is clicked
			if (clickedButton == saveFileButton) {
				saveFile();
			}
			if (clickedButton == addHolidayButton) {
				// Handles if nothing was selected from the holidayPicker
				if (holidayPicker.getModel().getValue() == null) {
					JOptionPane.showMessageDialog(null, "Error, You did not select a Holiday. Please try again.",
							"alert", JOptionPane.ERROR_MESSAGE);
					return;
				}
				addHolidayToList();
			}
			// Handles when the "Clear Button" is clicked
			if (clickedButton == clearButton) {
				newSchedule.setText("");
			}
		}
	}

	public static void main(String[] args) {

		SchedulerGUI scheduler = new SchedulerGUI();
	}

}

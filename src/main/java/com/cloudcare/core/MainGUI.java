package com.cloudcare.core;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.openqa.selenium.InvalidArgumentException;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class MainGUI implements ActionListener {

	// Initialize objects
	private JFrame mainFrame;
	private JLabel mainLabel;
	private JPanel panel;
	private ChromeDriver driver;

	// Initialize variables
	private String url = "https://www.google.it/";
	private String chromeLbl = "Chrome";
	private String jFrameTlt = "CloudCare Test";
	private String tltExpt = "Google";

	public MainGUI() {

		// Create JFrame object
		mainFrame = new JFrame();

		// Create JButton and JLabel objects
		mainLabel = new JLabel("Open 'www.google.it' in chrome browser:");

		JButton chromeButton = new JButton(chromeLbl);
		chromeButton.addActionListener(this);

		// Create JPanel object
		panel = new JPanel();
		panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 10, 25));
		panel.setLayout(new GridLayout(0, 1));
		panel.add(mainLabel);
		panel.add(chromeButton);

		// Set default window parameters
		mainFrame.add(panel, BorderLayout.CENTER);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setTitle(jFrameTlt);
		mainFrame.pack();
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {

		new MainGUI();
	}

	public void actionPerformed(ActionEvent arg0) {

		openUrlChromeBrowser();
	}

	
	/******** DRIVER SETUP **********/

	private void setupChromeDriver() {

		// Get chromedriver version presents on the system
		WebDriverManager wdm = WebDriverManager.chromedriver();
		wdm.setup();
		String driverPath = wdm.getDownloadedDriverPath();
		System.setProperty("webdriver.chrome.driver", driverPath);

		// Create chromeDriver object
		ChromeDriver chromeDriver = new ChromeDriver();

		// Delete all cookies and maximize window
		chromeDriver.manage().deleteAllCookies();
		chromeDriver.manage().window().maximize();

		// Set chromeDriver
		driver = chromeDriver;
	}

	/******** BASIC TEST EXAMPLE **********/
	
	private void openUrlChromeBrowser() {

		// Get chromedriver object
		setupChromeDriver();

		// Open URL requested
		boolean isUrlOpened = openUrlRequested(url);

		// Basic test Example
		if (isUrlOpened)
			verifyTltPage(driver);

	}
	
	
	private boolean openUrlRequested(String urlRequested) {

		try {

			// Try to open url requested
			driver.get(urlRequested);
			return true;

		} catch (InvalidArgumentException e) {

			// Manage url format wrong
			e.printStackTrace();
			System.out.println("Url format is wrong, check url string entered.");
			driver.quit();
			return false;
		}
	}
	

	private void verifyTltPage(ChromeDriver chromeDriver) {


		// Get actual title of the page
		String tltAct = chromeDriver.getTitle();

		/*
		 * Compare the actual title with the expected one and print the result as
		 * "Passed" or "Failed" 
		 * Usually I prefer to use some assertions lib (assertJ,
		 * testNG Assertions, etc..)
		 */
		if (tltAct.contentEquals(tltExpt)) {
			System.out.println("Test Passed!\nExpected result: " + tltExpt + " - Actual result: " + tltAct);
		} else {
			System.out.println("Test Failed\nExpected result: " + tltExpt + " - Actual result: " + tltAct);
		}

	}

}

package code.barathsv.cvssplitter;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class UserInterface 
{
	private JFrame frame;
	private JLabel inputFileLabel;
	private JLabel outputFolderLabel;
	private JSpinner spinner;
	private JLabel errorLabel;
	private JTextField filePrefix;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					UserInterface window = new UserInterface();
					window.frame.setVisible(true);
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	public UserInterface() 
	{
		frame = new JFrame("CVS File Splitter");
		frame.getContentPane().setBackground(Color.DARK_GRAY);
		frame.setBounds(200,200, 933, 245);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JLabel lblSelectInputFile = new JLabel("Input File : ");
		lblSelectInputFile.setForeground(Color.GREEN);
		lblSelectInputFile.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblSelectInputFile.setBounds(131, 11, 66, 14);
		frame.getContentPane().add(lblSelectInputFile);
		
		
		JLabel lblOutputFolder = new JLabel("Output Folder : ");
		lblOutputFolder.setForeground(Color.GREEN);
		lblOutputFolder.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOutputFolder.setBounds(108, 48, 89, 14);
		frame.getContentPane().add(lblOutputFolder);
		
		inputFileLabel = new JLabel("No File Selected");
		inputFileLabel.setForeground(Color.WHITE);
		inputFileLabel.setBounds(207, 11, 686, 14);
		frame.getContentPane().add(inputFileLabel);
		
		outputFolderLabel = new JLabel("No Folder Selected");
		outputFolderLabel.setForeground(Color.WHITE);
		outputFolderLabel.setBounds(207, 48, 686, 14);
		frame.getContentPane().add(outputFolderLabel);
		
		spinner = new JSpinner();
		spinner.setBounds(207, 80, 89, 20);
		spinner.setValue(100);
		frame.getContentPane().add(spinner);
		
		JLabel lblHowManyLines = new JLabel("Lines per file (including header) : ");
		lblHowManyLines.setForeground(Color.GREEN);
		lblHowManyLines.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblHowManyLines.setBounds(10, 83, 236, 14);
		frame.getContentPane().add(lblHowManyLines);
		
		JButton fileSelectorButton = new JButton("change");
		fileSelectorButton.addActionListener(selectPath(inputFileLabel, false));
		fileSelectorButton.setBounds(10, 11, 88, 14);
		frame.getContentPane().add(fileSelectorButton);
		
		JButton folderSelectorButton = new JButton("change");
		folderSelectorButton.addActionListener(selectPath(outputFolderLabel, true));
		folderSelectorButton.setBounds(10, 48, 88, 14);
		frame.getContentPane().add(folderSelectorButton);
		
		JButton btnRun = new JButton("Run");
		btnRun.addActionListener(runApplication());
		btnRun.setBounds(9, 172, 89, 23);
		frame.getContentPane().add(btnRun);
		
		errorLabel = new JLabel("");
		errorLabel.setForeground(Color.RED);
		errorLabel.setBounds(110, 172, 797, 23);
		frame.getContentPane().add(errorLabel);
		
		JLabel lblOutputFilePrefix = new JLabel("Output File Prefix : ");
		lblOutputFilePrefix.setForeground(Color.GREEN);
		lblOutputFilePrefix.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblOutputFilePrefix.setBounds(10, 123, 187, 14);
		frame.getContentPane().add(lblOutputFilePrefix);
		
		filePrefix = new JTextField();
		filePrefix.setBounds(131, 120, 165, 20);
		frame.getContentPane().add(filePrefix);
		filePrefix.setColumns(10);
		
	}

	private ActionListener runApplication()
	{
		return new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				MainApplication app = new MainApplication();
				try 
				{
					int numFilesCreated = app.splitFile(inputFileLabel.getText(), outputFolderLabel.getText(), (int) spinner.getValue(), filePrefix.getText());
					errorLabel.setText("Created " + numFilesCreated + " new files.");
				} 
				catch (Exception e) 
				{
					errorLabel.setText(e.getMessage());
					e.printStackTrace();
				}
			}
		};
	}
	
	private ActionListener selectPath(final JLabel label, final boolean searchDirectory) 
	{
		return new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				JFileChooser fileChooser = new JFileChooser();
				if(searchDirectory)
				    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				fileChooser.setBounds(100, 100, 800, 400);
				frame.getContentPane().add(fileChooser);
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION)
				{
				    String selectedFile = fileChooser.getSelectedFile().getAbsolutePath();
				    label.setText(selectedFile);
				    frame.getContentPane().remove(fileChooser);
				}
			}
		};
	}
}
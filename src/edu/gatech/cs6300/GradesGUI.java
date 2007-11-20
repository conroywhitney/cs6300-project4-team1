package edu.gatech.cs6300;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.Rectangle;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;

public class GradesGUI {
		
	private static JFrame jFrame;  //  @jve:decl-index=0:visual-constraint="107,6"
	private static JPanel jContentPane;
	private static JComboBox jComboBox;
	private static JTextArea jTextArea;
	private static JButton jButton;
	private ArrayList<Student> studentList;
		
	public GradesGUI(){
		this.getJFrame();
		if(this.studentList == null || this.studentList.size() == 0) {
			jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
			jButton.setEnabled(false);
			jTextArea.setEnabled(false);
			studentList= new ArrayList<Student>();
		}		
		jFrame.setVisible(true);
	}	
	
	/**
	 * Given a HashSet<Students> fill in combobox appropriately
	 * Only fill with students names -- will lazy-load info when selected
	 */
	public void populateComboStudents(HashSet<Student> students) {	
		for (Student student : students){
			jComboBox.addItem(student);		
		}
	}
		
	/**
	 * Count number of students in the combobox
	 */
	public int getNumStudentsInComboBox() {
		return jComboBox.getItemCount();
		
	}

	/**
	 * Given an index, set the selected Student
	 */
	public void setSelectedStudent(int index) {
		jComboBox.setSelectedIndex(index);
	}
	
	/**
	 * @return the Student currently selected in the combobox
	 */
	public Student getSelectedStudent() {		
		return (Student) jComboBox.getSelectedItem();		
	}
	
	/**
	 * @return the value in the StudentName Label
	 */
	public String getStudentNameLabel() {
		return getLabelValue("Name: ");
	}
	
	/**
	 * @return the value in the StudentGTID Label
	 */
	public String getStudentGTIDLabel() {
		return getLabelValue("GTID: ");
	}
	
	/**
	 * @return the value in the StudentEmail Label
	 */
	public String getStudentEmailLabel() {
		return getLabelValue("Email: ");
	}
	
	private String getLabelValue(String sLabel) {
		String sValue = null;
		int iLabelLength = sLabel.length();
		
		String text = jTextArea.getText();
				
		if (text.length() > 0) {
			int iLabelIndex = text.indexOf(sLabel);
			if (iLabelIndex >= 0) {
				sValue = text.substring(iLabelIndex + iLabelLength, text.indexOf("\n", iLabelIndex));
			}
		}
		return sValue;
	}
	
	/**
	 * @return the value in the StudentAttendance Label, cast to Integer
	 */
	public int getStudentAttendanceLabel() {
		String sAttendance = null; 
		int iAttendance = 0;
		String text = jTextArea.getText();
		
		if(text.length() > 0) {
			sAttendance = text.substring(text.indexOf("Attendance: ")+12,text.indexOf("%"));
			
			try {
				iAttendance= Integer.parseInt(sAttendance);
			} catch(Exception ex) {
				System.err.println(sAttendance);
			}
		}
		return iAttendance;		
	}
	
	private int getGradeLabelValueInt(String sLabel) {
		String sValue = getLabelValue(sLabel);
		int iValue = 0;
		try {
			iValue= Integer.parseInt(sValue);
		} catch(Exception ex) {
			System.err.println("Error parsing integer of: " + sLabel + ", " + sValue);
		}
		return iValue;
	}
	
	private Float getGradeLabelValueFloat(String sLabel) {
		String sValue = getLabelValue(sLabel);
		Float fValue = null;
		try {
			fValue= Float.parseFloat(sValue);
		} catch(Exception ex) {
			System.err.println("Error parsing integer of: " + sLabel + ", " + sValue);
		}
		return fValue;
	}
	
	public int getProjectTeamGradeLabel(int iProjectNumber) {
		return getGradeLabelValueInt("Project " + iProjectNumber + " team grade: ");
	}
	
	public Float getProjectContributionLabel(int iProjectNumber) {
		return getGradeLabelValueFloat("Project " + iProjectNumber + " Average contribution: ");
	}
	
	public int getProjectAverageGradeLabel(int iProjectNumber) {
		return getGradeLabelValueInt("Project " + iProjectNumber + " Average grade: ");
	}
	
	public int getAssignmentAvgGradeLabel(int iAssignmentNumber) {
		return getGradeLabelValueInt("Assignment " + iAssignmentNumber + " Average grade: ");
	}
	
	public int getStudentAssignmentGradeLabel(int iAssignmentNumber) {
		return getGradeLabelValueInt("Assignment " + iAssignmentNumber + " grade: ");
	}

	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		if (jFrame == null) {
			jFrame = new JFrame();
			jFrame.setSize(new Dimension(600, 450));
			jFrame.setContentPane(getJContentPane1());
			jFrame.setTitle("cs6300 GRADING TOOL");
		}
		return jFrame;
	}

	/**
	 * This method initializes jContentPane1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane1() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getJComboBox1(), null);
			jContentPane.add(getJTextArea1(), null);
			jContentPane.add(getJButton(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes jComboBox1	
	 * 	
	 * @return javax.swing.JComboBox	
	 */
	private JComboBox getJComboBox1(){
		if (jComboBox == null) {
			jComboBox = new JComboBox();
			jComboBox.setBounds(new Rectangle(15, 16, 272, 26));
			
			jComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
				public void propertyChange(java.beans.PropertyChangeEvent e) {
					if ((e.getPropertyName().equals("enabled"))) {
					}
				}
			});

			jComboBox.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {

					JComboBox cb = (JComboBox)e.getSource();

					Student selectedStudent = (Student)cb.getSelectedItem();
					
					String sInfo = selectedStudent.getBasicInfoForTextarea();
					
					Map<Integer, Team> studentTeams = selectedStudent.getTeams();

					for (int i=1; i<=studentTeams.keySet().size(); i++) {
						sInfo += studentTeams.get(i).project.getInfoForTextarea(selectedStudent);
					}
					
					Map<Integer, Assignment> assignments = selectedStudent.getAssignments();
					
					for (int j=1; j<= assignments.keySet().size(); j++){
						sInfo += assignments.get(j).getInfoForTextarea(selectedStudent);						
					}
					
					/* Using Student, display basic, project, and assignment info */
					jTextArea.setText(sInfo);
			        
			        /* Enable save button (since diff student) */
			        /* Should this ever really be disabled?? */
	        		jButton.setEnabled(true);
	        		
					jTextArea.setEnabled(true);
					
					/* TODO: Shouldn't have to do this */
					/* setSelectedStudent(cb.getSelectedIndex()); */
				}
			});
		}
		return jComboBox;
	}

	/**
	 * This method initializes jTextArea1	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getJTextArea1() {
		if (jTextArea == null) {
			jTextArea = new JTextArea();
			jTextArea.setBounds(new Rectangle(301, 22, 286, 387));
			jTextArea.setLineWrap(true);
		}
		return jTextArea;
	}
	
	/**
	 * This method initializes jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getJButton() {
		if (jButton == null) {
			jButton = new JButton();
			jButton.setBounds(new Rectangle(204, 53, 75, 29));
			jButton.setText("Save");

			jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					String filename = ((Student) jComboBox.getSelectedItem()).getName() + ".txt";
					
				    try {
				        BufferedWriter out = new BufferedWriter(new FileWriter(filename));
				        out.write(jTextArea.getText());
				        out.close();
				        jButton.setEnabled(false);
				    } catch (IOException ioe) {
				    	ioe.printStackTrace();
				    }
					
					final JFileChooser fc = new JFileChooser();
					int returnVal = fc.showSaveDialog(null);
					
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						try {
							PrintWriter pr = new PrintWriter(new FileWriter(file));
							pr.print(jTextArea.getText());
							pr.close();
							jButton.setEnabled(false);
						} catch (IOException ioe) {
							ioe.printStackTrace();
						}
					}
				}
			});
		}
		return jButton;
	}
}
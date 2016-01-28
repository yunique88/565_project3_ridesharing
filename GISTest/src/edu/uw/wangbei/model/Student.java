package edu.uw.wangbei.model;

public class Student {
	
	private int studentID;
	private String studentName;
	private int age;
	private String studentCol;

	public Student(int studentID, String studentName, int age, String studentCol) {
		this.studentID = studentID;
		this.studentName = studentName;
		this.age = age;
		this.studentCol = studentCol;
	}

	public int getStudentID() {
		return studentID;
	}

	public void setStudentID(int studentID) {
		this.studentID = studentID;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getStudentCol() {
		return studentCol;
	}

	public void setStudentCol(String studentCol) {
		this.studentCol = studentCol;
	}
	
	@Override
	public String toString() {
		return "studentID: " + studentID + " studentName: " + studentName + " age: " + age + " studentCol: " + studentCol;
	}
}

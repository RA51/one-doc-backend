package com.doc.central.app.domain;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name ="users")
public class Users implements Serializable  {

	private static final long serialVersionUID = 3810769506700193046L;
	@Id
    @Column(name ="user_id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private BigInteger userId;
	
	@Column(name ="first_name")
	private String firstName;
	
	@Column(name ="last_name")
	private String lastName;
	
	@Column(name ="dob")
	private Date dOB;
	
	@Column(name ="sex")
	private String sex;
    
	@Column(name ="age")
	private int age;
	
	@Column(name ="mother_name")
	private String motherName;
    
	@Column(name ="father_name")
	private String fatherName;
	
	@Column(name ="profession")
	private String profession;
	
	@Column(name ="address")
	private String address;
	
	@Column(name ="profile_image")
	private String profileImage;
	
	@Column(name ="adhar_No")
	private BigInteger adharNo;
	
	@Column(name ="city")
	private String city;
	
	@Column(name ="state")
	private String state;
	
	public BigInteger getUserId() {
		return userId;
	}

	public void setUserId(BigInteger userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getMotherName() {
		return motherName;
	}

	public void setMotherName(String motherName) {
		this.motherName = motherName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
    
	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public Date getdOB() {
		return dOB;
	}

	public void setdOB(Date dOB) {
		this.dOB = dOB;
	}
 // @JsonIgnore
	public BigInteger getAdharNo() {
		return adharNo;
	}

	public void setAdharNo(BigInteger adharNo) {
		this.adharNo = adharNo;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
}

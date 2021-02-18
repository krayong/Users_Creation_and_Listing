package com.krayong.users.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.UUID;

public class User implements Parcelable {
	public static final Creator<User> CREATOR = new Creator<User>() {
		@Override
		public User createFromParcel(Parcel in) {
			return new User(in);
		}
		
		@Override
		public User[] newArray(int size) {
			return new User[size];
		}
	};
	
	private String id;
	private String firstName;
	private String lastName;
	private long dateOfBirth;
	private Gender gender;
	private String country;
	private String state;
	private String homeTown;
	private String phoneNumber;
	private String telNumber;
	private long creationDate;
	private String imageUrl;
	
	private boolean firstNameValid, lastNameValid, dobValid, genderValid, countryValid, stateValid, homeTownValid, phoneNumberValid, telNumberValid;
	
	public User() {
	}
	
	public User(String firstName, String lastName, long dateOfBirth, Gender gender, String country, String state, String homeTown, String phoneNumber, String telNumber, String imageUrl) {
		this.id = UUID.randomUUID().toString();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.country = country;
		this.state = state;
		this.homeTown = homeTown;
		this.phoneNumber = phoneNumber.trim();
		this.telNumber = telNumber.trim();
		this.creationDate = Calendar.getInstance().getTimeInMillis();
		this.imageUrl = imageUrl;
	}
	
	protected User(Parcel in) {
		id = in.readString();
		firstName = in.readString();
		lastName = in.readString();
		dateOfBirth = in.readLong();
		gender = (Gender) in.readSerializable();
		country = in.readString();
		state = in.readString();
		homeTown = in.readString();
		phoneNumber = in.readString();
		telNumber = in.readString();
		creationDate = in.readLong();
		imageUrl = in.readString();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
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
	
	public long getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(long dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	public Gender getGender() {
		return gender;
	}
	
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getHomeTown() {
		return homeTown;
	}
	
	public void setHomeTown(String homeTown) {
		this.homeTown = homeTown;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	public void setPhoneNumberValid(boolean phoneNumberValid) {
		this.phoneNumberValid = phoneNumberValid;
	}
	
	public String getTelNumber() {
		return telNumber;
	}
	
	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}
	
	public long getCreationDate() {
		return creationDate;
	}
	
	public void setCreationDate(long creationDate) {
		this.creationDate = creationDate;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	private String capitalize(String input) {
		String[] inputs = input.trim().toLowerCase().split("\\s+");
		StringBuilder output = new StringBuilder();
		for (String i : inputs) {
			i = i.trim();
			output.append(i.substring(0, 1).toUpperCase())
					.append(i.substring(1))
					.append(" ");
		}
		return output.toString().trim();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(id);
		dest.writeString(firstName);
		dest.writeString(lastName);
		dest.writeLong(dateOfBirth);
		dest.writeSerializable(gender);
		dest.writeString(country);
		dest.writeString(state);
		dest.writeString(homeTown);
		dest.writeString(phoneNumber);
		dest.writeString(telNumber);
		dest.writeLong(creationDate);
		dest.writeString(imageUrl);
	}
	
	public boolean isUserValid() {
		return firstNameValid && lastNameValid && dobValid && genderValid &&
				countryValid && stateValid && homeTownValid && phoneNumberValid && telNumberValid;
	}
	
	public String checkFirstName() {
		if (firstName.isEmpty()) {
			return "First Name cannot be empty";
		}
		firstName = capitalize(firstName);
		firstNameValid = true;
		return null;
	}
	
	public String checkLastName() {
		if (lastName.isEmpty()) {
			return "Last Name cannot be empty";
		}
		lastName = capitalize(lastName);
		lastNameValid = true;
		return null;
	}
	
	public String checkDateOfBirth() {
		Calendar dob = Calendar.getInstance();
		dob.setTimeInMillis(dateOfBirth);
		if (dob.compareTo(Calendar.getInstance()) > 0) {
			return "Date of Birth cannot be empty";
		}
		dobValid = true;
		return null;
	}
	
	public String checkGender() {
		if (gender.equals(Gender.UNDEFINED)) {
			return "Gender cannot be empty";
		}
		genderValid = true;
		return null;
	}
	
	public String checkCountry() {
		if (country.isEmpty()) {
			return "Country cannot be empty";
		}
		country = capitalize(country);
		countryValid = true;
		return null;
	}
	
	public String checkState() {
		if (state.isEmpty()) {
			return "State cannot be empty";
		}
		state = capitalize(state);
		stateValid = true;
		return null;
	}
	
	public String checkHomeTown() {
		if (homeTown.isEmpty()) {
			return "Home Town cannot be empty";
		}
		homeTown = capitalize(homeTown);
		homeTownValid = true;
		return null;
	}
	
	public String checkPhoneNumber() {
		if (phoneNumber.isEmpty()) {
			return "Phone number cannot be empty";
		} else if (!phoneNumber.matches("\\d+")) {
			return "Phone number should contain only digits";
		} else if (phoneNumber.length() != 10 && phoneNumber.length() != 12) {
			return "Phone number should be 10 digits long";
		}
		phoneNumberValid = true;
		return null;
	}
	
	public String checkTelNumber() {
		if (telNumber.isEmpty()) {
			return "Telephone number cannot be empty";
		} else if (!telNumber.matches("\\d+")) {
			return "Telephone number should contain only digits";
		} else if (telNumber.length() != 8 && telNumber.length() != 10 && telNumber.length() != 12) {
			return "Telephone number should be 10 digits long";
		}
		telNumberValid = true;
		return null;
	}
}

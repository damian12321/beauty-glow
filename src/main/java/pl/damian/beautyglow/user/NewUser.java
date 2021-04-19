package pl.damian.beautyglow.user;



import org.springframework.format.annotation.DateTimeFormat;
import pl.damian.beautyglow.validation.FieldMatch;
import pl.damian.beautyglow.validation.ValidEmail;

import javax.persistence.Column;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;


import java.util.Date;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = " się nie zgadza")
})
public class NewUser {

	@ValidEmail
	@NotNull(message = "jest wymagane")
	@Size(min = 1, message = "jest wymagane")
	private String email;

	private String oldPassword;

	private String oldEmail;

	@NotNull(message = "jest wymagane")
	@Size(min = 1, message = "jest wymagane")
	private String password;
	
	@NotNull(message = "jest wymagane")
	@Size(min = 1, message = "jest wymagane")
	private String matchingPassword;

	@NotNull(message = "jest wymagane")
	@Size(min = 1, message = "jest wymagane")
	private String firstName;

	@NotNull(message = "jest wymagane")
	@Size(min = 1, message = "jest wymagane")
	private String lastName;

	private String phoneNumber;

	@DateTimeFormat(pattern="dd.MM.yyyy")
	@Column(name = "date_of_birth")
	@Past
	private Date date;

	public NewUser() {

	}

	public NewUser(String email, String password, String matchingPassword, String firstName, String lastName, Date date) {
		this.email = email;
		this.password = password;
		this.matchingPassword = matchingPassword;
		this.firstName = firstName;
		this.lastName = lastName;
		this.date = date;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date  date) {
		this.date = date;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}
}

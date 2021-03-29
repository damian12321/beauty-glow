package pl.damian.beautyglow.user;


import pl.damian.beautyglow.validation.FieldMatch;
import pl.damian.beautyglow.validation.ValidEmail;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "matchingPassword", message = " się nie zgadza")
})
public class NewUser {

	@ValidEmail
	@NotNull(message = "jest wymagane")
	@Size(min = 1, message = "jest wymagane")
	private String email;

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

	public NewUser() {

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

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}

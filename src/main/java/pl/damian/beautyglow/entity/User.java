package pl.damian.beautyglow.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Past;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "email")
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "phone_number")
	private String phoneNumber;
	@DateTimeFormat(pattern="dd.MM.yyyy")
	@Column(name = "date_of_birth")
	@Past
	private Date date;

	@Column(name = "active")
	private boolean active;

	@Column(name = "validation_key")
	private String validationKey;

	@OneToOne(cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Form form;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "users_roles", 
	joinColumns = @JoinColumn(name = "user_id"), 
	inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	@OneToMany(mappedBy = "user")
	List<UsersTreatments> usersTreatments;

	public User() {
	}

	public User(String email, String password, String firstName, String lastName) {
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(String email, String password, String firstName, String lastName,
				List<Role> roles) {
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public boolean getIsActive() {
		return active;
	}

	public void setIsActive(boolean active) {
		this.active = active;
	}

	public String getValidationKey() {
		return validationKey;
	}

	public void setValidationKey(String validationKey) {
		this.validationKey = validationKey;
	}

	public Date  getDate() {
		return date;
	}

	public void setDate(Date  date) {
		this.date = date;
	}

	public List<UsersTreatments> getUsersTreatments() {
		return usersTreatments;
	}

	public void setUsersTreatments(List<UsersTreatments> usersTreatments) {
		this.usersTreatments = usersTreatments;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id  + ", password='" + "*********" + '\''
				+ ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + ", email='" + email + '\''
				+ ", roles=" + roles +", active="+getIsActive()+  ", treatments=" + usersTreatments+'}';
	}
}

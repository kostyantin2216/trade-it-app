package com.tradeitsignals.datamodel.data;

import com.tradeitsignals.datamodel.DataModelObject;

import java.util.Date;

public class User implements DataModelObject {

	private Integer id;
	private String firstName;
	private String lastName;
	private String password;
	private String tempPassword;
	private String email;
	private String telephone;
	private Integer countryId;
	private String country;
	private String channels;
	private boolean isRegistered;
	private boolean isAdmin;
	private boolean isOnTrial;
	private Date tempPasswordRequestTime;
	private Date lastPayment;
	private Date updatedAt;
	private Date createdAt;
	
	public User() { }

	public User(Integer id, String firstName, String lastName, String password, String tempPassword,
				String email, String telephone, Integer countryId, String country, String channels,
				boolean isRegistered, boolean isAdmin, boolean isOnTrial,
				Date tempPasswordRequestTime, Date lastPayment, Date updatedAt, Date createdAt) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.tempPassword = tempPassword;
		this.email = email;
		this.telephone = telephone;
		this.countryId = countryId;
		this.country = country;
		this.channels = channels;
		this.isRegistered = isRegistered;
		this.isAdmin = isAdmin;
		this.isOnTrial = isOnTrial;
		this.tempPasswordRequestTime = tempPasswordRequestTime;
		this.lastPayment = lastPayment;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}

	public User(String firstName, String lastName, String password, String tempPassword, String email,
				String telephone, Integer countryId, String country, String channels,
				boolean isRegistered, boolean isAdmin, boolean isOnTrial,
				Date tempPasswordRequestTime, Date lastPayment, Date updatedAt, Date createdAt) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.tempPassword = tempPassword;
		this.email = email;
		this.telephone = telephone;
		this.countryId = countryId;
		this.country = country;
		this.channels = channels;
		this.isRegistered = isRegistered;
		this.isAdmin = isAdmin;
		this.isOnTrial = isOnTrial;
		this.tempPasswordRequestTime = tempPasswordRequestTime;
		this.lastPayment = lastPayment;
		this.updatedAt = updatedAt;
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getCountryId() {
		return countryId;
	}

	public void setCountryId(Integer countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getChannels() {
		return channels;
	}

	public void setChannels(String channels) {
		this.channels = channels;
	}

	public boolean isRegistered() {
		return isRegistered;
	}

	public void setIsRegistered(boolean isRegistered) {
		this.isRegistered = isRegistered;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public boolean isOnTrial() {
		return isOnTrial;
	}

	public void setIsOnTrial(boolean isOnTrial) {
		this.isOnTrial = isOnTrial;
	}

	public Date getTempPasswordRequestTime() {
		return tempPasswordRequestTime;
	}

	public void setTempPasswordRequestTime(Date tempPasswordRequestTime) {
		this.tempPasswordRequestTime = tempPasswordRequestTime;
	}

	public Date getLastPayment() {
		return lastPayment;
	}

	public void setLastPayment(Date lastPayment) {
		this.lastPayment = lastPayment;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				", password='" + password + '\'' +
				", tempPassword='" + tempPassword + '\'' +
				", email='" + email + '\'' +
				", telephone='" + telephone + '\'' +
				", countryId=" + countryId +
				", country='" + country + '\'' +
				", channels='" + channels + '\'' +
				", isRegistered=" + isRegistered +
				", isAdmin=" + isAdmin +
				", isOnTrial=" + isOnTrial +
				", tempPasswordRequestTime=" + tempPasswordRequestTime +
				", lastPayment=" + lastPayment +
				", updatedAt=" + updatedAt +
				", createdAt=" + createdAt +
				'}';
	}

}

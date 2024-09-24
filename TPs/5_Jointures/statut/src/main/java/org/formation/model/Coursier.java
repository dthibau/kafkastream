package org.formation.model;

public class Coursier {

	private String id;
	private Statut statut;
	private String firstName, lastName;

	public Coursier() {

	}
	public Coursier(String id, Statut statut, String firstName, String lastName) {
		this.id = id;
		this.statut = statut;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public Statut getStatut() {
		return statut;
	}

	public void setStatut(Statut statut) {
		this.statut = statut;
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

	@Override
	public String toString() {
		return "Coursier{" +
				"id='" + id + '\'' +
				", statut=" + statut +
				", firstName='" + firstName + '\'' +
				", lastName='" + lastName + '\'' +
				'}';
	}
}

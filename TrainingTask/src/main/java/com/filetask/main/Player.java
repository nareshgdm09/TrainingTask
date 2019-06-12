package com.filetask.main;

import java.util.Date;

public class Player {
	private int id;
	private String json_file;
	private String xml_file;
	private Date create_time;
	private String status;
	private String name;
	private String address;
	private Date dob;
	private int runs;
	private int wickets;

	public Player() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Player(int id, String json_file, String xml_file, Date create_time, String status, String name,
			String address, Date dob, int runs, int wickets) {
		super();
		this.id = id;
		this.json_file = json_file;
		this.xml_file = xml_file;
		this.create_time = create_time;
		this.status = status;
		this.name = name;
		this.address = address;
		this.dob = dob;
		this.runs = runs;
		this.wickets = wickets;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJson_file() {
		return json_file;
	}

	public void setJson_file(String json_file) {
		this.json_file = json_file;
	}

	public String getXml_file() {
		return xml_file;
	}

	public void setXml_file(String xml_file) {
		this.xml_file = xml_file;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public int getRuns() {
		return runs;
	}

	public void setRuns(int runs) {
		this.runs = runs;
	}

	public int getWickets() {
		return wickets;
	}

	public void setWickets(int wickets) {
		this.wickets = wickets;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((create_time == null) ? 0 : create_time.hashCode());
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + id;
		result = prime * result + ((json_file == null) ? 0 : json_file.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + runs;
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + wickets;
		result = prime * result + ((xml_file == null) ? 0 : xml_file.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (create_time == null) {
			if (other.create_time != null)
				return false;
		} else if (!create_time.equals(other.create_time))
			return false;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (id != other.id)
			return false;
		if (json_file == null) {
			if (other.json_file != null)
				return false;
		} else if (!json_file.equals(other.json_file))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (runs != other.runs)
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (wickets != other.wickets)
			return false;
		if (xml_file == null) {
			if (other.xml_file != null)
				return false;
		} else if (!xml_file.equals(other.xml_file))
			return false;
		return true;
	}

}

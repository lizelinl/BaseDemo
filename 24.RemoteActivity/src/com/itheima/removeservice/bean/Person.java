package com.itheima.removeservice.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
	private Integer id;
	private String name;
	private Integer age;

	public Person() {
		super();
	}

	public Person(Integer id, String name, Integer age) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", age=" + age + "]";
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeInt(id);
		parcel.writeString(name);
		parcel.writeInt(age);
	}
	
	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
		public Person createFromParcel(Parcel parcel) {
			return new Person(parcel.readInt(), parcel.readString(), parcel.readInt());
		}
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};

}

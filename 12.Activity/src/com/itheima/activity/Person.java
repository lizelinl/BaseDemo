package com.itheima.activity;

import android.os.Parcel;
import android.os.Parcelable;

public class Person /*implements Serializable*/ implements Parcelable {	// 可以序列化(把Object转为byte[])
	private String name;
	private Integer age;

	public Person() {
		super();
	}

	public Person(String name, Integer age) {
		super();
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
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
	public void writeToParcel(Parcel parcel, int flags) {	// 定义怎么把Person对象写入Parcel
		parcel.writeString(name);
		parcel.writeInt(age);
	}
	
	public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {		// 用来从Parcel中获取Person
		public Person createFromParcel(Parcel parcel) {		// 定义怎么从Parcel中获取数据, 创建Person
			return new Person(parcel.readString(), parcel.readInt());
		}
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};

	// Parcelable在绑定服务的时候有用

}

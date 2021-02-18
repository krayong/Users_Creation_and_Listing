package com.krayong.users.models;

import java.io.Serializable;

import androidx.annotation.NonNull;

public enum Gender implements Serializable {
	MALE("Male"),
	FEMALE("Female"),
	NON_BINARY("Non Binary"),
	UNDEFINED("Undefined");
	
	private final String displayName;
	
	Gender(String displayName) {
		this.displayName = displayName;
	}
	
	@NonNull
	@Override
	public String toString() {
		return displayName;
	}
}
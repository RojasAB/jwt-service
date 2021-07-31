package com.alonesoft.jwt.constants;

public enum LiquibaseConstants {

	CHANGELOG_PREFIX("classpath:db.changelog/"), CHANGELOG_INIT("changelog-init.yml"),
	CHANGELOG_MASTER("changelog-master.yml");
	
	private String value;
	
	LiquibaseConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}

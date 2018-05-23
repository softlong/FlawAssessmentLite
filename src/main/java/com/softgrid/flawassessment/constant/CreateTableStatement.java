package com.softgrid.flawassessment.constant;



/**
 * @author Vincent Geng
 *
 * Created on 8 May 2018
 */
public enum CreateTableStatement {

	GB30582B("GB30582B",
			"CREATE TABLE GB30582B ("
					+ " id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n"
					+ " tube_t varchar(100),\n"
					+ " radius_R0 varchar(100),\n"
					+ " radius_R1 varchar(100),\n"
					+ " radius_R2 varchar(100),\n"
					+ " length_L varchar(100),\n"
					+ " deep_d varchar(100),\n"
					+ " createdDate timestamp,\n"
					+ " result varchar(250),\n"
					+ " CONSTRAINT pk_GB30582B PRIMARY KEY (id)"
					+ ")"),

	GB30582C("GB30582C",
			"CREATE TABLE GB30582C ("
					+ " id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n"
					+ " tube_t varchar(100),\n"
					+ " radius_D varchar(100),\n"
					+ " power_C varchar(100),\n"
					+ " Modulus_E varchar(100),\n"
					+ " power_P varchar(100),\n"
					+ " deep_H0 varchar(100),\n"
					+ " deep_d varchar(100),\n"
					+ " sigma_Y varchar(100),\n"
					+ " sigma_R varchar(100),\n"
					+ " comboBox varchar(50),\n"
					+ " createdDate timestamp,\n"
					+ " result varchar(250),\n"
					+ " CONSTRAINT pk_GB30582C PRIMARY KEY (id)"
					+ ")");

	
	private final String tableName;
	private final String statement;

	private CreateTableStatement(String tableName, String statement) {
		this.tableName = tableName;
		this.statement = statement;
	}
	
	public String getTableName() {
		return tableName;
	}

	public String getStatement() {
		return statement;
	}

	@Override
	public String toString() {
		return this.statement;
	}
	
}

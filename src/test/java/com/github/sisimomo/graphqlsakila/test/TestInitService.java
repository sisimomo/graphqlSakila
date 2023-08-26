package com.github.sisimomo.graphqlsakila.test;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TestInitService {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ObjectMapper objectMapper;

	/**
	 * Initializes a MariaDB database by executing SQL scripts for schema.
	 */
	public void initMariaDB() {
		executeSqlScript(new FileSystemResource("schema.sql"));
	}

	/**
	 * Resets a MariaDB database by dropping all tables.
	 */
	public void resetMariaDB() {
		jdbcTemplate.execute("SET foreign_key_checks = 0");
		jdbcTemplate.queryForList("show tables", String.class).forEach(table -> jdbcTemplate.execute("DROP TABLE " + table));
		jdbcTemplate.execute("SET foreign_key_checks = 1");
	}

	/**
	 * Executes an SQL script against the database.
	 *
	 * @param script The script parameter is a Resource object that represents an SQL script file.
	 */
	private void executeSqlScript(Resource script) {
		try {
			ScriptUtils.executeSqlScript(jdbcTemplate.getDataSource().getConnection(), script);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}

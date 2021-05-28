package com.blogapp;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@SpringBootTest
class BlogappApplicationTests {

	private final DataSource dataSource;

	@Autowired
	BlogappApplicationTests(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Test
	void appCanConnectToDB() {
		assertThat(dataSource).isNotNull();
		log.info("Datasource object is created");

		try(Connection connection = dataSource.getConnection()) {
			assertThat(connection).isNotNull();
			log.info("Connection --> {}", connection.getCatalog());
			assertThat(connection.getCatalog()).isEqualTo("blogdb");
		} catch (SQLException throwables) {
			log.info("Exception occured while connecting to the database --> {}", throwables.getMessage());
		}
	}

}

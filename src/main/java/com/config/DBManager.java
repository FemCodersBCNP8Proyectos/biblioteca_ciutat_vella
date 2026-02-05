package com.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import io.github.cdimascio.dotenv.Dotenv;

public class DBManager {

  static Dotenv dotenv = Dotenv.load();

  private static final String URL = "jdbc:postgresql://localhost:5432/biblioteca_ciutat_vella";
  private static final String USER = dotenv.get("DB_USER");
  private static final String PASS = dotenv.get("DB_PASS");


  public static Connection getConnection() throws SQLException{
    return DriverManager.getConnection(URL, USER, PASS);
  }

}

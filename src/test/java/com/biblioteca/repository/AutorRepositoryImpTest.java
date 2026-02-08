package com.biblioteca.repository;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.biblioteca.model.Autor;
import com.config.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;


public class AutorRepositoryImpTest {

  private AutorRepositoryImp autorRepository;
  private Connection mockConnection;
  private PreparedStatement mockPreparedStatement;
  private MockedStatic<DBManager> mockedDBManager;

  @Before
  public void setUp () throws SQLException{
    autorRepository = new AutorRepositoryImp();
    mockConnection = mock(Connection.class);
    mockPreparedStatement = mock(PreparedStatement.class);

    mockedDBManager = mockStatic(DBManager.class);
    mockedDBManager.when(DBManager::getConnection).thenReturn(mockConnection);  
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
  }

  @After 
  public void tearDown(){
    if (mockedDBManager != null) {
      mockedDBManager.close();
    }
  }

  @Test

  public void createAutorSavesSuccessfully () throws SQLException {

    Autor autor = new Autor();
    autor.setNombre("Andrew Hunt");
    when(mockPreparedStatement.executeUpdate()).thenReturn(1);

    autorRepository.createAutor(autor);

    verify(mockConnection).prepareStatement("INSERT INTO autores (nombre) VALUES (?)");
    verify(mockPreparedStatement).setString(1, "Andrew Hunt");
    verify(mockPreparedStatement).executeUpdate();

  }
  
}

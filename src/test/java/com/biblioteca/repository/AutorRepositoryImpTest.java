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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockedStatic;



  

public class AutorRepositoryImpTest {

  private AutorRepositoryImp autorRepository;
  private Connection mockConnection;
  private PreparedStatement mockPreparedStatement;
  private MockedStatic<DBManager> mockedDBManager;
  private Statement mockStatement;
  private ResultSet mockResultSet;

  @Before
  public void setUp () throws SQLException{
    autorRepository = new AutorRepositoryImp();
    mockConnection = mock(Connection.class);
    mockPreparedStatement = mock(PreparedStatement.class);
    mockStatement = mock(Statement.class);
    mockResultSet = mock(ResultSet.class);

    mockedDBManager = mockStatic(DBManager.class);
    mockedDBManager.when(DBManager::getConnection).thenReturn(mockConnection);  
    when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
    when(mockConnection.createStatement()).thenReturn(mockStatement);
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

 /*  @Test 

  public void selectAllAutorSuccessfully () throws SQLException{

    when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

    when(mockResultSet.next())
      .thenReturn(true)
      .thenReturn(true)
      .thenReturn(false);
    when(mockResultSet.getInt("id_autor"))
      .thenReturn(1)
      .thenReturn(2);
    when(mockResultSet.getString("nombre"))
      .thenReturn("Andrew Hunt")
      .thenReturn("Dale Carnegie"); 

    List<Autor> autores = autorRepository.selectAllAutor();

    assertEquals(2, autores.size());
    assertEquals(0, 0);

  }
    */
  
    
}

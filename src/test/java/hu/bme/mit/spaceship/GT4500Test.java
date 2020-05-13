package hu.bme.mit.spaceship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class GT4500Test {

  private GT4500 ship;
  private TorpedoStore mockPrimaryTS;
  private TorpedoStore mockSecondaryTS;

  @BeforeEach
  public void init(){
    mockPrimaryTS = mock (TorpedoStore.class);
    mockSecondaryTS = mock (TorpedoStore.class);
    this.ship = new GT4500();
    this.ship.injectDependencies(mockPrimaryTS, mockSecondaryTS, false);
  }

  @Test
  public void fireTorpedo_Single_Success(){
    // Arrange
    when(mockPrimaryTS.fire(1)).thenReturn(true);
    when (mockSecondaryTS.fire(1)).thenReturn(true);
    // Act
    final boolean result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTS, times(1)).fire(1);
    verify(mockSecondaryTS, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Success() {
    // Arrange
    when(mockPrimaryTS.fire(1)).thenReturn(true);
    when(mockSecondaryTS.fire(1)).thenReturn(true);
    // Act
    final boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(true, result);
    verify(mockPrimaryTS, times(1)).fire(1);
    verify(mockSecondaryTS, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_Failure(){
    // Arrange
    when(mockPrimaryTS.fire(1)).thenReturn(false);
    when(mockSecondaryTS.fire(1)).thenReturn(false);
    
    // Act
    final boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    
    // Assert
    assertEquals(false, result);
    verify(mockPrimaryTS, times(1)).fire(1);
    verify(mockSecondaryTS, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Failure(){
    // Arrange
    when(mockPrimaryTS.fire(1)).thenReturn(false);
    when(mockSecondaryTS.fire(1)).thenReturn(false);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryTS, times(1)).fire(1);
    verify(mockSecondaryTS, times(1)).fire(1);
  }

  @Test
  public void fireTorpedo_All_Empty() {
    when(mockPrimaryTS.isEmpty()).thenReturn(true);
    when(mockSecondaryTS.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.ALL);
    result = ship.fireTorpedo(FiringMode.ALL);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryTS, times(0)).fire(1);
    verify(mockSecondaryTS, times(0)).fire(1);
  }
  @Test
  public void fireTorpedo_Single_AllTorpedos_Empty(){
    // Arrange
    when(mockPrimaryTS.isEmpty()).thenReturn(true);
    when(mockSecondaryTS.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    result = ship.fireTorpedo(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryTS, times(2)).isEmpty();
    verify(mockSecondaryTS, times(2)).isEmpty();
  }

  @Test
  public void fireTorpedo_Single_Alternating_Success(){
    // Arrange
    when(mockPrimaryTS.fire(1)).thenReturn(true);
    when(mockSecondaryTS.fire(1)).thenReturn(true);
    // Act
    boolean firstTry = ship.fireTorpedo(FiringMode.SINGLE);
    boolean secondTry = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(true, firstTry);
    assertEquals(true, secondTry);
    verify(mockPrimaryTS, times(1)).fire(1);
    verify(mockSecondaryTS, times(1)).fire(1);
  }
  @Test
  public void fireTorpedo_Single_Second_Empty_Success(){
    // Arrange
    when(mockPrimaryTS.fire(1)).thenReturn(true);
    when(mockSecondaryTS.isEmpty()).thenReturn(true);
    // Act
    boolean firstTry = ship.fireTorpedo(FiringMode.SINGLE);
    boolean secondTry = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(true, firstTry);
    assertEquals(true, secondTry);
    verify(mockPrimaryTS, times(2)).fire(1);
    verify(mockSecondaryTS, times(1)).isEmpty();
  }

  @Test
  public void fireLazer_Single_Failure(){

    // Arrange
    when(mockPrimaryTS.isEmpty()).thenReturn(true);
    when(mockSecondaryTS.isEmpty()).thenReturn(true);

    // Act
    boolean result = ship.fireLaser(FiringMode.SINGLE);

    // Assert
    assertEquals(false, result);
    verify(mockPrimaryTS, times(0)).fire(1);
    verify(mockSecondaryTS, times(0)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_First_Empty_Success(){
    // Arrange
    when(mockPrimaryTS.isEmpty()).thenReturn(true);
    when(mockSecondaryTS.fire(1)).thenReturn(true);
    // Act
    boolean result_first_try = ship.fireTorpedo(FiringMode.SINGLE);
    boolean result_second_try = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(true, result_first_try);
    assertEquals(true, result_second_try);
    verify(mockPrimaryTS, times(2)).isEmpty();
    verify(mockSecondaryTS, times(2)).fire(1);
  }

  @Test
  public void fireTorpedo_Single_All_Empty_Failure(){
    // Arrange
    when(mockPrimaryTS.isEmpty()).thenReturn(true);
    when(mockSecondaryTS.isEmpty()).thenReturn(true);
    // Act
    boolean result = ship.fireTorpedo(FiringMode.SINGLE);
    // Assert
    assertEquals(false, result);
    verify(mockPrimaryTS, times(1)).isEmpty();
    verify(mockSecondaryTS, times(1)).isEmpty();
  }

  

  
}
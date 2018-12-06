package de.gurkenlabs.litiengine;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {

  @AfterEach
  public void cleanup() {
    final File configFile = new File(Game.getConfiguration().getFileName());
    if (configFile.exists()) {
      configFile.delete();
    }
  }

  @Test
  public void testGameInitialization() {
    Game.init(Game.COMMADLINE_ARG_NOGUI);

    assertNotNull(Game.getRenderLoop());
    assertNotNull(Game.getLoop());
    assertNotNull(Game.getCamera());
    assertNotNull(Game.getScreenManager());
    assertNotNull(Game.getPhysicsEngine());
    assertNotNull(Game.getRenderEngine());
  }

  private class Status {
    boolean wasCalled = false;
  }

  @Test
  public void testInitializedListeners() {
    final Status status = new Status();

    Game.addGameListener(new GameAdapter() {
      @Override
      public void initialized(String... args) {
        status.wasCalled = true;
      }
    });

    Game.init(Game.COMMADLINE_ARG_NOGUI);

    assertTrue(status.wasCalled);
  }

  @Test
  public void testStartedListeners() {
    final Status status = new Status();

    Game.addGameListener(new GameAdapter() {
      @Override
      public void started() {
        status.wasCalled = true;
      }
    });

    Game.init(Game.COMMADLINE_ARG_NOGUI);
    assertFalse(status.wasCalled);
    Game.start();
    assertTrue(status.wasCalled);
  }
}

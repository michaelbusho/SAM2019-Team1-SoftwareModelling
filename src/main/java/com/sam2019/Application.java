package com.sam2019;

import java.io.InputStream;
import java.util.Objects;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import spark.TemplateEngine;
import spark.template.freemarker.FreeMarkerEngine;

import com.sam2019.ui.WebServer;

/**
 * The entry point for the sam2019 web application.
 *
 * @author
 */
public final class Application {
  private static final Logger LOG = Logger.getLogger(Application.class.getName());

  //
  // Application Launch method
  //

  /**
   * Dependency Injection</a>
   *
   */
  public static void main(String[] args) {
    // initialize Logging
    try {

      ClassLoader classLoader = Application.class.getClassLoader();
      final InputStream logConfig = classLoader.getResourceAsStream("log.properties");
      LogManager.getLogManager().readConfiguration(logConfig);
    } catch (Exception e) {
      e.printStackTrace();
      System.err.println("Could not initialize log manager because: " + e.getMessage());
    }



    // The application uses FreeMarker templates to generate the HTML
    // responses sent back to the client. This will be the engine processing
    // the templates and associated data.
    final TemplateEngine templateEngine = new FreeMarkerEngine();

    // inject the freemarker engine into web server
    final WebServer webServer = new WebServer(templateEngine);

    // inject web server into application
    final Application app = new Application(webServer);

    // start the application up
    app.initialize();




  }

  //
  // Attributes
  //

  private final WebServer webServer;

  //
  // Constructor
  //

  private Application(final WebServer webServer) {
    // validation
    Objects.requireNonNull(webServer, "webServer must not be null");
    //
    this.webServer = webServer;
  }

  //
  // Private methods
  //

  private void initialize() {
    LOG.fine("Application is initializing.");

    // configure Spark and startup the Jetty web server
    webServer.initialize();

    // other applications might have additional services to configure

    LOG.fine("Application initialization complete.");

  }

}

package windymelt.timeline

import org.scalatra._

class Timeline extends ScalatraServlet {

  val app = new windymelt.application.App()

  app.UserService.create("Windymelt")

  get("/") {
    views.html.hello()
  }

}

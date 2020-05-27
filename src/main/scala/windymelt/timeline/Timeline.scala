package windymelt.timeline

import org.scalatra._
import org.joda.time.DateTime

class Timeline extends ScalatraServlet {

  val app = new windymelt.application.App()

  val user = app.userService.create("Windymelt")

  user match {
    case Left(value) => //nop
    case Right(u) =>
      val ev1 = u.createEvent(
        "birth",
        Some("I birthed"),
        DateTime.parse("1993-08-13T00:00:00+09:00")
      )
      u.createTimeline("my timeline", Seq(ev1))
  }

  get("/") {
    views.html.hello()
  }

}

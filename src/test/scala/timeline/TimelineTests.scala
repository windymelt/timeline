package timeline

import org.scalatra.test.scalatest._

class TimelineTests extends ScalatraFunSuite {

  addServlet(classOf[Timeline], "/*")

  test("GET / on Timeline should return status 200") {
    get("/") {
      status should equal (200)
    }
  }

}

package windymelt.timeline

import org.scalatra._
import org.scalatra.json._
import org.joda.time.DateTime

import org.json4s._
import org.json4s.{DefaultFormats, Formats}
import org.json4s.JsonAST.JNothing
import org.json4s.JsonAST.JNull

import windymelt.timeline.application.dto.DTOJSONSerializer._

// data receive object
case class Event(
    name: String,
    description: String,
    occurredAt: String
)

case class TimelineDRO(
    title: String,
    events: String
)

class Timeline
    extends ScalatraServlet
    with JacksonJsonSupport
    with XsrfTokenSupport
    with UrlGeneratorSupport /* reverse routing support */ {
  protected implicit lazy val jsonFormats: Formats = DefaultFormats

  val app = new windymelt.timeline.application.App()
  val user = app.userService.create("guest").right.get
  implicit val thistl = this

  val ev1 = user.createEvent(
    "爆誕",
    Some("オギャ～"),
    DateTime.parse("1993-08-13T00:00:00+09:00")
  )
  val ev11 = user.createEvent(
    "第一次禁酒宣言",
    Some("人間は時として到底守れそうもない約束をしてしまう。"),
    DateTime.parse("2020-03-26T18:00:00+09:00")
  )
  val ev2 = user.createEvent(
    "このアプリを作成した",
    Some("このアプリを作り始めた。実装言語はScalaである。"),
    DateTime.parse("2020-05-25T20:00:00+09:00")
  )
  val ev3 = user.createEvent(
    "UIがついた",
    Some("FomanticUIでUIがついた"),
    DateTime.parse("2020-05-29T22:00:00+09:00")
  )
  val tl = user.createTimeline("ジンジン人生", Seq(ev1, ev11, ev2, ev3))

  val evb1 =
    user.createEvent(
      "大本営設置",
      Some(
        "1903年の大本営条例の全部改正により軍事参議院が設置され、戦時においても初めて軍令機関が陸海軍並列対等となったことから、" +
          "陸軍の参謀総長、海軍の海軍軍令部長の両名ともに幕僚長とされた。"
      ),
      DateTime.parse("1904-02-11T00:00:00")
    )
  val evb2 = user.createEvent(
    "旅順口攻撃",
    Some(
      "この奇襲自体がロシア側からも非難されないのは、当時は攻撃開始の前に宣戦布告しなければならないという国際法の規定がなかったためである。この攻撃ではロシアの艦艇数隻に損傷を与えたが、大きな戦果はなかった。"
    ),
    DateTime.parse("1904-02-08")
  )
  val evb3 = user.createEvent(
    "仁川上陸",
    Some("同日，日本陸軍先遣部隊の第12師団木越旅団が日本海軍の第2艦隊瓜生戦隊の護衛を受けながら朝鮮の仁川に上陸した。"),
    DateTime.parse("1904-02-08")
  )
  val evb4 = user.createEvent(
    "仁川沖海戦",
    Some("仁川港外にて同地に派遣されていたロシアの巡洋艦ヴァリャーグと砲艦コレーエツを攻撃し自沈に追い込んだ。"),
    DateTime.parse("1904-02-09")
  )
  val tl2 = user.createTimeline("日露戦争", Seq(evb1, evb2, evb3, evb4))

  get("/") {
    val triageEvent =
      app.eventService
        .findByEditor(user)
        .map(app.timelineDTOFactory.toDTO(_))
        .toSeq

    views.html.hello(
      Seq(
        app.timelineDTOFactory.toDTO(tl),
        app.timelineDTOFactory.toDTO(tl2)
      ),
      triageEvent
    )
  }

  get("/timelines/:id") {
    import scala.util.control.Exception._
    val idString: String = params("id")
    val idOpt = allCatch opt { BigInt(idString) }
    val timelineDTO = for {
      id <- idOpt
      timeline <- app.timelineRepository.find(id)
    } yield app.timelineDTOFactory.toDTO(timeline)

    timelineDTO match {
      case Some(dto) => views.html.timeline(dto)
      case None      => views.html.notfound()
    }
  }

  get("/events/:id") {
    import scala.util.control.Exception._
    val idString: String = params("id")
    val idOpt = allCatch opt { BigInt(idString) }
    val eventDTO = for {
      id <- idOpt
      event <- app.eventRepository.find(id)
    } yield app.timelineDTOFactory.toDTO(event)

    eventDTO match {
      case Some(dto) => views.html.event(dto)
      case None      => views.html.notfound()
    }
  }

  get("/events/:id/edit") {
    import scala.util.control.Exception._
    val idString: String = params("id")
    val idOpt = allCatch opt { BigInt(idString) }
    val eventDTO = for {
      id <- idOpt
      event <- app.eventRepository.find(id)
    } yield app.timelineDTOFactory.toDTO(event)

    eventDTO match {
      case Some(dto) => views.html.eventedit(Some(dto), xsrfKey, xsrfToken)
      case None      => views.html.notfound()
    }
  }

  post("/-/events") {
    // Create new event
    // accepts JSON

    // returns JSON
    contentType = formats("json")

    val ast = parsedBody
    ast match {
      case JNothing =>
      // not json.
      case otherwise =>
        println(otherwise)
        val ev = ast.extract[Event]
        val occurredAt = DateTime.parse(ev.occurredAt) // TODO: Guard
        val createdEv =
          user.createEvent(ev.name, Some(ev.description), occurredAt, None)
        app.timelineDTOFactory.toDTO(createdEv).toJSON
    }
  }

  post("/-/timelines") {
    // create new timeline
    // accepts JSON

    // returns JSON
    contentType = formats("json")

    val ast = parsedBody
    ast match {
      case JNothing =>
      // not json.
      case otherwise =>
        println(otherwise)
        import scala.util.control.Exception._
        val tl = ast.extract[TimelineDRO]
        val eventIds: Seq[BigInt] =
          tl.events.split(",").flatMap { s => allCatch opt { BigInt(s) } }

        val createdTimeline =
          app.timelineService.create(
            title = tl.title,
            eventIds = eventIds,
            user
          )

        createdTimeline match {
          case Right(tl) =>
            app.timelineDTOFactory.toDTO(tl)
          case Left(_) =>
            status = 500
            "failed"
        }
    }
  }

  val editroom = get("/-/editroom") {
    views.html.editroom()
  }

  xsrfGuard("/-/edit")

  get("/-/edit") {
    views.html.eventedit(None, xsrfKey, xsrfToken)
  }

  post("/-/edit") {
    "OK!!"
  }
}

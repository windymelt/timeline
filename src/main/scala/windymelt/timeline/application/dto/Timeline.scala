package windymelt.timeline.application.dto

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

import org.json4s._
import org.json4s.jackson.Serialization
import org.json4s.jackson.Serialization.{read, write}

// TODO: 粒度調整（時刻不要かどうか，など）ができるようにする
// TimelineもしくはEventはみずからの時刻の粒度を知っている

// TODO: リボンのプロパティを実装できるようにする
object DTODateTimeFormatter {
  val mediumFormat = DateTimeFormat.mediumDateTime()

  implicit class LocalFormatPrintable(x: DateTime) {
    def localFormat: String = mediumFormat.print(x)
  }
}

object DTOJSONSerializer {
  implicit val formats = Serialization.formats(NoTypeHints)

  implicit class JSONableDTO(x: DTO) {
    // TODO: convert DateTime into String
    def toJSON: String = write(x)
  }
}

trait DTO

final case class Timeline(
    id: String,
    title: String,
    editor: User,
    events: Seq[Event],
    extendedFrom: Set[Timeline] = Set.empty
) extends DTO

final case class User(
    id: String,
    name: String
) extends DTO

final case class Event(
    id: String,
    name: String,
    description: String,
    occurredAt: DateTime
) extends DTO

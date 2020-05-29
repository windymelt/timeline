package windymelt.timeline.application.dto

import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

// TODO: 粒度調整（時刻不要かどうか，など）ができるようにする
// TimelineもしくはEventはみずからの時刻の粒度を知っている

// TODO: リボンのプロパティを実装できるようにする
object DTODateTimeFormatter {
  val mediumFormat = DateTimeFormat.mediumDateTime()

  implicit class LocalFormatPrintable(x: DateTime) {
    def localFormat: String = mediumFormat.print(x)
  }
}

final case class Timeline(
    id: BigInt,
    title: String,
    editor: User,
    events: Seq[Event],
    extendedFrom: Set[Timeline] = Set.empty
)

final case class User(
    id: BigInt,
    name: String
)

final case class Event(
    id: BigInt,
    name: String,
    description: String,
    occurredAt: DateTime
)

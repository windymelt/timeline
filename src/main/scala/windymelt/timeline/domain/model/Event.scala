package windymelt.timeline.domain.model

import com.github.nscala_time.time.Imports._

import windymelt.timeline.Types.ID

trait EventModelComponent {

  final case class Event(
      id: ID,
      name: String,
      description: Option[String],
      occurredAt: DateTime,
      until: Option[DateTime]
  )
}

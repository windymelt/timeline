package windymelt.timeline.domain.model

import com.github.nscala_time.time.Imports._

import windymelt.timeline.Types.ID
import windymelt.timeline.domain.repository.{
  EventRepositoryComponent,
  TimelineRepositoryComponent
}
import windymelt.timeline.domain.repository.IDComponent

trait UserModelComponent {
  self: EventRepositoryComponent
    with TimelineRepositoryComponent
    with TimelineModelComponent
    with EventModelComponent
    with IDComponent =>

  final case class User(id: ID, name: String)
}

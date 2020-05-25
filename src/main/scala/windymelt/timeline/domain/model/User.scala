package windymelt.timeline.domain.model

import com.github.nscala_time.time.Imports._

import windymelt.timeline.Types.ID
import windymelt.timeline.domain.repository.EventRepositoryComponent
import windymelt.timeline.domain.repository.IDComponent

trait UserModelComponent {
  self: EventRepositoryComponent with IDComponent =>

  final case class User(id: ID, name: String) {
    def createEvent(
        name: String,
        description: Option[String],
        occurredAt: DateTime,
        until: Option[DateTime]
    ): Event = {
      val eventId = ID.gen()
      val ev = Event(eventId, name, description, occurredAt, until)
      eventRepository.save(ev)
    }
  }
}

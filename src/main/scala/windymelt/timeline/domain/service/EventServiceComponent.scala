package windymelt.timeline.domain.service

import windymelt.timeline.domain.model.EventModelComponent
import windymelt.timeline.domain.DomainError

trait EventServiceComponent {
  self: EventModelComponent =>
  val eventService: EventService

  trait EventService {
    def delete(event: Event): Either[DomainError, Unit]
  }
}

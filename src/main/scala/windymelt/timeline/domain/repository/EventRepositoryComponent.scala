package windymelt.timeline.domain.repository

import windymelt.timeline.domain.model.EventModelComponent

trait EventRepositoryComponent {
  self: EventModelComponent =>
  val eventRepository: EventRepository

  trait EventRepository {
    def save(event: Event): Event
    def delete(event: Event): Unit
  }
}

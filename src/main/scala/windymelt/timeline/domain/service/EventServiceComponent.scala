package windymelt.timeline.domain.service

import windymelt.timeline.domain.model.EventModelComponent
import windymelt.timeline.domain.DomainError
import windymelt.timeline.domain.model.UserModelComponent
import windymelt.timeline.domain.repository.EventRepositoryComponent

trait EventServiceComponent {
  self: EventModelComponent
    with UserModelComponent
    with EventRepositoryComponent =>
  val eventService: EventService

  trait EventService {
    def delete(event: Event): Either[DomainError, Unit]
    def findByEditor(user: User): Set[Event]
  }
}

trait ConcreteEventServiceComponent extends EventServiceComponent {
  self: EventModelComponent
    with UserModelComponent
    with EventRepositoryComponent =>
  val eventService: ConcreteEventService

  class ConcreteEventService extends EventService {
    def delete(event: Event): Either[DomainError, Unit] = {
      eventRepository.delete(event) // TODO: return Either
      Right()
    }
    def findByEditor(user: User): Set[Event] = {
      eventRepository.find(editor = user)
    }
  }
}

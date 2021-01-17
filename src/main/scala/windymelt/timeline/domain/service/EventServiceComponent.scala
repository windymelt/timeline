package windymelt.timeline.domain.service

import windymelt.timeline.domain.model.EventModelComponent
import windymelt.timeline.domain.DomainError
import windymelt.timeline.domain.model.UserModelComponent
import windymelt.timeline.domain.repository.EventRepositoryComponent
import windymelt.timeline.domain.model.TimelineModelComponent

trait EventServiceComponent {
  self: EventModelComponent
    with UserModelComponent
    with TimelineModelComponent
    with EventRepositoryComponent =>
  val eventService: EventService

  trait EventService {
    def delete(event: Event): Either[DomainError, Unit]
    def findByEditor(user: User): Set[Event]
    def findByTimeline(timeline: Timeline): Seq[Event]
  }
}

trait ConcreteEventServiceComponent extends EventServiceComponent {
  self: EventModelComponent
    with TimelineModelComponent
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
    def findByTimeline(
        timeline: Timeline
    ): Seq[Event] = {
      eventRepository.findByTimelineId(timeline.id)
    }
  }
}

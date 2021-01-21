package windymelt.timeline.domain.service

import com.github.nscala_time.time.Imports._

import windymelt.timeline.domain.model.EventModelComponent
import windymelt.timeline.domain.DomainError
import windymelt.timeline.domain.model.UserModelComponent
import windymelt.timeline.domain.repository.EventRepositoryComponent
import windymelt.timeline.domain.model.TimelineModelComponent
import windymelt.timeline.domain.repository.IDComponent

trait EventServiceComponent {
  self: EventModelComponent
    with UserModelComponent
    with TimelineModelComponent
    with EventRepositoryComponent =>
  val eventService: EventService

  trait EventService {
    def createEvent(
        name: String,
        description: Option[String],
        occurredAt: DateTime,
        until: Option[DateTime] = None,
        editor: User
    ): Event
    def delete(event: Event): Either[DomainError, Unit]
    def findByEditor(user: User): Set[Event]
    def findByTimeline(timeline: Timeline): Seq[Event]
  }
}

trait ConcreteEventServiceComponent extends EventServiceComponent {
  self: EventModelComponent
    with TimelineModelComponent
    with UserModelComponent
    with EventRepositoryComponent
    with IDComponent =>
  val eventService: ConcreteEventService

  class ConcreteEventService extends EventService {
    def createEvent(
        name: String,
        description: Option[String],
        occurredAt: DateTime,
        until: Option[DateTime] = None,
        editor: User
    ): Event = {
      val eventId = ID.gen()
      val ev =
        Event(eventId, name, description, occurredAt, until, editor)
      eventRepository.save(ev)
    }
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

package windymelt.timeline.domain.service

import windymelt.timeline.domain.DomainError

trait TimelineServiceComponent {
  self: windymelt.timeline.domain.model.EventModelComponent
    with windymelt.timeline.domain.model.TimelineModelComponent
    with windymelt.timeline.domain.model.UserModelComponent =>

  val timelineService: TimelineService

  object EventNotFoundError extends DomainError {
    override val message = "Event not found"
  }

  trait TimelineService {
    def create(
        title: String,
        eventIds: Seq[windymelt.timeline.Types.ID],
        editor: User
    ): Either[EventNotFoundError.type, Timeline]
  }
}

trait ConcreteTimelineServiceComponent extends TimelineServiceComponent {
  self: windymelt.timeline.domain.model.EventModelComponent
    with windymelt.timeline.domain.model.TimelineModelComponent
    with windymelt.timeline.domain.model.UserModelComponent
    with windymelt.timeline.domain.repository.EventRepositoryComponent
    with windymelt.timeline.domain.repository.TimelineRepositoryComponent
    with windymelt.timeline.domain.repository.IDComponent =>
  val timelineService: TimelineService

  class ConcreteTimelineService extends TimelineService {
    def createTimeline(
        title: String,
        events: Seq[Event],
        extendedFrom: Set[Timeline] = Set.empty,
        editor: User
    ): Timeline = {
      // TODO: Domain Service: Timeline merger/editor?
      val timelineId = ID.gen()
      val tl = Timeline(timelineId, title, editor, extendedFrom)
      events foreach (eventRepository.save)
      eventRepository.addRelationToTimeline(tl, events.toSet)
      timelineRepository.save(tl)
    }
    def create(
        title: String,
        eventIds: Seq[windymelt.timeline.Types.ID],
        editor: User
    ): Either[EventNotFoundError.type, Timeline] = {
      // check all eventIds corresponds to event
      println(eventIds)
      val events = eventRepository.find(eventIds)
      println(events)

      if (events.size != eventIds.size) {
        return Left(EventNotFoundError)
      }

      val id = ID.gen()

      val tl = Timeline(id, title, editor, Set()) // TODO: extend

      timelineRepository.save(tl)

      Right(tl)
    }
  }
}

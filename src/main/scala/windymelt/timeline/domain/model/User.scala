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

  final case class User(id: ID, name: String) {
    def createEvent(
        name: String,
        description: Option[String],
        occurredAt: DateTime,
        until: Option[DateTime] = None
    ): Event = {
      val eventId = ID.gen()
      val ev =
        Event(eventId, name, description, occurredAt, until, editor = this)
      eventRepository.save(ev)
    }

    def createTimeline(
        title: String,
        events: Seq[Event],
        extendedFrom: Set[Timeline] = Set.empty
    ): Timeline = {
      // TODO: Domain Service: Timeline merger/editor?
      val timelineId = ID.gen()
      val tl = Timeline(timelineId, title, this, events, extendedFrom)
      timelineRepository.save(tl)
    }
  }
}

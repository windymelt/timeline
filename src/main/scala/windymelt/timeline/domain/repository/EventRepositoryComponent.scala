package windymelt.timeline.domain.repository

import windymelt.timeline.domain.model.EventModelComponent
import windymelt.timeline.domain.model.UserModelComponent
import windymelt.timeline.domain.model.TimelineModelComponent

trait EventRepositoryComponent {
  self: EventModelComponent
    with UserModelComponent
    with TimelineModelComponent =>
  val eventRepository: EventRepository

  trait EventRepository {
    def save(event: Event): Event
    def delete(event: Event): Unit
    def find(editor: User): Set[Event]
    def find(eventIds: Seq[windymelt.timeline.Types.ID]): Seq[Event]
    def find(eventId: windymelt.timeline.Types.ID): Option[Event] =
      find(Seq(eventId)).headOption
    def findByTimelineId(timelineId: windymelt.timeline.Types.ID): Seq[Event]
    def addRelationToTimeline(timeline: Timeline, events: Set[Event]): Unit
  }
}

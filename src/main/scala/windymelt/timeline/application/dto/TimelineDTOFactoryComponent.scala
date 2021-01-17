package windymelt.timeline.application.dto

import windymelt.timeline.application.dto.{
  Timeline => DTOTimeline,
  Event => DTOEvent,
  User => DTOUser
}

trait TimelineDTOFactoryComponent {
  self: windymelt.timeline.domain.model.TimelineModelComponent
    with windymelt.timeline.domain.model.EventModelComponent
    with windymelt.timeline.domain.model.UserModelComponent =>

  val timelineDTOFactory: TimelineDTOFactory

  class TimelineDTOFactory {
    // TODO: Shapeless?
    def toDTO(
        timeline: Timeline,
        events: Seq[Event]
    ): windymelt.timeline.application.dto.Timeline = {
      DTOTimeline(
        timeline.id.toString(),
        timeline.title,
        toDTO(timeline.editor),
        events.map(toDTO)
      )
    }
    def toDTO(event: Event): windymelt.timeline.application.dto.Event = {
      DTOEvent(
        event.id.toString(),
        event.name,
        event.description.getOrElse(""),
        event.occurredAt,
        DayLevel,
        toDTO(event.editor)
      )
    }
    def toDTO(user: User): windymelt.timeline.application.dto.User = {
      DTOUser(user.id.toString(), user.name)
    }
  }
}

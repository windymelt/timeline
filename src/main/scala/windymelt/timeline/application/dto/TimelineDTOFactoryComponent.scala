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
    ): DTOTimeline = {
      DTOTimeline(
        timeline.id.toString(),
        timeline.title,
        toDTO(timeline.editor),
        events.map(toDTO)
      )
    }
    def toDTO(event: Event): DTOEvent = {
      DTOEvent(
        event.id.toString(),
        event.name,
        event.description.getOrElse(""),
        event.occurredAt,
        DayLevel,
        toDTO(event.editor)
      )
    }
    def toDTO(user: User): DTOUser = {
      DTOUser(user.id.toString(), user.name)
    }
  }
}

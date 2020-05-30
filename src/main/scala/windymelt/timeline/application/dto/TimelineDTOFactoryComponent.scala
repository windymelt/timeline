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
        timeline: Timeline
    ): windymelt.timeline.application.dto.Timeline = {
      DTOTimeline(
        timeline.id,
        timeline.title,
        toDTO(timeline.editor),
        timeline.events.map(toDTO)
      )
    }
    def toDTO(event: Event): windymelt.timeline.application.dto.Event = {
      DTOEvent(
        event.id,
        event.name,
        event.description.getOrElse(""),
        event.occurredAt
      )
    }
    def toDTO(user: User): windymelt.timeline.application.dto.User = {
      DTOUser(user.id, user.name)
    }
  }
}
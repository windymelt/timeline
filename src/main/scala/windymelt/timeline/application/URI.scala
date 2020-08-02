package windymelt.timeline.application

object URI {
  def permanent(timelineDTO: dto.Timeline): String =
    s"/timelines/${timelineDTO.id}"
}

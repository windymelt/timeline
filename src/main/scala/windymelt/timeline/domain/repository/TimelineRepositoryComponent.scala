package windymelt.timeline.domain.repository

trait TimelineRepositoryComponent {
  self: windymelt.timeline.domain.model.TimelineModelComponent =>
  val timelineRepository: TimelineRepository

  trait TimelineRepository {
    def save(timeline: Timeline): Timeline
    def delete(timeline: Timeline): Unit
  }
}

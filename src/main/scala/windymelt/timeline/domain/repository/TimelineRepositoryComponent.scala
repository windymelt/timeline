package windymelt.timeline.domain.repository

import windymelt.timeline.domain.model.{
  UserModelComponent,
  TimelineModelComponent
}

trait TimelineRepositoryComponent {
  self: TimelineModelComponent with UserModelComponent =>
  val timelineRepository: TimelineRepository

  trait TimelineRepository {
    def find(id: BigInt): Option[Timeline]
    def findByEditor(user: User): Set[Timeline]
    def save(timeline: Timeline): Timeline
    def delete(timeline: Timeline): Unit
  }
}

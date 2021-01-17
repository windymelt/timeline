package windymelt.timeline.domain.model

import windymelt.timeline.Types.ID

trait TimelineModelComponent {
  self: UserModelComponent with EventModelComponent =>
  final case class Timeline(
      id: ID,
      title: String,
      editor: User,
      extendedFrom: Set[Timeline] = Set.empty
  )
}

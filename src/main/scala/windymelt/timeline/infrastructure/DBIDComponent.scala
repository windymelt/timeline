package windymelt.timeline.infrastructure

import windymelt.timeline.Types
import windymelt.timeline.domain.repository.IDComponent

trait DBIDComponent extends IDComponent {
  self: Database =>
  val ID: IDFactory

  class DBIDFactory extends IDFactory {
    def gen(): Types.ID = uuid_short()
  }
}

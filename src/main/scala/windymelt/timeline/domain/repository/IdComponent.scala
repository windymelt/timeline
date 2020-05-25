package windymelt.timeline.domain.repository

import windymelt.timeline.Types.{ID => IDType}

trait IDComponent {
    val ID: IDFactory

    trait IDFactory {
        def gen(): IDType
    }
}
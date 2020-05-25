package windymelt.timeline.infrastructure

import windymelt.timeline.Types

trait InmemoryIdComponent extends windymelt.timeline.domain.repository.IDComponent {
    val ID: InMemoryIDFactory

    class InMemoryIDFactory extends IDFactory {
        var ids = collection.mutable.Set[BigInt]()
        def gen(): Types.ID = {
            val id = scala.util.Random.nextLong()
            ids.find(_ == id) match {
                case None =>
                ids.add(id)
                id
                case Some(_) => gen()
            }
        }
    }
}
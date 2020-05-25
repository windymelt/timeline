package windymelt.timeline.infrastructure

import windymelt.timeline.Types

trait InmemoryUserRepositoryComponent extends windymelt.timeline.domain.repository.UserRepositoryComponent {
    self: windymelt.timeline.domain.model.UserModelComponent =>
    val userRepository: InmemoryUserRepository

    class InmemoryUserRepository extends UserRepository {
        var db = collection.mutable.Seq[User]() // todo

        def save(user: User): User = db.find(_.id == user.id) match {
            case Some(value) =>
            db = db.filterNot(_.id == user.id)
            db = db :+ user
            user
            case None =>
            db = db :+ user
            user
        }

        def find(id: Types.ID): Option[User] = db.find(_.id == id)
        def find(name: String): Option[User] = db.find(_.name == name)
        
        def delete(user: User): Unit = {
            db = db.filterNot(_.id == user.id)
        }
    }
}
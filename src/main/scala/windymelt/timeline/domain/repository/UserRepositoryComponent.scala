package windymelt.timeline.domain.repository

import windymelt.timeline.Types.ID

trait UserRepositoryComponent {
    self: windymelt.timeline.domain.model.UserModelComponent =>
    val userRepository: UserRepository

    trait UserRepository {
        def save(user: User): User
        def find(id: ID): Option[User]
        def find(name: String): Option[User]
        def delete(user: User): Unit
    }
}

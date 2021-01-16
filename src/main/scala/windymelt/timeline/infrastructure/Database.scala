package windymelt.timeline.infrastructure

trait Database {
  import scalikejdbc._

  // Setup connection-pool regards to application.conf.
  // cf. application.conf.
  scalikejdbc.config.DBs.setupAll()

  // test
  val memberIds = DB readOnly { implicit session =>
    sql"select 1 as one".map(_.long(1)).list.apply()
  }
}

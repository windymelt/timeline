package windymelt.timeline.infrastructure

import windymelt.timeline.Loggable

trait Database extends Loggable {
  import scalikejdbc._
  import scalikejdbc.jodatime.JodaParameterBinderFactory._
  import scalikejdbc.jodatime.JodaBinders._
  import com.github.nscala_time.time.Imports._

  // Setup connection-pool regards to application.conf.
  // cf. application.conf.
  scalikejdbc.config.DBs.setupAll()

  // test
  val value = DB readOnly { implicit session =>
    sql"select 1 as one".map(_.long(1)).list.apply()
  }
  logger.info("DB Connection has been established: {}", value)

  def uuid_short(): BigInt = DB localTx { implicit session =>
    sql"SELECT UUID_SHORT() AS uuid_short"
      .map(_.long(1))
      .headOption()
      .get
  }
}

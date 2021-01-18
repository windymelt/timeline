package windymelt.timeline

object Types {
  type ID = BigInt

  // Tagged type
  type Tagged[U] = { type Tag = U }
  type @@[T, U] = T with Tagged[U]
  class Tagger[U] {
    def apply[T](t: T): T @@ U = t.asInstanceOf[T @@ U]
  }
  def Tag[U] = new Tagger[U]
}

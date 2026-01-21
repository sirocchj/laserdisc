package laserdisc
package protocol

import org.openjdk.jmh.annotations.{Benchmark, Scope, State}

@State(Scope.Benchmark)
class RESPParamWriteBench {

  private final val respParamWrite = RESPParamWrite[Int *: String *: Long *: Double *: EmptyTuple]

  private final val value = 0 *: "a" *: 1L *: 2.0d *: EmptyTuple

  @Benchmark def write(): Seq[GenBulk] = respParamWrite.write(value)
}

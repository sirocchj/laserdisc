## LaserDisc

[![Continuous Integration](https://github.com/laserdisc-io/laserdisc/actions/workflows/ci.yml/badge.svg)](https://github.com/laserdisc-io/laserdisc/actions/workflows/ci.yml)
[![Known Vulnerabilities](https://snyk.io/test/github/laserdisc-io/laserdisc/badge.svg?targetFile=build.sbt)](https://snyk.io/test/github/laserdisc-io/laserdisc?targetFile=build.sbt)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-blue.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://scala-steward.org)

[![laserdisc-core Scala version support](https://index.scala-lang.org/laserdisc-io/laserdisc/laserdisc-core/latest-by-scala-version.svg?targetType=Js)](https://index.scala-lang.org/laserdisc-io/laserdisc/laserdisc-core)


LaserDisc is a(nother) Scala driver for [Redis](https://redis.io/), Redis forks like [Valkey](https://valkey.io/), and Redis API-compatible datastores (like [Dragonfly](https://www.dragonflydb.io/) or [KeyDB](https://docs.keydb.dev/)), written in Scala from the ground up.

It differentiates itself from other Scala implementations (those that are not piggybacking on some Java library) for having a core layer, which is made up of all Redis commands for all Redis Serialization Protocol versions ([RESP 2](https://github.com/redis/redis-specifications/blob/1252427cdbc497f66a7f8550c6b5f2f35367dc92/protocol/RESP2.md) and [RESP 3](https://github.com/redis/redis-specifications/blob/1252427cdbc497f66a7f8550c6b5f2f35367dc92/protocol/RESP3.md)), which is strongly typed and that makes best use of contextual abstractions (e.g., [shapeless](https://github.com/milessabin/shapeless) and [refined](https://github.com/fthomas/refined) in Scala 2, built-in features and [Iron](https://github.com/Iltotore/iron) in Scala 3) to achieve this. All RESP protocol commands are serialized/deserialized into binary formar using [scodec](http://scodec.org/).

On top of this, one or more clients can be implemented. The one currently available out of the box is built using [fs2](https://fs2.io/) and [cats effect](https://typelevel.org/cats-effect/). This implementation has found great inspiration from the [fs2-kafka](https://github.com/Spinoco/fs2-kafka/) library. Other competing implementations - leveraging other effect systems - can be added with limited effort.

What LaserDisc offers:
- [x] Support for Scala 2 and Scala 3
- [x] Maximum type-safety with minimal memory and CPU overhead
- [x] Implementation of all request/response commands and push notifications, as per [this page](https://redis.io/commands)
- [x] Support for single-node Redis client
- [x] Alternative CLI

**Note:** the library is still evolving and it is completely possible more features will be added in the future. Even if the binary compatibility will not be guaranteed until version 1.0.0, the [semantic versioning](https://semver.org/) strategy will be observed in the process.

### Why the name

Two reasons:
1. "A LaserDisc" is an anagram for "Scala Redis"
2. LaserDiscs share their birth year (1978) with one of the authors of this library but, more importantly, they have been foundational in establishing laser-read technology on reflective surfaces and random access capabilities. More on [Wikipedia](https://en.wikipedia.org/wiki/LaserDisc)

### Getting Started

LaserDisc is currently available for Scala 2.12, 2.13, and 3 on both the JVM and JavaScript engines like Node.js.

To add LaserDisc as a dependency to your project just add the following to your `build.sbt`:
```
libraryDependencies += "io.laserdisc" %% "laserdisc-fs2" % latestVersion
```

If you only need protocols (i.e. Redis commands and RESP wire format), you may simply add:
```
libraryDependencies += "io.laserdisc" %% "laserdisc-core" % latestVersion
```

### Interoperability modules

Support for existing libraries is available via dedicated dependencies.

#### [Circe](https://circe.github.io/circe/)

When using this module, as long as a `io.circe.Decoder[A]` and/or a `io.circe.Encoder[A]` are `implicit`ly made available (or `given`, in the case of Scala 3), instances of `Show[A]` and `Read[Bulk, A]` can be derived from them, respectively, for free.

To use this module, just add the following to your `build.sbt`:

```
libraryDependecies += "io.laserdisc" %% "laserdisc-circe" % latestVersion 
```

To make use of them, at call site (i.e., where the `Show[A]` and/or `Read[Bulk, A]` are needed) it is sufficient to just

```scala
import laserdisc.interop.circe.*
```

*Note*: the derived `Show[A]` instance uses the most compact string representation of the JSON data structure, i.e. no spacing is used.

### Example usage
With a running Redis instance on `localhost:6379`, try running the following:
```scala
import cats.effect.{IO, IOApp}
import log.effect.LogWriter
import log.effect.fs2.SyncLogWriter
import cats.syntax.flatMap.*

object Main extends IOApp.Simple {

  import laserdisc.*
  import laserdisc.all.*
  import laserdisc.auto.*
  import laserdisc.fs2.*

  def redisTest(implicit log: LogWriter[IO]): IO[Unit] =
    RedisClient[IO].to("localhost", 6379).use { client =>
      client.send(
        set("a", 23),
        set("b", 55),
        get[PosInt]("b"),
        get[PosInt]("a")
      ).flatMap {
        case (Right(OK), Right(OK), Right(Some(getOfb)), Right(Some(getOfa))) if getOfb.value == 55 && getOfa.value == 23 =>
          log info "yay!"
        case other =>
          log.error(s"something went terribly wrong $other") >>
            IO.raiseError(new RuntimeException("boom"))
      }
    }

  override final val run: IO[Unit] = redisTest(SyncLogWriter.consoleLog[IO])
}
```

This should produce an output similar to the following one:
```
[debug] - [io-compute-3] Starting connection
[info] - [io-compute-4] Connected to server localhost:6379
[trace] - [io-compute-4] sending Arr(Bulk(SET),Bulk(a),Bulk(23))
[trace] - [io-compute-3] receiving Str(OK)
[trace] - [io-compute-1] sending Arr(Bulk(SET),Bulk(b),Bulk(55))
[trace] - [io-compute-4] receiving Str(OK)
[trace] - [io-compute-0] sending Arr(Bulk(GET),Bulk(b))
[trace] - [io-compute-5] receiving Bulk(55)
[trace] - [io-compute-0] sending Arr(Bulk(GET),Bulk(a))
[trace] - [io-compute-0] receiving Bulk(23)
[info] - [io-compute-5] yay!
[debug] - [io-compute-5] Shutting down connection
[debug] - [io-compute-5] Shutdown complete
[info] - [io-compute-0] Connection terminated: No issues
```

## License

LaserDisc is licensed under the **[MIT License](LICENSE)** (the "License"); you may not use this software except in
compliance with the License.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.

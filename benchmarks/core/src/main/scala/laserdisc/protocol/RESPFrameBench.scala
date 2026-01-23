package laserdisc
package protocol

import org.openjdk.jmh.annotations.{Benchmark, Scope, State}
import scodec.bits.BitVector

import laserdisc.RESPFrameFixture
import org.openjdk.jmh.infra.Blackhole

@State(Scope.Benchmark)
class RESPFrameBench {

  private[this] object Fixture extends RESPFrameFixture
  import Fixture._

  private final val mixedNoArr    = bytesOf(mixedNoArrList)
  private final val arrOneLevel   = bytesOf(arrOneLevelList)
  private final val arrFiveLevels = bytesOf(arrFiveLevelsList)

  private final val empty             = BitVector.empty
  private final val mixedNoArrFull    = BitVector(mixedNoArr)
  private final val arrOneLevelFull   = BitVector(arrOneLevel)
  private final val arrFiveLevelsFull = BitVector(arrFiveLevels)

  @Benchmark def frameOfFullBaseline(bh: Blackhole): Unit = {
    val frame = EmptyFrame.append(empty)
    bh.consume(frame)
  }
  @Benchmark def frameOfMixedNoArrFull(bh: Blackhole): Unit = {
    val frame = EmptyFrame.append(mixedNoArrFull)
    bh.consume(frame)
  }
  @Benchmark def frameOfMixedArrOneLevelFull(bh: Blackhole): Unit = {
    val frame = EmptyFrame.append(arrOneLevelFull)
    bh.consume(frame)
  }
  @Benchmark def frameOfMixedArrFiveLevelsFull(bh: Blackhole): Unit = {
    val frame = EmptyFrame.append(arrFiveLevelsFull)
    bh.consume(frame)
  }

  private final val mixedNoArrSmallChunkBuffers    = groupInChunks(mixedNoArr, 128)
  private final val arrOneLevelSmallChunkBuffers   = groupInChunks(arrOneLevel, 128)
  private final val arrFiveLevelsSmallChunkBuffers = groupInChunks(arrFiveLevels, 128)

  @Benchmark def frameOfChunkedBaseline(bh: Blackhole): Unit = {
    val frames = appendChunks(Iterator.empty)
    bh.consume(frames)
  }
  @Benchmark def frameOfChunkedShortMixedNoArr(bh: Blackhole): Unit = {
    val frames = appendChunks(mixedNoArrSmallChunkBuffers)
    bh.consume(frames)
  }
  @Benchmark def frameOfChunkedShortArrOneLevel(bh: Blackhole): Unit = {
    val frames = appendChunks(arrOneLevelSmallChunkBuffers)
    bh.consume(frames)
  }
  @Benchmark def frameOfChunkedShortArrFiveLevels(bh: Blackhole): Unit = {
    val frames = appendChunks(arrFiveLevelsSmallChunkBuffers)
    bh.consume(frames)
  }

  private final val mixedNoArrBigChunkBuffers    = groupInChunks(mixedNoArr, 1024)
  private final val arrOneLevelBigChunkBuffers   = groupInChunks(arrOneLevel, 1024)
  private final val arrFiveLevelsBigChunkBuffers = groupInChunks(arrFiveLevels, 1024)

  @Benchmark def frameOfChunkedLongMixedNoArr(bh: Blackhole): Unit = {
    val frames = appendChunks(mixedNoArrBigChunkBuffers)
    bh.consume(frames)
  }
  @Benchmark def frameOfChunkedLongArrOneLevel(bh: Blackhole): Unit = {
    val frames = appendChunks(arrOneLevelBigChunkBuffers)
    bh.consume(frames)
  }
  @Benchmark def frameOfChunkedLongArrFiveLevels(bh: Blackhole): Unit = {
    val frames = appendChunks(arrFiveLevelsBigChunkBuffers)
    bh.consume(frames)
  }
}

package laserdisc
package protocol

import org.scalacheck.Prop.forAll

final class ListExtPSpec extends ListPSpec {
  property("The List extended protocol using lpush roundtrips successfully given key and value1") {
    forAll { (k: Key, v1: Int, pi: PosInt) =>
      val protocol = lpush(k, v1)
      assertEquals(protocol.encode, Arr(Bulk("LPUSH"), Bulk(k), Bulk(v1)))
      assertEquals(protocol.decode(Num(pi.value.toLong)), pi)
    }
  }
  property("The List extended protocol using lpush roundtrips successfully given key and value1, value2") {
    forAll { (k: Key, v1: Int, v2: Int, pi: PosInt) =>
      val protocol = lpush(k, v1, v2)
      assertEquals(protocol.encode, Arr(Bulk("LPUSH"), Bulk(k), Bulk(v1), Bulk(v2)))
      assertEquals(protocol.decode(Num(pi.value.toLong)), pi)
    }
  }
  property("The List extended protocol using lpush roundtrips successfully given key and value1, value2, value3") {
    forAll { (k: Key, v1: Int, v2: Int, v3: Int, pi: PosInt) =>
      val protocol = lpush(k, v1, v2, v3)
      assertEquals(protocol.encode, Arr(Bulk("LPUSH"), Bulk(k), Bulk(v1), Bulk(v2), Bulk(v3)))
      assertEquals(protocol.decode(Num(pi.value.toLong)), pi)
    }
  }
  property("The List extended protocol using lpush roundtrips successfully given key and value1, value2, value3, value4") {
    forAll { (k: Key, v1: Int, v2: Int, v3: Int, v4: Int, pi: PosInt) =>
      val protocol = lpush(k, v1, v2, v3, v4)
      assertEquals(protocol.encode, Arr(Bulk("LPUSH"), Bulk(k), Bulk(v1), Bulk(v2), Bulk(v3), Bulk(v4)))
      assertEquals(protocol.decode(Num(pi.value.toLong)), pi)
    }
  }
  property("The List extended protocol using lpush roundtrips successfully given key and value1, value2, value3, value4, value5") {
    forAll { (k: Key, v1: Int, v2: Int, v3: Int, v4: Int, v5: Int, pi: PosInt) =>
      val protocol = lpush(k, v1, v2, v3, v4, v5)
      assertEquals(protocol.encode, Arr(Bulk("LPUSH"), Bulk(k), Bulk(v1), Bulk(v2), Bulk(v3), Bulk(v4), Bulk(v5)))
      assertEquals(protocol.decode(Num(pi.value.toLong)), pi)
    }
  }
  property("The List extended protocol using rpush roundtrips successfully given key and value1") {
    forAll { (k: Key, v1: Int, pi: PosInt) =>
      val protocol = rpush(k, v1)
      assertEquals(protocol.encode, Arr(Bulk("RPUSH"), Bulk(k), Bulk(v1)))
      assertEquals(protocol.decode(Num(pi.value.toLong)), pi)
    }
  }
  property("The List extended protocol using rpush roundtrips successfully given key and value1, value2") {
    forAll { (k: Key, v1: Int, v2: Int, pi: PosInt) =>
      val protocol = rpush(k, v1, v2)
      assertEquals(protocol.encode, Arr(Bulk("RPUSH"), Bulk(k), Bulk(v1), Bulk(v2)))
      assertEquals(protocol.decode(Num(pi.value.toLong)), pi)
    }
  }
  property("The List extended protocol using rpush roundtrips successfully given key and value1, value2, value3") {
    forAll { (k: Key, v1: Int, v2: Int, v3: Int, pi: PosInt) =>
      val protocol = rpush(k, v1, v2, v3)
      assertEquals(protocol.encode, Arr(Bulk("RPUSH"), Bulk(k), Bulk(v1), Bulk(v2), Bulk(v3)))
      assertEquals(protocol.decode(Num(pi.value.toLong)), pi)
    }
  }
  property("The List extended protocol using rpush roundtrips successfully given key and value1, value2, value3, value4") {
    forAll { (k: Key, v1: Int, v2: Int, v3: Int, v4: Int, pi: PosInt) =>
      val protocol = rpush(k, v1, v2, v3, v4)
      assertEquals(protocol.encode, Arr(Bulk("RPUSH"), Bulk(k), Bulk(v1), Bulk(v2), Bulk(v3), Bulk(v4)))
      assertEquals(protocol.decode(Num(pi.value.toLong)), pi)
    }
  }
  property("The List extended protocol using rpush roundtrips successfully given key and value1, value2, value3, value4, value5") {
    forAll { (k: Key, v1: Int, v2: Int, v3: Int, v4: Int, v5: Int, pi: PosInt) =>
      val protocol = rpush(k, v1, v2, v3, v4, v5)
      assertEquals(protocol.encode, Arr(Bulk("RPUSH"), Bulk(k), Bulk(v1), Bulk(v2), Bulk(v3), Bulk(v4), Bulk(v5)))
      assertEquals(protocol.decode(Num(pi.value.toLong)), pi)
    }
  }
}

package laserdisc
package protocol

import org.scalacheck.Prop.forAll

final class HashExtPSpec extends HashPSpec {
  property("The Hash extended protocol using hdel roundtrips successfully given key and field1") {
    forAll { (k: Key, f1: Key, nni: NonNegInt) =>
      val protocol = hdel(k, f1)
      assertEquals(protocol.encode, Arr(Bulk("HDEL"), Bulk(k), Bulk(f1)))
      assertEquals(protocol.decode(Num(nni.value.toLong)), nni)
    }
  }
  property("The Hash extended protocol using hdel roundtrips successfully given key and field1, field2") {
    forAll { (k: Key, f1: Key, f2: Key, nni: NonNegInt) =>
      val protocol = hdel(k, f1, f2)
      assertEquals(protocol.encode, Arr(Bulk("HDEL"), Bulk(k), Bulk(f1), Bulk(f2)))
      assertEquals(protocol.decode(Num(nni.value.toLong)), nni)
    }
  }
  property("The Hash extended protocol using hdel roundtrips successfully given key and field1, field2, field3") {
    forAll { (k: Key, f1: Key, f2: Key, f3: Key, nni: NonNegInt) =>
      val protocol = hdel(k, f1, f2, f3)
      assertEquals(protocol.encode, Arr(Bulk("HDEL"), Bulk(k), Bulk(f1), Bulk(f2), Bulk(f3)))
      assertEquals(protocol.decode(Num(nni.value.toLong)), nni)
    }
  }
  property("The Hash extended protocol using hdel roundtrips successfully given key and field1, field2, field3, field4") {
    forAll { (k: Key, f1: Key, f2: Key, f3: Key, f4: Key, nni: NonNegInt) =>
      val protocol = hdel(k, f1, f2, f3, f4)
      assertEquals(protocol.encode, Arr(Bulk("HDEL"), Bulk(k), Bulk(f1), Bulk(f2), Bulk(f3), Bulk(f4)))
      assertEquals(protocol.decode(Num(nni.value.toLong)), nni)
    }
  }
  property("The Hash extended protocol using hdel roundtrips successfully given key and field1, field2, field3, field4, field5") {
    forAll { (k: Key, f1: Key, f2: Key, f3: Key, f4: Key, f5: Key, nni: NonNegInt) =>
      val protocol = hdel(k, f1, f2, f3, f4, f5)
      assertEquals(protocol.encode, Arr(Bulk("HDEL"), Bulk(k), Bulk(f1), Bulk(f2), Bulk(f3), Bulk(f4), Bulk(f5)))
      assertEquals(protocol.decode(Num(nni.value.toLong)), nni)
    }
  }
  property("The Hash extended protocol using hmget roundtrips successfully given key and field1") {
    forAll { (k: Key, f1: Key) =>
      forAll { (v1: Int) =>
        val protocol = hmget[Int](k, f1)
        assertEquals(protocol.encode, Arr(Bulk("HMGET"), Bulk(k), Bulk(f1)))
        assertEquals(protocol.decode(Arr(Bulk(v1))), v1)
      }
    }
  }
  property("The Hash extended protocol using hmget roundtrips successfully given key and field1, field2") {
    forAll { (k: Key, f1: Key, f2: Key) =>
      forAll { (v1: Int, v2: Int) =>
        val protocol = hmget[Int, Int](k, f1, f2)
        assertEquals(protocol.encode, Arr(Bulk("HMGET"), Bulk(k), Bulk(f1), Bulk(f2)))
        assertEquals(protocol.decode(Arr(Bulk(v1), Bulk(v2))), (v1, v2))
      }
    }
  }
  property("The Hash extended protocol using hmget roundtrips successfully given key and field1, field2, field3") {
    forAll { (k: Key, f1: Key, f2: Key, f3: Key) =>
      forAll { (v1: Int, v2: Int, v3: Int) =>
        val protocol = hmget[Int, Int, Int](k, f1, f2, f3)
        assertEquals(protocol.encode, Arr(Bulk("HMGET"), Bulk(k), Bulk(f1), Bulk(f2), Bulk(f3)))
        assertEquals(protocol.decode(Arr(Bulk(v1), Bulk(v2), Bulk(v3))), (v1, v2, v3))
      }
    }
  }
  property("The Hash extended protocol using hmget roundtrips successfully given key and field1, field2, field3, field4") {
    forAll { (k: Key, f1: Key, f2: Key, f3: Key, f4: Key) =>
      forAll { (v1: Int, v2: Int, v3: Int, v4: Int) =>
        val protocol = hmget[Int, Int, Int, Int](k, f1, f2, f3, f4)
        assertEquals(protocol.encode, Arr(Bulk("HMGET"), Bulk(k), Bulk(f1), Bulk(f2), Bulk(f3), Bulk(f4)))
        assertEquals(protocol.decode(Arr(Bulk(v1), Bulk(v2), Bulk(v3), Bulk(v4))), (v1, v2, v3, v4))
      }
    }
  }
  property("The Hash extended protocol using hmget roundtrips successfully given key and field1, field2, field3, field4, field5") {
    forAll { (k: Key, f1: Key, f2: Key, f3: Key, f4: Key, f5: Key) =>
      forAll { (v1: Int, v2: Int, v3: Int, v4: Int, v5: Int) =>
        val protocol = hmget[Int, Int, Int, Int, Int](k, f1, f2, f3, f4, f5)
        assertEquals(protocol.encode, Arr(Bulk("HMGET"), Bulk(k), Bulk(f1), Bulk(f2), Bulk(f3), Bulk(f4), Bulk(f5)))
        assertEquals(protocol.decode(Arr(Bulk(v1), Bulk(v2), Bulk(v3), Bulk(v4), Bulk(v5))), (v1, v2, v3, v4, v5))
      }
    }
  }
  property("The Hash extended protocol using hmset roundtrips successfully given key and field1") {
    forAll { (k: Key, f1: Key) =>
      forAll { (v1: Int) =>
        val protocol = hmset(k, f1, v1)
        assertEquals(protocol.encode, Arr(Bulk("HMSET"), Bulk(k), Bulk(f1), Bulk(v1)))
        assertEquals(protocol.decode(Str(OK.value)), OK)
      }
    }
  }
  property("The Hash extended protocol using hmset roundtrips successfully given key and field1, field2") {
    forAll { (k: Key, f1: Key, f2: Key) =>
      forAll { (v1: Int, v2: Int) =>
        val protocol = hmset(k, f1, v1, f2, v2)
        assertEquals(protocol.encode, Arr(Bulk("HMSET"), Bulk(k), Bulk(f1), Bulk(v1), Bulk(f2), Bulk(v2)))
        assertEquals(protocol.decode(Str(OK.value)), OK)
      }
    }
  }
  property("The Hash extended protocol using hmset roundtrips successfully given key and field1, field2, field3") {
    forAll { (k: Key, f1: Key, f2: Key, f3: Key) =>
      forAll { (v1: Int, v2: Int, v3: Int) =>
        val protocol = hmset(k, f1, v1, f2, v2, f3, v3)
        assertEquals(protocol.encode, Arr(Bulk("HMSET"), Bulk(k), Bulk(f1), Bulk(v1), Bulk(f2), Bulk(v2), Bulk(f3), Bulk(v3)))
        assertEquals(protocol.decode(Str(OK.value)), OK)
      }
    }
  }
  property("The Hash extended protocol using hmset roundtrips successfully given key and field1, field2, field3, field4") {
    forAll { (k: Key, f1: Key, f2: Key, f3: Key, f4: Key) =>
      forAll { (v1: Int, v2: Int, v3: Int, v4: Int) =>
        val protocol = hmset(k, f1, v1, f2, v2, f3, v3, f4, v4)
        assertEquals(
          protocol.encode,
          Arr(Bulk("HMSET"), Bulk(k), Bulk(f1), Bulk(v1), Bulk(f2), Bulk(v2), Bulk(f3), Bulk(v3), Bulk(f4), Bulk(v4))
        )
        assertEquals(protocol.decode(Str(OK.value)), OK)
      }
    }
  }
  property("The Hash extended protocol using hmset roundtrips successfully given key and field1, field2, field3, field4, field5") {
    forAll { (k: Key, f1: Key, f2: Key, f3: Key, f4: Key, f5: Key) =>
      forAll { (v1: Int, v2: Int, v3: Int, v4: Int, v5: Int) =>
        val protocol = hmset(k, f1, v1, f2, v2, f3, v3, f4, v4, f5, v5)
        assertEquals(
          protocol.encode,
          Arr(Bulk("HMSET"), Bulk(k), Bulk(f1), Bulk(v1), Bulk(f2), Bulk(v2), Bulk(f3), Bulk(v3), Bulk(f4), Bulk(v4), Bulk(f5), Bulk(v5))
        )
        assertEquals(protocol.decode(Str(OK.value)), OK)
      }
    }
  }
}

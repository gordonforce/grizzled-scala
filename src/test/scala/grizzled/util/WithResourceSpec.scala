package grizzled.util

import grizzled.BaseSpec

import scala.util.Try

class WithResourceSpec extends BaseSpec {
  private class TestCloseable extends java.io.Closeable {
    var isClosed = false
    def close() = isClosed = true
  }

  "withResource" should "close a java.io.Closeable on success" in {
    val c = new TestCloseable
    withResource(c) { _ =>  }
    c.isClosed shouldBe true
  }

  it should "close a java.io.Closeable on failure" in {
    val c = new TestCloseable
    Try {
      withResource(c) { _ => throw new Exception("abort") }
    }

    c.isClosed shouldBe true
  }
}
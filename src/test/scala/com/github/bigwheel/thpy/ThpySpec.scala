package com.github.bigwheel.thpy

import com.github.bigwheel.thpy.Macro.anyToTyped
import java.io.BufferedReader
import java.io.StringReader
import java.io.StringWriter
import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.tools.nsc.interpreter.JPrintWriter

class ThpySpec extends FunSpec with Matchers {

  private[this] def toBufferedReader(s: String) = new BufferedReader(new StringReader(s))

  it ("""has "Welcome to Thpy" message""") {
    val input = ":quit"

    val sw = new StringWriter()
    break(toBufferedReader(input), new JPrintWriter(sw))
    sw.toString.contains("Welcome to Thpy") should be(true)
  }

  it ("can process") {
    val b = 3

    val input = """|1 + 1
                   |b
                   |:quit""".stripMargin

    break(toBufferedReader(input), b)
  }

  it ("can change mutable object") {
    val map = scala.collection.mutable.Map(1 -> "one")
    map.size should equal(1)
    val input = """|map
                   |map.put(2, "two")
                   |:quit""".stripMargin

    break(toBufferedReader(input), map)
    map.size should equal(2)
    map(2) should equal("two")
  }

}

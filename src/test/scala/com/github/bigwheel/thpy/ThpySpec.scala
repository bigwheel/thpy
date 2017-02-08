package com.github.bigwheel.thpy

import com.github.bigwheel.thpy.Macro.anyToTyped
import java.io.BufferedReader
import java.io.StringReader
import org.scalatest.FunSpec
import org.scalatest.Matchers

class ThpySpec extends FunSpec with Matchers {

  it ("can process") {
    val b = 3

    val input = """|1 + 1
                   |b
                   |:quit""".stripMargin

    break(new BufferedReader(new StringReader(input)), b)
  }

}

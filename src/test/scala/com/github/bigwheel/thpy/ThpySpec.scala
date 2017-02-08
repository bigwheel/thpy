package com.github.bigwheel.thpy

import com.github.bigwheel.thpy.Macro.anyToTyped
import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.io.Source
import scala.tools.nsc.interpreter._

class ThpySpec extends FunSpec with Matchers {

  it ("can process") {
    val b = 3


    break(Source.fromResource("input-for-test.txt").bufferedReader(), b)
  }

}

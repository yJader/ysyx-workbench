// import Mill dependency
import mill._
import mill.define.Sources
import mill.modules.Util
import mill.scalalib.TestModule.ScalaTest
import scalalib._
// support BSP
import mill.bsp._

trait MyModule extends SbtModule { m =>
  override def scalaVersion = "2.13.12"
  override def scalacOptions = Seq(
    "-language:reflectiveCalls",
    "-deprecation",
    "-feature",
    "-Xcheckinit"
  )
  override def ivyDeps = Agg(
    ivy"org.chipsalliance::chisel:6.2.0"
  )
  override def scalacPluginIvyDeps = Agg(
    ivy"org.chipsalliance:::chisel-plugin:6.2.0"
  )

  object test extends SbtModuleTests with TestModule.ScalaTest {
    override def ivyDeps = m.ivyDeps() ++ Agg(
      ivy"org.scalatest::scalatest::3.2.16"
    )
  }
}

object selector extends MyModule { m =>
  override def millSourcePath = os.pwd / "1_selector"
}

object coder extends MyModule { m =>
  override def millSourcePath = os.pwd / "2_decoder_encoder"
}

object alu extends MyModule { m =>
  override def moduleDeps: Seq[JavaModule] = Seq(coder)
  override def millSourcePath = os.pwd / "3_alu"
}

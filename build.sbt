name := "vynfields"

version := "1.0-SNAPSHOT"

libraryDependencies ++= Seq(
  //jdbc,
  //anorm,
  cache,
//  "org.apache.commons" % "commons-email" % "1.3.2"
  //"com.typesafe.play" %% "play-slick" % "0.6.0.1",
  //"com.typesafe.slick" %% "slick" % "2.0.1",
  //"com.typesafe.slick" %% "slick-extensions" % "2.0.1",
  //"ws.securesocial" %% "securesocial" % "2.1.3",
  //"org.scalatest" % "scalatest_2.10" % "2.0" % "test",
//  "postgresql" % "postgresql" % "9.3-1101.jdbc41"
//  "org.postgresql" % "postgresql" % "9.3-1101.jdbc41"
  //"org.postgresql" % "postgresql" % "9.3-1100-jdbc41"
"com.typesafe" %% "play-plugins-mailer" % "2.1-RC2"
)

//resolvers ++= Seq(
//  "Typesafe Releases" at "http://repo.typesafe.com/typesafe/maven-releases/",
//  Resolver.url("sbt-plugin-releases", url("http://repo.scala-sbt.org/scalasbt/sbt-plugin-releases/"))(Resolver.ivyStylePatterns),
//  Resolver.sonatypeRepo("releases")
//)

play.Project.playScalaSettings

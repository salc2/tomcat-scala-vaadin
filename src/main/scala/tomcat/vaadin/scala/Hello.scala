package tomcat.vaadin.scala

import org.apache.catalina.startup.Tomcat
import org.apache.catalina._
import java.io.File
import java.lang.Exception
import javax.servlet.http._
object Hello {

	def main(args:Array[String]):Unit = {
		val tom = new Tomcat()
			tom.setPort(8080)
			val ctx = tom.addContext("/",new File(".").
					getAbsolutePath())

			Tomcat.addServlet(ctx, "hello", new HttpServlet() {
					@throws[Exception]
					override def service(req:HttpServletRequest,
							resp:HttpServletResponse) = {
					val w = resp.getWriter()
					w.write("Hello World")
					w.flush()
					}})


		ctx.addServletMapping("/*", "hello")
		tom.start()
		tom.getServer().await()

	}
}

package tomcat.vaadin.server

import org.apache.catalina.startup.Tomcat
import org.apache.catalina._
import java.io.File
import java.lang.Exception
import javax.servlet.http._
import com.vaadin.ui._
import com.vaadin.ui.Button.ClickEvent
import com.vaadin.server._
import com.vaadin.annotations.Theme
import com.vaadin.annotations.VaadinServletConfiguration

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
			
			Tomcat.addServlet(ctx, "vaadin", HelloServlet)
		
		ctx.addServletMapping("/*", "hello")
		ctx.addServletMapping("/vaadin/*", "vaadin")
		ctx.addServletMapping("/VAADIN/*", "vaadin")
		tom.start()
		tom.getServer().await()

	}
}



@Theme("valo")
class VaadinUI extends UI {
	override def init(vr:VaadinRequest):Unit = {
		val layout = new VerticalLayout()
		layout.setMargin(true)
		setContent(layout)
		val button = new Button("Click me", new Button.ClickListener(){
			override def buttonClick(event:ClickEvent):Unit = 
			layout.addComponent(new Label("Fucking Awesome!!"))
		})
		layout.addComponent(button)
	}
}

@VaadinServletConfiguration(ui = classOf[VaadinUI] , productionMode = true)
object HelloServlet extends VaadinServlet


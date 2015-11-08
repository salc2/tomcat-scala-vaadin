package tomcat.vaadin.server

import org.apache.catalina.startup.Tomcat
import org.apache.catalina._
import java.io.File
import java.lang.Exception
import javax.servlet.http._
import com.vaadin.data.util._
import com.vaadin.ui._
import com.vaadin.ui.Button.ClickListener
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

			Tomcat.addServlet(ctx, "vaadin", HelloServlet)
		ctx.addServletMapping("/*", "vaadin")
		tom.start()
		tom.getServer().await()

	}
}

object ConvertButtonListener{
	implicit val click = (c:ClickEvent => Any) => new ClickListener(){
			override def buttonClick(event:ClickEvent):Unit = c(event)
		}
	
}

import ConvertButtonListener._
@Theme("mytheme")
class VaadinUI extends UI {
	override def init(vr:VaadinRequest):Unit = {
		val layout = new VerticalLayout()
		val name = new TextField("Name")
		val id = new TextField("Id")
		val grid = new Grid("Users")
		grid.addColumn("Id",classOf[String])
		grid.addColumn("Name",classOf[String])
		val button = new Button("Save",	
			(e:ClickEvent) => grid.addRow(id.getValue,name.getValue))
		layout.setMargin(true)
		layout.setSpacing(true)
		layout.addComponent(id)
		layout.addComponent(name)
		layout.addComponent(grid)
		layout.addComponent(button)
		setContent(layout)
	}
}

@VaadinServletConfiguration(ui = classOf[VaadinUI] , productionMode = true)
object HelloServlet extends VaadinServlet


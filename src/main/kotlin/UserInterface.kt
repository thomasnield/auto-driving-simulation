import javafx.animation.SequentialTransition
import javafx.animation.Timeline
import javafx.application.Application
import javafx.scene.shape.Polygon
import tornadofx.*

fun main() {
    Application.launch(MyApp::class.java)
}

class MyApp: App(MyView::class)

class MyView: View() {
    override val root = vbox {
        val vehicle = VehicleSprite()

        button("Start") {
            setOnAction {
                val queue = SequentialTransition()

                queue.children += timeline(play=false) {
                    vehicle.move(300.0, 300.0, 0.0, this)
                }

                animations.forEach { move ->
                    queue.children += timeline(play=false) {
                        vehicle.move(move.point.x, move.point.y, move.rotation, this)
                    }
                }

                queue.play()
            }
        }
        pane {
            this += vehicle
        }
    }
}

class VehicleSprite: Polygon() {

    init {

        points.addAll(

                //nose
                30.0, 30.0,

                // right antenna
                35.0, 45.0,
                55.0, 15.0,
                35.0, 46.0,

                // right wing
                40.0, 60.0,

                // left wing
                20.0, 60.0,

                // left antenna
                25.0, 45.0,
                5.0, 15.0,
                25.0, 46.0,

                // top antenna
                29.75, 30.5,
                30.0, 0.0,
                30.25, 30.5
        )
    }

    fun move(x: Double, y: Double, degrees: Double, timeline: Timeline) = timeline.run {
        keyframe(2000.millis) {
            keyvalue(this@VehicleSprite.rotateProperty(), degrees)
            keyvalue(this@VehicleSprite.translateXProperty(), x)
            keyvalue(this@VehicleSprite.translateYProperty(), y)
        }
    }
}

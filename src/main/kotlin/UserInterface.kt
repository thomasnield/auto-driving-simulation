import javafx.animation.PathTransition
import javafx.animation.SequentialTransition
import javafx.animation.Timeline
import javafx.application.Application
import javafx.scene.shape.*
import tornadofx.*

fun main() {
    Application.launch(MyApp::class.java)
}

class MyApp: App(MyView::class)

class MyView: View() {
    override val root = borderpane {
        val vehicle = VehicleSprite()

        left = button("Start") {

            setOnAction {

                PathTransition().apply {
                    duration = 10_000.millis
                    orientation = PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT
                    node = vehicle
                    isAutoReverse = true

                    path = Path().apply {
                        elements.add(MoveTo(100.0, 150.0))
                        elements.add(QuadCurveTo(125.0, 200.0, 175.0, 250.0))
                    }

                    play()
                }
            }
        }
        center = pane {
            this += vehicle
            useMaxHeight = true
            useMaxWidth = true
        }
    }
}

class VehicleSprite: Polygon() {

    init {

        sequenceOf(
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
        ).windowed(2, 2)
        .asSequence()
        .map { Point(it[0], it[1]).rotate(Math.PI / 2.0) }
        .flatMap { sequenceOf(it.x, it.y) }
        .toList()
        .let {
            points.setAll(it)
        }
    }
}

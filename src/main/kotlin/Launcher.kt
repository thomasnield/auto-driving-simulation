import kotlin.math.cos
import kotlin.math.sin

fun main() {

    val vehicle = Vehicle()

    (0 until 100).forEach { frame ->
        vehicle.moveForwardAndRotate(10.0, 1.0)
        println(vehicle)
    }
}

val animations = Vehicle().let { vehicle ->
    (0 until 100).map { frame ->
        vehicle.moveForwardAndRotate(10.0, 1.0)
        vehicle.location
    }
}

class Vehicle {

    var location = Point(30.0, 30.0)

    // Must be between 0.0 and 360.0
    var rotationDegrees = 0.0

    val sensorTipLeft get() = location.shift(-10.0, 20.0).rotate(rotationDegrees)
    val sensorTipRight get() = location.shift(10.0, 20.0).rotate(rotationDegrees)
    val sensorTipCenter get() = location.shift(20.0)

    operator fun plusAssign(point: Point) {
        location = location.shift(point.x, point.y)
    }

    fun moveForward(distance: Double) {
        location = location.shift(0.0, distance).rotate(rotationDegrees)
    }

    fun moveForwardAndRotate(distance: Double, rotate: Double) {
        rotationDegrees += rotate
        location = location.shift(0.0, distance).rotate(rotate)
    }

    fun rotate(degrees: Double) {
        rotationDegrees += degrees
    }
    override fun toString() = "$location -> $sensorTipLeft \\ $sensorTipCenter / $sensorTipRight"
}

data class Point(val x: Double = 0.0, val y: Double = 0.0) {

    fun shift(xAdd: Double = 0.0, yAdd: Double = 0.0) = Point(x + xAdd, y + yAdd)

    // Perform rotation operation on Point
    // https://en.wikipedia.org/wiki/Rotation_matrix
    fun rotate(degrees: Double) = Point(x*cos(degrees) - y*sin(degrees), x*sin(degrees) + y*cos(degrees))

    operator fun plus(point: Point) = Point(x + point.x, y + point.y)

    override fun toString() = "($x, $y)"
}
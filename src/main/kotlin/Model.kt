import kotlin.math.cos
import kotlin.math.sin


val animations = Vehicle().let { vehicle ->

    (0 until 200).map { i ->
        vehicle.moveForwardAndRotate(10.0, 10.0)
    }
}

class Movement(val point: Point, val rotation: Double)

class Vehicle {

    var location = Point(0.0, 0.0)

    // Must be between 0.0 and 360.0
    var rotationDegrees = 0.0

    val sensorTipLeft get() = location.shift(-1.0, 20.0).rotate(rotationDegrees)
    val sensorTipRight get() = location.shift(1.0, 20.0).rotate(rotationDegrees)
    val sensorTipCenter get() = location.shift(20.0)

    operator fun plusAssign(point: Point) {
        location = location.shift(point.x, point.y)
    }

    fun moveForwardAndRotate(distance: Double, rotate: Double): Movement {
        rotationDegrees += rotate
        location = location.shift(rotate, distance).rotate(rotate)
        return Movement(location, rotationDegrees)
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

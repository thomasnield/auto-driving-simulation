import kotlin.math.cos
import kotlin.math.sin

// graph https://www.desmos.com/calculator/zllkuimfec

val animations = Vehicle().let { vehicle ->

    (0 until 100).map { i ->
        vehicle.moveForwardAndRotate(10.0, 30.0)
    }
}

class Movement(val point: Point, val rotation: Double)

class Vehicle {

    var location = Point(300.0, 300.0)

    // Must be between 0.0 and 360.0
    var rotationDegrees = 0.0

    val sensorTipLeft get() = location.shift(-1.0, 20.0).rotate(rotationDegrees)
    val sensorTipRight get() = location.shift(1.0, 20.0).rotate(rotationDegrees)
    val sensorTipCenter get() = location.shift(20.0)

    fun moveForwardAndRotate(distance: Double, rotate: Double): Movement {
        rotationDegrees += rotate
        val radians = rotationDegrees * (Math.PI / 180.0)
        location = location.shift(distance * sin(radians), distance * cos(radians))
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

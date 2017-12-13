@file:Suppress("UNUSED_PARAMETER")
package lesson6.task1

import lesson1.task1.sqr
import java.lang.Math.sqrt

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {
    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = Math.sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point): this(linkedSetOf(a, b, c))
    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return Math.sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        var distan = sqrt(sqr(center.x - other.center.x) + sqr(center.y - other.center.y)) - (radius + other.radius)
        return when {
            distan <= 0.0 -> 0.0
            else -> distan
        }
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = sqrt(sqr(center.x - p.x) + sqr(center.y - p.y)) <= radius
}

    /**
     * Отрезок между двумя точками
     */
    data class Segment(val begin: Point, val end: Point) {
        override fun equals(other: Any?) =
                other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

        override fun hashCode() =
                begin.hashCode() + end.hashCode()
    }

    /**
     * Средняя
     *
     * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
     * Если в множестве менее двух точек, бросить IllegalArgumentException
     */
    fun diameter(vararg points: Point): Segment {
        if (points.size < 2) {
            throw IllegalArgumentException()
        }
        var maxDist = 0.0
        var begin: Point? = null
        var end: Point? = null
        for (i in 0 until points.size) {
            for (j in i until points.size) {
                var dist = points[i].distance(points[j])
                if (dist > maxDist) {
                    maxDist = dist
                    begin = points[i]
                    end = points[j]
                }
            }
        }
        return Segment(begin!!, end!!)
    }

    /**
     * Простая
     *
     *
     * Построить окружность по её диаметру, заданному двумя точками
     * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
     */
    fun circleByDiameter(diameter: Segment): Circle {
        val point1 = Point(diameter.begin.x, diameter.begin.y)
        val point2 = Point(diameter.end.x, diameter.end.y)
        val cenX = (point1.x + point2.x) / 2
        val cenY = (point1.y + point2.y) / 2
        val radius = point1.distance(point2) / 2
        return Circle(Point(cenX, cenY), radius)
    }

    /**
     * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
     * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
     * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
     * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
     */
    class Line private constructor(val b: Double, val angle: Double) {
        init {
            assert(angle >= 0 && angle < Math.PI) { "Incorrect line angle: $angle" }
        }

        constructor(point: Point, angle: Double) : this(point.y * Math.cos(angle) - point.x * Math.sin(angle), angle)

        /**
         * Средняя
         *
         * Найти точку пересечения с другой линией.
         * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
         */
        fun crossPoint(other: Line): Point {
            var x: Double
            var y: Double
            if (angle == other.angle) {
                throw IllegalArgumentException()
            }
            if (Math.abs(Math.PI / 2 - angle) < 1e-5) {
                x = -b
                y = other.b
            } else if (Math.abs(Math.PI / 2 - other.angle) < 1e-5) {
                x = -other.b
                y = b
            } else {
                x = (other.b * Math.cos(angle) - b * Math.cos(other.angle)) / (Math.cos(angle) * Math.cos(other.angle) * (Math.tan(angle) - Math.tan(other.angle)))
                y = x * Math.tan(angle) + b / Math.cos(angle)
            }
            return Point(x, y)
        }

        override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

        override fun hashCode(): Int {
            var result = b.hashCode()
            result = 31 * result + angle.hashCode()
            return result
        }

        override fun toString() = "Line(${Math.cos(angle)} * y = ${Math.sin(angle)} * x + $b)"
    }

    /**
     * Средняя
     *
     * Построить прямую по отрезку
     */
    fun lineBySegment(s: Segment): Line = TODO()

    /**
     * Средняя
     *
     * Построить прямую по двум точкам
     */
    fun lineByPoints(a: Point, b: Point): Line = TODO()

    /**
     * Сложная
     *
     * Построить серединный перпендикуляр по отрезку или по двум точкам
     */
    fun bisectorByPoints(a: Point, b: Point): Line {
        var k: Double
        if (b.y - a.y == 0.0) {
            k = Math.PI / 2
        } else if (b.x - a.x == 0.0) {
            k = 0.0
        } else {
            k =-1/ (b.y - a.y) / (b.x - a.x)
        }
        return Line(Point((a.x + b.x) / 2, (a.y + b.y) / 2), k)
    }

    /**
     * Средняя
     *
     * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
     * Если в списке менее двух окружностей, бросить IllegalArgumentException
     */
    fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> = TODO()

    /**
     * Сложная
     *
     * Дано три различные точки. Построить окружность, проходящую через них
     * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
     * Описание алгоритмов см. в Интернете
     * (построить окружность по трём точкам, или
     * построить окружность, описанную вокруг треугольника - эквивалентная задача).
     */
    fun circleByThreePoints(a: Point, b: Point, c: Point): Circle = TODO()

    /**
     * Очень сложная
     *
     * Дано множество точек на плоскости. Найти круг минимального радиуса,
     * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
     * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
     *
     * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
     * три точки данного множества, либо иметь своим диаметром отрезок,
     * соединяющий две самые удалённые точки в данном множестве.
     */
    fun minContainingCircle(vararg points: Point): Circle = TODO()




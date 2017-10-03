@file:Suppress("UNUSED_PARAMETER")
package lesson2.task1

import lesson1.task1.discriminant
import java.lang.Math.abs

/**
 * Пример
 *
 * Найти наименьший корень биквадратного уравнения ax^4 + bx^2 + c = 0
 */
fun minBiRoot(a: Double, b: Double, c: Double): Double {
    // 1: в главной ветке if выполняется НЕСКОЛЬКО операторов
    if (a == 0.0) {
        if (b == 0.0) return Double.NaN // ... и ничего больше не делать
        val bc = -c / b
        if (bc < 0.0) return Double.NaN // ... и ничего больше не делать
        return -Math.sqrt(bc)
        // Дальше функция при a == 0.0 не идёт
    }
    val d = discriminant(a, b, c)   // 2
    if (d < 0.0) return Double.NaN  // 3
    // 4
    val y1 = (-b + Math.sqrt(d)) / (2 * a)
    val y2 = (-b - Math.sqrt(d)) / (2 * a)
    val y3 = Math.max(y1, y2)       // 5
    if (y3 < 0.0) return Double.NaN // 6
    return -Math.sqrt(y3)           // 7
}

/**
 * Простая
 *
 * Мой возраст. Для заданного 0 < n < 200, рассматриваемого как возраст человека,
 * вернуть строку вида: «21 год», «32 года», «12 лет».
 */
fun ageDescription(age: Int): String {
    val agePro100 = age % 100
    return   if (agePro100 in 11 .. 14) {
          "$age лет"
        } else {
        val agePro10 = age % 10
        if (agePro10 == 0) {
             "$age  лет"
        } else if (agePro10 == 1) {
             "$age год"
        } else if (agePro10 in 2 .. 4) {
             "$age года"
        } else if (agePro10 in 5 .. 9) {
             "$age лет"
        } else "не определено"
    }
    }


/**
 * Простая
 *
 * Путник двигался t1 часов со скоростью v1 км/час, затем t2 часов — со скоростью v2 км/час
 * и t3 часов — со скоростью v3 км/час.
 * Определить, за какое время он одолел первую половину пути?
 */
fun timeForHalfWay(t1: Double, v1: Double,
                   t2: Double, v2: Double,
                   t3: Double, v3: Double): Double {
    val Way1 = t1 * v1
    val Way2 = t2 * v2
    val Way3 = t3 * v3
    val AllWay = (Way1 + Way2 + Way3) / 2
    return when {
        Way1 >= AllWay -> AllWay / v1
        Way1 + Way2 >= AllWay -> (AllWay - Way1) / v2 + t1

        else -> return (AllWay - Way1 - Way2) / v3 + t1 + t2
    }
}



/**
 * Простая
 *
 * Нa шахматной доске стоят черный король и две белые ладьи (ладья бьет по горизонтали и вертикали).
 * Определить, не находится ли король под боем, а если есть угроза, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от первой ладьи, 2, если только от второй ладьи,
 * и 3, если угроза от обеих ладей.
 * Считать, что ладьи не могут загораживать друг друга
 */
fun whichRookThreatens(kingX: Int, kingY: Int,
                       rookX1: Int, rookY1: Int,
                       rookX2: Int, rookY2: Int): Int {
    return when {
        (kingX == rookX1 || kingY == rookY1) && (kingX != rookX2 && kingY != rookY2) -> 1
        (kingX == rookX2 || kingY == rookY2) && (kingX != rookX1 && kingY != rookY1) -> 2
        kingX != rookX1 && kingX != rookX2 && kingY != rookY1 && kingY != rookY2 -> 0
        (kingX == rookX2 || kingX == rookX1) && (kingY == rookY2 || kingY == rookY1) -> 3

        else -> TODO()
    }
}

/**
 * Простая
 *
 * На шахматной доске стоят черный король и белые ладья и слон
 * (ладья бьет по горизонтали и вертикали, слон — по диагоналям).
 * Проверить, есть ли угроза королю и если есть, то от кого именно.
 * Вернуть 0, если угрозы нет, 1, если угроза только от ладьи, 2, если только от слона,
 * и 3, если угроза есть и от ладьи и от слона.
 * Считать, что ладья и слон не могут загораживать друг друга.
 */
fun rookOrBishopThreatens(kingX: Int, kingY: Int,
                          rookX: Int, rookY: Int,
                          bishopX: Int, bishopY: Int): Int {
    val residaulX = abs (kingX - bishopX)
    val residaulY = abs (kingY - bishopY)
    return when {
        (residaulX != residaulY) && (kingX != rookX && kingY != rookY) -> 0
        (residaulX != residaulY) && (kingX == rookX || kingY == rookY) -> 1
        (residaulX == residaulY) && (kingX != rookX && kingY != rookY) -> 2
        (residaulX == residaulY) && (kingX == rookX || kingY == rookY) -> 3
        else  -> TODO ()
    }
}

/**
 * Простая
 *
 * Треугольник задан длинами своих сторон a, b, c.
 * Проверить, является ли данный треугольник остроугольным (вернуть 0),
 * прямоугольным (вернуть 1) или тупоугольным (вернуть 2).
 * Если такой треугольник не существует, вернуть -1.
 */
fun sqr(x: Double) = x * x

fun triangleKind(a: Double, b: Double, c: Double): Int {
    var cosA = (sqr(b) + sqr(c) - sqr(a)) / 2 * b * c
    val cosB = (sqr(a) + sqr(c) - sqr(b)) / 2 * a * c
    val cosC = (sqr(a) + sqr(b) - sqr(c)) / 2 * b * a
    return when {
        (a + b < c || a + c < b || b + c < a) -> -1
        (cosA > 90 || cosB > 90 || cosC > 90) -> 2
        (cosA == 90.0 || cosB == 90.0 || cosC == 90.0) -> 1
        (cosA < 90 && cosB < 90 && cosC < 90) -> 0

        else -> TODO()
    }

}

/**
 * Средняя
 *
 * Даны четыре точки на одной прямой: A, B, C и D.
 * Координаты точек a, b, c, d соответственно, b >= a, d >= c.
 * Найти длину пересечения отрезков AB и CD.
 * Если пересечения нет, вернуть -1.
 */
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int = TODO()
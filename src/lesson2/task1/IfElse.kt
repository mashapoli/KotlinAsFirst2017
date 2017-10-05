@file:Suppress("UNUSED_PARAMETER")
package lesson2.task1

import lesson1.task1.discriminant
import lesson1.task1.sqr
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
    val ageMod100 = age % 100
    val ageMod10 = age % 10
    return when{
        ageMod100 in 11..14 -> "$age лет"
        ageMod10 == 0 -> "$age лет"
        ageMod10 == 1 -> "$age год"
        ageMod10 in 2..4 -> "$age года"
        ageMod10 in 5..9 -> "$age лет"
        else -> "не определено"

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
    val kingXEquallyRookX1 = kingX == rookX1
    val kingYEquallyRookY1 = kingY == rookY1
    val kingXNotEqRookX2 = kingX != rookX2
    val kingYNotEqRookY2 = kingY != rookY2
    val kingXEqaRookX2 = kingX == rookX2
    val kingYEqaRookY2 = kingY == rookY2
    val kingXNotEqRookX1 = kingX != rookX1
    val kingYNotEqRookY1 = kingY != rookY1
    return when {
        (kingXEquallyRookX1 || kingYEquallyRookY1) && (kingXNotEqRookX2 && kingYNotEqRookY2) -> 1
        (kingXEqaRookX2 || kingYEqaRookY2) && (kingXNotEqRookX1 && kingYNotEqRookY1) -> 2
        (kingXNotEqRookX1 && kingXNotEqRookX2 && kingYNotEqRookY1 && kingYNotEqRookY2) -> 0
        (kingXEqaRookX2 || kingXEquallyRookX1) && (kingYEqaRookY2 || kingYEquallyRookY1) -> 3

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
    val residaulX = abs(kingX - bishopX)
    val residaulY = abs(kingY - bishopY)
    val residaulXEqallyResidaulY = residaulX == residaulY
    val residaulXNotEqaResidaulY = residaulX != residaulY
    val kingXNotEqRookX = kingX != rookX
    val kingXEqaRookX = kingX == rookX
    val kingYNotEqRookY = kingY != rookY
    val kingYEqaRookY = kingY == rookY
    return when {
        residaulXNotEqaResidaulY && (kingXNotEqRookX && kingYNotEqRookY) -> 0
        residaulXNotEqaResidaulY && (kingXEqaRookX || kingYEqaRookY) -> 1
        residaulXEqallyResidaulY && (kingXNotEqRookX && kingYNotEqRookY) -> 2
        residaulXEqallyResidaulY && (kingXEqaRookX || kingYEqaRookY) -> 3
        else -> TODO()
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


fun triangleKind(a: Double, b: Double, c: Double): Int {
    return if (a + b < c || a + c < b || b + c < a) {
        -1
    } else when {
        a > b && a > c -> when {
            sqr(a) == sqr(b) + sqr(c) -> 1
            sqr(a) > sqr(b) + sqr(c) -> 2
            else -> 0
        }
        b > a && b > c -> when {
            sqr(b) == sqr(a) + sqr(c) -> 1
            sqr(b) > sqr(a) + sqr(c) -> 2
            else -> 0
        }
        c > a && c > b -> when {
            sqr(c) == sqr(b) + sqr(a) -> 1
            sqr(c) > sqr(b) + sqr(a) -> 2
            else -> 0
        }
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
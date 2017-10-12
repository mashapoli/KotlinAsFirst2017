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
    val way1 = t1 * v1
    val way2 = t2 * v2
    val way3 = t3 * v3
    val halfWay = (way1 + way2 + way3) / 2
    return when {
        way1 >= halfWay -> halfWay / v1
        way1 + way2 >= halfWay -> (halfWay - way1) / v2 + t1
        else -> return (halfWay - way1 - way2) / v3 + t1 + t2
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
    val kingXEqaRookX2 = kingX == rookX2
    val kingYEqaRookY2 = kingY == rookY2

    val noThreats = !kingXEquallyRookX1 && !kingXEqaRookX2 && !kingYEquallyRookY1 && !kingYEqaRookY2
    val firstRookThreat = (kingXEquallyRookX1 || kingYEquallyRookY1) && (!kingXEqaRookX2 && !kingYEqaRookY2)
    val secondRookThreat = (kingXEqaRookX2 || kingYEqaRookY2) && (!kingXEquallyRookX1 && !kingYEquallyRookY1)
    val bothRooksThreat = (kingXEqaRookX2 || kingXEquallyRookX1) && (kingYEqaRookY2 || kingYEquallyRookY1)
    return when {
        noThreats -> 0
        firstRookThreat -> 1
        secondRookThreat -> 2
        bothRooksThreat -> 3

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
    val differenceX = abs(kingX - bishopX)
    val differenceY = abs(kingY - bishopY)
    val differenceXEqallyDifferenceY =  differenceX ==  differenceY
    val kingXEqaRookX = kingX == rookX
    val kingYEqaRookY = kingY == rookY

    val noThreats = !differenceXEqallyDifferenceY && (!kingXEqaRookX && !kingYEqaRookY )
    val rookThreat = !differenceXEqallyDifferenceY && (kingXEqaRookX || kingYEqaRookY)
    val bishopThreat = differenceXEqallyDifferenceY && (!kingXEqaRookX && !kingYEqaRookY )
    val rookBishopThreat = differenceXEqallyDifferenceY && (kingXEqaRookX || kingYEqaRookY)
    return when {
        noThreats -> 0
        rookThreat -> 1
        bishopThreat -> 2
        rookBishopThreat -> 3
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
    if (a + b < c || a + c < b || b + c < a) {
        return -1
    }
    var max: Double
    var l1: Double
    var l2: Double
    if (a >= b && a >= c) {
        max = a
        l1 = b
        l2 = c
    } else if (b >= a && b >= c) {
        max = b
        l1 = a
        l2 = c
    } else {
        max = c
        l1 = a
        l2 = b

    }
    return when {
        sqr(max) == sqr(l1) + sqr(l2) -> 1
        sqr(max) > sqr(l1) + sqr(l2) -> 2
        else -> 0
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
fun segmentLength(a: Int, b: Int, c: Int, d: Int): Int {
    return when {
        (b < c || a > d) -> -1  //не пересекаются
        (b >= d && a >= c) -> d - a // точка d лежит левее b, с левее а, таким образом отрзки пересекаются в DA
        (a >= c && b <= d) -> b - a // отрезок АВ меньше CD, таким образом они пресекаются в АВ
        (a <= c && d <= b) -> d - c // отрезок CD меньше АВ, таким образом они пресекаются в CD
        (a <= c && b <= d) -> b - c // точка b лежит левее d, a левее c, таким образом отрзки пересекаются в BC
        else -> TODO()

    }
}
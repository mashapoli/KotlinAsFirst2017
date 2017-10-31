@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import lesson1.task1.numberRevert
import lesson1.task1.sqr
import java.lang.Math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result *= i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    return (2..Math.sqrt(n.toDouble()).toInt()).none { n % it == 0 }
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 */
fun digitNumber(n: Int): Int =
        when {
        abs(n) < 10 -> 1
        else -> digitNumber(n / 10) + 1
    }



/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int {
    if (n <= 2) {
        return 1
    }
    var fibPrev = 1
    var fibCur = 1
    var cur = 2
    while (cur != n) {
        val tmp = fibCur
        fibCur += fibPrev
        fibPrev = tmp
        cur++
    }
    return fibCur
}

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int {
    var numberM = abs(m)
    var numberN = abs(n)
    while (numberM != numberN) {
        if (numberM > numberN) {
            numberM -= numberN
        } else numberN -= numberM
    }
    return abs(m * n) / numberM
}


/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int = (2..n).firstOrNull { n % it == 0 } ?: 1


//
//
//}
/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int = (n - 1 downTo 1).firstOrNull { n % it == 0 } ?: 1





/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean = (2..min(m, n)).none { m % it == 0 && n % it == 0 }


/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean = (0..sqrt(n.toDouble()).toInt()).any { it * it in m..n }


/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    var i = 1
    var koef = -1
    var sini: Double
    var sin = 0.0
    var xNew = x % (2 * Math.PI)
    do {
        sini = pow(xNew, i.toDouble()) / factorial(i)
        koef *= (-1)
        sin += koef * sini
        i += 2
    } while (abs(sini) > eps)
    return sin

}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    var i = 0
    var koef = -1
    var cosi: Double
    var cos = 0.0
    var xNew = x % (2 * PI)
    do {
        cosi = pow(xNew, i.toDouble()) / factorial(i)
        koef *= (-1)
        cos += koef * cosi
        i += 2
    } while (cosi > eps)
    return cos

}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 * Не использовать строки при решении задачи.
 */
fun revert(n: Int): Int {
    var number = n
    var result = 0
    do {
        result = result * 10 + number % 10
        number /= 10
    } while (number >= 1)
    return result
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 */
fun isPalindrome(n: Int): Boolean = revert(n) == n


/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var number = n
    var firstDigit = number % 10
    do {
        if (firstDigit != number % 10) {
            return true
        }
        number /= 10
    } while (number > 0)
    return false
}


/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 */
fun squareSequenceDigit(n: Int): Int {
    var seq = 0
    var i = 1
    var square = 0
    while (seq < n) {
        square = i * i
        seq += digitNumber(square)
        i++
    }
    while (seq > n) {
        square /= 10
        seq--
    }

    return square % 10
}

/**
 * Сложная
 *

 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 */
fun fibSequenceDigit(n: Int): Int {
    var seq = 0
    var i = 1
    var fi = 0
    while (seq < n) {
        fi = fib(i)
        seq += digitNumber(fi)
        i++
    }
    while (seq > n) {
        fi /= 10
        seq--
    }

    return fi % 10
}

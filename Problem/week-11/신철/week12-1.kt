
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()
    val m = scanner.nextInt()

    val graph = Array(n + 1) { mutableListOf<Int>() }
    val arr = IntArray(n + 1)

    repeat(m) {
        val a = scanner.nextInt()
        val b = scanner.nextInt()
        graph[a].add(b)
        arr[b]++
    }

    val queue = LinkedList<Int>()
    //val result = StringBuilder()
    val result = mutableListOf<Int>()

    for (i in 1..n) {
        if (arr[i] == 0) {
            queue.add(i)
        }
    }

    while (queue.isNotEmpty()) {
        val current = queue.poll()
        //result.append(current).append(' ')
        result.add(current)
        for (next in graph[current]) {
            arr[next]--
            if (arr[next] == 0) {
                queue.add(next)
            }
        }
    }

//graph[1] = []
//graph[2] = []
//graph[3] = [1]
//graph[4] = [2]

// indegree[1] = 1
// indegree[2] = 1
// indegree[3] = 0
// indegree[4] = 0
     
    //println(result.toString().trim())
    println(result.joinToString(" "))
}
import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val n = scanner.nextInt()
    
    val buildTime = IntArray(n + 1)
    val needToBuild = IntArray(n + 1)
    val graph = Array(n + 1) { mutableListOf<Int>() }
    val result = IntArray(n + 1)
    
    for (i in 1..n) {
        buildTime[i] = scanner.nextInt()
        while (true) {
            val pre = scanner.nextInt()
            if (pre == -1) break
            graph[pre].add(i)
            needToBuild[i]++
        }
    }
    
    // 위상 정렬을 위한 큐
    val queue: Queue<Int> = LinkedList()
    
    // degree가 0인 노드를 큐에 넣음
    for (i in 1..n) {
        if (needToBuild[i] == 0) {
            queue.add(i)
            result[i] = buildTime[i]
        }
    }
    
    // 위상 정렬 수행
    while (queue.isNotEmpty()) {
        val current = queue.poll()
        for (next in graph[current]) {
            result[next] = maxOf(result[next], result[current] + buildTime[next])
            needToBuild[next]--
            if (needToBuild[next] == 0) {
                queue.add(next)
            }
        }
    }
    
//println
//buildTime[1] = 10
//buildTime[2] = 10
//buildTime[3] = 4
//buildTime[4] = 4
//buildTime[5] = 3
    
//graph[1] = [2, 3, 4]
//graph[2] = []
//graph[3] = [4, 5]
//graph[4] = []
//graph[5] = []

//needToBuild[1] = 0
//needToBuild[2] = 1
//needToBuild[3] = 1
//needToBuild[4] = 2
//needToBuild[5] = 1
    
    for (i in 1..n) {
        println(result[i])
    }
}
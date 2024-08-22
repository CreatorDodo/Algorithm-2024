import java.util.*

fun main() {

//중요한건, 시간을 처리하는 부분이다.
//1. 우선 초기에 queue에 들어간 값의 경우에는 시간을 계산할 수 계산할 수 없어서 result에 세팅
//2. while문을 위상정렬과 동일한 로직으로 돈다.
// -> while문 내에서 maxTime을 계산해주는데
// 2-1. 내 선수(내가 만족해야 하는 조건)의 초기 time값과 갱신된 시간을 비교하여 더 큰 값을 maxTime에 넣어준다.
// 2-2.  이 코드를 보자.
//result[i] = max(result[i], (maxTime + time[i]))

//왜 이걸 이렇게 작성해야 하냐면 특정 i가 여러번 갱신될 수 있는데, 일전에 계산된 값보다 현재 값이 작은 경우가 존재할 수 있음

// 2-2에 대한 반례
//5
//10 -1
//20 1 -1
//30 2 -1
//40 3 5 -1
//100 -1

// 내 알고리즘에 의하면
//graph: [1: [2], 5: [4], 2: [3], 3: [4]]

//graph를 보면 4의 경우에는 5와 3에서 두번 갱신되는데, 더 큰 값을 들고 있어야 해서 2-2 작업이 필요하다.


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

    graph.sorted()

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
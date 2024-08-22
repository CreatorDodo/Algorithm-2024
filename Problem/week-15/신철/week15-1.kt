/*
https://school.programmers.co.kr/learn/courses/30/lessons/133500

프로그래머스 lv3
인천 앞바다에는 1부터 n까지 서로 다른 번호가 매겨진 등대 n개가 존재합니다. 
등대와 등대 사이를 오가는 뱃길이 n-1개 존재하여, 어느 등대에서 출발해도 다른 모든 등대까지 이동할 수 있습니다. 
등대 관리자 윤성이는 전력을 아끼기 위하여, 이 중 몇 개의 등대만 켜 두려고 합니다. 하지만 등대를 아무렇게나 꺼버리면, 
뱃길을 오가는 배들이 위험할 수 있습니다. 한 뱃길의 양쪽 끝 등대 중 적어도 하나는 켜져 있도록 등대를 켜 두어야 합니다.

예를 들어, 아래 그림과 같이 등대 8개와 7개의 뱃길들이 있다고 합시다. 이 경우 1번 등대와 5번 등대 두 개만 켜 두어도 
모든 뱃길은 양쪽 끝 등대 중 하나가 켜져 있으므로, 배들은 안전하게 운항할 수 있습니다.

등대의 개수 n과 각 뱃길이 연결된 등대의 번호를 담은 이차원 배열 lighthouse가 매개변수로 주어집니다. 
윤성이가 켜 두어야 하는 등대 개수의 최솟값을 return 하도록 solution 함수를 작성해주세요.

입출력 예
n	lighthouse	result
8	[[1, 2], [1, 3], [1, 4], [1, 5], [5, 6], [5, 7], [5, 8]]	2
10	[[4, 1], [5, 1], [5, 6], [7, 6], [1, 2], [1, 3], [6, 8], [2, 9], [9, 10]]	3

 첫 번째
Node 1: [2, 3, 4, 5]
Node 2: [1]
Node 3: [1]
Node 4: [1]
Node 5: [1, 6, 7, 8]
Node 6: [5]
Node 7: [5]
Node 8: [5]

 두 번째 
Node 1: [4, 5, 2, 3]
Node 2: [1, 9]
Node 3: [1]
Node 4: [1]
Node 5: [1, 6]
Node 6: [5, 7, 8]
Node 7: [6]
Node 8: [6]
Node 9: [2, 10]
Node 10: [9]
*/


 class Solution {
    val graph = Array(n + 1) { mutableListOf<Int>() }
    for (path in lighthouse) {
        graph[path[0]].add(path[1])
        graph[path[1]].add(path[0])
    }

    for (i in 1..n) {
        println("Node $i: ${graph[i]}")
    }

    val dp = Array(n + 1) { IntArray(2) { -1 } }

    fun dfs(node: Int, parent: Int) {
        dp[node][0] = 0
        dp[node][1] = 1

        for (neighbor in graph[node]) {
            if (neighbor == parent) continue
            dfs(neighbor, node)
            dp[node][0] += dp[neighbor][1]  // If this node is off, neighbor must be on
            dp[node][1] += minOf(dp[neighbor][0], dp[neighbor][1])  // If this node is on, neighbor can be either on or off
        }
    }
    dfs(1, -1)
    return minOf(dp[1][0], dp[1][1])
}

//다른 짧게 푼 예제 올림.
/*
fun Solution {
    val graph = Array(100_001) { mutableListOf<Int>() }
    val lightOn = BooleanArray(100_001)

    fun preOrder(parent: Int, node: Int) {
        for (child in graph[node]) {
            if (child == parent) continue
            preOrder(node, child)

            if (!lightOn[child]) {
                lightOn[node] = true
            }
        }
    }

    fun solution(n: Int, lighthouse: Array<IntArray>): Int {
        for ((s, e) in lighthouse) {
            graph[s].add(e)
            graph[e].add(s)
        }

        preOrder(0, 1)
        return lightOn.count { it }
    }
}
 */
/*
코딩대회 서버관리자로서 참가자들의 제출 기록을 보고 등수를 계산하려 합니다. 

제출기록은 

"12:10 AC 1 silvia", 
"12:13 AC 1 silvia", 
"12:19 WA 1 silvia", 
"12:20 AC 2 silvia", 
"13:20 WA 100 ray", 
"13:21 AC 100 ray", 
"13:59 WA 3 silvia", 
"13:22 WA 1 chul",
"14:01 AC 1 chul" 

처럼 제출시각, 정답여부, 문제번호, 제출한 참가자 이름 형태로 시간순으로 주어집니다.
"AC"는 정답, "WC" 는 오답을 의미하고 오답 후에 정답을 입력하면 해당 문제는 정답으로 인정합니다.
등수를 계산하는 방법은
1. 더많은 문제를 맟춘 참가자가 등수가 높습니다.
2. 맞은 문제 수가 같다면 패널티 가 적은 참가자가 등수가 높습니다.
3. 만약 패널티가 같다면, 이름의 철자 순으로 빠른 참가자가 등수가 높습니다.

패널티는 다음과 같이 부과됩니다.
1. 맞은 문제에 대해서만 패널티를 부과합니다.
2. 각 문제의 패널티는 대회 시작 이후 해당 문제를 처음 맞은 시간입니다.
3. 만약 문제를 처음 맞기 이전에 틀린 적이 있다면 맞기 이전에 틀릿 횟수 곱하기 20 만큼 해당 문제 해당 문제에 패널티가 부과됩니다. 단, 문제를 맞은 이후 틀린 횟수는 패널티에 영향을 주지 않습니다.
4. 맞은 모든 문제의 패널티를 더합니다.

대회의 제출 기록을 담은 Array<String> logs와 대회 시작을 뜻하는 문자열 stime이 매개변수를 주어질때, 등수가 높은 순서대로 참가자의 이름을 배열을 담아 return하도록 solution 함수를 완성해주세요
*/

fun solution(logs: ArrayList<String>, stime: String): ArrayList<String> {
  val attempts: MutableMap<String, MutableList<Attempt>> =mutableMapOf()
  val startMinute = stime.split(":").let{it->it[0].toInt() * 60 + it[1].toInt()}

//"silvia" = [[1, 10, 1], [1, 13, 1], [2, 20, -1], [3, 20, -1]],
//"ray" = [[100, 20, -1], [100, 101, 1]],
//"chul" = [[1, 20, -1]]
	logs.forEach{log->
		val part = log.split(" ")
	  val attemptMin = part[0].split(":")
		  .let{it->it[0].toInt() * 60 + it[1].toInt()}
		val result = part[1]
	  val problemNum = part[2].toInt()
	  val name = part[3]

	  val participantAttempts = attempts.getOrPut(name){mutableListOf()}
	  // solved, penalty, wrongAttempts
	  val existingAttempt = participantAttempts.find{ it.problemNum == problemNum}

		// 처음 맞힘 ->이름을 key으로하고, 맞힌문제 번호, 패널티
	  // 처음 틀림 ->이름을 key으로하고, 틀린문제 번호, 패널티
	  // 한문제만 틀린적이 있는 사용자의 경우 틀린갯수 -1 하자.
	  if (result == "AC") {
			//만약 이전에 해당문제를 틀린 상태에서 맞힌거라면, 이전 틀린 정보들을 삭제하고 해당 맞춘문제에 시간 패널티를 적용한다.
		  //만약 이전에 해당문제를 맞춘 상태라면 attempts에 추가하지 않는다.
		  //attempts[name]!!.add(problemNum, (startMinute - attemptMin).toInt(), 1)
		  if(existingAttempt == null) {
		     participantAttempts
		     .add(Attempt(problemNum,(attemptMin - startMinute),1))
		  }else if(existingAttempt.status == -1) {
		  //시도한 모든 attempts들의 갯수를 구한다.
		     participantAttempts
		     .add(Attempt(problemNum,(attemptMin - startMinute),1))
		     existingAttempt.penalty = existingAttempt.status * 20
		     existingAttempt.status = 1
		     participantAttempts.remove(existingAttempt)
			}else if(existingAttempt.status == 1) {
	     //do nothing
		  }
		} else if (result == "WA") {
    // 만약 이전에 해당문제를 맞춘 상태라면 추가하지 않는다.
    // 만약 이전에 해당문제를 틀릴 상태라면 패널티를 20으로 해서 추가한다.
    if(existingAttempt == null) {
	     participantAttempts.add(Attempt(problemNum, 20, -1))
    }else if(existingAttempt.status == -1) {
	     existingAttempt.penalty += existingAttempt.penalty + 20
    }else if(existingAttempt.status == 1) {
	     //do nothing
    }
	} //end of forEach

	val results = attempts.map{}.sortedWith(compareBy(
		{-it.second},{ it.third},{ it.first})
	)
	val resultArray = ArrayList<String>()
  results.forEachIndexed{index, (name, solved, penalty)->
	resultArray.add("${index + 1} $name $solved $penalty")
	
	return resultArray
}
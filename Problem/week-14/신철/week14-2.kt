class Solution {
    fun removeDuplicates(nums: IntArray): Int {
        if (nums.size <= 2) return nums.size
        var result=1
        var count=1

        nums.sort()

        for(i in 1 until nums.size) {
            //i인덱스를 움직여야
            if(nums[i] == nums[i-1]) {
                count++
            }else {
                count =1
            }
            //count가 3이면 , 최종적으로 넣는곳
            if(count <= 2) {
                nums[result] = nums[i]
                result++
            }
        }

        return result
    }
}

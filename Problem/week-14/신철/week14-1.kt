
//Input: nums1 = [1,2,3,0,0,0], m = 3, nums2 = [2,5,6], n = 3
//Output: [1,2,2,3,5,6]

//Input: nums1 = [1], m = 1, nums2 = [], n = 0
//Output: [1]

class Solution {
    fun merge(nums1: IntArray, m: Int, nums2: IntArray, n: Int): IntArray {
        val result = IntArray(m+n)
        var i=0
        var j=0
        var k=0

        //
        while (i < m && j<n) {
            if(nums1[i] <= nums2[j]) {
                result[k] = nums1[i]
                i++
            }else {
                result[k]= nums2[j]
                j++
            }
            k++
        }
        while (i<m) {
            result[k] = nums1[i]
            i++
            k++
        }

        while (j<n) {
            result[k] = nums2[j]
            j++
            k++
        }

        for(temp in 0 until m+n){
            nums1[temp] = result[temp]
        }

        return nums1
    }
}
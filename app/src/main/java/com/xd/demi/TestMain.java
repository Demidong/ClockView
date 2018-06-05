package com.xd.demi;


import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by demi on 2018/5/11.
 * https://blog.csdn.net/qy1387/article/details/7752973
 */

public class TestMain {
    public static void main(String args[]) {
        int arr[] = new int[]{10, 8, 20, 15, 7, 5, 3, 1};
        int arr2[] = new int[]{1, 2, 3, 5, 4, 6, 7, 8, 9, 10};

//        insertSort(arr);
//        shellSort(arr);
//        selectSort(arr);
//        heapSort(arr);
//        mergingSort(arr);
//        quickSort(arr);
        //bubbleSort(arr2);
//        CocktailSort(arr2);

        quick_sort(arr,0,arr.length-1);
     //   radixSort(arr);
       //System.out.print(  longestPalindrome2("abababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababababa"));

    }


    public static  String longestPalindrome(String s) {
        int temp = 0 ;
        int right = 1;
        int left = 0;
        for (int i = 0; i <s.length() ; i++) {
            for (int j = i+1; j <s.length() ; j++) {
                if(s.charAt(i) == s.charAt(j)){
                    temp =j+1;
                    if(s.charAt(i) != s.charAt(left) && right -left  < temp-i){
                        left = i;
                    }
                }
            }
            if(right - left < temp-i){
                right = temp ;
            }
        }
        String str = s.substring(left,right);
        for (int i = 0; i < str.length(); i++) {
            if(i<= str.length()/2 && str.charAt(i) != str.charAt(str.length()-i-1)){
                return s.substring(0,1);
            }
        }
        return str;
    }

    public static  String longestPalindrome2(String s) {
        if(s.length() == 1 ){
            return  s;
        }
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i <s.length() ; i++) {
            for (int j = i+1; j <s.length() ; j++) {
                if(s.charAt(i) == s.charAt(j)){
                    strings.add(s.substring(i,j+1));
                }
            }
        }
        System.out.print("11111 : "+Arrays.toString(strings.toArray()));
        ArrayList<String> deleteList = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            String str  = strings.get(i);
                for (int j = 1; j < str.length() ; j++) {
                    if(str.charAt(j) != str.charAt(str.length()-j-1) && j< str.length()/2){
                        deleteList.add(str);
                        break;
                    }
                }
        }
        for (int i = 0; i < deleteList.size() ; i++) {
            strings.remove(deleteList.get(i));
        }
        System.out.print("22222 : "+Arrays.toString(strings.toArray()));
        int maxLength = 1;
        String result = String.valueOf(s.charAt(0));
        for (int i = 0; i <strings.size() ; i++) {
            String str  = strings.get(i);
            if(maxLength < str.length()){
                maxLength = str.length();
                result = str;
            }
        }
        return result;
    }
    public static  String longestPalindrome3(String s) {
        if(s.length() == 1 ){
            return  s;
        }
        ArrayList<String> strings = new ArrayList<>();
        //先找出首尾相同的字符串
        for (int i = 0; i <s.length() ; i++) {
            for (int j = i+1; j <s.length() ; j++) {
                if(s.charAt(i) == s.charAt(j)){
                    strings.add(s.substring(i,j+1));
                }
            }
        }
        System.out.print("11111 : "+Arrays.toString(strings.toArray()));
        //找出是回文的字符串
        ArrayList<String> deleteList = new ArrayList<>();
        for (int i = 0; i < strings.size(); i++) {
            String str  = strings.get(i);
                for (int j = 1; j < str.length() ; j++) {
                    if(str.charAt(j) != str.charAt(str.length()-j-1) && j< str.length()/2){
                        deleteList.add(str);
                        break;
                    }
                }
        }
        for (int i = 0; i < deleteList.size() ; i++) {
            strings.remove(deleteList.get(i));
        }
        System.out.print("22222 : "+Arrays.toString(strings.toArray()));
        //找出最长的那一个回文
        int maxLength = 1;
        String result = String.valueOf(s.charAt(0));
        for (int i = 0; i <strings.size() ; i++) {
            String str  = strings.get(i);
            if(maxLength < str.length()){
                maxLength = str.length();
                result = str;
            }
        }
        return result;
    }


    /**
     * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2 。

     请找出这两个有序数组的中位数。要求算法的时间复杂度为 O(log (m+n)) 。

     示例 1:

     nums1 = [1, 3]
     nums2 = [2]

     中位数是 2.0
     示例 2:

     nums1 = [1, 2]
     nums2 = [3, 4]

     中位数是 (2 + 3)/2 = 2.5
     * @param nums1
     * @param nums2
     * @return
     */
    public static double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int[] tmpArr = new int[nums1.length+nums2.length];
        int right =0;
        int left =0;
        //third记录中间数组的索引
        int third = 0;
        while (left < nums1.length && right < nums2.length) {
            //从两个数组中取出最小的放入中间数组
            if (nums1[left] <= nums2[right]) {
                tmpArr[third++] = nums1[left++];
            } else {
                tmpArr[third++] = nums2[right++];
            }
        }

        //剩余部分依次放入中间数组
        while (left < nums1.length) {
            tmpArr[third++] = nums1[left++];
        }

        while (right < nums2.length) {
            tmpArr[third++] = nums2[right++];
        }
        System.out.print( "middle："+Arrays.toString(tmpArr));
        if(tmpArr.length%2==0){
            return (tmpArr[tmpArr.length/2-1] + tmpArr[tmpArr.length/2]) * 0.5;
        }else{
            return tmpArr[tmpArr.length/2];
        }
    }

    public int[] twoSum(int[] nums, int target) {
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length; j++) {
                if (i != j && nums[i] + nums[j] == target) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{0, 1};
    }

    public static int lengthOfLongestSubstring(String s) {
        if(s == null && s.length()==0 ){ return  0;}
        ArrayList<Character> list = new ArrayList<>();
        int max = 1;
        for (int i = 0; i < s.length() - 1; i++) {
            if(!list.contains(s.charAt(i))){
                list.add(s.charAt(i));
            }
            for (int j = 1 + i; j < s.length(); j++) {
                if (s.charAt(i) != s.charAt(j) && !list.contains(s.charAt(j))) {
                    list.add(s.charAt(j));
                } else {
                    list.clear();
                    break;
                }
                max = Math.max(max, list.size());
            }
        }
        return max;
    }
    /**
     * 插入排序
     *
     * @param array
     */
    public static void insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int temp = array[i];
            int j = i - 1;
            for (; j >= 0 && array[j] > temp; j--) {
                //将大于temp的值整体后移一个单位
                array[j + 1] = array[j];
            }
            array[j + 1] = temp;
            System.out.println(Arrays.toString(array) + " i=" + i);
        }
        System.out.println(Arrays.toString(array) + " insertSort");
    }

    /**
     * 希尔排序
     *
     * @param array
     */
    public static void shellSort(int[] array) {
        int i;
        int j;
        int temp;
        int gap = 1;
        int len = array.length;
        while (gap < len / 3) {
            gap = gap * 3 + 1;
        }
        for (; gap > 0; gap /= 3) {
            for (i = gap; i < len; i++) {
                temp = array[i];
                for (j = i - gap; j >= 0 && array[j] > temp; j -= gap) {
                    array[j + gap] = array[j];
                }
                array[j + gap] = temp;
            }
        }
        System.out.println(Arrays.toString(array) + " shellSort");
    }

    /**
     * 简单选择排序
     *
     * @param array
     */
    public static void selectSort(int[] array) {
        int position = 0;
        for (int i = 0; i < array.length; i++) {
            int j = i + 1;
            position = i;
            int temp = array[i];
            for (; j < array.length; j++) {
                if (array[j] < temp) {
                    temp = array[j];
                    position = j;
                }
            }
            array[position] = array[i];
            array[i] = temp;
        }
        System.out.println(Arrays.toString(array) + " selectSort");
    }

    /**
     * 堆排序
     *
     * @param array
     */
    public static void heapSort(int[] array) {
    /*
     *  第一步：将数组堆化
     *  beginIndex = 第一个非叶子节点。
     *  从第一个非叶子节点开始即可。无需从最后一个叶子节点开始。
     *  叶子节点可以看作已符合堆要求的节点，根节点就是它自己且自己以下值为最大。
     */
        int len = array.length - 1;
        int beginIndex = (len - 1) >> 1;
        for (int i = beginIndex; i >= 0; i--) {
            maxHeapify(i, len, array);
        }
    /*
     * 第二步：对堆化数据排序
     * 每次都是移出最顶层的根节点A[0]，与最尾部节点位置调换，同时遍历长度 - 1。
     * 然后从新整理被换到根节点的末尾元素，使其符合堆的特性。
     * 直至未排序的堆长度为 0。
     */
        for (int i = len; i > 0; i--) {
            swap(0, i, array);
            maxHeapify(0, i - 1, array);
        }
        System.out.println(Arrays.toString(array) + " heapSort");
    }

    private static void swap(int i, int j, int[] arr) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 调整索引为 index 处的数据，使其符合堆的特性。
     *
     * @param index 需要堆化处理的数据的索引
     * @param len   未排序的堆（数组）的长度
     */
    private static void maxHeapify(int index, int len, int[] arr) {
        int li = (index << 1) + 1; // 左子节点索引
        int ri = li + 1;           // 右子节点索引
        int cMax = li;             // 子节点值最大索引，默认左子节点。
        if (li > len) {
            return;       // 左子节点索引超出计算范围，直接返回。
        }
        if (ri <= len && arr[ri] > arr[li]) // 先判断左右子节点，哪个较大。
        {
            cMax = ri;
        }
        if (arr[cMax] > arr[index]) {
            swap(cMax, index, arr);      // 如果父节点被子节点调换，
            maxHeapify(cMax, len, arr);  // 则需要继续判断换下后的父节点是否符合堆的特性。
        }
    }

    /**
     * 冒泡排序
     *
     * @param array
     */
    public static void bubbleSort(int[] array) {
        int temp ;
        System.out.println(Arrays.toString(array) + " init");
        boolean isChanged ;
        for (int i = 0; i < array.length - 1; i++) {
            isChanged = false;
            for (int j = array.length - 1; j > i; j--) {
                if (array[j-1] > array[j]) {
                    temp = array[j-1];
                    array[j-1] = array[j];
                    array[j] = temp;
                    isChanged = true;
                }
                System.out.println(Arrays.toString(array) + " i=" + i + "j=" + j);
            }
            if(!isChanged) return;
        }
        System.out.println(Arrays.toString(array) + " bubbleSort");
    }
    /**
     * 鸡尾酒排序
     *
     * @param arr
     */
    public static void CocktailSort(int[] arr) {
        int i, temp, left = 0, right = arr.length - 1;
        while (left < right) {
            for (i = left; i < right; i++) {
                if (arr[i] > arr[i + 1]) {
                    temp = arr[i];
                    arr[i + 1] = temp;
                    arr[i] = arr[i + 1];
                }
                System.out.println(Arrays.toString(arr) + " left=" + left + "right=" + right);
            }
            right--;
            for (i = right; i > left; i--) {
                if (arr[i] < arr[i - 1]) {
                    temp = arr[i];
                    arr[i - 1] = temp;
                    arr[i] = arr[i - 1];
                }
                System.out.println(Arrays.toString(arr) + " left=" + left + "right=" + right);
            }
            left++;
        }
        System.out.println(Arrays.toString(arr) + " CocktailSort");
    }

    /**
     * 快速排序
     *
     * @param array
     */
    public static void quickSort(int[] array) {
        _quickSort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array) + " quickSort");
    }


    private static int getMiddle(int[] list, int low, int high) {
        int tmp = list[low];    //数组的第一个作为中轴
        while (low < high) {
            while (low < high && list[high] >= tmp) {
                high--;
            }


            list[low] = list[high];   //比中轴小的记录移到低端
            while (low < high && list[low] <= tmp) {
                low++;
            }
            list[high] = list[low];   //比中轴大的记录移到高端
        }
        list[low] = tmp;              //中轴记录到尾
        return low;                  //返回中轴的位置
    }


    private static void _quickSort(int[] list, int low, int high) {
        if (low < high) {
            int middle = getMiddle(list, low, high);  //将list数组进行一分为二
            _quickSort(list, low, middle - 1);      //对低字表进行递归排序
            _quickSort(list, middle + 1, high);      //对高字表进行递归排序
        }
    }

    //快速排序
   static void quick_sort(int s[], int l, int r)
    {
        if (l < r)
        {
            //Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 参见注1
            int i = l, j = r, x = s[l];
            while (i < j)
            {
                while(i < j && s[j] >= x) // 从右向左找第一个小于x的数
                    j--;
                    s[i] = s[j];

                while(i < j && s[i] < x) // 从左向右找第一个大于等于x的数
                    i++;
                    s[j] = s[i];
            }
            s[i] = x;
            quick_sort(s, l, i - 1); // 递归调用
            quick_sort(s, i + 1, r);
        }
        System.out.println(Arrays.toString(s) + " quickSort");
    }

    /**
     * 归并排序
     *
     * @param array
     */
    public static void mergingSort(int[] array) {
        sort(array, 0, array.length - 1);
        System.out.println(Arrays.toString(array) + " mergingSort");
    }

    private static void sort(int[] data, int left, int right) {
        if (left < right) {
            //找出中间索引
            int center = (left + right) / 2;
            //对左边数组进行递归
            sort(data, left, center);
            //对右边数组进行递归
            sort(data, center + 1, right);
            //合并
            merge(data, left, center, right);
        }
    }

    private static void merge(int[] data, int left, int center, int right) {
        int[] tmpArr = new int[data.length];
        int mid = center + 1;
        //third记录中间数组的索引
        int third = left;
        int tmp = left;
        while (left <= center && mid <= right) {
            //从两个数组中取出最小的放入中间数组
            if (data[left] <= data[mid]) {
                tmpArr[third++] = data[left++];
            } else {
                tmpArr[third++] = data[mid++];
            }
        }

        //剩余部分依次放入中间数组
        while (mid <= right) {
            tmpArr[third++] = data[mid++];
        }

        while (left <= center) {
            tmpArr[third++] = data[left++];
        }

        //将中间数组中的内容复制回原数组
        while (tmp <= right) {
            data[tmp] = tmpArr[tmp++];
        }
    }



    /**
     * 基数排序
     *
     * @param array
     */
    public static void radixSort(int[] array) {
        //首先确定排序的趟数;
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        int time = 0;
        //判断位数;
        while (max > 0) {
            max /= 10;
            time++;
        }


        //建立10个队列;
        ArrayList<ArrayList<Integer>> queue = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ArrayList<Integer> queue1 = new ArrayList<>();
            queue.add(queue1);
        }


        //进行time次分配和收集;
        for (int i = 0; i < time; i++) {
            //分配数组元素;
            for (int anArray : array) {
                //得到数字的第time+1位数;
                int x = anArray % (int) Math.pow(10, i + 1) / (int) Math.pow(10, i);
                ArrayList<Integer> queue2 = queue.get(x);
                queue2.add(anArray);
                queue.set(x, queue2);
            }
            int count = 0;//元素计数器;
            //收集队列元素;
            for (int k = 0; k < 10; k++) {
                while (queue.get(k).size() > 0) {
                    ArrayList<Integer> queue3 = queue.get(k);
                    array[count] = queue3.get(0);
                    queue3.remove(0);
                    count++;
                }
            }
        }
        System.out.println(Arrays.toString(array) + " radixSort");
    }

//    public static  String convert(String s, int numRows) {
//        StringBuilder sb = new StringBuilder();
//        int sum = 2*(numRows-1);
//        int a = 2*(numRows-1);
//        int b = sum - a;
//        int c = 0;
//        for (int i = 0; i <s.length() ; i++) {
//            sb.append(s.charAt(c));
//            if(b > s.length()){
//                a = a - 2;
//                b = b + 2;
//            }
//        }
//    }

}


package cn.jh;

import org.junit.Test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;

public class test {
    @Test
    public void test(){
        Solution solution = new Solution();
        solution.fib(5);

    }
    public class Solution {
        public int fib(int n) {
            if (n < 2){
                return n;
            }
            return fib(n - 1) + fib(n - 2);
        }

        public int sum(int left,int right){
            if (left==0){
                return 0;
            }
            int leftTemp=sum(left-1,left-2);
            int rightTemp=sum(right-1,right-2);
            return leftTemp+rightTemp;
        }

    }



    public class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }

    public class ListNode {
      int val;
      ListNode next;
      ListNode(int x) {
          val = x;
         next = null;
     }
  }

}

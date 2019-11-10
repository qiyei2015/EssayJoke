package com.qiyei.sdk.launchstarter.task;

import java.util.Stack;

/**
 * @author Created by qiyei2015 on 2018/7/9.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 有向图中寻找环
 */
public class DirectCycle {
    /**
     * i是否被访问
     */
    private boolean[] marked;
    /**
     * 表示i访是从哪个顶点访问过来的
     */
    private int[] edgeTo;
    /**
     * 环
     */
    private Stack<Integer> cycle;
    /**
     * 递归调用栈上的所有点
     */
    private boolean[] onStack;

    /**
     * 构造方法
     * @param graph
     */
    public DirectCycle(DirectionGraph graph) {
        onStack = new boolean[graph.V()];
        marked = new boolean[graph.V()];
        edgeTo = new int[graph.V()];
        for (int v = 0;v < graph.V() ;v++){
            dfs(graph,v);
        }
    }



    /**
     * 是否有环
     * @return
     */
    public boolean hasCycle(){
        return cycle != null;
    }

    /**
     * 环
     * @return
     */
    public Iterable<Integer> cycle(){
        return cycle;
    }

    private void dfs(DirectionGraph graph, int v) {
        marked[v] = true;
        onStack[v] = true;
        for (int w : graph.adj(v)){
            if (hasCycle()){
                return;
            }
            if (!marked[w]){
                //表示顶点w是从v访问过来的
                edgeTo[w] = v;
                dfs(graph,w);
            }else if (onStack[w]){
                //被访问过了
                cycle = new Stack<>();

                for (int x = v; x!= w;x = edgeTo[x]){
                    cycle.push(x);
                }
                cycle.push(w);
                cycle.push(v);
            }
        }

        onStack[v] = false;
    }
}

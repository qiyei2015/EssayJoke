package com.qiyei.performance.bootstarter.sort;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Vector;

/**
 * @author Created by qiyei2015 on 2019/11/15.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 有向无环图的拓扑排序算法
 */
public class Graph {

    /**
     * 顶点数
     */
    private int mV;
    /**
     * 邻接表
     */
    private List<Integer>[] mAdj;

    public Graph(int v) {
        mV = v;
        mAdj = new ArrayList[mV];
        for (int i = 0;i < mV;i++){
            mAdj[i] = new ArrayList<>();
        }
    }

    /**
     * 添加一条 u-> v 的边
     * @param u
     * @param v
     */
    public void addEdge(int u,int v){
        mAdj[u].add(v);
    }

    public List<Integer> topologicalSort(){

        int indegree[] = new int[mV];

        for (int i = 0; i < mV; i++) {//初始化所有点的入度数量
            ArrayList<Integer> temp = (ArrayList<Integer>) mAdj[i];
            for (int node : temp) {
                indegree[node]++;
            }
        }
        Queue<Integer> queue = new LinkedList<Integer>();
        for (int i = 0; i < mV; i++) {//找出所有入度为0的点
            if (indegree[i] == 0) {
                queue.add(i);
            }
        }
        int cnt = 0;
        Vector<Integer> topOrder = new Vector<Integer>();
        while (!queue.isEmpty()) {
            int u = queue.poll();
            topOrder.add(u);
            for (int node : mAdj[u]) {//找到该点（入度为0）的所有邻接点
                if (--indegree[node] == 0) {//把这个点的入度减一，如果入度变成了0，那么添加到入度0的队列里
                    queue.add(node);
                }
            }
            cnt++;
        }
        if (cnt != mV) {//检查是否有环，理论上拿出来的点的次数和点的数量应该一致，如果不一致，说明有环
            throw new IllegalStateException("Exists a cycle in the graph");
        }
        return topOrder;
    }
}

package com.qiyei.sdk.launchstarter.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * @author Created by qiyei2015 on 2019/4/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description: 有向图
 */
public class DirectionGraph {

    /**
     * 顶点数
     */
    private int mV;
    /**
     * 邻接表表示的边
     */
    private List<Integer>[] mAdj;

    private Stack<Integer> mStack;
    /**
     * 记录是否被访问过
     */
    private boolean[] mMarked;

    public DirectionGraph(int v) {
        mV = v;
        mAdj = new List[v];
        for (int i = 0 ;i < mAdj.length ; i++){
            mAdj[i] = new ArrayList<>();
        }
        mStack = new Stack<>();
        mMarked = new boolean[v];
    }

    /**
     * @return {@link #mV}
     */
    public int V() {
        return mV;
    }

    /**
     * @return {@link #mAdj}
     */
    public List<Integer> adj(int v) {
        return mAdj[v];
    }

    /**
     * 添加一条 u -> v的有向边
     * @param u
     * @param v
     */
    public void addEdge(int u,int v){
        mAdj[u].add(v);
    }

    /**
     * 顶点的拓扑排序
     * @return
     */
    public List<Integer> topologySort(){
        DirectCycle cycle = new DirectCycle(this);
        if (cycle.hasCycle()){
            return null;
        }
        //初始化所有点的入度
        for (int i = 0; i < mV ;i++){
            //未进行深度优先搜索，进行深度优先搜索
            if (!mMarked[i]){
                dfs(i);
            }
        }

        return mStack;
    }

    /**
     * 从v开始dfs
     * @param v
     */
    private void dfs(int v){
        //标志已访问
        mMarked[v] = true;
        for (int i : mAdj[v]){
            if (!mMarked[i]){
                dfs(i);
            }
        }
        mStack.push(v);
    }
}

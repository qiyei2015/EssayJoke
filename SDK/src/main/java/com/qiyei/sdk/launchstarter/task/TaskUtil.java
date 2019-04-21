package com.qiyei.sdk.launchstarter.task;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Created by qiyei2015 on 2019/4/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class TaskUtil {

    public static List<Task> sort(List<Task> originalList, List<Class<? extends Task>> taskClazzList) {
        if (originalList == null || originalList.size() == 0 || taskClazzList == null){
            return null;
        }
        List<Task> newList = new ArrayList<>();
        List<Integer> indexList = new ArrayList<>();

        DirectionGraph graph = new DirectionGraph(originalList.size());
        //保存是否被依赖
        Set<Integer> dependSet = new HashSet<>();
        //构造图
        for (int i = 0 ;i < originalList.size(); i++){
            Task task = originalList.get(i);
            //没有依赖，或者已经被分发了，就不添加了
            if (task.getDepends() == null || task.getDepends().size() == 0 || task.getStatus() == TaskStatus.DISPATCH){
                continue;
            }
            //对于task的每一个依赖，添加一条指向该task的边
            for (Class<? extends Task> clazz : taskClazzList){
                int indexClazz = getIndexClazz(task,clazz,taskClazzList);
                //添加一条 indexClazz到 i的依赖
                graph.addEdge(indexClazz,i);
                //记录依赖的index
                dependSet.add(indexClazz);
            }
        }
        indexList = graph.topologySort();
        newList = getResult(indexList,dependSet,originalList);
        return newList;
    }

    private static int getIndexClazz(Task task,Class<? extends Task> clazz,List<Class<? extends Task>> taskClazzList) {
        int index = taskClazzList.indexOf(clazz);
        if (index < 0) {
            throw new IllegalStateException(task.getName() +
                    " depends on " + clazz.getSimpleName() + " can not be found in task list ");
        }
        return index;
    }

    private static List<Task> getResult(List<Integer> indexList, Set<Integer> dependSet, List<Task> originalList) {
        List<Task> dependList = new ArrayList<>();
        List<Task> inDependList = new ArrayList<>();
        List<Task> newList = new ArrayList<>();

        for (int index : indexList){
            if (dependSet.contains(index)){
                dependList.add(originalList.get(index));
            }else {
                inDependList.add(originalList.get(index));
            }
        }

        //被以来的先执行
        newList.addAll(dependList);
        newList.addAll(inDependList);
        return newList;
    }
}

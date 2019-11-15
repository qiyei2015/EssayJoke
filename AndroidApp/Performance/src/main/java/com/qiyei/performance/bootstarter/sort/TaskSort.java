package com.qiyei.performance.bootstarter.sort;



import androidx.annotation.NonNull;

import com.qiyei.performance.bootstarter.task.State;
import com.qiyei.performance.bootstarter.task.Task;
import com.qiyei.performance.bootstarter.utils.Logger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Created by qiyei2015 on 2019/11/13.
 * @version: 1.0
 * @email: 1273482124@qq.com
 * @description:
 */
public class TaskSort {

    private static final String TAG = "TaskSort";

    /**
     * 高优先级的Task
     */
    private static List<Task> sNewTasksHigh = new ArrayList<>();

    /**
     * 任务的有向无环图的拓扑排序
     * @param originTasks
     * @param clsLaunchTasks
     * @return
     */
    public static synchronized List<Task> sort(List<Task> originTasks,List<Class<? extends Task>> clsLaunchTasks){
        long makeTime = System.currentTimeMillis();

        Set<Integer> dependSet = new HashSet<>();
        Graph graph = new Graph(originTasks.size());

        for (int i = 0; i < originTasks.size(); i++) {
            Task task = originTasks.get(i);
            if (task.getState() == State.DISPATCH || task.dependsOn() == null || task.dependsOn().size() == 0) {
                continue;
            }
            for (Class cls : task.dependsOn()) {
                int indexOfDepend = getIndexOfTask(originTasks, clsLaunchTasks, cls);
                if (indexOfDepend < 0) {
                    throw new IllegalStateException(task.getName() +
                            " depends on " + cls.getSimpleName() + " can not be found in task list ");
                }
                dependSet.add(indexOfDepend);
                graph.addEdge(indexOfDepend, i);
            }
        }
        List<Integer> indexList = graph.topologicalSort();
        List<Task> newTasksAll = getResultTasks(originTasks, dependSet, indexList);

        Logger.d(TAG,"task analyse cost makeTime " + (System.currentTimeMillis() - makeTime));
        printAllTaskName(newTasksAll);
        return newTasksAll;
    }


    @NonNull
    private static List<Task> getResultTasks(List<Task> originTasks,
                                             Set<Integer> dependSet, List<Integer> indexList) {
        List<Task> newTasksAll = new ArrayList<>(originTasks.size());
        List<Task> newTasksDepended = new ArrayList<>();// 被别人依赖的
        List<Task> newTasksWithOutDepend = new ArrayList<>();// 没有依赖的
        List<Task> newTasksRunAsSoon = new ArrayList<>();// 需要提升自己优先级的，先执行（这个先是相对于没有依赖的先）
        for (int index : indexList) {
            if (dependSet.contains(index)) {
                newTasksDepended.add(originTasks.get(index));
            } else {
                Task task = originTasks.get(index);
                if (task.needRunAsSoon()) {
                    newTasksRunAsSoon.add(task);
                } else {
                    newTasksWithOutDepend.add(task);
                }
            }
        }
        // 顺序：被别人依赖的————》需要提升自己优先级的————》需要被等待的————》没有依赖的
        sNewTasksHigh.addAll(newTasksDepended);
        sNewTasksHigh.addAll(newTasksRunAsSoon);
        newTasksAll.addAll(sNewTasksHigh);
        newTasksAll.addAll(newTasksWithOutDepend);
        return newTasksAll;
    }

    private static void printAllTaskName(List<Task> newTasksAll) {
        if (true) {
            return;
        }
        for (Task task : newTasksAll) {
            Logger.d(TAG,task.getName());
        }
    }

    public static List<Task> getTasksHigh() {
        return sNewTasksHigh;
    }

    /**
     * 获取任务在任务列表中的index
     *
     * @param originTasks
     * @param taskName
     * @return
     */
    private static int getIndexOfTask(List<Task> originTasks,
                                      List<Class<? extends Task>> clsLaunchTasks, Class cls) {
        int index = clsLaunchTasks.indexOf(cls);
        if (index >= 0) {
            return index;
        }

        // 仅仅是保护性代码
        final int size = originTasks.size();
        for (int i = 0; i < size; i++) {
            if (cls.getSimpleName().equals(originTasks.get(i).getName())) {
                return i;
            }
        }
        return index;
    }

}

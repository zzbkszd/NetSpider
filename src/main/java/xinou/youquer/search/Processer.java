package xinou.youquer.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.*;

/**
 * 处理线程
 * Created by shizhida on 15/10/12.
 */
public class Processer<T extends Task> implements Runnable{

    protected Stack<T> pool;//任务池
    protected ThreadPoolExecutor executor;//线程池
    protected List<ExecuteFutre> obList;//观察线程列表
    protected boolean stop = false;//停止开关

    public Processer(){
        pool = new Stack<>();
        obList = new ArrayList<>();
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
    }

    //增加任务
    public synchronized void push (T url){
        pool.push(url);
    }

    //提取任务
    public synchronized T pop(){
        if(pool.empty())
            return null;
        T url = pool.pop();
        return url;
    }

    //当所有任务执行完毕后停止
    public void stop(){
        this.stop = true;
    }

    @Override
    public void run(){
        while(true){
            if(pool.size()==0&&stop&&obList.size()==0){
                executor.shutdown();
                break;
            }
            else{
                //执行一个任务
                Task t = pop();
                if(t!=null) {
                    ExecuteFutre task = new ExecuteFutre(t::execute);
                    obList.add(task);
                    executor.execute(task);
                }

            }
            checkExecutingTask();
        }
    }

    /**
     * 检视所有正在执行的任务，将超时的任务关闭，已经完成的移出观察列表
     */
    private void checkExecutingTask() {
        List<Integer> toDelete = new ArrayList<>();
        for(int i=0;i<obList.size();i++){
            ExecuteFutre task = obList.get(i);
            if(task.isDone()||task.isCancelled())
                toDelete.add(i);
            if(System.currentTimeMillis()-task.createDate>60*1000*3)
                task.cancel(true);
        }
        //防止动态修改，根据list性质，索引在i之前的不会改变,要从后向前扫描
        for (int i=toDelete.size()-1;i>=0;i--) {
            obList.remove(i);
        }
    }

    /**
     * 封装future以监控超时时间
     */
    private class ExecuteFutre extends FutureTask<Integer>{

        long createDate = System.currentTimeMillis();
        public ExecuteFutre(Callable<Integer> callable) {
            super(callable);
        }

        public ExecuteFutre(Runnable runnable, Integer result) {
            super(runnable, result);
        }
    }


}

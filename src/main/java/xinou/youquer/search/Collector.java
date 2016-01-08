package xinou.youquer.search;

import java.util.List;

/**
 * Created by shizhida on 15/10/12.
 */
public abstract class Collector extends Task{

    protected String url;

    protected Processer processer;

    public Collector (String url,Processer processer){
        this.url = url;
        this.processer = processer;
    }

    /**
     * 解析一个列表页面
     * @return
     */
    public abstract List<String> scanList();

}

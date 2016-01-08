package xinou.youquer.search;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by shizhida on 15/10/12.
 */
public abstract class Task extends NetWorkRole {

    String savePath;
    public Task(String savePath){
        this.savePath = savePath;
    }
    public abstract Integer execute();

    protected void saveFile(String title,byte[] content) throws IOException {

//        String baseUri = "/Users/shizhida/Documents/image/";

        File path = new File(savePath);
        if(!path.exists())
            path.mkdirs();

        File file = new File(savePath+title);

        if(file.exists())
            return;

        file.createNewFile();

        FileOutputStream out = new FileOutputStream(file);

//        out.write(title.getBytes());

        out.write(content);

        System.out.println("保存完成-"+title);

        out.close();

    }

}

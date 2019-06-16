package com.app.nytimes.imageLoader;

import java.io.File;
import android.content.Context;
import android.content.ContextWrapper;


class FileCache {

    private File cacheDir;

    FileCache(Context context){

        //Find the dir at SDCARD to save cached images
        ContextWrapper cw = new ContextWrapper(context);
        cacheDir = cw.getDir("s2s_cache_dir", Context.MODE_PRIVATE);
        if(!cacheDir.exists()){
            // create cache dir in your application context
            cacheDir.mkdirs();
        }
    }

    File getFile(String url){
        //Identify images by hashcode or encode by URLEncoder.encode.
        String filename=String.valueOf(url.hashCode());

        return new File(cacheDir, filename);

    }

    void clear(){
        // list all files inside cache directory
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        //delete all cache directory files
        for(File f:files)
            f.delete();
    }

}

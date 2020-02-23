package com.api.service;

import com.api.exception.FileException;
import com.api.exception.InfoCode;
import com.api.model.BatchAssingment;

import java.io.File;

/**
 * 删除文件
 *
 * @author 李星源
 * @version 1.0
 */
public class DeleteFiles {


    public static void deleteFiles(BatchAssingment batchAssingment,String path0){
        for (int i=0;i<batchAssingment.getCount();i++){
            String name=batchAssingment.getTaskId()+"_"+batchAssingment.getAccountIdList().get(i);
            File file=new File(path0,name+".zip");
            if (!file.exists()){
                throw new FileException(InfoCode.FILE_EMPTY,null);
            }
            file.delete();
        }
    }
}

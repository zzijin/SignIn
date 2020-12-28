package com.example.administrator.myapp.client.file;

import java.io.File;

public class PictureFile extends BaseFile {
    PictureFile(File file) {
        super(file);
    }

    PictureFile(String fileName, String fileExtensionName, int fileSize, int filePackCount) {
        super(fileName, fileExtensionName, fileSize, filePackCount);
    }
}

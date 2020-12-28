package com.example.administrator.myapp.client.file;

import java.io.File;

public class TextFile extends BaseFile{

    TextFile(File file) {
        super(file);
    }

    TextFile(String fileName, String fileExtensionName, int fileSize, int filePackCount) {
        super(fileName, fileExtensionName, fileSize, filePackCount);
    }
}
